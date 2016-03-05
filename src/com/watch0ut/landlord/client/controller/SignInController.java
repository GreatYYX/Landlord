package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.view.HallPane;
import com.watch0ut.landlord.client.view.SignInPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 登录对话框的控制器，登录等操作在此实现
 *
 * Created by Jack on 16/2/18.
 */
public class SignInController {

    private MainApplication application;
    private SignInPane signInPane;

    public SignInController(Application application, SignInPane signInPane) {
        this.application = (MainApplication) application;
        this.signInPane = signInPane;
        this.signInPane.setLoginHandler(new LoginHandler());
        this.signInPane.setSingUpHandler(new SignUpHandler());
        this.signInPane.setResetHandler(new ResetHandler());
    }

    private void login() {
        Stage stage = new Stage();
        stage.setTitle("大厅");
        stage.setScene(new Scene(new HallPane(), 800, 600));
        application.setStage(stage);
    }

    class LoginHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            login();
        }
    }

    class SignUpHandler implements EventHandler<ActionEvent> {
        private final String uri = "http://landlord.watch0ut.com";

        @Override
        public void handle(ActionEvent event) {
            application.getHostServices().showDocument(uri);
        }
    }

    class ResetHandler implements EventHandler<ActionEvent> {
        private final String uri = "http://landlord.watch0ut.com";

        @Override
        public void handle(ActionEvent event) {
            application.getHostServices().showDocument(uri);
        }
    }
}
