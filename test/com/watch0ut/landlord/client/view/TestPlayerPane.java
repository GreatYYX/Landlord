package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.view.PlayerPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试玩家面板
 *
 * Created by Jack on 16/2/25.
 */
public class TestPlayerPane {

    public static Parent initialize() {
        VBox parent = new VBox(5);
        parent.setAlignment(Pos.CENTER);

        final PlayerPane playerPane = new PlayerPane();
        parent.getChildren().add(playerPane);

        Button readyButton = new Button("Ready");
        readyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerPane.ready();
            }
        });

        Button playButton = new Button("Play");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerPane.play();
            }
        });

        Button leaveButton = new Button("Leave");
        leaveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerPane.leave();
            }
        });

        Button enterButton = new Button("Enter");
        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerPane.enter("Q.png", "Queen");
            }
        });

        Button actionButton = new Button("Show Action");
        actionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                playerPane.updateAction(ActionView.LANDLORD_3);
            }
        });

        parent.getChildren().addAll(enterButton, readyButton, playButton, actionButton, leaveButton);
        return parent;
    }
}
