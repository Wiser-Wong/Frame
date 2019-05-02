package com.wiser.library.tab.listener;

/**
 * @author Wiser
 * 
 *         page 切换监听
 */
public interface OnTabPageChangeListener {

	void onPageScrolled(int i, float v, int i1);

	void onPageSelected(int i);

	void onPageScrollStateChanged(int i);

}
