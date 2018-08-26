package com.wiser.frame;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERDate;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import butterknife.BindView;

public class IndexActivity extends WISERActivity<IndexBiz> {

	@BindView(R.id.tv_name) TextView tvName;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_index);
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutErrorId(R.layout.view_error);
		builder.layoutLoadingId(R.layout.view_loading);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
		builder.recycleView().isFooter(true);
		// builder.setProgressBackgroundColorSchemeColor(Color.BLACK);
		// builder.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.cpv_default_color),getResources().getColor(R.color.design_default_color_primary));
		builder.tintFitsSystem(true);
		builder.tintIs(true);
		builder.tintColor(getResources().getColor(R.color.colorPrimaryDark));
		builder.swipeBack(true);
		return builder;
	}

	@SuppressLint("SetTextI18n")
	@Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
//		tvName.setText(WISERDate.getLongForDateStr("2018-09-11",WISERDate.DATE_HG,true)+"");
		tvName.setText(WISERDate.getDateStrForLong(1536595200000L,WISERDate.DATE_HZ,false)+"");
		// onRefresh();
		WISERHelper.display().commitReplace(R.id.fl_content,new IndexFragment());
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

	@Override public void onLoadMore() {
		super.onLoadMore();
		WISERHelper.toast().show("上拉刷新");
		new Handler(getMainLooper()).postDelayed(new Runnable() {

			@Override public void run() {
				if (adapter().getLoadState() == adapter().LOAD_END) return;
				adapter().addList(biz().addNewData());
				adapter().loadState(adapter().LOAD_END);
				// showLoading();
				// adapter().loadState(adapter().LOAD_COMPLETE);
			}
		}, 4000);
	}
}
