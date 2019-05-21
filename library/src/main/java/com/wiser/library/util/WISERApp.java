package com.wiser.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import com.wiser.library.helper.WISERHelper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
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
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
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
	 * 获取Mac地址
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		return WISERMacAddress.getMacAddress();
	}

	/**
	 * 获取IP地址 /192.168.0.111
	 *
	 * @return IP地址
	 */
	public static InetAddress getLocalNetAddress() {
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
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 *
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPenGPS(final Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}

		return false;
	}

	/**
	 * 获取设备唯一标识符
	 * 
	 * @return
	 */
	@SuppressLint({ "MissingPermission", "HardwareIds" }) public static String getDeviceId() {

		Context context = WISERHelper.getInstance();
		String imei = "";
		String androidId = "";
		String macAddress = "";

		// imei
		if (ActivityCompat.checkSelfPermission(WISERHelper.getInstance(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
			TelephonyManager tm = (TelephonyManager) WISERHelper.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
			imei = tm.getDeviceId();
		}
		// Android id
		ContentResolver contentResolver = context.getContentResolver();
		if (contentResolver != null) {
			androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
		}
		// mac
		macAddress = getMacAddress();

		StringBuilder longIdBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(imei)) {
			longIdBuilder.append(imei);
		}
		if (StringUtils.isNotBlank(androidId)) {
			longIdBuilder.append(androidId);
		}
		if (StringUtils.isNotBlank(macAddress)) {
			longIdBuilder.append(macAddress);
		}
		return longIdBuilder.toString();
	}

	/**
	 * 获取IP地址 192.168.0.111
	 *
	 * @return IP地址
	 */
	public static String getIpAddress() {
		@SuppressLint("MissingPermission")
		NetworkInfo info = ((ConnectivityManager) WISERHelper.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			// 3/4g网络
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}

			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
				// wifi网络
				WifiManager wifiManager = (WifiManager) WISERHelper.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				return intIP2StringIP(wifiInfo.getIpAddress());
			} else if (info.getType() == ConnectivityManager.TYPE_ETHERNET) {
				// 有限网络
				return getLocalIp();
			}
		}
		return null;
	}

	private static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + (ip >> 24 & 0xFF);
	}

	// 获取有限网IP
	private static String getLocalIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return "0.0.0.0";
	}

	/**
	 * 通过网络接口取
	 * 
	 * @return
	 */
	public static String getMacForNet() {
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;
				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return null;
				}
				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:", b));
				}
				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
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

	/**
	 * 显示虚拟按键
	 */
	public static void showVirtualKey(Activity activity) {
		if (activity == null) return;
		// 显示虚拟按键
		if (Build.VERSION.SDK_INT < 19) {
			// 低版本sdk
			View v = activity.getWindow().getDecorView();
			v.setSystemUiVisibility(View.VISIBLE);
		} else {
			View decorView = activity.getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	/**
	 * 隐藏虚拟按键
	 */
	public static void hideVirtualKey(Activity activity) {
		// 隐藏虚拟按键
		if (Build.VERSION.SDK_INT < 19) {
			View v = activity.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			View decorView = activity.getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

}
