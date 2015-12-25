package com.watch0ut.landlord.command.concrete;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 14-10-20.
 *
 * server<->client
 * 客户端或服务器发送的纯文本消息
 */
public class TextCommand extends AbstractCommand {

    //客户端需要统一所有文字信息均为\n结尾
    //public static final String LINE_DELIMITER = "\n";

    private static final char REPLACE_CHAR = '*';
    private static final String[] BLOCK_LIST = {"TMD", "TNND"};

    private String text_;

    public TextCommand() {
        text_ = "";
    }

    @Override
    public byte[] bodyToBytes() throws Exception {
        return text_.getBytes();
    }

    @Override
    public void bytesToBody(byte[] bytes) throws Exception {
        text_ = new String(bytes, Configuration.CHARSET);
    }

    private static String makeBlockString(int len) {
        String str = "";
        for(int i = 0; i < len; i++) {
            str += REPLACE_CHAR;
        }
        return str;
    }

    public void doFilter() {
        for(String str : BLOCK_LIST) {
            if(text_.contains(str)) {
                text_ = text_.replaceAll(str, makeBlockString(str.length()));
            }
        }
    }
}
