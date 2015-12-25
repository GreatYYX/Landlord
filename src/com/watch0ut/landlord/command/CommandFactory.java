package com.watch0ut.landlord.command;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GreatYYX on 14-10-20.
 */
public class CommandFactory {

    private static final String PACKAGE = "com.watch0ut.landlord.command.concrete.";

    private CommandFactory() {
    }


    /**
     * 利用反射创建类
     * @param name
     * @return
     */
    public static AbstractCommand newCommand(String name) {
        if(StringUtils.isNotEmpty(name)) {
            String className = PACKAGE + name + "Command";
            try {
                Class c = Class.forName(className);
                AbstractCommand cmd = (AbstractCommand)c.newInstance();
                return cmd;
            } catch(Exception e) {
                //e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
