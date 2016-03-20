package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.object.Player;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

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

    private final static int OPPOSITE_READY_X = CENTER_X - PlayerPane.WIDTH / 2;
    private final static int OPPOSITE_READY_Y = CENTER_Y - HALF_INNER_SQUARE - PlayerPane.HEIGHT / 2;
    private final static int OPPOSITE_PLAY_X = CENTER_X + 148;
    private final static int OPPOSITE_PLAY_Y = 42;

    private int state;

    private PlayerPane selfPane;
    private PlayerPane earlyPane;
    private PlayerPane latePane;
    private PlayerPane oppositePane;

    private StartButton startButton;

    private PlayerInfoPane playerInfoPane;
    private PlayerListTable playerListTable;
    private ObservableList<PlayerModel> playerModels;
    private ChatPane chatPane;

    private PlayerModel self;
    private PlayerModel early;
    private PlayerModel late;
    private PlayerModel opposite;

    private IntegerProperty selfState;
    private IntegerProperty earlyState;
    private IntegerProperty lateState;
    private IntegerProperty oppositeState;

    public TablePane() {
        setMinSize(1164, 694);
        setMaxSize(1164, 694);

        state = LACK;

        initializeCenter();
        initializeRight();

        selfState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        earlyState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        lateState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        oppositeState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        StateChangeListener listener = new StateChangeListener();
        selfState.addListener(listener);
        earlyState.addListener(listener);
        lateState.addListener(listener);
        oppositeState.addListener(listener);
    }

    private void initializeCenter() {
        AnchorPane frontPane = new AnchorPane();
        frontPane.setPrefSize(CENTER_WIDTH, CENTER_HEIGHT);
        
        selfPane = new PlayerPane();
        selfPane.setLayoutX(SELF_READY_X);
        selfPane.setLayoutY(SELF_READY_Y);
        frontPane.getChildren().addAll(selfPane);

        earlyPane = new PlayerPane();
        earlyPane.setLayoutX(EARLY_READY_X);
        earlyPane.setLayoutY(EARLY_READY_Y);
        frontPane.getChildren().add(earlyPane);

        latePane = new PlayerPane();
        latePane.setLayoutX(LATE_READY_X);
        latePane.setLayoutY(LATE_READY_Y);
        frontPane.getChildren().add(latePane);

        oppositePane = new PlayerPane();
        oppositePane.setLayoutX(OPPOSITE_READY_X);
        oppositePane.setLayoutY(OPPOSITE_READY_Y);
        frontPane.getChildren().add(oppositePane);

        startButton = new StartButton();
        updateStartButtonPosition();
        frontPane.getChildren().add(startButton);

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
        playerModels = FXCollections.observableArrayList();
        playerListTable.setItems(playerModels);

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
        startButton.setVisible(true);
    }

    public void updatePlayerPosition() {
        if (state == FULL) {
//            selfPane.play();
            selfPane.setLayoutX(SELF_PLAY_X);
            selfPane.setLayoutY(SELF_PLAY_Y);

            earlyPane.setLayoutX(EARLY_PLAY_X);
            earlyPane.setLayoutY(EARLY_PLAY_Y);

            latePane.setLayoutX(LATE_PLAY_X);
            latePane.setLayoutY(LATE_PLAY_Y);

            oppositePane.setLayoutX(OPPOSITE_PLAY_X);
            oppositePane.setLayoutY(OPPOSITE_PLAY_Y);
        }
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setOnStart(EventHandler<ActionEvent> handler) {
        startButton.setOnAction(handler);
    }

    public void setPlayerInfo(PlayerModel playerModel) {
        playerInfoPane.bind(playerModel);
    }

    public void addPlayer(PlayerModel playerModel) {
        playerModels.add(playerModel);
        int delta = (playerModel.getTablePosition() - self.getTablePosition()) % 4;
        switch (delta) {
            case 1:
                lateSeat(playerModel);
                break;
            case 2:
                oppositeSeat(playerModel);
                break;
            case 3:
                earlySeat(playerModel);
                break;
        }
    }

    public void clearPlayers() {
        playerModels.clear();
    }

    public void selfSeat(PlayerModel playerModel) {
        self = playerModel;
        selfPane.seat(playerModel.getAvatar(), playerModel.getNickName());
        startButton.setVisible(true);
        selfState.bind(playerModel.stateProperty());
    }

    public void selfUnSeat() {
        self = null;
        playerInfoPane.unbind();
        selfState.unbind();
        selfPane.unseat();
    }

    public void selfReady() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                selfPane.ready();
                startButton.setVisible(false);
            }
        });
    }

    public void earlySeat(PlayerModel playerModel) {
        early = playerModel;
        earlyPane.seat(playerModel.getAvatar(), playerModel.getNickName());
        earlyState.bind(playerModel.stateProperty());
    }

    public void earlyUnSeat() {
        early = null;
        earlyState.unbind();
        earlyPane.unseat();
    }

    public void earlyReady() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                earlyPane.ready();
            }
        });
    }

    public void lateSeat(PlayerModel playerModel) {
        late = playerModel;
        latePane.seat(playerModel.getAvatar(), playerModel.getNickName());
        lateState.bind(playerModel.stateProperty());
    }

    public void lateUnSeat() {
        late = null;
        lateState.unbind();
        latePane.unseat();
    }

    public void lateReady() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                latePane.ready();
            }
        });
    }

    public void oppositeSeat(PlayerModel playerModel) {
        opposite = playerModel;
        oppositePane.seat(playerModel.getAvatar(), playerModel.getNickName());
        oppositeState.bind(playerModel.stateProperty());
    }

    public void oppositeUnSeat() {
        opposite = null;
        oppositeState.unbind();
        oppositePane.unseat();
    }

    public void oppositeReady() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                oppositePane.ready();
            }
        });
    }

    public void onSelfStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (oldState.equals(Player.STATE.Seated) &&
                newState.equals(Player.STATE.Ready)) {
            selfReady();
        }
    }

    public void onEarlyStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (oldState.equals(Player.STATE.Seated) &&
                newState.equals(Player.STATE.Ready)) {
            earlyReady();
        }
    }

    public void onLateStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (oldState.equals(Player.STATE.Seated) &&
                newState.equals(Player.STATE.Ready)) {
            lateReady();
        }
    }

    public void onOppositeStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (oldState.equals(Player.STATE.Seated) &&
                newState.equals(Player.STATE.Ready)) {
            oppositeReady();
        }
    }

