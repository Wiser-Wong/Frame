package com.wiser.library.manager;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHandlerExecutor implements Executor {

	@Inject WISERHandlerExecutor() {}

	private final Handler handler = new Handler(Looper.getMainLooper());

	@Override public void execute(@NonNull Runnable runnable) {
		handler.post(runnable);
	}
}