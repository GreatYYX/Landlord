package com.watch0ut.landlord.client;

import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.client.controller.TablePaneController;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.service.WClientHandler;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.client.view.SignInPane;
import com.watch0ut.landlord.client.view.TablePane;
import com.watch0ut.landlord.command.concrete.UnseatCommand;
import com.watch0ut.landlord.object.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 主程序
 *
 * Created by Jack on 16/3/5.
 */
public class MainApplication extends Application {
    private Stage signInStage;
    private Stage hallStage;
    private Stage tableStage;

    private HallController hallController;
    private TablePaneController tablePaneController;

    @Override
    public void init() throws Exception {
        super.init();
    }

    public void initStage(Stage stage) {
        WClientHandler handler = new WClientHandler();
        signInStage = stage;
        signInStage.setTitle("登录");
        SignInPane signInPane = new SignInPane();
        signInStage.setScene(new Scene(signInPane, 280, 310));
        signInStage.setResizable(false);
        signInStage.show();

        hallStage = new Stage();
        hallStage.setTitle("大厅");
        HallPane hallPane = new HallPane();
        hallController = new HallController(this, hallPane);
        hallStage.setScene(new Scene(hallPane, 800, 620));

        tableStage = new Stage();
        TablePane tablePane = new TablePane();
        tablePaneController = new TablePaneController(this, tablePane);
        tableStage.setScene(new Scene(tablePane, 1164, 694));
        tableStage.setResizable(false);
        tableStage.setOnCloseRequest(new CloseRequestHandler());

        handler.setSignInController(new SignInController(this, signInPane));
        handler.setHallController(hallController);
        WClient.getInstance().setHandler(handler);
    }

    public void showTable() {
        tableStage.show();
    }

    public void showHall(Player player) {
        hallController.updatePlayer(player);
        hallStage.show();
    }

    public void showSignIn() {
        signInStage.show();
    }

    public void closeSignIn() {
        signInStage.close();
    }

    public void closeHall() {
        hallStage.close();
    }

    public void closeTable() {
        tableStage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initStage(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        WClient client = WClient.getInstance();
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class CloseRequestHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                WClient.getInstance().sendCommand(new UnseatCommand());
            }
        }
    }
}
