package com.wiser.frame;

import android.os.Bundle;
import android.view.View;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;


public class SecondActivity extends WISERActivity<ISecondBiz> {

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.activity_second);
        return builder;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    public void back(View view) {
        biz().back();
    }

    public void resetUi(View view){
        biz().resetUi();
    }
}
