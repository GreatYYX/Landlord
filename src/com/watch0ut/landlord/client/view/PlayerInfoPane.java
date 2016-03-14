package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.model.PlayerModel;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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

    public PlayerInfoPane(PlayerModel playerModel) {
        this(playerModel.getAvatar(), playerModel.getNickName(), playerModel.getScore());

        avatarView.avatarProperty().bind(playerModel.avatarProperty());
        nickNameLabel.textProperty().bind(playerModel.nickNameProperty());
        scoreLabel.textProperty().bind(playerModel.scoreStringProperty());
    }

    public PlayerInfoPane(String picture, String nickName, int score) {
        setMinSize(240, 68);
        setMaxSize(240, 68);
        setVgap(10);
        setHgap(10);
        setPadding(new Insets(10, 10, 10, 10));

        avatarView = new AvatarView(picture, AvatarView.SMALL);
        getChildren().add(avatarView);

        Label staticLabel = new Label("昵称：");
        nickNameLabel = new Label(nickName);
        HBox nickNameBox = new HBox();
        nickNameBox.getChildren().addAll(staticLabel, nickNameLabel);

        staticLabel = new Label("积分：");
        scoreLabel = new Label(Integer.toString(score));
        HBox scoreBox = new HBox();
        scoreBox.getChildren().addAll(staticLabel, scoreLabel);

        VBox vBox = new VBox(4, nickNameBox, scoreBox);
        getChildren().add(vBox);
    }

    public void bind(PlayerModel playerModel) {
        updateAvatar(playerModel.getAvatar());
        updateNickName(playerModel.getNickName());
        updateScore(playerModel.getScore());
        avatarView.avatarProperty().bind(playerModel.avatarProperty());
        nickNameLabel.textProperty().bind(playerModel.nickNameProperty());
        scoreLabel.textProperty().bind(playerModel.scoreStringProperty());
    }

    public void unbind() {
        avatarView.avatarProperty().unbind();
        nickNameLabel.textProperty().unbind();
        scoreLabel.textProperty().unbind();
    }

    public void update(String photo, String nickName, int score) {
        updateAvatar(photo);
        updateNickName(nickName);
        updateScore(score);
    }

    public void updateAvatar(String photo) {
        avatarView.update(photo, AvatarView.SMALL);
    }

    public void updateNickName(String nickName) {
        nickNameLabel.setText(nickName);
    }

    public void updateScore(int score) {
        scoreLabel.setText(Integer.toString(score));
    }
}
