package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.controller.PlayerListController;
import com.watch0ut.landlord.client.model.PlayerModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试玩家列表
 *
 * Created by Jack on 16/2/27.
 */
public class TestPlayerListTable {

    private static int id = 1;
    private static List<PlayerModel> players;

    public static Parent initialize() {
        players = new ArrayList<PlayerModel>();

        VBox parent = new VBox(10);
        parent.setAlignment(Pos.CENTER);

        PlayerListTable playerListTable = new PlayerListTable();
        final PlayerListController playerListController = new PlayerListController(playerListTable);

        HBox buttons = new HBox(5);
        buttons.setAlignment(Pos.CENTER);

        Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int score = (int)(Math.random() * 20 + 40);
                PlayerModel player = new PlayerModel(
                        id++,
                        "J.png",
                        "Jack",
                        score
                );
                players.add(player);
                playerListController.addPlayer(player);
            }
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int i = (int)(Math.random() * players.size());
                PlayerModel player = players.get(i);
                player.setScore((int)(Math.random() * 10 + 50));
            }
        });

        buttons.getChildren().addAll(addButton, updateButton);

        parent.getChildren().addAll(playerListTable, buttons);

        return parent;
    }
}
