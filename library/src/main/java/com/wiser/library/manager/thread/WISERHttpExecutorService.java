package com.wiser.library.manager.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHttpExecutorService extends ThreadPoolExecutor {

    private static final int DEFAULT_THREAD_COUNT = 5;

    public WISERHttpExecutorService() {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }
}