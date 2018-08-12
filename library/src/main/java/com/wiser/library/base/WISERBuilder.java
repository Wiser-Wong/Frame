package com.wiser.library.base;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERBuilder {

	private int						layoutId;							// 布局ID

	private FrameLayout				contentRoot;						// 根布局

	private LayoutInflater			mInflater;

	private WISERActivity			wiserActivity;

	/**
	 * TintManger
	 */
	private SystemBarTintManager	tintManager;

	private int						tintColor;

	private boolean					statusBarEnabled			= true;

	private boolean					navigationBarTintEnabled	= true;

	private boolean					fitsSystem					= true;

	private boolean					tint;

	/**
	 * 构造器
	 *
	 * @param wiserActivity
	 *            参数
	 * @param inflater
	 *            参数
	 */
	public WISERBuilder(@NonNull WISERActivity wiserActivity, @NonNull LayoutInflater inflater) {
		this.wiserActivity = wiserActivity;
		this.mInflater = inflater;
	}

	public void layoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	private int getLayoutId() {
		return layoutId;
	}

	int getTintColor() {
		return tintColor;
	}

	boolean isTintColor() {
		return tintColor > 0;
	}

	public void tintColor(int tintColor) {
		this.tintColor = tintColor;
	}

	public boolean getStatusBarTintEnabled() {
		return statusBarEnabled;
	}

	public void tintStatusBarEnabled(boolean isStatusBar) {
		this.statusBarEnabled = isStatusBar;
	}

	public void tintNavigationBarEnabled(boolean isNavigationBar) {
		this.navigationBarTintEnabled = isNavigationBar;
	}

	public boolean getNavigationBarTintEnabled() {
		return navigationBarTintEnabled;
	}

	public boolean isTint() {
		return tint;
	}

	public void tintIs(boolean isTint) {
		this.tint = isTint;
	}

	public void tintFitsSystem(boolean isFitsSystem) {
		this.fitsSystem = isFitsSystem;
	}

	public boolean isFitsSystem() {
		return fitsSystem;
	}

	protected SystemBarTintManager tintManager() {
		return tintManager;
	}

	/**
	 * 状态栏高度
	 */
	void systemBarWindow() {
		if (isFitsSystem()) {
			ViewGroup contentFrameLayout = wiserActivity.findViewById(Window.ID_ANDROID_CONTENT);
			View parentView = contentFrameLayout.getChildAt(0);
			if (parentView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				parentView.setFitsSystemWindows(true);
				setTranslucentStatus(true);
			}
		}
	}

	/**
	 * 状态栏颜色
	 */
	void systemBarColor() {
		if (isTint()) {
			tintManager = new SystemBarTintManager(wiserActivity);
			// enable status bar tint
			tintManager.setStatusBarTintEnabled(getStatusBarTintEnabled());
			// enable navigation bar tint
			tintManager.setNavigationBarTintEnabled(getNavigationBarTintEnabled());
			tintManager.setStatusBarTintColor(getTintColor());
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT) private void setTranslucentStatus(boolean on) {
		Window win = wiserActivity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/**
	 * 创建视图
	 *
	 * @return
	 */
	View createView() {
		contentRoot = new FrameLayout(wiserActivity.getContext());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		// 内容
		if (getLayoutId() > 0) {
			contentRoot.addView(mInflater.inflate(getLayoutId(), null, false), layoutParams);
		}
		return contentRoot;
	}

}
