package com.wiser.library.tab.bottom;

import java.lang.reflect.Method;

import com.wiser.library.base.WISERTabPageActivity;
import com.wiser.library.base.WISERTabPageFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author Wiser
 * 
 *         Tab Page View
 */
public class WISERTabPageView extends RelativeLayout implements ViewPager.OnPageChangeListener {

	private WISERPageView			pageView;						// 页 布局

	private WISERTabView			tabView;						// tab 布局

	private View					otherView;						// 页 布局 平级View

	private RelativeLayout			pageLayout;						// 页 外层布局

	private WISERTabPageActivity	wiserTabPageActivity;

	private WISERTabPageFragment	wiserTabPageFragment;

	private OnTabPageChangeListener	onTabPageChangeListener;

	private boolean					isFragment;

	private Fragment[]				fragments	= new Fragment[0];

	private int						index;

	private boolean					isResumePage;

	public static final int			LEFT		= 1;				// tab 处于 内容左侧

	public static final int			TOP			= 2;				// tab 处于 内容上侧

	public static final int			RIGHT		= 3;				// tab 处于 内容右侧

	public static final int			BOTTOM		= 4;				// tab 处于 内容下侧

	private int						direction	= BOTTOM;			// 默认处于底部

	private int						otherViewDirection;				// page 平级 View 放置位置

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

		pageLayout = new RelativeLayout(getContext());
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

		pageLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		addView(pageLayout);

		if (tabView != null && tabView.getTabLayoutView() != null) {
			addView(tabView.getTabLayoutView());
		}

		if (pageView != null) pageLayout.addView(pageView);

		if (otherView != null) pageLayout.addView(otherView);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		setLayoutParams(params);

		LayoutParams pageLayoutParams = (LayoutParams) pageLayout.getLayoutParams();
		pageLayoutParams.alignWithParent = true;

		switch (direction) {
			case LEFT:
				pageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				if (tabView != null && tabView.getTabLayoutView() != null) {
					pageLayoutParams.addRule(RelativeLayout.RIGHT_OF, tabView.getTabLayoutView().getId());
					LayoutParams tabParams = (LayoutParams) tabView.getTabLayoutView().getLayoutParams();
					tabParams.alignWithParent = true;
					tabParams.width = LayoutParams.WRAP_CONTENT;
					tabParams.height = LayoutParams.MATCH_PARENT;
					tabParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				}
				break;
			case TOP:
				pageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				if (tabView != null && tabView.getTabLayoutView() != null) {
					pageLayoutParams.addRule(RelativeLayout.BELOW, tabView.getTabLayoutView().getId());
					LayoutParams tabParams = (LayoutParams) tabView.getTabLayoutView().getLayoutParams();
					tabParams.alignWithParent = true;
					tabParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				}
				break;
			case RIGHT:
				pageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				if (tabView != null && tabView.getTabLayoutView() != null) {
					pageLayoutParams.addRule(RelativeLayout.LEFT_OF, tabView.getTabLayoutView().getId());
					LayoutParams tabParams = (LayoutParams) tabView.getTabLayoutView().getLayoutParams();
					tabParams.alignWithParent = true;
					tabParams.width = LayoutParams.WRAP_CONTENT;
					tabParams.height = LayoutParams.MATCH_PARENT;
					tabParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				}
				break;
			case BOTTOM:
				pageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				if (tabView != null && tabView.getTabLayoutView() != null) {
					pageLayoutParams.addRule(RelativeLayout.ABOVE, tabView.getTabLayoutView().getId());
					LayoutParams tabParams = (LayoutParams) tabView.getTabLayoutView().getLayoutParams();
					tabParams.alignWithParent = true;
					tabParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				}
				break;
		}

		LayoutParams pageViewParams = (LayoutParams) pageView.getLayoutParams();
		pageLayoutParams.alignWithParent = true;

		switch (otherViewDirection) {
			case LEFT:
				pageViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				if (otherView != null) {
					pageViewParams.addRule(RelativeLayout.RIGHT_OF, otherView.getId());
					LayoutParams otherParams = (LayoutParams) otherView.getLayoutParams();
					otherParams.alignWithParent = true;
					otherParams.width = LayoutParams.WRAP_CONTENT;
					otherParams.height = LayoutParams.MATCH_PARENT;
					otherParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				}
				break;
			case TOP:
				pageViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				if (otherView != null) {
					pageViewParams.addRule(RelativeLayout.BELOW, otherView.getId());
					LayoutParams otherParams = (LayoutParams) otherView.getLayoutParams();
					otherParams.alignWithParent = true;
					otherParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				}
				break;
			case RIGHT:
				pageViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				if (otherView != null) {
					pageViewParams.addRule(RelativeLayout.LEFT_OF, otherView.getId());
					LayoutParams otherParams = (LayoutParams) otherView.getLayoutParams();
					otherParams.alignWithParent = true;
					otherParams.width = LayoutParams.WRAP_CONTENT;
					otherParams.height = LayoutParams.MATCH_PARENT;
					otherParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				}
				break;
			case BOTTOM:
				pageViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				if (otherView != null) {
					pageViewParams.addRule(RelativeLayout.ABOVE, otherView.getId());
					LayoutParams otherParams = (LayoutParams) otherView.getLayoutParams();
					otherParams.alignWithParent = true;
					otherParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				}
				break;
		}
		return this;
	}

	public void setOtherViewId(int layoutId, int direction) {
		this.otherViewDirection = direction;
		if (layoutId > 0) {
			otherView = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
			if (otherView != null) {
				int otherViewId = otherView.getId();
				if (otherViewId < 0) {
					int OTHER_VIEW_ID = 0X12030125;
					otherView.setId(OTHER_VIEW_ID);
				}
			}
		}
	}

	/**
	 * 设置tab 方向
	 * 
	 * @param direction
	 */
	public void setTabDirection(int direction) {
		this.direction = direction;
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
			if (wiserTabPageFragment != null) wiserTabPageFragment.BEHIND_PAGE_INDEX = i;
		} else {
			if (wiserTabPageActivity != null) wiserTabPageActivity.BEHIND_PAGE_INDEX = i;
		}

		if (onTabPageChangeListener != null) onTabPageChangeListener.onPageSelected(i);

		if (isFragment) {
			if (wiserTabPageFragment != null) wiserTabPageFragment.CURRENT_INDEX = i;
		} else {
			if (wiserTabPageActivity != null) wiserTabPageActivity.CURRENT_INDEX = i;
		}

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
