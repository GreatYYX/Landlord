package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 聊天面板
 *
 * Created by Jack on 16/2/21.
 */
public class ChatPane extends VBox {

    private VBox messageListBox;
    private TextField messageText;
    private Button sendButton;

    public ChatPane() {
        setMinWidth(240);
        setMaxWidth(240);

        messageListBox = new VBox(8);
        messageListBox.setPadding(new Insets(4, 4, 4, 4));
        messageListBox.setMaxWidth(224);

        ScrollPane scrollPane = new ScrollPane(messageListBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinHeight(368);
        scrollPane.setPannable(true);
        getChildren().add(scrollPane);

        messageText = new TextField();
        messageText.setPrefWidth(188);
        messageText.setPrefHeight(28);

        sendButton = new Button("发送");
        sendButton.setPrefWidth(44);
        sendButton.setPrefHeight(28);

        HBox hBox = new HBox(4, messageText, sendButton);
        hBox.setPadding(new Insets(2, 2, 2, 2));
        getChildren().add(hBox);
    }

    public void setSendButtonHandler(EventHandler<ActionEvent> value) {
        sendButton.setOnAction(value);
    }

    public void setEnterKeyHandler(EventHandler<KeyEvent> value) {
        messageText.setOnKeyPressed(value);
    }

    public void addMessage(String time, String player) {
        String message = messageText.getText().trim();
        if (message.length() > 0) {
            messageListBox.getChildren().add(new MessagePane(time, player, message));
            messageText.setText("");
        }
    }

    public void addMessage(String time, String player, String message) {
        messageListBox.getChildren().add(new MessagePane(time, player, message));
    }

}
