package com.wiser.library.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.LocationManager;
import android.os.Build;
import android.text.TextUtils;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERCheck {

	/**
	 * 验证
	 *
	 * @param <T>
	 *            参数
	 *
	 * @param service
	 *            参数
	 * @param <T>
	 *            参数
	 */
	public static <T> void validateServiceInterface(Class<T> service) {
		if (!service.isInterface()) {
			throw new IllegalArgumentException("该类不是接口");
		}
	}

	/**
	 * 验证
	 *
	 * @param <T>
	 *            参数
	 *
	 * @param service
	 *            参数
	 * @param <T>
	 *            参数
	 */
	public static <T> void validateServiceImlp(Class<T> service) {
		if (service.isInterface()) {
			throw new IllegalArgumentException("该类不是实现类,需要接口类");
		}
	}

	/**
	 * 检查是否为空
	 *
	 * @param <T>
	 *            参数
	 *
	 * @param reference
	 *            参数
	 *
	 * @param errorMessageTemplate
	 *            参数
	 * @return 返回值
	 */
	public static <T> void checkNotNull(T reference, String errorMessageTemplate) {
		if (reference == null) {
			throw new NullPointerException(errorMessageTemplate);
		}
	}

	/**
	 * 检查是否为空
	 *
	 * @param <T>
	 *            参数
	 * @param reference
	 *            参数
	 *
	 * @param errorMessageTemplate
	 *            参数
	 * @return 返回值
	 */
	public static <T> T checkUINotNull(T reference, String errorMessageTemplate) {
		if (reference == null) {
			throw new NullPointerException(errorMessageTemplate);
		}
		return reference;
	}

	/**
	 * 检查参数
	 *
	 * @param expression
	 *            参数
	 * @param errorMessageTemplate
	 *            参数
	 */
	public static void checkArgument(boolean expression, String errorMessageTemplate) {
		if (!expression) {
			throw new IllegalArgumentException(errorMessageTemplate);
		}
	}

	/**
	 * 检查是否越界
	 *
	 * @param index
	 *            参数
	 * @param size
	 *            参数
	 * @param desc
	 *            参数
	 */
	public static void checkPositionIndex(int index, int size, String desc) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(desc);
		}
	}

	/**
	 * 判断是否相同
	 *
	 * @param a
	 *            参数
	 * @param b
	 *            参数
	 * @return 返回值
	 */
	public static boolean equal(Object a, Object b) {
		return a == b || (a != null && a.equals(b));
	}

	/**
	 * 判断是否为空
	 *
	 * @param text
	 *            参数
	 * @return 返回值
	 */
	public static boolean isEmpty(CharSequence text) {
		return null == text || text.length() == 0;
	}

	/**
	 * 是否包存在
	 * 
	 * @param packageName
	 * @return
	 */
	public static boolean isPackageExist(String packageName) {
		if (packageName == null || "".equals(packageName)) {
			return false;
		}
		try {
			WISERHelper.getInstance().getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 校验手机号
	 *
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern p = Pattern.compile("(13[0-9]|14[57]|15[012356789]|18[0-9]|17[0-9])\\d{8}");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 检测是否为合法的邮箱格式
	 *
	 * @param email
	 * @return true，email格式合法；false，email格式不合法
	 */
	public static boolean isEmail(String email) {
		if (TextUtils.isEmpty(email)) {
			return false;
		}

		String str = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 验证邮编是否合法
	 *
	 * @param postCode
	 *            邮编
	 * @return true 邮编符合规范 ;false 邮编不符合规范
	 */
	public static boolean isPostCode(String postCode) {
		if (TextUtils.isEmpty(postCode)) {
			return false;
		}
		if (postCode.matches("[1-9]\\d{5}(?!\\d)")) {
			return true;
		}
		return false;
	}

	/**
	 * 检测是否为合法的的汉字
	 *
	 * @param chineseStr
	 *            中文字符串
	 * @return true 汉字合法； false 汉字不合法
	 */
	public static boolean isChinese(String chineseStr) {
		if (TextUtils.isEmpty(chineseStr)) {
			return false;
		}

		String str = "^[\u4e00-\u9fa5]{0,}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(chineseStr);
		return m.matches();
	}

	/**
	 * 根据Unicode编码判断中文汉字和符号
	 *
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
	}

	/**
	 * 验证身份证号码
	 *
	 * @param number
	 *            身份证号码
	 * @return true 身份证符合规范 ;false 身份证有误
	 */
	public static Boolean isID(String number) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		String pattern = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		return m.matches();
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isEmpty(str)) return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 是否是Float类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (isEmpty(str)) return false;
		try {
			float v = Float.parseFloat(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是Double类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDouble(String str) {
		if (isEmpty(str)) return false;
		try {
			double ss = Double.parseDouble(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是Double类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLong(String str) {
		if (isEmpty(str)) return false;
		try {
			long ss = Long.parseLong(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断输入的字符串是否是纯数字
	 *
	 * @param value
	 *            输入的字符串
	 * @return true 全部都是数字； false 包含非数字字符
	 */
	public static boolean isAllNum(String value) {
		if (TextUtils.isEmpty(value)) {
			return false;
		}

		if (!value.isEmpty() && value.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断软键盘是否显示
	 *
	 * @return
	 */
	public static boolean isKeyboardShown(Activity activity) {
		if (activity == null) return false;
		if (activity.getWindow() == null) return false;
		if (activity.getWindow().getDecorView() == null) return false;
		// 获取当前屏幕内容的高度
		int screenHeight = activity.getWindow().getDecorView().getHeight();
		// 获取View可见区域的bottom
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		// 计算软件盘的高度
		int softInputHeight = screenHeight - rect.bottom;
		/**
		 * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零， 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API
		 * Level高于20时， 我们需要减去底部虚拟按键栏的高度（如果有的话）
		 */
		if (Build.VERSION.SDK_INT >= 20) {
			softInputHeight = softInputHeight - WISERApp.getSoftBottomBarHeight(activity);
		}
		// 存一份到本地
		if (softInputHeight > 0) {
			// HNAHelper.config().keyboardHeight = softInputHeight;
		}
		return softInputHeight != 0;
	}

	/**
	 * 判断 用户是否安装微信客户端
	 */
	public static boolean isWXCanUse() {
		final PackageManager packageManager = WISERHelper.getInstance().getPackageManager();
		List<PackageInfo> pi = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		if (pi != null) {
			for (int i = 0; i < pi.size(); i++) {
				String pn = pi.get(i).packageName;
				if (pn.equals("com.tencent.mm")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断 用户是否安装QQ客户端
	 */
	public static boolean isQQCanUse() {
		final PackageManager packageManager = WISERHelper.getInstance().getPackageManager();
		List<PackageInfo> pi = packageManager.getInstalledPackages(0);
		if (pi != null) {
			for (int i = 0; i < pi.size(); i++) {
				String pn = pi.get(i).packageName;
				if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 检查手机上是否安装了指定的软件
	 *
	 * @param context
	 * @param packageName：应用包名
	 * @return
	 */
	public static boolean isAvilible(Context context, String packageName) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}

	/**
	 * 检查手机上是否安装了百度地图
	 *
	 * @param context
	 * @return
	 */
	public static boolean isBaiDuMapAvilible(Context context) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains("com.baidu.BaiduMap");
	}

	/**
	 * 检查手机上是否安装了高德地图
	 *
	 * @param context
	 * @return
	 */
	public static boolean isGaoDeMapAvilible(Context context) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains("com.autonavi.minimap");
	}

	/**
	 * 检查手机上是否安装了Google地图
	 *
	 * @param context
	 * @return
	 */
	public static boolean isGoogleMapAvilible(Context context) {
		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		// 用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<>();
		// 从pinfo中将包名字逐一取出，压入pName list中
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		// 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains("com.google.android.apps.maps");
	}

	/**
	 * 判断是否存在虚拟按键
	 *
	 * @return
	 */
	public static boolean isHasNavigationBar(Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			@SuppressLint("PrivateApi")
			Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasNavigationBar;
	}

	/**
	 * 程序是否在前台运行
	 *
	 * @return
	 */
	public static boolean isAppOnForeground() {
		ActivityManager activityManager = (ActivityManager) WISERHelper.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = WISERHelper.getInstance().getPackageName();

		assert activityManager != null;
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null) return false;

		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @return true 表示开启
	 */
	public static boolean isOPenGPS() {
		LocationManager locationManager = (LocationManager) WISERHelper.getInstance().getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		assert locationManager != null;
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		return gps || network;
	}

	/**
	 * 判断服务是否处于运行状态.
	 *
	 * @param serviceName
	 * @param context
	 * @return
	 */
	public static boolean isServiceRunning(String serviceName, Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
		for (ActivityManager.RunningServiceInfo info : infos) {
			if (serviceName.equals(info.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
