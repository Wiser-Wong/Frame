package com.wiser.frame;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;

import java.util.ArrayList;
import java.util.List;

public class IThirdBiz extends WISERBiz<ThirdFragment> {

	public void ddd(List<ABean> aBeans){
		WISERHelper.toast().show(aBeans.toString());
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

}
