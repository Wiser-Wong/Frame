package com.wiser.frame;

import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.zxing.WISERScanActivity;
import com.wiser.library.zxing.view.ViewfinderView;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import butterknife.BindView;
import butterknife.OnClick;

public class ScanActivity extends WISERScanActivity<ScanBiz> {

	@BindView(R.id.surface_view) SurfaceView		surfaceView;

	@BindView(R.id.viewfinder_view) ViewfinderView	viewfinderView;

	@Override public WISERBuilder buildScan(WISERBuilder builder) {
		builder.layoutId(R.layout.activity_scan);
		return builder;
	}

	@Override public void initDataScan(Intent intent) {
		initScan(viewfinderView, surfaceView);
	}

	@Override public void scanSuccess(String json) {
		WISERHelper.toast().show(json);
	}

	@Override public void scanFail() {
		WISERHelper.toast().show("扫描失败");
	}

	@OnClick(value = { R.id.scan_back, R.id.scan_photo, R.id.scan_flash }) public void onClickView(View view) {
		switch (view.getId()) {
			case R.id.scan_back:
				this.finish();
				break;
			case R.id.scan_photo:
				photo();
				break;
			case R.id.scan_flash:
				flash();
				break;
		}
	}

	@Override public void applyPermissionFail(int requestCode) {
		super.applyPermissionFail(requestCode);
		WISERHelper.toast().show("请到设置页面进行授权");
	}

	@Override public void applyPermissionSuccess(int requestCode) {
		super.applyPermissionSuccess(requestCode);
	}
}
