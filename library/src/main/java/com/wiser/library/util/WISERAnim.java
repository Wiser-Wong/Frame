package com.wiser.library.util;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERAnim {

	/**
	 * 透明动画(从不透明~透明(1~0) 从透明~不透明(0~1))
	 *
	 * @param view
	 */
	public static void alphaAnim(View view, float one, float two) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(one, two);
		alphaAnimation.setDuration(2500);
		alphaAnimation.setInterpolator(new AccelerateInterpolator());
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		view.startAnimation(alphaAnimation);
	}

	/**
	 * 控件背景颜色之间缓慢切换
	 *
	 * @param view
	 *            颜色变化View
	 * @param RED
	 *            颜色切换其中一种颜色
	 * @param BLUE
	 *            颜色切换其中另一种颜色
	 */
	public static void changeColorAnim(View view, int RED, int BLUE) {
		ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", RED, BLUE);
		colorAnim.setDuration(3000);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
		colorAnim.start();
	}

	/**
	 * 放大方法
	 *
	 * @param view
	 *            控件布局
	 * @param flag
	 *            true是该控件动画结束时处于结束位置，false是该控件动画结束时处于开始位置
	 */
	public static void magnifyAnim(View view, float m, boolean flag) {
		ScaleAnimation maginfy = new ScaleAnimation(1.0f, m, 1.0f, m, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		maginfy.setDuration(100);
		maginfy.setFillAfter(flag);
		view.startAnimation(maginfy);
	}

	/**
	 * 缩小方法
	 *
	 * @param view
	 *            控件布局
	 * @param flag
	 *            true是该控件动画结束时处于结束位置，false是该控件动画结束时处于开始位置
	 */
	public static void shrinkAnim(View view, float m, boolean flag) {
		ScaleAnimation shrink = new ScaleAnimation(m, 1.0f, m, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		shrink.setDuration(100);
		shrink.setFillAfter(flag);
		view.startAnimation(shrink);
	}

	/**
	 * 一个控件从自己位置移动到另一个控件位置(带缩小效果)
	 *
	 * @param view1
	 * @param view2
	 */
	public static void transAndScaleAnim(View view1, View view2) {
		int[] location = new int[2];
		view1.getLocationOnScreen(location);
		int startX = location[0];
		int startY = location[1];
		view2.getLocationOnScreen(location);
		int lastX = location[0];
		int lastY = location[1];
		int x = lastX - startX;
		int y = lastY - startY;
		AnimationSet set = new AnimationSet(false);
		// 移动动画
		TranslateAnimation trans = new TranslateAnimation(0, x, 0, y);
		// 缩小动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		set.addAnimation(trans);
		set.addAnimation(scaleAnimation);
		set.setDuration(1500);
		set.setInterpolator(new AccelerateInterpolator());// 先加速后减速
		view1.startAnimation(set);
	}

}
