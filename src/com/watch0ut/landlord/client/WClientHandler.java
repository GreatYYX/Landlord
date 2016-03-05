package com.watch0ut.landlord.client;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.LoginCommand;
import com.watch0ut.landlord.command.concrete.LoginResponseCommand;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.command.concrete.RefreshPlayerListCommand;
import com.watch0ut.landlord.object.Player;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by GreatYYX on 12/24/15.
 */
public class WClientHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WClientHandler.class);
    private Player player_ = null;

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractCommand cmd = (AbstractCommand)message;
        String name = cmd.getName();
        if(name.equalsIgnoreCase("LoginResponse")) {
            LoginResponseCommand cmdResp = (LoginResponseCommand)cmd;
            if(cmdResp.getStateCode() == LoginResponseCommand.SUCCESS) {
                player_ = cmdResp.getPlayer();
                System.out.println("Logined as: " + player_.getNickName());
            } else {
                System.out.println("Login failed.");
            }
        } else if(name.equalsIgnoreCase("RefreshPlayerList")) {
            RefreshPlayerListCommand cmdResp = (RefreshPlayerListCommand)cmd;
            List<Player> playerList = cmdResp.getPlayerList();
        } else {
            LOGGER.error("Command router missing: {}", cmd.getClass());
        }
    }

}
