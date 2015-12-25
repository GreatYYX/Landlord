package com.watch0ut.landlord.object;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

/**
 * Created by GreatYYX on 14-10-10.
 *
 * 荷官，负责发牌洗牌判赢等操作
 * 只记录当前一轮的数据，不保留其他状态
 */
public class Dealer {

    List<Card> deck_; //一副牌无大小王共52张
    Map<Card, Player> tributeMap_; //贡牌堆
    //Map<Player, Card> revertMap_; //还牌堆
    int finishCount_; //已经逃出的玩家数量
    int lordCount_; //地主数量
    List<Player> finishLord_; //已逃出地主
    List<Player> finishFarmer_; //已逃出农民

    Table table_;
    List<Player> playerList_; //table中的playerlist指针

    public void Dealer(Table table) {
        setTable(table);
        init();
    }

    public void setTable(Table table) {
        table_ = table;
        playerList_ = table_.getPlayerList();
    }

    /**
     * 每局初始化
     */
    public void init() {
        deck_ = new ArrayList<Card>();
        tributeMap_ = new TreeMap<Card, Player>();//利用TreeMap自动对Card排序
        //revertMap_ = new HashMap<Player, Card>();
        finishCount_ = 0;
        lordCount_ = 0;
        finishLord_ = new ArrayList<Player>();
        finishFarmer_ = new ArrayList<Player>();
    }

    /**
     * 洗牌
     */
    public void shuffle() {

        //初始化
        for(int i = 0; i < Card.POINT.length; i++) {
            for(int j = 0; j < Card.SUIT.length; j++) {
                deck_.add(new Card(i, j));
                //System.out.println(deck_.get(i * Card.SUIT.length + j).getCard());
            }
        }

        //洗牌
        for(int i = 0; i < 52; i++) {
            int rand = randRange(i, 52);
            swapCard(i, rand);
            //System.out.println(deck_.get(i).getCard());
        }
    }

    /**
     * 发牌
     */
    public void deal() {
        for(int i = 0; i < 4; i++) {
            List<Card> playerCards = new ArrayList<Card>();
            for(int j = 0; j < 13; j++) {
                playerCards.add(deck_.get(i * 13 + j));
            }
            Player.ROLE role = playerList_.get(i).setCards(playerCards);
            if(role == Player.ROLE.Landlord3A) {
                lordCount_ = 1;
            } else if(role == Player.ROLE.Landlord3 || role == Player.ROLE.LandlordA) {
                lordCount_ = 2;
            }
        }

        //deck_.clear();
    }

    /**
     * 玩家结束
     */
    public void playerFinish(Player player) {
        table_.setFinishSequence(player, finishCount_);
        player.setStatus(Player.STATUS.Finish);
        finishCount_++;

        Player.ROLE currRole = player.getRole();
        switch(currRole) {
            case Landlord3:
            case LandlordA:
            case Landlord3A:
                finishLord_.add(player);
            case Farmer:
                finishFarmer_.add(player);
        }

        //一地主（Landlord3A），地主逃出或3农民逃出则结束
        if(lordCount_ == 1) {
            if(finishLord_.size() == 1 || finishFarmer_.size() == 3) {
                roundFinish();
            }
        }
        //两地主，任何一方两人逃出结束
        else if(lordCount_ == 2) {
            if(finishLord_.size() == 2 || finishFarmer_.size() == 2) {
                roundFinish();
            }
        }
    }

