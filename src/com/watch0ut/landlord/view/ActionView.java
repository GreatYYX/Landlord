package com.watch0ut.landlord.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Jack on 16/2/13.
 */
public class ActionView extends Label {
    private final static String ACTION_PATH = "icon/action/";
    private final static String LANDLORD_3_STRING = "landlord_3.png";
    private final static String LANDLORD_3A_STRING = "landlord_3A.png";
    private final static String LANDLORD_A_STRING = "landlord_A.png";

    public final static int LANDLORD_3 = 1;
    public final static int LANDLORD_A = 2;
    public final static int LANDLORD_3A = 3;

    public ActionView(int action) {
        setMinWidth(48);
        setMinHeight(48);
        update(action);
    }

    /**
     * 更新角色图片
     * @param action 角色类型
     */
    public void update(int action) {
        String imagePath = ACTION_PATH;
        switch (action) {
            case LANDLORD_3:
                imagePath += LANDLORD_3_STRING;
                break;
            case LANDLORD_A:
                imagePath += LANDLORD_A_STRING;
                break;
            case LANDLORD_3A:
                imagePath += LANDLORD_3A_STRING;
                break;
            default:
                break;
        }
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            setGraphic(new ImageView(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
