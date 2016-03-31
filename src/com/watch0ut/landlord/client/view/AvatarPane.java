package com.watch0ut.landlord.client.view;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * 头像面板,显示头像和名字
 *
 * Created by Jack on 16/2/13.
 */
public class AvatarPane extends VBox {
    private AvatarView avatarView;
    private int avatarSizeType;
    private Label nickNameLabel;

    /**
     * 构造函数
     * @param avatar 头像
     * @param sizeType 头像尺寸
     * @param username 名字
     */
    public AvatarPane(String avatar, int sizeType, String username) {
        setAlignment(Pos.CENTER);
        setMaxWidth(AvatarView.getSizeByType(sizeType));
        avatarSizeType = sizeType;
        avatarView = new AvatarView(avatar, avatarSizeType);

        nickNameLabel =  new Label(username);
        // 当用户昵称过长时就换行显示
        nickNameLabel.setWrapText(true);
        nickNameLabel.setTextAlignment(TextAlignment.CENTER);
        nickNameLabel.setMaxWidth(AvatarView.getSizeByType(sizeType));

        getChildren().add(avatarView);
        getChildren().add(nickNameLabel);
    }

    public StringProperty avatarProperty() {
        return avatarView.avatarProperty();
    }

    public StringProperty nickNameProperty() {
        return nickNameLabel.textProperty();
    }

    public void updateAvatar(String avatar) {
        avatarView.update(avatar, avatarSizeType);
    }

    public void updateNickName(String nickName) {
        nickNameLabel.setText(nickName);
    }
}
