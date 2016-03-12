package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Player;
import com.watch0ut.landlord.object.Table;
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
 *
 * Created by Jack on 16/2/19.
 */
public class MiniTablePane extends VBox {

    private Image idleImage;

    private GridPane gridPane;
    private AvatarView topAvatar;
    private Label topLabel;
    private AvatarView leftAvatar;
    private Label leftLabel;
    private AvatarView bottomAvatar;
    private Label bottomLabel;
    private AvatarView rightAvatar;
    private Label rightLabel;
    private TableView tableView;
    private Label numberLabel;
    private int tableId;

    public MiniTablePane(int number) {
        setAlignment(Pos.CENTER);
        tableId = number - 1;

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(4);
        gridPane.setVgap(4);

        try {
            idleImage = new Image(getClass().getResourceAsStream("icon/avatar/" + AvatarView.IDLE));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        initializeAvatars();
        initializeLabels();

        tableView = new TableView();
        gridPane.add(tableView, 1, 1);

        getChildren().add(gridPane);

        numberLabel = new Label("- " + number + " -");
        getChildren().add(numberLabel);
    }

    private void initializeAvatars() {
        topAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(topAvatar, 1, 0);
        GridPane.setHalignment(topAvatar, HPos.CENTER);
        GridPane.setValignment(topAvatar, VPos.CENTER);

        leftAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(leftAvatar, 0, 1);
        GridPane.setHalignment(leftAvatar, HPos.CENTER);
        GridPane.setValignment(leftAvatar, VPos.CENTER);

        bottomAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(bottomAvatar, 1, 2);
        GridPane.setHalignment(bottomAvatar, HPos.CENTER);
        GridPane.setValignment(bottomAvatar, VPos.CENTER);

        rightAvatar = new AvatarView(idleImage, AvatarView.MINI);
        gridPane.add(rightAvatar, 2, 1);
        GridPane.setHalignment(rightAvatar, HPos.CENTER);
        GridPane.setValignment(rightAvatar, VPos.CENTER);
    }

    private void initializeLabels() {
        topLabel = new Label();
        topLabel.setWrapText(true);
        topLabel.setTextAlignment(TextAlignment.RIGHT);
        gridPane.add(topLabel, 0, 0);
        GridPane.setHalignment(topLabel, HPos.RIGHT);
        GridPane.setValignment(topLabel, VPos.CENTER);

        leftLabel = new Label();
        leftLabel.setWrapText(true);
        leftLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(leftLabel, 0, 2);
        GridPane.setValignment(leftLabel, VPos.TOP);
        GridPane.setHalignment(leftLabel, HPos.CENTER);

        bottomLabel = new Label();
        bottomLabel.setWrapText(true);
        bottomLabel.setTextAlignment(TextAlignment.LEFT);
        gridPane.add(bottomLabel, 2, 2);
        GridPane.setHalignment(bottomLabel, HPos.LEFT);
        GridPane.setValignment(bottomLabel, VPos.CENTER);

        rightLabel = new Label();
        rightLabel.setWrapText(true);
        rightLabel.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(rightLabel, 2, 0);
        GridPane.setValignment(rightLabel, VPos.BOTTOM);
        GridPane.setHalignment(rightLabel, HPos.CENTER);
    }
    
    public void seat(Player player) {
        switch (player.getTablePosition()) {
            case Table.TOP:
                topAvatar.update(player.getPhoto(), AvatarView.MINI);
                topLabel.setText(player.getNickName());
                break;
            case Table.BOTTOM:
                bottomAvatar.update(player.getPhoto(), AvatarView.MINI);
                bottomLabel.setText(player.getNickName());
                break;
            case Table.LEFT:
                leftAvatar.update(player.getPhoto(), AvatarView.MINI);
                leftLabel.setText(player.getNickName());
                break;
            case Table.RIGHT:
                rightAvatar.update(player.getPhoto(), AvatarView.MINI);
                rightLabel.setText(player.getNickName());
                break;
        }
    }

    public void unseat(Player player) {
        switch (player.getTablePosition()) {
            case Table.TOP:
                tableView.setTopIdle();
                topAvatar.update(idleImage, AvatarView.MINI);
                topLabel.setText("");
                break;
            case Table.BOTTOM:
                tableView.setBottomIdle();
                bottomAvatar.update(idleImage, AvatarView.MINI);
                bottomLabel.setText("");
                break;
            case Table.LEFT:
                tableView.setLeftIdle();
                leftAvatar.update(idleImage, AvatarView.MINI);
                leftLabel.setText("");
                break;
            case Table.RIGHT:
                tableView.setRightIdle();
                rightAvatar.update(idleImage, AvatarView.MINI);
                rightLabel.setText("");
                break;
        }
    }

    public void ready(Player player) {
        switch (player.getTablePosition()) {
            case Table.TOP:
                tableView.setTopReady();
                break;
            case Table.BOTTOM:
                tableView.setBottomReady();
                break;
            case Table.LEFT:
                tableView.setLeftReady();
                break;
            case Table.RIGHT:
                tableView.setRightReady();
                break;
        }
    }

    public int getTableId() {
        return tableId;
    }
}
