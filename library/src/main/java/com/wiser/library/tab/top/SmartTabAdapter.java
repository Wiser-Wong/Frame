package com.wiser.library.tab.top;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * @author Wiser
 * 
 *         适配器
 */
public class SmartTabAdapter extends FragmentStatePagerAdapter {

	private Context			mContext;

	private SmartTabInfo[]	mSmartTabInfos;

	public SmartTabAdapter(FragmentManager fm, Context context, SmartTabInfo[] mSmartTabInfos) {
		super(fm);
		this.mContext = context;
		this.mSmartTabInfos = mSmartTabInfos;
	}

	@Override public Fragment getItem(int position) {
		return Fragment.instantiate(mContext, mSmartTabInfos[position].mClazz.getName(), mSmartTabInfos[position].mBundle);
	}

	@Override public int getCount() {
		return mSmartTabInfos.length;
	}

	@Override public CharSequence getPageTitle(int position) {
		return mSmartTabInfos[position].mTitle;
	}
}
