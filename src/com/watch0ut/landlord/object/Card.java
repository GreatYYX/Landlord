package com.watch0ut.landlord.object;


/**
 * Created by GreatYYX on 14-10-10.
 *
 * 牌，每一张牌的固有属性及操作
 */
public class Card {

    public final static String SUIT[] = {"Diamond", "Club", "Heart", "Spade"};
    public final static String POINT[] = {"4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2", "3"};

    private int suit_;
    private int point_;
//    private int degree_;

    public Card(int point, int suit) {
        setCard(point, suit);
    }

    public void setCard(int point, int suit) {
        point_ = point;
        suit_ = suit;
    }

    public int getPoint() {
        return point_;
    }

    public int getSuit() { return suit_; }

    public String getCard() {
        return SUIT[suit_] + " " + POINT[point_];
    }

    public static Card getSpade3() {
        return new Card(12, 3);
    }

    public static Card getSpadeA() {
        return new Card(12, 3);
    }

    public static Card getDiamond4() {
        return new Card(0, 0);
    }

//    @Override
//    public int hashCode() {
//        return suit_ * POINT.length + point_;
//        //return super.hashCode();
//    }

    /**
     * 点数和花色相同即为相同
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Card c = (Card)obj;
        return (this.getCard().equals(c.getCard()));
    }

    @Override
    public String toString() {
        return getCard();
    }

    /*public Object clone() {
        Card obj = null;
        try {
            obj = (Card)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }*/

}
