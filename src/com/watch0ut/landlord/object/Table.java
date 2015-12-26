package com.watch0ut.landlord.object;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by GreatYYX on 14-10-10.
 *
 * 牌桌，记录位置
 * 同时在一桌未换人情况下记录数据
 * 当出现Player变化后当前Table对象即作废
 */
public class Table {

    public static final int UNSEATED = -1;
    public static final int BOTTOM = 0;
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;

    private int id_;
    private Player[] players_ = new Player[]{null, null, null, null};
    private int playerCount_ = 0; //player数量
    private int roundCount_ = 0; //局数
    private int[] scores_ = new int[]{0, 0, 0, 0}; //累计比分（index为座位POSITION）
    private Dealer dealer_;

    public Table(int id, Dealer dealer) {
        id_ = id;
        dealer_ = dealer;
    }

    /**
     * 重置，当有player入座或离开后需执行
     * 仅更新统计信息
     */
    public void reset() {
        roundCount_ = 0;
        scores_ = new int[]{0, 0, 0, 0};
        dealer_.reset();
    }

    /**
     * 入座
     * @param player
     * @param pos
     * @return
     */
    public boolean seat(Player player, int pos) {
        //已有用户则无法占座
        if(players_[pos] != null) {
            return false;
        }
        //入座
        players_[pos] = player;
        player.setTablePosition(pos);
        player.setTableId(id_);
        player.setState(Player.STATE.Seated);
        playerCount_++;
        reset();
        return true;
    }

    /**
     * 离开座位
     * @param player
     * @param pos
     * @return
     */
    public boolean unseat(Player player, int pos) {
        //空座和非自己座位无法离开座位
        if(players_[pos] == null || players_[pos] != player) {
            return false;
        }
        //离开
        players_[pos] = null;
        player.setTablePosition(UNSEATED);
        player.setTableId(-1);
        player.setState(Player.STATE.Idle);
        playerCount_--;
        reset();
        return true;
    }

    public Player getPlayer(int pos) {
        return players_[pos];
    }

    public Dealer getDealer() {
        return dealer_;
    }

    public int getId() {
        return id_;
    }
}
