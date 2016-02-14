package com.watch0ut.landlord.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jack on 16/2/13.
 */
public class AvatarView extends Label {
    public final static String IDLE = "idle.png";
    public final static String TABLE = "../widget/table_small.png";
    public final static String TABLE_PLAY = "../widget/table_small_play.png";

    public final static int SMALL_SIZE = 64;
    public final static int MIDDLE_SIZE = 96;
    public final static int BIG_SIZE = 128;

    private final static String AVATAR_PATH = "icon/avatar/";

    public final static int SMALL = 1;
    public final static int MIDDLE = 2;
    public final static int BIG = 3;

    /**
     * 构造函数，三个参数
     * @param picture 头像名称
     * @param width 宽度
     * @param height 高度
     */
    public AvatarView(String picture, int width, int height) {
        update(picture, width, height);
    }

    /**
     * 构造函数，两个参数
     * @param picture 头像名称
     * @param type 尺寸类型，比如Small，Middle和Big
     */
    public AvatarView(String picture, int type) {
        update(picture, type);
    }

    /**
     * 更新头像图片和尺寸
     * @param picture 头像名称
     * @param width 头像宽度
     * @param height 头像高度
     */
    public void update(String picture, int width, int height) {
        setWidth(width);
        setHeight(height);

        try {
            Image image = new Image(getClass().getResourceAsStream(AVATAR_PATH + picture));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新头像图片和尺寸
     * @param picture 头像名称
     * @param type 尺寸类型
     */
    public void update(String picture, int type) {
        int size;
        switch (type) {
            case SMALL:
                size = SMALL_SIZE;
                break;
            case MIDDLE:
                size = MIDDLE_SIZE;
                break;
            case BIG:
                size = BIG_SIZE;
                break;
            default:
                size = SMALL_SIZE;
        }

        update(picture, size, size);
    }
}
