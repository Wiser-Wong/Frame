package com.wiser.library.helper;

import java.io.File;

import javax.inject.Inject;

import com.wiser.library.util.WISERCheckUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERDisplay implements IWISERDisplay {

	@Inject
	public WISERDisplay(){}

	@Override public Context context() {
		return WISERHelper.getActivityManage().getCurrentActivity();
	}

	@Override public <T extends FragmentActivity> T activity() {
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
		intent(clazz, null);
	}

	@Override public void intent(String clazzName) {
		if (activity() == null || clazzName == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClassName(WISERHelper.getInstance(), clazzName);
		activity().startActivity(intent);
	}

	@Override public void intent(Intent intent) {
		intent(intent, null);
	}

	@Override public void intent(Class clazz, Bundle bundle) {
		if (clazz == null) return;
		if (activity() == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(activity(), clazz);
		intent(intent, bundle);
	}

	@Override public void intent(Intent intent, Bundle options) {
		intentForResult(intent, options, -1);
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

	@Override public void intentAnimation(Class clazz, int in, int out) {
		intent(clazz);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override public void intentAnimation(Class clazz, int in, int out, Bundle bundle) {
		intent(clazz, bundle);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override public void intentForResultAnimation(Class clazz, int in, int out, int requestCode) {
		intentForResultAnimation(clazz, in, out, null, requestCode);
	}

	@Override public void intentForResultAnimation(Class clazz, int in, int out, Bundle bundle, int requestCode) {
		intentForResult(clazz, bundle, requestCode);
		if (activity() == null) {
			return;
		}
		activity().overridePendingTransition(in, out);
	}

	@Override public void commitAdd(int layoutId, Fragment fragment) {
		if (layoutId > 0 && fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().add(layoutId, fragment).commit();
		}
	}

	@Override public void commitReplace(int layoutId, Fragment fragment) {
		if (layoutId > 0 && fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
		}
	}

	@SuppressLint("ResourceType") @Override public void commitChildReplace(Fragment srcFragment, int layoutId, Fragment fragment) {
		WISERCheckUtil.checkArgument(layoutId > 0, "提交布局ID 不能为空~");
		WISERCheckUtil.checkNotNull(fragment, "fragment不能为空~");
		if (activity() == null) {
			return;
		}
		srcFragment.getChildFragmentManager().beginTransaction().replace(layoutId, fragment, fragment.getClass().getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.commitAllowingStateLoss();
	}

	@SuppressLint("ResourceType") @Override public void commitBackStack(int layoutId, Fragment fragment) {
		WISERCheckUtil.checkArgument(layoutId > 0, "提交布局ID 不能为空~");
		WISERCheckUtil.checkNotNull(fragment, "fragment不能为空~");
		if (activity() == null) {
			return;
		}
		activity().getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	@SuppressLint("ResourceType") @Override public void commitBackStack(int layoutId, Fragment fragment, int animation) {
		WISERCheckUtil.checkArgument(layoutId > 0, "提交布局ID 不能为空~");
		WISERCheckUtil.checkArgument(animation > 0, "动画 不能为空~");
		WISERCheckUtil.checkNotNull(fragment, "fragment不能为空~");
		if (activity() == null) {
			return;
		}
		activity().getSupportFragmentManager().beginTransaction().add(layoutId, fragment, fragment.getClass().getName()).addToBackStack(fragment.getClass().getName())
				.setTransition(animation != 0 ? animation : FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commitAllowingStateLoss();
	}

	@Override public void commitRemove(Fragment fragment) {
		if (fragment != null) {
			activity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
		}
	}

	/**
	 * android用于 直接打电话intent跳转 需要权限
	 * <uses-permission android:name="android.permission.CALL_PHONE"/>
	 *
	 * @param phoneNumber
	 *            电话号码
	 */
	@SuppressLint("MissingPermission") @Override public void intentCall(String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
		activity().startActivity(intent);
	}

	/**
	 * android用于打开HTML文件的intent跳转
	 * 
	 * @param html
	 */
	@Override public void intentHtml(String html) {
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
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		activity().startActivity(intent);
	}

	/**
	 * 调用系统照相机拍照
	 *
	 * @param uri
	 *            照片路径Uri
	 * @param requestCode
	 *            请求码
	 */
	@Override public void intentCamera(Uri uri, int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity().startActivityForResult(intent, requestCode);
	}

	/**
	 * 跳转相册
	 * 
	 * @param requestCode
	 *            请求码
	 */
	@Override public void intentPhoto(int requestCode) {
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
	 * 安装新的应用
	 * 
	 * @param path
	 *            apk路径
	 */
	@Override public void installApk(String path) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity().startActivity(intent);
	}
}
