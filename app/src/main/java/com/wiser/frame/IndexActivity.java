package com.wiser.frame;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;

public class IndexActivity extends WISERActivity<IndexBiz> {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_index);
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutErrorId(R.layout.view_error);
		builder.layoutLoadingId(R.layout.view_loading);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.tintFitsSystem(true);
		builder.tintIs(true);
		builder.tintColor(getResources().getColor(R.color.colorPrimaryDark));
		builder.swipeBack(true);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
	}
}
