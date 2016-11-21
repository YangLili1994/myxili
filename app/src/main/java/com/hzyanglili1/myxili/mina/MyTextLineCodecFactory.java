package com.hzyanglili1.myxili.mina;

/**
 * Created by hzyanglili1 on 2016/11/15.
 */

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

import java.nio.charset.Charset;


/**
 * @author BruceYang
 *
 */
public class MyTextLineCodecFactory extends TextLineCodecFactory {

    private ByteArrayDecoder decoder;
    private TextLineEncoder encoder;

    public MyTextLineCodecFactory(Charset charset) {

        this.encoder = new TextLineEncoder(charset, LineDelimiter.UNIX);
        this.decoder = new ByteArrayDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) {
        return this.encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) {
        return this.decoder;
    }

}

