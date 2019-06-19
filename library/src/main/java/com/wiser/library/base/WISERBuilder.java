package com.wiser.library.base;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCheck;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERBuilder {

	private int						layoutBarId;								// 标题ID

	private int						layoutId;									// 布局ID

	private int						layoutEmptyId;								// 空布局ID

	private int						layoutErrorId;								// 错误布局ID

	private int						layoutLoadingId;							// loading布局ID

	private int						state;										// 显示的是Activity 还是 Fragment 或者DialogFragment

	private boolean					isRefresh					= false;		// 是否下拉刷新

	private boolean					isDFRefresh					= false;		// 是否默认进入页面就开始下拉刷新

	private int[]					refreshColors				= new int[0];	// 刷新loading颜色值变化

	private int						refreshBgColor				= -1;			// 刷新loading背景颜色

	private FrameLayout				contentRoot;								// 根布局

	private LinearLayout			contentToolBar;								// 带title带主布局

	private FrameLayout				contentLayout;								// 带title带主布局

	private SwipeRefreshLayout		layoutRefresh;								// 刷新布局

	private View					toolBarView;								// 标题布局

	private View					contentView;								// 主布局

	private View					layoutEmpty;								// 空布局

	private View					layoutError;								// 错误布局

	private View					layoutLoading;								// loading

	private LayoutInflater			mInflater;

	private WISERView				wiserView;

	private WISERRecycleView		mRecycleView;

	private int						titleViewId;

	private int						backViewId;

	private String					titleName;

	/**
	 * TintManger
	 */
	private SystemBarTintManager	tintManager;

	private int						tintColor;									// 状态栏颜色

	private boolean					statusBarEnabled			= true;

	private boolean					navigationBarTintEnabled	= true;

	// private boolean fitsSystem = false; // 是否填充系统状态栏

	private boolean					tint;										// 状态栏颜色

	private boolean					isSwipeBack					= false;		// 是否滑动返回上一个Activity

	private int						requestedOrientation		= -333;			// 屏幕变化

	/**
	 * 构造器
	 *
	 * @param mWiserActivity
	 *            参数
	 * @param inflater
	 *            参数
	 */
	WISERBuilder(@NonNull WISERActivity mWiserActivity, @NonNull LayoutInflater inflater) {
		this.state = WISERView.STATE_ACTIVITY;
		wiserView = new WISERView();
		wiserView.initUI(mWiserActivity);
		this.mInflater = inflater;
		this.mRecycleView = new WISERRecycleView(wiserView, state);
	}

	/**
	 * 构造器
	 *
	 * @param mWiserFragment
	 *            参数
	 * @param inflater
	 *            参数
	 */
	WISERBuilder(@NonNull WISERFragment mWiserFragment, @NonNull LayoutInflater inflater) {
		this.state = WISERView.STATE_FRAGMENT;
		wiserView = new WISERView();
		wiserView.initUI(mWiserFragment);
		this.mInflater = inflater;
		this.mRecycleView = new WISERRecycleView(wiserView, state);
	}

	/**
	 * 构造器
	 *
	 * @param mWiserDialogFragment
	 *            参数
	 * @param inflater
	 *            参数
	 */
	WISERBuilder(@NonNull WISERDialogFragment mWiserDialogFragment, @NonNull LayoutInflater inflater) {
		this.state = WISERView.STATE_DIALOG_FRAGMENT;
		wiserView = new WISERView();
		wiserView.initUI(mWiserDialogFragment);
		this.mInflater = inflater;
		this.mRecycleView = new WISERRecycleView(wiserView, state);
	}

	WISERView wiserView() {
		return wiserView;
	}

	public WISERRecycleView recycleView() {
		return mRecycleView;
	}

	WISERRVAdapter adapter() {
		if (mRecycleView != null) return mRecycleView.adapter();
		return null;
	}

	RecyclerView wiserRecycleView() {
		if (mRecycleView != null) return mRecycleView.recyclerView();
		return null;
	}

	public void setRequestedOrientation(int requestedOrientation) {
		this.requestedOrientation = requestedOrientation;
	}

	public void isRootLayoutRefresh(boolean isRefresh, boolean isDFRefresh) {
		this.isRefresh = isRefresh;
		this.isDFRefresh = isDFRefresh;
	}

	public void setColorSchemeColors(@ColorInt int... colors) {
		this.refreshColors = colors;
	}

	public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
		this.refreshBgColor = color;
	}

	public void layoutBarId(@LayoutRes int layoutBarId) {
		this.layoutBarId = layoutBarId;
	}

	private int getLayoutBarId() {
		return layoutBarId;
	}

	public void titleViewId(@IdRes int titleViewId, String titleName) {
		this.titleViewId = titleViewId;
		this.titleName = titleName;
	}

	public void backViewId(@IdRes int backViewId) {
		this.backViewId = backViewId;
	}

	public void titleBarViewId(@IdRes int titleViewId, String titleName, @IdRes int backViewId) {
		this.titleViewId = titleViewId;
		this.titleName = titleName;
		this.backViewId = backViewId;
	}

	public void layoutId(@LayoutRes int layoutId) {
		this.layoutId = layoutId;
	}

	protected int getLayoutId() {
		return layoutId;
	}

	public void layoutEmptyId(@LayoutRes int layoutEmptyId) {
		this.layoutEmptyId = layoutEmptyId;
	}

	private int getLayoutEmptyId() {
		return layoutEmptyId;
	}

	public void layoutErrorId(@LayoutRes int layoutErrorId) {
		this.layoutErrorId = layoutErrorId;
	}

	private int getLayoutErrorId() {
		return layoutErrorId;
	}

	public void layoutLoadingId(@LayoutRes int layoutLoadingId) {
		this.layoutLoadingId = layoutLoadingId;
	}

	private int getLayoutLoadingId() {
		return layoutLoadingId;
	}

	public void layoutView(View layoutView) {
		this.contentView = layoutView;
	}

	private int getTintColor() {
		return tintColor;
	}

	boolean isTintColor() {
		return tintColor > 0;
	}

	public void tintColor(@ColorInt int tintColor) {
		this.tintColor = tintColor;
	}

	private boolean getStatusBarTintEnabled() {
		return statusBarEnabled;
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

	// public void tintFitsSystem(boolean isFitsSystem) {
	// this.fitsSystem = isFitsSystem;
	// }

	// private boolean isFitsSystem() {
	// return fitsSystem;
	// }

	public void swipeBack(boolean isSwipeBack) {
		this.isSwipeBack = isSwipeBack;
	}

	boolean isSwipeBack() {
		return isSwipeBack;
	}

	protected SystemBarTintManager tintManager() {
		return tintManager;
	}

	// /**
	// * 状态栏高度
	// */
	// void systemBarWindow() {
	// if (isFitsSystem()) {
	// ViewGroup contentFrameLayout =
	// wiserView.activity().findViewById(Window.ID_ANDROID_CONTENT);
	// View parentView = contentFrameLayout.getChildAt(0);
	// if (parentView != null && Build.VERSION.SDK_INT >=
	// Build.VERSION_CODES.KITKAT) {
	// parentView.setFitsSystemWindows(true);
	// setTranslucentStatus(true);
	// }
	// }
	// }

	/**
	 * 全透状态栏
	 */
	public void setStatusBarFullTransparent() {
		if (Build.VERSION.SDK_INT >= 21) {// 21表示5.0
			Window window = wiserView.activity().getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= 19) {// 19表示4.4
			wiserView.activity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 虚拟键盘也透明
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 半透明状态栏
	 */
	public void setHalfTransparent() {
		if (Build.VERSION.SDK_INT >= 21) {// 21表示5.0
			View decorView = wiserView.activity().getWindow().getDecorView();
			int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
			decorView.setSystemUiVisibility(option);
			wiserView.activity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else if (Build.VERSION.SDK_INT >= 19) {// 19表示4.4
			wiserView.activity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 虚拟键盘也透明
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	/**
	 * 状态栏颜色
	 */
	void systemBarColor() {
		if (isTint()) {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				setTranslucentStatus(true);
			}

			tintManager = new SystemBarTintManager(wiserView.activity());
			// enable status bar tint
			tintManager.setStatusBarTintEnabled(getStatusBarTintEnabled());
			// enable navigation bar tint
			tintManager.setNavigationBarTintEnabled(getNavigationBarTintEnabled());
			tintManager.setStatusBarTintColor(getTintColor());
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.KITKAT) private void setTranslucentStatus(boolean on) {
		Window win = wiserView.activity().getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	// 设置全屏显示开关 是否去掉状态栏
	public void setFullScreenToggle(boolean isFull) {
		if (isFull) {
			WindowManager.LayoutParams lp = wiserView.activity().getWindow().getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			wiserView.activity().getWindow().setAttributes(lp);
			wiserView.activity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attr = wiserView.activity().getWindow().getAttributes();
			attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			wiserView.activity().getWindow().setAttributes(attr);
			wiserView.activity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}

	// 去除状态栏
	public void removeStateBar() {
		// 无title
		wiserView.activity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 全屏
		wiserView.activity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 显示虚拟按键
	 */
	public void showVirtualKey() {
		// 显示虚拟按键
		if (Build.VERSION.SDK_INT < 19) {
			// 低版本sdk
			View v = wiserView.activity().getWindow().getDecorView();
			v.setSystemUiVisibility(View.VISIBLE);
		} else {
			View decorView = wiserView.activity().getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	/**
	 * 隐藏虚拟按键
	 */
	public void hideVirtualKey() {
		// 隐藏虚拟按键
		if (Build.VERSION.SDK_INT < 19) {
			View v = wiserView.activity().getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else {
			View decorView = wiserView.activity().getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	/**
	 * 注册滑动清除Activity创建
	 * //需要app风格设置为<item name="android:windowIsTranslucent">true</item>
	 */
	void createSwipeBackActivity() {
		if (isSwipeBack()) {
			SwipeBackHelper.onCreate(wiserView.activity());
			// SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(true).setScrimColor(android.R.color.transparent).setSwipeSensitivity(0.5f).setSwipeRelateEnable(true).setSwipeRelateOffset(300);
			SwipeBackHelper.getCurrentPage(wiserView.activity())// 获取当前页面
					.setSwipeBackEnable(true)// 设置是否可滑动
					.setSwipeEdge(200)// 可滑动的范围。px。200表示为左边200px的屏幕
					.setSwipeEdgePercent(0.2f)// 可滑动的范围。百分比。0.2表示为左边20%的屏幕
					.setSwipeSensitivity(0.5f)// 对横向滑动手势的敏感程度。0为迟钝 1为敏感
					.setScrimColor(Color.TRANSPARENT)// 底层阴影颜色
					.setClosePercent(0.8f)// 触发关闭Activity百分比
					.setSwipeRelateEnable(true)// 是否与下一级activity联动(微信效果)。默认关
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
									wiserView.activity().overridePendingTransition(0, 0);
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
		if (isSwipeBack()) SwipeBackHelper.onDestroy(wiserView.activity());
	}

	/**
	 * 沉浸式状态栏风格 <android.support.v7.widget.Toolbar需要用到toolbar布局才可以达到不被渗透到状态栏里
	 *
	 * @return
	 */
	@SuppressLint("WrongConstant") WISERBuilder systemBarTheme() {
		if (requestedOrientation != -333)
			// 设置屏幕方向
			wiserView.activity().setRequestedOrientation(requestedOrientation);

		// if (isFitsSystem()) {
		// wiserView.activity().setTheme(R.style.TranslucentStatus);
		// }
		return this;
	}

	/**
	 * 刷新完成
	 */
	void refreshComplete() {
		if (isRefresh && layoutRefresh != null) {
			layoutRefresh.setRefreshing(false);
		}
	}

	/**
	 * 创建视图
	 *
	 * @return
	 */
	View createView() {

		contentRoot = new FrameLayout(wiserView.activity());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		contentRoot.setLayoutParams(layoutParams);
		// 创建主布局 包括 toolBar
		createContentToolBar();
		// toolBar
		if (getLayoutBarId() > 0) {
			toolBarView = mInflater.inflate(getLayoutBarId(), contentToolBar, false);
			contentToolBar.addView(toolBarView);
		}
		// 创建内容布局
		createContentLayout();
		// 下拉刷新布局
		createRVRefreshView();
		// 内容
		if (getLayoutId() > 0) {
			contentView = mInflater.inflate(getLayoutId(), contentLayout, false);
			if (isRefresh) layoutRefresh.addView(contentView);
			else contentLayout.addView(contentView);
		} else {
			if (contentView != null) {
				if (isRefresh) layoutRefresh.addView(contentView);
				else contentLayout.addView(contentView);
			}
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
	 * 设置title 内容 以及控制
	 */
	void setToolBarControl() {
		if (toolBarView == null) return;
		if (titleViewId > 0) {
			View titleView = ButterKnife.findById(toolBarView, titleViewId);
			if (titleView instanceof TextView && !WISERCheck.isEmpty(titleName)) {
				((TextView) titleView).setText(titleName);
			}
		}
		if (backViewId > 0) {
			View backView = ButterKnife.findById(toolBarView, backViewId);
			if (backView != null) {
				backView.setOnClickListener(new View.OnClickListener() {

					@Override public void onClick(View v) {
						if (state == WISERView.STATE_ACTIVITY && wiserView != null && wiserView.activity() != null) {
							wiserView.activity().finish();
						}
					}
				});
			}
		}
	}

	/**
	 * 创建SwipeRefreshLayout
	 */
	void createRVRefreshView() {
		if (isRefresh) {
			layoutRefresh = new SwipeRefreshLayout(wiserView.activity());
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
			layoutRefresh.setLayoutParams(layoutParams);
			contentLayout.addView(layoutRefresh);
			if (refreshBgColor != -1) layoutRefresh.setProgressBackgroundColorSchemeColor(refreshBgColor);
			layoutRefresh.setProgressViewEndTarget(true, layoutRefresh.getProgressCircleDiameter() + 25);
			if (refreshColors.length > 0) layoutRefresh.setColorSchemeColors(refreshColors);
			switch (state) {
				case WISERView.STATE_ACTIVITY:
					layoutRefresh.setOnRefreshListener(wiserView.activity());
					break;
				case WISERView.STATE_FRAGMENT:
					layoutRefresh.setOnRefreshListener(wiserView.fragment());
					break;
				case WISERView.STATE_DIALOG_FRAGMENT:
					layoutRefresh.setOnRefreshListener(wiserView.dialogFragment());
					break;
			}
			// 默认刷新
			if (isDFRefresh) layoutRefresh.setRefreshing(true);
		}
	}

	/**
	 * 创建内容以及toolBar布局
	 */
	private void createContentToolBar() {
		contentToolBar = new LinearLayout(wiserView.activity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		contentToolBar.setLayoutParams(params);
		contentToolBar.setOrientation(LinearLayout.VERTICAL);
		contentRoot.addView(contentToolBar);
	}

	/**
	 * 创建内容布局
	 */
	private void createContentLayout() {
		contentLayout = new FrameLayout(wiserView.activity());
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		contentLayout.setLayoutParams(params);
		contentToolBar.addView(contentLayout);
	}

	/**
	 * 创建空布局
	 */
	private void createEmptyView() {
		if (getLayoutEmptyId() > 0) {
			layoutEmpty = mInflater.inflate(getLayoutEmptyId(), contentLayout, false);
			WISERCheck.checkNotNull(layoutEmpty, "无法根据布局文件ID,获取layoutEmpty");
			if (isRefresh) contentLayout.addView(layoutEmpty);
			layoutEmpty.setVisibility(View.GONE);
		}
	}

	/**
	 * 创建错误布局
	 */
	private void createErrorView() {
		if (getLayoutErrorId() > 0) {
			layoutError = mInflater.inflate(getLayoutErrorId(), contentLayout, false);
			WISERCheck.checkNotNull(layoutError, "无法根据布局文件ID,获取layoutError");
			contentLayout.addView(layoutError);
			layoutError.setVisibility(View.GONE);
		}
	}

	/**
	 * 创建loading布局
	 */
	private void createLoadingView() {
		if (getLayoutLoadingId() > 0) {
			layoutLoading = mInflater.inflate(getLayoutLoadingId(), contentLayout, false);
			WISERCheck.checkNotNull(layoutLoading, "无法根据布局文件ID,获取layoutLoading");
			contentLayout.addView(layoutLoading);
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
		if (isRefresh) changeShowLayoutAndAnim(layoutRefresh, false);
		else changeShowLayoutAndAnim(contentView, false);
		changeShowLayoutAndAnim(layoutError, true);
	}

	/**
	 * 显示空布局
	 */
	void showEmptyView() {
		if (layoutEmpty == null) return;
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, false);
		if (isRefresh) changeShowLayoutAndAnim(layoutRefresh, false);
		else changeShowLayoutAndAnim(contentView, false);
		changeShowLayoutAndAnim(layoutEmpty, true);
	}

	/**
	 * 显示主布局
	 */
	void showContentView() {
		if (contentView == null) return;
		changeShowLayoutAndAnim(layoutEmpty, false);
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, false);
		if (isRefresh) changeShowLayoutAndAnim(layoutRefresh, true);
		else changeShowLayoutAndAnim(contentView, true);
	}

	/**
	 * 显示Loading
	 */
	void showLoadingView() {
		if (layoutLoading == null) return;
		if (isRefresh) changeShowLayoutAndAnim(layoutRefresh, false);
		else changeShowLayoutAndAnim(contentView, false);
		changeShowLayoutAndAnim(layoutEmpty, false);
		changeShowLayoutAndAnim(layoutError, false);
		changeShowLayoutAndAnim(layoutLoading, true);
	}

	/**
	 * 加载刷新
	 */
	boolean loadingRefresh() {
		if (isRefresh && layoutRefresh != null) {
			layoutRefresh.setRefreshing(true);
			return true;
		}
		return false;
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
			anim = AnimationUtils.loadAnimation(wiserView.activity(), android.R.anim.fade_in);
		} else {
			if (view.getVisibility() == View.GONE) {
				return;
			}
			view.setVisibility(View.GONE);
			anim = AnimationUtils.loadAnimation(wiserView.activity(), android.R.anim.fade_out);
		}
		anim.setDuration(wiserView.activity().getResources().getInteger(android.R.integer.config_shortAnimTime));
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
		if (wiserView != null) wiserView.detach();
		wiserView = null;
		if (mRecycleView != null) mRecycleView.detach();
		mRecycleView = null;
		tintManager = null;
		contentRoot = null;
		layoutRefresh = null;
		contentToolBar = null;
		toolBarView = null;
		contentLayout = null;
		refreshColors = null;
		contentView = null;
		layoutError = null;
		layoutEmpty = null;
		layoutLoading = null;
		mInflater = null;
		layoutId = 0;
		layoutBarId = 0;
		layoutErrorId = 0;
		layoutLoadingId = 0;
		layoutEmptyId = 0;
	}

	/**
	 * 清除实例
	 */
	private void detachObj() {
		tintManager = null;
	}

}
