package com.wiser.frame;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;

public class ISecondFragmentBiz extends WISERBiz<SecondFragment> {

	public void skip() {
		ThirdActivity.intent();
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
