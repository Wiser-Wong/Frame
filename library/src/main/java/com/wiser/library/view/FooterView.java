package com.wiser.library.view;

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

import com.wiser.library.util.WISERApp;

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
	}

	// 创建loading
	private void createLoading() {
		bar = new ProgressBar(getContext());
		LayoutParams params = new LayoutParams(WISERApp.dip2px(40), WISERApp.dip2px(40));
		bar.setLayoutParams(params);
		bar.setPadding(10, 20, 10, 20);
		int color = Color.GRAY;
		ColorStateList colorStateList = ColorStateList.valueOf(color);
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
		text.setPadding(10, 20, 10, 20);
		text.setTextSize(16);
		text.setTextColor(Color.GRAY);
		text.setText("正在加载...");
		addView(text);
	}

	public ProgressBar bar() {
		return bar;
	}

	public TextView text() {
		return text;
	}
}