//    public boolean isAllReady() {
//        if (selfState.getValue() == Player.STATE.Idle.getValue() ||
//                selfState.getValue() == Player.STATE.Seated.getValue())
//            return false;
//        if (earlyState.getValue() == Player.STATE.Idle.getValue() ||
//                earlyState.getValue() == Player.STATE.Seated.getValue())
//            return false;
//        if (lateState.getValue() == Player.STATE.Idle.getValue() ||
//                lateState.getValue() == Player.STATE.Seated.getValue())
//            return false;
//        if (oppositeState.getValue() == Player.STATE.Idle.getValue() ||
//                oppositeState.getValue() == Player.STATE.Seated.getValue())
//            return false;
//        return true;
//    }

    private boolean isAllReady() {
        if (selfState.getValue() != Player.STATE.Ready.getValue())
            return false;
        if (earlyState.getValue() != Player.STATE.Ready.getValue())
            return false;
        if (lateState.getValue() != Player.STATE.Ready.getValue())
            return false;
        if (oppositeState.getValue() != Player.STATE.Ready.getValue())
            return false;
        return true;
    }

    class StateChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            IntegerProperty state = (IntegerProperty) observable;
            if (state.equals(selfState)) {
                onSelfStateChange(oldValue.intValue(), newValue.intValue());
            } else if (state.equals(earlyState)) {
                onEarlyStateChange(oldValue.intValue(), newValue.intValue());
            } else if (state.equals(lateState)) {
                onLateStateChange(oldValue.intValue(), newValue.intValue());
            } else if (state.equals(oppositeState)) {
                onOppositeStateChange(oldValue.intValue(), newValue.intValue());
            }

            if (isAllReady()) {

            }
        }
    }
}
