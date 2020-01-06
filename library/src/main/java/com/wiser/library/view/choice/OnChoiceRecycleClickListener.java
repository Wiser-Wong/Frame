package com.wiser.library.view.choice;

import android.view.View;

/**
 * @author Wiser
 * 
 * @param <T>
 *
 *            RecycleView 点击选择监听
 */
public interface OnChoiceRecycleClickListener<T> {

	void callBackAdapterClick(View itemView, int position, T t);
}