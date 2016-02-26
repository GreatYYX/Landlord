package com.watch0ut.landlord.client.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * 玩家面板
 *
 * Created by Jack on 16/2/25.
 */
public class PlayerPane extends VBox {

    private AnchorPane idleParent;
    private VBox playedParent;

    private ActionView actionView;
    private AvatarPane avatarPane;
    private ImageView readyImage;

    public PlayerPane() {
        setAlignment(Pos.CENTER);
        setMinSize(128, 148);

        idleParent = new AnchorPane();
        idleParent.setMinSize(128, 148);
        idleParent.setMaxSize(128, 148);

        playedParent = new VBox(4);
        playedParent.setAlignment(Pos.CENTER);
        playedParent.setPadding(new Insets(2, 0, 2, 0));

        actionView = new ActionView(ActionView.FARMER);
        playedParent.getChildren().add(actionView);

        avatarPane = new AvatarPane("", AvatarView.LARGE, "");
        avatarPane.setLayoutX(0);
        avatarPane.setLayoutY(0);
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
        readyImage.setLayoutY(96);

        getChildren().add(idleParent);
    }

    public void ready() {
        idleParent.getChildren().add(readyImage);
    }

    public void play() {
        getChildren().remove(idleParent);
        idleParent.getChildren().removeAll(avatarPane, readyImage);
        playedParent.getChildren().add(avatarPane);
        getChildren().add(playedParent);
    }

    public void leave() {
        if (getChildren().contains(playedParent)) {
            getChildren().remove(playedParent);
            updateAction(ActionView.FARMER);
            getChildren().add(idleParent);
        }
        idleParent.getChildren().removeAll(avatarPane, readyImage);
    }

    public void enter(String picture, String nickName) {
        updatePlayer(picture, nickName);
        idleParent.getChildren().add(avatarPane);
    }

    public void updateAction(int action) {
        if (actionView == null) {
            actionView.setVisible(false);
        } else {
            actionView.update(action);
        }
    }

    public void updatePlayer(String picture, String nickName) {
        avatarPane.updateAvatar(picture);
        avatarPane.updateUsername(nickName);
    }
}
