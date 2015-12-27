package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by GreatYYX on 14-10-21.
 *
 * server<-client
 * 入座
 *
 */
public class SeatCommand extends AbstractCommand {

    private int tableId_;
    private int tablePosition_;

    public SeatCommand() {

    }

    public int getTableId() {
        return tableId_;
    }

    public int getTablePosition_() {
        return tablePosition_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(tableId_);
        oos.writeObject(tablePosition_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        tableId_ = (Integer)ois.readObject();
        tablePosition_ = (Integer)ois.readObject();
    }
}
