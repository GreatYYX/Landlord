package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.controller.SelfHandController;
import com.watch0ut.landlord.object.Card;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试自己的手牌
 *
 * Created by Jack on 16/2/28.
 */
public class TestSelfHandPane {

    private final static int CARD_NUMBER = 13;

    public static Parent initialize() {
        VBox parent = new VBox(10);
        parent.setAlignment(Pos.CENTER);
        parent.setPrefSize(500, 250);

        List<Card> cards = new ArrayList<>(CARD_NUMBER);
        for (int i = 0; i < CARD_NUMBER; i++) {
            int point = (int)(Math.random() * CARD_NUMBER);
            int suit = (int)(Math.random() * 4);
            cards.add(new Card(point, suit));
        }

        SelfHandPane handPane = new SelfHandPane(cards);
        SelfHandController selfHandController = new SelfHandController(handPane);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        Button dealButton = new Button("Deal");
        Button playButton = new Button("Play");
        playButton.setDisable(true);
        Button passButton = new Button("Pass");
        passButton.setDisable(true);

        dealButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dealButton.setDisable(true);
                selfHandController.deal();
                playButton.setDisable(false);
                passButton.setDisable(false);
            }
        });

        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selfHandController.play();
            }
        });

        passButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selfHandController.pass();
            }
        });

        hBox.getChildren().addAll(dealButton, playButton, passButton);
        parent.getChildren().addAll(handPane, hBox);

        return parent;
    }
}
