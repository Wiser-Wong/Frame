package com.wiser.frame;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;

import butterknife.BindView;
import butterknife.OnClick;

public class TestLayoutActivity extends WISERActivity {

	@BindView(R.id.iv_click) AppCompatImageView ivClick;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.test_layout);
		builder.layoutBarId(R.layout.title_layout);
		builder.isRootLayoutRefresh(true, false);
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutLoadingId(R.layout.view_loading);
		builder.layoutErrorId(R.layout.view_error);
		return builder;
	}

	@Override protected void initData(Intent intent) {
	}

	@OnClick(value = {R.id.iv_click,R.id.tv_empty,R.id.progress_view,R.id.tv_error}) public void onClickView(View view){
		switch (view.getId()){
			case R.id.iv_click:
				showEmptyView();
				break;
			case R.id.tv_empty:
				showLoadingView();
				break;
			case R.id.progress_view:
				showErrorView();
				break;
			case R.id.tv_error:
				showContentView();
				break;
		}
	}
}
