package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERTabPageActivity;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.tab.bottom.WISERTabPageView;
import com.wiser.library.tab.bottom.WISERTabView;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;

public class TabPageActivity extends WISERTabPageActivity implements WISERTabView.OnTabClickListener, WISERTabPageView.OnTabPageChangeListener {

	@BindView(R.id.iv_tab1) ImageView	ivTab1;

	@BindView(R.id.iv_tab2) ImageView	ivTab2;

	@BindView(R.id.iv_tab3) ImageView	ivTab3;

	@BindView(R.id.iv_tab4) ImageView	ivTab4;

	@Override protected WISERTabPageView buildTabPageView(WISERTabPageView tabPageView) {
		tabPageView.tabIds(R.layout.include_tab_bottom, R.id.iv_tab1, R.id.iv_tab2, R.id.iv_tab3, R.id.iv_tab4);
		tabPageView.setPages(new IndexFragment(), new SecondFragment(), new ThreeFragment(), new TabPageFragment());
		tabPageView.setTabDirection(WISERTabPageView.LEFT);
		tabPageView.setOtherViewId(R.layout.include_page_left,WISERTabPageView.BOTTOM);
		tabPageView.setOnTabClickListener(this);
		tabPageView.setOnTabPageChangeListener(this);
		tabPageView.isPageCanScroll(false);
		tabPageView.isTabCanRepeatedlyClick(true);
		tabPageView.isTabCutPageAnim(false);
		return tabPageView;
	}

	@Override protected WISERBuilder buildFrame(WISERBuilder builder) {
		builder.tintFitsSystem(true);
		builder.tintIs(true);
		builder.tintColor(Color.RED);
		builder.swipeBack(true);
		return builder;
	}

	@Override protected void initTabPageViewData(Intent intent) {
		setCurrentItem(2);
	}

	@Override public void onTabClick(View view) {
		WISERHelper.toast().show(CURRENT_INDEX + "");
		switch (view.getId()) {
			case R.id.iv_tab1:
				break;
			case R.id.iv_tab2:
				ivTab2.setImageResource(R.mipmap.ic_launcher_round);
				break;
			case R.id.iv_tab3:
				break;
			case R.id.iv_tab4:
				break;
		}
	}

	@Override public void onPageScrolled(int i, float v, int i1) {

	}

	@Override public void onPageSelected(int i) {
		switch (i) {
			case 0:
				break;
			case 1:
				ivTab2.setImageResource(R.mipmap.ic_launcher_round);
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}

	@Override public void onPageScrollStateChanged(int i) {

	}
}
