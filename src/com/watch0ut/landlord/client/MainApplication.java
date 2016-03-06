package com.watch0ut.landlord.client;

import com.watch0ut.landlord.client.controller.HallController;
import com.watch0ut.landlord.client.controller.SignInController;
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
    
    public void initSignInStage(Stage stage) {
        signInStage = stage;
        signInStage.setTitle("登录");
        SignInPane signInPane = new SignInPane();
        new SignInController(this, signInPane);
        signInStage.setScene(new Scene(signInPane, 280, 310));
        signInStage.setResizable(false);
        signInStage.show();
    }

    public void initHallStage(Player player) {
        hallStage = new Stage();
        hallStage.setTitle("大厅");
        HallPane hallPane = new HallPane(player);
        new HallController(this, hallPane);
        hallStage.setScene(new Scene(hallPane, 800, 620));
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
        initSignInStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
