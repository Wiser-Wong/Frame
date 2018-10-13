package com.wiser.library.view.photoview;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author Wiser
 *
 *         图片放大缩小预览
 */
public class PhotoView extends AppCompatImageView implements IPhotoView {

	private final PhotoViewAttache	mAttache;

	private ScaleType				mPendingScaleType;

	public PhotoView(Context context) {
		this(context, null);
	}

	public PhotoView(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}

	public PhotoView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		super.setScaleType(ScaleType.MATRIX);
		mAttache = new PhotoViewAttache(this);

		if (null != mPendingScaleType) {
			setScaleType(mPendingScaleType);
			mPendingScaleType = null;
		}
	}

	@Override public boolean canZoom() {
		return mAttache.canZoom();
	}

	@Override public RectF getDisplayRect() {
		return mAttache.getDisplayRect();
	}

	@Override public float getMinScale() {
		return mAttache.getMinScale();
	}

	@Override public float getMidScale() {
		return mAttache.getMidScale();
	}

	@Override public float getMaxScale() {
		return mAttache.getMaxScale();
	}

	@Override public float getScale() {
		return mAttache.getScale();
	}

	@Override public ScaleType getScaleType() {
		return mAttache.getScaleType();
	}

	@Override public void setAllowParentInterceptOnEdge(boolean allow) {
		mAttache.setAllowParentInterceptOnEdge(allow);
	}

	@Override public void setMinScale(float minScale) {
		mAttache.setMinScale(minScale);
	}

	@Override public void setMidScale(float midScale) {
		mAttache.setMidScale(midScale);
	}

	@Override public void setMaxScale(float maxScale) {
		mAttache.setMaxScale(maxScale);
	}

	@Override
	// setImageBitmap calls through to this method
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (null != mAttache) {
			mAttache.update();
		}
	}

	@Override public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (null != mAttache) {
			mAttache.update();
		}
	}

	@Override public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		if (null != mAttache) {
			mAttache.update();
		}
	}

	@Override public void setOnMatrixChangeListener(PhotoViewAttache.OnMatrixChangedListener listener) {
		mAttache.setOnMatrixChangeListener(listener);
	}

	@Override public void setOnLongClickListener(OnLongClickListener l) {
		mAttache.setOnLongClickListener(l);
	}

	@Override public void setOnPhotoTapListener(PhotoViewAttache.OnPhotoTapListener listener) {
		mAttache.setOnPhotoTapListener(listener);
	}

	@Override public void setOnViewTapListener(PhotoViewAttache.OnViewTapListener listener) {
		mAttache.setOnViewTapListener(listener);
	}

	@Override public void setScaleType(ScaleType scaleType) {
		if (null != mAttache) {
			mAttache.setScaleType(scaleType);
		} else {
			mPendingScaleType = scaleType;
		}
	}

	@Override public void setZoomable(boolean zoomable) {
		mAttache.setZoomable(zoomable);
	}

	@Override public void zoomTo(float scale, float focalX, float focalY) {
		mAttache.zoomTo(scale, focalX, focalY);
	}

	@Override protected void onDetachedFromWindow() {
		mAttache.cleanup();
		super.onDetachedFromWindow();
	}

}