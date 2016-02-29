package com.watch0ut.landlord.client.view;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 其他玩家的手牌
 *
 * Created by Jack on 16/2/28.
 */
public class OtherHandPane extends VBox implements HandPane {

    private final static int DELTA_WIDTH = 16;
    private final static int DELTA_HEIGHT = 16;

    public final static int HORIZONTAL = 0;
    public final static int VERTICAL = 1;

    private int cardNumber;
    private int orientation;

    private AnchorPane handPane;
    private MarkLabel surplusLabel;
    private List<CardView> handView;

    public OtherHandPane(int cardNumber, int orientation) {
        setAlignment(Pos.CENTER);

        this.cardNumber = cardNumber;
        this.orientation = orientation;

        handView = new ArrayList<>();

        surplusLabel = new MarkLabel(MarkLabel.SURPLUS);
        surplusLabel.setVisible(false);

        handPane = new AnchorPane();
        int width, height;
        if (orientation == HORIZONTAL) {
            width = (cardNumber - 1) * DELTA_WIDTH + CardView.SMALL_WIDTH;
            height = CardView.SMALL_HEIGHT;
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().add(handPane);
            getChildren().addAll(surplusLabel, hBox);
        } else {
            width = CardView.SMALL_WIDTH;
            height = (cardNumber - 1) * DELTA_HEIGHT + CardView.SMALL_HEIGHT;
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(handPane);
            getChildren().addAll(vBox, surplusLabel);
        }
        handPane.setMaxSize(width, height);
        handPane.setPrefSize(width, height);
    }

    @Override
    public void draw() {
        int i = handView.size();
        if (i < cardNumber) {
            CardView cardView = new CardView();
            cardView.setSmall();
            int delta = 0;
            if (orientation == VERTICAL) {
                delta = i * DELTA_HEIGHT;
                cardView.setLayoutY(delta);
                handPane.setPrefHeight(delta + CardView.SMALL_HEIGHT);
            } else {
                delta = i * DELTA_WIDTH;
                cardView.setLayoutX(delta);
                handPane.setPrefWidth(delta + CardView.SMALL_WIDTH);
            }
            handView.add(cardView);
            handPane.getChildren().add(cardView);
        }
    }

    public void play(int number) {
        int newNumber = cardNumber - number;
        if (newNumber < 0)
            return;
        for (int i = cardNumber - 1; i >= newNumber; i--) {
            CardView cardView = handView.get(i);
            handPane.getChildren().remove(cardView);
            handView.remove(i);
        }
        if (orientation == HORIZONTAL) {
            int width = newNumber == 0 ? 0 : (newNumber - 1) * DELTA_WIDTH + CardView.SMALL_WIDTH;
            handPane.setPrefWidth(width);
        } else {
            int height = newNumber == 0 ? 0 : (newNumber - 1) * DELTA_HEIGHT + CardView.SMALL_HEIGHT;
            handPane.setPrefHeight(height);
        }
        cardNumber = newNumber;
    }

    @Override
    public void updateState() {
        if (!surplusLabel.isVisible()) {
            surplusLabel.setVisible(true);
        }
        if (cardNumber == 0)
            surplusLabel.setVisible(false);
        else
            surplusLabel.updateSurplus(cardNumber);
    }

    @Override
    public int getCardNumber() {
        return cardNumber;
    }
}
