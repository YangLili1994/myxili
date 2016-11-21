package com.hzyanglili1.myxili.mina;

import android.util.Log;

/**
 * Created by hzyanglili1 on 2016/11/14.
 */

public class Commands {

    //客户端接收到的服务器命令
    public static final String ReadTimeRequest = "68 10 00 10 00 68 5B 71 05 00 64 00 00 0D 0A 61 00 00 30 01 00 E0 BE 16";
    public static final String TerminalAddrSetRequest = "68 10 00 10 00 68 5B 71 05 00 64 00 00 0D 0A 61 00 00 30 01 00 E0 BE 16";
    public static final String ReadHeartPeriod = "68 10 00 10 00 68 5B 71 05 00 64 00 00 0D 0A 61 00 00 30 01 00 E0 BE 16";
    //客户端响应服务器命令
    public static final String TimeReCmd = "681600160068987105006400000D0A610000300100E0304913011116AF16";
    public static final String TerminalAddrSetRe = "681100110068807105006400000D04630000210100E000D016";
    public static final String HeartPeriodRe = "681100110068987105006400000D0A620000070100E015E816";//终端返回心跳周期15min

    //客户端发送的命令
    public static final String LoginCmd = "681200120068C97105006400000002700000001000E000010616";

    //客户端接收到的数据
    public static final String LoginCmdConfirm = "68 11 00 11 00 68 10 71 05 00 64 00 00 02 00 60 00 00 00 00 00 E0 00 2C 16";


    public static final String HeartCmd = "101010";

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

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
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
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
