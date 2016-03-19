package com.watch0ut.landlord.object;

import com.watch0ut.landlord.object.cardtype.CardType;
import sun.net.www.content.audio.basic;

import java.util.*;

/**
 * Created by GreatYYX on 3/15/16.
 */
public class TestPlayer {


    public static void main(String[] args) {
        Player.STATE state = Player.STATE.Seated;
        System.out.println(state.getValue());
        System.out.println(state);

        List<Card> cards = new ArrayList<>();
        cards.add(new Card(0, 0));  // 4
        cards.add(new Card(1, 1));  // 5
        cards.add(new Card(2, 2));  // 6
        cards.add(new Card(3, 3));  // 7
        cards.add(new Card(4, 0));  // 8
        cards.add(new Card(5, 1));  // 9
        cards.add(new Card(6, 2));  // 10
        cards.add(new Card(7, 3));  // J
        cards.add(new Card(8, 0));  // Q
        cards.add(new Card(9, 1));  // K
        cards.add(new Card(10, 2)); // A
        cards.add(new Card(11, 3)); // 2
        cards.add(new Card(12, 0)); // 3

        Player player = new Player(0, "test", "TestMan", "", 10, 2, 1, 1, 1);
        Player basicPlayer = player.getBasicPlayer();
        player.setTableId(1);
        System.out.println("table id: " + player.getTableId() + " " + basicPlayer.getTableId());
        player.setCards(cards);

        List<Card> playedCards = new ArrayList<>();
        playedCards.add(new Card(0, 0));
        CardType cardType = Player.tryToPlay(null, playedCards);
        for(Card c : player.getCards()) {
            System.out.print(c + " ");
        }
        System.out.println("");
        player.play(null, cardType);
        for(Card c : player.getCards()) {
            System.out.print(c + " ");
        }
        System.out.println("");
    }
}
