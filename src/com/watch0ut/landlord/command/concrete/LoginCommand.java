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
 * server<-client
 * 玩家登陆
 *
 * Protocol: user|pwd
 */
public class LoginCommand extends AbstractCommand {

    private String user_;
    private String pwd_;

    public LoginCommand() {
    }

    public LoginCommand(String user, String pwd) {
        user_ = user;
        pwd_ = pwd;
    }

    public String getUser() {
        return user_;
    }

    public String getPassword() {
        return pwd_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user_);
        oos.writeObject(pwd_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        user_ = (String)ois.readObject();
        pwd_ = (String)ois.readObject();
    }
}
