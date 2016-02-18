package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 14-10-21.
 *
 * server<-client
 * 玩家准备指令
 */
public class ReadyCommand extends AbstractCommand {

    public ReadyCommand() {

    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
