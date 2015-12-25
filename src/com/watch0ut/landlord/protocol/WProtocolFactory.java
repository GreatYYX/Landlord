package com.watch0ut.landlord.protocol;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;

/**
 * Created by GreatYYX on 14-10-20.
 *
 * Protocol Structure
 * [ prefix(length) ][ command name ][   command body     ]
 * |<-     4      ->|<-    20     ->|<-    length-20    ->|
 * |<-     4      ->|<-         length (max 2048)       ->|
 *
 */
public class WProtocolFactory implements ProtocolCodecFactory {

    public static final int COMMAND_PREFIX_LENGTH = 4; //int长度
    public static final int COMMAND_NAME_LENGTH = 20;
    public static final int COMMAND_MAX_DATA_LENGTH = 2048;
    public static final char COMMAND_NAME_PAD_CHAR = ' ';

    private WProtocolEncoder encoder_;
    private WProtocolDecoder decoder_;

    public WProtocolFactory() {
        encoder_ = new WProtocolEncoder();
        decoder_ = new WProtocolDecoder();
    }

    @Override
    public WProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return encoder_;
    }

    @Override
    public WProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder_;
    }
}
