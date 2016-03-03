package com.watch0ut.landlord.client.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * 牌桌面板，包括玩家信息面板、玩家列表面板和聊天面板
 *
 * Created by Jack on 16/3/1.
 */
public class TablePane extends BorderPane {

    public final static int LACK = 0;
    public final static int FULL = 1;

    private final static String BG_PATH = "icon/widget/table_bg.png";
    private final static int CENTER_WIDTH = 924;
    private final static int CENTER_HEIGHT = 694;
    private final static int CENTER_X = 462;
    private final static int CENTER_Y = 347;
    private final static int HALF_INNER_SQUARE = 168;

    private final static int SELF_READY_X = CENTER_X - PlayerPane.WIDTH / 2;
    private final static int SELF_READY_Y = CENTER_Y + HALF_INNER_SQUARE - PlayerPane.HEIGHT / 2;
    private final static int SELF_PLAY_X = CENTER_X - 215 - PlayerPane.WIDTH;
    private final static int SELF_PLAY_Y = CENTER_HEIGHT - 180;

    private final static int EARLY_READY_X = CENTER_X - HALF_INNER_SQUARE - PlayerPane.WIDTH / 2;
    private final static int EARLY_READY_Y = CENTER_Y - PlayerPane.HEIGHT / 2;
    private final static int EARLY_PLAY_X = 10;
    private final static int EARLY_PLAY_Y = CENTER_Y - PlayerPane.HEIGHT / 2;

    private final static int LATE_READY_X = CENTER_X + HALF_INNER_SQUARE - PlayerPane.WIDTH / 2;
    private final static int LATE_READY_Y = CENTER_Y - PlayerPane.HEIGHT / 2;
    private final static int LATE_PLAY_X = CENTER_WIDTH - PlayerPane.WIDTH - 10;
    private final static int LATE_PLAY_Y = CENTER_Y - PlayerPane.HEIGHT / 2;

    private final static int RELATIVELY_READY_X = CENTER_X - PlayerPane.WIDTH / 2;
    private final static int RELATIVELY_READY_Y = CENTER_Y - HALF_INNER_SQUARE - PlayerPane.HEIGHT / 2;
    private final static int RELATIVELY_PLAY_X = CENTER_X + 148;
    private final static int RELATIVELY_PLAY_Y = 42;

    private int state;

    private PlayerPane self;
    private PlayerPane early;
    private PlayerPane late;
    private PlayerPane relatively;

    private StartButton startButton;

    private PlayerInfoPane playerInfoPane;
    private PlayerListTable playerListTable;
    private ChatPane chatPane;

    public TablePane() {
        setMinSize(1164, 694);
        setMaxSize(1164, 694);

        state = LACK;

        initializeCenter();
        initializeRight();

    }

    private void initializeCenter() {
        AnchorPane frontPane = new AnchorPane();
        frontPane.setPrefSize(CENTER_WIDTH, CENTER_HEIGHT);
        
        self = new PlayerPane();
        self.setLayoutX(SELF_READY_X);
        self.setLayoutY(SELF_READY_Y);
        frontPane.getChildren().addAll(self);

        early = new PlayerPane();
        early.setLayoutX(EARLY_READY_X);
        early.setLayoutY(EARLY_READY_Y);
        frontPane.getChildren().add(early);

        late = new PlayerPane();
        late.setLayoutX(LATE_READY_X);
        late.setLayoutY(LATE_READY_Y);
        frontPane.getChildren().add(late);

        relatively = new PlayerPane();
        relatively.setLayoutX(RELATIVELY_READY_X);
        relatively.setLayoutY(RELATIVELY_READY_Y);
        frontPane.getChildren().add(relatively);

        startButton = new StartButton();
        updateStartButtonPosition();
        frontPane.getChildren().add(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setState(TablePane.FULL);
                updatePlayerPosition();
            }
        });

        StackPane centerPane = new StackPane();
        ImageView background = new ImageView();
        background.setFitWidth(CENTER_WIDTH);
        background.setFitHeight(CENTER_HEIGHT);
        try {
            Image image = new Image(getClass().getResourceAsStream(BG_PATH));
            background.setImage(image);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        centerPane.getChildren().addAll(background, frontPane);
        setCenter(centerPane);
    }

    private void initializeRight() {
        playerInfoPane = new PlayerInfoPane();
        playerListTable = new PlayerListTable();
        playerListTable.setMinHeight(112);
        playerListTable.setPrefHeight(139);
        chatPane = new ChatPane();
        chatPane.setPrefSize(240, 477);
        VBox rightBox = new VBox(5);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.getChildren().addAll(playerInfoPane, playerListTable, chatPane);

        setRight(rightBox);
    }

    public void updateStartButtonPosition() {
        switch (state) {
            case FULL:
                break;
            case LACK:
                startButton.setLayoutX(CENTER_X - startButton.getPrefWidth() / 2);
                startButton.setLayoutY(CENTER_HEIGHT - startButton.getPrefHeight() - 20);
                break;
            default:
                startButton.setLayoutX(CENTER_X - startButton.getPrefWidth() / 2);
                startButton.setLayoutY(CENTER_HEIGHT - startButton.getPrefHeight() - 20);
        }
    }

    public void updatePlayerPosition() {
        if (state == FULL) {
//            self.play();
            self.setLayoutX(SELF_PLAY_X);
            self.setLayoutY(SELF_PLAY_Y);

            early.setLayoutX(EARLY_PLAY_X);
            early.setLayoutY(EARLY_PLAY_Y);

            late.setLayoutX(LATE_PLAY_X);
            late.setLayoutY(LATE_PLAY_Y);

            relatively.setLayoutX(RELATIVELY_PLAY_X);
            relatively.setLayoutY(RELATIVELY_PLAY_Y);
        }
    }

    public void setState(int state) {
        this.state = state;
    }
}
