package com.watch0ut.landlord.object.cardtype;

import java.util.List;

import com.watch0ut.landlord.object.Card;

/**
 * Created by GreatYYX on 14-10-10.
 */
public class One extends CardType {

    public static boolean isValid(List<Card> cards) {
        return true;
    }

    public One(List<Card> cards) {
        cards_ = cards;
    }

    @Override
    public int compareTo(Object obj) {
        if(obj == null || obj == this){
            return 0;
        }

        //if(obj instanceof One) {
        One one = (One)obj;

        Card c1 = (Card)this.cards_.get(0);
        Card c2 = (Card)one.cards_.get(0);

        return comparePointFirst(c1, c2);
    }
}
