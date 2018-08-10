package com.wiser.library.base;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERBuilder {

	private int				layoutId;		// 布局ID

	private FrameLayout		contentRoot;	// 根布局

	private LayoutInflater	mInflater;

	private WISERActivity	wiserActivity;

	/**
	 * 构造器
	 *
	 * @param wiserActivity
	 *            参数
	 * @param inflater
	 *            参数
	 */
	public WISERBuilder(@NonNull WISERActivity wiserActivity, @NonNull LayoutInflater inflater) {
		this.wiserActivity = wiserActivity;
		this.mInflater = inflater;
	}

	public void layoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	private int getLayoutId() {
		return layoutId;
	}

	/**
	 * 创建视图
	 *
	 * @return
	 */
	View createView() {
		contentRoot = new FrameLayout(wiserActivity.getContext());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

		// 内容
		if (getLayoutId() > 0) {
			contentRoot.addView(mInflater.inflate(getLayoutId(), null, false), layoutParams);
		}
		return contentRoot;
	}

}
