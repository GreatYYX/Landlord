package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 12/27/15.
 *
 * server<-client
 * 客户端请求服务器主动关闭Socket连接
 * 在客户端退出前调用
 *
 */
public class DisconnectCommand extends AbstractCommand {

    public DisconnectCommand() {

    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
