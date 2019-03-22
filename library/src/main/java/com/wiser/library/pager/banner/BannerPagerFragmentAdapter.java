package com.wiser.library.pager.banner;

import java.util.List;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author Wiser
 * @version 版本
 */
public class BannerPagerFragmentAdapter extends FragmentPagerAdapter {

	/**
	 * 数据
	 */
	private List<Fragment>	mItems;

	private WISERView		wiserView;

	private boolean			isCircle;

	public BannerPagerFragmentAdapter(WISERActivity activity, List<Fragment> list) {
		super(activity.getSupportFragmentManager());
		this.wiserView = activity.wiserView();
		this.mItems = list;
	}

	public BannerPagerFragmentAdapter getAdapter() {
		return this;
	}

	public WISERActivity activity() {
		return wiserView.activity();
	}

	public void setCircle(boolean isCircle) {
		this.isCircle = isCircle;
		notifyDataSetChanged();
	}

	public boolean isCircle() {
		return this.isCircle;
	}

	public void setItems(List<Fragment> mItems) {
		this.mItems = mItems;
		notifyDataSetChanged();
	}

	@Override public int getCount() {
		if (mItems != null && mItems.size() > 0) {
			if (isCircle) return Integer.MAX_VALUE;
			else return mItems.size();
		}
		return 0;
	}

	public List<Fragment> getItems() {
		return mItems;
	}

	@Override public Fragment getItem(int position) {
		return mItems.get(position);
	}
}
