package com.wiser.library.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * 
 *         代理类
 */
public class WISERProxy implements InvocationHandler {

	private Object proxy;

	public WISERProxy(Object realObj) {
		this.proxy = realObj;
	}

	@Override public Object invoke(Object o, Method method, Object[] objects) throws Exception {
		method.setAccessible(true);
		return WISERHelper.methodManage().methodInvoke(proxy, method, objects);
	}

}
