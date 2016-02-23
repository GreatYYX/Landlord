package com.watch0ut.landlord.client.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * 玩家个人信息面板，显示头像、昵称和积分
 *
 * Created by Jack on 16/2/21.
 */
public class PlayerInfoPane extends FlowPane {

    private AvatarView avatarView;
    private Label nickNameLabel;
    private Label scoreLabel;

    public PlayerInfoPane() {
        this(AvatarView.IDLE, "", 0);
    }

    public PlayerInfoPane(String picture, String nickName, int score) {
        setMinSize(240, 68);
        setMaxSize(240, 68);
        setVgap(10);
        setHgap(10);
        setPadding(new Insets(10, 10, 10, 10));

        avatarView = new AvatarView(picture, AvatarView.SMALL);
        getChildren().add(avatarView);

        nickNameLabel = new Label("昵称：" + nickName);
        scoreLabel = new Label("积分：" + score);

        VBox vBox = new VBox(4, nickNameLabel, scoreLabel);
        getChildren().add(vBox);
    }

    public void updateScore(int score) {
        scoreLabel.setText("积分：" + score);
    }
}
