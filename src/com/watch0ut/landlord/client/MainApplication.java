package com.watch0ut.landlord.client;

import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.service.WClientHandler;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.client.view.SignInPane;
import com.watch0ut.landlord.object.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主程序
 *
 * Created by Jack on 16/3/5.
 */
public class MainApplication extends Application {
    private Stage signInStage;
    private Stage hallStage;

    private HallController hallController;

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

        handler.setSignInController(new SignInController(this, signInPane));
        handler.setHallController(hallController);
        WClient.getInstance().setHandler(handler);
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
}
