package com.wiser.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Objects;

import com.wiser.library.helper.WISERHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERApp {

	/**
	 * 获取app的版本code
	 *
	 * @return
	 */
	public static int getAppVersionCode() {
		int verCode = -1;
		try {
			verCode = WISERHelper.getInstance().getPackageManager().getPackageInfo(WISERHelper.getInstance().getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * 获取app的版本name
	 *
	 * @return
	 */
	public static String getAppVersionName() {
		String verName = "";
		try {
			verName = WISERHelper.getInstance().getPackageManager().getPackageInfo(WISERHelper.getInstance().getPackageName(), 0).versionName;
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
					if (!ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) break;
					else ip = null;
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

	/**
	 * 底部虚拟按键栏的高度
	 * 
	 * @param activity
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) public static int getSoftBottomBarHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		// 这个方法获取可能不是真实屏幕的高度
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int usableHeight = metrics.heightPixels;
		// 获取当前屏幕的真实高度
		activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
		int realHeight = metrics.heightPixels;
		if (realHeight > usableHeight) {
			return realHeight - usableHeight;
		} else {
			return 0;
		}
	}

	/**
	 * 获取屏幕分辨率
	 *
	 * @return
	 */
	public static int[] getScreenDisplay() {
		int width = getScreenWidth();// 手机屏幕的宽度
		int height = getScreenHeight();// 手机屏幕的高度
		return new int[] { width, height };
	}

	/**
	 * 获取屏幕高度
	 *
	 * @return
	 */
	public static int getScreenDPI() {
		int dpi = 0;
		WindowManager windowManager = (WindowManager) WISERHelper.getInstance().getSystemService(Context.WINDOW_SERVICE);
		assert windowManager != null;
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Class c;
		try {
			c = Class.forName("android.view.Display");
			Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi = displayMetrics.heightPixels;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dpi;
	}

	/**
	 * 获取虚拟键盘高度
	 *
	 * @return
	 */
	public static int getVirtualKeyboardHeight() {
		int totalHeight = getScreenDPI();
		int contentHeight = getScreenHeight();
		return totalHeight - contentHeight;
	}

	/**
	 * 获得屏幕的宽度
	 *
	 * @return
	 */
	public static int getScreenWidth() {
		return WISERHelper.getInstance().getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获得屏幕的高度
	 *
	 * @return
	 */
	public static int getScreenHeight() {
		return WISERHelper.getInstance().getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * dip转换成px
	 * 
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(float dpValue) {
		final float scale = WISERHelper.getInstance().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * px转换成dip
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(float pxValue) {
		final float scale = WISERHelper.getInstance().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值
	 * 
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(float pxValue) {
		final float fontScale = WISERHelper.getInstance().getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值
	 * 
	 * @param spValue
	 * @return
	 */
	public static int sp2px(float spValue) {
		final float fontScale = WISERHelper.getInstance().getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 根据Unicode编码判断中文汉字和符号
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 获取手机状态栏高度
	 */
	@SuppressLint("PrivateApi") public static int getStatusBarHeight() {
		Class<?> c;
		Object obj;
		Field field;
		int x, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = WISERHelper.getInstance().getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取设备id
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" }) public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		assert tm != null;
		return tm.getDeviceId();
	}

	/**
	 * 复制文本内容到剪切板
	 * 
	 * @param copyText
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT) public static void copyText(String copyText) {
		((ClipboardManager) Objects.requireNonNull(WISERHelper.getInstance().getSystemService(Context.CLIPBOARD_SERVICE))).setPrimaryClip(ClipData.newPlainText("text", copyText));
	}

	/**
	 * 为Activity设置横竖屏
	 * 
	 * @param act
	 */
	public static void setScreenOrientation(Activity act) {
		final DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		if (dm.widthPixels < dm.heightPixels) {
			act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	/**
	 * 跳转百度地图
	 *
	 * @param context
	 * @param location
	 * @param mapTitle
	 */
	public static void goBaiduMap(Context context, double[] location, String mapTitle) {
		Intent intent = null;
		if (WISERCheck.isAvilible(context, "com.baidu.BaiduMap")) {// 传入指定应用包名
			try {
				// intent =
				// Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
				intent = Intent.getIntent("intent://map/direction?" +
				// "origin=latlng:"+"34.264642646862,108.95108518068&" + //起点 此处不传值默认选择当前位置
						"destination=latlng:" + location[0] + "," + location[1] + "|name:" + mapTitle + // 终点
						"&mode=driving&" + // 导航路线方式
						"region=北京" + //
						"&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
				context.startActivity(intent); // 启动调用
			} catch (URISyntaxException e) {
				Log.e("intent", e.getMessage());
			}
		} else {// 未安装
			// market为路径，id为包名
			// 显示手机上所有的market商店
			Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG).show();
			Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}

	/**
	 * 跳转高德地图
	 *
	 * @param context
	 * @param location
	 * @param mapTitle
	 */
	public static void goGaoDeMap(Context context, double[] location, String mapTitle) {
		Intent intent = null;
		if (WISERCheck.isAvilible(context, "com.autonavi.minimap")) {
			try {
				intent = Intent.getIntent("androidamap://navi?sourceApplication=慧医&poiname=" + mapTitle + "&lat=" + location[0] + "&lon=" + location[1] + "&dev=0");
				context.startActivity(intent);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
			Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}

	/**
	 * 跳转Google地图
	 *
	 * @param context
	 * @param location
	 * @param mapTitle
	 */
	public static void goGoogleMap(Context context, double[] location, String mapTitle) {
		Intent intent = null;
		if (WISERCheck.isAvilible(context, "com.google.android.apps.maps")) {
			Uri gmmIntentUri = Uri.parse("google.navigation:q=" + location[0] + "," + location[1] + ", + Sydney +Australia");
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			mapIntent.setPackage("com.google.android.apps.maps");
			context.startActivity(mapIntent);
		} else {
			Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG).show();

			Uri uri = Uri.parse("market://details?id=com.google.android.apps.maps");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}

	/**
	 * 打开网页版百度地图
	 *
	 * @param slat
	 *            起点纬度
	 * @param slon
	 *            起点经度
	 * @param sname
	 *            poi名字
	 * @param dlat
	 *            终点纬度
	 * @param dlon
	 *            终点经度
	 * @param dname
	 *            mode=driving
	 * @param city
	 *            城市名
	 */
	public static void openBaiduWebMap(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
		Uri mapUri = Uri.parse(getWebBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname, String.valueOf(dlat), String.valueOf(dlon), dname, city, "游鱼"));
		Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
		loction.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(loction);
	}

	/**
	 * 网页版百度地图 有经纬度
	 *
	 * @param originLat
	 * @param originLon
	 * @param originName
	 *            ->注：必填
	 * @param desLat
	 * @param desLon
	 * @param destination
	 * @param region
	 *            : 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。-->注：必填，不填不会显示导航路线
	 * @param appName
	 * @return
	 */
	public static String getWebBaiduMapUri(String originLat, String originLon, String originName, String desLat, String desLon, String destination, String region, String appName) {
		String uri = "http://api.map.baidu.com/direction?origin=latlng:%1$s,%2$s|name:%3$s" + "&destination=latlng:%4$s,%5$s|name:%6$s&mode=driving&region=%7$s&output=html" + "&src=%8$s";
		return String.format(uri, originLat, originLon, originName, desLat, desLon, destination, region, appName);
	}

	/**
	 * 获取经纬度
	 *
	 * @param context
	 * @return
	 */
	public static Location getLocation(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		// List<String> lp = lm.getAllProviders();
		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		assert lm != null;
		String providerName = lm.getBestProvider(criteria, true);

		if (providerName != null) {
			@SuppressLint("MissingPermission")
			Location location = lm.getLastKnownLocation(providerName);
			if (location != null) {
				return location;
			}
		}
		return null;
	}

	/**
	 * Manifest中meta_data的字符串信息
	 *
	 * @param metaKey
	 * @return
	 */
	public static String getMetaInfo(String metaKey, String defaultValue) {
		PackageManager pManager = WISERHelper.getInstance().getPackageManager();
		ApplicationInfo appInfo;
		String msg = defaultValue;
		try {
			appInfo = pManager.getApplicationInfo(WISERHelper.getInstance().getPackageName(), PackageManager.GET_META_DATA);
		} catch (PackageManager.NameNotFoundException e) {
			return msg;
		}
		if (appInfo != null && appInfo.metaData != null) {

			Object obj = appInfo.metaData.get(metaKey);
			if (obj != null && !TextUtils.isEmpty(obj.toString())) {
				msg = obj.toString();
			}
		}
		return msg;
	}

	/**
	 * 获取网络状态
	 *
	 * @return
	 */
	public static String getNetType() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) WISERHelper.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		assert mConnectivityManager != null;
		@SuppressLint("MissingPermission")
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
		if (info == null || !info.isConnectedOrConnecting()) {
			return "无网";
		}
		switch (info.getType()) {
			case ConnectivityManager.TYPE_WIFI:
			case ConnectivityManager.TYPE_WIMAX:
			case ConnectivityManager.TYPE_ETHERNET:
				return "WiFi";
			case ConnectivityManager.TYPE_MOBILE:
				switch (info.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_LTE: // 4G
					case TelephonyManager.NETWORK_TYPE_HSPAP:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
						return "4G";
					case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
						return "3G";
					case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
					case TelephonyManager.NETWORK_TYPE_EDGE:
						return "2G";
				}
				break;
		}
		return "无网";
	}

	/**
	 * 获取Imei
	 * 
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" }) public static String getImei() {

		String imei = "";
		if (ActivityCompat.checkSelfPermission(WISERHelper.getInstance(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
			TelephonyManager tm = (TelephonyManager) WISERHelper.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
			assert tm != null;
			imei = tm.getDeviceId();
		}
		return imei;
	}

}
