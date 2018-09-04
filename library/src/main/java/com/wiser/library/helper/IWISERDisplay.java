package com.wiser.library.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERDisplay {

	/**
	 * 获取上下文
	 *
	 * @return 返回值
	 */
	Context context();

	/**
	 * @param <T>
	 *            参数
	 * @return 返回值
	 */
	<T extends FragmentActivity> T activity();

	/**
	 * 结束界面
	 */
	void finish();

	/**
	 * 跳转intent
	 *
	 * @param clazz
	 *            参数
	 **/

	void intent(Class clazz);

	/**
	 * @param clazzName
	 *            参数
	 */
	void intent(String clazzName);

	/**
	 * @param intent
	 *            参数
	 */
	void intent(Intent intent);

	/**
	 * @param clazz
	 *            参数
	 * @param bundle
	 *            参数
	 */
	void intent(Class clazz, Bundle bundle);

	/**
	 * @param intent
	 *            参数
	 * @param options
	 *            参数
	 */
	void intent(Intent intent, Bundle options);

	/**
	 * @param clazz
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentForResult(Class clazz, int requestCode);

	/**
	 * @param clazz
	 *            参数
	 * @param bundle
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentForResult(Class clazz, Bundle bundle, int requestCode);

	/**
	 * @param intent
	 *            参数
	 * @param options
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentForResult(Intent intent, Bundle options, int requestCode);

	/**
	 * 跳转
	 *
	 * @param clazz
	 *            参数
	 * @param fragment
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentFromFragment(Class clazz, Fragment fragment, int requestCode);

	/**
	 * 跳转
	 *
	 * @param intent
	 *            参数
	 * @param fragment
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentFromFragment(Intent intent, Fragment fragment, int requestCode);

	/**
	 * @param clazz
	 *            参数
	 * @param bundle
	 *            参数
	 * @param requestCode
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void intentForResultFromFragment(Class clazz, Bundle bundle, int requestCode, Fragment fragment);

	/**
	 * @param clazz
	 *            参数
	 * @param in
	 *            参数
	 * @param out
	 *            参数
	 */
	void intentAnimation(Class clazz, @AnimRes int in, @AnimRes int out);

	/**
	 * @param clazz
	 *            参数
	 * @param in
	 *            参数
	 * @param out
	 *            参数
	 * @param bundle
	 *            参数
	 */
	void intentAnimation(Class clazz, @AnimRes int in, @AnimRes int out, Bundle bundle);

	/**
	 * @param clazz
	 *            参数
	 * @param in
	 *            参数
	 * @param out
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentForResultAnimation(Class clazz, @AnimRes int in, @AnimRes int out, int requestCode);

	/**
	 * @param clazz
	 *            参数
	 * @param in
	 *            参数
	 * @param out
	 *            参数
	 * @param bundle
	 *            参数
	 * @param requestCode
	 *            参数
	 */
	void intentForResultAnimation(Class clazz, @AnimRes int in, @AnimRes int out, Bundle bundle, int requestCode);

	/**
	 * @param layoutId
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitAdd(int layoutId, Fragment fragment);

	/**
	 * @param layoutId
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitReplace(int layoutId, Fragment fragment);

	/**
	 * @param srcFragment
	 *            参数
	 * @param layoutId
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitChildReplace(Fragment srcFragment, @IdRes int layoutId, Fragment fragment);

	/**
	 * @param layoutId
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitBackStack(@IdRes int layoutId, Fragment fragment);

	/**
	 * @param layoutId
	 *            参数
	 * @param fragment
	 *            参数
	 * @param animation
	 *            参数
	 */
	void commitBackStack(@IdRes int layoutId, Fragment fragment, int animation);

	/**
	 * 
	 * @param fragment
	 *            参数
	 */
	void commitRemove(Fragment fragment);

	/**
	 * 调用拨打电话页
	 * 
	 * @param phoneNumber
	 *            手机号
	 */
	void intentCall(String phoneNumber);

	/**
	 * android用于打开HTML文件的intent跳转
	 * 
	 * @param html
	 *            Html网址路径
	 */
	void intentHtml(String html);

	/**
	 * android用于打开PDF文件的intent跳转
	 * 
	 * @param path
	 *            pdf路径
	 */
	void intentPdf(String path);

	/**
	 * 
	 * @param path
	 *            txt文本路径
	 */
	void intentTxt(String path);

	/**
	 * android用于打开音频文件的intent跳转
	 * 
	 * @param path
	 *            音频路径
	 */
	void intentAudio(String path);

	/**
	 * android用于打开视频文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	void intentVideo(String path);

	/**
	 * android用于打开Word文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	void intentWord(String path);

	/**
	 * android用于打开Excel文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	void intentExcel(String path);

	/**
	 * 跳转浏览器打开网页
	 *
	 * @param url
	 *            跳转的URL地址
	 */
	void intentWeb(String url);

	/**
	 * 调用系统照相机拍照
	 *
	 * @param uri
	 *            照片路径Uri
	 * @param requestCode
	 *            请求码
	 */
	void intentCamera(Uri uri, int requestCode);

	/**
	 * 跳转相册
	 *
	 * @param requestCode
	 *            请求码
	 */
	void intentPhoto(int requestCode);

	/**
	 * 截图方法
	 *
	 * @param uri
	 *            uri
	 * @param requestCode
	 *            请求吗
	 */
	void cropPhoto(Uri uri, int requestCode);

	/**
	 * 跳转设置
	 */
	void intentSetting();

	/**
	 * 跳转app详情
	 */
	void intentAppDetails();

	/**
	 * 安装新的应用
	 *
	 * @param path
	 *            apk路径
	 */
	void installApk(String path);
}
