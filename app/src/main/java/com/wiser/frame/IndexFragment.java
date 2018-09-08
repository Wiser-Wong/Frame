package com.wiser.frame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

public class IndexFragment extends WISERFragment<IndexFragmentBiz> {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_index);
		// builder.recycleView().recycleViewId(R.id.rlv_fragment);
		// builder.recycleView().recycleViewStaggeredGridManager(2,
		// LinearLayoutManager.VERTICAL);
		// builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.recycleView().setFooterStyle(Color.BLUE, Color.RED, Color.WHITE);
		builder.recycleView().setFooterPadding(0, 5, 0, 5);
		builder.recycleView().isFooter(true);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
	}

	@Override public void onLoadMore() {
		super.onLoadMore();
		if (adapter().getLoadState() == WISERRVAdapter.LOAD_END || adapter().getLoadState() == WISERRVAdapter.LOAD_RUNNING) return;
		adapter().loadState(WISERRVAdapter.LOAD_RUNNING);
		new Handler().postDelayed(new Runnable() {

			@Override public void run() {
				// adapter().addList(biz().addNewData());
				adapter().loadState(WISERRVAdapter.LOAD_END);
				adapter().loadTip("我们结束了");
			}
		}, 4000);
	}
}
