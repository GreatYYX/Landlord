package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Card;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试已出的牌
 *
 * Created by Jack on 16/2/28.
 */
public class TestPlayedCardPane {

    public static Parent initialize() {
        List<Card> cards = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            int point = (int)(Math.random() * 13);
            int suit = (int)(Math.random() * 4);
            cards.add(new Card(point, suit));
        }

        return new PlayedCardPane(cards);
    }
}
