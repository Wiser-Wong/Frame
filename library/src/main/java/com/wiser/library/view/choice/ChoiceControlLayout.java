package com.wiser.library.view.choice;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;

import com.wiser.library.R;
import com.wiser.library.base.WISERActivity;

/**
 * @author Wiser
 *
 *         选择控件
 */
public class ChoiceControlLayout<T> extends RecyclerView implements OnChoiceRecycleClickListener<T> {

	private int							choiceLayoutId;

	private int							spanCount	= 3;

	private ChoiceRecycleViewAdapter<T>	choiceRecycleViewAdapter;

	private OnChoiceAdapter<T>			onChoiceAdapter;

	private OnChoiceListener<T>			onChoiceListener;

	public ChoiceControlLayout(@NonNull Context context) {
		super(context);
		init(context, null);
	}

	public ChoiceControlLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	// 初始化
	private void init(Context context, AttributeSet attrs) {

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChoiceControlLayout);
		if (ta != null) {
			choiceLayoutId = ta.getResourceId(R.styleable.ChoiceControlLayout_ccl_layoutId, -1);
			spanCount = ta.getInt(R.styleable.ChoiceControlLayout_ccl_spanCount, spanCount);
			ta.recycle();
		}

		initData();
	}

	private void initData() {
		// 去掉自带滑动
		setOverScrollMode(OVER_SCROLL_NEVER);
		setLayoutManager(new GridLayoutManager(getContext(), spanCount));
		setAdapter(choiceRecycleViewAdapter = new ChoiceRecycleViewAdapter<>((WISERActivity) getContext(), choicesLayoutId()));
		choiceRecycleViewAdapter.setOnChoiceRecycleClickListener(this);
	}

	private int choicesLayoutId() {
		if (choiceLayoutId == -1) throw new InflateException("未设置布局ccl_layoutId,该属性是需要展示的item布局xml文件");
		return choiceLayoutId;
	}

	public void setItems(List<T> list) {
		if (choiceRecycleViewAdapter != null) choiceRecycleViewAdapter.setItems(list);
	}

	// 更新数据
	public void notifyItemPositionData(int position, T t) {
		choiceRecycleViewAdapter.getItems().set(position, t);
		choiceRecycleViewAdapter.notifyItemChanged(position);
	}

	public void setChoiceAdapter(OnChoiceAdapter<T> onChoiceAdapter) {
		if (this.onChoiceAdapter == null) {
			this.onChoiceAdapter = onChoiceAdapter;
			if (choiceRecycleViewAdapter != null) choiceRecycleViewAdapter.setOnChoiceAdapter(onChoiceAdapter);
		}
	}

	public void setOnChoiceListener(OnChoiceListener<T> onChoiceListener) {
		this.onChoiceListener = onChoiceListener;
	}

	@Override public void callBackAdapterClick(View itemView, int position, T t) {
		if (onChoiceListener != null) onChoiceListener.onChoiceItemClick(this, itemView, position, t);
	}

}
