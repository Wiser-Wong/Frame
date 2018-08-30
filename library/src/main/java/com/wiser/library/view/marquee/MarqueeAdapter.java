package com.wiser.library.view.marquee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * @author Wiser
 * 
 *         跑马灯适配器
 * @param <V>
 *            数据类
 */
public abstract class MarqueeAdapter<V> extends MarqueeFactory<V> {

	private LayoutInflater mInflater;

	protected MarqueeAdapter(Context mContext) {
		mInflater = LayoutInflater.from(mContext);
	}

	protected View inflate(int layoutId) {
		return mInflater.inflate(layoutId, null, false);
	}

	protected abstract View createItemView(V data);

	@Override protected View generateMarqueeItemView(V data) {
		return createItemView(data);
	}
}