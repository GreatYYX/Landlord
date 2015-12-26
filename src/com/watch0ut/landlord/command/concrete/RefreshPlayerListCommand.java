package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by GreatYYX on 12/26/15.
 *
 * server->client
 * 更新player列表，需在player状态更新后推送所有客户端
 *
 */
public class RefreshPlayerListCommand extends AbstractCommand {

    private List<Player> playerList_;

    public RefreshPlayerListCommand() {

    }

    public RefreshPlayerListCommand(List<Player> players) {
        playerList_ = players;
    }

    public List<Player> getPlayerList() {
        return playerList_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(playerList_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        playerList_ = (List<Player>)ois.readObject();
    }
}
