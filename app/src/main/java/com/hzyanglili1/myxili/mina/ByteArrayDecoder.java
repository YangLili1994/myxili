package com.hzyanglili1.myxili.mina;

/**
 * Created by hzyanglili1 on 2016/11/15.
 */

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.io.InputStream;


public class ByteArrayDecoder extends ProtocolDecoderAdapter {

    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

        InputStream inputStream = ioBuffer.asInputStream();
        int limit = ioBuffer.limit();
        byte[] bytes = new byte[limit];

        inputStream.read(bytes);

        protocolDecoderOutput.write(bytes);
    }
}
