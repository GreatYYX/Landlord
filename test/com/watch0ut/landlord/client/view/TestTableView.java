package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试TableView控件，以及它提供的函数
 *
 * Created by Jack on 16/2/20.
 */
public class TestTableView {

    public static int upReadyCount = 0;
    public static int leftReadyCount = 0;
    public static int downReadyCount = 0;
    public static int rightReadyCount = 0;
    public static int playCount = 0;

    /**
     * 测试TablePane的布局和提供的更改状态函数
     * @return 构造好的Pane对象
     */
    public static Parent initialize() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        final TableView tablePane = new TableView();
        vBox.getChildren().add(tablePane);

        initializeUpReadyButton(vBox, tablePane);
        initializeLeftReadyButton(vBox, tablePane);
        initializeDownReadyButton(vBox, tablePane);
        initializeRightReadyButton(vBox, tablePane);

        Button playButton = new Button("Play");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TestTableView.playCount % 2 == 0)
                    tablePane.setTablePlay();
                else
                    tablePane.setTableNormal();
                TestTableView.playCount++;
            }
        });
        vBox.getChildren().add(playButton);

        return vBox;
    }

    private static void initializeUpReadyButton(VBox vBox, final TableView tablePane) {
        Button upReadyButton = new Button("Up Ready");
        upReadyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TestTableView.upReadyCount % 2 == 0)
                    tablePane.setUpReady();
                else
                    tablePane.setUpLeave();
                TestTableView.upReadyCount++;
            }
        });
        vBox.getChildren().add(upReadyButton);
    }

    private static void initializeLeftReadyButton(VBox vBox, final TableView tablePane) {
        Button leftReadyButton = new Button("Left Ready");
        leftReadyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TestTableView.leftReadyCount % 2 == 0)
                    tablePane.setLeftReady();
                else
                    tablePane.setLeftLeave();
                TestTableView.leftReadyCount++;
            }
        });
        vBox.getChildren().add(leftReadyButton);
    }

    private static void initializeDownReadyButton(VBox vBox, final TableView tablePane) {
        Button downReadyButton = new Button("Down Ready");
        downReadyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TestTableView.downReadyCount % 2 == 0)
                    tablePane.setDownReady();
                else
                    tablePane.setDownLeave();
                TestTableView.downReadyCount++;
            }
        });
        vBox.getChildren().add(downReadyButton);
    }

    private static void initializeRightReadyButton(VBox vBox, final TableView tablePane) {
        Button rightReadyButton = new Button("Right Ready");
        rightReadyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TestTableView.rightReadyCount % 2 == 0)
                    tablePane.setRightReady();
                else
                    tablePane.setRightLeave();
                TestTableView.rightReadyCount++;
            }
        });
        vBox.getChildren().add(rightReadyButton);
    }
}
