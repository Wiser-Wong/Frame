package com.wiser.library.view;

import com.wiser.library.util.WISERApp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Wiser
 * 
 *         上拉加载更多布局
 */
public class FooterView extends LinearLayout {

	private ProgressBar	bar;

	private TextView	text;

	public FooterView(Context context) {
		super(context);
		init();
	}

	public FooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		setLayoutParams(params);
		setGravity(Gravity.CENTER);

		createLoading();
		createText();

		setBackgroundColor(Color.LTGRAY);
	}

	// 创建loading
	private void createLoading() {
		bar = new ProgressBar(getContext());
		LayoutParams params = new LayoutParams(WISERApp.dip2px(40), WISERApp.dip2px(40));
		bar.setLayoutParams(params);
		bar.setPadding(WISERApp.dip2px(10), WISERApp.dip2px(20), WISERApp.dip2px(10), WISERApp.dip2px(20));
		ColorStateList colorStateList = ColorStateList.valueOf(Color.GRAY);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			bar.setIndeterminateTintList(colorStateList);
			bar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
		}
		addView(bar);
	}

	// 创建文本
	private void createText() {
		text = new TextView(getContext());
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		text.setLayoutParams(params);
		text.setPadding(WISERApp.dip2px(10), WISERApp.dip2px(20), WISERApp.dip2px(10), WISERApp.dip2px(20));
		text.setTextSize(WISERApp.sp2px(6));
		text.setTextColor(Color.GRAY);
		addView(text);
	}

	public void setBarColor(int color) {
		ColorStateList colorStateList = ColorStateList.valueOf(color);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if (bar == null) return;
			bar.setIndeterminateTintList(colorStateList);
			bar.setIndeterminateTintMode(PorterDuff.Mode.SRC_ATOP);
		}
	}

	public void setPadding(int leftPadding, int topPadding, int rightPadding, int bottomPadding) {
		if (text == null) return;
		text.setPadding(WISERApp.dip2px(leftPadding), WISERApp.dip2px(topPadding), WISERApp.dip2px(rightPadding), WISERApp.dip2px(bottomPadding));
		if (bar == null) return;
		bar.setPadding(WISERApp.dip2px(leftPadding), WISERApp.dip2px(topPadding), WISERApp.dip2px(rightPadding), WISERApp.dip2px(bottomPadding));
	}

	public ProgressBar bar() {
		return bar;
	}

	public TextView text() {
		return text;
	}
}
