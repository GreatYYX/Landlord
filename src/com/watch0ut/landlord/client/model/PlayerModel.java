package com.watch0ut.landlord.client.model;

import com.watch0ut.landlord.object.Table;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 玩家模型，用于玩家列表。
 *
 * Created by Jack on 16/2/27.
 */
public class PlayerModel {
//    public final static int IDLE = 0;
//    public final static int SEATED = 1;
//    public final static int READY = 2;
//    public final static int PLAY = 3;
//    public final static int WAIT = 4;
//    public final static int FINISH = 5;

    private int id;
    private int tablePosition;
    private final SimpleStringProperty avatar;
    private final SimpleStringProperty nickName;
    private final SimpleIntegerProperty score;
    private final SimpleStringProperty scoreString;
    private final SimpleIntegerProperty state;

    public PlayerModel(int id, String avatar, String nickName, int score) {
        this.id = id;
        this.tablePosition = Table.UNSEATED;
        this.avatar = new SimpleStringProperty(avatar);
        this.nickName = new SimpleStringProperty(nickName);
        this.score = new SimpleIntegerProperty(score);
        this.scoreString = new SimpleStringProperty(Integer.toString(score));
        this.state = new SimpleIntegerProperty(0);
    }

    public int getState() {
        return state.get();
    }

    public SimpleIntegerProperty stateProperty() {
        return state;
    }

    public void setState(int state) {
        this.state.set(state);
    }

    public int getTablePosition() {
        return tablePosition;
    }

    public void setTablePosition(int tablePosition) {
        this.tablePosition = tablePosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar.get();
    }

    public SimpleStringProperty avatarProperty() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar.set(avatar);
    }

    public String getNickName() {
        return nickName.get();
    }

    public SimpleStringProperty nickNameProperty() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName.set(nickName);
    }

    public int getScore() {
        return score.get();
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
    }

    public String getScoreString() {
        return scoreString.get();
    }

    public SimpleStringProperty scoreStringProperty() {
        return scoreString;
    }

    public void setScoreString(String scoreString) {
        this.scoreString.set(scoreString);
    }
}
