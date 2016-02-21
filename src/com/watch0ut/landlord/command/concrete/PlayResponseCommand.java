package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 2/21/16.
 */
public class PlayResponseCommand extends AbstractCommand {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private String seqId_;
    private int stateCode_; //状态码

    public PlayResponseCommand() {

    }

    public PlayResponseCommand(String seqId, int stateCode) {
        seqId_ = seqId;
        stateCode_ = stateCode;
    }

    public String getSeqId() {
        return seqId_;
    }

    public int getStateCode() {
        return stateCode_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
