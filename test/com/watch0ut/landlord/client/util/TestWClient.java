package com.watch0ut.landlord.client.util;

import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.LoginCommand;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.command.concrete.TextCommand;

/**
 * 测试WClient
 *
 * Created by Jack on 16/3/6.
 */
public class TestWClient {
    public static void main(String args[]) {
        WClient client = WClient.getInstance();
        client.connect();

        AbstractCommand cmd1 = new LoginCommand("badguy", "123456");
        client.sendCommand(cmd1);
        AbstractCommand cmd = new LoginCommand("root@example.com", "123456");
        client.sendCommand(cmd);
        cmd = new TextCommand("yyx", "hello everyone");
        client.sendCommand(cmd);
        cmd = new LogoutCommand();
        client.sendCommand(cmd);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
    }
}
