package com.watch0ut.landlord.client.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

/**
 * 秒表，默认显示20。
 *
 * Created by Jack on 16/2/24.
 */
public class StopwatchPane extends StackPane {
    private static final String CLOCK_PATH = "icon/widget/clock.png";

    private ImageView background;
    private Label numberLabel;

    public StopwatchPane() {
        setMinSize(56, 56);
        setMaxSize(56, 56);

        background = new ImageView();
        background.setFitWidth(56);
        background.setFitHeight(56);
        try {
            Image image = new Image(getClass().getResourceAsStream(CLOCK_PATH));
            background.setImage(image);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        numberLabel = new Label("20");
        numberLabel.setFont(Font.font("System Bold", 30));

        getChildren().addAll(background, numberLabel);
    }

    public void updateTime(int time) {
        numberLabel.setText(time + "");
    }

    public void bindTimeProperty(ObservableValue time) {
        numberLabel.textProperty().bind(time);
    }

    public void unbindTimeProperty() {
        numberLabel.textProperty().unbind();
    }

}
