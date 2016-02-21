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
//    public static final String CLIENT_VERSION = "1.0.0";
    public static final long PLAY_DURATION = 30 * 1000L; //每个Player出牌时间
    public static final long PLAY_DURATION_SERVER = 35 * 1000L; //服务器上多留5s再超时
    public static final long CLIENT_START_DELAY = 3 * 1000L; // 发牌动画时间
    public static final int TABLE_PER_HALL = 8; //大厅中牌桌数
}
