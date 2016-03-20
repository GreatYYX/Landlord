package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private IntegerProperty topState;
    private IntegerProperty leftState;
    private IntegerProperty bottomState;
    private IntegerProperty rightState;

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

        topState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        leftState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        bottomState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        rightState = new SimpleIntegerProperty(Player.STATE.Idle.getValue());
        StateChangeListener listener = new StateChangeListener();
        topState.addListener(listener);
        leftState.addListener(listener);
        bottomState.addListener(listener);
        rightState.addListener(listener);
    }

    public IntegerProperty topStateProperty() {
        return topState;
    }

    public IntegerProperty leftStateProperty() {
        return leftState;
    }

    public IntegerProperty bottomStateProperty() {
        return bottomState;
    }

    public IntegerProperty rightStateProperty() {
        return rightState;
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

    private void topStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (!oldState.equals(Player.STATE.Idle) && newState.equals(Player.STATE.Idle)) {
            // 玩家离座
            topState.unbind();
            setTopIdle();
            return;
        }

        if (oldState.equals(Player.STATE.Seated) && newState.equals(Player.STATE.Ready)) {
            // 玩家准备
            setTopReady();
            return;
        }

    }

    private void bottomStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (!oldState.equals(Player.STATE.Idle) && newState.equals(Player.STATE.Idle)) {
            // 玩家离座
            bottomState.unbind();
            setBottomIdle();
            return;
        }

        if (oldState.equals(Player.STATE.Seated) && newState.equals(Player.STATE.Ready)) {
            // 玩家准备
            setBottomReady();
            return;
        }
    }

    private void leftStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (!oldState.equals(Player.STATE.Idle) && newState.equals(Player.STATE.Idle)) {
            // 玩家离座
            leftState.unbind();
            setLeftIdle();
            return;
        }

        if (oldState.equals(Player.STATE.Seated) && newState.equals(Player.STATE.Ready)) {
            // 玩家准备
            setLeftReady();
            return;
        }
    }

    private void rightStateChange(Integer oldValue, Integer newValue) {
        Player.STATE oldState = Player.STATE.getState(oldValue);
        Player.STATE newState = Player.STATE.getState(newValue);
        if (!oldState.equals(Player.STATE.Idle) && newState.equals(Player.STATE.Idle)) {
            // 玩家离座
            rightState.unbind();
            setRightIdle();
            return;
        }

        if (oldState.equals(Player.STATE.Seated) && newState.equals(Player.STATE.Ready)) {
            // 玩家准备
            setRightReady();
            return;
        }
    }

    private boolean isAllReady() {
        Player.STATE top = Player.STATE.getState(topState.getValue());
        if (!top.equals(Player.STATE.Ready))
            return false;
        Player.STATE bottom = Player.STATE.getState(bottomState.getValue());
        if (!bottom.equals(Player.STATE.Ready))
            return false;
        Player.STATE left = Player.STATE.getState(leftState.getValue());
        if (!left.equals(Player.STATE.Ready))
            return false;
        Player.STATE right = Player.STATE.getState(rightState.getValue());
        if (!right.equals(Player.STATE.Ready))
            return false;
        return true;
    }

    private boolean hasIdle() {
        if (topState.getValue() == Player.STATE.Idle.getValue())
            return true;
        if (bottomState.getValue() == Player.STATE.Idle.getValue())
            return true;
        if (leftState.getValue() == Player.STATE.Idle.getValue())
            return true;
        if (rightState.getValue() == Player.STATE.Idle.getValue())
            return true;
        return false;
    }

    class StateChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            IntegerProperty source = (IntegerProperty)observable;
            if (source.equals(topState)) {
                topStateChange((Integer) oldValue, (Integer) newValue);
            } else if (source.equals(bottomState)) {
                bottomStateChange((Integer) oldValue, (Integer) newValue);
            } else if (source.equals(leftState)) {
                leftStateChange((Integer) oldValue, (Integer) newValue);
            } else if (source.equals(rightState)) {
                rightStateChange((Integer) oldValue, (Integer) newValue);
            }
            if (isAllReady()) {
                setTablePlay();
            }
            if (hasIdle()) {
                setTableNormal();
            }
        }
    }
}
