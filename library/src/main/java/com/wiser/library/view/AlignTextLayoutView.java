package com.wiser.library.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wiser.library.R;
import com.wiser.library.util.WISERApp;

/**
 * @author Wiser
 * 
 *         不规则排列文本展示布局
 */
public class AlignTextLayoutView extends LinearLayout {

	/**
	 * 标签的列间距 px
	 */
	private int					itemMargins	= 10;

	/**
	 * 标签的行间距 px
	 */
	private int					lineMargins	= 10;

	private LayoutInflater		mInflater;

	private List<String>		list;

	private int					itemLayoutId;

	private int					itemLayoutTextId;

	private int					containerWidth;				// 该控件宽度

	private Paint				paint		= new Paint();	// 用来测量文本的宽度

	private OnAlignItemListener	onAlignItemListener;

	public AlignTextLayoutView(Context context) {
		super(context);
	}

	public AlignTextLayoutView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	// 初始化
	private void init(@Nullable AttributeSet attrs) {
		mInflater = LayoutInflater.from(getContext());

		@SuppressLint("CustomViewStyleable")
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AlignLayout);
		if (typedArray != null) {
			itemLayoutId = typedArray.getResourceId(R.styleable.AlignLayout_alignLayoutItemId, -1);
			itemLayoutTextId = typedArray.getResourceId(R.styleable.AlignLayout_alignLayoutItemTextId, -1);
			itemMargins = (int) typedArray.getDimension(R.styleable.AlignLayout_alignLayoutItemMargins,
					TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemMargins, getResources().getDisplayMetrics()));
			lineMargins = (int) typedArray.getDimension(R.styleable.AlignLayout_alignLayoutLineMargins,
					TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineMargins, getResources().getDisplayMetrics()));
			typedArray.recycle();
		}
	}

	/**
	 * 填充数据
	 *
	 * @param list
	 */
	public AlignTextLayoutView setData(List<String> list) {
		this.list = list;
		if (list == null) return this;
		if (list.size() == 0) return this;
		removeAllViews();
		setTextView();
		return this;
	}

	// 设置容器总宽度
	public AlignTextLayoutView setContainerWidth(int containerWidth) {
		this.containerWidth = containerWidth;
		return this;
	}

	// 设置容器总宽度
	public AlignTextLayoutView setContainerWidth(int containerWidth, boolean isDp) {
		if (isDp) {
			this.containerWidth = WISERApp.dip2px(containerWidth);
		} else {
			this.containerWidth = containerWidth;
		}
		return this;
	}

	// item间距
	public AlignTextLayoutView setItemMargin(int margin) {
		this.itemMargins = margin;
		return this;
	}

	// item间距
	public AlignTextLayoutView setItemMargin(int margin, boolean isDp) {
		if (isDp) {
			this.itemMargins = WISERApp.dip2px(margin);
		} else {
			this.itemMargins = margin;
		}
		return this;
	}

	// line间距
	public AlignTextLayoutView setItemLineMargin(int margin) {
		this.lineMargins = margin;
		return this;
	}

	// line间距
	public AlignTextLayoutView setItemLineMargin(int margin, boolean isDp) {
		if (isDp) {
			this.lineMargins = WISERApp.dip2px(margin);
		} else {
			this.lineMargins = margin;
		}
		return this;
	}

	// 设置ItemLayout ID
	public AlignTextLayoutView setItemLayoutId(@LayoutRes int itemLayoutId) {
		this.itemLayoutId = itemLayoutId;
		return this;
	}

	// 设置ItemLayout 中 TextView ID
	public AlignTextLayoutView setItemLayoutTextId(@IdRes int itemLayoutTextId) {
		this.itemLayoutTextId = itemLayoutTextId;
		return this;
	}

	/**
	 * 设置Text布局
	 */
	private void setTextView() {
		if (itemLayoutId == -1) return;

		MarginLayoutParams containerParams = (MarginLayoutParams) getLayoutParams();
		// 总父布局宽度
		containerWidth = WISERApp.getScreenWidth() - getPaddingLeft() - getPaddingRight() - containerParams.leftMargin - containerParams.rightMargin;

		// Item layout布局
		View itemLayout = mInflater.inflate(itemLayoutId, this, false);
		LayoutParams tvParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		tvParams.setMargins(0, 0, itemMargins, 0);

		// Item text布局
		int itemPadding = 0;// item Padding
		if (itemLayoutTextId != -1) {
			TextView itemText = itemLayout.findViewById(itemLayoutTextId);
			itemPadding = itemText.getCompoundPaddingLeft() + itemText.getCompoundPaddingRight();
			MarginLayoutParams itemTextParams = (MarginLayoutParams) itemText.getLayoutParams();
			itemPadding = itemPadding + itemTextParams.leftMargin + itemTextParams.rightMargin;
			paint.setTextSize(itemText.getTextSize());
		}
		// 每一行父布局
		LinearLayout layout = new LinearLayout(getContext());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, lineMargins, 0, 0);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.HORIZONTAL);
		addView(layout);

		// 一行剩余宽度
		int remainWidth = containerWidth;

		// 循环添加控件
		for (int i = 0; i < list.size(); ++i) {
			final String text = list.get(i);
			final float itemWidth = paint.measureText(text) + itemPadding;
			if (remainWidth > itemWidth) {// 可用的宽度大于文本控件宽度就添加文本布局
				addItemView(layout, tvParams, text, i);
			} else {// 否则添加新的一行
				resetTextViewMarginsRight(layout);
				layout = new LinearLayout(getContext());
				layout.setLayoutParams(params);
				layout.setOrientation(LinearLayout.HORIZONTAL);

				// 将前面那一个text加入新的一行
				addItemView(layout, tvParams, text, i);
				addView(layout);
				remainWidth = containerWidth;
			}
			remainWidth = (int) (remainWidth - itemWidth + 0.5f) - itemMargins;
		}
		resetTextViewMarginsRight(layout);
	}

	// 将每行最后一个text的MarginsRight去掉
	private void resetTextViewMarginsRight(ViewGroup viewGroup) {
		if (viewGroup.getChildCount() > 0) {
			final LinearLayout tempLayout = (LinearLayout) viewGroup.getChildAt(viewGroup.getChildCount() - 1);
			tempLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		}
	}

	// 添加文本布局以及内容展示
	private void addItemView(ViewGroup viewGroup, ViewGroup.LayoutParams params, String text, int position) {
		if (itemLayoutId == -1) return;
		final LinearLayout itemLayout = (LinearLayout) mInflater.inflate(itemLayoutId, null);
		if (itemLayoutTextId != -1) {
			final TextView tvItem = itemLayout.findViewById(itemLayoutTextId);
			tvItem.setText(text);
		}
		itemLayout.setOnClickListener(new ItemListener(position));
		viewGroup.addView(itemLayout, params);
	}

	private class ItemListener implements View.OnClickListener {

		private int position;

		ItemListener(int position) {
			this.position = position;
		}

		@Override public void onClick(View v) {
			if (onAlignItemListener != null) onAlignItemListener.onItemClick(v, position, list.get(position));
		}
	}

	public AlignTextLayoutView setOnAlignItemListener(OnAlignItemListener onAlignItemListener) {
		this.onAlignItemListener = onAlignItemListener;
		return this;
	}

	public interface OnAlignItemListener {

		void onItemClick(View view, int position, String text);

	}

}
