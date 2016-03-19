package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.TablePane;
import com.watch0ut.landlord.command.concrete.ReadyCommand;
import com.watch0ut.landlord.object.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

/**
 * Created by Jack on 16/3/3.
 */
public class TableController {

    private PlayerModel self;
    private MainApplication application;
    private TablePane tablePane;

    public TableController(MainApplication application, TablePane tablePane) {
        this.application = application;
        this.tablePane = tablePane;
        this.tablePane.setOnStart(new StartHandler());

    }

    public void show() {
        application.showTable();
    }

    public void addPlayers(List<PlayerModel> playerModels) {
        for (int i = 0; i < playerModels.size(); i++) {
            PlayerModel playerModel = playerModels.get(i);
            if (self.getId() != playerModel.getId() && self.getTableId() == playerModel.getTableId()) {
                tablePane.addPlayer(playerModel);
            }
        }
    }

    public void clearPlayers() {
        tablePane.clearPlayers();
    }

    public void selfSeat(PlayerModel playerModel) {
        self = playerModel;
        tablePane.setPlayerInfo(playerModel);
        tablePane.addPlayer(playerModel);
        tablePane.selfSeat(playerModel);
    }

    public void selfUnSeat() {
        self = null;
        tablePane.clearPlayers();
        tablePane.selfUnSeat();
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
