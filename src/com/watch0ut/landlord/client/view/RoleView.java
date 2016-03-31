package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 角色控件，分为四种：农民，地主3，地主A和地址3A
 *
 * Created by Jack on 16/2/13.
 */
public class RoleView extends ImageView {
    private final static String LANDLORD_3_PATH = "icon/action/landlord_3.png";
    private final static String LANDLORD_3A_PATH = "icon/action/landlord_3A.png";
    private final static String LANDLORD_A_PATH = "icon/action/landlord_A.png";

    private static Image landlord3Image;
    private static Image landlordAImage;
    private static Image landlord3AImage;

    private final static int SIZE = 32;

    private IntegerProperty role;

    /**
     * 构造函数，加载角色图片，设置尺寸和角色图片
     * @param role 角色类型，分别为：FARMER，LANDLORD_3，LANDLORD_A和LANDLORD_3A
     */
    public RoleView(Player.ROLE role) {
        try {
            landlord3Image = new Image(getClass().getResourceAsStream(LANDLORD_3_PATH));
            landlord3AImage = new Image(getClass().getResourceAsStream(LANDLORD_3A_PATH));
            landlordAImage = new Image(getClass().getResourceAsStream(LANDLORD_A_PATH));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setFitWidth(SIZE);
        setFitHeight(SIZE);
        update(role);

        this.role = new SimpleIntegerProperty(Player.ROLE.Farmer.getValue());
        this.role.addListener(new RoleChangeListener());
    }

    public int getRole() {
        return role.get();
    }

    public IntegerProperty roleProperty() {
        return role;
    }

    public void setRole(int role) {
        this.role.set(role);
    }

    /**
     * 更新角色图片
     * @param role 角色类型，分别为：FARMER，LANDLORD_3，LANDLORD_A和LANDLORD_3A
     */
    public void update(Player.ROLE role) {
        switch (role) {
            case Landlord3A:
                setImage(landlord3AImage);
                break;
            case LandlordA:
                setImage(landlordAImage);
                break;
            case Landlord3:
                setImage(landlord3Image);
                break;
            case Farmer:
            default:
                setImage(null);
                break;
        }
    }

    class RoleChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            update(Player.ROLE.getRole(newValue.intValue()));
        }
    }
}
