package com.wiser.library.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author Wiser
 * @version 版本
 */
public abstract class WISERHolder<T> extends RecyclerView.ViewHolder {

	private WISERRVAdapter adapter;

	public WISERHolder(@NonNull View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void setAdapter(WISERRVAdapter adapter) {
		this.adapter = adapter;
	}

	public WISERRVAdapter adapter() {
		return adapter;
	}

	public abstract void bindData(T t, int position);

}
