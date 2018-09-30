package com.wiser.library.manager.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERSingleWorkExecutorService extends ThreadPoolExecutor {

	public WISERSingleWorkExecutorService() {
		super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}
}