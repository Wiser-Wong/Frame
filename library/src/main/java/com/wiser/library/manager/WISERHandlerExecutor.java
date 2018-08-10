package com.wiser.library.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHandlerExecutor implements Executor {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(Runnable runnable) {
        handler.post(runnable);
    }
}