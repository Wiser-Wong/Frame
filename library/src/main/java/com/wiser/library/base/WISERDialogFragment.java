package com.wiser.library.base;

import java.util.List;

import com.wiser.library.R;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERBizModel;
import com.wiser.library.util.WISERApp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public abstract class WISERDialogFragment<B extends IWISERBiz> extends DialogFragment
		implements IWISERView, SwipeRefreshLayout.OnRefreshListener, IWISERRVScrollListener.OnLoadMoreListener, DialogInterface.OnKeyListener {

	protected final int		TOP				= Gravity.TOP;

	protected final int		CENTER			= Gravity.CENTER;

	protected final int		BOTTOM			= Gravity.BOTTOM;

	protected final int		TOP_START		= Gravity.TOP | Gravity.START;

	protected final int		TOP_END			= Gravity.TOP | Gravity.END;

	protected final int		CENTER_START	= Gravity.CENTER | Gravity.START;

	protected final int		CENTER_END		= Gravity.CENTER | Gravity.END;

	protected final int		BOTTOM_START	= Gravity.BOTTOM | Gravity.START;

	protected final int		BOTTOM_END		= Gravity.BOTTOM | Gravity.END;

	private boolean			isLocation;											// 是否定制位置

	private int				x, y;												// 定制位置绝对坐标

	public static final int	CONTROL_TOP		= 7777;								// 控件上

	public static final int	CONTROL_BOTTOM	= 7778;								// 控件下

	public static final int	CONTROL_FIT		= 7779;								// 适应控件上或者下

	private int				widget			= CONTROL_BOTTOM;

	private boolean			isLocationTop;										// 是否控件上

	private B				b;

	private WISERBuilder	mWiserBuilder;

	private WISERBizModel	bizModel;

	private Unbinder		unbinder;

	protected abstract WISERBuilder build(WISERBuilder builder);

	protected abstract void initData(Bundle savedInstanceState);

	protected abstract int dialogWeight();

	protected abstract int dialogTheme();

	protected abstract boolean isWidthFullScreen();

	protected abstract boolean isCloseOnTouchOutside();

	protected abstract boolean isCloseOnTouchBack();

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

			// 定制位置显示
			showLocation();
		}

		// 创建构建类
		mWiserBuilder = new WISERBuilder(this, inflater);
		// 创建Biz储存对象
		bizModel = new WISERBizModel(this);
		// 管理Biz
		WISERHelper.getBizManage().attach(bizModel);
		// 填充视图
		View view = build(mWiserBuilder).createView();

		// 初始化所有组件
		unbinder = ButterKnife.bind(this, view);
		if (biz() != null) {
			// 将Fragment对应的实例传给biz
			biz().initUi(this);
			// 初始化Biz数据
			biz().initBiz(getArguments());
		}

		// 初始化数据
		initData(getArguments());
		initAfterData(savedInstanceState);

		if (getDialog() != null) {
			// 设置点击空白处是否关闭Dialog
			getDialog().setCanceledOnTouchOutside(isCloseOnTouchOutside());

			// 设置返回键点击是否关闭Dialog
			if (!isCloseOnTouchBack()) getDialog().setOnKeyListener(this);
		}

		return view;
	}

	public void initAfterData(Bundle savedInstanceState) {

	}

	// 当前Activity实例
	public WISERActivity activity() {
		return (WISERActivity) getActivity();
	}

	// 获取Adapter实例
	public WISERRVAdapter adapter() {
		// WISERCheckUtil.checkNotNull(mWiserBuilder.adapter(),
		// "未找到注册的RecycleAdapter实例");
		return mWiserBuilder.adapter();
	}

	// 获取RecycleView实例
	public RecyclerView recyclerView() {
		return mWiserBuilder.wiserRecycleView();
	}

	// 添加RecycleView 适配器
	public void setItems(List list) {
		if (adapter() != null) adapter().setItems(list);
	}

	// 显示空布局
	@Override public void showEmptyView() {
		if (mWiserBuilder != null) mWiserBuilder.showEmptyView();
	}

	// 显示错误布局
	@Override public void showErrorView() {
		if (mWiserBuilder != null) mWiserBuilder.showErrorView();
	}

	// 显示主布局
	@Override public void showContentView() {
		if (mWiserBuilder != null) mWiserBuilder.showContentView();
	}

	// 显示加载动画
	@Override public void showLoading() {
		if (mWiserBuilder != null) mWiserBuilder.showLoadingView();
	}

	// 刷新完成
	@Override public void refreshComplete() {
		if (mWiserBuilder != null) mWiserBuilder.refreshComplete();
	}

	// 正在刷新
	@Override public void onRefresh() {}

	// 上拉加载
	@Override public void onLoadMore() {}

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		if (bizModel != null) return (B) bizModel.biz();
		return null;
	}

	public <D extends IWISERDisplay> D display() {
		return (D) WISERHelper.display();
	}

	public WISERView wiserView() {
		return mWiserBuilder == null ? null : mWiserBuilder.wiserView();
	}

	@Override public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		return event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK;
	}

	@Override public void onDetach() {
		super.onDetach();

		// 销毁Activity对应的Biz实例
		WISERHelper.getBizManage().detach(bizModel);
		// 清空
		detach();
	}

	/**
	 * 清除引用
	 */
	public void detach() {
		if (mWiserBuilder != null) mWiserBuilder.detach();
		b = null;
		mWiserBuilder = null;
		bizModel = null;
		// 清空注解view
		unbinder.unbind();
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
