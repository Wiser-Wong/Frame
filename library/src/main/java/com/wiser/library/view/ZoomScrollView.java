package com.wiser.library.view;

import java.lang.ref.WeakReference;

import com.wiser.library.R;
import com.wiser.library.helper.WISERHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Wiser
 * 
 *         滚动放大图片
 */
public class ZoomScrollView extends NestedScrollView {

	private View				zoomView;

	/** 记录上次事件的Y轴 */
	private float				mLastMotionY;

	/** 记录一个滚动了多少距离，通过这个来设置缩放 */
	private int					allScroll			= -1;

	/** 控件原本的宽度 */
	private int					width				= 0;

	/** 控件原本的高度 */
	private int					height				= 0;

	/** 被放大的控件id */
	private int					zoomId;

	/** 最大放大多少像素 */
	private int					maxZoom;

	/** 阻力大小 */
	private float				dragSize			= 1;

	/** 滚动监听 */
	private ScrollViewListener	scrollViewListener	= null;

	private final int			CHANGE				= 111;

	private ZoomHandler			handler;

	@SuppressLint("HandlerLeak")
	private class ZoomHandler extends Handler {

		WeakReference<ZoomScrollView> reference;

		ZoomHandler(ZoomScrollView scrollView) {
			reference = new WeakReference<>(scrollView);
		}

		@Override public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (reference != null && reference.get() != null) {
				switch (msg.what) {
					case CHANGE:
						allScroll -= 45;
						if (allScroll < 0) {
							allScroll = 0;
						}
						LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) zoomView.getLayoutParams();
						lp.height = height + allScroll / 2;
						lp.width = width + allScroll / 2;
						lp.gravity = Gravity.CENTER;
						zoomView.setLayoutParams(lp);
						if (allScroll != 0) {
							handler.sendEmptyMessage(CHANGE);
						} else {
							allScroll = -1;
						}
						break;
				}
			}
		}
	}

	public ZoomScrollView(Context context) {
		super(context);
	}

	public ZoomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs);
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		zoomView = findViewById(zoomId);
	}

	private void init(AttributeSet attrs) {
		handler = new ZoomHandler(this);
		TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.ZoomScrollView);
		zoomId = t.getResourceId(R.styleable.ZoomScrollView_zoomId, -1);
		maxZoom = t.getDimensionPixelOffset(R.styleable.ZoomScrollView_maxZoom, 0);
		dragSize = t.getFloat(R.styleable.ZoomScrollView_dragSize, 1);
		t.recycle();
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override public boolean dispatchTouchEvent(MotionEvent event) {
		if (zoomView == null || maxZoom == 0) {
			return super.dispatchTouchEvent(event);
		}

		final int action = event.getAction();

		if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
			if (allScroll != -1) {
				handler.sendEmptyMessageDelayed(CHANGE, 10);
			}
			return super.dispatchTouchEvent(event);
		}

		switch (action) {
			case MotionEvent.ACTION_MOVE: {
				final float y = event.getY() * dragSize;
				final float diff, absDiff;
				diff = y - mLastMotionY;
				mLastMotionY = y;
				absDiff = Math.abs(diff);
				if (allScroll >= 0 && absDiff > 1) {
					allScroll += diff;

					if (allScroll < 0) {
						allScroll = 0;
					} else if (allScroll > maxZoom) {
						allScroll = maxZoom;
					}
					WISERHelper.log().i("allScroll", "allScroll:" + allScroll);
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) zoomView.getLayoutParams();
					lp.height = height + allScroll / 2;
					lp.width = width + allScroll / 2;
					lp.gravity = Gravity.CENTER;
					zoomView.setLayoutParams(lp);
					if (allScroll == 0) {
						allScroll = -1;
					}
					return false;
				}
				if (isReadyForPullStart()) {
					if (absDiff > 0) {
						if (diff >= 1f && isReadyForPullStart()) {
							mLastMotionY = y;
							allScroll = 0;
							height = zoomView.getHeight();
							width = zoomView.getWidth();
							return true;
						}
					}
				}
				break;
			}

		}

		return super.dispatchTouchEvent(event);
	}

	@SuppressLint("ClickableViewAccessibility") @Override public boolean onTouchEvent(MotionEvent ev) {
		if (allScroll != -1) {
			Log.i("ScrollView", "onTouchEvent");
			return false;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 返回是否可以开始放大
	 * 
	 * @return
	 */
	protected boolean isReadyForPullStart() {
		return getScrollY() == 0;
	}

	@Override protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	public interface ScrollViewListener {

		void onScrollChanged(ZoomScrollView scrollView, int x, int y, int oldx, int oldy);

	}

	@Override protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (handler != null) {
			handler.removeMessages(CHANGE);
			handler = null;
		}
	}
}