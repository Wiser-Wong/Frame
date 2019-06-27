package com.wiser.frame;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.tab.listener.OnTabShowCurrentPageListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

public class IndexFragment extends WISERFragment<IndexFragmentBiz> implements OnTabShowCurrentPageListener , WISERRVAdapter.OnFooterCustomListener {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_index);
		builder.recycleView().recycleViewId(R.id.rlv_fragment);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.recycleView().footerLayoutId(R.layout.title_layout);
		builder.recycleView().setOnFooterCustomListener(this);
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
				if (adapter() == null) return;
				adapter().loadState(WISERRVAdapter.LOAD_END);
				adapter().loadTip("我们结束了");
			}
		}, 4000);
	}

	@Override
	public void onShowCurrentPage(int position) {
		System.out.println("-----IndexFragment-------show");
	}

	@Override
	public void onHideCurrentPage(int position) {
		System.out.println("-----IndexFragment-------hide");
	}

	@Override
	public void footerListener(View footerView, int state) {
		if (adapter() == null) return;
		if (footerView == null) return;
		TextView tvFooter = footerView.findViewById(R.id.tv_title_name);
		switch (state) {
			case WISERRVAdapter.LOAD_RUNNING:
				footerView.setVisibility(View.VISIBLE);
				tvFooter.setText("加载呢");
				break;
			case WISERRVAdapter.LOAD_COMPLETE:
				footerView.setVisibility(View.GONE);
				break;
			case WISERRVAdapter.LOAD_END:
				footerView.setVisibility(View.VISIBLE);
				tvFooter.setText("没啥数据了");
				break;
		}
	}
}
