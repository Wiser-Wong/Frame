package com.wiser.frame;

import com.wiser.library.helper.WISERHelper;

import android.app.Application;

public class WiserApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		WISERHelper.newBind().setWiserBind(new AtBind()).Inject(this, BuildConfig.DEBUG);
	}
}
