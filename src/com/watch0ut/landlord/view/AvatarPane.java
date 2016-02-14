package com.watch0ut.landlord.view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

/**
 * Created by Jack on 16/2/13.
 */
public class AvatarPane extends VBox {
    private ActionView actionView;
    private AvatarView avatarView;

    public AvatarPane(int action, String avatar) {
        setAlignment(Pos.CENTER);
        actionView = new ActionView(action);
        avatarView = new AvatarView(avatar, AvatarView.MIDDLE);

        getChildren().add(actionView);
        getChildren().add(avatarView);
    }
}
