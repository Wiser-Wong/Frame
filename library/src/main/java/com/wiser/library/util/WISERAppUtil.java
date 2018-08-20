package com.wiser.library.util;

import android.content.Context;
import android.content.pm.PackageManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERAppUtil {

    /**
     * 获取app的版本code
     *
     * @param context
     * @return
     */
    public static int getAppVersionCode(Context context) {
        int verCode = -1;
        if (context == null) return verCode;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }


    /**
     * 获取app的版本name
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String verName = "";
        if (context == null) return verName;
        try {
            verName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    /**
     * 获取IP地址
     *
     * @return IP地址
     */
    private static InetAddress getLocalNetAddress() {
        InetAddress ip = null;
        try {
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {
                NetworkInterface ni = en_netInterface.nextElement();
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":"))
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;

    }

    /**
     * 通过ip地址获得Mac
     *
     * @return Mac地址
     */
    public static String getMacForip() {
        String strMacAddress = null;
        try {
            InetAddress ip = getLocalNetAddress();

            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }

                String str = Integer.toHexString(b[i] & 0xFF).toUpperCase();
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddress = buffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strMacAddress;
    }
}
