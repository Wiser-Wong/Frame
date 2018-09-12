package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.tab.WISERTabPageView;

import android.os.Bundle;

public class SecondFragment extends WISERFragment implements WISERTabPageView.OnTabShowCurrentPageListener {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_second);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {

	}

    @Override public void onShowCurrentPage(int position) {
		System.out.println("-------SecondFragment-----Show");
    }

    @Override public void onHideCurrentPage(int position) {
		System.out.println("-------SecondFragment-----Hide");
	}
}
