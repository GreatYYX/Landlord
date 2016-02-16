package com.watch0ut.landlord.client.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * 头像面板,显示角色、头像和名字
 * Created by Jack on 16/2/13.
 */
public class AvatarPane extends VBox {
    private ActionView actionView;
    private AvatarView avatarView;
    private Label usernameLabel;

    /**
     * 构造函数
     * @param action 角色类型
     * @param avatar 头像
     * @param username 名字
     */
    public AvatarPane(int action, String avatar, String username) {
        setAlignment(Pos.CENTER);
        actionView = new ActionView(action);
        avatarView = new AvatarView(avatar, AvatarView.MIDDLE);
        usernameLabel =  new Label(username);

        getChildren().add(actionView);
        getChildren().add(avatarView);
        getChildren().add(usernameLabel);
    }

    public void updateAction(int action) {
        actionView.update(action);
    }

    public void updateAvatar(String avatar) {
        avatarView.update(avatar, AvatarView.MIDDLE);
    }

    public void updateUsername(String username) {
        usernameLabel.setText(username);
    }
}
