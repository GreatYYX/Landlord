package com.watch0ut.landlord.client.view;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * 消息面板，包括时间、玩家和消息内容
 * Created by Jack on 16/2/21.
 */
public class MessagePane extends VBox {

    private Label timeLabel;
    private Label playerLabel;
    private Label messageLabel;

    public MessagePane(String time, String player, String message) {
        setMinWidth(224);
        setMaxWidth(224);

        timeLabel = new Label(time);
        timeLabel.setFont(new Font("System", 10));

        playerLabel = new Label(player);
        playerLabel.setMinWidth(56);
        playerLabel.setMaxWidth(56);
        playerLabel.setWrapText(true);

        messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(176);

        getChildren().add(timeLabel);

        HBox hBox = new HBox(8, playerLabel, messageLabel);
        getChildren().add(hBox);
    }
}
