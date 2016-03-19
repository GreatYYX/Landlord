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
    private int gameCount_;
    private int winCount_;
    private int loseCount_;
    private int landlordCount_;
    private int score_;

    public enum ROLE {
        Landlord3A, Landlord3, LandlordA, Farmer
    }
    private ROLE role_;

    public enum STATE {
        Idle(0),       //空闲
        Seated(1),     //入坐
        Ready(2),      //准备
        Play(3),       //出牌
        Wait(4),       //等待出牌
        Finish(5);     //结束

        private final int stateId_;
        private STATE(int stateId) {
            stateId_ = stateId;
        }

        public static STATE getState(int stateId) {
            for (STATE state : STATE.values()) {
                if (state.getValue() == stateId)
                    return state;
            }
            return Idle;
        }

        public int getValue() {
            return stateId_;
        }

    }
    private STATE state_;

    private List<Card> cards_ = new ArrayList<Card>();
    // player基本信息
    // 采用直接维护Player对象的方式
    // 而非每次重新获取状态以提高性能
    private Player basicPlayer_ = null;

//    public Player() {
//        state_ = STATE.Idle;
//        tablePosition_ = -1;
//        tableId_ = -1;
//    }

    public Player(int id, String userName, String nickName, String photo, int score,
                  int gameCount, int winCount, int loseCount, int landlordCount) {
        state_ = STATE.Idle;
        tablePosition_ = -1;
        tableId_ = -1;

        id_ = id;
        userName_ = userName;
        nickName_ = nickName;
        photo_ = photo;
        score_ = score;
        gameCount_ = gameCount;
        winCount_ = winCount;
        loseCount_ = loseCount;
        landlordCount_ = landlordCount;
    }

    /**
     * 基础player信息
     * 包含基本属性和状态，不包含牌及身份
     * @return
     */
    public Player getBasicPlayer() {
        if(basicPlayer_ == null) {
            basicPlayer_ = new Player(id_, userName_, nickName_, photo_,
                    score_, gameCount_, winCount_, loseCount_, landlordCount_);
            basicPlayer_.setState(state_);
            basicPlayer_.setTableId(tableId_);
            basicPlayer_.setTablePosition(tablePosition_);
        }
        return basicPlayer_;
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
            return cards_.removeAll(cards);
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
     */
    public CardType play(CardType prevType, CardType currType) {

        CardType tried = tryToPlay(prevType, currType);
        if(tried != null) {
            removeCards(currType.getCards());
        }

        return tried;
    }

    /**
     * 尝试出牌（重载）
     */
    public static CardType tryToPlay(CardType prevType, List<Card> cards) {
        return tryToPlay(prevType, findType(cards));
    }

    /**
     * 尝试出牌，并没有真正从手牌中扣除，扣除牌需调用removeCards或play
     * @param prevType 之前玩家出的最大牌，null则说明没有大过当前玩家上一轮的牌（本轮仍然当前玩家出牌）
     * @param currType 需要出的牌，null则为不出
     * @return 合法则返回实例化的CardType对象，否则返回null（不出或比上家小）
     */
    public static CardType tryToPlay(CardType prevType, CardType currType) {
        if(currType == null) { // 不出牌
            return null;
        }

        if(prevType == null) { // 一圈中最大，仍本玩家出牌
            return currType;
        }

        //判断和上家类型是否相同
        if (((prevType instanceof One) && (currType instanceof One)) ||
                ((prevType instanceof Two) && (currType instanceof Two)) ||
                ((prevType instanceof Three) && (currType instanceof Three)) ||
                ((prevType instanceof Five) && (currType instanceof Five))) {

            //牌面大于上家
            if(currType.compareTo(prevType) > 0) {
                return currType;
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
    private static CardType findType(List<Card> cards, boolean first) {

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
    private static CardType findType(List<Card> cards) {
        return findType(cards, false);
    }

    /**
     * 点数优先排序
     * @param cards
     */
    private static void sortPointFirst(List<Card> cards) {
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
    private static void sortSuitFirst(List<Card> cards) {
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
        if(basicPlayer_ != null) basicPlayer_.setId(id);
    }

    public String getUserName() {
        return userName_;
    }

    public void setUserName(String name) {
        userName_ = name;
        if(basicPlayer_ != null) basicPlayer_.setUserName(name);
    }

    public String getNickName() {
        return nickName_;
    }

    public void setNickName(String nickName) {
        nickName_ = nickName;
        if(basicPlayer_ != null) basicPlayer_.setNickName(nickName);
    }

    public String getPhoto() {
        return photo_;
    }

    public void setPhoto(String photo) {
        photo_ = photo;
        if(basicPlayer_ != null) basicPlayer_.setPhoto(photo);
    }

    public ROLE getRole() {
        return role_;
    }

    public int getScore() {
        return score_;
    }

    public void setScore(int score) {
        score_ = score;
        if(basicPlayer_ != null) basicPlayer_.setScore(score);
    }

    public int getGameCount() {
        return gameCount_;
    }

    public void setGameCount(int gameCount) {
        gameCount_ = gameCount;
        if(basicPlayer_ != null) basicPlayer_.setGameCount(gameCount);
    }

    public int getWinCount() {
        return winCount_;
    }

    public void setWinCount(int winCount) {
        winCount_ = winCount;
        if(basicPlayer_ != null) basicPlayer_.setWinCount(winCount);
    }

    public int getLoseCount_() {
        return loseCount_;
    }

    public void setLoseCount(int loseCount) {
        loseCount_ = loseCount;
        if(basicPlayer_ != null) basicPlayer_.setLoseCount(loseCount);
    }

    public int getLandlordCount() {
        return landlordCount_;
    }

    public void setLandlordCount(int landlordCount) {
        landlordCount_ = landlordCount;
        if(basicPlayer_ != null) basicPlayer_.setLandlordCount(landlordCount);
    }

    public int getTablePosition() {
        return tablePosition_;
    }

    public void setTablePosition(int position) {
        tablePosition_ = position;
        if(basicPlayer_ != null) basicPlayer_.setTablePosition(position);
    }

    public int getTableId() {
        return tableId_;
    }

    public void setTableId(int tableId) {
        tableId_ = tableId;
        if(basicPlayer_ != null) basicPlayer_.setTableId(tableId);
    }

    public STATE getState() {
        return state_;
    }

    public void setState(STATE state) {
        state_ = state;
        if(basicPlayer_ != null) basicPlayer_.setState(state);
    }
}
