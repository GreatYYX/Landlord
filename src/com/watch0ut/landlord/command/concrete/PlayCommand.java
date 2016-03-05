package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Card;
import com.watch0ut.landlord.object.cardtype.CardType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by GreatYYX on 2/19/16.
 *
 * server<-client
 * 客户端出牌
 * 客户端需要等到相同seqId的PlayResponse回复后才能认为出牌成功，否则此轮不出
 *
 */
public class PlayCommand extends AbstractCommand {
    private CardType cardType_;
    private int seqId_;

    public PlayCommand() {

    }

    public PlayCommand(int seqId, CardType cardType) {
        seqId_ = seqId;
        cardType_ = cardType;
    }

    public CardType getCardType() {
        return cardType_;
    }

    public int getSeqId() {
        return seqId_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(seqId_);
        oos.writeObject(cardType_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        seqId_ = ois.readInt();
        cardType_ = (CardType)ois.readObject();
    }
}
