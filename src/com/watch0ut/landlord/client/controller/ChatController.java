package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.util.TimeUtils;
import com.watch0ut.landlord.client.view.ChatPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * 聊天面板控制器
 *
 * Created by Jack on 16/2/21.
 */
public class ChatController {

    private ChatPane chatPane;

    public ChatController(final ChatPane chatPane) {
        this.chatPane = chatPane;
        this.chatPane.setSendButtonHandler(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chatPane.addMessage(TimeUtils.getTime(), "Jack");
            }
        });
        this.chatPane.setEnterKeyHandler(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    chatPane.addMessage(TimeUtils.getTime(), "Jack");
                }
            }
        });
    }

    public void addMessage(String player, String message) {
        if (message.length() > 0) {
            chatPane.addMessage(TimeUtils.getTime(), player, message);
        }
    }

}
