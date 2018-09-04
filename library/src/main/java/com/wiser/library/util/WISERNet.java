package com.wiser.library.util;

import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.telephony.TelephonyManager;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERNet {

	private static final int	NETWORK_TYPE_UNAVAILABLE	= -1;

	// private static final int NETWORK_TYPE_MOBILE = -100;
	private static final int	NETWORK_TYPE_WIFI			= -101;

	private static final int	NETWORK_CLASS_WIFI			= -101;

	private static final int	NETWORK_CLASS_UNAVAILABLE	= -1;

	/**
	 * Unknown network class.
	 */
	private static final int	NETWORK_CLASS_UNKNOWN		= 0;

	/**
	 * Class of broadly defined "2G" networks.
	 */
	private static final int	NETWORK_CLASS_2_G			= 1;

	/**
	 * Class of broadly defined "3G" networks.
	 */
	private static final int	NETWORK_CLASS_3_G			= 2;

	/**
	 * Class of broadly defined "4G" networks.
	 */
	private static final int	NETWORK_CLASS_4_G			= 3;

	// 适配低版本手机
	/**
	 * Current network is GPRS
	 */
	private static final int	NETWORK_TYPE_GPRS			= 1;

	/**
	 * Current network is EDGE
	 */
	private static final int	NETWORK_TYPE_EDGE			= 2;

	/**
	 * Current network is UMTS
	 */
	private static final int	NETWORK_TYPE_UMTS			= 3;

	/**
	 * Current network is CDMA: Either IS95A or IS95B
	 */
	private static final int	NETWORK_TYPE_CDMA			= 4;

	/**
	 * Current network is EVDO revision 0
	 */
	private static final int	NETWORK_TYPE_EVDO_0			= 5;

	/**
	 * Current network is EVDO revision A
	 */
	private static final int	NETWORK_TYPE_EVDO_A			= 6;

	/**
	 * Current network is 1xRTT
	 */
	private static final int	NETWORK_TYPE_1xRTT			= 7;

	/**
	 * Current network is HSDPA
	 */
	private static final int	NETWORK_TYPE_HSDPA			= 8;

	/**
	 * Current network is HSUPA
	 */
	private static final int	NETWORK_TYPE_HSUPA			= 9;

	/**
	 * Current network is HSPA
	 */
	private static final int	NETWORK_TYPE_HSPA			= 10;

	/**
	 * Current network is iDen
	 */
	private static final int	NETWORK_TYPE_IDEN			= 11;

	/**
	 * Current network is EVDO revision B
	 */
	private static final int	NETWORK_TYPE_EVDO_B			= 12;

	/**
	 * Current network is LTE
	 */
	private static final int	NETWORK_TYPE_LTE			= 13;

	/**
	 * Current network is eHRPD
	 */
	private static final int	NETWORK_TYPE_EHRPD			= 14;

	/**
	 * Current network is HSPA+
	 */
	private static final int	NETWORK_TYPE_HSPAP			= 15;

	/**
	 * 判断当前网络是否连接
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		assert cm != null;
		@SuppressLint("MissingPermission")
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取运营商
	 *
	 * @return
	 */
	public static String getProvider(Context context) {
		String provider = "未知";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			assert telephonyManager != null;
			@SuppressLint("MissingPermission")
			String IMSI = telephonyManager.getSubscriberId();
			if (IMSI == null) {
				if (TelephonyManager.SIM_STATE_READY == telephonyManager.getSimState()) {
					String operator = telephonyManager.getSimOperator();
					if (operator != null) {
						if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
							provider = "中国移动";
						} else if (operator.equals("46001")) {
							provider = "中国联通";
						} else if (operator.equals("46003")) {
							provider = "中国电信";
						}
					}
				}
			} else {
				if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
					provider = "中国移动";
				} else if (IMSI.startsWith("46001")) {
					provider = "中国联通";
				} else if (IMSI.startsWith("46003")) {
					provider = "中国电信";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}

	/**
	 * 获取网络类型
	 *
	 * @return
	 */
	public static String getCurrentNetworkType(Context context) {
		int networkClass = getNetworkClass(context);
		String type = "未知";
		switch (networkClass) {
			case NETWORK_CLASS_UNAVAILABLE:
				type = "无";
				break;
			case NETWORK_CLASS_WIFI:
				type = "Wi-Fi";
				break;
			case NETWORK_CLASS_2_G:
				type = "2G";
				break;
			case NETWORK_CLASS_3_G:
				type = "3G";
				break;
			case NETWORK_CLASS_4_G:
				type = "4G";
				break;
			case NETWORK_CLASS_UNKNOWN:
				type = "未知";
				break;
		}
		return type;
	}

	private static int getNetworkClassByType(int networkType) {
		switch (networkType) {
			case NETWORK_TYPE_UNAVAILABLE:
				return NETWORK_CLASS_UNAVAILABLE;
			case NETWORK_TYPE_WIFI:
				return NETWORK_CLASS_WIFI;
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
			case NETWORK_TYPE_1xRTT:
			case NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case NETWORK_TYPE_UMTS:
			case NETWORK_TYPE_EVDO_0:
			case NETWORK_TYPE_EVDO_A:
			case NETWORK_TYPE_HSDPA:
			case NETWORK_TYPE_HSUPA:
			case NETWORK_TYPE_HSPA:
			case NETWORK_TYPE_EVDO_B:
			case NETWORK_TYPE_EHRPD:
			case NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}

	private static int getNetworkClass(Context context) {
		int networkType = NETWORK_CLASS_UNKNOWN;
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			assert connectivity != null;
			@SuppressLint("MissingPermission")
			NetworkInfo network = connectivity.getActiveNetworkInfo();
			if (network != null && network.isAvailable() && network.isConnected()) {
				int type = network.getType();
				if (type == ConnectivityManager.TYPE_WIFI) {
					networkType = NETWORK_TYPE_WIFI;
				} else if (type == ConnectivityManager.TYPE_MOBILE) {
					TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
					assert telephonyManager != null;
					networkType = telephonyManager.getNetworkType();
				}
			} else {
				networkType = NETWORK_CLASS_UNAVAILABLE;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return getNetworkClassByType(networkType);
	}

	/**
	 * 判断wifi 是否可用
	 *
	 * @param context
	 * @return
	 * @throws Exception
	 */
	@SuppressLint("MissingPermission") public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable;
		assert connectivityManager != null;
		isWifiDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}

	/**
	 * 获取网速
	 * 
	 * @param activity
	 * @return
	 */
	@SuppressLint("WrongConstant") public static long getUidRxBytes(Activity activity) { // 获取总的接受字节数，包含Mobile和WiFi等
		PackageManager pm = activity.getPackageManager();
		ApplicationInfo ai = null;
		try {
			ai = pm.getApplicationInfo(WISERHelper.getInstance().getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		if (ai != null) return TrafficStats.getUidRxBytes(ai.uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);

		return TrafficStats.UNSUPPORTED;
	}
}
