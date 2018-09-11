package com.wiser.library.view.flip;

import com.wiser.library.R;
import com.wiser.library.base.WISERActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.view.View;

/**
 * @author Wiser
 * 
 *         翻转动画
 */
public class FlipManage implements Animator.AnimatorListener {

	private static FlipManage	flipManage;

	private AnimatorSet			leftAnimatorSet;

	private AnimatorSet			rightAnimatorSet;

	private AnimatorSet			topAnimatorSet;

	private AnimatorSet			bottomAnimatorSet;

	private View				frontView;			// 正面视图

	private View				backView;			// 背面视图

	private View				frontClickView;		// 正面点击的View

	private View				backClickView;		// 反面点击的View

	private boolean				isShowBack;			// 是否显示背面

	private FlipManage(WISERActivity activity, View frontView, View backView) {
		this.frontView = frontView;
		this.backView = backView;
		if (activity != null) {
			leftAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity, R.animator.animator_flip_left);
			rightAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity, R.animator.animator_flip_right);
			topAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity, R.animator.animator_flip_top);
			bottomAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(activity, R.animator.animator_flip_bottom);
			int distance = 16000;
			float scale = activity.getResources().getDisplayMetrics().density * distance;
			if (frontView != null) frontView.setCameraDistance(scale);
			if (backView != null) backView.setCameraDistance(scale);
			leftAnimatorSet.addListener(this);
			rightAnimatorSet.addListener(this);
			topAnimatorSet.addListener(this);
			bottomAnimatorSet.addListener(this);
		}
	}

	/**
	 * 绑定视图
	 * 
	 * @param activity
	 * @param front
	 * @param back
	 */
	public static void bind(WISERActivity activity, View front, View back) {
		synchronized (FlipManage.class) {
			if (flipManage == null) flipManage = new FlipManage(activity, front, back);
		}
	}

	/**
	 * 绑定正面点击View
	 * 
	 * @param frontClickView
	 */
	public void bindFrontClickView(View frontClickView) {
		this.frontClickView = frontClickView;
	}

	/**
	 * 绑定反面点击View
	 * 
	 * @param backClickView
	 */
	public void bindBackClickView(View backClickView) {
		this.backClickView = backClickView;
	}

	public static FlipManage with() {
		synchronized (FlipManage.class) {
			if (flipManage == null) flipManage = new FlipManage(null, null, null);
		}
		return flipManage;
	}

	/**
	 * 左右切换翻转
	 */
	public void startFlipAnimLR() {
		if (rightAnimatorSet == null || leftAnimatorSet == null) return;
		// 正面朝上
		if (!isShowBack) {
			rightAnimatorSet.setTarget(frontView);
			leftAnimatorSet.setTarget(backView);
			rightAnimatorSet.start();
			leftAnimatorSet.start();
			isShowBack = true;
		} else { // 背面朝上
			rightAnimatorSet.setTarget(backView);
			leftAnimatorSet.setTarget(frontView);
			rightAnimatorSet.start();
			leftAnimatorSet.start();
			isShowBack = false;
		}
	}

	/**
	 * 上下切换翻转
	 */
	public void startFlipAnimTB() {
		if (bottomAnimatorSet == null || topAnimatorSet == null) return;
		// 正面朝上
		if (!isShowBack) {
			bottomAnimatorSet.setTarget(frontView);
			topAnimatorSet.setTarget(backView);
			bottomAnimatorSet.start();
			topAnimatorSet.start();
			isShowBack = true;
		} else { // 背面朝上
			bottomAnimatorSet.setTarget(backView);
			topAnimatorSet.setTarget(frontView);
			bottomAnimatorSet.start();
			topAnimatorSet.start();
			isShowBack = false;
		}
	}

	/**
	 * 销毁
	 */
	public void destroyFlipManage() {
		flipManage = null;
		leftAnimatorSet = null;
		rightAnimatorSet = null;
		topAnimatorSet = null;
		bottomAnimatorSet = null;
		frontView = null;
		backView = null;
	}

	@Override public void onAnimationStart(Animator animator) {
		if (frontClickView != null) frontClickView.setClickable(false);
		if (backClickView != null) backClickView.setClickable(false);
		// 正面朝上
		if (!isShowBack) {
			if (backView != null) backView.bringToFront();
		} else { // 背面朝上
			if (frontView != null) frontView.bringToFront();
		}
	}

	@Override public void onAnimationEnd(Animator animator) {
		if (frontClickView != null) frontClickView.setClickable(true);
		if (backClickView != null) backClickView.setClickable(true);

	}

	@Override public void onAnimationCancel(Animator animator) {

	}

	@Override public void onAnimationRepeat(Animator animator) {

	}
}
