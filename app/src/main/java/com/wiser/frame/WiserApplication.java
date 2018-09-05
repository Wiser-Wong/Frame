package com.wiser.frame;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERCrashHandler;
import com.wiser.library.util.WISERFile;

import android.annotation.SuppressLint;
import android.app.Application;

public class WiserApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		WISERHelper.newBind().setWiserBind(new AtBind()).setCrashHandler(this, "frame/error").Inject(this, BuildConfig.DEBUG);
	}
}
