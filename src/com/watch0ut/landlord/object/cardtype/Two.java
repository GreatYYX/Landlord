package com.watch0ut.landlord.object.cardtype;

import java.util.List;

import com.watch0ut.landlord.object.Card;

/**
 * Created by GreatYYX on 14-10-10.
 */
public class Two extends CardType {

    public static boolean isValid(List<Card> cards) {
        return (cards.size() == 2 && cards.get(0).getPoint() == cards.get(1).getPoint());
    }

    public Two(List<Card> cards) {
        cards_ = cards;
    }

    @Override
    public int compareTo(Object obj) {
        if(obj == null || obj == this){
            return 0;
        }

        Two two = (Two)obj;

        Card c1 = (Card)this.cards_.get(1);
        Card c2 = (Card)two.cards_.get(1);

        return comparePointFirst(c1, c2);
    }
}
