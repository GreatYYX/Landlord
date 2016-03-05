package com.watch0ut.landlord.server;

import java.net.InetSocketAddress;
import java.util.*;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.statemachine.context.AbstractStateContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.object.Dealer;
import com.watch0ut.landlord.object.Hall;
import com.watch0ut.landlord.object.Player;
import com.watch0ut.landlord.object.Table;
import com.watch0ut.landlord.object.cardtype.CardType;
import com.watch0ut.landlord.server.database.DatabaseHelper;
import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.*;

/**
 * Created by GreatYYX on 12/25/15.
 */
public class WServerHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WServerHandler.class);

    private final Map<Integer, IoSession> sessionMap_ = Collections.synchronizedMap(new HashMap<Integer, IoSession>());
    private final Map<Integer, Player> playerMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>()); // 用户总表
    private final Map<Integer, Player> playerBasicMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>()); // 用户简化表
    private final DatabaseHelper dbHelper_ = new DatabaseHelper();
    private final Hall hall_ = new Hall();

    private static final String CONTEXT = "WServerContext";
    static class WServerContext extends AbstractStateContext {
        public int uid = -1; // -1 means NOT_CONNECTED
    }

    /**
     * Socket创建
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        session.setAttribute(CONTEXT, new WServerContext());
    }

    /**
     * Socket开启
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOGGER.info("[SESS {}] Session Opened", session.getId());
    }

    /**
     * Socket关闭
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        //为防止强制退出，logout一次
        logout(session, new LogoutCommand());
        LOGGER.info("[SESS {}] Session Closed", session.getId());
        super.sessionClosed(session);
    }

    /**
     * 异常
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.warn("[SESS {}] Exception Caught: {}", session.getId(), cause);
        session.close(true);
    }

    /**
     * 消息路由
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractCommand cmd = (AbstractCommand)message;
        String cmdName = cmd.getName();
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        if(cmdName.equalsIgnoreCase("login") && ctx.uid == -1) {
            login(session, (LoginCommand) message);
        } else if(cmdName.equalsIgnoreCase("logout") && playerInState(ctx.uid, Player.STATE.Idle)) {
            logout(session, (LogoutCommand) message);
        } else if(cmdName.equalsIgnoreCase("seat") && playerInState(ctx.uid, Player.STATE.Idle)) {
            seat(session, (SeatCommand) message);
        } else if(cmdName.equalsIgnoreCase("unseat") &&
                (playerInState(ctx.uid, Player.STATE.Seated) || playerInState(ctx.uid, Player.STATE.Ready))) {
            unseat(session, (UnseatCommand) message);
        } else if(cmdName.equalsIgnoreCase("ready") && playerInState(ctx.uid, Player.STATE.Seated)) {
            ready(session, (ReadyCommand) message);
        } else if(cmdName.equalsIgnoreCase("play") && playerInState(ctx.uid, Player.STATE.Play)) {
            play(session, (PlayCommand) message);
        } else if(cmdName.equalsIgnoreCase("text") &&
                (playerInState(ctx.uid, Player.STATE.Seated) || playerInState(ctx.uid, Player.STATE.Wait) ||
                 playerInState(ctx.uid, Player.STATE.Play) || playerInState(ctx.uid, Player.STATE.Finish))) {
            textMessage(session, (TextCommand) message);
        } else if(cmdName.equalsIgnoreCase("disconnect")) {
            disconnect(session, (DisconnectCommand) message);
        } else {
            LOGGER.warn("[SESS {}] Unhandled or invalid state event {}", session.getId(), cmd.getClass());
        }
    }

    /**
     * 客户端主动请求断开socket连接
     * 需要先执行logout
     */
    public void disconnect(IoSession session, DisconnectCommand cmd) {
        logout(session, new LogoutCommand());
        session.close(true); // sessionClosed()
        LOGGER.info("[SESS {}] Disconnect", session.getId());
    }

    /**
     * 登陆
     * 需已经建立Socket
     */
    public void login(IoSession session, LoginCommand cmd) {
        // 数据库读取并更新
        String ip = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
        Player player = dbHelper_.login(cmd.getUser(), cmd.getPassword(), ip);
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        if(player != null) {
            ctx.uid = player.getId();
            synchronized(sessionMap_) {
                sessionMap_.put(ctx.uid, session);
            }
            synchronized(playerMap_) {
                playerMap_.put(ctx.uid, player);
            }
            synchronized(playerBasicMap_) {
                playerBasicMap_.put(ctx.uid, player.getBasicPlayer());
            }

            // 将对象返回用户
            AbstractCommand cmdRes = new LoginResponseCommand(player);
            sendCommand(session, cmdRes);

            // 客户端刷新
            AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
            broadcastCommand(cmdRefresh);

            LOGGER.info("[SESS {}] Login success: {}", session.getId(), cmd.getUser());
        } else {
            AbstractCommand cmdRes = new LoginResponseCommand("Login error.");
            sendCommand(session, cmdRes);
            LOGGER.info("[SESS {}] Login fail: {}", session.getId(), cmd.getUser());
        }
    }

    /**
     * 登出
     * IDLE->NOT_CONNECTED
     * 登出后不断开Socket
     * 当客户端断线时（sessionClosed）或用户强制退出时（客户端需禁止游戏中玩家退出），
     * 也会强制调用该方法，此时如果正在进行游戏，所有同桌用户将被强制结束游戏。
     */
    public void logout(IoSession session, LogoutCommand cmd) {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        Player player = playerMap_.get(ctx.uid);
        if(player != null) {
            // 如果还在游戏，所有玩家强制停止
            if(playerInState(ctx.uid, Player.STATE.Wait) || playerInState(ctx.uid, Player.STATE.Play)
                    || playerInState(ctx.uid, Player.STATE.Finish)) {
                Table table = hall_.getTable(player.getTableId());
                AbstractCommand cmdForceStop = new GameOverCommand(true);
                for(int i = 0; i < 4; i++) {
                    Player currPlayer = table.getPlayer(i);
                    if(currPlayer != player) { // 同桌其余玩家被结束游戏
                        currPlayer.setState(Player.STATE.Seated);
                        sendCommand(currPlayer.getId(), cmdForceStop);
                    } else { // 自身被处以惩罚
                        dbHelper_.disconnectPenalty(player.getId());
                    }
                }
            }
            // 如果还在座位
            if(player.getTableId() != Table.UNSEATED) {
                hall_.getTable(player.getTableId()).unseat(player, player.getTablePosition());
            }
            synchronized(sessionMap_) {
                sessionMap_.remove(ctx.uid);
            }
            synchronized(playerMap_) {
                playerMap_.remove(ctx.uid);
            }
            synchronized(playerBasicMap_) {
                playerBasicMap_.remove(ctx.uid);
            }

            //客户端刷新
            AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
            broadcastCommand(cmdRefresh);

            LOGGER.info("[SESS {}] Logout", session.getId());
        }
    }

    /**
     * 入座
     * IDLE->SEATED
     */
    public void seat(IoSession session, SeatCommand cmd) {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        Player player = playerMap_.get(ctx.uid);
        boolean ret = false;
        synchronized (player) {
            ret = hall_.getTable(cmd.getTableId()).seat(player, cmd.getTablePosition_());
        }
        if(ret) {
            // 告知用户入座
            AbstractCommand cmdRes = new SeatResponseCommand(SeatResponseCommand.SUCCESS, "");
            sendCommand(session, cmdRes);

            // 推送
            AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
            broadcastCommand(cmdRefresh);

            LOGGER.info("[SESS {}] seated", session.getId());
        } else {
            AbstractCommand cmdRes = new SeatResponseCommand(SeatResponseCommand.ERROR, "");
            sendCommand(session, cmdRes);
        }
    }

    /**
     * 空闲（进入/返回大厅）
     * SEATED->IDLE, READY->IDLE
     * 只允许seated和ready之后离开座位进入空闲状态
     * 不允许换座，即从seated到seated与从ready到seated
     */
    public void unseat(IoSession session, UnseatCommand cmd) {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        Player player = playerMap_.get(ctx.uid);
        boolean ret = false;
        synchronized (player) {
            ret = hall_.getTable(player.getTableId()).unseat(player, player.getTablePosition());
        }

        // 推送
        AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
        broadcastCommand(cmdRefresh);

        LOGGER.info("[SESS {}] unseated", session.getId());
    }

    /**
     * 准备
     * SEATED->READY
     * 同桌全部READY后READY->WAIT，其中一位为READY->WAIT->PLAY
     */
    public void ready(IoSession session, ReadyCommand cmd) {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        Player player = playerMap_.get(ctx.uid);
        synchronized (player) {
            player.setState(Player.STATE.Ready);
        }

        //player是否都已ready
        Table table = hall_.getTable(player.getTableId());
        if(table.getPlayer(0).getState().equals(Player.STATE.Ready) &&
                table.getPlayer(1).getState().equals(Player.STATE.Ready) &&
                table.getPlayer(2).getState().equals(Player.STATE.Ready) &&
                table.getPlayer(3).getState().equals(Player.STATE.Ready)) {
            Dealer dealer = table.getDealer();
            dealer.gameInit();
            dealer.shuffle();
            GameStartCommand cmdStart = new GameStartCommand(dealer.getSeqId());
            cmdStart.setFirstPlayerUid(dealer.getFirstPlayer().getId());
            for(int i = 0; i < 4; i++) {
                Player p = playerMap_.get(table.getPlayer(i).getId());
                synchronized (p) {
                    p.setState(Player.STATE.Wait); // Ready->Wait
                }
                cmdStart.setCards(p.getCards());
                sendCommand(p.getId(), cmdStart);
            }
            // 设定开局计时器
            dealer.setTimer(new Timer());
            dealer.getTimer().schedule(new GameTimer(dealer),
                    Configuration.CLIENT_START_DELAY, Configuration.PLAY_DURATION_SERVER);
        }

        AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
        broadcastCommand(cmdRefresh);

        LOGGER.info("[SESS {}] ready", session.getId());
    }

    /**
     * 游戏进行（出牌）
     * 当前Player状态PLAY->WAIT，下家WAIT->PLAY
     */
    public void play(IoSession session, PlayCommand cmd) throws Exception {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        AbstractCommand cmdRes;
        Player player = playerMap_.get(ctx.uid);
        Dealer dealer = hall_.getTable(player.getTableId()).getDealer();
        if(dealer.getSeqId() == cmd.getSeqId()) {
            playOrFinish(dealer, cmd.getCardType());
            cmdRes = new PlayResponseCommand(dealer.getSeqId(), PlayResponseCommand.SUCCESS);
            sendCommand(session, cmdRes);
        } else {
            // timeout后接收到到Play指令被忽略
            cmdRes = new PlayResponseCommand(dealer.getSeqId(), PlayResponseCommand.ERROR);
            sendCommand(session, cmdRes);
        }
    }

    /**
     * 出牌或结束
     */
    protected void playOrFinish(Dealer dealer, CardType cardType) throws Exception {
        dealer.getTimer().cancel(); // 关闭计时器

        Player player = playerMap_.get(dealer.getPlayingPlayer().getId());
        boolean moveOn = false;
        synchronized (player) {
            player.setState(Player.STATE.Wait); // 当前用户状态切换为Wait
            moveOn = dealer.playAndMoveNext(dealer.getPlayingPlayer(), cardType);
        }
        if(moveOn) {
            // 游戏继续
            AbstractCommand cmdNext = new NextPlayCommand(cardType, dealer.getPlayingPlayer().getId(), dealer.seqIdIncrement());
            Table table = dealer.getTable();
            for(int i = 0; i < 4; i++) {
                sendCommand(table.getPlayer(i).getId(), cmdNext);
            }

            // 重置计时器
            dealer.setTimer(new Timer());
            dealer.getTimer().schedule(new GameTimer(dealer), 0, Configuration.PLAY_DURATION_SERVER);
        } else {
            // 游戏结束
            AbstractCommand cmdGameOver = new GameOverCommand();
            for(int i = 0; i < 4; i++) {
                sendCommand(dealer.getTable().getPlayer(i).getId(), cmdGameOver);
                playerMap_.get(dealer.getTable().getPlayer(i).getId()).setState(Player.STATE.Idle);

            }
        }
    }

    /**
     * 每桌对于入座用户聊天信息的群发
     * SEATED, READY, PLAY, WAIT, FINISH
     */
    public void textMessage(IoSession session, TextCommand cmd) {
        WServerContext ctx = (WServerContext)session.getAttribute(CONTEXT);
        //过滤内容
        cmd.doFilter();
        //群发同桌玩家
        int tableId = playerMap_.get(ctx.uid).getTableId();
        for(int i = 0; i < 4; i++) {
            int playerId = hall_.getTable(tableId).getPlayer(i).getId();
            sendCommand(playerId, cmd);
        }
    }

    /**
     * 对指定uid的player发送指令
     * @param uid
     * @param cmd
     */
    private void sendCommand(int uid, AbstractCommand cmd) {
        sendCommand(sessionMap_.get(uid), cmd);
    }

    /**
     * 对指定session发送指令
     * @param session
     * @param cmd
     */
    private void sendCommand(IoSession session, AbstractCommand cmd) {
        if(session.isConnected())
            session.write(cmd);
    }

    /**
     * 向所有player广播
     * @param cmd
     */
    private void broadcastCommand(AbstractCommand cmd) {
        synchronized(sessionMap_) {
            for(IoSession sess : sessionMap_.values()) {
                if(sess.isConnected()) {
                    sess.write(cmd);
                }
            }
        }
    }

    /**
     * 玩家是否在该状态
     */
    private boolean playerInState(int uid, Player.STATE state) {
        return playerMap_.get(uid).getState() == state;
    }

    class GameTimer extends TimerTask {
        private Dealer dealer_;
        boolean timeout = false;

        public GameTimer(Dealer dealer) {
            dealer_ = dealer;
        }

        @Override
        public void run() {
            if(!timeout) { // 第一次执行
                Player player = playerMap_.get(dealer_.getFirstPlayer().getId());
                synchronized (player) {
                    player.setState(Player.STATE.Play);
                }
                timeout = true;
            } else { // 第二次则timeout（不出牌）
                try {
                    playOrFinish(dealer_, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
