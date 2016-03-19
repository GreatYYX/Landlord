package com.watch0ut.landlord.client.service;

import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.LoginResponseCommand;
import com.watch0ut.landlord.command.concrete.RefreshPlayerListCommand;
import com.watch0ut.landlord.command.concrete.SeatResponseCommand;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jack on 16/3/9.
 */
public class WClientHandler extends IoHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WClientHandler.class);

    private SignInController signInController;
    private HallController hallController;

    public void setSignInController(SignInController signInController) {
        this.signInController = signInController;
    }

    public void setHallController(HallController hallController) {
        this.hallController = hallController;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        AbstractCommand cmd = (AbstractCommand)message;
        String name = cmd.getName();
        if(name.equalsIgnoreCase("LoginResponse")) {
            if (signInController == null)
                return;
            LoginResponseCommand command = (LoginResponseCommand) cmd;
            if (command.getStateCode() == LoginResponseCommand.SUCCESS) {
                signInController.onLoginSucceeded();
                hallController.updatePlayer(command.getPlayer());
            } else {
                signInController.onLoginFailed(command.getMessage());
            }
        } else if (name.equalsIgnoreCase("RefreshPlayerList")) {
            RefreshPlayerListCommand command = (RefreshPlayerListCommand) cmd;
            hallController.updatePlayerList(command.getPlayerList());
        } else if (name.equalsIgnoreCase("SeatResponse")) {
            if (hallController == null)
                return;
            SeatResponseCommand command = (SeatResponseCommand) cmd;
            if (command.getStateCode() == SeatResponseCommand.SUCCESS)
                hallController.onSeatSucceed();
            else {
                LOGGER.info("Seat error message: {}", command.getMessage());
            }
        } else {
            LOGGER.warn("Command router missing: {} ", cmd.getClass());
        }
    }
}
