package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.PopupWindow;

/**
 * 登录面板
 *
 * Created by Jack on 16/3/5.
 */
public class SignInPane extends AnchorPane {

    private AvatarView logoView;
    private TextField usernameField;
    private Tooltip usernameTip;
    private PasswordField passwordField;
    private Tooltip passwordTip;
    private CheckBox rememberCheckBox;
    private CheckBox autoCheckBox;
    private Button loginButton;
    private Hyperlink signUpLink;
    private Hyperlink resetPasswordLink;

    private LoginButtonEventHandler handler = new LoginButtonEventHandler();

    public SignInPane() {
        setPrefSize(280, 310);

        logoView = new AvatarView(AvatarView.IDLE, AvatarView.BIG);
        logoView.setLayoutX(92);
        logoView.setLayoutY(12);
        getChildren().add(logoView);

        usernameField = new TextField("root@example.com");
        usernameField.setPrefSize(220, 40);
        usernameField.setPromptText("用户名");
        usernameField.setLayoutX(30);
        usernameField.setLayoutY(120);
        getChildren().add(usernameField);

        usernameTip = new Tooltip("请输入用户名");
        usernameField.setTooltip(usernameTip);

        passwordField = new PasswordField();
        passwordField.setText("123456");
        passwordField.setPrefSize(220, 40);
        passwordField.setPromptText("密码");
        passwordField.setLayoutX(30);
        passwordField.setLayoutY(160);
        getChildren().add(passwordField);

        passwordTip = new Tooltip("请输入密码");
        passwordField.setTooltip(passwordTip);

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

    public void setUsername(String username) {
        usernameField.setText(username);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public String getPassword() {
        return passwordField.getText().trim();
    }

    public void setRememberCheckBox(boolean value) {
        rememberCheckBox.setSelected(value);
    }

    public boolean isRememberPassword() {
        return rememberCheckBox.isSelected();
    }

    public void setAutoLogin(boolean value) {
        autoCheckBox.setSelected(value);
    }

    public boolean isAutoLogin() {
        return autoCheckBox.isSelected();
    }

    public void showUsernameTip(String tip) {
        usernameTip.setText(tip);
        double x = usernameTip.getAnchorX();
        double y = usernameTip.getAnchorY();
        usernameTip.show(usernameField, x, y);
    }

    public void showPasswordTip(String tip) {
        passwordTip.setText(tip);
        double x = passwordTip.getAnchorX();
        double y = passwordTip.getAnchorY();
        passwordTip.show(passwordField, x, y);
    }

    public void onLogin() {
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        rememberCheckBox.setDisable(true);
        autoCheckBox.setDisable(true);
        loginButton.setText("登录中...");
        loginButton.addEventHandler(MouseEvent.MOUSE_ENTERED, handler);
        loginButton.addEventHandler(MouseEvent.MOUSE_EXITED, handler);
    }

    public void onNormal() {
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        rememberCheckBox.setDisable(false);
        autoCheckBox.setDisable(false);
        loginButton.setText("登录");
        loginButton.removeEventHandler(MouseEvent.MOUSE_ENTERED, handler);
        loginButton.removeEventHandler(MouseEvent.MOUSE_EXITED, handler);
    }

    class LoginButtonEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
                loginButton.setText("取消");
            } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
                loginButton.setText("登录中...");
            }
        }
    }

    public void setOnLogin(EventHandler<ActionEvent> handler) {
        loginButton.setOnAction(handler);
    }

    public void setOnSingUp(EventHandler<ActionEvent> handler) {
        signUpLink.setOnAction(handler);
    }

    public void setOnResetPassword(EventHandler<ActionEvent> handler) {
        resetPasswordLink.setOnAction(handler);
    }
}
