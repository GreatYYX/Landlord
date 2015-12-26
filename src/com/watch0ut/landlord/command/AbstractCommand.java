package com.watch0ut.landlord.command;

/**
 * Created by GreatYYX on 14-10-20.
 *
 * 所有实例类必须存在无参数构造（用于工厂方法）
 */
public abstract class AbstractCommand {

    /**
     * 属性转换为bytes[]用于网络传输
     * @return
     * @throws Exception
     */
    public abstract byte[] bodyToBytes() throws Exception;

    /**
     * 传输协议中的bytes[]转换为具体的属性
     * @param bytes
     * @throws Exception
     */
    public abstract void bytesToBody(byte[] bytes) throws Exception;

    /**
     * 获取类名（去掉包名及右侧的Command）
     * 类必须位于包com.watch0ut.landlord.command.concrete中
     * @return
     */
    public String getName() {
        String name = this.getClass().getName();
        int start = name.lastIndexOf('.') + 1;
        int end = name.length() - 7; //"Command"
        name = name.substring(start, end);
        return name;
    }
}
