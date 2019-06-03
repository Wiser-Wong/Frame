package com.wiser.library.tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Wiser
 *
 *         tab 页面适配器
 */
public class WISERTabPageAdapter extends FragmentPagerAdapter {

	private Fragment[] fragments;

	public WISERTabPageAdapter(FragmentManager fm, Fragment... fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override public Fragment getItem(int i) {
		return fragments[i];
	}

	@Override public int getCount() {
		return fragments == null || fragments.length == 0 ? 0 : fragments.length;
	}

	public void detach() {
		fragments = null;
	}

}
