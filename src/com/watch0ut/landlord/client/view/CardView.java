package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Card;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 纸牌
 *
 * Created by Jack on 16/2/13.
 */
public class CardView extends ImageView {

    private final static String CARD_PATH = "icon/card/";
    public final static String REAR = "Rear.png";

    public final static int WIDTH = 126;
    public final static int HEIGHT = 154;

    public final static int SMALL_WIDTH = 84;
    public final static int SMALL_HEIGHT = 103;

    private Card card;

    /**
     * 构造函数，根据花色和点数，显示对应图片。
     * @param suit 花色, 0对应方片、1对应草花、2对应红桃、3对应黑桃
     * @param point 点数，0对应4、11对应2、12对应3
     */
    public CardView(int suit, int point) {
        card = new Card(point, suit);
        String name = card.getCard() + ".png";
        update(name);
    }

    /**
     * 构造函数，根据Card显示相应的图片
     * @param card 包含花色和点数信息
     */
    public CardView(Card card) {
        this.card = card;
        String name = card.getCard() + ".png";
        update(name);
    }

    /**
     * 构造函数，根据图片的名字显示相应的图片
     * @param name 图片名字
     */
    public CardView(String name) {
        update(name);
    }

    public CardView(String name, boolean small) {
        if (small)
            update(name, SMALL_WIDTH, SMALL_HEIGHT);
        else
            update(name);
    }

    public Card getCard() {
        return card;
    }

    public void update(String name) {
        update(name, WIDTH, HEIGHT);
    }

    /**
     * 通过图片的名字，更新显示的图片
     * @param name 图片的名字
     */
    public void update(String name, int width, int height) {
        setFitWidth(width);
        setFitHeight(height);
        try {
            Image image = new Image(getClass().getResourceAsStream(CARD_PATH + name));
            setImage(image);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
