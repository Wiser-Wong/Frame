package com.wiser.library.pager.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @param <T>
 * @author Wiser
 */
public interface BannerHolder<T> {

    View createView(Context context, LayoutInflater inflater,ViewGroup container);

    void bindData(Context context, int position, T data);

}
