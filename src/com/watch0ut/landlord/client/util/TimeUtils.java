package com.watch0ut.landlord.client.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类，提供常用一些函数
 *
 * Created by Jack on 16/2/21.
 */
public class TimeUtils {

    public static String getTime() {
        Date nowTime = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(nowTime);
    }
}
