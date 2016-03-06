package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by GreatYYX on 14-10-21.
 *
 * server->client
 * 游戏结束
 */
public class GameOverCommand extends AbstractCommand {
    private boolean forced_ = false; // 强制结束

    public GameOverCommand() {

    }

    public GameOverCommand(boolean forced) {
        forced_ = forced;
    }

    public boolean isForced() {
        return forced_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeBoolean(forced_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        forced_ = ois.readBoolean();
    }
}
