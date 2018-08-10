package com.wiser.frame;

import android.content.Intent;

import com.wiser.library.base.WISERBiz;


public class ISecondBiz extends WISERBiz<SecondActivity> {

    public void back() {
        ui().startActivity(new Intent(ui(), HomeActivity.class));
        ui().finish();
    }

}
