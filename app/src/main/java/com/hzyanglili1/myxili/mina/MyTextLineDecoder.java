package com.hzyanglili1.myxili.mina;

/**
 * Created by hzyanglili1 on 2016/11/15.
 */

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;

import java.nio.charset.Charset;

/**
 *  编码器将数据直接发出去(不做处理)
 */
public class MyTextLineDecoder extends TextLineDecoder {

    public MyTextLineDecoder(Charset charset) {
        super(charset);
    }

    public MyTextLineDecoder(LineDelimiter delimiter) {
        super(delimiter);
    }

    public MyTextLineDecoder(String delimiter) {
        super(delimiter);
    }

    public MyTextLineDecoder() {
    }

    public MyTextLineDecoder(Charset charset, String delimiter) {
        super(charset, delimiter);
    }

    public MyTextLineDecoder(Charset charset, LineDelimiter delimiter) {
        super(charset, delimiter);
    }

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {

        /*--------在当前buffer后面追加一个'/n' -------*/

//        in.setAutoExpand(true);//设置自动扩展
//        in.position(in.limit());
////        in.putChar('\r');
////        in.putChar('\n');
////        in.put((byte)'\r');
//        in.put((byte)'\n');
//        in.position(0);

        super.decode(session, in, out);
    }
}