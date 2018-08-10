package com.wiser.frame;

import android.os.Bundle;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;


public class IHomeBiz extends WISERBiz<HomeActivity> {


    @Override
    public void initBiz(Bundle savedInstanceState) {
        super.initBiz(savedInstanceState);
        System.out.println("-------savedInstanceStateBIz---------");
    }

    public void bizMethod(String string) {
        WISERHelper.toast().show(string);
        ui().homeMethod("ddddd");
    }

}
