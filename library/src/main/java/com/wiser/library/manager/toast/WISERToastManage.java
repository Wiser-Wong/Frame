package com.wiser.library.manager;

import javax.inject.Inject;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERApp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Looper;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Wiser
 * 
 *         Toast管理类
 */
public class WISERToastManage {

	protected Toast mToast = null;

	@Inject public WISERToastManage() {}

	/**
	 * 简单Toast 消息弹出
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
					showToast(msg, Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(msg, Toast.LENGTH_SHORT);
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
					showToast(String.valueOf(msg), Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(String.valueOf(msg), Toast.LENGTH_SHORT);
		}
	}

	/**
	 * 简单Toast 消息弹出
	 *
	 * @param msg
	 *            参数
	 */
	public void show(final long msg) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showToast(String.valueOf(msg), Toast.LENGTH_SHORT);
				}
			});
		} else {
			showToast(String.valueOf(msg), Toast.LENGTH_SHORT);
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
	public void show(final String msg, final int duration) {
		// 判断是否在主线程
		boolean isMainLooper = Looper.getMainLooper().getThread() != Thread.currentThread();

		if (isMainLooper) {
			WISERHelper.mainLooper().execute(new Runnable() {

				@Override public void run() {
					showToast(msg, duration);
				}
			});
		} else {
			showToast(msg, duration);
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
					showToast(String.valueOf(msg), duration);
				}
			});
		} else {
			showToast(String.valueOf(msg), duration);
		}
	}

	/**
	 * 弹出提示
	 *
	 * @param text
	 *            参数
	 * @param duration
	 *            参数
	 */
	@SuppressLint("showToast") private void showToast(String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(WISERHelper.getInstance(), text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}

		mToast.show();
	}

	public void clear() {
		if (mToast != null) {
			mToast.cancel();
			mToast = null;
		}
	}

}
