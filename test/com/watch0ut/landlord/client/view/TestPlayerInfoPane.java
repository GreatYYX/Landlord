package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试玩家个人信息面板
 * Created by Jack on 16/2/21.
 */
public class TestPlayerInfoPane {

    public static Parent initialize() {
        VBox parent = new VBox();
        parent.setAlignment(Pos.CENTER);

        final PlayerInfoPane playerInfoPane = new PlayerInfoPane("J.png", "Jack", 100);
        parent.getChildren().add(playerInfoPane);

        Button scoreButton = new Button("Update Score");
        scoreButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int score = (int)(Math.random() * 100);
                playerInfoPane.updateScore(score);
            }
        });
        parent.getChildren().add(scoreButton);

        return parent;
    }
}
