package com.wiser.library.pager.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import com.wiser.library.base.WISERActivity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Wiser
 * @version 版本
 */
public class BannerView extends BannerInterceptScrollView {

    public static final int DEFAULT_INTERVAL = 3000;

    public static final int LEFT = 0;

    public static final int RIGHT = 1;

    /**
     * do nothing when sliding at the last or first item
     **/
    public static final int SLIDE_BORDER_MODE_NONE = 0;

    /**
     * cycle when sliding at the last or first item
     **/
    public static final int SLIDE_BORDER_MODE_CYCLE = 1;

    /**
     * deliver event to parent when sliding at the last or first item
     **/
    public static final int SLIDE_BORDER_MODE_TO_PARENT = 2;

    /**
     * auto scroll time in milliseconds, default is {@link #DEFAULT_INTERVAL}
     **/
    private long interval = DEFAULT_INTERVAL;

    /**
     * auto scroll direction, default is {@link #RIGHT}
     **/
    private int direction = RIGHT;

    /**
     * whether stop auto scroll when touching, default is true
     **/
    private boolean stopScrollWhenTouch = true;

    /**
     * how to process when sliding at the last or first item, default is
     * {@link #SLIDE_BORDER_MODE_NONE}
     **/
    private int slideBorderMode = SLIDE_BORDER_MODE_NONE;

    /**
     * whether animating when auto scroll at the last or first item
     **/
    private boolean isBorderAnimation = true;

    /**
     * scroll factor for auto scroll animation, default is 1.0
     **/
    private double autoScrollFactor = 1.0;

    /**
     * scroll factor for swipe scroll animation, default is 1.0
     **/
    private double swipeScrollFactor = 1.0;

    private Handler handler;

    private boolean isAutoScroll = false;

    private boolean isStopByTouch = false;

    private float touchX = 0f, downX = 0f;

    private BannerDurationScroller scroller = null;

    private BannerPagerAdapter adapter;

    public static final int SCROLL_WHAT = 0;

    public BannerView(Context paramContext) {
        super(paramContext);
        init();
    }

    public BannerView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        setViewPagerScroller();
    }

    /**
     * start auto scroll, first scroll delay time is {@link #()}
     */
    public void startScroll() {
        isAutoScroll = true;
        sendScrollMessage((long) (interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startScroll(long delayTimeInMills) {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    /**
     * set the factor by which the duration of sliding animation will change while
     * swiping
     */
    public BannerView setSwipeScrollDurationFactor(double scrollFactor) {
        swipeScrollFactor = scrollFactor;
        return this;
    }

    /**
     * set the factor by which the duration of sliding animation will change while
     * auto scrolling
     */
    public BannerView setAutoScrollDurationFactor(double scrollFactor) {
        autoScrollFactor = scrollFactor;
        return this;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    /**
     * set ViewPager scroller to change animation duration when sliding
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);
            scroller = new BannerDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0) {
            setCurrentItem(totalCount - 1, isBorderAnimation);
        } else if (nextItem == totalCount) {
            setCurrentItem(0, isBorderAnimation);
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
                isStopByTouch = true;
                stopScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startScroll();
            }
        }

        if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
            touchX = ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downX = touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            /**
             * current index is first one and slide to right or current index is last one
             * and slide to left.<br/>
             * if slide border mode is to parent, then requestDisallowInterceptTouchEvent
             * false.<br/>
             * else scroll to last one when current item is first one, scroll to first one
             * when current item is last one.
             */
            if ((currentItem == 0 && downX <= touchX) || (currentItem == pageCount - 1 && downX >= touchX)) {
                if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem(pageCount - currentItem - 1, isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<BannerView> bannerView;

        public MyHandler(BannerView bannerView) {
            this.bannerView = new WeakReference<>(bannerView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    BannerView pager = this.bannerView.get();
                    if (pager != null) {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + pager.scroller.getDuration());
                    }
                default:
                    break;
            }
        }
    }

    /**
     * get auto scroll direction
     *
     * @return {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * set auto scroll direction
     *
     * @param direction {@link #LEFT} or {@link #RIGHT}, default is {@link #RIGHT}
     */
    public BannerView setDirection(int direction) {
        this.direction = direction;
        return this;
    }

    /**
     * whether stop auto scroll when touching, default is true
     *
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * set whether stop auto scroll when touching, default is true
     *
     * @param stopScrollWhenTouch
     */
    public BannerView setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
        return this;
    }

    /**
     * get how to process when sliding at the last or first item
     *
     * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE},
     * {@link #SLIDE_BORDER_MODE_TO_PARENT},
     * {@link #SLIDE_BORDER_MODE_CYCLE}, default is
     * {@link #SLIDE_BORDER_MODE_NONE}
     */
    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    /**
     * set how to process when sliding at the last or first item
     *
     * @param slideBorderMode {@link #SLIDE_BORDER_MODE_NONE},
     *                        {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *                        {@link #SLIDE_BORDER_MODE_CYCLE}, default is
     *                        {@link #SLIDE_BORDER_MODE_NONE}
     */
    public BannerView setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
        return this;
    }

    /**
     * whether animating when auto scroll at the last or first item, default is true
     *
     * @return
     */
    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    /**
     * set whether animating when auto scroll at the last or first item, default is
     * true
     *
     * @param isBorderAnimation
     */
    public BannerView setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
        return this;
    }

    /**
     * 启动Banner
     *
     * @param activity
     * @param holder
     * @param list
     * @return
     */
    public BannerView setPages(WISERActivity activity, BannerHolder holder, List list) {
        adapter = new BannerPagerAdapter(activity, holder);
        adapter.setItems(list);
        setAdapter(adapter);
        setOffscreenPageLimit(2);
        return this;
    }

    /**
     * 开启定时循环轮播
     *
     * @param interval
     * @return
     */
    public BannerView startTurning(long interval) {
        this.interval = interval;
        startScroll(interval);
        if (adapter != null) {
            adapter.setCircle(true);
            if (adapter.getItems() != null) setCurrentItem(adapter.getItems().size() * 50000);
        }
        return this;
    }

    /**
     * 设置是否循环 默认不循环 但是如果调用startTurning方法默认是循环的
     *
     * @param isCircle
     * @return
     */
    public BannerView setCircle(boolean isCircle) {
        if (adapter != null) {
            adapter.setCircle(isCircle);
            if (adapter.getItems() != null && isCircle)
                setCurrentItem(adapter.getItems().size() * 50000);
        }
        return this;
    }

    /**
     * 设置默认预加载条目
     *
     * @param limit
     * @return
     */
    public BannerView setOffsetPageLimit(int limit) {
        setOffscreenPageLimit(limit);
        return this;
    }

    // Banner适配器实例
    public BannerPagerAdapter adapter() {
        return adapter;
    }

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     * @return
     */
    public BannerView setOnItemClickListener(BannerPagerAdapter.OnItemClickListener onItemClickListener) {
        if (adapter != null) adapter.setOnItemListener(onItemClickListener);
        return this;
    }

    /**
     * 设置拦截ScrollView
     *
     * @param isInterceptTouch
     * @return
     */
    public BannerView isInterceptTouch(boolean isInterceptTouch) {
        setInterceptTouch(isInterceptTouch);
        return this;
    }
}
