package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.tab.bottom.WISERTabPageView;

import android.os.Bundle;

public class ThreeFragment extends WISERFragment implements WISERTabPageView.OnTabShowCurrentPageListener {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_three);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}

	@Override public void onShowCurrentPage(int position) {
		System.out.println("-------ThreeFragment-----Show");
	}

	@Override public void onHideCurrentPage(int position) {
		System.out.println("-------ThreeFragment-----Hide");
	}
}
