package com.wiser.library.core;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author sky
 * @version 版本
 */
public class WISERWorkExecutorService extends ThreadPoolExecutor {

    public WISERWorkExecutorService() {
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }
}