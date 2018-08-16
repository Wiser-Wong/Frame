package com.wiser.library.base;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wiser.library.R;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCheckUtil;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERBuilder {

	private int						layoutId;								// 布局ID

	private int						layoutEmptyId;							// 空布局ID

	private int						layoutErrorId;							// 错误布局ID

	private int						layoutLoadingId;						// loading布局ID

	private FrameLayout				contentRoot;							// 根布局

	private View					layoutContent;							// 主布局

	private View					layoutEmpty;							// 空布局

	private View					layoutError;							// 错误布局

	private View					layoutLoading;							// loading

	private LayoutInflater			mInflater;

	private WISERActivity			mWiserActivity;

	private WISERRecycleView		mRecycleView;

	/**
	 * TintManger
	 */
	private SystemBarTintManager	tintManager;

	private int						tintColor;								// 状态栏颜色

	private boolean					statusBarEnabled			= true;

	private boolean					navigationBarTintEnabled	= true;

	private boolean					fitsSystem					= true;		// 是否填充系统状态栏

	private boolean					tint;									// 状态栏颜色

	private boolean					isSwipeBack					= false;	// 是否滑动返回上一个Activity

	/**
	 * 构造器
	 *
	 * @param mWiserActivity
	 *            参数
	 * @param inflater
	 *            参数
	 */
	WISERBuilder(@NonNull WISERActivity mWiserActivity, @NonNull LayoutInflater inflater) {
		this.mWiserActivity = mWiserActivity;
		this.mInflater = inflater;
		this.mRecycleView = new WISERRecycleView(mWiserActivity);
	}

	public WISERRecycleView recycleView() {
		return mRecycleView;
	}

	public WISERRVAdapter adapter() {
		return mRecycleView.adapter();
	}

	public void layoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	private int getLayoutId() {
		return layoutId;
	}

	public void layoutEmptyId(int layoutEmptyId) {
		this.layoutEmptyId = layoutEmptyId;
	}

	private int getLayoutEmptyId() {
		return layoutEmptyId;
	}

	public void layoutErrorId(int layoutErrorId) {
		this.layoutErrorId = layoutErrorId;
	}

	private int getLayoutErrorId() {
		return layoutErrorId;
	}

	public void layoutLoadingId(int layoutLoadingId) {
		this.layoutLoadingId = layoutLoadingId;
	}

	private int getLayoutLoadingId() {
		return layoutLoadingId;
	}

	private int getTintColor() {
		return tintColor;
	}

	boolean isTintColor() {
		return tintColor > 0;
	}

	public void tintColor(int tintColor) {
		this.tintColor = tintColor;
	}

	private boolean getStatusBarTintEnabled() {
		return statusBarEnabled;
	}

	public void tintStatusBarEnabled(boolean isStatusBar) {
		this.statusBarEnabled = isStatusBar;
	}

	public void tintNavigationBarEnabled(boolean isNavigationBar) {
		this.navigationBarTintEnabled = isNavigationBar;
	}

	private boolean getNavigationBarTintEnabled() {
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

	private boolean isFitsSystem() {
		return fitsSystem;
	}

	public void swipeBack(boolean isSwipeBack) {
		this.isSwipeBack = isSwipeBack;
	}

	public boolean isSwipeBack() {
		return isSwipeBack;
	}

	protected SystemBarTintManager tintManager() {
		return tintManager;
	}

	/**
	 * 状态栏高度
	 */
	public void systemBarWindow() {
		if (isFitsSystem()) {
			ViewGroup contentFrameLayout = mWiserActivity.findViewById(Window.ID_ANDROID_CONTENT);
			View parentView = contentFrameLayout.getChildAt(0);
			if (parentView != null && Build.VERSION.SDK_INT >= 15) {
				parentView.setFitsSystemWindows(true);
			}
		}
	}

	/**
	 * 状态栏颜色
	 */
	void systemBarColor() {
		if (isTint()) {
			tintManager = new SystemBarTintManager(mWiserActivity);
			// enable status bar tint
			tintManager.setStatusBarTintEnabled(getStatusBarTintEnabled());
			// enable navigation bar tint
			tintManager.setNavigationBarTintEnabled(getNavigationBarTintEnabled());
			tintManager.setStatusBarTintColor(getTintColor());
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT) private void setTranslucentStatus(boolean on) {
		Window win = mWiserActivity.getWindow();
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
	 * 注册滑动清除Activity创建
	 * //需要app风格设置为<item name="android:windowIsTranslucent">true</item>
	 */
	void createSwipeBackActivity() {
		if (isSwipeBack()) {
			SwipeBackHelper.onCreate(mWiserActivity);
			// SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(true).setScrimColor(android.R.color.transparent).setSwipeSensitivity(0.5f).setSwipeRelateEnable(true).setSwipeRelateOffset(300);
			SwipeBackHelper.getCurrentPage(mWiserActivity)// 获取当前页面
					.setSwipeBackEnable(true)// 设置是否可滑动
					.setSwipeEdge(200)// 可滑动的范围。px。200表示为左边200px的屏幕
					.setSwipeEdgePercent(0.2f)// 可滑动的范围。百分比。0.2表示为左边20%的屏幕
					.setSwipeSensitivity(0.5f)// 对横向滑动手势的敏感程度。0为迟钝 1为敏感
					.setScrimColor(Color.TRANSPARENT)// 底层阴影颜色
					.setClosePercent(0.8f)// 触发关闭Activity百分比
					.setSwipeRelateEnable(false)// 是否与下一级activity联动(微信效果)。默认关
					.setSwipeRelateOffset(500)// activity联动时的偏移量。默认500px。
					.setDisallowInterceptTouchEvent(false)// 不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
					.addListener(new SwipeListener() {// 滑动监听

						@Override public void onScroll(float percent, int px) {// 滑动的百分比与距离
						}

						@Override public void onEdgeTouch() {// 当开始滑动
						}

						@Override public void onScrollToClose() {// 当滑动关闭
							WISERHelper.mainLooper().execute(new Runnable() {

								@Override public void run() {
									// 需要手动取消Activity动画切换 否则会出现透明当前Activity再次动画切换
									mWiserActivity.overridePendingTransition(0, 0);
								}
							});
						}
					});
		}
	}

	/**
	 * 销毁滑动清除的Activity
	 */
	void destroySwipeBackActivity() {
		if (isSwipeBack()) SwipeBackHelper.onDestroy(mWiserActivity);
	}

	/**
	 * 沉浸式状态栏风格 <android.support.v7.widget.Toolbar需要用到toolbar布局才可以达到不被渗透到状态栏里
	 *
	 * @return
	 */
	public WISERBuilder systemBarTheme() {
		if (isFitsSystem()) {
			mWiserActivity.setTheme(R.style.TranslucentStatus);
		}
		return this;
	}

	/**
	 * 创建视图
	 *
	 * @return
	 */
	View createView() {
		contentRoot = new FrameLayout(mWiserActivity);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		contentRoot.setLayoutParams(layoutParams);
		// 内容
		if (getLayoutId() > 0) {
			layoutContent = mInflater.inflate(getLayoutId(), contentRoot, false);
			contentRoot.addView(layoutContent);
		}
		// RecycleView
		mRecycleView.createRecycleView(contentRoot);
		// 创建空布局
		createEmptyView();
		// 创建错误布局
		createErrorView();
		// loading布局
		createLoadingView();
		return contentRoot;
	}

	/**
	 * 创建空布局
	 */
	private void createEmptyView() {
		if (getLayoutEmptyId() > 0) {
			layoutEmpty = mInflater.inflate(getLayoutEmptyId(), contentRoot, false);
			WISERCheckUtil.checkNotNull(layoutEmpty, "无法根据布局文件ID,获取layoutEmpty");
			contentRoot.addView(layoutEmpty);
			layoutEmpty.setVisibility(View.GONE);
		}
	}

	/**
	 * 创建错误布局
	 */
	private void createErrorView() {
		if (getLayoutErrorId() > 0) {
			layoutError = mInflater.inflate(getLayoutErrorId(), contentRoot, false);
			WISERCheckUtil.checkNotNull(layoutError, "无法根据布局文件ID,获取layoutError");
			contentRoot.addView(layoutError);
			layoutError.setVisibility(View.GONE);
		}
	}

	/**
	 * 创建loading布局
	 */
	private void createLoadingView() {
		if (getLayoutLoadingId() > 0) {
			layoutLoading = mInflater.inflate(getLayoutLoadingId(), contentRoot, false);
			WISERCheckUtil.checkNotNull(layoutLoading, "无法根据布局文件ID,获取layoutLoading");
			contentRoot.addView(layoutLoading);
			layoutLoading.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示错误布局
	 */
	void showErrorView() {
		if (layoutError == null) return;
		changeShowLayoutAndAnim(layoutEmpty, false);
		changeShowLayoutAndAnim(layoutLoading, false);
		changeShowLayoutAndAnim(layoutContent, false);
		changeShowLayoutAndAnim(layoutError, true);
	}

	/**
	 * 显示空布局
	 */
	void showEmptyView() {
		if (layoutEmpty == null) return;
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, false);
		changeShowLayoutAndAnim(layoutContent, false);
		changeShowLayoutAndAnim(layoutEmpty, true);
	}

	/**
	 * 显示主布局
	 */
	void showContentView() {
		if (layoutContent == null) return;
		changeShowLayoutAndAnim(layoutEmpty, false);
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, false);
		changeShowLayoutAndAnim(layoutContent, true);
	}

	/**
	 * 显示Loading
	 */
	void showLoadingView() {
		if (layoutLoading == null) return;
		changeShowLayoutAndAnim(layoutContent, false);
		changeShowLayoutAndAnim(layoutEmpty, false);
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, true);
	}

	/**
	 * 改变布局显示增加动画显示隐藏
	 * 
	 * @param view
	 *            布局
	 * @param visible
	 *            是否显示
	 */
	private void changeShowLayoutAndAnim(View view, boolean visible) {
		if (view == null) {
			return;
		}
		Animation anim;
		if (visible) {
			if (view.getVisibility() == View.VISIBLE) {
				return;
			}
			view.setVisibility(View.VISIBLE);
			anim = AnimationUtils.loadAnimation(mWiserActivity, android.R.anim.fade_in);
		} else {
			if (view.getVisibility() == View.GONE) {
				return;
			}
			view.setVisibility(View.GONE);
			anim = AnimationUtils.loadAnimation(mWiserActivity, android.R.anim.fade_out);
		}
		anim.setDuration(mWiserActivity.getResources().getInteger(android.R.integer.config_mediumAnimTime));
		view.startAnimation(anim);
	}

	/**
	 * 清空所有
	 */
	void detach() {
		// 清除视图实例
		detachView();
		// 清除实例
		detachObj();
	}

	/**
	 * 清除视图实例
	 */
	private void detachView() {
		contentRoot = null;
		layoutContent = null;
		layoutError = null;
		layoutEmpty = null;
		layoutLoading = null;
		mInflater = null;
	}

	/**
	 * 清除实例
	 */
	private void detachObj() {
		mWiserActivity = null;
		tintManager = null;
		if (mRecycleView != null) mRecycleView.detach();
		mRecycleView = null;
	}

}
