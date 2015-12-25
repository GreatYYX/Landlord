package com.watch0ut.landlord.protocol;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.watch0ut.landlord.command.AbstractCommand;

/**
 * Created by GreatYYX on 14-10-20.
 */
public class WProtocolEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession sess, Object msg, ProtocolEncoderOutput out) throws Exception {

        //获取填充长度
        AbstractCommand cmd = (AbstractCommand)msg;
        byte[] cmdBodyBytes = cmd.bodyToBytes();
        int cmdNameLength = WProtocolFactory.COMMAND_NAME_LENGTH;
        int length = cmdNameLength + cmdBodyBytes.length;
        byte[] bytes = new byte[length];

        //名字填充空格
        String cmdName = StringUtils.rightPad(cmd.getName(), cmdNameLength, WProtocolFactory.COMMAND_NAME_PAD_CHAR);

        //填充name和body
        System.arraycopy(cmdName.getBytes(), 0, bytes, 0, cmdNameLength);
        System.arraycopy(cmdBodyBytes, 0, bytes, cmdNameLength, cmdBodyBytes.length);

        //加上prefix完整填充
        IoBuffer buf = IoBuffer.allocate(bytes.length + WProtocolFactory.COMMAND_PREFIX_LENGTH, false);
        buf.setAutoExpand(true);
        buf.putInt(bytes.length);
        buf.put(bytes);

        //传输
        buf.flip();
        out.write(buf);
    }
}
