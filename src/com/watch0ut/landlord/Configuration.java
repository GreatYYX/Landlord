package com.watch0ut.landlord;

import java.nio.charset.Charset;

/**
 * Created by GreatYYX on 14-10-21.
 */
public class Configuration {
    public static final Charset CHARSET = Charset.forName("UTF-8");
    public static final String SEVER_HOST = "127.0.0.1";
    public static final int SEVER_PORT = 12221;
    public static final long CLIENT_CONNECT_TIMEOUT = 3 * 1000L;
    public static final float PLAY_DURATION = 20; //每个Player出牌时间
    public static final int TABLE_PER_HALL = 8; //大厅中牌桌数
}
