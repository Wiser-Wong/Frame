package com.wiser.frame;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.google.gson.Gson;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;
import com.wiser.library.util.WISERGsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class ThirdFragment extends WISERFragment<IThirdBiz> {

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_main);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		builder.recycleView().recycleAdapter(new HomeAdapter(activity()));
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, null);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		biz().addData();
	}

	@OnClick(R.id.tv_json) public void parse(View view) {
		List<ABean> aBeans = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			ABean aBean = new ABean();
			aBean.age = i + "";
			aBeans.add(aBean);
		}
		Gson gson = new Gson();
		List<ABean> tBeans = WISERGsonUtil.getDataList(gson.toJson(aBeans), ABean.class);
		biz().ddd(tBeans);
		// SecondActivity.intent();
		// showEmptyView();
		// showErrorView();
		// showLoading();
	}
}
