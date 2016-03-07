package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.object.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.List;

/**
 * 大厅控制器
 *
 * Created by Jack on 16/3/6.
 */
public class HallController {

    private HallPane hallPane;
    private MainApplication application;

    private PlayerListController playerListController;

    public HallController(Application application, HallPane pane) {
        this.application = (MainApplication) application;
        this.hallPane = pane;
        hallPane.setOnAbout(new AboutHandler());
        hallPane.setOnLogout(new LogoutHandler());
        hallPane.setOnExit(new ExitHandler());
        playerListController = new PlayerListController(hallPane.getPlayerListTable());
    }

    public void updatePlayer(Player player) {
        hallPane.updatePlayer(player);
    }

    public void updatePlayerList(List<Player> players) {
        for (Player player : players) {
            playerListController.updatePlayer(player);
        }
    }

    public void about() {

    }

    public void logout() {
        playerListController.clear();
        WClient client = WClient.getInstance();
        client.sendCommand(new LogoutCommand());
        application.closeHall();
        application.showSignIn();
    }

    public void exit() {
        WClient client = WClient.getInstance();
        client.sendCommand(new LogoutCommand());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.disconnect();
        application.closeHall();
    }

    class AboutHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            about();
        }
    }

    class LogoutHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logout();
        }
    }

    class ExitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            exit();
        }
    }
}
