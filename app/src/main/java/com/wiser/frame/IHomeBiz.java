package com.wiser.frame;

import android.os.Bundle;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class IHomeBiz extends WISERBiz<HomeActivity> {

	@Override public void initBiz(Bundle savedInstanceState) {
		super.initBiz(savedInstanceState);
	}

	public void bizMethod(String string) {
		WISERHelper.toast().show(string);
		ui().homeMethod(string);
	}

	public void addData() {
		List<ABean> aBeans = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			ABean aBean = new ABean();
			aBean.age = "年龄：-->>" + i;
			aBeans.add(aBean);
		}
		ui().setItems(aBeans);
	}

	public void loadData() {
		Call<ABean> call = http(IHttp.class).get();
		ABean aBean = httpBody(call);
	}

}
