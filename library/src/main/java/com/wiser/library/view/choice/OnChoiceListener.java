package com.wiser.library.view.choice;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Wiser
 * @param <T>
 *            选择监听
 */
public interface OnChoiceListener<T> {

	void onChoiceItemClick(ViewGroup viewGroup, View view, int position, T t);
}