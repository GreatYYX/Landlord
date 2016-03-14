package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.view.PlayerListTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * 玩家列表控制器
 *
 * Created by Jack on 16/2/27.
 */
public class PlayerListController {

    private PlayerListTable playerListTable;
    private ObservableList<PlayerModel> playerItems;

    public PlayerListController(PlayerListTable playerListTable) {
        this.playerListTable = playerListTable;
        playerItems = FXCollections.observableArrayList();
        this.playerListTable.setItems(playerItems);
    }

    public void addPlayers(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            playerItems.add(player);
        }
    }

    public void addPlayer(PlayerModel player) {
        playerItems.add(player);
    }

    public void removePlayer(PlayerModel player) {
        playerItems.remove(player);
    }

    public void removePlayers(List<PlayerModel> players) {
        for (PlayerModel player : players) {
            playerItems.remove(player);
        }
    }

    public void clear() {
        playerItems.clear();
    }
}
