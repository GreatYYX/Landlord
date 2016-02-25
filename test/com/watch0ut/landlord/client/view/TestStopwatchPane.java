package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.controller.StopwatchPaneController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * 测试秒表
 *
 * Created by Jack on 16/2/24.
 */
public class TestStopwatchPane {

    public static Parent initialize() {
        VBox parent = new VBox(10);
        parent.setAlignment(Pos.CENTER);

        final StopwatchPane stopwatchPane = new StopwatchPane();
        final StopwatchPaneController stopwatchPaneController = new StopwatchPaneController(20, stopwatchPane);

        Button startButton = new Button("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                new Thread(task).start();
                stopwatchPaneController.start();
            }
        });
        parent.getChildren().addAll(stopwatchPane, startButton);

        return parent;
    }
}

class CountdownThread extends Thread {
    private StopwatchPane stopwatchPane;
    private int time;

    public CountdownThread(StopwatchPane stopwatchPane, int time) {
        this.stopwatchPane = stopwatchPane;
        this.time = time;
    }

    public void run() {
        for (; time >= 0; time--) {
            // 界面操作需要在Application所在线程中完成
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stopwatchPane.updateTime(time);
                }
            });

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
