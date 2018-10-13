package com.wiser.library.base;

import java.util.List;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERActivityModel;
import com.wiser.library.model.WISERBizModel;
import com.wiser.library.util.WISERCheck;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public abstract class WISERActivity<B extends IWISERBiz> extends FragmentActivity implements IWISERView, SwipeRefreshLayout.OnRefreshListener, IWISERRVScrollListener.OnLoadMoreListener {

	private WISERBuilder		mWiserBuilder;

	private WISERBizModel		bizModel;

	private WISERActivityModel	activityModel;

	protected LayoutInflater	mInflater;

	protected abstract WISERBuilder build(WISERBuilder builder);

	protected abstract void initData(Intent intent);

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(this);
		// 创建构建类
		mWiserBuilder = new WISERBuilder(this, mInflater);
		// 创建Biz储存对象
		bizModel = new WISERBizModel(this);
		// Activity管理model
		activityModel = new WISERActivityModel(this, bizModel, false);
		// 管理Activity
		WISERHelper.getActivityManage().addActivity(activityModel);
		// 管理Biz
		WISERHelper.getBizManage().attach(bizModel);
		// 填充视图
		setContentView(build(mWiserBuilder).systemBarTheme().createView());

		// 注册滑动清除Activity
		mWiserBuilder.createSwipeBackActivity();

		// // 需要先注册SwipeBack在设置状态栏否则状态栏颜色没有效果
		// 状态栏颜色
		mWiserBuilder.systemBarColor();

		// 初始化所有组件
		ButterKnife.bind(this);
		if (biz() != null) {
			// 将Activity对应的实例传给biz
			biz().initUi(this);
			// 初始化Biz数据
			biz().initBiz(savedInstanceState);
			biz().initBiz(getIntent());
		}
		// 初始化数据
		initData(getIntent());
		initAfterData(savedInstanceState);
	}

	public void initAfterData(Bundle savedInstanceState) {

	}

	public WISERBuilder builder() {
		return mWiserBuilder;
	}

	public int getLayoutId() {
		if (mWiserBuilder != null) return mWiserBuilder.getLayoutId();
		return 0;
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

	// 获取Adapter实例
	public WISERRVAdapter adapter() {
		WISERCheck.checkNotNull(mWiserBuilder.adapter(), "未找到注册的RecycleAdapter实例");
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

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		if (bizModel != null) {
			return (B) bizModel.biz();
		}
		return null;
	}

	public <D extends IWISERDisplay> D display() {
		return (D) WISERHelper.display();
	}

	/**
	 * @param <T>
	 *            参数
	 * @param clazz
	 *            参数
	 * @return 返回值
	 */
	public <T> T findFragment(Class<T> clazz) {
		if (clazz == null) return null;
		return (T) getSupportFragmentManager().findFragmentByTag(clazz.getName());
	}

	public WISERView wiserView() {
		return mWiserBuilder == null ? null : mWiserBuilder.wiserView();
	}

	@Override protected void onResume() {
		super.onResume();
		// 更改Activity为运行状态
		WISERHelper.getActivityManage().onResume(this);
		// 打印存在Activity和Biz的日志
		WISERHelper.getActivityManage().logActivityList();
	}

	@Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (mWiserBuilder.isSwipeBack()) SwipeBackHelper.onPostCreate(this);
	}

	@Override protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			// 结束当前Activity
			WISERHelper.getActivityManage().finishActivity(activityModel);
			// // 销毁Activity对应的Biz实例
			// WISERHelper.getBizManage().detach(bizModel);
			// 滑动清除Activity
			mWiserBuilder.destroySwipeBackActivity();

			// 清除实例
			detach();
		}
		// 更改Activity为暂停状态
		WISERHelper.getActivityManage().onPause(this);
	}

	/**
	 * 清除引用
	 */
	public void detach() {
		if (mWiserBuilder != null) mWiserBuilder.detach();
		mWiserBuilder = null;
		bizModel = null;
		activityModel = null;
	}

}
