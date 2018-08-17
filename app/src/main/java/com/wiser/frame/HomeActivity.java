package com.wiser.frame;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERRecycleView;
import com.wiser.library.helper.WISERHelper;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;

public class HomeActivity extends WISERActivity<IHomeBiz> {

	private ABean					aBean	= new ABean();

	@BindView(R.id.tv_json) Button	tvJson;

	public static void intent() {
		WISERHelper.display().intent(HomeActivity.class);
	}

	@Override protected WISERBuilder build(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_main);
		builder.tintFitsSystem(true);
		builder.tintIs(false);
		builder.tintColor(getResources().getColor(R.color.colorAccent));
		builder.swipeBack(false);
		builder.recycleView().recycleViewId(R.id.home_rlv);
		builder.recycleView().recycleAdapter(new HomeAdapter(this));
		builder.recycleView().recycleViewLinearManager(LinearLayoutManager.VERTICAL, new DefaultItemAnimator());
		builder.layoutEmptyId(R.layout.view_empty);
		builder.layoutErrorId(R.layout.view_error);
		builder.layoutLoadingId(R.layout.view_loading);
		return builder;
	}

	@Override public void initData(Bundle savedInstanceState) {
		System.out.println("----tvJson--->>" + tvJson);
		biz().bizMethod("dddd");
		biz().addData();
		WISERHelper.log().e(WISERHelper.getActivityManage().getCurrentActivity().getClass().getSimpleName());
	}

	public void parse(View view) {
		// List<ABean> aBeans = new ArrayList<>();
		// for (int i = 0; i < 4; i++) {
		// ABean aBean = new ABean();
		// aBean.age = i + "";
		// aBeans.add(aBean);
		// }
		// Gson gson = new Gson();
		// List<ABean> tBeans = new Utils<ABean>().getDatas(gson.toJson(aBeans),
		// ABean.class);
		// homeMethod("aaa");
		SecondActivity.intent();
		// showEmptyView();
		// showErrorView();
		// showLoading();
	}

	public void homeMethod(String s) {

		System.out.println("------biz()---->>" + biz());
		System.out.println("------biz-name---->>" + biz().getClass().getSimpleName());
		System.out.println("------IHomeBiz-name---->>" + IHomeBiz.class.getSimpleName());
		System.out.println("----isExistHome------->>" + WISERHelper.getBizManage().isExist(IHomeBiz.class));
		System.out.println("-----isExistSecond------->>" + WISERHelper.getBizManage().isExist(ISecondBiz.class));
		tvJson.setText(s);

	}

}
