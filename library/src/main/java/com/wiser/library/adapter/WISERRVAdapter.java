package com.wiser.library.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wiser.library.base.WISERActivity;
import com.wiser.library.util.WISERCheckUtil;

import java.util.List;

/**
 * @author Wiser
 * @param <V>
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class WISERRVAdapter<T, V extends WISERHolder> extends RecyclerView.Adapter<V> {

	private LayoutInflater	mInflater;

	/**
	 * 数据
	 */
	private List			mItems;

	private WISERActivity	mWiserActivity;

	public WISERRVAdapter(WISERActivity mWiserActivity) {
		WISERCheckUtil.checkNotNull(mWiserActivity, "View层不存在");
		this.mWiserActivity = mWiserActivity;
		this.mInflater = LayoutInflater.from(mWiserActivity);
	}

	// WiserHolder对象
	public abstract V newViewHolder(ViewGroup viewGroup, int type);

	public WISERRVAdapter getAdapter() {
		return this;
	}

	public View inflate(ViewGroup viewGroup, int layoutId) {
		return mInflater.inflate(layoutId, viewGroup, false);
	}

	public WISERActivity activity() {
		return mWiserActivity;
	}

	public void setItems(List mItems) {
		this.mItems = mItems;
		notifyDataSetChanged();
	}

	public List getItems() {
		return mItems;
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public void addItem(int position, Object object) {
		if (object == null || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.add(position, object);
		notifyItemInserted(position);
	}

	public void addList(int position, List list) {
		if (list == null || list.size() < 1 || getItems() == null || position < 0 || position > getItems().size()) {
			return;
		}
		mItems.addAll(position, list);
		notifyItemRangeInserted(position, list.size());
	}

	public void addList(List list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.addAll(list);
		notifyItemRangeInserted(position, list.size());
	}

	public void delete(int position) {
		if (getItems() == null || position < 0 || getItems().size() < position) {
			return;
		}
		mItems.remove(position);
		notifyItemRemoved(position);
	}

	public void delete(List list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		int position = getItemCount();
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void delete(int position, List list) {
		if (list == null || list.size() < 1 || getItems() == null) {
			return;
		}
		mItems.removeAll(list);
		notifyItemRangeRemoved(position, list.size());
	}

	public void clear() {
		mItems.clear();
		notifyDataSetChanged();
	}

	@NonNull @Override public V onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		V holder;
		holder = newViewHolder(viewGroup, i);
		return holder;
	}

	@Override public void onBindViewHolder(@NonNull V v, int i) {
		v.bindData(getItem(i), i);
	}

	@Override public int getItemCount() {
		return mItems == null ? 0 : mItems.size();
	}
}
