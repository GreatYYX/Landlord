package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.TablePane;
import com.watch0ut.landlord.command.concrete.ReadyCommand;
import com.watch0ut.landlord.object.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by Jack on 16/3/3.
 */
public class TablePaneController {

    private MainApplication application;
    private TablePane tablePane;

    public TablePaneController(MainApplication application, TablePane tablePane) {
        this.application = application;
        this.tablePane = tablePane;
        this.tablePane.setOnStart(new StartHandler());
    }

    public void addPlayer(Player player) {
        PlayerModel playerModel = new PlayerModel(
                player.getId(),
                player.getPhoto(),
                player.getNickName(),
                player.getScore()
        );
        tablePane.addPlayer(playerModel);
    }

    public void selfSeat(Player player) {
        tablePane.setPlayerInfo(player);
        addPlayer(player);
        tablePane.selfSeat(player);
    }

    public void selfReady() {
        tablePane.selfReady();
    }

    public void allReady() {
        tablePane.setState(TablePane.FULL);
        tablePane.updatePlayerPosition();
    }

    class StartHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            WClient.getInstance().sendCommand(new ReadyCommand());
        }
    }
}
