package com.watch0ut.landlord.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.protocol.WProtocolFactory;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.IoHandlerTransition;
import org.apache.mina.statemachine.context.IoSessionStateContextLookup;
import org.apache.mina.statemachine.context.StateContext;
import org.apache.mina.statemachine.context.StateContextFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GreatYYX on 12/24/15.
 */
public class WServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WServer.class);
    IoAcceptor acceptor_;

    public WServer() {
        acceptor_ =  new NioSocketAcceptor();
        acceptor_.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor_.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new WProtocolFactory()));
//        acceptor_.setHandler(createWServerHandlerSM());
        acceptor_.setHandler(new WServerHandler());
    }

//    private static IoHandler createWServerHandlerSM() {
//        StateMachine sm = StateMachineFactory.getInstance(
//                IoHandlerTransition.class).create(WServerHandlerSM.NOT_CONNECTED,
//                new WServerHandlerSM());
//
//        return new StateMachineProxyBuilder().setStateContextLookup(
//                new IoSessionStateContextLookup(new StateContextFactory() {
//                    public StateContext create() {
//                        return new WServerHandlerSM.WServerContext();
//                    }
//                })).create(IoHandler.class, sm);
//    }

    public void start() throws IOException {
        acceptor_.bind(new InetSocketAddress(Configuration.SEVER_PORT));
    }

    public static void main(String[] args) {
        try {
            new WServer().start();
            LOGGER.info("Server started.");
        } catch (IOException e) {
            LOGGER.error("Can't start server!", e);
        }
    }
}
