package com.watch0ut.landlord.object.cardtype;

import java.util.List;

import com.watch0ut.landlord.object.Card;

/**
 * Created by GreatYYX on 14-10-10.
 */
public class Five extends CardType {

    //5张牌中的5种类型 （顺子，同花，三带二，四带一，同花顺）
    enum TYPE { Straight, Flush, ThreePlusTwo, FourPlusOne, StraightFlush };
    private TYPE type_;

    public static boolean isValid(List<Card> cards) {
        if(cards.size() == 5 &&
           (isStraight(cards) || isFlush(cards) ||
           isThreePlusTwo(cards) || isFourPlusOne(cards))) {
            return true;
        }
        return false;
    }

    public Five(List<Card> cards) {
        cards_ = cards;
        if(isStraight(cards)) {
            type_ = TYPE.Straight;
        }
        else if(isFlush(cards)) {
            type_ = TYPE.Flush;
        }
        else if(isThreePlusTwo(cards)) {
            type_ = TYPE.ThreePlusTwo;
        }
        else if(isFourPlusOne(cards)) {
            type_ = TYPE.FourPlusOne;
        }

        //isFlush判断比isStraight简单，所以再判断一次耗费较少
        //另外同花Flush不包含顺子，顺子Straight不包含同花
        //但同花顺的判断可以利用同花和顺子的判断方法
        if((type_ == TYPE.Straight) && isFlush(cards)) {
            type_ = TYPE.StraightFlush;
        }
    }

    @Override
    public int compareTo(Object obj) {
        if(obj == null || obj == this){
            return 0;
        }

        Five five = (Five)obj;

        //优先比较5个类型的大小
        if(this.type_.ordinal() > five.type_.ordinal()) {
            return 1;
        }
        else if(this.type_.ordinal() < five.type_.ordinal()) {
            return -1;
        }
        //同类型内部比较
        else {
            //顺子和同花顺特殊情况45A23->A2345
            Card c1, c2;
            if(five.type_ == TYPE.Straight || five.type_ == TYPE.StraightFlush) {
                c1 = (Card)this.cards_.get(1); //5
                c2 = (Card)five.cards_.get(1); //5
            } else {
                c1 = (Card)this.cards_.get(4);
                c2 = (Card)five.cards_.get(4);
            }

            switch(five.type_) {
                //花色优先：
                case Flush:
                    return compareSuitFirst(c1, c2);
                //点数优先
                case Straight:
                case ThreePlusTwo:
                case FourPlusOne:
                case StraightFlush:
                default:
                    return comparePointFirst(c1, c2);
            }
        }
    }

    /**
     * 顺子
     */
    private static boolean isStraight(List<Card> cards) {
        //普通顺子(4,5,6,7,8~10,J,Q,K,A)
        if (cards.get(0).getPoint() + 1 == cards.get(1).getPoint() &&
            cards.get(1).getPoint() + 1 == cards.get(2).getPoint() &&
            cards.get(2).getPoint() + 1 == cards.get(3).getPoint() &&
            cards.get(3).getPoint() + 1 == cards.get(4).getPoint()) {
            return true;
        }
        //A在开头的顺子（4,5,A,2,3）
        else if(cards.get(0).getPoint() == 0 &&
                cards.get(1).getPoint() == 1 &&
                cards.get(2).getPoint() == 10 &&
                cards.get(3).getPoint() == 11 &&
                cards.get(4).getPoint() == 12) {
            return true;
        }
        return false;
    }


    /**
     * 同花
     */
    private static boolean isFlush(List<Card> cards) {
        if (cards.get(0).getSuit() == cards.get(1).getSuit() &&
            cards.get(1).getSuit() == cards.get(2).getSuit() &&
            cards.get(2).getSuit() == cards.get(3).getSuit() &&
            cards.get(3).getSuit() == cards.get(4).getSuit()) {
            return true;
        }
        return false;
    }

    /**
     * 三带二
     */
    private static boolean isThreePlusTwo(List<Card> cards) {
        //AAABB
        if (cards.get(0).getPoint() == cards.get(1).getPoint() &&
            cards.get(1).getPoint() == cards.get(2).getPoint() &&
            cards.get(3).getPoint() == cards.get(4).getPoint()) {
            return true;
        }
        //AABBB
        else if(cards.get(0).getPoint() == cards.get(1).getPoint() &&
                cards.get(2).getPoint() == cards.get(3).getPoint() &&
                cards.get(3).getPoint() == cards.get(4).getPoint()) {
            return true;
        }
        return false;
    }

    /**
     * 四带一
     */
    private static boolean isFourPlusOne(List<Card> cards) {
        //AAAAB
        if (cards.get(0).getPoint() == cards.get(1).getPoint() &&
            cards.get(1).getPoint() == cards.get(2).getPoint() &&
            cards.get(2).getPoint() == cards.get(3).getPoint()) {
            return true;
        }
        //ABBBB
        else if(cards.get(1).getPoint() == cards.get(2).getPoint() &&
                cards.get(2).getPoint() == cards.get(3).getPoint() &&
                cards.get(3).getPoint() == cards.get(4).getPoint()) {
            return true;
        }
        return false;
    }
}

