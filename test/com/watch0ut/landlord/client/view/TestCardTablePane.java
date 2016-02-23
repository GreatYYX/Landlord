package com.watch0ut.landlord.client.view;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试牌桌
 *
 * Created by Jack on 16/2/21.
 */
public class TestCardTablePane {

    public static Parent initialize() {
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10, 10, 10, 10));
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        for (int i = 1; i <= 16; i++) {
            flowPane.getChildren().add(new CardTablePane(i));
        }

        return flowPane;
    }
}
