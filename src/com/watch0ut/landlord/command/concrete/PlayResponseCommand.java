package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Card;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by GreatYYX on 2/21/16.
 */
public class PlayResponseCommand extends AbstractCommand {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private int seqId_;
    private int stateCode_; //状态码

    public PlayResponseCommand() {

    }

    public PlayResponseCommand(int seqId, int stateCode) {
        seqId_ = seqId;
        stateCode_ = stateCode;
    }

    public int getSeqId() {
        return seqId_;
    }

    public int getStateCode() {
        return stateCode_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(seqId_);
        oos.writeInt(stateCode_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        seqId_ = ois.readInt();
        stateCode_ = ois.readInt();
    }
}
