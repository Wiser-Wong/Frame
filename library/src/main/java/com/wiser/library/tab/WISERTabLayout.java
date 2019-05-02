package com.wiser.library.tab;

import java.util.ArrayList;
import java.util.List;

import com.wiser.library.tab.listener.OnTabClickListener;
import com.wiser.library.tab.listener.OnTabPageChangeListener;
import com.wiser.library.tab.listener.OnTabSwitchPageListener;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * 
 *         tab
 */
public class WISERTabLayout extends FrameLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {

	private WISERTabPage			tabPageView;									// 页 布局

	private View					tabLayoutView;									// Tab 布局视图

	private int[]					tabIds					= new int[0];			// Tab ID列表

	private List<View>				tabViews				= new ArrayList<>();	// Tab 视图列表

	private boolean					isTabCutPageAnim		= false;				// 是否点击Tab Page可以滑动

	private OnTabClickListener		onTabClickListener;								// Tab 点击监听

	private OnTabPageChangeListener	onTabPageChangeListener;						// page 切换监听

	private OnTabSwitchPageListener	onTabSwitchPageListener;						// page 滑动 tab 跟随

	public int						CURRENT_INDEX			= 0;

	public int						BEHIND_PAGE_INDEX		= 0;

	private int						index;

	private boolean					isDefaultOnPageSelected	= true;					// 是否第一次执行 onPageSelected

	public WISERTabLayout(@NonNull Context context) {
		super(context);
	}

	public WISERTabLayout(@NonNull Context context, @NonNull AttributeSet attrs) {
		super(context, attrs);
	}

	public View tabLayoutView() {
		return tabLayoutView;
	}

	public WISERTabPage tabPageView() {
		return tabPageView;
	}

	public WISERTabLayout isDefaultOnPageSelected(boolean isDefaultOnPageSelected) {
		this.isDefaultOnPageSelected = isDefaultOnPageSelected;
		return this;
	}

	/**
	 * 设置是否page可以滑动
	 *
	 * @param isScroll
	 */
	public WISERTabLayout isPageCanScroll(boolean isScroll) {
		if (tabPageView != null) tabPageView.isScroll(isScroll);
		return this;
	}

	/**
	 * tabId
	 *
	 * @param tabIds
	 */
	public WISERTabLayout tabIds(@LayoutRes int tabLayoutId, @IdRes int... tabIds) {
		this.tabLayoutView = LayoutInflater.from(getContext()).inflate(tabLayoutId, this, false);
		if (tabLayoutView == null) return this;
		addView(tabLayoutView);
		this.tabIds = tabIds;
		if (tabIds != null && tabIds.length > 0) {
			for (int tabId : tabIds) {
				View view = ButterKnife.findById(tabLayoutView, tabId);
				tabViews.add(view);
			}
		}
		setListener();
		return this;
	}

	/**
	 * 设置页码
	 *
	 * @param tabPageId
	 * @param fragments
	 */
	public WISERTabLayout setPages(@IdRes int tabPageId, Fragment... fragments) {
		tabPageView = ((FragmentActivity) getContext()).findViewById(tabPageId);
		if (tabPageView != null) {
			tabPageView.addOnPageChangeListener(this);
			tabPageView.setPageAdapter(fragments);
			if (isDefaultOnPageSelected) onPageSelected(CURRENT_INDEX);
		}
		return this;
	}

	private void setListener() {
		if (tabViews != null && tabViews.size() > 0 && tabIds != null && tabIds.length == tabViews.size()) {
			for (int i = 0; i < tabViews.size(); i++) {
				tabViews.get(i).setOnClickListener(this);
			}
		}
	}

	@Override public void onClick(View view) {
		if (tabIds == null || tabIds.length == 0) return;
		if (tabPageView == null) return;
		for (int i = 0; i < tabIds.length; i++) {
			if (view.getId() == tabIds[i]) {
				if (tabPageView.getChildCount() > i) {
					tabPageView.setCurrentItem(i, isTabCutPageAnim);
				}
				if (onTabClickListener != null) onTabClickListener.onTabClick(view);
				break;
			}
		}
	}

	/**
	 * 设置当前页
	 *
	 * @param i
	 *            位置
	 */
	public void setCurrentItem(int i) {
		if (tabPageView != null) tabPageView.setCurrentItem(i, isTabCutPageAnim);
	}

	/**
	 * 设置当前页
	 *
	 * @param i
	 *            位置
	 * @param isAnim
	 *            是否切换需要动画
	 */
	public void setCurrentItem(int i, boolean isAnim) {
		if (tabPageView != null) tabPageView.setCurrentItem(i, isAnim);

	}

	/**
	 * 是否切换需要动画
	 *
	 * @param isTabCutPageAnim
	 */
	public WISERTabLayout isTabCutPageAnim(boolean isTabCutPageAnim) {
		this.isTabCutPageAnim = isTabCutPageAnim;
		return this;
	}

	public WISERTabLayout setOnTabClickListener(OnTabClickListener onTabClickListener) {
		this.onTabClickListener = onTabClickListener;
		return this;
	}

	public WISERTabLayout setOnTabPageChangeListener(OnTabPageChangeListener onTabPageChangeListener) {
		this.onTabPageChangeListener = onTabPageChangeListener;
		return this;
	}

	public WISERTabLayout setOnTabSwitchPageListener(OnTabSwitchPageListener onTabSwitchPageListener) {
		this.onTabSwitchPageListener = onTabSwitchPageListener;
		return this;
	}

	@Override public void onPageScrolled(int i, float v, int i1) {
		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageScrolled(i, v, i1);
	}

	@Override public void onPageSelected(int i) {

		BEHIND_PAGE_INDEX = i;

		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageSelected(i);

		if (onTabSwitchPageListener != null && tabViews != null && tabViews.size() > i) onTabSwitchPageListener.onTabSwitch(tabViews.get(i), i);

		CURRENT_INDEX = i;

		if (index == i) {
			tabPageView.checkMethod("onShowCurrentPage", i);
		} else {
			tabPageView.checkMethod("onHideCurrentPage", index);
			tabPageView.checkMethod("onShowCurrentPage", i);
		}
		index = i;
	}

	@Override public void onPageScrollStateChanged(int i) {
		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageScrollStateChanged(i);
	}

	public void onDetach() {
		if (tabPageView != null) tabPageView.detach();
		tabPageView = null;
		tabLayoutView = null;
		tabIds = null;
		if (tabViews != null) tabViews.clear();
		tabViews = null;
		onTabClickListener = null;
		onTabPageChangeListener = null;
		isTabCutPageAnim = false;
		isDefaultOnPageSelected = false;
		CURRENT_INDEX = 0;
		BEHIND_PAGE_INDEX = 0;
	}

}
