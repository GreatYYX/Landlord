package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.object.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * 玩家面板
 *
 * Created by Jack on 16/2/25.
 */
public class PlayerPane extends AnchorPane {

    public final static int WIDTH = 128;
    public final static int HEIGHT = 180;

    private RoleView roleView;
    private AvatarPane avatarPane;
    private ImageView readyImage;

    public PlayerPane() {
        setMinSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);

        roleView = new RoleView(Player.ROLE.Farmer);
        roleView.setLayoutX(48);
        roleView.setLayoutY(0);
        roleView.setVisible(false);
        getChildren().add(roleView);

        avatarPane = new AvatarPane("", AvatarView.LARGE, "");
        avatarPane.setLayoutX(0);
        avatarPane.setLayoutY(32);
        getChildren().add(avatarPane);

        readyImage = new ImageView();
        try {
            Image image = new Image(getClass().getResourceAsStream("icon/widget/ready.png"));
            readyImage.setImage(image);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        readyImage.setFitWidth(32);
        readyImage.setFitHeight(32);
        readyImage.setLayoutX(96);
        readyImage.setLayoutY(128);
        readyImage.setVisible(false);
        getChildren().add(readyImage);
    }

    public void ready() {
        readyImage.setVisible(true);
    }

    public void unseat() {
        avatarPane.avatarProperty().unbind();
        avatarPane.nickNameProperty().unbind();
        readyImage.setVisible(false);
        avatarPane.updateAvatar(null);
        avatarPane.updateNickName("");
    }

    public void seat(PlayerModel playerModel) {
        avatarPane.updateNickName(playerModel.getNickName());
        avatarPane.updateAvatar(playerModel.getAvatar());
        avatarPane.avatarProperty().bind(playerModel.avatarProperty());
        avatarPane.nickNameProperty().bind(playerModel.nickNameProperty());
    }

    public void seat(String picture, String nickName) {
        updatePlayer(picture, nickName);
    }

    public void showRole() {
        roleView.setVisible(true);
    }

    public void hideRole() {
        roleView.setVisible(false);
    }

    public void updatePlayer(String picture, String nickName) {
        avatarPane.updateAvatar(picture);
        avatarPane.updateNickName(nickName);
    }
}
