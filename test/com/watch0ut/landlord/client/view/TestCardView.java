package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Card;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试纸牌
 * Created by Jack on 16/2/21.
 */
public class TestCardView {

    public static Parent initialize() {
        CardView rear = new CardView(CardView.REAR);
        CardView smallRear = new CardView(CardView.REAR, true);

        CardView diamond4 = new CardView(0, 0);
        CardView spade3 = new CardView(Card.getSpade3());
        CardView spadeA = new CardView(3, 10);

        FlowPane root = new FlowPane();
        root.getChildren().add(smallRear);
        root.getChildren().add(rear);
        root.getChildren().add(diamond4);
        root.getChildren().add(spade3);
        root.getChildren().add(spadeA);

        return root;
    }
}
