package com.wiser.frame;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndexAdapter extends WISERRVAdapter<IndexModel, WISERHolder> {

	public IndexAdapter(WISERActivity mWiserActivity) {
		super(mWiserActivity);
	}

	public IndexAdapter(WISERFragment fragment){
		super(fragment);
	}

	@Override public WISERHolder newViewHolder(ViewGroup viewGroup, int type) {
		return new IndexHolder(inflate(viewGroup, R.layout.item));
	}

	public class IndexHolder extends WISERHolder<IndexModel> {

		TextView textView;

		public IndexHolder(@NonNull View itemView) {
			super(itemView);
			textView = itemView.findViewById(R.id.tv_age);
		}

		@Override public void bindData(IndexModel indexModel, int position) {
			if (indexModel == null) return;
			textView.setText(indexModel.age);
		}
	}
}
