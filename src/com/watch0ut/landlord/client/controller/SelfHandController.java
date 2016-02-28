package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.client.view.SelfHandPane;
import com.watch0ut.landlord.object.Card;
import javafx.application.Platform;

import java.util.List;

/**
 * 自己的手牌控制器
 *
 * Created by Jack on 16/2/28.
 */
public class SelfHandController {

    private SelfHandPane handPane;

    public SelfHandController(SelfHandPane selfHandPane) {
        this.handPane = selfHandPane;
    }

    public void deal() {
        new DealThread(handPane).start();
    }

    public void play() {
        List<Card> cards = handPane.getSelectedCards();
        if (cards.size() == 0)
            return;
        handPane.play();
        handPane.clearSelectedCards();
        handPane.updateState();
    }

    public void pass() {
        handPane.pass();
        handPane.clearSelectedCards();
    }

    class DealThread extends Thread {
        private SelfHandPane handPane;

        public DealThread(SelfHandPane selfHandPane) {
            this.handPane = selfHandPane;
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
}
