package com.wiser.library.helper;

import com.wiser.library.base.WISERActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.View;

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
	<T extends WISERActivity> T activity();

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
	 *            clazz
	 * @param intent
	 *            参数
	 */
	void intent(Class clazz, Intent intent);

	/**
	 * @param clazz
	 *            参数
	 * @param bundle
	 *            参数
	 */
	void intentBundle(Class clazz, Bundle bundle);

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
	 * 启动服务
	 * 
	 * @param classZ
	 */
	void intentService(Class classZ);

	/**
	 * 启动服务
	 * 
	 * @param clazzName
	 *            参数
	 */
	void intentService(String clazzName);

	/**
	 * 启动服务
	 * 
	 * @param classZ
	 * @param intent
	 */
	void intentService(Class classZ, Intent intent);

	/**
	 * 启动服务
	 *
	 * @param classZ
	 * @param bundle
	 */
	void intentService(Class classZ, Bundle bundle);

	/**
	 * 启动服务
	 *
	 * @param intent
	 * @param bundle
	 */
	void intentService(Intent intent, Bundle bundle);

	/**
	 * 结束服务
	 *
	 * @param classZ
	 */
	void intentStopService(Class classZ);

	/**
	 * 结束服务
	 * 
	 * @param clazzName
	 *            参数
	 */
	void intentStopService(String clazzName);

	/**
	 * 发送广播
	 * 
	 * @param action
	 */
	void intentBroadCast(String action);

	/**
	 * 发送广播
	 * 
	 * @param intent
	 */
	void intentBroadCast(Intent intent);

	/**
	 * 发送广播
	 *
	 * @param bundle
	 */
	void intentBroadCast(String action, Bundle bundle);

	/**
	 * 发送广播
	 *
	 * @param intent
	 * @param bundle
	 */
	void intentBroadCast(Intent intent, Bundle bundle);

	/**
	 * 注册广播
	 *
	 * @param action
	 * @param receiver
	 */
	void intentRegisteredBroadCast(String action, BroadcastReceiver receiver);

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
	 * @param intent
	 *            参数
	 * @param sharedElements
	 *            参数
	 */
	void intentTransitionAnimation(Class clazz, Intent intent, Pair<View, String>... sharedElements);

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
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitAdd(@IdRes int id, Fragment fragment);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitAddAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim, @AnimatorRes @AnimRes int oldFragmentInAnim,
			@AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitAddAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 */
	void commitAdd(@IdRes int id, Fragment fragment, String tag);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitReplace(@IdRes int id, Fragment fragment);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitReplaceAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim, @AnimatorRes @AnimRes int oldFragmentInAnim,
			@AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitReplaceAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 */
	void commitReplace(@IdRes int id, Fragment fragment, String tag);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitReplaceAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitReplaceAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 * @param srcFragment
	 *            参数
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitChildReplace(Fragment srcFragment, @IdRes int id, Fragment fragment);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 */
	void commitBackStack(@IdRes int id, Fragment fragment);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            参数
	 */
	void commitBackStack(@IdRes int id, Fragment fragment, String tag);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitBackStackAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitBackStackAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitBackStackAddAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitBackStackAddAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitBackStackReplaceAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitBackStackReplaceAnim(@IdRes int id, Fragment fragment, String tag, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param tag
	 *            标志
	 * @param newFragmentInAnim
	 *            新的Fragment 进入动画
	 * @param oldFragmentOutAnim
	 *            旧的Fragment 出去动画
	 * @param oldFragmentInAnim
	 *            旧的Fragment 进入动画
	 * @param newFragmentOutAnim
	 *            新的Fragment出去动画
	 */
	void commitBackStackReplaceAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int newFragmentInAnim, @AnimatorRes @AnimRes int oldFragmentOutAnim,
			@AnimatorRes @AnimRes int oldFragmentInAnim, @AnimatorRes @AnimRes int newFragmentOutAnim);

	/**
	 *
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param in
	 *            进入动画
	 * @param out
	 *            出去动画
	 */
	void commitBackStackReplaceAnim(@IdRes int id, Fragment fragment, @AnimatorRes @AnimRes int in, @AnimatorRes @AnimRes int out);

	/**
	 * @param id
	 *            参数
	 * @param fragment
	 *            参数
	 * @param animation
	 *            参数
	 */
	void commitBackStack(@IdRes int id, Fragment fragment, @AnimRes @AnimatorRes int animation);

	/**
	 * 显示Fragment
	 * 
	 * @param fragment
	 */
	void showFragment(Fragment fragment);

	/**
	 * 隐藏Fragment
	 *
	 * @param fragment
	 */
	void hideFragment(Fragment fragment);

	/**
	 * Fragment 出栈
	 */
	void popBackStack();

	/**
	 * Fragment 出栈
	 * 
	 * @param var1
	 *            参数
	 * @param var2
	 *            参数
	 */
	void popBackStack(@Nullable String var1, int var2);

	/**
	 * Fragment 出栈
	 * 
	 * @param var1
	 *            参数
	 * @param var2
	 *            参数
	 */
	void popBackStack(int var1, int var2);

	/**
	 * Fragment 出栈
	 */
	void popBackStackImmediate();

	/**
	 * Fragment 出栈
	 *
	 * @param var1
	 *            参数
	 * @param var2
	 *            参数
	 */
	void popBackStackImmediate(@Nullable String var1, int var2);

	/**
	 * Fragment 出栈
	 *
	 * @param var1
	 *            参数
	 * @param var2
	 *            参数
	 */
	void popBackStackImmediate(int var1, int var2);

	/**
	 * @param fragment
	 *            参数
	 */
	void commitRemove(Fragment fragment);

	/**
	 * @param <T>
	 *            参数
	 * @param tagName
	 *            参数
	 * @return 返回值
	 */
	<T> T findFragment(String tagName);

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
	 * @param outPath
	 *            输出路径String
	 * @param authority
	 *            7.0以上需要
	 * @param requestCode
	 *            请求码
	 * @return 返回文件绝对路径 file.getAbsolutePath();
	 */
	String intentCamera(String outPath, String authority, int requestCode);

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
	 * @param context
	 *            上下文
	 * @param authority
	 *            认证
	 * @param path
	 *            apk路径
	 */
	void installApk(Context context, String authority, String path);

	/**
	 * 跳转到微信客户端
	 *
	 * @param activity
	 */
	void intentWeChatClient(Activity activity);
}
