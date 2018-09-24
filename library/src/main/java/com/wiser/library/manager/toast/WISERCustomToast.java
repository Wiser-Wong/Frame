package com.wiser.library.manager.toast;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERApp;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Wiser
 * 
 *         自定义Toast
 */
public abstract class WISERCustomToast extends WISERToastManage {

	private View		v;

	private TextView	toastView	= null;

	protected abstract @LayoutRes int toastLayoutId();

	protected abstract @DrawableRes int toastDrawableBackgroundId();

	protected abstract void initView(View v, String msg);

	protected abstract int gravity();

	/**
	 * 显示
	 *
	 * @param msg
	 *            参数
	 */
	public void show(final String msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showCustomToast(msg, Toast.LENGTH_SHORT);
				}
			});
		} else {
			showCustomToast(msg, Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 简单Toast 消息弹出
	 *
	 * @param msg
	 *            参数
	 */
	public void show(final int msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showCustomToast(WISERHelper.getInstance().getString(msg), Toast.LENGTH_SHORT);
				}
			});
		} else {
			showCustomToast(WISERHelper.getInstance().getString(msg), Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 显示
	 *
	 * @param msg
	 *            参数
	 * @param duration
	 *            参数
	 */
	public void show(final String msg, final int duration) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showCustomToast(msg, duration);
				}
			});
		} else {
			showCustomToast(msg, duration);
		}
	}

	/**
	 * 简单Toast 消息弹出
	 *
	 * @param msg
	 *            参数
	 * @param duration
	 *            参数
	 */
	public void show(final int msg, final int duration) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showCustomToast(WISERHelper.getInstance().getString(msg), duration);
				}
			});
		} else {
			showCustomToast(WISERHelper.getInstance().getString(msg), duration);
		}
	}

	/**
	 * 显示自定义Toast
	 * 
	 * @param showMsg
	 * @param duration
	 */
	private void showCustomToast(String showMsg, int duration) {
		if (toastLayoutId() != 0) {
			try {
				customLayoutToast(showMsg, duration);
			} catch (Exception e) {
				customTextToast(showMsg, duration);
			}
		} else {
			customTextToast(showMsg, duration);
		}
	}

	/**
	 * 自定义Drawable背景
	 *
	 * @param showMsg
	 */
	private void customTextToast(String showMsg, int duration) {
		clear();
		if (mToast == null) {
			mToast = new Toast(WISERHelper.getActivityManage().getCurrentActivity());
		}
		if (toastView == null) {
			toastView = new TextView(WISERHelper.getActivityManage().getCurrentActivity());
		}
		toastView.setPadding(WISERApp.dip2px(40), WISERApp.dip2px(20), WISERApp.dip2px(40), WISERApp.dip2px(20));

		if (toastDrawableBackgroundId() != 0) {
			try {
				toastView.setBackgroundResource(toastDrawableBackgroundId());
			} catch (Exception e) {
				toastView.setBackgroundResource(R.drawable.toast_bg);
			}
		} else {
			toastView.setBackgroundResource(R.drawable.toast_bg);
		}
		toastView.setText(showMsg);
		toastView.setTextColor(Color.WHITE);
		toastView.setTextSize(16.0f);
		mToast.setView(toastView);
		mToast.setGravity(gravity(), 0, 0);
		mToast.setView(toastView);
		mToast.setGravity(gravity(), 0, 0);
		mToast.setDuration(duration);
		mToast.show();
	}

	/**
	 * 自定义Layout 背景
	 * 
	 * @param showMsg
	 * @param duration
	 */
	private void customLayoutToast(String showMsg, int duration) {
		clear();
		if (mToast == null) {
			mToast = new Toast(WISERHelper.getInstance());
			LayoutInflater inflate = (LayoutInflater) WISERHelper.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			assert inflate != null;
			v = inflate.inflate(toastLayoutId(), null);
			mToast.setView(v);
			initView(v, showMsg);
			mToast.setDuration(duration);
			mToast.setGravity(gravity(), 0, 0);
		} else {
			initView(v, showMsg);
			mToast.setDuration(duration);
			mToast.setGravity(gravity(), 0, 0);
		}
		mToast.show();
	}

}
