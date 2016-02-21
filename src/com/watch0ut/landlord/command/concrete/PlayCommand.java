package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.cardtype.CardType;

/**
 * Created by GreatYYX on 2/19/16.
 *
 * server<-client
 * 客户端出牌
 *
 */
public class PlayCommand extends AbstractCommand {
    private CardType cardType_;

    public PlayCommand(CardType cardType) {

    }

    public CardType getCardType() {
        return cardType_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
