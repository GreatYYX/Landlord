package com.watch0ut.landlord.client.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 头像控件，尺寸共六种，分别为tiny，mini，small，middle，big和large。
 * 默认的头像名为idle。
 * Created by Jack on 16/2/13.
 */
public class AvatarView extends ImageView {

    private final static int TINY_SIZE = 16;
    private final static int MINI_SIZE = 32;
    private final static int SMALL_SIZE = 48;
    private final static int MIDDLE_SIZE = 64;
    private final static int BIG_SIZE = 96;
    private final static int LARGE_SIZE = 128;

    private final static String AVATAR_PATH = "icon/avatar/";
    public final static String IDLE = "idle.png";

    public final static int TINY = 1;
    public final static int MINI = 2;
    public final static int SMALL = 3;
    public final static int MIDDLE = 4;
    public final static int BIG = 5;
    public final static int LARGE = 6;

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

    public AvatarView(Image image, int sizeType) {
        update(image, sizeType);
    }

    /**
     * 更新头像图片和尺寸
     * @param picture 头像名称
     * @param width 头像宽度
     * @param height 头像高度
     */
    public void update(String picture, int width, int height) {
        setFitWidth(width);
        setFitHeight(height);

        Image image = null;
        try {
            image = new Image(getClass().getResourceAsStream(AVATAR_PATH + picture));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setImage(image);
    }

    /**
     * 更新头像图片和尺寸
     * @param picture 头像名称
     * @param sizeType 尺寸类型
     */
    public void update(String picture, int sizeType) {
        int size = getSizeByType(sizeType);
        update(picture, size, size);
    }

    public void update(Image image, int sizeType) {
        int size = getSizeByType(sizeType);
        setFitWidth(size);
        setFitHeight(size);

        setImage(image);
    }

    public static int getSizeByType(int sizeType) {
        switch (sizeType) {
            case TINY:
                return TINY_SIZE;
            case MINI:
                return MINI_SIZE;
            case SMALL:
                return SMALL_SIZE;
            case MIDDLE:
                return MIDDLE_SIZE;
            case BIG:
                return BIG_SIZE;
            case LARGE:
                return LARGE_SIZE;
            default:
                return 0;
        }
    }
}
