package com.wiser.library.view.irregular;

import android.view.View;

/**
 * @author Wiser
 * 
 *         选择监听
 */
public interface OnIrregularAdapter<T> {

	void onCreateItemView(View itemView, int position, T t);

}
