package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.util.DrawThread;
import com.watch0ut.landlord.client.view.OtherHandPane;

/**
 * 其他玩家手牌控制器
 *
 * Created by Jack on 16/2/29.
 */
public class OtherHandController {

    private OtherHandPane handPane;

    public OtherHandController(OtherHandPane otherHandPane) {
        handPane = otherHandPane;
    }

    public void draw() {
        new DrawThread(handPane).start();
    }

    public void play(int number) {
        handPane.play(number);
        handPane.updateState();
    }
}
