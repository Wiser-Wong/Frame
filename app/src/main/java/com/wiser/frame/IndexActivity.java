package com.wiser.frame;

import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERDialogFragment;
import com.wiser.library.base.WISERStaggeredDivider;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERDate;
import com.wiser.library.view.FooterView;
import com.wiser.library.view.marquee.MarqueeAdapter;
import com.wiser.library.view.marquee.MarqueeView;
import com.wiser.library.zxing.WISERQRCode;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class IndexActivity extends WISERActivity<IndexBiz> implements WISERRVAdapter.FooterCustomListener {

	@BindView(R.id.tv_name) TextView				tvName;

	@BindView(R.id.iv_qr) ImageView					ivQR;

	@BindView(R.id.marquee) MarqueeView<IndexModel>	marqueeView;

	@BindView(R.id.tv_d) TextView					textView;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_index);
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutErrorId(R.layout.view_error);
		builder.layoutLoadingId(R.layout.view_loading);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		// builder.recycleView().recycleViewStaggeredGridManager(2,
		// LinearLayoutManager.VERTICAL, new WISERStaggeredDivider(20, 0, 20, 0), null);
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		builder.recycleView().recycleAdapter(new IndexAdapter(this));
		builder.isRootLayoutRefresh(true, false);
		builder.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN);
		builder.recycleView().setFooterStyle(Color.BLUE, Color.RED, Color.WHITE);
		builder.recycleView().setFooterPadding(0, 5, 0, 5);
		builder.recycleView().isFooter(true);
		builder.recycleView().footerLayoutId(R.layout.title_layout);
		// builder.setProgressBackgroundColorSchemeColor(Color.BLACK);
		// builder.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.cpv_default_color),getResources().getColor(R.color.design_default_color_primary));
		builder.tintFitsSystem(true);
		builder.tintIs(true);
		builder.tintColor(getResources().getColor(R.color.colorPrimaryDark));
		builder.swipeBack(true);
		return builder;
	}

	@SuppressLint("SetTextI18n") @Override public void initData(Bundle savedInstanceState) {
		biz().addAdapterData();
		// tvName.setText(WISERDate.getLongForDateStr("2018-09-11",WISERDate.DATE_HG,true)+"");
		tvName.setText(WISERDate.getDateStrForLong(1536595200000L, WISERDate.DATE_HZ, false) + "");
		// onRefresh();
		// WISERHelper.display().commitReplace(R.id.fl_content, new IndexFragment());

		WISERQRCode.createQRCodeBitmapForUrl("", "WiserWong", R.mipmap.ic_launcher, ivQR, false);

		marquee();

		if (adapter() != null) adapter().setFooterCustomListener(this);

	}

	// 跑马灯
	private void marquee() {
		marqueeView.setMarquee(biz().indexModels, new MarqueeAdapter<IndexModel>(this) {

			@Override protected View createItemView(IndexModel data) {
				if (data == null) return null;
				View view = inflate(R.layout.marquee_item);
				TextView tvMarquee = view.findViewById(R.id.tv_marquee);
				tvMarquee.setText(data.age);
				return view;
			}
		}).setMarqueeAnim(R.anim.anim_marquee_bottom_in, R.anim.anim_marquee_top_out).setTimeInterval(100).start();
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
		if (adapter().getLoadState() == WISERRVAdapter.LOAD_END || adapter().getLoadState() == WISERRVAdapter.LOAD_RUNNING) return;
		adapter().loadState(WISERRVAdapter.LOAD_RUNNING);
		adapter().loadTip("上拉刷新");
		new Handler(getMainLooper()).postDelayed(new Runnable() {

			@Override public void run() {
				adapter().addList(biz().addNewData());
				adapter().loadState(WISERRVAdapter.LOAD_END);
				adapter().loadTip("已经到头了");
				// showLoading();
				// adapter().loadState(adapter().LOAD_COMPLETE);
			}
		}, 4000);
	}

	@OnClick({ R.id.tv_name, R.id.iv_qr, R.id.tv_d }) public void onClickView(View view) {
		switch (view.getId()) {
			case R.id.tv_name:
				WISERHelper.display().intent(WebViewActivity.class);
				break;
			case R.id.iv_qr:
				WISERHelper.display().intent(ScanActivity.class);
				break;
			case R.id.tv_d:
				IndexDialogFragment.newInstance().setLocation(textView, WISERDialogFragment.CONTROL_FIT).show(getSupportFragmentManager(), "");
				break;
		}
	}

	@Override public void footerListener(FooterView footerView, int state) {
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
