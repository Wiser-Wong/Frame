package com.wiser.frame;

import com.bumptech.glide.Glide;
import com.wiser.library.adapter.WISERHolder;
import com.wiser.library.adapter.WISERRVAdapter;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERFragment;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

public class IndexAdapter extends WISERRVAdapter<IndexModel, WISERHolder> {

	public IndexAdapter(WISERActivity mWiserActivity) {
		super(mWiserActivity);
	}

	public IndexAdapter(WISERFragment fragment) {
		super(fragment);
	}

	@Override public WISERHolder newViewHolder(ViewGroup viewGroup, int type) {
		return new IndexHolder(inflate(viewGroup, R.layout.item));
	}

	public class IndexHolder extends WISERHolder<IndexModel> {

		@BindView(R.id.tv_age) TextView		textView;

		@BindView(R.id.iv_photo) ImageView	imageView;

		public IndexHolder(@NonNull View itemView) {
			super(itemView);
		}

		@Override public void bindData(final IndexModel indexModel, final int position) {
			if (indexModel == null) return;
			textView.setText(indexModel.age);
			Glide.with(activity()).load(indexModel.photoUrl).into(imageView);
		}
	}
}
