package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试标记文本，显示四种类型的标记文本
 *
 * Created by Jack on 16/2/23.
 */
public class TestMarkLabel {

    private static int surplusNumber = 13;

    public static Parent initialize() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(5, 10, 5, 10));

        MarkLabel win = new MarkLabel(MarkLabel.WIN);
        MarkLabel lose = new MarkLabel(MarkLabel.LOSE);
        MarkLabel ready = new MarkLabel(MarkLabel.READY);
        final MarkLabel surplus = new MarkLabel(MarkLabel.SURPLUS);
        surplus.updateSurplus(surplusNumber);

        vBox.getChildren().addAll(win, lose, ready, surplus);

        Button updateSurplusButton = new Button("Update Surplus");
        updateSurplusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                surplusNumber = --surplusNumber % 13;
                surplus.updateSurplus(surplusNumber);
                if (surplusNumber <= 0)
                    surplusNumber += 13;
            }
        });
        vBox.getChildren().add(updateSurplusButton);

        return vBox;
    }
}
