package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.Card;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by GreatYYX on 12/29/15.
 *
 * server->client
 * 游戏开始
 *
 */
public class GameStartCommand extends AbstractCommand {

    private List<Card> cards_;
    private int firstPlayerUid_;

    public GameStartCommand() {

    }

    public void setFirstPlayerUid(int uid) {
        firstPlayerUid_ = uid;
    }

    public int getFirstPlayerUid() {
        return firstPlayerUid_;
    }

    public void setCards(List<Card> cards) {
        cards_ = cards;
    }

    public List<Card> getCards() {
        return cards_;
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(cards_);
        oos.writeObject(firstPlayerUid_);
        return bos.toByteArray();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis);
        cards_ = (List<Card>)ois.readObject();
        firstPlayerUid_ = (Integer)ois.readObject();
    }
}
