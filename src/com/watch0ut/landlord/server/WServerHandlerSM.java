package com.watch0ut.landlord.server;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.*;
import com.watch0ut.landlord.object.Hall;
import com.watch0ut.landlord.object.Player;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.statemachine.annotation.IoHandlerTransition;
import org.apache.mina.statemachine.annotation.IoHandlerTransitions;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.context.AbstractStateContext;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.apache.mina.statemachine.event.IoHandlerEvents.*;

/**
 * Created by GreatYYX on 14-10-20.
 */
public class WServerHandlerSM extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WServerHandlerSM.class);

    private final Map<Integer, IoSession> sessionMap_ = Collections.synchronizedMap(new HashMap<Integer, IoSession>());
//    private final Set<String> users_ = Collections.synchronizedSet(new HashSet<String>());
    private final Hall hall_ = new Hall();
    private final Map<Integer, Player> playerMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>());
    private final Map<Integer, Player> playerBasicMap_ = Collections.synchronizedMap(new HashMap<Integer, Player>());

    @State public static final String ROOT = "Root";
    @State(ROOT) public static final String NOT_CONNECTED = "NotConnected"; //未登录（但是SOCKET已经建立）
    @State(ROOT) public static final String IDLE = "Idle";
    @State(ROOT) public static final String SEATED = "Seated";
    @State(ROOT) public static final String READY = "Ready";
    @State(ROOT) public static final String PLAY = "Play";
    @State(ROOT) public static final String WAIT = "Wait";
    @State(ROOT) public static final String FINISH = "Finish";

    /**
     * 内部类，用于记录每个session的数据（相比IoSession类型安全）
     */
    static class WServerContext extends AbstractStateContext {
        public String user;
        public int uid;
    }

    @IoHandlerTransition(on = SESSION_OPENED, in = NOT_CONNECTED)
    public void sessionOpened(IoSession session) {
    }

    @IoHandlerTransition(on = SESSION_CREATED, in = NOT_CONNECTED)
    public void sessionCreated(IoSession session) {
    }

    @IoHandlerTransition(on = SESSION_CLOSED, in = ROOT)
    public void sessionClosed(WServerContext ctx, IoSession session) {
        //为防止强制退出，检测用户是否已经从列表移除
        Player player = playerMap_.get(ctx.uid);
        Player playerBasic = playerBasicMap_.get(ctx.uid);
        if(player != null) {
            hall_.getTable(player.getTableId()).unseat(player, player.getTablePosition(), playerBasic);
            synchronized(sessionMap_) {
                sessionMap_.remove(ctx.uid);
            }
            synchronized(playerMap_) {
                playerMap_.remove(ctx.uid);
            }
            synchronized(playerBasicMap_) {
                playerBasicMap_.remove(ctx.uid);
            }
        }
        session.close(true);

        LOGGER.info("Session Closed");
    }

    /**
     * 客户端请求断开socket连接
     * 需要先执行logout
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED)
    public void disconnect(WServerContext ctx, IoSession session, DisconnectCommand cmd) {
        session.close(true);

        LOGGER.info("Disconnect: {}", ctx.user);
    }

    /**
     * 登陆
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED, next = IDLE)
    public void login(WServerContext ctx, IoSession session, LoginCommand cmd) {
        //数据库读取
//        if(DB RESULT SUCCESS) {
            int uid = 0;
            ctx.user = cmd.getUser();
            ctx.uid = uid;
//            session.setAttribute("user", ctx.user);
            synchronized(sessionMap_) {
                sessionMap_.put(ctx.uid, session);
            }
            Player player = new Player(uid);
            synchronized(playerMap_) {
                playerMap_.put(uid, player);
            }
            synchronized(playerBasicMap_) {
                playerBasicMap_.put(uid, player.getBasicPlayer());
            }

            //更新数据库

            //将对象返回用户
            AbstractCommand cmdRes = new LoginResponseCommand(player);
            sendCommand(session, cmdRes);

            //客户端刷新
            AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
            broadcastCommand(cmdRefresh);

            LOGGER.info("Login: {}", ctx.user);
//        } else {
//            AbstractCommand cmdRes = new LoginResponseCommand("error password");
//            sendCommand(session, cmdRes);
//            LOGGER.info("Login fail: {}", ctx.user);
//        }
    }

    /**
     * 登出
     * 登出后不断开socket
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = NOT_CONNECTED)
    public void logout(WServerContext ctx, IoSession session, LogoutCommand cmd) {
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
        AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
        broadcastCommand(cmdRefresh);

        LOGGER.info("Logout: {}", ctx.user);
    }

    /**
     * 入座
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = SEATED)
    public void seat(WServerContext ctx, IoSession session, SeatCommand cmd) {
        boolean ret = hall_.getTable(cmd.getTableId())
                .seat(playerMap_.get(ctx.uid), cmd.getTablePosition_(), playerBasicMap_.get(ctx.uid));
        if(ret) {
            //告知用户入座

            //推送
            AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
            broadcastCommand(cmdRefresh);
        } else {
            //
        }
    }

    /**
     * 空闲（进入/返回大厅）
     */
    @IoHandlerTransitions({
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED, next = IDLE),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY, next = IDLE)
    })
    public void unseat(WServerContext ctx, IoSession session, UnseatCommand cmd) {
        Player player = playerMap_.get(ctx.uid);
        Player playerBasic = playerBasicMap_.get(ctx.uid);
        boolean ret = hall_.getTable(player.getTableId())
                .unseat(player, player.getTablePosition(), playerBasic);
    }

