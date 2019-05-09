package com.wiser.library.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

public class WISERMacAddress {

	/**
	 * 获取本机Mac地址
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		String strMac;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {// 6.0以下
			strMac = getLocalMacAddressFromWifiInfo();
			return strMac;
		} else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {// 6.0以上7.0以下
			strMac = getMacAddress6To7();
			return strMac;
		} else {// 7.0以上
			if (!TextUtils.isEmpty(getMacAddress6To7())) {// 7.0以上1
				strMac = getMacAddress6To7();
				return strMac;
			} else if (!TextUtils.isEmpty(getMachineHardwareAddress())) {// 7.0以上2
				strMac = getMachineHardwareAddress();
				return strMac;
			} else {// 7.0以上3
				strMac = getLocalMacAddressFromBusybox();
				return strMac;
			}
		}
	}

	/**
	 * 根据wifi信息获取本地mac
	 *
	 * @return
	 */
	@SuppressLint("HardwareIds") public static String getLocalMacAddressFromWifiInfo() {
		WifiManager wifi = (WifiManager) WISERHelper.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifi.getConnectionInfo();
		return wifiInfo.getMacAddress();
	}

	/**
	 * android 6.0及以上、7.0以下 获取mac地址
	 *
	 * @return
	 */
	public static String getMacAddress6To7() {
		// 如果是6.0以下，直接通过wifimanager获取
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			String macAddress0 = getMacAddressFromWifiManage();
			if (!TextUtils.isEmpty(macAddress0)) {
				return macAddress0;
			}
		}
		String str = "";
		String macSerial = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if ("".equals(macSerial)) {
			try {
				return loadFileAsString().toUpperCase().substring(0, 17);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return macSerial;
	}

	@SuppressLint("HardwareIds") private static String getMacAddressFromWifiManage() {
		if (isAccessWifiStateAuthorized()) {
			WifiManager wifiMgr = (WifiManager) WISERHelper.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo;
			try {
				wifiInfo = wifiMgr.getConnectionInfo();
				return wifiInfo.getMacAddress();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Check whether accessing wifi state is permitted
	 *
	 * @return
	 */
	private static boolean isAccessWifiStateAuthorized() {
		if (PackageManager.PERMISSION_GRANTED == WISERHelper.getInstance().checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
			return true;
		} else return false;
	}

	private static String loadFileAsString() throws Exception {
		FileReader reader = new FileReader("/sys/class/net/eth0/address");
		String text = loadReaderAsString(reader);
		reader.close();
		return text;
	}

	private static String loadReaderAsString(Reader reader) throws Exception {
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[4096];
		int readLength = reader.read(buffer);
		while (readLength >= 0) {
			builder.append(buffer, 0, readLength);
			readLength = reader.read(buffer);
		}
		return builder.toString();
	}

	/**
	 * android 7.0及以上 （2）扫描各个网络接口获取mac地址
	 *
	 */
	/**
	 * 获取设备HardwareAddress地址
	 *
	 * @return
	 */
	public static String getMachineHardwareAddress() {
		Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		String hardWareAddress = null;
		NetworkInterface iF = null;
		if (interfaces == null) {
			return null;
		}
		while (interfaces.hasMoreElements()) {
			iF = interfaces.nextElement();
			try {
				hardWareAddress = bytesToString(iF.getHardwareAddress());
				if (hardWareAddress != null) break;
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		return hardWareAddress;
	}

	/***
	 * byte转为String
	 *
	 * @param bytes
	 * @return
	 */
	private static String bytesToString(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		StringBuilder buf = new StringBuilder();
		for (byte b : bytes) {
			buf.append(String.format("%02X:", b));
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * android 7.0及以上 （3）通过busybox获取本地存储的mac地址
	 *
	 */

	/**
	 * 根据busybox获取本地Mac
	 *
	 * @return
	 */
	public static String getLocalMacAddressFromBusybox() {
		String result = "";
		String Mac = "";
		result = callCmd("busybox ifconfig", "HWaddr");
		// 对该行数据进行解析
		// 例如：eth0 Link encap:Ethernet HWaddr 00:16:E8:3E:DF:67
		if (result.length() > 0 && result.contains("HWaddr")) {
			Mac = result.substring(result.indexOf("HWaddr") + 6, result.length() - 1);
			result = Mac;
		}
		return result;
	}

	private static String callCmd(String cmd, String filter) {
		StringBuilder result = new StringBuilder();
		String line = "";
		try {
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStreamReader is = new InputStreamReader(proc.getInputStream());
			BufferedReader br = new BufferedReader(is);

			while ((line = br.readLine()) != null && !line.contains(filter)) {
				result.append(line);
			}

			assert line != null;
			result = new StringBuilder(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
}
