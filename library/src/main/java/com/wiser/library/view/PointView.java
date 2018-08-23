package com.wiser.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Wiser
 * @version 版本
 */
public class PointView extends View {

	private Paint	mPaint;

	private int		color	= Color.GRAY;

	private int		radius	= 7;

	public PointView(Context context) {
		super(context);
		init();
	}

	public PointView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		initPaint();
	}

	public void setColor(int color) {
		this.color = color;
		mPaint.setColor(color);
		postInvalidate();
	}

	private void initPaint() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(color);
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);// 设置为圆角
		// 帮助消除锯齿
		mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(radius, radius, radius, mPaint);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int minimumWidth = getSuggestedMinimumWidth();
		final int minimumHeight = getSuggestedMinimumHeight();
		int width = measureWidth(minimumWidth, widthMeasureSpec);
		int height = measureHeight(minimumHeight, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int defaultWidth, int measureSpec) {

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
			case MeasureSpec.AT_MOST:
				defaultWidth = 2 * radius + getPaddingLeft() + getPaddingRight();
				break;
			case MeasureSpec.EXACTLY:
				defaultWidth = specSize;
				break;
			case MeasureSpec.UNSPECIFIED:
				defaultWidth = Math.max(defaultWidth, specSize);
		}
		return defaultWidth;
	}

	private int measureHeight(int defaultHeight, int measureSpec) {

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		switch (specMode) {
			case MeasureSpec.AT_MOST:
				defaultHeight = 2 * radius + +getPaddingTop() + getPaddingBottom();
				break;
			case MeasureSpec.EXACTLY:
				defaultHeight = specSize;
				break;
			case MeasureSpec.UNSPECIFIED:
				defaultHeight = Math.max(defaultHeight, specSize);
				// 1.基准点是baseline
				// 2.ascent：是baseline之上至字符最高处的距离
				// 3.descent：是baseline之下至字符最低处的距离
				// 4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
				// 5.top：是指的是最高字符到baseline的值,即ascent的最大值
				// 6.bottom：是指最低字符到baseline的值,即descent的最大值
				break;
		}
		return defaultHeight;

	}
}
