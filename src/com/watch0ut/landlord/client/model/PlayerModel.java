package com.watch0ut.landlord.client.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * 玩家模型，用于玩家列表。
 *
 * Created by Jack on 16/2/27.
 */
public class PlayerModel {
    private int id;
    private final SimpleStringProperty avatar;
    private final SimpleStringProperty nickName;
    private final SimpleIntegerProperty score;

    public PlayerModel(int id, String avatar, String nickName, int score) {
        this.id = id;
        this.avatar = new SimpleStringProperty(avatar);
        this.nickName = new SimpleStringProperty(nickName);
        this.score = new SimpleIntegerProperty(score);
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
}
