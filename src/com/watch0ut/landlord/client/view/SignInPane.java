package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * 登录面板
 *
 * Created by Jack on 16/3/5.
 */
public class SignInPane extends AnchorPane {

    private AvatarView logoView;
    private TextField usernameField;
    private PasswordField passwordField;
    private CheckBox rememberCheckBox;
    private CheckBox autoCheckBox;
    private Button loginButton;
    private Hyperlink signUpLink;
    private Hyperlink resetPasswordLink;

    public SignInPane() {
        setPrefSize(280, 310);

        logoView = new AvatarView(AvatarView.IDLE, AvatarView.BIG);
        logoView.setLayoutX(92);
        logoView.setLayoutY(12);
        getChildren().add(logoView);

        usernameField = new TextField();
        usernameField.setPrefSize(220, 40);
        usernameField.setPromptText("用户名");
        usernameField.setLayoutX(30);
        usernameField.setLayoutY(120);
        getChildren().add(usernameField);

        passwordField = new PasswordField();
        passwordField.setPrefSize(220, 40);
        passwordField.setPromptText("密码");
        passwordField.setLayoutX(30);
        passwordField.setLayoutY(160);
        getChildren().add(passwordField);

        rememberCheckBox = new CheckBox("记住密码");
        rememberCheckBox.setLayoutX(30);
        rememberCheckBox.setLayoutY(210);
        autoCheckBox = new CheckBox("自动登录");
        autoCheckBox.setLayoutX(170);
        autoCheckBox.setLayoutY(210);
        getChildren().addAll(rememberCheckBox, autoCheckBox);

        loginButton = new Button("登录");
        loginButton.setPrefSize(220, 30);
        loginButton.setLayoutX(30);
        loginButton.setLayoutY(240);
        getChildren().add(loginButton);

        signUpLink = new Hyperlink("注册帐号");
        signUpLink.setLayoutX(30);
        signUpLink.setLayoutY(280);
        resetPasswordLink = new Hyperlink("重置密码");
        resetPasswordLink.setLayoutX(190);
        resetPasswordLink.setLayoutY(280);
        getChildren().addAll(signUpLink, resetPasswordLink);
    }

    public void setLoginHandler(EventHandler<ActionEvent> handler) {
        loginButton.setOnAction(handler);
    }

    public void setSingUpHandler(EventHandler<ActionEvent> handler) {
        signUpLink.setOnAction(handler);
    }

    public void setResetHandler(EventHandler<ActionEvent> handler) {
        resetPasswordLink.setOnAction(handler);
    }
}
