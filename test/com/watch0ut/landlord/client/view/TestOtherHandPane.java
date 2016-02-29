package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.controller.OtherHandController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 测试其他玩家手牌
 *
 * Created by Jack on 16/2/29.
 */
public class TestOtherHandPane {

    public static Parent initialize() {
        VBox parent = new VBox();
        parent.setAlignment(Pos.CENTER);

        HBox handPanes = new HBox(10);
        handPanes.setAlignment(Pos.CENTER);
        OtherHandPane earlyHand = new OtherHandPane(13, OtherHandPane.VERTICAL);
        OtherHandController earlyHandController = new OtherHandController(earlyHand);
        OtherHandPane relativelyHand = new OtherHandPane(13, OtherHandPane.HORIZONTAL);
        OtherHandController relativelyHandController = new OtherHandController(relativelyHand);
        handPanes.getChildren().addAll(earlyHand, relativelyHand);

        HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.CENTER);
        Button drawButton = new Button("Draw");
        Button playButton = new Button("Play");
        playButton.setDisable(true);
        drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                earlyHandController.draw();
                relativelyHandController.draw();
                playButton.setDisable(false);
                drawButton.setDisable(true);
            }
        });
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int number = (int)(Math.random() * 4 + 1);
                earlyHandController.play(number);
                relativelyHandController.play(number);
            }
        });
        buttons.getChildren().addAll(drawButton, playButton);

        parent.getChildren().addAll(handPanes, buttons);
        return parent;
    }
}
