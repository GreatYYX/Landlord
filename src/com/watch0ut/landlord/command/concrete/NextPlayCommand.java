package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Card;
import com.watch0ut.landlord.object.cardtype.CardType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by GreatYYX on 2/19/16.
 *
 * server->client
 * 客户端超时，强制切换到下个用户出牌
 *
 */
public class NextPlayCommand extends AbstractCommand {
    private int seqId_;
    private CardType currCardType_; // 当前玩家出的牌（可能是空）
    private int nextUid_;

    public NextPlayCommand() {

    }

    public NextPlayCommand(CardType currCardType, int nextUid, int seqId) {
        currCardType_ = currCardType;
        nextUid_ = nextUid;
        seqId_ = seqId;
    }

    public int getSeqId() {
        return seqId_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(seqId_);
        oos.writeInt(nextUid_);
        oos.writeObject(currCardType_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        seqId_ = ois.readInt();
        nextUid_ = ois.readInt();
        currCardType_ = (CardType)ois.readObject();
    }
}
