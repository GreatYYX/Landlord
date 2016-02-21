package com.watch0ut.landlord.client.view;

import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试头像面板，测试各种尺寸和各种名字长度
 * Created by Jack on 16/2/21.
 */
public class TestAvatarPane {

    public static Parent initialize() {
        AvatarPane avatarPaneJ = new AvatarPane("J.png", AvatarView.MINI, "Jack");
        AvatarPane avatarPaneQ = new AvatarPane("Q.png", AvatarView.SMALL, "Queen");
        AvatarPane avatarPaneK = new AvatarPane("K.png", AvatarView.MIDDLE, "King");
        AvatarPane avatarPane4 = new AvatarPane("4.png", AvatarView.BIG, "四四四四四四四四");
        AvatarPane avatarPane8 = new AvatarPane("8.png", AvatarView.LARGE, "88888888888888");

        FlowPane root = new FlowPane();
        root.getChildren().addAll(avatarPaneJ, avatarPaneQ, avatarPaneK,
                avatarPane4, avatarPane8);

        return root;
    }
}
