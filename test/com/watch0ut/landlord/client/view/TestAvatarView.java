package com.watch0ut.landlord.client.view;

import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试AvatarView，构造各种尺寸的头像
 *
 * Created by Jack on 16/2/21.
 */
public class TestAvatarView {

    public static Parent initialize() {
        AvatarView avatarView2 = new AvatarView("2.png", AvatarView.TINY);
        AvatarView avatarView4 = new AvatarView("4.png", AvatarView.MINI);
        AvatarView avatarView8 = new AvatarView("8.png", AvatarView.SMALL);
        AvatarView avatarViewJ = new AvatarView("J.png", AvatarView.MIDDLE);
        AvatarView avatarViewQ = new AvatarView("Q.png", AvatarView.BIG);
        AvatarView avatarViewK = new AvatarView("K.png", AvatarView.LARGE);

        FlowPane root = new FlowPane();
        root.getChildren().add(avatarView2);
        root.getChildren().add(avatarView4);
        root.getChildren().add(avatarView8);
        root.getChildren().add(avatarViewJ);
        root.getChildren().add(avatarViewQ);
        root.getChildren().add(avatarViewK);

        return root;
    }
}