//    /**
//     * 准备
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED, next = READY)
//    public void ready(WServerContext ctx, IoSession session, ReadyCommand cmd) {
//    }
//
//    /**
//     * 游戏进行
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY, next = PLAY),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = WAIT, next = PLAY)
//    })
//    public void play(WServerContext ctx, IoSession session, PlayCommand cmd) {
//    }
//
//    /**
//     * 游戏进行中等待
//     */
//    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY, next = WAIT)
//    public void wait(WServerContext ctx, IoSession session, WaitCommand cmd) {
//    }
//
//    /**
//     * 结束游戏
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY, next = FINISH),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = WAIT, next = FINISH)
//    })
//    public void finish(WServerContext ctx, IoSession session, FinishCommand cmd) {
//    }

    /**
     * 每桌对于入座用户聊天信息的群发
     */
    @IoHandlerTransitions({
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = WAIT),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = FINISH)
    })
    public void textMessage(WServerContext ctx, IoSession session, TextCommand cmd) {
        //过滤内容
        cmd.doFilter();
        //群发同桌玩家
        int tableId = playerMap_.get(ctx.uid).getTableId();
        for(int i = 0; i < 4; i++) {
            int playerId = hall_.getTable(tableId).getPlayer(i).getId();
            sendCommand(playerId, cmd);
        }
    }

    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = ROOT, weight = 10)
    public void error(Event event, StateContext ctx, IoSession session, AbstractCommand cmd) {
        LOGGER.warn(cmd.getName() + ": " + ctx.getCurrentState().getId().toLowerCase());
    }

    /**
     * Exception
     * @param session
     * @param e
     */
    @IoHandlerTransition(on = EXCEPTION_CAUGHT, in = ROOT, weight = 10)
    public void exceptionCaught(IoSession session, Exception e) {
        LOGGER.warn("Unexpected error.", e);
        session.close(true);
    }

    /**
     * Unhandled Event
     */
    @IoHandlerTransition(in = ROOT, weight = 100)
    public void unhandledEvent(Event e, IoSession session) {
//        if(e.getId().equals("inputClosed")) {
//            session.close(true);
//        }
        LOGGER.warn("Unhandled event: {}", e);
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
}
