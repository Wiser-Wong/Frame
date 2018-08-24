package com.wiser.library.pager.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Wiser
 * @version 版本
 */
public class BannerInterceptScrollView extends ViewPager {

    int lastX = -1;
    int lastY = -1;

    private boolean isInterceptTouch = false;

    public BannerInterceptScrollView(Context context) {
        super(context);
    }

    public BannerInterceptScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        int dealtX = 0;
        int dealtY = 0;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isInterceptTouch)
                    // 保证子View能够接收到Action_move事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isInterceptTouch) {
                    dealtX += Math.abs(x - lastX);
                    dealtY += Math.abs(y - lastY);
                    // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                    if (dealtX >= dealtY) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    lastX = x;
                    lastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    public void setInterceptTouch(boolean isInterceptTouch) {
        this.isInterceptTouch = isInterceptTouch;
    }
}
