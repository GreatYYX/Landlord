package com.watch0ut.landlord.client.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * 操作按钮，包括出牌，不出
 *
 * Created by Jack on 16/2/27.
 */
public class OperatePane extends FlowPane {

    private Button playButton;
    private Button discardButton;

    public OperatePane() {
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10, 10, 10, 10));
        setPrefSize(144, 52);

        playButton = new Button("出牌");
        playButton.setPrefSize(56, 32);

        discardButton = new Button("不出");
        discardButton.setPrefSize(56, 32);

        getChildren().addAll(playButton, discardButton);
    }
}
