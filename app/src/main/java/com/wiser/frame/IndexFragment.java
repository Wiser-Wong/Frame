package com.wiser.frame;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

public class IndexFragment extends WISERFragment<IndexFragmentBiz> {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_index);
		builder.recycleView().recycleViewId(R.id.rlv_fragment);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.recycleView().isFooter(true);
		builder.swipeBack(true);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
	}
}
