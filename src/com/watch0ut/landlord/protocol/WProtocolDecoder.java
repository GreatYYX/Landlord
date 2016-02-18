package com.watch0ut.landlord.protocol;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.CommandFactory;

/**
 * Created by GreatYYX on 14-10-20.
 */
public class WProtocolDecoder extends CumulativeProtocolDecoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(WProtocolDecoder.class);

    @Override
    protected boolean doDecode(IoSession sess, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        if(in.prefixedDataAvailable(WProtocolFactory.COMMAND_PREFIX_LENGTH, WProtocolFactory.COMMAND_MAX_DATA_LENGTH)) {

            //获取data
            int length = in.getInt(); //取出prefix
            byte[] bytes = new byte[length];
            in.get(bytes);

            //获取指令名
            int cmdNameLength = WProtocolFactory.COMMAND_NAME_LENGTH;
            byte[] cmdNameBytes = new byte[cmdNameLength];
            System.arraycopy(bytes, 0, cmdNameBytes, 0, cmdNameLength);
            String cmdName = StringUtils.trim(new String(cmdNameBytes)); //去空格

            //创建指令体
            AbstractCommand cmdBody = CommandFactory.newCommand(cmdName);
            if (cmdBody != null) {
                int cmdBodyLength = length - cmdNameLength;
                byte[] cmdBodyBytes = new byte[cmdBodyLength];
                System.arraycopy(bytes, cmdNameLength, cmdBodyBytes, 0, cmdBodyLength);
                //bodyBytes转body填充类并继续传输
                cmdBody.bytesToBody(cmdBodyBytes);
                out.write(cmdBody);

                LOGGER.info("[SESS {}] Command \"{}\" received.", sess.getId(), cmdName);
            } else {
                LOGGER.info("[SESS {}] Unknown \"{}\" command received, skip.", sess.getId(), cmdName);
            }

            return true;
        }
        return false;
    }
}
