package com.watch0ut.landlord.view;

import com.watch0ut.landlord.object.Card;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jack on 16/2/13.
 */
public class CardView extends Label {
    public final static int WIDTH = 126;
    public final static int HEIGHT = 154;
    private final static String CARD_PATH = "icon/card/";

    public final static int SMALL_WIDTH = 84;
    public final static int SMALL_HEIGHT = 103;

    /**
     * 构造函数，根据花色和点数，显示对应图片。
     * @param suit 花色, 0对应方片、1对应草花、2对应红桃、3对应黑桃
     * @param point 点数，0对应4、11对应2、12对应3
     */
    public CardView(int suit, int point) {
        String name = new Card(point, suit).getCard() + ".png";
        update(name);
    }

    /**
     * 构造函数，根据Card显示相应的图片
     * @param card 包含花色和点数信息
     */
    public CardView(Card card) {
        String name = card.getCard() + ".png";
        update(name);
    }

    /**
     * 构造函数，根据图片的名字显示相应的图片
     * @param name
     */
    public CardView(String name) {
        update(name);
    }

    public CardView(String name, boolean small) {
        try {
            Image image = new Image(getClass().getResourceAsStream(CARD_PATH + name));
            ImageView imageView = new ImageView(image);
            if (small) {
                imageView.setFitWidth(SMALL_WIDTH);
                imageView.setFitHeight(SMALL_HEIGHT);
            }
            setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过图片的名字，更新显示的图片
     * @param name 图片的名字
     */
    public void update(String name) {
        try {
            Image image = new Image(getClass().getResourceAsStream(CARD_PATH + name));
            ImageView imageView = new ImageView(image);
            setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
