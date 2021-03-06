package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.SignInPane;
import com.watch0ut.landlord.command.concrete.LoginCommand;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
        this.signInPane.setOnLogin(new LoginHandler());
        this.signInPane.setOnSingUp(new SignUpHandler());
        this.signInPane.setOnResetPassword(new ResetPasswordHandler());
    }

    private void login() {
        String username = signInPane.getUsername();
        if (username.isEmpty()) {
            signInPane.showUsernameTip("用户名不能为空！");
            return;
        }
        String password = signInPane.getPassword();
        if (password.isEmpty()) {
            signInPane.showPasswordTip("密码不能为空！");
        }
        signInPane.onLogin();
        WClient client = WClient.getInstance();
        if (!client.isConnected())
            client.connect();
        client.sendCommand(new LoginCommand(username, password));
    }

    public void onLoginSucceeded() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                signInPane.onNormal();
                application.closeSignIn();
                application.showHall();
            }
        });
    }

    public void onLoginFailed(String error) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                signInPane.onNormal();
                signInPane.showUsernameTip(error);
            }
        });

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

    class ResetPasswordHandler implements EventHandler<ActionEvent> {
        private final String uri = "http://landlord.watch0ut.com";

        @Override
        public void handle(ActionEvent event) {
            application.getHostServices().showDocument(uri);
        }
    }
}


