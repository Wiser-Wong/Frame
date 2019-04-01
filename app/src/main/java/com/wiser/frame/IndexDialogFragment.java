package com.wiser.frame;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERDialogFragment;
import com.wiser.library.helper.WISERHelper;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import butterknife.BindView;

public class IndexDialogFragment extends WISERDialogFragment<IndexDialogFragmentBiz> {

	@BindView(R.id.tv_dialog) TextView textView;

	public static WISERDialogFragment newInstance() {
		return new IndexDialogFragment();
	}

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.fragment_dialog);
		builder.recycleView().recycleViewId(R.id.dialog_rlv);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.recycleView().isFooter(true);
		return builder;
	}

	@Override protected void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
	}

	@Override protected int dialogWeight() {
		return CENTER;
	}

	@Override protected int dialogTheme() {
		return 0;
	}

	@Override protected boolean isWidthFullScreen() {
		return true;
	}

	@Override protected boolean isCloseOnTouchOutside() {
		return false;
	}

	@Override
	protected boolean isCloseOnTouchBack() {
		return true;
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

	@Override public void onRefresh() {
		WISERHelper.toast().show("刷新");
		new Handler().postDelayed(new Runnable() {

			@Override public void run() {
				// showLoading();
				refreshComplete();
			}
		}, 4000);
	}
}
