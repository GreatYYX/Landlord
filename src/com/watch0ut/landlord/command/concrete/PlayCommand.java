package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.object.cardtype.CardType;

import java.util.Random;

/**
 * Created by GreatYYX on 2/19/16.
 *
 * server<-client
 * 客户端出牌
 * 客户端需要等到相同seqId的PlayResponse回复后才能认为出牌成功，否则此轮不出
 *
 */
public class PlayCommand extends AbstractCommand {
    private CardType cardType_;
    private String seqId_; // 用于确保Play与PlayResponse命令的对应，客户端使用generateSeqId生成

    public PlayCommand() {

    }

    public PlayCommand(String seqId, CardType cardType) {
        seqId_ = seqId;
        cardType_ = cardType;
    }

    public CardType getCardType() {
        return cardType_;
    }

    public String getSeqId() {
        return seqId_;
    }

    public static String generateSeqId(int uid) {
        StringBuilder seqId = new StringBuilder();
        Random rand = new Random();
        int idx;
        for(int i = 0; i < 5; i++) {
            idx = rand.nextInt(26);
            seqId.append((char)(idx + 'a'));
        }
        return seqId.toString();
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return new byte[0];
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {

    }
}
