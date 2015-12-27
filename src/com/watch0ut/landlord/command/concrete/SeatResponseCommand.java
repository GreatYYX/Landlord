package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by GreatYYX on 12/27/15.
 *
 * server->client
 * 玩家入座回复
 *
 */
public class SeatResponseCommand extends AbstractCommand {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private int stateCode_; //状态码
    private String msg_; //失败原因
    private int tableId_;
    private int tablePosition_;

    public SeatResponseCommand() {

    }

    public SeatResponseCommand(int stateCode, String msg) {
        stateCode_ = stateCode;
        msg_ = msg;
    }

    public int getTableId() {
        return tableId_;
    }

    public int getTablePosition_() {
        return tablePosition_;
    }

    public int getStateCode() {
        return stateCode_;
    }

    public String getMessage() {
        return msg_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(stateCode_);
        if(stateCode_ == SUCCESS) {
            oos.writeObject(tableId_);
            oos.writeObject(tablePosition_);
        } else {
            oos.writeObject(msg_);
        }
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        stateCode_ = (Integer)ois.readObject();
        if(stateCode_ == SUCCESS) {
            tableId_ = (Integer)ois.readObject();
            tablePosition_ = (Integer)ois.readObject();
        } else {
            msg_ = (String)ois.readObject();
        }
    }
}
