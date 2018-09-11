package com.wiser.library.base;

import com.wiser.library.tab.WISERPageView;
import com.wiser.library.tab.WISERTabPageView;
import com.wiser.library.tab.WISERTabView;

import android.content.Intent;

/**
 * @author Wiser
 * 
 *         tab page
 */
public abstract class WISERTabPageActivity extends WISERActivity {

	WISERTabPageView	tabPageView;

	public int			CURRENT_INDEX	= 0;

	protected abstract WISERTabPageView buildTabPageView(WISERTabPageView tabPageView);

	protected abstract WISERBuilder buildFrame(WISERBuilder builder);

	protected abstract void initTabPageViewData(Intent intent);

	@Override protected WISERBuilder build(WISERBuilder builder) {
		tabPageView = new WISERTabPageView(this);
		builder.layoutView(buildTabPageView(tabPageView));
		tabPageView.createLayout();
		return buildFrame(builder);
	}

	@Override protected void initData(Intent intent) {
		initTabPageViewData(intent);
	}

	protected WISERTabView tabView() {
		return tabPageView == null ? null : tabPageView.tabView();
	}

	protected WISERPageView pageView() {
		return tabPageView == null ? null : tabPageView.pageView();
	}

	protected WISERTabPageView tabPageView() {
		return tabPageView;
	}

	@Override protected void onPause() {
		super.onPause();
		if (isFinishing()) {
			detachTab();
		}
	}

	private void detachTab() {
		if (tabPageView != null) tabPageView.detach();
		tabPageView = null;
		CURRENT_INDEX = 0;
	}

	/**
	 * 设置当前页
	 * 
	 * @param i
	 *            位置
	 */
	protected void setCurrentItem(int i) {
		if (pageView() != null && tabView() != null) pageView().setCurrentItem(i, tabView().isTabCutPageAnim());
	}

	/**
	 * 设置当前页
	 *
	 * @param i
	 *            位置
	 * @param isAnim
	 *            是否切换需要动画
	 */
	protected void setCurrentItem(int i, boolean isAnim) {
		if (pageView() != null) pageView().setCurrentItem(i, isAnim);
	}
}
