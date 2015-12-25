package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.command.AbstractCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by GreatYYX on 14-10-21.
 *
 * server->client
 * 对于客户端请求的统一应答指令
 */
public class ResponseCommand extends AbstractCommand {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    private int state_ = SUCCESS;
    private String msg_ = "";

    public ResponseCommand() {

    }

    public ResponseCommand(int state) {
        this(state, "");
    }

    public ResponseCommand(int state, String msg) {
        state_ = state;
        msg_ = msg;
    }

    public int getState() {
        return state_;
    }

    public String getMessage() {
        return msg_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(state_);
        oos.writeObject(msg_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        state_ = ois.readInt();
        msg_ = (String)ois.readObject();
    }
}