    /**
     * 一局结束
     */
    public void roundFinish() {
        //补全逃出顺序，并列
        for(int i = 0; i < 4; i++) {
            Player currPlayer = playerList_.get(i);
            if(!currPlayer.getStatus().equals(Player.STATUS.Finish)) {
                table_.setFinishSequence(currPlayer, finishCount_);
                currPlayer.setStatus(Player.STATUS.Finish);
            }
        }

        //判定胜利方，是否需要贡牌
        int lordTribute = 0, farmerTribute = 0;
        if(lordCount_ == 1) {
            int seq = table_.getFinishSequence(finishLord_.get(0));
            switch(seq) {
                case 0: //农民每人2贡张，地主胜
                    lordTribute = 6;
                    farmerTribute = -2;
                    break;
                case 1: //农民每人1贡张，地主胜
                    lordTribute = 3;
                    farmerTribute = -1;
                    break;
                case 2: //地主贡3张，农民胜
                    lordTribute = -3;
                    farmerTribute = 1;
                    break;
                case 3: //地主贡6张，农民胜
                    lordTribute = -6;
                    farmerTribute = 2;
            }
        }
        else if(lordCount_ == 2) {
            //seq1一定早于seq2
            int seq1 = table_.getFinishSequence(finishLord_.get(0));
            int seq2 = table_.getFinishSequence(finishLord_.get(1));

            //12地33农，地主胜，农民每人贡2张
            if(seq1 == 0 && seq2 == 1) {
                lordTribute = 2;
                farmerTribute = -2;
            }
            //13地24农，地主胜，农民每人贡1张
            else if(seq1 == 0 && seq2 == 2) {
                lordTribute = 1;
                farmerTribute = -1;
            }
            //14地或14农，平
            else if((seq1 == 0 && seq2 == 3) ||
                    (seq1 == 1 && seq2 == 2)) {
                lordTribute = 0;
                farmerTribute = 0;
            }
            //24地13农，农民胜，地主每人贡1张
            else if(seq1 == 1 && seq2 == 3) {
                lordTribute = -1;
                farmerTribute = 1;
            }
            //34地12农，农民胜，地主每人贡2张
            else if(seq1 == 2 && seq2 == 3) {
                lordTribute = -2;
                farmerTribute = 2;
            }
        }

        //记录贡牌
        Iterator<Player> iter;
        iter = finishLord_.iterator();
        while(iter.hasNext()) {
            iter.next().setTribute(lordTribute);
        }
        iter = finishFarmer_.iterator();
        while(iter.hasNext()) {
            iter.next().setTribute(farmerTribute);
        }
    }

    /**
     * 贡牌（自动）
     * 并确定选取贡牌及选取还牌的顺序
     */
    public void tribute() {
        tributeMap_.clear();
        Map<Integer, Player> chooseSeq = new TreeMap<Integer, Player>();
        Iterator iter;
        int tribPlayerNum = 0; //贡牌玩家数

        for(int i = 0; i < 4; i++) {
            Player currPlayer = playerList_.get(i);
            //需要贡牌则获取贡牌
            if(currPlayer.getTribute() < 0) {
                tributeMap_.putAll(currPlayer.getTributeCards());
                tribPlayerNum++;
            }
            //不需要贡牌则取出上一盘的逃出顺序
            //将顺序存在TreeMap中自动重新排序
            else {
                int seqTribute = table_.getFinishSequence(currPlayer);
                chooseSeq.put(new Integer(seqTribute), currPlayer);
            }
        }

        //填充收取贡牌顺序
        iter = chooseSeq.entrySet().iterator();
        int seqTribute = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            Player currPlayer = (Player)entry.getValue();
            table_.setChooseTributeSequence(currPlayer, seqTribute);
            seqTribute++;
        }

        //计算并填充选取还牌顺序
        //tributeMap会按照Card大小正向排序，因此反向设置顺序
        iter = tributeMap_.entrySet().iterator();
        int seqRevert = tribPlayerNum;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();

            Player currPlayer = (Player)entry.getValue();
            if(table_.getChooseRevertSequence(currPlayer) == -1) {
                table_.setChooseRevertSequence(currPlayer, seqRevert);
                seqRevert--;
            }
        }
    }

    /**
     * 生成[min,max)的随机数
     * @param min
     * @param max
     */
    private int randRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max) % (max - min) + min;
    }

    /**
     * 交换deck中的牌
     * a与b参数为deck中待交换的id
     * @param a
     * @param b
     */
    private void swapCard(int a, int b) {
        Card tmp = deck_.get(a);
        deck_.set(a, deck_.get(b));
        deck_.set(b, tmp);
    }
}
