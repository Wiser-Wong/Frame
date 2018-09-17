package com.wiser.library.tab.bottom;

import java.util.ArrayList;
import java.util.List;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wiser.library.base.WISERTabPageActivity;
import com.wiser.library.base.WISERTabPageFragment;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * 
 *         tab view
 */
public class WISERTabView implements View.OnClickListener {

	private List<View>				tabViews				= new ArrayList<>();	// Tab 视图列表

	private WISERPageView			pageView;										// 页 布局

	private LayoutInflater			mInflater;

	private int						tabLayoutId;									// Tab 布局 ID

	private View					tabLayoutView;									// Tab 布局视图

	private FrameLayout				tabRootView;									// 根布局

	private int[]					tabIds					= new int[0];			// Tab ID列表

	private OnTabClickListener		onTabClickListener;								// Tab 点击监听

	private boolean					isTabCanRepeatedlyClick	= false;				// 是否Tab 能多次点击

	private boolean					isTabCutPageAnim		= false;				// 是否点击Tab Page可以滑动

	private WISERTabPageActivity	wiserTabPageActivity;

	private WISERTabPageFragment	wiserTabPageFragment;

	private boolean					isFragment;

	WISERTabView(WISERTabPageActivity wiserTabPageActivity, LayoutInflater mInflater) {
		isFragment = false;
		this.wiserTabPageActivity = wiserTabPageActivity;
		this.mInflater = mInflater;
		tabRootView = new FrameLayout(wiserTabPageActivity);
		tabRootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		int TAB_VIEW_ID = 0X12030123;
		if (wiserTabPageActivity.findViewById(TAB_VIEW_ID) != null) {
            int VIEW_ID = 0X12030124;
            tabRootView.setId(VIEW_ID);
        } else {
            tabRootView.setId(TAB_VIEW_ID);
        }
	}

	WISERTabView(WISERTabPageFragment wiserTabPageFragment, LayoutInflater mInflater) {
		isFragment = true;
		this.wiserTabPageFragment = wiserTabPageFragment;
		this.mInflater = mInflater;
		tabRootView = new FrameLayout(wiserTabPageFragment.activity());
		tabRootView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		int TAB_VIEW_ID = 0X12030123;
		if (wiserTabPageFragment.activity() != null) {
			if (wiserTabPageFragment.activity().findViewById(TAB_VIEW_ID) != null) {
				int VIEW_ID = 0X12030124;
				tabRootView.setId(VIEW_ID);
			} else {
				tabRootView.setId(TAB_VIEW_ID);
			}
		} else {
			tabRootView.setId(TAB_VIEW_ID);
		}
	}

	public FrameLayout getTabRootView() {
		return tabRootView;
	}

	public View getTabLayoutView() {
		return tabLayoutView;
	}

	/**
	 * 是否切换需要动画
	 *
	 * @param isTabCutPageAnim
	 */
	public void isTabCutPageAnim(boolean isTabCutPageAnim) {
		this.isTabCutPageAnim = isTabCutPageAnim;
	}

	/**
	 * 是否切换需要动画
	 * 
	 * @return
	 */
	public boolean isTabCutPageAnim() {
		return isTabCutPageAnim;
	}

	/**
	 *
	 */

	/**
	 * 是否可以多次点击Tab
	 *
	 * @param isTabCanRepeatedlyClick
	 */
	public void isTabCanRepeatedlyClick(boolean isTabCanRepeatedlyClick) {
		this.isTabCanRepeatedlyClick = isTabCanRepeatedlyClick;
	}

	public void setPageView(WISERPageView pageView) {
		this.pageView = pageView;
	}

	/**
	 * tabId
	 *
	 * @param tabLayoutId
	 * @param tabIds
	 */
	public void tabIds(int tabLayoutId, int... tabIds) {
		this.tabLayoutId = tabLayoutId;
		this.tabIds = tabIds;
		createTabLayout();
	}

	private void setListener() {
		if (tabViews != null && tabViews.size() > 0 && tabIds != null && tabIds.length == tabViews.size()) {
			for (int i = 0; i < tabViews.size(); i++) {
				tabViews.get(i).setOnClickListener(this);
			}
		}
	}

	/**
	 * 创建TabLayout
	 */
	void createTabLayout() {
		if (tabLayoutId > 0) {
			tabLayoutView = mInflater.inflate(tabLayoutId, null, false);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			tabLayoutView.setLayoutParams(params);
			if (tabIds != null && tabIds.length > 0) {
				for (int tabId : tabIds) {
					View view = ButterKnife.findById(tabLayoutView, tabId);
					tabViews.add(view);
				}
			}
			setListener();
			tabRootView.addView(tabLayoutView);
		}
	}

	@Override public void onClick(View view) {
		if (tabIds == null || tabIds.length == 0) return;
		for (int i = 0; i < tabIds.length; i++) {
			if (view.getId() == tabIds[i]) {
				if (pageView.getChildCount() > i) {
					if (isFragment) {
						if (wiserTabPageFragment.CURRENT_INDEX != i) {
							pageView.setCurrentItem(i, isTabCutPageAnim);
						}
					} else {
						if (wiserTabPageActivity.CURRENT_INDEX != i) {
							pageView.setCurrentItem(i, isTabCutPageAnim);
						}
					}
				}
				if (isTabCanRepeatedlyClick) if (onTabClickListener != null) onTabClickListener.onTabClick(view);
				break;
			}
		}
	}

	public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
		this.onTabClickListener = onTabClickListener;
	}

	public interface OnTabClickListener {

		void onTabClick(View view);
	}

	protected void detach() {
		tabViews = null;
		if (pageView != null) pageView.detach();
		pageView = null;
		mInflater = null;
		tabLayoutId = 0;
		tabLayoutView = null;
		tabRootView = null;
		tabIds = null;
		onTabClickListener = null;
		isTabCanRepeatedlyClick = false;
		isTabCutPageAnim = false;
		wiserTabPageActivity = null;
		wiserTabPageFragment = null;
	}
}
