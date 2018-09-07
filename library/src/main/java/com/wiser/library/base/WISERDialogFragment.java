package com.wiser.library.base;

import com.wiser.library.R;
import com.wiser.library.util.WISERApp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
public abstract class WISERDialogFragment extends DialogFragment {

	protected final int	TOP				= Gravity.TOP;

	protected final int	CENTER			= Gravity.CENTER;

	protected final int	BOTTOM			= Gravity.BOTTOM;

	protected final int	TOP_START		= Gravity.TOP | Gravity.START;

	protected final int	TOP_END			= Gravity.TOP | Gravity.END;

	protected final int	CENTER_START	= Gravity.CENTER | Gravity.START;

	protected final int	CENTER_END		= Gravity.CENTER | Gravity.END;

	protected final int	BOTTOM_START	= Gravity.BOTTOM | Gravity.START;

	protected final int	BOTTOM_END		= Gravity.BOTTOM | Gravity.END;

	protected abstract View createDialogView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

	protected abstract void initData(Bundle savedInstanceState);

	protected abstract int dialogWeight();

	protected abstract int dialogTheme();

	protected abstract boolean isWidthFullScreen();

	protected abstract boolean isCloseOnTouchOutside();

	private boolean			isLocation;							// 是否定制位置

	private int				x, y;								// 定制位置绝对坐标

	public static final int	CONTROL_TOP		= 7777;				// 控件上

	public static final int	CONTROL_BOTTOM	= 7778;				// 控件下

	public static final int	CONTROL_FIT		= 7779;				// 适应控件上或者下

	private int				widget			= CONTROL_BOTTOM;

	private boolean			isLocationTop;						// 是否控件上

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置style
		if (dialogTheme() > 0) {
			setStyle(DialogFragment.STYLE_NORMAL, dialogTheme());
		} else {
			if (isLocation) setStyle(DialogFragment.STYLE_NORMAL, R.style.DefaultLocationDialogTheme);
			else setStyle(DialogFragment.STYLE_NORMAL, R.style.DefaultDialogTheme);
		}
	}

	@Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (getDialog() != null) {
			// 去除标题栏
			getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

			// 设置点击空白处关闭Dialog
			getDialog().setCanceledOnTouchOutside(isCloseOnTouchOutside());

			// 定制位置显示
			showLocation();
		}
		// 注册ID
		View view = createDialogView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, view);

		initData(savedInstanceState);

		return view;
	}

	@Override public void onStart() {
		super.onStart();
		if (isWidthFullScreen()) {
			if (getDialog() != null && getDialog().getWindow() != null) {
				getDialog().getWindow().setLayout(WISERApp.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
				getDialog().getWindow().setBackgroundDrawable(null);
			}
		}
	}

	/**
	 * 判断弹窗是否显示
	 * 
	 * @return
	 */
	public boolean isShowing() {
		return getDialog() != null && getDialog().isShowing();
	}

	@Override public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}

	/**
	 * 设置位置
	 * 
	 * @param view
	 * @return
	 */
	public WISERDialogFragment setLocation(View view, int widget) {
		if (view == null) return this;
		this.isLocation = true;
		this.widget = widget;
		int[] locationView = new int[2];
		view.getLocationInWindow(locationView); // 获取在当前窗体内的绝对坐标
		view.getLocationOnScreen(locationView);// 获取在整个屏幕内的绝对坐标
		x = locationView[0];
		switch (widget) {
			case CONTROL_TOP:// 控件上
				y = locationView[1] - WISERApp.getStatusBarHeight();
				break;
			case CONTROL_BOTTOM:// 控件下
				y = locationView[1] + view.getHeight() - WISERApp.getStatusBarHeight();
				break;
			case CONTROL_FIT:// 控件上或者下适配
				if ((locationView[1] + view.getHeight() / 2) > WISERApp.getScreenHeight() / 2) {
					isLocationTop = true;
					y = locationView[1] - WISERApp.getStatusBarHeight();
				} else {
					isLocationTop = false;
					y = locationView[1] + view.getHeight() - WISERApp.getStatusBarHeight();
				}
				break;
			default:
				y = locationView[1] + view.getHeight() - WISERApp.getStatusBarHeight();
				break;
		}
		return this;
	}

	// 定制位置显示
	private void showLocation() {
		if (isLocation) {
			new Handler(Looper.getMainLooper()).post(new Runnable() {

				@Override public void run() {
					if (getDialog() != null && getDialog().getWindow() != null) {
						int[] location = new int[2];
						getDialog().getWindow().getDecorView().getLocationInWindow(location); // 获取在当前窗体内的绝对坐标
						getDialog().getWindow().getDecorView().getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标
						Window window = getDialog().getWindow();
						window.getDecorView().setPadding(0, 0, 0, 0);
						WindowManager.LayoutParams lp = window.getAttributes();
						lp.gravity = TOP; // 位置
						lp.x = -(location[0] - x);
						switch (widget) {
							case CONTROL_TOP:// 控件上
								lp.y = y - window.getDecorView().getHeight();
								break;
							case CONTROL_BOTTOM:// 控件下
								lp.y = y;
								break;
							case CONTROL_FIT:// 控件上或者下适配
								if (isLocationTop) lp.y = y - window.getDecorView().getHeight();
								else lp.y = y;
								break;
							default:

								break;
						}
						window.setAttributes(lp);
					}
				}
			});
		} else {
			Window window = getDialog().getWindow();
			if (window != null) {
				window.getDecorView().setPadding(0, 0, 0, 0);
				WindowManager.LayoutParams lp = window.getAttributes();
				lp.gravity = dialogWeight(); // 位置
				window.setAttributes(lp);
			}
		}
	}

}
