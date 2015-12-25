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

    public enum POSITION { Top, Right, Bottom, Left };

    private List<Player> playerList_;
    private boolean needTribute_; //新桌第一轮不考虑tribute

    //当有Player改变以下数据恢复默认
    int count_ = 0; //局数
    int[] finishSequence_ = {-1, -1, -1, -1};//逃出顺序,-1,0,1,2,3，从0开始
    int[] chooseTributeSequence_ = {-1, -1, -1, -1};//选择贡牌顺序，-1不选
    int[] chooseRevertSequence_ = {-1, -1, -1, -1};//选择还牌顺序，-1不选

    public Table() {
        init();
        playerList_ = new ArrayList<Player>();
    }

    public void init() {
        count_ = 0;
        needTribute_ = false;
        for(int i = 0; i < 4; i++) {
            finishSequence_[i] = -1;
            chooseTributeSequence_[i] = -1;
            chooseRevertSequence_[i] = -1;
        }
    }

    public void setPosition(Player player, POSITION pos) {
        playerList_.add(player);
        player.setPosition(pos);
    }

    POSITION getPosition(Player player) {
        return player.getPosition();
    }

    /**
     * 设置逃出顺序
     * @param player
     * @param seq [0,4)，同时逃出则数字相同
     */
    public void setFinishSequence(Player player, int seq) {
        finishSequence_[playerList_.indexOf(player)] = seq;
    }

    public int getFinishSequence(Player player) {
        return finishSequence_[playerList_.indexOf(player)];
    }

    public int getChooseTributeSequence(Player player) {
        return chooseTributeSequence_[playerList_.indexOf(player)];
    }

    public void setChooseTributeSequence(Player player, int seq) {
        chooseTributeSequence_[playerList_.indexOf(player)] = seq;
    }

    public int getChooseRevertSequence(Player player) {
        return chooseRevertSequence_[playerList_.indexOf(player)];
    }

    public void setChooseRevertSequence(Player player, int seq) {
        chooseRevertSequence_[playerList_.indexOf(player)] = seq;
    }

    public int getCount() {
        return count_;
    }

    public void setCount(int count) {
        count_ = count;
    }

    public List<Player> getPlayerList() {
        return playerList_;
    }
}
