package com.watch0ut.landlord.client.util;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.client.view.HandPane;
import javafx.application.Platform;

/**
 * Created by Jack on 16/2/29.
 */
public class DrawThread extends Thread {
    private HandPane handPane;

    public DrawThread(HandPane handPane) {
        this.handPane = handPane;
    }

    public void run() {
        int cardNumber = handPane.getCardNumber();
        for (int i = 1; i <= cardNumber; i++) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    handPane.draw();
                }
            });

            try {
                sleep(Configuration.CLIENT_START_DELAY / cardNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                handPane.updateState();
            }
        });
    }
}
