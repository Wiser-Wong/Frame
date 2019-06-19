package com.wiser.library.tab;

import java.lang.reflect.Method;

import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Wiser
 * 
 *         page view
 */
public class WISERTabPageView extends ViewPager {

	private WISERTabPageAdapter	pageAdapter;

	private boolean				isScroll	= false;

	private Fragment[]			fragments	= new Fragment[0];

	public void isScroll(boolean isScroll) {
		this.isScroll = isScroll;
	}

	public WISERTabPageView(@NonNull Context context) {
		super(context);
	}

	public WISERTabPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置适配器
	 *
	 * @param fragments
	 */
	public void setPageAdapter(Fragment... fragments) {
		this.fragments = fragments;
		if (pageAdapter == null) {
			pageAdapter = new WISERTabPageAdapter(WISERHelper.getActivityManage().getCurrentActivity().getSupportFragmentManager(), fragments);
			setOffscreenPageLimit(fragments == null || fragments.length == 0 ? 0 : fragments.length);
			setAdapter(pageAdapter);
		} else {
			pageAdapter.notifyDataSetChanged();
		}
	}

	public void notifyDataSetChanged() {
		if (pageAdapter != null) pageAdapter.notifyDataSetChanged();
	}

	public WISERTabPageAdapter adapter() {
		return pageAdapter;
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isScroll && super.onInterceptTouchEvent(ev);
	}

	@SuppressLint("ClickableViewAccessibility") @Override public boolean onTouchEvent(MotionEvent ev) {
		return isScroll && super.onTouchEvent(ev);
	}

	public void detach() {
		if (pageAdapter != null) pageAdapter.detach();
		pageAdapter = null;
		fragments = null;
		isScroll = false;
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
