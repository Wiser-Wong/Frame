package com.wiser.library.view.irregular;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Wiser
 * @param <T>
 *            选择监听
 */
public interface OnItemClickListener<T> {

	void onItemClick(ViewGroup viewGroup, View view, int position, T t);
}