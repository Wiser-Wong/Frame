package com.wiser.library.view.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

/**
 * @author Wiser
 * 
 *         文字与数字或字母 排版控件
 */
public class TextComposingView extends AppCompatTextView {

	// 内容填充画笔
	private Paint					contentPaint;

	// 内容
	private String					mText;

	// 文字测量工具
	private Paint.FontMetricsInt	textFm;

	// 文字显示区的宽度
	private int						textWidth;

	// 单行文字的高度
	private int						singleLineHeight;

	public TextComposingView(Context context) {
		this(context, null);
		init();
	}

	public TextComposingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		init();
	}

	public TextComposingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		contentPaint = getPaint();
		contentPaint.setTextSize(getTextSize());
		contentPaint.setAntiAlias(true);
		contentPaint.setColor(getCurrentTextColor());
		textFm = contentPaint.getFontMetricsInt();
		singleLineHeight = Math.abs(textFm.top - textFm.bottom);
	}

	public void setText(String text) {
		this.mText = text;
		invalidate();
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		textWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
		int textHeight = (int) getViewHeight();
		int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
		int height = heightMode == View.MeasureSpec.UNSPECIFIED ? textHeight : heightMode == View.MeasureSpec.EXACTLY ? heightSize : Math.min(textHeight, heightSize);
		int width = widthMode == View.MeasureSpec.UNSPECIFIED ? textWidth : widthSize;
		setMeasuredDimension(width, height);
	}

	// 获取View高度
	private float getViewHeight() {
		char[] textChars = mText.toCharArray();
		float ww = 0.0f;
		int count = 0;
		StringBuilder sb = new StringBuilder();
		for (char textChar : textChars) {
			float v = contentPaint.measureText(textChar + "");
			if (ww + v <= textWidth) {
				sb.append(textChar);
				ww += v;
			} else {
				count++;
				sb = new StringBuilder();
				ww = 0.0f;
				ww += v;
				sb.append(textChar);
			}
		}
		if (sb.toString().length() != 0) {
			count++;
		}
		return count * singleLineHeight + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? getLineSpacingExtra() : 1) * (count - 1) + getPaddingBottom() + getPaddingTop();
	}

	@Override protected void onDraw(Canvas canvas) {
		drawText(canvas);
	}

	/**
	 * 循环遍历画文字
	 *
	 * @param canvas
	 */
	private void drawText(Canvas canvas) {

		char[] textChars = mText.toCharArray();
		float ww = 0.0f;
		float startL = 0.0f;
		float startT = 0.0f;
		startL += getPaddingLeft();
		if (getMeasuredHeight() > getViewHeight() && getGravity() == Gravity.CENTER) startT += getPaddingTop() + getMeasuredHeight() / 2;
		else startT += getPaddingTop() + singleLineHeight / 2 + (textFm.bottom - textFm.top) / 2 - textFm.bottom;
		StringBuilder sb = new StringBuilder();

		for (char textChar : textChars) {
			float v = contentPaint.measureText(textChar + "");
			if (ww + v <= textWidth) {
				sb.append(textChar);
				ww += v;
			} else {
				canvas.drawText(sb.toString(), startL, startT, contentPaint);
				startT += singleLineHeight + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ? getLineSpacingExtra() : 0);
				sb = new StringBuilder();
				ww = 0.0f;
				ww += v;
				sb.append(textChar);
			}
		}

		if (sb.toString().length() > 0) {
			canvas.drawText(sb.toString(), startL, startT, contentPaint);
		}

	}
}
