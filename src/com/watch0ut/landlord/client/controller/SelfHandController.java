package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.util.DrawThread;
import com.watch0ut.landlord.client.view.SelfHandPane;
import com.watch0ut.landlord.object.Card;

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

    public void draw() {
        new DrawThread(handPane).start();
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


}
