package com.watch0ut.landlord.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 登录对话框的控制器，登录等操作在此实现
 * Created by Jack on 16/2/18.
 */
public class SignInController {
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private CheckBox rememberCheckBox;
    @FXML
    private CheckBox autoLoginCheckBox;
    @FXML
    private Button loginButton;

    @FXML
    private void login() {
        usernameText.setText("哈哈！！！");
    }
}
