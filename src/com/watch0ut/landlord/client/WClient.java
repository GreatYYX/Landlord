package com.watch0ut.landlord.client;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.DisconnectCommand;
import com.watch0ut.landlord.command.concrete.LoginCommand;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.command.concrete.TextCommand;
import com.watch0ut.landlord.protocol.WProtocolFactory;
import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Created by GreatYYX on 12/24/15.
 */
public class WClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(WClient.class);
    private IoConnector connector_;
    private IoSession session_;

    public WClient() {
        connector_ = new NioSocketConnector();
        connector_.setConnectTimeoutMillis(Configuration.CLIENT_CONNECT_TIMEOUT);
        connector_.getFilterChain().addLast("logger", new LoggingFilter());
        connector_.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new WProtocolFactory()));
        connector_.setHandler(new WClientHandler());
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

    public static void main(String args[]) {
        WClient client = new WClient();
        client.connect();

        AbstractCommand cmd1 = new LoginCommand("badguy", "123456");
        client.sendCommand(cmd1);
        AbstractCommand cmd = new LoginCommand("yyx", "123456");
        client.sendCommand(cmd);
        cmd = new TextCommand("yyx", "hello everyone");
        client.sendCommand(cmd);
        cmd = new LogoutCommand();
        client.sendCommand(cmd);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }
}
