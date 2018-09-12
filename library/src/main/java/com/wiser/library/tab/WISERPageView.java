package com.wiser.library.tab;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author Wiser
 * 
 *         page view
 */
public class WISERPageView extends ViewPager {

	private WISERTabPageAdapter	pageAdapter;

	private boolean				isScroll	= false;

	private WISERActivity		activity;

	public void isScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	public WISERPageView(@NonNull Context context) {
		super(context);
		if (context instanceof WISERActivity) this.activity = (WISERActivity) context;
		init();
	}

	public WISERPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		setLayoutParams(params);
		int PAGE_VIEW_ID = 0X222222;
		if (activity != null) {
			if (activity.findViewById(PAGE_VIEW_ID) != null) {
				int VIEW_ID = 0X222223;
				setId(VIEW_ID);
			} else {
				setId(PAGE_VIEW_ID);
			}
		} else {
			setId(PAGE_VIEW_ID);
		}
	}

	/**
	 * 设置适配器
	 *
	 * @param fragments
	 */
	public void setPageAdapter(Fragment... fragments) {
		if (pageAdapter == null) pageAdapter = new WISERTabPageAdapter(WISERHelper.getActivityManage().getCurrentActivity().getSupportFragmentManager(), fragments);
		setOffscreenPageLimit(fragments == null || fragments.length == 0 ? 0 : fragments.length);
		setAdapter(pageAdapter);
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isScroll && super.onInterceptTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility") @Override public boolean onTouchEvent(MotionEvent ev) {
		return isScroll && super.onTouchEvent(ev);
	}

	protected void detach() {
		if (pageAdapter != null) pageAdapter.detach();
		pageAdapter = null;
		isScroll = false;
	}
}
