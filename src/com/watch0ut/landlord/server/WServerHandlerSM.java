package com.watch0ut.landlord.server;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.LoginCommand;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.command.concrete.RefreshPlayerListCommand;
import com.watch0ut.landlord.command.concrete.TextCommand;
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

    private final Set<IoSession> sessions_ = Collections.synchronizedSet(new HashSet<IoSession>());
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
     * 内部类，相比IoSession类型安全
     */
    static class WServerContext extends AbstractStateContext {
        public String user;
    }

    @IoHandlerTransition(on = SESSION_OPENED, in = NOT_CONNECTED)
    public void sessionOpened(IoSession session) {
    }

    @IoHandlerTransition(on = SESSION_CREATED, in = NOT_CONNECTED)
    public void sessionCreated(IoSession session) {
    }

    @IoHandlerTransition(on = SESSION_CLOSED, in = ROOT)
    public void sessionClosed(WServerContext ctx, IoSession session) {
        synchronized(sessions_) {
            sessions_.remove(session);
        }
        session.close(true);

        LOGGER.info("Session Closed");
    }

    /**
     * 登陆
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = NOT_CONNECTED, next = IDLE)
    public void login(WServerContext ctx, IoSession session, LoginCommand cmd) {
        //用户登录
        ctx.user = cmd.getUser();
        //....db operation...
        int uid = 0;
        session.setAttribute("user", ctx.user);
        synchronized(sessions_) {
            sessions_.add(session);
        }
        Player player = new Player(uid);
        playerMap_.put(uid, player);
        playerBasicMap_.put(uid, player.getBasicPlayer());

        //客户端刷新
        AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
        broadcastCommand(cmdRefresh);

        LOGGER.info("Login: {}", ctx.user);
    }

    /**
     * 登出
     * 登出后直接注销session断开socket
     * 因此重新登录需要先建立socket
     * @param ctx
     * @param session
     * @param cmd
     */
    @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = NOT_CONNECTED)
    public void logout(WServerContext ctx, IoSession session, LogoutCommand cmd) {
        synchronized(sessions_) {
            sessions_.remove(session);
        }

        //客户端刷新
        AbstractCommand cmdRefresh = new RefreshPlayerListCommand((List<Player>)playerBasicMap_.values());
        broadcastCommand(cmdRefresh);

        LOGGER.info("Logout: {}", ctx.user);
        session.close(true);
    }

//    /**
//     * 入座
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = IDLE, next = SEATED),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = FINISH, next = SEATED)
//    })
//    public void seat(WServerContext ctx, IoSession session, SeatCommand cmd) {
//    }
//
//    /**
//     * 空闲（进入/返回大厅）
//     */
//    @IoHandlerTransitions({
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED, next = IDLE),
//        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY, next = IDLE)
//    })
//    public void idle(WServerContext ctx, IoSession session, IdleCommand cmd) {
//    }
//
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
     * 对于入座用户聊天信息的群发
     */
    @IoHandlerTransitions({
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = SEATED),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = READY),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = PLAY),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = WAIT),
        @IoHandlerTransition(on = MESSAGE_RECEIVED, in = FINISH)
    })
    public void textMessage(WServerContext ctx, IoSession sess, TextCommand cmd) {
        cmd.doFilter();
        broadcastCommand(cmd);
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
     * 对指定player发送指令
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
        synchronized(sessions_) {
            for(IoSession sess : sessions_) {
                if(sess.isConnected()) {
                    sess.write(cmd);
                }
            }
        }
    }
}
