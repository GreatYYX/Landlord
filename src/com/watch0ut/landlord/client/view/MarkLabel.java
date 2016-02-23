package com.watch0ut.landlord.client.view;

import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 * 标记文本，包括：胜利、输了、准备和剩几张。
 *
 * Created by Jack on 16/2/23.
 */
public class MarkLabel extends Label {
    public static final int WIN = 0;
    public static final int LOSE = 1;
    public static final int READY = 2;
    public static final int SURPLUS = 3;

    private static final String WIN_STRING = "胜利";
    private static final String LOSE_STRING = "输了";
    private static final String READY_STRING = "准备!";
    private static final String SURPLUS_STRING = "剩 %d 张";

    private int type;

    public MarkLabel(int type) {
        this.type = type;
        switch (type) {
            case WIN:
                setText(WIN_STRING);
                setTextFill(Paint.valueOf("#ffb805"));
                setFont(Font.font("System Bold", 24));
                break;
            case LOSE:
                setText(LOSE_STRING);
                setTextFill(Paint.valueOf("#aea8a8"));
                setFont(Font.font("System Bold", 24));
                break;
            case READY:
                setText(READY_STRING);
                setTextFill(Paint.valueOf("#ffc118"));
                setFont(Font.font("System Bold", 30));
                break;
            case SURPLUS:
                setText(SURPLUS_STRING);
                setTextFill(Paint.valueOf("#0096d7"));
                setFont(Font.font("System", 18));
                break;
            default:
                setText("");
        }
    }

    /**
     * 当标记文本是剩余几张类型的时候，可以更新剩余张数
     * @param number 剩余张数
     */
    public void updateSurplus(int number) {
        if (type == SURPLUS) {
            setText(String.format(SURPLUS_STRING, number));
        }
    }
}
