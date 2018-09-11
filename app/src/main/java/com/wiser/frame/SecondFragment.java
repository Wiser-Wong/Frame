package com.wiser.frame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.base.WISERFragment;

public class SecondFragment extends WISERFragment {

    @Override
    protected WISERBuilder build(WISERBuilder builder) {
        builder.layoutId(R.layout.fragment_second);
        return builder;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
