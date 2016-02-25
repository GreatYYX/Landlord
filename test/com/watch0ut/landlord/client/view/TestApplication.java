package com.watch0ut.landlord.client.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主程序测试类
 *
 * Created by Jack on 16/2/13.
 */
public class TestApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Landlord");

        // 测试SignInPane
//        Parent signInPane = FXMLLoader.load(getClass().getResource("SignInPane.fxml"));
//        primaryStage.setScene(new Scene(signInPane, 280, 260));
//        primaryStage.setResizable(false);

        // 测试ActionView
//        primaryStage.setScene(new Scene(TestActionView.initialize(), 130, 40));

        // 测试AvatarView
//        primaryStage.setScene(new Scene(TestAvatarView.initialize(), 400, 130));

        // 测试AvatarPane
//        primaryStage.setScene(new Scene(TestAvatarPane.initialize(), 400, 200));

        // 测试TablePane
//        primaryStage.setScene(new Scene(TestTablePane.initialize(), 150, 200));

        // 测试CardTablePane
//        primaryStage.setScene(new Scene(TestCardTablePane.initialize(), 540, 600));

        // 测试CardView
//        primaryStage.setScene(new Scene(TestCardView.initialize(), 400, 400));

        // 测试PlayerInfoPane
//        primaryStage.setScene(new Scene(TestPlayerInfoPane.initialize(), 240, 100));

        // 测试EffectTextLabel
//        primaryStage.setScene(new Scene(TestMarkLabel.initialize(), 150, 200));

        // 测试StopwatchPane
        primaryStage.setScene(new Scene(TestStopwatchPane.initialize(), 150, 150));

        // 测试ChatPane
//        primaryStage.setScene(new Scene(TestChatPane.initialize(), 240, 450));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
