package com.wiser.frame;

import android.content.Intent;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;


public class ISecondBiz extends WISERBiz<SecondActivity> {

    public void back() {
        ui().startActivity(new Intent(ui(), HomeActivity.class));
        ui().finish();
    }

    public void resetUi() {
        if (WISERHelper.getStructureManage().isExist(IHomeBiz.class)) {
            IHomeBiz biz = WISERHelper.getStructureManage().biz(IHomeBiz.class);
            if (biz != null) {
                biz.bizMethod("真有意思");
            }
        }
    }
}
