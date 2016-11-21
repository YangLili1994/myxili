package com.hzyanglili1.myxili.mina;

import android.icu.text.LocaleDisplayNames;
import android.util.Log;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by hzyanglili1 on 2016/11/14.
 */

public class MinaThread extends Thread{


    public static final String LocalIp = "10.240.169.56";
    public static final int LocalPort = 3355;

    public static final String ServerIp = "183.247.165.39";
    public static final int ServerPort = 9988;


    private String TAG = "MinaThread";

    private IoSession session = null;
    private IoConnector connector = null;

    @Override
    public void run() {
        super.run();
        Log.d("haha",TAG+"客户端开始连接");
        //获取连接
        connector = new NioSocketConnector();
        //设置连接超时
        connector.setConnectTimeoutMillis(10000);
        //添加过滤器
       // connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        //connector.getFilterChain().addLast("mycodec",new ProtocolCodecFilter(new MyTextLineCodecFactory()));

        connector.getFilterChain().addLast(
                "codec",new ProtocolCodecFilter(new MyTextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        //设置处理器
        connector.setHandler(new MinaClientHandler());

        //设置ip和端口
       // connector.setDefaultRemoteAddress(new InetSocketAddress(LocalIp,LocalPort));
        connector.setDefaultRemoteAddress(new InetSocketAddress(ServerIp,ServerPort));
        //监听客户端是否断线
        connector.addListener(new IoListener(){
            @Override
            public void sessionDestroyed(IoSession ioSession) throws Exception {
                super.sessionDestroyed(ioSession);

                //断开重连

                int failCount = 1;
                while (true){
                    Thread.sleep(5000);
                    ConnectFuture future = connector.connect();
                    //等待连接创建完成
                    future.awaitUninterruptibly();
                    //获取session
                    session = future.getSession();

                    if (session != null && session.isConnected()){
                        //重连成功 发送登录报文
                        Log.d("haha",TAG+"重新建立连接");
                        session.write(IoBuffer.wrap(Commands.hexStringToBytes(Commands.LoginCmd)));
                        break;
                    }else {
                        Log.d("haha",TAG+"重连失败次数："+(failCount++));
                    }
                }
            }
        });

        ConnectFuture connectFuture = connector.connect();
        connectFuture.awaitUninterruptibly();
        session = connectFuture.getSession();
        if (session != null && session.isConnected()){
            //连接成功  发送登录报文
            session.write(IoBuffer.wrap(Commands.hexStringToBytes(Commands.LoginCmd)));

        }else {
            Log.d("haha",TAG+"连接服务器失败");
        }
    }



}
