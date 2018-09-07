package com.wiser.library.view.marquee;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ViewFlipper;

import com.wiser.library.R;
import com.wiser.library.util.WISERJava;

/**
 * @author Wiser
 * 
 *         属性 ViewAnimator_inAnimation ViewAnimator_outAnimation
 *         ViewAnimator_animateFirstView ViewFlipper_flipInterval
 *         ViewFlipper_autoStart MarqueeView_marqueeAnimDuration
 *         <p>
 *         注意： interval 必须大于 animDuration，否则视图将会重叠
 */
public class MarqueeView<E> extends ViewFlipper implements Observer {

	protected MarqueeFactory<E>	factory;

	private final int			DEFAULT_ANIM_RES_IN		= R.anim.anim_marquee_bottom_in;

	private final int			DEFAULT_ANIM_RES_OUT	= R.anim.anim_marquee_top_out;

	public MarqueeView(Context context) {
		this(context, null);
	}

	public MarqueeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		if (getInAnimation() == null || getOutAnimation() == null) {
			setInAnimation(getContext(), DEFAULT_ANIM_RES_IN);
			setOutAnimation(getContext(), DEFAULT_ANIM_RES_OUT);
		}
		setOnClickListener(onClickListener);
	}

	private void setMarqueeFactory(MarqueeFactory<E> factory) {
		this.factory = factory;
		factory.attachedToMarqueeView(this);
		refreshChildViews();
	}

	protected void refreshChildViews() {
		if (getChildCount() > 0) {
			removeAllViews();
		}
		List mViews = factory.getMarqueeViews();
		for (int i = 0; i < mViews.size(); i++) {
			if (mViews.get(i) instanceof View) addView((View) mViews.get(i));
		}
	}

	@Override public void update(Observable o, Object arg) {
		if (arg == null) return;
		if (MarqueeFactory.COMMAND_UPDATE_DATA.equals(arg.toString())) {
			Animation animation = getInAnimation();
			if (animation != null && animation.hasStarted()) {
				animation.setAnimationListener(new MarqueeAnimationListener() {

					@Override public void onAnimationEnd(Animation animation) {
						refreshChildViews();
						if (animation != null) {
							animation.setAnimationListener(null);
						}
					}
				});
			} else {
				refreshChildViews();
			}
		}
	}
	// <editor-fold desc="点击事件模块">

	private OnItemClickListener<E>	onItemClickListener;

	private boolean					isJustOnceFlag	= true;

	private final OnClickListener	onClickListener	= new OnClickListener() {

														@Override public void onClick(View v) {
															if (onItemClickListener != null) {
																if (factory == null || WISERJava.isEmpty(factory.getData()) || getChildCount() == 0) {
																	onItemClickListener.onItemClickListener(null, null, -1);
																	return;
																}
																final int displayedChild = getDisplayedChild();
																final E mData = factory.getData().get(displayedChild);
																onItemClickListener.onItemClickListener(getCurrentView(), mData, displayedChild);
															}
														}
													};

	@Override public void setOnClickListener(@Nullable OnClickListener l) {
		if (isJustOnceFlag) {
			super.setOnClickListener(l);
			isJustOnceFlag = false;
		} else {
			throw new UnsupportedOperationException("The setOnClickListener method is not supported,please use setOnItemClickListener method.");
		}
	}

	/**
	 * 初始跑马灯
	 * 
	 * @param list
	 *            集合
	 * @param adapter
	 *            适配器
	 * @return
	 */
	public MarqueeView<E> setMarquee(List<E> list, MarqueeAdapter<E> adapter) {
		adapter.setData(list);
		setMarqueeFactory(adapter);
		return this;
	}

	/**
	 * 启动动画
	 * 
	 * @param inResId
	 *            进入动画
	 * @param outResId
	 *            出去动画
	 * @return
	 */
	public MarqueeView<E> setMarqueeAnim(@AnimRes int inResId, @AnimRes int outResId) {
		setInAnimation(getContext(), inResId);
		setOutAnimation(getContext(), outResId);
		return this;
	}

	/**
	 * 启动动画
	 * 
	 * @param inAnimation
	 *            进入动画
	 * @param outAnimation
	 *            出去动画
	 * @return
	 */
	public MarqueeView<E> setMarqueeAnim(Animation inAnimation, Animation outAnimation) {
		setInAnimation(inAnimation);
		setOutAnimation(outAnimation);
		return this;
	}

	/**
	 * 设置点击事件
	 * 
	 * @param mOnItemClickListener
	 * @return
	 */
	public MarqueeView<E> setOnItemClickListener(OnItemClickListener<E> mOnItemClickListener) {
		this.onItemClickListener = mOnItemClickListener;
		return this;
	}

	/**
	 * 设置切换页面时间间隔
	 * 
	 * @param duration
	 * @return
	 */
	public MarqueeView<E> setTimeInterval(int duration) {
		long animDuration = 0;
		if (getInAnimation() != null) {
			animDuration = getInAnimation().getDuration();
		}
		if (duration >= 30) setFlipInterval((int) (duration + animDuration));
		else setFlipInterval((int) (30 + animDuration));
		return this;
	}

	/**
	 * 启动跑马灯必须调用
	 */
	public void start() {
		startFlipping();
	}

}
