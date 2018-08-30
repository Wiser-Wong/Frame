package com.wiser.library.view.marquee;

import android.view.View;

/**
 * @author Wiser
 * 
 *         点击事件
 */
public interface OnItemClickListener<E> {

	void onItemClickListener(View mView, E mData, int mPosition);
}
