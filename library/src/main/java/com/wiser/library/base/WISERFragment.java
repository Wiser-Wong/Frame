package com.wiser.library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.helper.IWISERDisplay;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.model.WISERBizModel;
import com.wiser.library.util.WISERGenericSuperclass;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public abstract class WISERFragment<B extends IWISERBiz> extends Fragment implements IWISERView, SwipeRefreshLayout.OnRefreshListener, IWISERRVScrollListener.OnLoadMoreListener {

	private B				b;

	private WISERBuilder	mWiserBuilder;

	private WISERBizModel	bizModel;

	private Unbinder		unbinder;

	protected abstract WISERBuilder build(WISERBuilder builder);

	protected abstract void initData(Bundle savedInstanceState);

	@Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		// 创建构建类
		mWiserBuilder = new WISERBuilder(this, inflater);
		// 创建Biz储存对象
		bizModel = new WISERBizModel(biz());
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
		return view;
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
	@Override public void onLoadMore() {
	}

	/**
	 * 获取泛型实例
	 *
	 * @return
	 */
	public B biz() {
		try {
			if (b == null) b = (B) WISERGenericSuperclass.getActualTypeArgument(this.getClass()).newInstance();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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
		return (T) activity().getSupportFragmentManager().findFragmentByTag(clazz.getName());
	}

	public WISERView wiserView() {
		return mWiserBuilder == null ? null : mWiserBuilder.wiserView();
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
}
