package com.wiser.library.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.wiser.library.base.IWISERBiz;
import com.wiser.library.base.WISERBiz;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.proxy.WISERProxy;
import com.wiser.library.util.WISERClass;
import com.wiser.library.util.WISERGenericSuperclass;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERBizModel<B extends IWISERBiz> {

	private Object	bizObj;		// 代理对象

	private int		key;		// model key

	private Object	o;			// 绑定类this

	private Class	service;	// 泛型类

	private B		b;			// 代理对象泛型

	private Object	impl;		// 实现类

	public WISERBizModel(Object o) {
		this.o = o;
		if (o != null) {
			this.service = WISERClass.getClassGenricType(o.getClass(), 0);
			if (this.service != null) {
				if (this.service.isInterface()) this.impl = WISERClass.getImplClass(service);
				else this.impl = service;
			}
		}
		this.bizObj = biz();
		key = this.hashCode();
	}

	/**
	 * 泛型对象
	 * 
	 * @return
	 */
	public B biz() {
		if (b == null) {
			try {
				if (service != null) {
					if (service.isInterface()) {
						InvocationHandler handler = new WISERProxy(impl);
						b = (B) Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class[] { service }, handler);
					} else {
						b = (B) WISERGenericSuperclass.getActualTypeArgument(o.getClass()).newInstance();
					}
				} else {
					b = (B) WISERGenericSuperclass.getActualTypeArgument(o.getClass()).newInstance();
				}
			} catch (Exception e) {
				if (o != null) WISERHelper.log().e(o.getClass().getName() + "该界面没有业务类扩展");
			}
		}
		return b;
	}

	public Object getBizObj() {
		return bizObj;
	}

	public Class getService() {
		return service;
	}

	public int getKey() {
		return key;
	}

	public void clearAll() {
		key = 0;
		b = null;
		o = null;
		if (impl != null) {
			if (impl instanceof WISERBiz) ((WISERBiz) impl).detach();
		}
		if (bizObj != null) {
			if (bizObj instanceof WISERBiz) ((WISERBiz) bizObj).detach();
		}
		impl = null;
		bizObj = null;
		service = null;
	}
}
