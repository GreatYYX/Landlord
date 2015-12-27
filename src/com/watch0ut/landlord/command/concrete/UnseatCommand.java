package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 12/27/15.
 *
 * server<-client
 * 离开座位
 *
 */
public class UnseatCommand extends AbstractCommand {

    public UnseatCommand() {

    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
