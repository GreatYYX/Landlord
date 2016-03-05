package com.watch0ut.landlord.client;

import com.watch0ut.landlord.client.controller.SignInController;
import com.watch0ut.landlord.client.view.SignInPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主程序
 *
 * Created by Jack on 16/3/5.
 */
public class MainApplication extends Application {
    private Stage currentStage;

    public void setStage(Stage stage) {
        currentStage.close();
        currentStage = stage;
        currentStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentStage = primaryStage;
        currentStage.setTitle("登录");
        SignInPane signInPane = new SignInPane();
        SignInController signInController = new SignInController(this, signInPane);
        currentStage.setScene(new Scene(signInPane, 280, 310));
        currentStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
