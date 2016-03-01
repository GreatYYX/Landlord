package com.watch0ut.landlord.client.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 牌桌面板，包括玩家信息面板、玩家列表面板和聊天面板
 *
 * Created by Jack on 16/3/1.
 */
public class TablePane extends BorderPane {

    private final static String BG_PATH = "icon/widget/table_bg.png";

    private PlayerInfoPane playerInfoPane;
    private PlayerListTable playerListTable;
    private ChatPane chatPane;

    public TablePane() {
        setMinSize(1164, 694);
        setMaxSize(1164, 694);

        playerInfoPane = new PlayerInfoPane();
        playerListTable = new PlayerListTable();
        playerListTable.setMinHeight(112);
        playerListTable.setPrefHeight(139);
        chatPane = new ChatPane();
        chatPane.setPrefSize(240, 477);
        VBox rightBox = new VBox(5);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.getChildren().addAll(playerInfoPane, playerListTable, chatPane);

        setRight(rightBox);

        StackPane centerPane = new StackPane();
        ImageView background = new ImageView();
        background.setFitWidth(924);
        background.setFitHeight(694);
        try {
            Image image = new Image(getClass().getResourceAsStream(BG_PATH));
            background.setImage(image);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        centerPane.getChildren().add(background);
        setCenter(centerPane);
    }
}
