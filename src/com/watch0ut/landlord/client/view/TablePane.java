package com.watch0ut.landlord.client.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * 桌子，还提供显示玩家状态，当所有玩家都准备完毕，就进入PLAY状态
 *
 * Created by Jack on 16/2/20.
 */
public class TablePane extends StackPane {
    private Image readyImage;
    private Image tableImage;
    private Image playingTableImage;

    private ImageView backgroundImage;
    private GridPane gridPane;
    private ImageView upReadyImage;
    private ImageView leftReadyImage;
    private ImageView downReadyImage;
    private ImageView rightReadyImage;

    public TablePane() {
        setMinSize(48, 48);
        setMaxSize(48, 48);

        try {
            readyImage = new Image(getClass().getResourceAsStream("icon/widget/ready.png"));
            tableImage = new Image(getClass().getResourceAsStream("icon/widget/table_small.png"));
            playingTableImage = new Image(getClass().getResourceAsStream("icon/widget/table_small_play.png"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        backgroundImage = new ImageView(tableImage);
        backgroundImage.setFitWidth(48);
        backgroundImage.setFitHeight(48);
        getChildren().add(backgroundImage);

        gridPane = new GridPane();

        upReadyImage = new ImageView();
        upReadyImage.setFitWidth(16);
        upReadyImage.setFitHeight(16);
        gridPane.add(upReadyImage, 1, 0);

        leftReadyImage = new ImageView();
        leftReadyImage.setFitWidth(16);
        leftReadyImage.setFitHeight(16);
        gridPane.add(leftReadyImage, 0, 1);

        downReadyImage = new ImageView();
        downReadyImage.setFitWidth(16);
        downReadyImage.setFitHeight(16);
        gridPane.add(downReadyImage, 1, 2);

        rightReadyImage = new ImageView();
        rightReadyImage.setFitWidth(16);
        rightReadyImage.setFitHeight(16);
        gridPane.add(rightReadyImage, 2, 1);

        getChildren().add(gridPane);

    }

    public void setUpReady() {
        upReadyImage.setImage(readyImage);
    }

    public void setUpLeave() {
        upReadyImage.setImage(null);
    }

    public void setLeftReady() {
        leftReadyImage.setImage(readyImage);
    }

    public void setLeftLeave() {
        leftReadyImage.setImage(null);
    }

    public void setDownReady() {
        downReadyImage.setImage(readyImage);
    }

    public void setDownLeave() {
        downReadyImage.setImage(null);
    }

    public void setRightReady() {
        rightReadyImage.setImage(readyImage);
    }

    public void setRightLeave() {
        rightReadyImage.setImage(null);
    }

    public void setTablePlay() {
        backgroundImage.setImage(playingTableImage);
    }

    public void setTableNormal() {
        backgroundImage.setImage(tableImage);
    }
}
