package com.hzyanglili1.myxili;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hzyanglili1.myxili.mina.MinaThread;
import com.hzyanglili1.myxili.utils.VolleyHelper;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    public static final String myUrl = "http://183.247.165.39:9988";

    public static final String LoginCmd = "681200120068C97105006400000002700000001000E000010616";
    public static final String LoginCmdConfirm = "68 11 00 11 00 68 10 71 05 00 64 00 00 02 00 60 00 00 00 00 00 E0 00 2C 16";
    public static final String ReadTimeRequest = "68 10 00 10 00 68 5B 71 05 00 64 00 00 0D 0A 61 00 00 30 01 00 E0 BE 16";
    public static final String HeartCmd = "101010";


    //socket连接
    public static Socket clientSocket = null;
    String result = null;
    private Boolean hasLogin = false;
    InputStream inputStream = null;
    OutputStream outputStream = null;

    private Button btURLConnection = null;
    private Button btHttpClient = null;
    private Button btVolley = null;
    private Button btSocket = null;
    private Button btMina = null;

    private TextView textView = null;

    private EditText username = null;
    private EditText password = null;

    private StringBuilder response = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case SHOW_RESPONSE:
//                    String response = (String)msg.obj;
//                    textView.setText(response);
//                    break;
//                default:break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewAndEvent();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    void initViewAndEvent() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        textView = (TextView) findViewById(R.id.responseText);

        btSocket = (Button) findViewById(R.id.bySocket);
        btSocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("haha", "开始socket连接");
                sendBySocket();
            }
        });

        btMina = (Button) findViewById(R.id.byMina);
        btMina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("haha",TAG+"开启mina线程");
                new MinaThread().start();
            }
        });
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }

            stringBuilder.append(hv).append(" ");
        }
        return stringBuilder.toString().trim().toUpperCase();
    }


    void sendBySocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final SocketAddress socketAddress = new InetSocketAddress("183.247.165.39", 9988);
                    clientSocket = new Socket();
                    clientSocket.connect(socketAddress, 8000);

                    outputStream = clientSocket.getOutputStream();
                    inputStream = clientSocket.getInputStream();
                    //发送登录帧
                    outputStream.write(hexStringToBytes(LoginCmd));
                    outputStream.flush();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //一直读取返回帧
                            while (true) {
                                byte[] buffer = new byte[30];
                                int readBytes = 0;
                                //读取返回帧
                                try {
                                    inputStream.read(buffer);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Log.d("haha","读取IO异常");
                                }
                                result = bytesToHexString(buffer);

                                if (result.contains(LoginCmdConfirm)){
                                    Log.d("haha","收到登录确认帧");
                                    hasLogin = true;
                                }else if (result.contains(ReadTimeRequest)){
                                    Log.d("haha","收到请求时间");
                                }else {
                                    Log.d("haha",result);
                                }
                            }
                        }
                    }).start();

                    //每5min发送一次心跳包
                    final OutputStream finalOutputStream = outputStream;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                if (hasLogin) {
                                    try {
                                        Thread.sleep(1000*60*5);
                                        finalOutputStream.write(hexStringToBytes(HeartCmd));
                                        finalOutputStream.flush();
                                        Log.d("haha", "已发送心跳包");
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }).start();

                    // outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
