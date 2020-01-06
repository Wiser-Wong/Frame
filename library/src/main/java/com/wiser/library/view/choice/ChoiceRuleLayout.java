package com.wiser.library.view.choice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wiser.library.R;

/**
 * @author Wiser
 * @param <T>
 * 
 *            选择控件
 */
public class ChoiceRuleLayout<T> extends LinearLayout {

	private int					spanCount	= 3;

	private int					choiceLayoutId;

	private LayoutInflater		mInflater;

	private List<T>				list;

	private OnChoiceListener<T>	onChoiceListener;

	private OnChoiceAdapter<T>	onChoiceAdapter;

	private List<View>			views		= new ArrayList<>();

	public ChoiceRuleLayout(Context context) {
		super(context);
		init(context, null);
	}

	public ChoiceRuleLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {

		mInflater = LayoutInflater.from(context);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChoiceRuleLayout);
		if (ta != null) {
			choiceLayoutId = ta.getResourceId(R.styleable.ChoiceRuleLayout_crl_layoutId, -1);
			spanCount = ta.getInt(R.styleable.ChoiceRuleLayout_crl_spanCount, spanCount);
			ta.recycle();
		}

		setOrientation(LinearLayout.VERTICAL);
	}

	public void notifyList(List<T> list) {
		this.list = list;
	}

	public void setItems(List<T> list) {
		if (list == null || list.size() == 0) return;
		this.list = list;
		removeAllViews();
		setView();
	}

	private void setView() {
		if (choiceLayoutId == -1) return;

		// 每一行父布局
		LinearLayout layout = new LinearLayout(getContext());
		LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		addView(layout);
		int size;
		if (list.size() % spanCount == 0) {
			size = list.size();
		} else {
			size = list.size() + spanCount - list.size() % spanCount;
		}
		for (int i = 0; i < size; i++) {
			if (i > 0 && i % spanCount == 0) {
				layout = new LinearLayout(getContext());
				layout.setLayoutParams(params);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				addItemView(layout, i, list.get(i), true);
				addView(layout);
			} else {
				if (i > list.size() - 1) {
					addItemView(layout, i, null, false);
				} else {
					addItemView(layout, i, list.get(i), true);
				}
			}
		}
	}

	private void addItemView(LinearLayout layout, final int position, final T t, boolean isVisible) {
		final View view = mInflater.inflate(choiceLayoutId, layout, false);
		LayoutParams params = (LayoutParams) view.getLayoutParams();
		params.width = 0;
		params.weight = 1;
		view.setLayoutParams(params);
		layout.addView(view);
		if (!isVisible) {
			view.setVisibility(View.INVISIBLE);
		} else {
			views.add(view);
			if (onChoiceAdapter != null) onChoiceAdapter.onCreateItemView(view, position, t);
			view.setOnClickListener(new OnClickListener() {

				@Override public void onClick(View v) {
					if (onChoiceListener != null) onChoiceListener.onChoiceItemClick(ChoiceRuleLayout.this, view, position, t);
				}
			});
		}
	}

	public void notifyItemPositionData(int position, T t) {
		if (views == null || views.size() == 0) return;
		for (int i = 0; i < views.size(); i++) {
			if (i == position) {
				if (onChoiceAdapter != null) onChoiceAdapter.onCreateItemView(views.get(i), position, t);
			}
		}
	}

	public void notifyItemPositionData(int position) {
		if (views == null || views.size() == 0) return;
		for (int i = 0; i < views.size(); i++) {
			if (i == position) {
				if (onChoiceAdapter != null) onChoiceAdapter.onCreateItemView(views.get(i), position, list != null && list.size() > 0 ? list.get(position) : null);
			}
		}
	}

	public void notifyDataSetChanged() {
		if (list == null || list.size() == 0) return;
		removeAllViews();
		setView();
	}

	public void notifyDataSetChanged(List<T> list) {
		removeAllViews();
		this.list = list;
		if (this.list == null || this.list.size() == 0) return;
		setView();
	}

	public void setOnChoiceListener(OnChoiceListener<T> onChoiceListener) {
		this.onChoiceListener = onChoiceListener;
	}

	public void setChoiceAdapter(OnChoiceAdapter<T> onChoiceAdapter) {
		this.onChoiceAdapter = onChoiceAdapter;
	}

	@Override protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (views != null) views.clear();
		if (list != null) list.clear();
		views = null;
		list = null;
		mInflater = null;
		onChoiceAdapter = null;
		onChoiceListener = null;
	}
}
