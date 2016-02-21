package com.watch0ut.landlord.object;

import com.watch0ut.landlord.exception.InvalidCardType;
import com.watch0ut.landlord.object.cardtype.CardType;

import java.util.*;

/**
 * Created by GreatYYX on 14-10-10.
 *
 * 荷官，负责发牌洗牌判赢等操作
 * 只记录上一局及当前一局的数据，不保留其他状态
 * 注：一局指从GameInit到GameFinish，一局指出牌的一圈
 */
public class Dealer {

    // 本局
    List<Card> deck_; //一副牌无大小王共52张
    int lordCount_; //地主数量
    List<Player> finishLord_; //已逃出地主
    List<Player> finishFarmer_; //已逃出农民
    Player firstPlayer_; //方片四持有玩家第一个出牌，或一轮中出最大牌的玩家下一轮第一个出
    CardType prevCardType_; // 本轮中之前玩家出的最大牌
    int finishCount_; //已经逃出的玩家数量
    int playingPosition_; //目前出牌玩家位置
    Timer timer_;

    //上一局
//    Map<Card, Player> tributeMap_; //贡牌堆
//    Map<Player, Card> revertMap_; //还牌堆
    int[] finishSequence_ = new int[]{-1, -1, -1, -1};//逃出顺序,-1,0,1,2,3，从0开始
//    int[] chooseTributeSequence_ = new int[]{-1, -1, -1, -1};//选择贡牌顺序，-1不选
//    int[] chooseRevertSequence_ = new int[]{-1, -1, -1, -1};//选择还牌顺序，-1不选
    int[] tributeCount_ = new int[]{0, 0, 0, 0};//0为不供不收，大于0为收（1，2，3，6），小于0为贡（-1，-2，-3，-6）

    Table table_;
    List<Player> playerList_;

    public Dealer() {
    }

    public void setTable(Table table) {
        if(table != null) {
            table_ = table;
        }
    }

    public void setPlayerList(List<Player> players) {
        playerList_ = players;
    }

    public Player getFirstPlayer() {
        return firstPlayer_;
    }

    /**
     * 每局初始化
     */
    public void gameInit() {
        deck_ = new ArrayList<Card>();
//        tributeMap_ = new TreeMap<Card, Player>();//利用TreeMap自动对Card排序
//        revertMap_ = new HashMap<Player, Card>();
        finishCount_ = 0;
        lordCount_ = 0;
        finishLord_ = new ArrayList<Player>();
        finishFarmer_ = new ArrayList<Player>();
        firstPlayer_ = null;
        prevCardType_ = null;
    }

    /**
     * Table发生变动后重置历史状态
     */
    public void reset() {
        finishSequence_ = new int[]{-1, -1, -1, -1};
        tributeCount_ = new int[]{0, 0, 0, 0};
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
            //发牌
            List<Card> playerCards = new ArrayList<Card>();
            for(int j = 0; j < 13; j++) {
                playerCards.add(deck_.get(i * 13 + j));
            }
            //确定角色
            Player.ROLE role = playerList_.get(i).setCards(playerCards);
            if(role == Player.ROLE.Landlord3A) {
                lordCount_ = 1;
            } else if(role == Player.ROLE.Landlord3 || role == Player.ROLE.LandlordA) {
                lordCount_ = 2;
            }
            //确定第一个出牌玩家
            if(playerCards.contains(Card.getDiamond4())) {
                firstPlayer_ = playerList_.get(i);
                playingPosition_ = firstPlayer_.getTablePosition();
            }
        }

