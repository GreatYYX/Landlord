package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Card;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * 已出的牌
 *
 * Created by Jack on 16/2/28.
 */
public class PlayedCardPane extends AnchorPane {

    private final static int DELTA = 16;

    public PlayedCardPane(List<Card> cards) {
        setMinSize(CardView.SMALL_WIDTH, CardView.SMALL_HEIGHT);
        setMaxSize(CardView.SMALL_WIDTH * 5, CardView.SMALL_HEIGHT);
        setPrefSize(cards.size() * DELTA + CardView.SMALL_WIDTH, CardView.SMALL_HEIGHT);

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            CardView cardView = new CardView(card);
            cardView.setSmall();
            cardView.setLayoutX(i * DELTA);
            getChildren().add(cardView);
        }
    }
}
