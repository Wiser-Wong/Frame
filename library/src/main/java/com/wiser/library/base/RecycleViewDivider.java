package com.wiser.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.manager.WISERLogManage;

import java.util.Objects;

/**
 * @author Wiser
 * @version 版本
 */
public class RecycleViewDivider extends DividerItemDecoration {

	private Paint		mPaint;

	private Drawable	mDivider;

	private int			mDividerHeight	= 2;									// 分割线高度，默认为1px

	private int			mOrientation;											// 列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL

	private int			type			= WISERRecycleView.DEFAULT_DECORATION;

	/**
	 * 默认分割线：高度为2px，颜色为灰色
	 *
	 * @param context
	 * @param orientation
	 *            列表方向
	 */
	RecycleViewDivider(Context context, int orientation) {
		super(context, orientation);
		this.mOrientation = orientation;
	}

	/**
	 * 自定义分割线
	 *
	 * @param context
	 * @param orientation
	 *            列表方向
	 * @param drawableId
	 *            分割线图片
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT) RecycleViewDivider(Context context, int orientation, int drawableId, int type) {
		this(context, orientation);
		this.type = type;
		try {
			mDivider = ContextCompat.getDrawable(context, drawableId);
			assert mDivider != null;
			mDividerHeight = mDivider.getIntrinsicHeight();
			setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(context, drawableId)));
		} catch (Exception e) {
			WISERHelper.log().e("未找到分割线图片资源id");
		}
	}

	/**
	 * 自定义分割线
	 *
	 * @param context
	 * @param orientation
	 *            列表方向
	 * @param dividerHeight
	 *            分割线高度
	 * @param dividerColor
	 *            分割线颜色
	 */
	RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor, int type) {
		this(context, orientation);
		this.type = type;
		mDividerHeight = dividerHeight;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(dividerColor);
		mPaint.setStyle(Paint.Style.FILL);
	}

	// 获取分割线尺寸
	@Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);
		outRect.set(0, 0, 0, mDividerHeight);
	}

	// 绘制分割线
	@Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		super.onDraw(c, parent, state);
		switch (type) {
			case WISERRecycleView.DEFAULT_DECORATION:
			case WISERRecycleView.CUSTOM_DECORATION:
				if (mOrientation == LinearLayoutManager.VERTICAL) {
					drawHorizontal(c, parent);
				} else {
					drawVertical(c, parent);
				}
				break;
		}
	}

	// 绘制横向 item 分割线
	private void drawHorizontal(Canvas canvas, RecyclerView parent) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
		final int childSize = parent.getChildCount();
		for (int i = 0; i < childSize; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int top = child.getBottom() + layoutParams.bottomMargin;
			final int bottom = top + mDividerHeight;
			if (mDivider != null) {
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(canvas);
			}
			if (mPaint != null) {
				canvas.drawRect(left, top, right, bottom, mPaint);
			}
		}
	}

	// 绘制纵向 item 分割线
	private void drawVertical(Canvas canvas, RecyclerView parent) {
		final int top = parent.getPaddingTop();
		final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
		final int childSize = parent.getChildCount();
		for (int i = 0; i < childSize; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int left = child.getRight() + layoutParams.rightMargin;
			final int right = left + mDividerHeight;
			if (mDivider != null) {
				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(canvas);
			}
			if (mPaint != null) {
				canvas.drawRect(left, top, right, bottom, mPaint);
			}
		}
	}

}