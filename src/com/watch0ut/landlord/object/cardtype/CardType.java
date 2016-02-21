package com.watch0ut.landlord.object.cardtype;

import java.util.List;

import com.watch0ut.landlord.object.Card;


/**
 * Created by GreatYYX on 14-10-10.
 *
 * 出牌类型。所有传入CardType子类的List必须先经过Operator中的点数优先排序！！
 */
public class CardType implements Comparable<Object> {
    protected List<Card> cards_ = null;

    /**
     * 判断合法性的静态方法
     * 所有子类需要重载
     * @param cards
     * @return
     */
    public static boolean isValid(List<Card> cards) {
        return true;
    }

    /**
     * 获取牌
     * @return
     */
    public List<Card> getCards() {
        return cards_;
    }

    /**
     * 子类实现该接口，用于牌大小比较
     * @param obj
     * @return
     */
    @Override
    public int compareTo(Object obj) {
        return 0;
    }

    /**
     * 点数优先比较
     * 先比较最大点数，再比较花色
     * @param c1
     * @param c2
     * @return c1>c2返回1，c1==c2返回0，c1<c2返回-1
     */
    public int comparePointFirst(Card c1, Card c2) {

        if(c1.equals(c2)) {
            return 0;
        }

        if(c1.getPoint() > c2.getPoint()) {
            return 1;
        }
        else if(c1.getPoint() < c2.getPoint()) {
            return -1;
        }
        else { //c1.getPoint() == c2.getPoint()
            if(c1.getSuit() > c2.getSuit()) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }

    /**
     * 花色优先比较（仅用于同花）
     * 先比较花色，再比较最大点数
     * @param c1
     * @param c2
     * @return c1>c2返回1，c1==c2返回0，c1<c2返回-1
     */
    public int compareSuitFirst(Card c1, Card c2) {

        if(c1.equals(c2)) {
            return 0;
        }

        if(c1.getSuit() > c2.getSuit()) {
            return 1;
        }
        else if(c1.getSuit() < c2.getSuit()) {
            return -1;
        }
        else { //c1.getSuit() == c2.getSuit()
            if(c1.getPoint() > c2.getPoint()) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }

}