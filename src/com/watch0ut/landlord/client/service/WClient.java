package com.watch0ut.landlord.client.service;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.DisconnectCommand;
import com.watch0ut.landlord.command.concrete.LoginResponseCommand;
import com.watch0ut.landlord.command.concrete.RefreshPlayerListCommand;
import com.watch0ut.landlord.command.concrete.SeatResponseCommand;
import com.watch0ut.landlord.protocol.WProtocolFactory;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 客户端，主要负责与服务器通信
 *
 * Created by GreatYYX on 12/24/15.
 */
public class WClient {

    private static WClient instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(WClient.class);
    private IoConnector connector_;
    private IoSession session_;

    public static WClient getInstance() {
        if (instance == null) {
            synchronized (WClient.class) {
                if (instance == null) {
                    instance = new WClient();
                }
            }
        }
        return instance;
    }

    private WClient() {
        connector_ = new NioSocketConnector();
        connector_.setConnectTimeoutMillis(Configuration.CLIENT_CONNECT_TIMEOUT);
        connector_.getFilterChain().addLast("logger", new LoggingFilter());
        connector_.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new WProtocolFactory()));
    }

    public void setHandler(IoHandler ioHandler) {
        connector_.setHandler(ioHandler);
    }

    public boolean isConnected() {
        return session_ != null && session_.isConnected();
    }

    public void connect() {
        ConnectFuture connectFuture = connector_.connect(new InetSocketAddress(Configuration.SEVER_HOST, Configuration.SEVER_PORT));
        connectFuture.awaitUninterruptibly(Configuration.CLIENT_CONNECT_TIMEOUT);
        try {
            session_ = connectFuture.getSession();
        } catch (RuntimeIoException e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    public void disconnect() {
        if (session_ != null) {
            sendCommand(new DisconnectCommand()); //退出前必须发送该指令让服务器主动关闭连接
            session_.getCloseFuture().awaitUninterruptibly();
//            session_.close(false).awaitUninterruptibly(Configuration.CLIENT_CONNECT_TIMEOUT);
            connector_.dispose();
            session_ = null;
        }
    }

    public IoSession getSession() {
        return session_;
    }

    public void sendCommand(AbstractCommand cmd) {
        if (session_ != null) {
            session_.write(cmd);
        }
    }
}


