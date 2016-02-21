package com.watch0ut.landlord.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * 头像面板,显示头像和名字
 * Created by Jack on 16/2/13.
 */
public class AvatarPane extends VBox {
//    private ActionView actionView;
    private AvatarView avatarView;
    private int avatarSizeType;
    private Label usernameLabel;

    /**
     * 构造函数
     * @param avatar 头像
     * @param sizeType 头像尺寸
     * @param username 名字
     */
    public AvatarPane(String avatar, int sizeType, String username) {
        setAlignment(Pos.CENTER);
        setMaxWidth(AvatarView.getSizeByType(sizeType));
//        actionView = new ActionView(action);
        avatarSizeType = sizeType;
        avatarView = new AvatarView(avatar, avatarSizeType);
        usernameLabel =  new Label(username);
        // 当用户昵称过长时就换行显示
        usernameLabel.setWrapText(true);
        usernameLabel.setTextAlignment(TextAlignment.CENTER);

//        getChildren().add(actionView);
        getChildren().add(avatarView);
        getChildren().add(usernameLabel);
    }

//    public void updateAction(int action) {
//        actionView.update(action);
//    }

    public void updateAvatar(String avatar) {
        avatarView.update(avatar, avatarSizeType);
    }

    public void updateUsername(String username) {
        usernameLabel.setText(username);
    }
}
