package com.wiser.frame;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;

public class ISecondBiz extends WISERBiz<SecondActivity> {

	public void back() {
		HomeActivity.intent();
	}

	public void resetUi() {
		if (WISERHelper.getBizManage().isExist(IHomeBiz.class)) {
			IHomeBiz biz = WISERHelper.getBizManage().biz(IHomeBiz.class);
			if (biz != null) {
				biz.bizMethod("真有意思");
			}
		}
	}
}
