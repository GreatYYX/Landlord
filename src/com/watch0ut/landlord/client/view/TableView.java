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
public class TableView extends StackPane {
    private Image readyImage;
    private Image tableImage;
    private Image playingTableImage;

    private ImageView backgroundImage;
    private GridPane gridPane;
    private ImageView topReadyImage;
    private ImageView leftReadyImage;
    private ImageView bottomReadyImage;
    private ImageView rightReadyImage;

    public TableView() {
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

        topReadyImage = new ImageView();
        topReadyImage.setFitWidth(16);
        topReadyImage.setFitHeight(16);
        gridPane.add(topReadyImage, 1, 0);

        leftReadyImage = new ImageView();
        leftReadyImage.setFitWidth(16);
        leftReadyImage.setFitHeight(16);
        gridPane.add(leftReadyImage, 0, 1);

        bottomReadyImage = new ImageView();
        bottomReadyImage.setFitWidth(16);
        bottomReadyImage.setFitHeight(16);
        gridPane.add(bottomReadyImage, 1, 2);

        rightReadyImage = new ImageView();
        rightReadyImage.setFitWidth(16);
        rightReadyImage.setFitHeight(16);
        gridPane.add(rightReadyImage, 2, 1);

        getChildren().add(gridPane);

    }

    public void setTopReady() {
        topReadyImage.setImage(readyImage);
    }

    public void setTopIdle() {
        topReadyImage.setImage(null);
    }

    public void setLeftReady() {
        leftReadyImage.setImage(readyImage);
    }

    public void setLeftIdle() {
        leftReadyImage.setImage(null);
    }

    public void setBottomReady() {
        bottomReadyImage.setImage(readyImage);
    }

    public void setBottomIdle() {
        bottomReadyImage.setImage(null);
    }

    public void setRightReady() {
        rightReadyImage.setImage(readyImage);
    }

    public void setRightIdle() {
        rightReadyImage.setImage(null);
    }

    public void setTablePlay() {
        backgroundImage.setImage(playingTableImage);
    }

    public void setTableNormal() {
        backgroundImage.setImage(tableImage);
    }
}
