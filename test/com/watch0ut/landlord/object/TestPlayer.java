package com.watch0ut.landlord.object;

/**
 * Created by GreatYYX on 3/15/16.
 */
public class TestPlayer {


    public static void main(String[] args) {
        Player.STATE state = Player.STATE.Seated;
        System.out.println(state.get());
        System.out.println(state);
    }
}
