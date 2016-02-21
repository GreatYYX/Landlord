package com.watch0ut.landlord.client.view;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * 牌桌，包含牌桌号和四个玩家，会显示玩家的头像和昵称。
 * Created by Jack on 16/2/19.
 */
public class CardTablePane extends VBox {

    private Image idleImage;

    private GridPane gridPane;
    private AvatarView upAvatar;
    private Label upLabel;
    private AvatarView leftAvatar;
    private Label leftLabel;
    private AvatarView downAvatar;
    private Label downLabel;
    private AvatarView rightAvatar;
    private Label rightLabel;
    private TablePane tablePane;
    private Label numberLabel;

    public CardTablePane(int number) {
        setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(4);
        gridPane.setVgap(4);

        try {
            idleImage = new Image(getClass().getResourceAsStream(AvatarView.IDLE));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        initializeAvatars();
        initializeLabels();

        tablePane = new TablePane();
        gridPane.add(tablePane, 1, 1);

        getChildren().add(gridPane);

        numberLabel = new Label("- " + number + " -");
        getChildren().add(numberLabel);
    }

    private void initializeAvatars() {
        upAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(upAvatar, 1, 0);
        gridPane.setHalignment(upAvatar, HPos.CENTER);
        gridPane.setValignment(upAvatar, VPos.CENTER);

        leftAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(leftAvatar, 0, 1);
        gridPane.setHalignment(leftAvatar, HPos.CENTER);
        gridPane.setValignment(leftAvatar, VPos.CENTER);

        downAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(downAvatar, 1, 2);
        gridPane.setHalignment(downAvatar, HPos.CENTER);
        gridPane.setValignment(downAvatar, VPos.CENTER);

        rightAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(rightAvatar, 2, 1);
        gridPane.setHalignment(rightAvatar, HPos.CENTER);
        gridPane.setValignment(rightAvatar, VPos.CENTER);
    }

    private void initializeLabels() {
        upLabel = new Label();
        upLabel.setWrapText(true);
        upLabel.setTextAlignment(TextAlignment.RIGHT);
        gridPane.add(upLabel, 0, 0);
        gridPane.setHalignment(upLabel, HPos.RIGHT);
        gridPane.setValignment(upLabel, VPos.CENTER);

        leftLabel = new Label();
        leftLabel.setWrapText(true);
        leftLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(leftLabel, 0, 2);
        gridPane.setValignment(leftLabel, VPos.TOP);
        gridPane.setHalignment(leftLabel, HPos.CENTER);

        downLabel = new Label();
        downLabel.setWrapText(true);
        downLabel.setTextAlignment(TextAlignment.LEFT);
        gridPane.add(downLabel, 2, 2);
        gridPane.setHalignment(downLabel, HPos.LEFT);
        gridPane.setValignment(downLabel, VPos.CENTER);

        rightLabel = new Label();
        rightLabel.setWrapText(true);
        rightLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(rightLabel, 2, 0);
        gridPane.setValignment(rightLabel, VPos.BOTTOM);
        gridPane.setHalignment(rightLabel, HPos.CENTER);
    }

}
