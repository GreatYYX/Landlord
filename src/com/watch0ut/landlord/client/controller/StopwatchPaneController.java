package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.view.StopwatchPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 * 秒表控制器，有一个内部类，用来更新秒数。
 *
 * Created by Jack on 16/2/25.
 */
public class StopwatchPaneController {

    private int time;
    private StopwatchPane stopwatchPane;

    public StopwatchPaneController(int time, StopwatchPane stopwatchPane) {
        this.time = time;
        this.stopwatchPane = stopwatchPane;
    }

    /**
     * 开始倒计时，先创建一个倒计时任务，绑定倒计时结束的事件，然后开始倒计时。
     */
    public void start() {
        CountdownTask task = new CountdownTask(time);
        stopwatchPane.bindTimeProperty(task.messageProperty());
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                onSucceeded();
            }
        });
        new Thread(task).start();
    }

    /**
     * 当倒计时完成时的响应函数，解绑属性。
     */
    public void onSucceeded() {
        stopwatchPane.unbindTimeProperty();
    }

    /**
     * 计数任务，主要进行倒计时。
     */
    private class CountdownTask extends Task<StringProperty> {

        private Integer time;

        public CountdownTask(int time) {
            this.time = time;
            updateMessage(this.time.toString());
        }

        @Override
        protected StringProperty call() throws Exception {
            for (int i = time; i >= 0; i--) {
                if (isCancelled())
                    break;
                updateMessage(i + "");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return new SimpleStringProperty(time.toString());
        }
    }
}
