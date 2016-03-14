package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.client.view.MiniTablePane;
import com.watch0ut.landlord.command.concrete.LogoutCommand;
import com.watch0ut.landlord.object.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 大厅控制器
 *
 * Created by Jack on 16/3/6.
 */
public class HallController {

    private PlayerModel self;
    private HallPane hallPane;
    private MainApplication application;

    private PlayerListController playerListController;
    private List<MiniTablePaneController> miniTablePaneControllers;
    private List<PlayerModel> playerModels;

    public HallController(Application application, HallPane pane) {
        this.application = (MainApplication) application;
        this.hallPane = pane;
        hallPane.setOnAbout(new AboutHandler());
        hallPane.setOnLogout(new LogoutHandler());
        hallPane.setOnExit(new ExitHandler());
        playerListController = new PlayerListController(hallPane.getPlayerListTable());
        List<MiniTablePane> miniTablePanes = hallPane.getTableList();
        miniTablePaneControllers = new ArrayList<>(miniTablePanes.size());
        for (int i = 0; i < miniTablePanes.size(); i++) {
            MiniTablePane miniTablePane = miniTablePanes.get(i);
            miniTablePaneControllers.add(new MiniTablePaneController(miniTablePane));
        }

        playerModels = new ArrayList<>();
    }

    public void updatePlayer(Player player) {
        self = new PlayerModel(
                player.getId(),
                player.getPhoto(),
                player.getNickName(),
                player.getScore()
        );
        playerModels.add(self);
        hallPane.updatePlayer(self);
    }

    private void updatePlayModel(PlayerModel playerModel, Player player) {
        if (!playerModel.getAvatar().equals(player.getPhoto())) {
            playerModel.setAvatar(player.getPhoto());
        }

        if (!playerModel.getNickName().equals(player.getNickName())) {
            playerModel.setNickName(player.getNickName());
        }

        if (playerModel.getScore() != player.getScore()) {
            playerModel.setScore(player.getScore());
        }
    }

    private void addPlayerModel(Player player) {
        PlayerModel playerModel = new PlayerModel(
                player.getId(),
                player.getPhoto(),
                player.getNickName(),
                player.getScore()
        );
        playerModels.add(playerModel);
        playerListController.addPlayer(playerModel);
    }

    public void updatePlayerList(List<Player> players) {
        for (int i = 0; i < playerModels.size(); i++) {
            PlayerModel playerModel = playerModels.get(i);
            for (int j = 0; j < players.size();) {
                Player player = players.get(j);
                if (playerModel.getId() == player.getId()) {
                    updatePlayModel(playerModel, player);
                    players.remove(j);
                } else {
                    j++;
                }
            }
        }
        for (int k = 0; k < players.size(); k++) {
            Player player = players.get(k);
            addPlayerModel(player);
        }
//        for (Player player : players) {
//            playerListController.updatePlayer(player);
//            if (self == null)
//                continue;
//            if (player.getId() == self.getId()) {
//                if (player.getState() == Player.STATE.Seated &&
//                        self.getState() == Player.STATE.Idle) {
//                    seat(player);
//                } else if (player.getState() == Player.STATE.Idle &&
//                        self.getState() != Player.STATE.Idle) {
//                    unseat(player);
//                } else if (player.getState() == Player.STATE.Ready &&
//                        self.getState() == Player.STATE.Seated) {
//                    ready(player);
//                }
//
//            }
//        }
    }

    private void seat(Player player) {
        MiniTablePaneController miniTablePaneController = miniTablePaneControllers.get(player.getTableId());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                self.setTableId(player.getTableId());
//                self.setTablePosition(player.getTablePosition());
//                self.setState(player.getState());
                miniTablePaneController.seat(player);
                application.showTable(player);
            }
        });
    }

    private void unseat(Player player) {
//        MiniTablePaneController miniTablePaneController = miniTablePaneControllers.get(self.getTableId());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                miniTablePaneController.unseat(self);
//                self.setTableId(player.getTableId());
//                self.setTablePosition(player.getTablePosition());
//                self.setState(player.getState());
            }
        });
    }

    private void ready(Player player) {
//        MiniTablePaneController miniTablePaneController = miniTablePaneControllers.get(self.getTableId());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                miniTablePaneController.ready(player);
//                self.setState(player.getState());

            }
        });
    }

    public void onSeatSucceed() {
        for (int i = 0; i < miniTablePaneControllers.size(); i++) {
            MiniTablePaneController miniTablePaneController = miniTablePaneControllers.get(i);
            miniTablePaneController.removeMouseClickedHandler();
        }
    }

    public void about() {

    }

    public void logout() {
        playerModels.clear();
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
