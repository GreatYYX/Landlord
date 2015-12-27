package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by GreatYYX on 12/26/15.
 *
 * server->client
 * 玩家登陆回复
 *
 */
public class LoginResponseCommand extends AbstractCommand {

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;

    private int stateCode_; //状态码
    private String msg_; //失败原因
    private Player player_; //成功则返回Player对象

    public LoginResponseCommand() {

    }

    public LoginResponseCommand(Player player) {
        player_ = player;
        stateCode_ = SUCCESS;
    }

    public LoginResponseCommand(String msg) {
        msg_ = msg;
        stateCode_ = ERROR;
    }

    public int getStateCode() {
        return stateCode_;
    }

    public Player getPlayer() {
        return player_;
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
            oos.writeObject(player_);
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
            player_ = (Player)ois.readObject();
        } else {
            msg_ = (String)ois.readObject();
        }
    }
}
