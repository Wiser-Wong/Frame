package com.wiser.frame;

import com.wiser.frame.slidingmenu.ContentFragment;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.tab.WISERTabLayout;
import com.wiser.library.tab.WISERTabPage;
import com.wiser.library.tab.listener.OnTabClickListener;
import com.wiser.library.tab.listener.OnTabPageChangeListener;
import com.wiser.library.tab.listener.OnTabSwitchPageListener;

import android.content.Intent;
import android.view.View;

import butterknife.BindView;

public class TabLayoutActivity extends WISERActivity implements OnTabPageChangeListener, OnTabClickListener, OnTabSwitchPageListener {

	@BindView(R.id.tab_layout) WISERTabLayout	tabLayout;

	@BindView(R.id.tab_page_view) WISERTabPage	tabPage;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.tab_act);
		return builder;
	}

	@Override protected void initData(Intent intent) {
		tabLayout.tabIds(R.layout.include_tab_bottom, R.id.iv_tab1, R.id.iv_tab2, R.id.iv_tab3, R.id.iv_tab4)
				.setPages(R.id.tab_page_view, new IndexFragment(), new SecondFragment(), new ThreeFragment(), new ContentFragment()).isTabCutPageAnim(true)
				.setOnTabPageChangeListener(this).setOnTabClickListener(this).isPageCanScroll(true).setOnTabSwitchPageListener(this);
	}

	@Override public void onPageScrolled(int i, float v, int i1) {

	}

	@Override public void onPageSelected(int i) {
		System.out.println("---------i----" + i);
	}

	@Override public void onPageScrollStateChanged(int i) {

	}

	@Override public void onTabClick(View view) {
		System.out.println("------onTabClick-------" + view);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		if (tabLayout != null) tabLayout.onDetach();
	}

	@Override public void onTabSwitch(View view, int position) {
		System.out.println("------------" + view + "-----------" + position);
	}
}
