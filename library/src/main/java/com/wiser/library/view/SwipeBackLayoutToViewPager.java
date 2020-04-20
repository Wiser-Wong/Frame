package com.wiser.library.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Wiser
 *
 * 滑动返回与ViewPager冲突解决
 */
public class SwipeBackLayoutToViewPager extends ViewPager {

    private float mLastX;
    private float mLastY;

    public SwipeBackLayoutToViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeBackLayoutToViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentItem() == 0) {
            float x = ev.getX();
            float y = ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float xDiff = Math.abs(x - mLastY);
                    float yDiff = Math.abs(y - mLastY);
                    //在第一页，判断到是向左边滑动，即想滑动第二页
                    if (xDiff > 0 && x - mLastX < 0 && xDiff * 0.5f > yDiff) {
                        //告诉父容器不要拦截事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else {//否则在第一页，想画出屏幕返回上一页面
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            mLastX = x;
            mLastY = y;
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
