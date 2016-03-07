package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.view.PlayerListTable;
import com.watch0ut.landlord.object.Player;
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

    public void addPlayers(List<Player> players) {
        for (Player player : players) {
            addPlayer(player);
        }
    }

    public void addPlayer(Player player) {
        playerItems.add(new PlayerModel(
                player.getId(),
                player.getPhoto(),
                player.getNickName(),
                player.getScore()
        ));
    }

    public void removePlayer(Player player) {
        for (int i = 0; i < playerItems.size(); i++) {
            PlayerModel playerModel = playerItems.get(i);
            if (playerModel.getId() == player.getId()) {
                playerItems.remove(i);
            }
        }
    }

    public void removePlayers(List<Player> players) {
        for (Player player : players) {
            removePlayer(player);
        }
    }

    public void clear() {
        playerItems.clear();
    }

    public void updatePlayer(Player player) {
        int i = 0;
        for (; i < playerItems.size(); i++) {
            PlayerModel playerModel = playerItems.get(i);
            if (playerModel.getId() == player.getId()) {
                playerModel.setAvatar(player.getPhoto());
                playerModel.setNickName(player.getNickName());
                playerModel.setScore(player.getScore());
            }
        }
        if (i == playerItems.size()) {
            addPlayer(player);
        }
    }
}
