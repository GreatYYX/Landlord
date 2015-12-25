package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 12/25/15.
 */
public class LogoutCommand extends AbstractCommand {
    public LogoutCommand() {

    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
