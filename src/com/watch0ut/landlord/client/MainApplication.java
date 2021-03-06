package com.watch0ut.landlord.client;

import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.client.controller.TableController;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.service.WClientHandler;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.client.view.SignInPane;
import com.watch0ut.landlord.client.view.TablePane;
import com.watch0ut.landlord.command.concrete.UnseatCommand;
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
    private TableController tableController;

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
        hallStage.setOnCloseRequest(new HallCloseHandler());

        tableStage = new Stage();
        TablePane tablePane = new TablePane();
        tableController = new TableController(this, tablePane);
        tableStage.setScene(new Scene(tablePane, 1164, 694));
        tableStage.setResizable(false);
        tableStage.setOnCloseRequest(new TableCloseHandler());

        hallController.setTableController(tableController);
        handler.setSignInController(new SignInController(this, signInPane));
        handler.setHallController(hallController);
        handler.setTableController(tableController);
        WClient.getInstance().setHandler(handler);
    }

    public void showHall() {
        hallStage.show();
    }

    public void showTable() {
        tableStage.show();
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
        if (tableStage.isShowing())
            tableStage.close();
        WClient client = WClient.getInstance();
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class HallCloseHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                if (tableStage.isShowing())
                    tableStage.close();
            }
        }
    }

    class TableCloseHandler implements EventHandler<WindowEvent> {
        @Override
        public void handle(WindowEvent event) {
            if (event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                tableController.selfUnSeat();
                WClient.getInstance().sendCommand(new UnseatCommand());
            }
        }
    }
}
