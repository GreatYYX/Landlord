package com.watch0ut.landlord.object;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import com.watch0ut.landlord.object.cardtype.*;

/**
 * Created by GreatYYX on 14-10-10.
 *
 * 玩家，除自身属性外，包含自身的牌以及牌合法性判定操作
 */
public class Player implements Serializable {

    private int tablePosition_ = -1;
    private int tableId_ = -1;

    private int id_;
    private String userName_;
    private String nickName_;
    private String photo_;
    private int roundCount_;
    private int winCount_;
    private int loseCount_;
    private int landlordCount_;
    private int score_;

    public enum ROLE {
        Landlord3A, Landlord3, LandlordA, Farmer
    }
    private ROLE role_;

    public enum STATE {
        Idle,       //空闲
        Seated,     //入坐
        Ready,      //准备
        Play,       //出牌
        Wait,       //等待出牌
        Finish      //结束
    }
    private STATE state_;

    private List<Card> cards_ = new ArrayList<Card>();

    public Player() {
        state_ = STATE.Idle;
        tablePosition_ = -1;
        tableId_ = -1;
    }

    public Player(int id, String userName, String nickName, String photo, int score,
                  int roundCount, int winCount, int loseCount, int landlordCount) {
        state_ = STATE.Idle;
        tablePosition_ = -1;
        tableId_ = -1;

        id_ = id;
        userName_ = userName;
        nickName_ = nickName;
        photo_ = photo;
        score_ = score;
        roundCount_ = roundCount;
        winCount_ = winCount;
        loseCount_ = loseCount;
        landlordCount_ = landlordCount;
    }

    /**
     * 基础player信息
     * @return
     */
    public Player getBasicPlayer() {
        Player player = new Player(id_, userName_, nickName_, photo_,
                score_, roundCount_, winCount_, loseCount_, landlordCount_);
        player.setState(state_);
        player.setTableId(tableId_);
        player.setTablePosition(tablePosition_);
        return player;
    }

    public List<Card> getCards() {
        return cards_;
    }

    /**
     * 整组用户牌设置，仅在发牌时调用
     * 并完成用户角色判定
     * @param cards
     * @return 角色
     */
    public ROLE setCards(List<Card> cards) {
        sortPointFirst(cards);
        this.cards_ = cards;

        //确定角色
        Card spade3 = Card.getSpade3();
        Card spadeA = Card.getSpadeA();
        if (cards_.contains(spade3) && cards_.contains(spadeA)) {
            role_ = ROLE.Landlord3A;
        } else if (cards_.contains(spade3)) {
            role_ = ROLE.Landlord3;
        } else if (cards_.contains(spadeA)) {
            role_ = ROLE.LandlordA;
        } else {
            role_ = ROLE.Farmer;
        }

        return role_;
    }

    /**
     * 添加牌
     */
    public boolean addCards(List<Card> cards) {
        return cards_.addAll(cards);
    }

    /**
     * 移除牌
     */
    public boolean removeCards(List<Card> cards) {
        if(cards_.containsAll(cards)) {
            return cards.removeAll(cards);
        }
        return false;
    }

    /**
     * 获取贡牌
     * @return 所贡的牌
     */
//    public Map<Card, Player> getTributeCards() {
//
//        if(tribute_ >= 0) {
//            return null;
//        }
//
//        Card spade3 = Card.getSpade3();
//        Card spadeA = Card.getSpadeA();
//        Map<Card, Player> tribMap = new TreeMap<Card, Player>();
//        int times = Math.abs(tribute_);
//        int pos = cards_.size() - 1;
//
//        while(times > 0) {
//            Card card = cards_.get(pos);
//            //跳过身份牌
//            if(card.equals(spade3) || card.equals(spadeA)) {
//                continue;
//            }
//            //填充
//            tribMap.put(card, this);
//            cards_.remove(pos);
//            times--;
//        }
//
//        return tribMap;
//    }

    /**
     * 出牌
     * @param leftType 上家的牌
     * @param cards 需要出的牌
     * @return 合法则返回实例化的CardType对象，否则返回null
     */
    public CardType play(CardType leftType, List<Card> cards) {

        CardType currType = findType(cards);
        if(currType == null) {
            return null;
        }

        //判断和上家类型是否相同
        if (((leftType instanceof One) && (currType instanceof One)) ||
                ((leftType instanceof Two) && (currType instanceof Two)) ||
                ((leftType instanceof Three) && (currType instanceof Three)) ||
                ((leftType instanceof Five) && (currType instanceof Five))) {

            //牌面大于上家
            if(currType.compareTo(leftType) > 0) {
                if(removeCards(cards)) {
                    return currType;
                }
            }
        }
        return null;
    }

