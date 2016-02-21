//package com.watch0ut.landlord.server;
//
//import com.watch0ut.landlord.Configuration;
//import com.watch0ut.landlord.command.AbstractCommand;
//import com.watch0ut.landlord.command.concrete.*;
//import com.watch0ut.landlord.object.Dealer;
//import com.watch0ut.landlord.object.Hall;
//import com.watch0ut.landlord.object.Player;
//import com.watch0ut.landlord.object.Table;
//import com.watch0ut.landlord.object.cardtype.CardType;
//import com.watch0ut.landlord.server.database.DatabaseHelper;
//import org.apache.mina.core.service.IoHandlerAdapter;
//import org.apache.mina.core.session.IoSession;
//import org.apache.mina.statemachine.StateControl;
//import org.apache.mina.statemachine.annotation.IoHandlerTransition;
//import org.apache.mina.statemachine.annotation.IoHandlerTransitions;
//import org.apache.mina.statemachine.annotation.State;
//import org.apache.mina.statemachine.context.AbstractStateContext;
//import org.apache.mina.statemachine.context.StateContext;
//import org.apache.mina.statemachine.event.Event;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.net.InetSocketAddress;
//import java.util.*;
//
//import static org.apache.mina.statemachine.event.IoHandlerEvents.*;
//
///**
// * Created by GreatYYX on 14-10-20.
// *
// * 所有player的状态操作在本类实现
// * 注意区分player的状态和ServerHandler状态机的状态
// */
//public class WServerHandlerSM extends IoHandlerAdapter {
//    private static final Logger LOGGER = LoggerFactory.getLogger(WServerHandlerSM.class);
//
//    private final Map<Integer, IoSession> sessionMap_ = Collections.synchronizedMap(new HashMap<Integer, IoSession>());
////    private final Set<String> users_ = Collections.synchronizedSet(new HashSet<String>());
//    private final Hall hall_ = new Hall();
//    private final Map<Integer, Player> playerMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>()); // 用户总表
//    private final Map<Integer, Player> playerBasicMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>()); // 用户简化表
//    private DatabaseHelper dbHelper_ = new DatabaseHelper();
//
//    @State public static final String ROOT = "Root";
//    @State(ROOT) public static final String NOT_CONNECTED = "NotConnected"; //未登录（但是Socket已经建立）
//    @State(ROOT) public static final String IDLE = "Idle";
//    @State(ROOT) public static final String SEATED = "Seated";
//    @State(ROOT) public static final String READY = "Ready";
//    @State(ROOT) public static final String PLAY = "Play"; // Play包含Player的Play及Wait状态
////    @State(ROOT) public static final String WAIT = "Wait";
//    @State(ROOT) public static final String FINISH = "Finish";
//
//    /**
//     * 内部类，用于记录每个session的数据（相比IoSession类型安全）
//     */
//    static class WServerContext extends AbstractStateContext {
//        public String user;
//        public int uid;
//    }
//
//    /**
//     * Socket创建
//     */
//    @IoHandlerTransition(on = SESSION_CREATED, in = NOT_CONNECTED)
//    public void sessionCreated(IoSession session) {
//    }
//
//    /**
//     *  Socket开启
//     */
//    @IoHandlerTransition(on = SESSION_OPENED, in = NOT_CONNECTED)
//    public void sessionOpened(IoSession session) {
//        LOGGER.info("[SESS {}] Session Opened", session.getId());
//    }
//
//    /**
//     * Socket关闭
//     */
//    @IoHandlerTransition(on = SESSION_CLOSED, in = ROOT)
//    public void sessionClosed(WServerContext ctx, IoSession session) {
//        //为防止强制退出，logout一次
//        logout(ctx, session, new LogoutCommand());
//        LOGGER.info("[SESS {}] Session Closed", session.getId());
//    }
//
//    /**
//     * 客户端主动请求断开socket连接
//     * 需要先执行logout
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED)
//    public void disconnect(WServerContext ctx, IoSession session, DisconnectCommand cmd) {
//        logout(ctx, session, new LogoutCommand());
//        session.close(true); // sessionClosed()
//        LOGGER.info("[SESS {}] Disconnect", session.getId());
//    }
//
//    /**
//     * 登陆
//     * 需已经建立Socket
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED, next = IDLE)
//    public void login(WServerContext ctx, IoSession session, LoginCommand cmd) {
//        // 数据库读取并更新
//        String ip = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
//        Player player = dbHelper_.login(cmd.getUser(), cmd.getPassword(), ip);
//        if(player != null) {
//            ctx.user = player.getUserName();
//            ctx.uid = player.getId();
////            session.setAttribute("user", ctx.user);
//            synchronized(sessionMap_) {
//                sessionMap_.put(ctx.uid, session);
//            }
//            synchronized(playerMap_) {
//                playerMap_.put(ctx.uid, player);
//            }
//            synchronized(playerBasicMap_) {
//                playerBasicMap_.put(ctx.uid, player.getBasicPlayer());
//            }
//
//            // 将对象返回用户
//            AbstractCommand cmdRes = new LoginResponseCommand(player);
//            sendCommand(session, cmdRes);
//
//            // 客户端刷新
//            AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
//            broadcastCommand(cmdRefresh);
//
//            LOGGER.info("[SESS {}] Login success: {}", session.getId(), cmd.getUser());
//        } else {
//            AbstractCommand cmdRes = new LoginResponseCommand("Login error.");
//            sendCommand(session, cmdRes);
//            LOGGER.info("[SESS {}] Login fail: {}", session.getId(), cmd.getUser());
//            StateControl.breakAndContinue(); // 保持NOT_CONNECTED状态
//        }
//    }
//
//    /**
//     * 登出
//     * 登出后不断开Socket
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = NOT_CONNECTED)
//    public void logout(WServerContext ctx, IoSession session, LogoutCommand cmd) {
//        Player player = playerMap_.get(ctx.uid);
//        Player playerBasic = playerBasicMap_.get(ctx.uid);
//        if(player != null) {
//            if(player.getTableId() != Table.UNSEATED) {
//                hall_.getTable(player.getTableId()).unseat(player, player.getTablePosition(), playerBasic);
//            }
//            synchronized(sessionMap_) {
//                sessionMap_.remove(ctx.uid);
//            }
//            synchronized(playerMap_) {
//                playerMap_.remove(ctx.uid);
//            }
//            synchronized(playerBasicMap_) {
//                playerBasicMap_.remove(ctx.uid);
//            }
//
//            //客户端刷新
//            AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
//            broadcastCommand(cmdRefresh);
//
//            LOGGER.info("[SESS {}] Logout", session.getId());
//        }
//    }
//
//    /**
//     * 入座
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = SEATED)
//    public void seat(WServerContext ctx, IoSession session, SeatCommand cmd) {
//        boolean ret = hall_.getTable(cmd.getTableId())
//                .seat(playerMap_.get(ctx.uid), cmd.getTablePosition_(), playerBasicMap_.get(ctx.uid));
//        if(ret) {
//            setPlayerState(ctx.uid, Player.STATE.Seated);
//
//            // 告知用户入座
//            AbstractCommand cmdRes = new SeatResponseCommand(SeatResponseCommand.SUCCESS, "");
//            sendCommand(session, cmdRes);
//
//            // 推送
//            AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
//            broadcastCommand(cmdRefresh);
//
//            LOGGER.info("[SESS {}] seated", session.getId());
//        } else {
//            AbstractCommand cmdRes = new SeatResponseCommand(SeatResponseCommand.ERROR, "");
//            sendCommand(session, cmdRes);
//            StateControl.breakAndContinue(); // 保持IDLE状态
//        }
//    }
//
//    /**
//     * 空闲（进入/返回大厅）
//     * 只允许seated和ready之后离开座位进入空闲状态
//     * 不允许换座，即从seated到seated与从ready到seated
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED, next = IDLE),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY, next = IDLE)
//    })
//    public void unseat(WServerContext ctx, IoSession session, UnseatCommand cmd) {
//        Player player = playerMap_.get(ctx.uid);
//        Player playerBasic = playerBasicMap_.get(ctx.uid);
//        boolean ret = hall_.getTable(player.getTableId())
//                .unseat(player, player.getTablePosition(), playerBasic);
//        setPlayerState(ctx.uid, Player.STATE.Idle);
//
//        // 推送
//        AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
//        broadcastCommand(cmdRefresh);
//    }
//
//    /**
//     * 准备
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED, next = READY)
//    public void ready(WServerContext ctx, IoSession session, ReadyCommand cmd) {
//        setPlayerState(ctx.uid, Player.STATE.Ready);
//
//        //player是否都已ready
//        Table table = hall_.getTable(playerMap_.get(ctx.uid).getTableId());
//        if(table.getPlayer(0).getState().equals(Player.STATE.Ready) &&
//            table.getPlayer(1).getState().equals(Player.STATE.Ready) &&
//            table.getPlayer(2).getState().equals(Player.STATE.Ready) &&
//            table.getPlayer(3).getState().equals(Player.STATE.Ready)) {
//            Dealer dealer = table.getDealer();
//            dealer.gameInit();
//            dealer.shuffle();
//            GameStartCommand cmdStart = new GameStartCommand();
//            cmdStart.setFirstPlayerUid(dealer.getFirstPlayer().getId());
//            for(int i = 0; i < 4; i++) {
//                setPlayerState(table.getPlayer(i).getId(), Player.STATE.Wait); // Ready->Wait
//                cmdStart.setCards(table.getPlayer(i).getCards());
//                sendCommand(table.getPlayer(i).getId(), cmdStart);
//            }
//            // 设定开局计时器
//            dealer.setTimer(new Timer());
//            dealer.getTimer().schedule(new GameTimer(dealer),
//                    Configuration.CLIENT_START_DELAY, Configuration.PLAY_DURATION_SERVER);
//        }
//
//        AbstractCommand cmdRefresh = new RefreshPlayerListCommand(new ArrayList<Player>(playerBasicMap_.values()));
//        broadcastCommand(cmdRefresh);
//    }
//
//    /**
//     * 游戏进行（出牌）
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY, next = PLAY), // 第一次出牌后变成PLAY
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY)
//    })
//    public void play(WServerContext ctx, IoSession session, PlayCommand cmd) {
//        Player player = playerMap_.get(ctx.uid);
//        Dealer dealer = hall_.getTable(player.getTableId()).getDealer();
//        if(ctx.uid == dealer.getPlayingPlayer().getId()) {
//            play(dealer, cmd.getCardType());
//            if(player.getCards().size() == 0) { // 牌出完
//                setPlayerState(ctx.uid, Player.STATE.Finish);
//                if(dealer.playerFinish(player)) {
//                    // 全部出完则结束游戏
//                    dealer.gameFinish();
//                    AbstractCommand cmdGameOver = new GameOverCommand();
//                    for(int i = 0; i < 4; i++) {
//                        sendCommand(dealer.getTable().getPlayer(i).getId(), cmdGameOver);
//                        setPlayerState(dealer.getTable().getPlayer(i).getId(), Player.STATE.Idle);
//
//                    }
//                }
//            }
//        } else {
//            // timeout后接收到到Play指令被忽略
//            // AbstractCommand cmd = new PlayResponseCommand(cmd.getCardType());
//        }
//    }
//
//    public void play(Dealer dealer, CardType cardType) {
//        dealer.getTimer().cancel(); // 关闭计时器
//
//        setPlayerState(dealer.getPlayingPlayer().getId(), Player.STATE.Wait); // 当前用户状态切换为Wait
//        dealer.playAndMoveNext(dealer.getPlayingPlayer(), cardType); // 切换到下个用户（下个用户状态由计时器设定）
//
//        AbstractCommand cmdNext = new NextPlayCommand(null, dealer.getPlayingPlayer().getId());
//        Table table = dealer.getTable();
//        for(int i = 0; i < 4; i++) {
//            sendCommand(table.getPlayer(i).getId(), cmdNext);
//        }
//
//        // 重置计时器
//        dealer.setTimer(new Timer());
//        dealer.getTimer().schedule(new GameTimer(dealer), 0, Configuration.PLAY_DURATION_SERVER);
//    }
//
//    /**
//     * 每桌对于入座用户聊天信息的群发
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY),
////        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = WAIT),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = FINISH)
//    })
//    public void textMessage(WServerContext ctx, IoSession session, TextCommand cmd) {
//        //过滤内容
//        cmd.doFilter();
//        //群发同桌玩家
//        int tableId = playerMap_.get(ctx.uid).getTableId();
//        for(int i = 0; i < 4; i++) {
//            int playerId = hall_.getTable(tableId).getPlayer(i).getId();
//            sendCommand(playerId, cmd);
//        }
//    }
//
//    /**
//     * Unhandled message
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = ROOT, weight = 100)
//    public void messageReceived(Event event, StateContext ctx, IoSession session, AbstractCommand cmd) {
//        LOGGER.warn("[SESS {}] Message Received: {}", session.getId(), cmd.getName() + ": " + ctx.getCurrentState().getId().toLowerCase());
//    }
//
//    /**
//     * 消息发送后回调，不做处理
//     */
//    @IoHandlerTransition(on = MESSAGE_SENT, in = ROOT, weight = 100)
//    public void messageSent() {
//    }
//
//    /**
//     * Exception
//     * @param session
//     * @param e
//     */
//    @IoHandlerTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 100)
//    public void exceptionCaught(IoSession session, Exception e) {
//        LOGGER.warn("[SESS {}] Exception Caught: {}", session.getId(), e);
//        session.close(true);
//    }
//
//    /**
//     * Unhandled event
//     */
//    @IoHandlerTransition(in = ROOT, weight = 1000)
//    public void unhandledEvent(Event e, IoSession session) {
////        if(e.getId().equals("inputClosed")) {
////            session.close(true);
////        }
//        LOGGER.warn("[SESS {}] Unhandled event: {}", session.getId(), e);
//    }
//
//    /**
//     * 对指定uid的player发送指令
//     * @param uid
//     * @param cmd
//     */
//    private void sendCommand(int uid, AbstractCommand cmd) {
//        sendCommand(sessionMap_.get(uid), cmd);
//    }
//
//    /**
//     * 对指定session发送指令
//     * @param session
//     * @param cmd
//     */
//    private void sendCommand(IoSession session, AbstractCommand cmd) {
//        if(session.isConnected())
//            session.write(cmd);
//    }
//
//    /**
//     * 向所有player广播
//     * @param cmd
//     */
//    private void broadcastCommand(AbstractCommand cmd) {
//        synchronized(sessionMap_) {
//            for(IoSession sess : sessionMap_.values()) {
//                if(sess.isConnected()) {
//                    sess.write(cmd);
//                }
//            }
//        }
//    }
//
//    /**
//     * 设置玩家状态
//     */
//    private void setPlayerState(int uid, Player.STATE state) {
//        synchronized (playerMap_) {
//            Player player = playerMap_.get(uid);
//            player.setState(state);
//        }
//        synchronized (playerBasicMap_) {
//            Player playerBasic = playerBasicMap_.get(uid);
//            playerBasic.setState(state);
//        }
//    }
//
//    class GameTimer extends TimerTask {
//        private Dealer dealer_;
//        boolean timeout = false;
//
//        public GameTimer(Dealer dealer) {
//            dealer_ = dealer;
//        }
//
//        @Override
//        public void run() {
//            if(!timeout) { // 第一次执行
//                setPlayerState(dealer_.getFirstPlayer().getId(), Player.STATE.Play);
//                timeout = true;
//            } else { // 第二次则timeout（不出牌）
//                play(dealer_, null);
//            }
//        }
//    }
//}
