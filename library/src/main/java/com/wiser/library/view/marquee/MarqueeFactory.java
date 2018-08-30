package com.wiser.library.view.marquee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import android.content.Context;
import android.view.View;

/**
 * @author Wiser
 * 
 *         负责解析提供数据及事件监听
 */
public abstract class MarqueeFactory<E> extends Observable {

	public final static String	COMMAND_UPDATE_DATA	= "UPDATE_DATA";

	private List<View>			mViews;

	private List<E>				dataList;

	private MarqueeView			mMarqueeView;

	abstract View generateMarqueeItemView(E data);

	protected List getMarqueeViews() {
		return mViews != null ? mViews : Collections.EMPTY_LIST;
	}

	public void setData(List<E> dataList) {
		if (dataList == null) {
			return;
		}
		this.dataList = dataList;
		mViews = new ArrayList<>();
		for (int i = 0; i < dataList.size(); i++) {
			E data = dataList.get(i);
			View mView = generateMarqueeItemView(data);
			mViews.add(mView);
		}
		notifyDataChanged();
	}

	public List<E> getData() {
		return dataList;
	}

	private boolean isAttachedToMarqueeView() {
		return this.mMarqueeView != null;
	}

	protected void attachedToMarqueeView(MarqueeView marqueeView) {
		if (!isAttachedToMarqueeView()) {
			this.mMarqueeView = marqueeView;
			this.addObserver(marqueeView);
			return;
		}
		throw new IllegalStateException(String.format("The %s has been attached to the %s!", toString(), mMarqueeView.toString()));
	}

	private void notifyDataChanged() {
		if (isAttachedToMarqueeView()) {
			setChanged();
			notifyObservers(COMMAND_UPDATE_DATA);
		}
	}
}