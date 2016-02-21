package com.watch0ut.landlord.client.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 角色控件，分为四种：农民，地主3，地主A和地址3A
 * Created by Jack on 16/2/13.
 */
public class ActionView extends ImageView {
    private final static String LANDLORD_3_PATH = "icon/action/landlord_3.png";
    private final static String LANDLORD_3A_PATH = "icon/action/landlord_3A.png";
    private final static String LANDLORD_A_PATH = "icon/action/landlord_A.png";

    private static Image landlord3Image;
    private static Image landlordAImage;
    private static Image landlord3AImage;

    private final static int SIZE = 32;

    public final static int FARMER = 0;
    public final static int LANDLORD_3 = 1;
    public final static int LANDLORD_A = 2;
    public final static int LANDLORD_3A = 3;

    /**
     * 构造函数，加载角色图片，设置尺寸和角色图片
     * @param action 角色类型，分别为：FARMER，LANDLORD_3，LANDLORD_A和LANDLORD_3A
     */
    public ActionView(int action) {
        try {
            landlord3Image = new Image(getClass().getResourceAsStream(LANDLORD_3_PATH));
            landlord3AImage = new Image(getClass().getResourceAsStream(LANDLORD_3A_PATH));
            landlordAImage = new Image(getClass().getResourceAsStream(LANDLORD_A_PATH));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setFitWidth(SIZE);
        setFitHeight(SIZE);
        update(action);
    }

    /**
     * 更新角色图片
     * @param action 角色类型，分别为：FARMER，LANDLORD_3，LANDLORD_A和LANDLORD_3A
     */
    public void update(int action) {
        switch (action) {
            case LANDLORD_3:
                setImage(landlord3Image);
                break;
            case LANDLORD_A:
                setImage(landlordAImage);
                break;
            case LANDLORD_3A:
                setImage(landlord3AImage);
                break;
            case FARMER:
            default:
                setImage(null);
                break;
        }
    }
}
