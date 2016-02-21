package com.watch0ut.landlord.object.cardtype;

import java.util.List;

import com.watch0ut.landlord.object.Card;

/**
 * Created by GreatYYX on 14-10-10.
 */
public class Three extends CardType {

    public static boolean isValid(List<Card> cards) {
        return (cards.get(0).getPoint() == cards.get(1).getPoint()) && (cards.get(1).getPoint() == cards.get(2).getPoint());
    }

    public Three(List<Card> cards) {
        cards_ = cards;
    }

    @Override
    public int compareTo(Object obj) {
        if(obj == null || obj == this){
            return 0;
        }

        Three three = (Three)obj;

        Card c1 = (Card)this.cards_.get(2);
        Card c2 = (Card)three.cards_.get(2);

        return comparePointFirst(c1, c2);
    }
}
