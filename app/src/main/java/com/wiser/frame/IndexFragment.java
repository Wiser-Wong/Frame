package com.wiser.frame;

import android.os.Bundle;
import android.os.Handler;
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

	@Override public void onLoadMore() {
		super.onLoadMore();
		new Handler().postDelayed(new Runnable() {

			@Override public void run() {
				if (adapter().getLoadState() == adapter().LOAD_END) return;
				adapter().addList(biz().addNewData());
				adapter().loadState(adapter().LOAD_END);
			}
		}, 4000);
	}
}
