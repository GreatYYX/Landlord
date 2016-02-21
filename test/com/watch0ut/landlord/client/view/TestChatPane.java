package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.controller.ChatController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试聊天面板，可以发送和接收消息
 * Created by Jack on 16/2/21.
 */
public class TestChatPane {

    public static Parent initialize() {
        VBox parent = new VBox(10);
        parent.setAlignment(Pos.CENTER);

        ChatPane chatPane = new ChatPane();
        final ChatController chatController = new ChatController(chatPane);

        Button receiveMessageButton = new Button("Receive Message");
        receiveMessageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                chatController.addMessage("King", "说得好非师范教");
            }
        });

        parent.getChildren().addAll(chatPane, receiveMessageButton);

        return parent;
    }
}
