package com.wiser.library.helper;

import java.io.File;

import javax.inject.Inject;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.util.WISERCheck;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.util.Pair;
import android.view.View;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERDisplay implements IWISERDisplay {

	@Inject public WISERDisplay() {}

	@Override public Context context() {
		return WISERHelper.getActivityManage().getCurrentActivity();
	}

	@Override public <T extends WISERActivity> T activity() {
		T wiserActivity = WISERHelper.getActivityManage().getCurrentIsRunningActivity();
		if (wiserActivity != null) {
			return wiserActivity;
		} else {
			return WISERHelper.getActivityManage().getCurrentActivity();
		}
	}

	@Override public void finish() {
		if (activity() != null) activity().finish();
	}

	@Override public void intent(Class clazz) {
		intentBundle(clazz, null);
	}

	@Override public void intent(String clazzName) {
		if (activity() == null || WISERCheck.isEmpty(clazzName)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClassName(activity(), clazzName);
		activity().startActivity(intent);
	}

	@Override public void intent(Class clazz, Intent intent) {
		if (clazz == null) return;
		if (activity() == null) return;
		if (intent != null) {
			intent.setClass(activity(), clazz);
			activity().startActivity(intent);
		} else {
			intentBundle(clazz, null);
		}

	}

	@Override public void intentBundle(Class clazz, Bundle bundle) {
		if (clazz == null) return;
		if (activity() == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intent(intent, bundle);
	}

	@Override public void intent(Intent intent, Bundle options) {
		if (intent == null) return;
		if (activity() == null) return;
		if (options != null) intent.putExtras(options);
		activity().startActivity(intent);
	}

	@Override public void intentForResult(Class clazz, int requestCode) {
		intentForResult(clazz, null, requestCode);
	}

	@Override public void intentForResult(Class clazz, Bundle bundle, int requestCode) {
		if (clazz == null) return;
		if (activity() == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intentForResult(intent, bundle, requestCode);
	}

	@Override @TargetApi(Build.VERSION_CODES.JELLY_BEAN) public void intentForResult(Intent intent, Bundle options, int requestCode) {
		if (intent == null) return;
		if (activity() == null) {
			return;
		}
		if (options != null) {
			intent.putExtras(options);
		}
		activity().startActivityForResult(intent, requestCode);
	}

	@Override public void intentService(Class classZ) {
		if (classZ == null) return;
		Intent intent = new Intent();
		intent.setClass(WISERHelper.getInstance(), classZ);
		WISERHelper.getInstance().startService(intent);
	}

	@Override public void intentService(String clazzName) {
		if (WISERCheck.isEmpty(clazzName)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClassName(WISERHelper.getInstance(), clazzName);
		WISERHelper.getInstance().startService(intent);
	}

	@Override public void intentService(Class classZ, Intent intent) {
		if (classZ == null) return;
		if (intent == null) return;
		intent.setClass(WISERHelper.getInstance(), classZ);
		WISERHelper.getInstance().startService(intent);
	}

	@Override public void intentService(Class classZ, Bundle bundle) {
		if (classZ == null) return;
		Intent intent = new Intent();
		intent.setClass(WISERHelper.getInstance(), classZ);
		intentService(intent, bundle);
	}

	@Override public void intentService(Intent intent, Bundle bundle) {
		if (intent == null) return;
		if (bundle != null) intent.putExtras(bundle);
		WISERHelper.getInstance().startActivity(intent);
	}

	@Override public void intentStopService(Class classZ) {
		if (classZ == null) return;
		Intent intent = new Intent();
		intent.setClass(WISERHelper.getInstance(), classZ);
		WISERHelper.getInstance().stopService(intent);
	}

	@Override public void intentStopService(String clazzName) {
		if (WISERCheck.isEmpty(clazzName)) {
			return;
		}
		Intent intent = new Intent();
		intent.setClassName(WISERHelper.getInstance(), clazzName);
		WISERHelper.getInstance().stopService(intent);
	}

	@Override public void intentBroadCast(String action) {
		if (WISERCheck.isEmpty(action)) return;
		Intent intent = new Intent(action);
		WISERHelper.getInstance().sendBroadcast(intent);
	}

	@Override public void intentBroadCast(Intent intent) {
		if (intent == null) return;
		WISERHelper.getInstance().sendBroadcast(intent);
	}

	@Override public void intentBroadCast(String action, Bundle bundle) {
		if (WISERCheck.isEmpty(action)) return;
		Intent intent = new Intent(action);
		intentBroadCast(intent, bundle);
	}

	@Override public void intentBroadCast(Intent intent, Bundle bundle) {
		if (intent == null) return;
		if (bundle != null) intent.putExtras(bundle);
		WISERHelper.getInstance().sendBroadcast(intent);
	}

	@Override public void intentRegisteredBroadCast(String action, BroadcastReceiver receiver) {
		if (WISERCheck.isEmpty(action) || receiver == null) return;
		IntentFilter filter = new IntentFilter();
		filter.addAction(action);
		WISERHelper.getInstance().registerReceiver(receiver, filter);
	}

	@Override public void intentFromFragment(Class clazz, Fragment fragment, int requestCode) {
		if (clazz == null) return;
		if (fragment == null) return;
		if (activity() == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intentFromFragment(intent, fragment, requestCode);
	}

	@Override public void intentFromFragment(Intent intent, Fragment fragment, int requestCode) {
		if (fragment == null) return;
		if (activity() == null) {
			return;
		}
		activity().startActivityFromFragment(fragment, intent, requestCode);
	}

	@Override public void intentForResultFromFragment(Class clazz, Bundle bundle, int requestCode, Fragment fragment) {
		if (activity() == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		activity().startActivityFromFragment(fragment, intent, requestCode);
	}

	@Override public void intentAnimation(Class clazz, @AnimRes @AnimatorRes int in, @AnimRes @AnimatorRes int out) {
		intent(clazz);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override public void intentAnimation(Class clazz, @AnimRes @AnimatorRes int in, @AnimRes @AnimatorRes int out, Bundle bundle) {
		intentBundle(clazz, bundle);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override @TargetApi(Build.VERSION_CODES.LOLLIPOP) public void intentTransitionAnimation(Class clazz, Intent intent, Pair<View, String>... sharedElements) {
		if (clazz == null) return;
		if (activity() == null) {
			return;
		}
		if (intent != null) {
			intent.setClass(activity(), clazz);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) activity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity(), sharedElements).toBundle());
			else activity().startActivity(intent);
		} else {
			Intent createIntent = new Intent();
			createIntent.setClass(activity(), clazz);
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) activity().startActivity(createIntent, ActivityOptions.makeSceneTransitionAnimation(activity(), sharedElements).toBundle());
			else activity().startActivity(createIntent);
		}
	}

	@Override public void intentForResultAnimation(Class clazz, @AnimRes @AnimatorRes int in, @AnimRes @AnimatorRes int out, int requestCode) {
		intentForResultAnimation(clazz, in, out, null, requestCode);
	}

	@Override public void intentForResultAnimation(Class clazz, @AnimRes @AnimatorRes int in, @AnimRes @AnimatorRes int out, Bundle bundle, int requestCode) {
		intentForResult(clazz, bundle, requestCode);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override public void commitAdd(@IdRes int id, Fragment fragment) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
		}
	}

	@Override public void commitAddAnim(@IdRes int id, Fragment fragment, @AnimRes @AnimatorRes int newFragmentInAnim, @AnimRes @AnimatorRes int oldFragmentOutAnim,
			@AnimRes @AnimatorRes int oldFragmentInAnim, @AnimRes @AnimatorRes int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).add(id, fragment).commit();
		}
	}

	@Override public void commitAddAnim(int id, Fragment fragment, int in, int out) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).add(id, fragment).commit();
		}
	}

	@Override public void commitAdd(@IdRes int id, Fragment fragment, String tag) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().add(id, fragment, tag).commit();
		}
	}

	@Override public void commitAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimRes @AnimatorRes int newFragmentInAnim, @AnimRes @AnimatorRes int oldFragmentOutAnim,
			@AnimRes @AnimatorRes int oldFragmentInAnim, @AnimRes @AnimatorRes int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).add(id, fragment, tag).commit();
		}
	}

	@Override public void commitAddAnim(int id, Fragment fragment, String tag, int in, int out) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).add(id, fragment, tag).commit();
		}
	}

	@Override public void commitReplace(@IdRes int id, Fragment fragment) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
		}
	}

	@Override public void commitReplaceAnim(@IdRes int id, Fragment fragment, @AnimRes @AnimatorRes int newFragmentInAnim, @AnimRes @AnimatorRes int oldFragmentOutAnim,
			@AnimRes @AnimatorRes int oldFragmentInAnim, @AnimRes @AnimatorRes int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).replace(id, fragment).commit();
		}
	}

	@Override public void commitReplaceAnim(int id, Fragment fragment, int in, int out) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).replace(id, fragment).commit();
		}
	}

	@Override public void commitReplace(@IdRes int id, Fragment fragment, String tag) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().replace(id, fragment, tag).commit();
		}
	}

	@Override public void commitReplaceAnim(@IdRes int id, Fragment fragment, String tag, @AnimRes @AnimatorRes int newFragmentInAnim, @AnimRes @AnimatorRes int oldFragmentOutAnim,
			@AnimRes @AnimatorRes int oldFragmentInAnim, @AnimRes @AnimatorRes int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).replace(id, fragment, tag)
					.commit();
		}
	}

	@Override public void commitReplaceAnim(int id, Fragment fragment, String tag, int in, int out) {
		if (activity() == null) return;
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).replace(id, fragment, tag).commit();
		}
	}

	@Override public void commitChildReplace(Fragment srcFragment, @IdRes int id, Fragment fragment) {
		if (activity() == null) return;
		if (fragment == null) return;
		if (srcFragment == null) return;
		srcFragment.getChildFragmentManager().beginTransaction().replace(id, fragment, fragment.getClass().getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commitAllowingStateLoss();
	}

	@Override public void commitBackStack(@IdRes int id, Fragment fragment) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().add(id, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	@Override public void commitBackStack(int id, Fragment fragment, String tag) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().add(id, fragment, tag).addToBackStack(fragment.getClass().getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commitAllowingStateLoss();
	}

	@Override public void commitBackStackAddAnim(@IdRes int id, Fragment fragment, String tag, @AnimRes @AnimatorRes int newFragmentInAnim, @AnimRes @AnimatorRes int oldFragmentOutAnim,
			@AnimRes @AnimatorRes int oldFragmentInAnim, @AnimRes @AnimatorRes int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).add(id, fragment, tag)
				.addToBackStack(tag).commitAllowingStateLoss();
	}

	@Override public void commitBackStackAddAnim(int id, Fragment fragment, String tag, int in, int out) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).add(id, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
	}

	@Override public void commitBackStackAddAnim(int id, Fragment fragment, int newFragmentInAnim, int oldFragmentOutAnim, int oldFragmentInAnim, int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim)
				.add(id, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
	}

	@Override public void commitBackStackAddAnim(int id, Fragment fragment, int in, int out) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).add(id, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
				.commitAllowingStateLoss();
	}

	@Override public void commitBackStackReplaceAnim(int id, Fragment fragment, String tag, int newFragmentInAnim, int oldFragmentOutAnim, int oldFragmentInAnim, int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim).replace(id, fragment, tag)
				.addToBackStack(tag).commitAllowingStateLoss();
	}

	@Override public void commitBackStackReplaceAnim(int id, Fragment fragment, String tag, int in, int out) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).replace(id, fragment, tag).addToBackStack(tag).commitAllowingStateLoss();
	}

	@Override public void commitBackStackReplaceAnim(int id, Fragment fragment, int newFragmentInAnim, int oldFragmentOutAnim, int oldFragmentInAnim, int newFragmentOutAnim) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(newFragmentInAnim, oldFragmentOutAnim, oldFragmentInAnim, newFragmentOutAnim)
				.replace(id, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
	}

	@Override public void commitBackStackReplaceAnim(int id, Fragment fragment, int in, int out) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().setCustomAnimations(in, out).replace(id, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
				.commitAllowingStateLoss();
	}

	@Override public void commitBackStack(@IdRes int id, Fragment fragment, @AnimRes @AnimatorRes int animation) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().add(id, fragment, fragment.getClass().getName()).setTransition(animation != 0 ? animation : FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
	}

	@Override public void showFragment(Fragment fragment) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
	}

	@Override public void hideFragment(Fragment fragment) {
		if (activity() == null) return;
		if (fragment == null) return;
		activity().getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
	}

	@Override public void popBackStack() {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStack();
	}

	@Override public void popBackStack(@Nullable String var1, int var2) {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStack(var1, var2);
	}

	@Override public void popBackStack(int var1, int var2) {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStack(var1, var2);
	}

	@Override public void popBackStackImmediate() {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStack();
	}

	@Override public void popBackStackImmediate(@Nullable String var1, int var2) {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStackImmediate(var1, var2);
	}

	@Override public void popBackStackImmediate(int var1, int var2) {
		if (activity() == null) return;
		activity().getSupportFragmentManager().popBackStackImmediate(var1, var2);
	}

	@Override public void commitRemove(Fragment fragment) {
		if (fragment != null && activity() != null) {
			activity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}
	}

	/**
	 * @param <T>
	 *            参数
	 * @param tagName
	 *            参数
	 * @return 返回值
	 */
	public <T> T findFragment(String tagName) {
		if (WISERCheck.isEmpty(tagName)) return null;
		if (activity() == null) return null;
		return (T) activity().getSupportFragmentManager().findFragmentByTag(tagName);
	}

	/**
	 * android用于 直接打电话intent跳转 需要权限
	 * <uses-permission android:name="android.permission.CALL_PHONE"/>
	 *
	 * @param phoneNumber
	 *            电话号码
	 */
	@SuppressLint("MissingPermission") @Override public void intentCall(String phoneNumber) {
		if (activity() == null) return;
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
		activity().startActivity(intent);
	}

	/**
	 * android用于打开HTML文件的intent跳转
	 *
	 * @param html
	 */
	@Override public void intentHtml(String html) {
		if (activity() == null) return;
		Uri uri = Uri.parse(html).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(html).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开PDF文件的intent跳转
	 *
	 * @param path
	 *            Pdf文件路径
	 */
	@Override public void intentPdf(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/pdf");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开文本文件的intent跳转
	 *
	 * @param path
	 *            文本文件路径
	 */
	@Override public void intentTxt(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri1 = Uri.parse(path);
		intent.setDataAndType(uri1, "text/plain");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开音频文件的intent跳转
	 *
	 * @param path
	 *            音频文件路径
	 */
	@Override public void intentAudio(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "audio/*");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开视频文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	@Override public void intentVideo(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "video/*");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开Word文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	@Override public void intentWord(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/msword");
		activity().startActivity(intent);
	}

	/**
	 * android用于打开Excel文件的intent跳转
	 *
	 * @param path
	 *            视频文件路径
	 */
	@Override public void intentExcel(String path) {
		if (activity() == null) return;
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(path));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		activity().startActivity(intent);
	}

	/**
	 * 跳转浏览器打开网页
	 *
	 * @param url
	 *            跳转的URL地址
	 */
	@Override public void intentWeb(String url) {
		if (activity() == null) return;
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		activity().startActivity(intent);
	}

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
	@Override public String intentCamera(String outPath, String authority, int requestCode) {
		if (activity() == null) return "";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File outDir = new File(outPath);
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			else {
				intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity(), authority, outFile));
			}
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			activity().startActivityForResult(intent, requestCode);
			return outFile.getAbsolutePath();
		}
		return "";
	}

	/**
	 * 跳转相册
	 *
	 * @param requestCode
	 *            请求码
	 */
	@Override public void intentPhoto(int requestCode) {
		if (activity() == null) return;
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		activity().startActivityForResult(intent, requestCode);
	}

	/**
	 * 截图方法
	 *
	 * @param uri
	 *            uri
	 * @param requestCode
	 *            请求吗
	 */
	@Override public void cropPhoto(Uri uri, int requestCode) {
		if (activity() == null) return;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// intent.putExtra("scale", true);
		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", true);
		// intent.putExtra("outputFormat",
		// Bitmap.CompressFormat.JPEG.toString());
		// intent.putExtra("noFaceDetection", true); // no face detection
		activity().startActivityForResult(intent, requestCode);
	}

	/**
	 * 跳转设置
	 */
	@Override public void intentSetting() {
		if (activity() == null) return;
		Intent localIntent = new Intent();
		localIntent.setAction(Settings.ACTION_SETTINGS);
		activity().startActivity(localIntent);
	}

	/**
	 * 跳转App详情
	 */
	@Override public void intentAppDetails() {
		if (activity() == null) return;
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		localIntent.setData(Uri.fromParts("package", activity().getPackageName(), null));
		activity().startActivity(localIntent);
	}

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
	@Override public void installApk(Context context, String authority, String path) {
		if (activity() == null) return;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
		} else {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			File file = new File(path);
			intent.setDataAndType(FileProvider.getUriForFile(context, authority, file), "application/vnd.android.package-archive");
		}
		activity().startActivity(intent);
	}

	/**
	 * 跳转到微信客户端
	 *
	 * @param activity
	 */
	@Override public void intentWeChatClient(Activity activity) {
		Intent intent = new Intent();
		ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(cmp);
		activity.startActivity(intent);
	}

}
