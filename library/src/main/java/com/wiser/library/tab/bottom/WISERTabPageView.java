package com.wiser.library.tab.bottom;

import java.lang.reflect.Method;

import com.wiser.library.base.WISERTabPageActivity;
import com.wiser.library.base.WISERTabPageFragment;
import com.wiser.library.util.WISERApp;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * @author Wiser
 * 
 *         Tab Page View
 */
public class WISERTabPageView extends RelativeLayout implements ViewPager.OnPageChangeListener {

	private WISERPageView			pageView;						// 页 布局

	private WISERTabView			tabView;						// tab 布局

	private WISERTabPageActivity	wiserTabPageActivity;

	private WISERTabPageFragment	wiserTabPageFragment;

	private OnTabPageChangeListener	onTabPageChangeListener;

	private boolean					isFragment;

	private Fragment[]				fragments	= new Fragment[0];

	private int						index;

	private boolean					isResumePage;

	public WISERTabPageView(WISERTabPageActivity wiserTabPageActivity) {
		super(wiserTabPageActivity);
		this.isFragment = false;
		this.wiserTabPageActivity = wiserTabPageActivity;
		init();
	}

	public WISERTabPageView(WISERTabPageFragment wiserTabPageFragment) {
		super(wiserTabPageFragment.activity());
		this.isFragment = true;
		this.wiserTabPageFragment = wiserTabPageFragment;
		init();
	}

	public WISERTabPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WISERPageView pageView() {
		return pageView;
	}

	public WISERTabView tabView() {
		return tabView;
	}

	private void init() {
		LayoutInflater mInflater = LayoutInflater.from(getContext());

		pageView = new WISERPageView(getContext());
		if (isFragment) tabView = new WISERTabView(wiserTabPageFragment, mInflater);
		else tabView = new WISERTabView(wiserTabPageActivity, mInflater);
		tabView.setPageView(pageView);

		pageView.addOnPageChangeListener(this);

	}

	/**
	 * 创建布局
	 */
	public WISERTabPageView createLayout() {

		if (tabView != null && tabView.getTabRootView() != null) {
			addView(tabView.getTabRootView());
		}
		if (pageView != null) addView(pageView);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(params);

		LayoutParams pageParams = (LayoutParams) pageView.getLayoutParams();
		pageParams.alignWithParent = true;
		pageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		if (tabView != null && tabView.getTabRootView() != null) pageParams.addRule(RelativeLayout.ABOVE, tabView.getTabRootView().getId());

		if (tabView != null && tabView.getTabRootView() != null) {
			LayoutParams tabParams = (LayoutParams) tabView.getTabRootView().getLayoutParams();
			tabParams.alignWithParent = true;
			tabParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}
		return this;
	}

	/**
	 * 是否Tab 点击 page 可以滑动
	 * 
	 * @param isTabCutPageAnim
	 */
	public void isTabCutPageAnim(boolean isTabCutPageAnim) {
		if (tabView != null) tabView.isTabCutPageAnim(isTabCutPageAnim);
	}

	/**
	 * 是否可以多次点击Tab
	 * 
	 * @param isRepeatedlyClick
	 */
	public void isTabCanRepeatedlyClick(boolean isRepeatedlyClick) {
		if (tabView != null) tabView.isTabCanRepeatedlyClick(isRepeatedlyClick);
	}

	/**
	 * 设置是否page可以滑动
	 * 
	 * @param isScroll
	 */
	public void isPageCanScroll(boolean isScroll) {
		if (pageView != null) pageView.isScroll(isScroll);
	}

	/**
	 * 设置页码
	 *
	 * @param fragments
	 */
	public void setPages(Fragment... fragments) {
		if (pageView != null) pageView.setPageAdapter(fragments);
		this.fragments = fragments;
	}

	/**
	 * 设置tab id 以及 tabLayout
	 *
	 * @param tabLayoutId
	 * @param tabIds
	 */
	public void tabIds(int tabLayoutId, int... tabIds) {
		if (tabView != null) tabView.tabIds(tabLayoutId, tabIds);
	}

	// 设置背景
	public void setTabBackgroundColor(int color) {
		if (tabView != null && tabView.getTabRootView() != null) {
			tabView.getTabRootView().setBackgroundColor(color);
		}
	}

	// 设置背景
	public void setTabBackgroundRes(@ColorRes int res) {
		if (tabView != null && tabView.getTabRootView() != null) {
			tabView.getTabRootView().setBackgroundResource(res);
		}
	}

	// 设置高度
	public void setTabHeight(int height) {
		if (tabView != null && tabView.getTabRootView() != null) {
			tabView.getTabRootView().getLayoutParams().height = height;
		}
	}

	// 设置高度
	public void setTabHeight(int height, boolean isDp) {
		int tabHeight = isDp ? WISERApp.dip2px(height) : height;
		if (tabView != null && tabView.getTabRootView() != null) {
			tabView.getTabRootView().getLayoutParams().height = tabHeight;
		}
	}

	public void setOnTabClickListener(WISERTabView.OnTabClickListener onTabClickListener) {
		if (tabView != null) tabView.setOnTabClickListener(onTabClickListener);
	}

	public boolean isResumePage() {
		return isResumePage;
	}

	public void isResumePage(boolean isResumePage) {
		isResumePage = isResumePage;
	}

	@Override public void onPageScrolled(int i, float v, int i1) {
		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageScrolled(i, v, i1);
	}

	@Override public void onPageSelected(int i) {
		if (isFragment) {
			if (wiserTabPageFragment != null) wiserTabPageFragment.CURRENT_INDEX = i;
		} else {
			if (wiserTabPageActivity != null) wiserTabPageActivity.CURRENT_INDEX = i;
		}

		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageSelected(i);

		if (index == i) {
			checkMethod("onShowCurrentPage", i);
		} else {
			checkMethod("onHideCurrentPage", index);
			checkMethod("onShowCurrentPage", i);
		}
		index = i;
		isResumePage = true;
	}

	@Override public void onPageScrollStateChanged(int i) {
		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageScrollStateChanged(i);
	}

	public void setOnTabPageChangeListener(OnTabPageChangeListener onTabPageChangeListener) {
		this.onTabPageChangeListener = onTabPageChangeListener;
	}

	public interface OnTabPageChangeListener {

		void onPageScrolled(int i, float v, int i1);

		void onPageSelected(int i);

		void onPageScrollStateChanged(int i);
	}

	public interface OnTabShowCurrentPageListener {

		void onShowCurrentPage(int position);

		void onHideCurrentPage(int position);
	}

	public void detach() {
		if (pageView != null) pageView.detach();
		pageView = null;
		if (tabView != null) tabView.detach();
		tabView = null;
		wiserTabPageActivity = null;
		wiserTabPageFragment = null;
		onTabPageChangeListener = null;
	}

	/**
	 * 检查Tab Fragment 中是否存在显示该Fragment页面 方法
	 * 
	 * @param methodName
	 * @param position
	 */
	public void checkMethod(String methodName, int position) {
		try {
			if (fragments != null && fragments.length > position) {
				Class<?> fragmentClass = fragments[position].getClass();
				Method[] methods = fragmentClass.getMethods();
				for (Method method : methods) {
					if (method.getName().equals(methodName)) {
						Object userInfo = fragmentClass.newInstance();
						method.invoke(userInfo, position);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
