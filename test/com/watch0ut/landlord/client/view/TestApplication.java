package com.watch0ut.landlord.client.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Created by Jack on 16/2/13.
 */
public class TestApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        AvatarView avatarView2 = new AvatarView("2.png", AvatarView.SMALL);
        AvatarView avatarView4 = new AvatarView("4.png", AvatarView.MIDDLE);
        AvatarView avatarView8 = new AvatarView("8.png", AvatarView.BIG);

        CardView rear = new CardView("Rear.png");
        CardView diamond4 = new CardView(0, 0);
        CardView smallRear = new CardView("Rear.png", true);

        AvatarPane avatarPane3A = new AvatarPane(ActionView.LANDLORD_3A, "J.png", "Jack");
        AvatarPane avatarPane = new AvatarPane(0, "Q.png", "Queen");

        FlowPane root = new FlowPane();
        root.getChildren().add(avatarView2);
        root.getChildren().add(avatarView4);
        root.getChildren().add(avatarView8);

        root.getChildren().add(smallRear);
        root.getChildren().add(rear);
        root.getChildren().add(diamond4);

        root.getChildren().add(avatarPane3A);
        root.getChildren().add(avatarPane);

        primaryStage.setTitle("Landlord");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
