package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.Configuration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 大厅面板，包括牌桌和玩家列表
 *
 * Created by Jack on 16/2/27.
 */
public class HallPane extends BorderPane {

    private final static int RIGHT_WIDTH = 240;

    private FlowPane tablesPane;
    private List<CardTablePane> tableList;
    private PlayerListTable playerListTable;

    public HallPane() {
        setPrefSize(800, 600);
        setPadding(new Insets(10, 10, 10, 10));

        tableList = new ArrayList<>(Configuration.TABLE_PER_HALL);
        tablesPane = new FlowPane();
        tablesPane.setPadding(new Insets(10, 10, 10, 10));
        tablesPane.setVgap(10);
        tablesPane.setHgap(10);
        for (int i = 0; i < Configuration.TABLE_PER_HALL; i++) {
            CardTablePane cardTablePane = new CardTablePane(i + 1);
            tableList.add(cardTablePane);
            tablesPane.getChildren().add(cardTablePane);
        }
        setCenter(tablesPane);

        playerListTable = new PlayerListTable();
        VBox rightPart = new VBox();
        rightPart.setMinWidth(RIGHT_WIDTH);
        rightPart.setMaxWidth(RIGHT_WIDTH);
        rightPart.setAlignment(Pos.CENTER);
        rightPart.getChildren().add(playerListTable);
        setRight(rightPart);
    }

}
