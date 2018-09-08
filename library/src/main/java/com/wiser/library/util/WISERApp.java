package com.wiser.library.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Objects;

import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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

}