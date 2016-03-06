package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.command.AbstractCommand;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by GreatYYX on 12/24/15.
 */
public class WClientHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WClientHandler.class);

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractCommand cmd = (AbstractCommand)message;
        String name = cmd.getName();
        if(name.equalsIgnoreCase("LoginResponseCommand")) {

        } else {
            LOGGER.error("Command router missing: {}", cmd.getClass());
        }
    }

}
