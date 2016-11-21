package com.hzyanglili1.myxili.mina;

import android.util.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * Created by hzyanglili1 on 2016/11/14.
 */

public class MinaClientHandler extends IoHandlerAdapter{

    private String TAG = "MinaClientHandler";

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        Log.e("haha",TAG+"客户端发生异常");
        cause.printStackTrace();
        super.exceptionCaught(session, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

       // Log.d("haha",TAG+"客户端接收到了消息:"+new String((byte[])message));

//        IoBuffer ioBuffer = (IoBuffer)message;
//        byte[] b = new byte[ioBuffer.limit()];
//        ioBuffer.get(b);

        //Log.d("haha",TAG+"客户端接收到的消息为:"+message.toString());

        String msg = Commands.bytesToHexString((byte[]) message);

       // session.write(IoBuffer.wrap((byte[]) message));

        Log.d("haha",TAG+"客户端接收到的消息zhuanhuan为:"+msg);
//
//        if (msg.equals(Commands.LoginCmdConfirm)){
//            //为登录成功确认帧---登录成功
//            Log.d("haha",TAG+"登录成功");
//        }else if (msg.equals(Commands.ReadTimeRequest)){
//            //服务器请求读取召测终端时间 终端返回时间为16年11月01日13时49分30秒
//            Log.d("haha",TAG+"服务器请求召测终端时间");
//            session.write(Commands.hexStringToBytes(Commands.TimeReCmd));
//        }else if (msg.equals(Commands.TerminalAddrSetRequest)){
//            //服务器请求设置终端地址
//            Log.d("haha",TAG+"服务器请求设置终端地址");
//            session.write(Commands.hexStringToBytes(Commands.TerminalAddrSetRe));
//        }else if (msg.equals(Commands.ReadHeartPeriod)){
//            //服务器请求读取心跳周期
//            Log.d("haha",TAG+"服务器请求读取心跳周期");
//            session.write(Commands.hexStringToBytes(Commands.HeartPeriodRe));//服务器返回心跳周期15min
//        }

        super.messageReceived(session, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log.d("haha",TAG+"客户端发送数据成功");
        super.messageSent(session, message);
    }


}
