package com.wiser.library.manager.method;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.inject.Inject;

import com.wiser.library.annotation.thread.Background;
import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 *
 *         方法代理
 */
public class WISERMethodManage {

	@Inject public WISERMethodManage() {}

	/**
	 * 通过注解Background 类型执行对应的线程方法
	 *
	 * @param o
	 * @param method
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Object methodInvoke(final Object o, final Method method, final Object[] objects) throws Exception {
		if (method.isAnnotationPresent(Background.class)) {
			Background background = method.getAnnotation(Background.class);
			if (background != null) {
				Future<Object> futureTask = null;
				switch (background.value()) {
					case HTTP:
						futureTask = WISERHelper.threadPoolManage().getHttpExecutorService().submit(new Callable<Object>() {

							@Override public Object call() throws Exception {
								return method.invoke(o, objects);
							}
						});
						break;
					case WORK:
						futureTask = WISERHelper.threadPoolManage().getWorkExecutorService().submit(new Callable<Object>() {

							@Override public Object call() throws Exception {
								return method.invoke(o, objects);
							}
						});
						break;
					case SINGLE:
						futureTask = WISERHelper.threadPoolManage().getSingleWorkExecutorService().submit(new Callable<Object>() {

							@Override public Object call() throws Exception {
								return method.invoke(o, objects);
							}
						});
						break;
				}
				return futureTask.get();
			} else {
				return method.invoke(o, objects);
			}
		} else {
			return method.invoke(o, objects);
		}
	}

}
