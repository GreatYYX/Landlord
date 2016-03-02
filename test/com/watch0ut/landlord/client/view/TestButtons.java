package com.watch0ut.landlord.client.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * 测试准备按钮和操作面板
 *
 * Created by Jack on 16/2/27.
 */
public class TestButtons {

    public static Parent initialize() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);

        StartButton startButton = new StartButton();
        OperatePane operatePane = new OperatePane();

        vBox.getChildren().addAll(startButton, operatePane);
        return vBox;
    }
}
