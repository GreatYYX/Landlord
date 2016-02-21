package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.cardtype.CardType;

/**
 * Created by GreatYYX on 2/19/16.
 *
 * server->client
 * 客户端超时，强制切换到下个用户出牌
 *
 */
public class NextPlayCommand extends AbstractCommand {


    public NextPlayCommand(CardType currType, int nextUid) {

    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