        //deck_.clear();
    }

    public Player getPlayingPlayer() {
        return table_.getPlayer(playingPosition_);
    }

    /**
     *
     * 记录当前玩家出的牌，同时playingPosition指向下个玩家
     * @param player
     * @param cardType
     * @return true则继续游戏，false则游戏结束
     */
    public boolean playAndMoveNext(Player player, CardType cardType) throws InvalidCardType {
        if(firstPlayer_ == player) { // 本轮第一个出牌
            if(cardType == null) throw new InvalidCardType(); // 此时cardType不能是null，必须出牌
            prevCardType_ = player.play(null, cardType);
        } else {
            prevCardType_ = player.play(prevCardType_, cardType);
        }

        // 判断玩家是否出完牌
        if(player.getCards().size() == 0) {
            if(playerFinish(player)) {
                return false;
            }
        }

        //更新玩家位置
        playingPosition_ = (playingPosition_ + 1) % 4;
        while(finishSequence_[playingPosition_] == -1) {
            playingPosition_ = (playingPosition_ + 1) % 4;
        }

        return true;
    }

    /**
     * 玩家结束，同时判断是否全局结束
     * @param player
     * @return true则全局结束
     */
    public boolean playerFinish(Player player) {
        finishSequence_[player.getTablePosition()] = finishCount_++;
        player.setState(Player.STATE.Finish);

        Player.ROLE currRole = player.getRole();
        switch(currRole) {
            case Landlord3:
            case LandlordA:
            case Landlord3A:
                finishLord_.add(player);
            case Farmer:
                finishFarmer_.add(player);
        }

        boolean isGameFinish = false;
        //一地主（Landlord3A），地主逃出或3农民逃出则结束
        if(lordCount_ == 1) {
            if(finishLord_.size() == 1 || finishFarmer_.size() == 3) {
                isGameFinish = true;
                gameFinish();
            }
        }
        //两地主，任何一方两人逃出结束
        else if(lordCount_ == 2) {
            if(finishLord_.size() == 2 || finishFarmer_.size() == 2) {
                isGameFinish = true;
                gameFinish();
            }
        }
        return isGameFinish;
    }

    /**
     * 游戏结束
     */
    public void gameFinish() {
        //补全逃出顺序，并列
        for(int i = 0; i < 4; i++) {
            Player currPlayer = playerList_.get(i);
            if(!currPlayer.getState().equals(Player.STATE.Finish)) {
                finishSequence_[currPlayer.getTablePosition()] = finishCount_;
                currPlayer.setState(Player.STATE.Finish);
            }
        }

        //判定胜利方，是否需要贡牌
        int lordTribute = 0, farmerTribute = 0;
        if(lordCount_ == 1) {
            int seq = finishSequence_[finishLord_.get(0).getTablePosition()];
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
            int seq1 = finishSequence_[finishLord_.get(0).getTablePosition()];
            int seq2 = finishSequence_[finishLord_.get(1).getTablePosition()];

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

        //记录下一轮贡牌数量
        Iterator<Player> iter;
        iter = finishLord_.iterator();
        while(iter.hasNext()) {
            tributeCount_[iter.next().getTablePosition()] = lordTribute;
        }
        iter = finishFarmer_.iterator();
        while(iter.hasNext()) {
            tributeCount_[iter.next().getTablePosition()] = farmerTribute;
        }
    }

    /**
     * 贡牌（自动）
     * 并确定选取贡牌及选取还牌的顺序
     */
//    public void tribute() {
//        tributeMap_.clear();
//        Map<Integer, Player> chooseSeq = new TreeMap<Integer, Player>();
//        Iterator iter;
//        int tribPlayerNum = 0; //贡牌玩家数
//
//        for(int i = 0; i < 4; i++) {
//            Player currPlayer = playerList_.get(i);
//            //需要贡牌则获取贡牌
//            if(currPlayer.getTribute() < 0) {
//                tributeMap_.putAll(currPlayer.getTributeCards());
//                tribPlayerNum++;
//            }
//            //不需要贡牌则取出上一盘的逃出顺序
//            //将顺序存在TreeMap中自动重新排序
//            else {
//                int seqTribute = table_.getFinishSequence(currPlayer);
//                chooseSeq.put(new Integer(seqTribute), currPlayer);
//            }
//        }
//
//        //填充收取贡牌顺序
//        iter = chooseSeq.entrySet().iterator();
//        int seqTribute = 0;
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//
//            Player currPlayer = (Player)entry.getValue();
//            table_.setChooseTributeSequence(currPlayer, seqTribute);
//            seqTribute++;
//        }
//
//        //计算并填充选取还牌顺序
//        //tributeMap会按照Card大小正向排序，因此反向设置顺序
//        iter = tributeMap_.entrySet().iterator();
//        int seqRevert = tribPlayerNum;
//        while (iter.hasNext()) {
//            Map.Entry entry = (Map.Entry) iter.next();
//
//            Player currPlayer = (Player)entry.getValue();
//            if(table_.getChooseRevertSequence(currPlayer) == -1) {
//                table_.setChooseRevertSequence(currPlayer, seqRevert);
//                seqRevert--;
//            }
//        }
//    }


    public Timer getTimer() {
        return timer_;
    }

    public void setTimer(Timer timer) {
        timer_ = timer;
    }

    public Table getTable() {
        return table_;
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
