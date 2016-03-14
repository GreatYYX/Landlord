package com.watch0ut.landlord.client.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 主程序测试类
 *
 * Created by Jack on 16/2/13.
 */
public class TestApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        testPanes(primaryStage);
        primaryStage.show();
    }

    private void testPanes(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Landlord");

        // 测试SignInPane
//        primaryStage.setScene(new Scene(new SignInPane(), 280, 310));

        // 测试ActionView
//        primaryStage.setScene(new Scene(TestActionView.initialize(), 130, 40));

        // 测试AvatarView
//        primaryStage.setScene(new Scene(TestAvatarView.initialize(), 400, 130));

        // 测试AvatarPane
//        primaryStage.setScene(new Scene(TestAvatarPane.initialize(), 400, 200));

        // 测试TableView
//        primaryStage.setScene(new Scene(TestTableView.initialize(), 150, 200));

        // 测试PlayerListTable
        primaryStage.setScene(new Scene(TestPlayerListTable.initialize(), 250, 300));

        // 测试HallPane
//        primaryStage.setScene(new Scene(new HallPane(), 800, 620));

        // 测试PlayedCardPane
//        primaryStage.setScene(new Scene(TestPlayedCardPane.initialize(), 150, 105));

        // 测试SelfHandPane
//        primaryStage.setScene(new Scene(TestSelfHandPane.initialize(), 500, 250));

        // 测试OtherHandPane
//        primaryStage.setScene(new Scene(TestOtherHandPane.initialize(), 400, 400));

        // 测试MiniTablePane
//        primaryStage.setScene(new Scene(TestMiniTablePane.initialize(), 540, 600));

        // 测试CardView
//        primaryStage.setScene(new Scene(TestCardView.initialize(), 400, 400));

        // 测试PlayerInfoPane
//        primaryStage.setScene(new Scene(TestPlayerInfoPane.initialize(), 240, 100));

        // 测试EffectTextLabel
//        primaryStage.setScene(new Scene(TestMarkLabel.initialize(), 150, 200));

        // 测试PlayerPane
//        primaryStage.setScene(new Scene(TestPlayerPane.initialize(), 150, 360));

        // 测试StopwatchPane
//        primaryStage.setScene(new Scene(TestStopwatchPane.initialize(), 150, 150));

        // 测试ReadyButton和OperatePane
//        primaryStage.setScene(new Scene(TestButtons.initialize(), 144, 100));

        // 测试ChatPane
//        primaryStage.setScene(new Scene(TestChatPane.initialize(), 240, 450));

        // 测试TablePane
//        primaryStage.setScene(new Scene(TestTablePane.initialize(), 1164, 694));

//        primaryStage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