    /**
     * 判断出牌类型是否合法并格式化牌类型
     * @param cards
     * @param first 第一次出牌则为true，其余皆为false
     * @return 合法则返回实例化的CardType对象，否则返回null
     */
    private CardType findType(List<Card> cards, boolean first) {

        int len = cards.size();
        CardType cardType = null;

        //第一次出牌需要含有方片4
        Card diamond4 = Card.getDiamond4();
        if(first) {
            if(!cards.contains(diamond4))
                return null;
        }

        switch (len) {
            case 0:
                return null;
            case 1:
                if (One.isValid(cards)) {
                    cardType = new One(cards);
                }
                break;
            case 2:
                sortPointFirst(cards);
                if (Two.isValid(cards)) {
                    cardType = new Two(cards);
                }
                break;
            case 3:
                sortPointFirst(cards);
                if (Three.isValid(cards)) {
                    cardType = new Three(cards);
                }
                break;
            case 5:
                sortPointFirst(cards);
                if (Five.isValid(cards)) {
                    cardType = new Five(cards);
                }
        }

        return cardType;
    }

    /**
     * findType方法重载，供非第一次出牌使用
     * @param cards
     * @return
     */
    private CardType findType(List<Card> cards) {
        return findType(cards, false);
    }

    /**
     * 点数优先排序
     * @param cards
     */
    private void sortPointFirst(List<Card> cards) {
        if(cards.size() <= 1) {
            return;
        }

        Collections.sort(cards, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Card c1 = (Card) o1;
                Card c2 = (Card) o2;
                if (c1.equals(c2)) {
                    return 0;
                }

                if (c1.getPoint() > c2.getPoint()) {
                    return 1;
                } else if (c1.getPoint() < c2.getPoint()) {
                    return -1;
                } else { //c1.getPoint == c2.getPoint
                    if (c1.getSuit() > c2.getSuit()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    /**
     * 花色优先排序
     * @param cards
     */
    private void sortSuitFirst(List<Card> cards) {
        if(cards.size() <= 1) {
            return;
        }

        Collections.sort(cards, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Card c1 = (Card) o1;
                Card c2 = (Card) o2;
                if (c1.equals(c2)) {
                    return 0;
                }

                if (c1.getSuit() > c2.getSuit()) {
                    return 1;
                } else if (c1.getSuit() < c2.getSuit()) {
                    return -1;
                } else {
                    if (c1.getPoint() > c2.getPoint()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        id_ = id;
    }

    public String getUserName() {
        return userName_;
    }

    public void setUserName(String name) {
        userName_ = name;
    }

    public String getNickName() {
        return nickName_;
    }

    public void setNickName(String nickName) {
        this.nickName_ = nickName;
    }

    public String getPhoto() {
        return photo_;
    }

    public void setPhoto(String photo) {
        photo_ = photo;
    }

    public ROLE getRole() {
        return role_;
    }

    public int getScore() {
        return score_;
    }

    public void setScore(int score) {
        score_ = score_;
    }

    public int getRoundCount() {
        return roundCount_;
    }

    public void setRoundCount(int roundCount) {
        roundCount_ = roundCount;
    }

    public int getWinCount() {
        return winCount_;
    }

    public void setWinCount(int winCount) {
        winCount_ = winCount;
    }

    public int getLoseCount_() {
        return loseCount_;
    }

    public void setLoseCount(int loseCount) {
        loseCount_ = loseCount;
    }

    public int getLandlordCount() {
        return landlordCount_;
    }

    public void setLandlordCount(int landlordCount) {
        landlordCount_ = landlordCount;
    }

    public int getTablePosition() {
        return tablePosition_;
    }

    public void setTablePosition(int position) {
        tablePosition_ = position;
    }

    public int getTableId() {
        return tableId_;
    }

    public void setTableId(int tableId) {
        this.tableId_ = tableId;
    }

    public STATE getState() {
        return state_;
    }

    public void setState(STATE state) {
        state_ = state;
    }
}
