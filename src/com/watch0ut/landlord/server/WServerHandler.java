package com.watch0ut.landlord.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.*;

/**
 * Created by GreatYYX on 12/25/15.
 */
public class WServerHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WServerHandler.class);
    private final Set<IoSession> sessions_ = Collections.synchronizedSet(new HashSet<IoSession>());
//    private final Set<String> users_ = Collections.synchronizedSet(new HashSet<String>());

    public static final String NOT_CONNECTED = "NotConnected"; //未登录（但是SOCKET已经建立）
    public static final String IDLE = "Idle";
    public static final String SEATED = "Seated";
    public static final String READY = "Ready";
    public static final String PLAY = "Play";
    public static final String WAIT = "Wait";
    public static final String FINISH = "Finish";

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractCommand cmd = (AbstractCommand)message;
        String name = cmd.getName();
        if(name.equalsIgnoreCase("login")) {
            login(session, (LoginCommand)message);
        } else if(name.equalsIgnoreCase("logout")) {
            logout(session, (LogoutCommand)message);
        } else {
            LOGGER.error("Command router missing: {}", cmd.getClass());
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOGGER.warn("Unexpected error: {}", cause);
        session.close(true);
    }

    public void login(IoSession session, LoginCommand cmd) {
        //user login
        session.setAttribute("user", cmd.getUser());
        synchronized(sessions_) {
            sessions_.add(session);
        }

        //refresh user list

        LOGGER.info("Login: {}", cmd.getName());
    }

    public void logout(IoSession session, LogoutCommand cmd) {
        synchronized(sessions_) {
            sessions_.remove(session);
        }

        LOGGER.info("Logout: {}", session.getAttribute("user"));
        session.close(true);
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
