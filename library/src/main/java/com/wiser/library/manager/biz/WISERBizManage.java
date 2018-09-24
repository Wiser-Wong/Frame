package com.wiser.library.manager.biz;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import com.wiser.library.base.WISERBiz;
import com.wiser.library.model.WISERBizModel;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERBizManage implements IWISERBizManage {

	private final Map<Integer, Object> bizs;

	@Inject public WISERBizManage() {
		bizs = new ConcurrentHashMap<>();
	}

	@Override public void attach(WISERBizModel model) {
		synchronized (bizs) {
			if (model == null) return;
			if (model.getBizClass() == null) return;

			bizs.put(model.getKey(), model.getBizClass());
		}
	}

	@Override public void detach(WISERBizModel model) {
		synchronized (bizs) {
			if (model == null) return;
			if (model.getBizClass() == null) return;
			bizs.remove(model.getKey());
			model.clearAll();
		}
	}

	@Override public void detachAll() {
		bizs.clear();
	}

	@Override public <B extends WISERBiz> boolean isExist(Class<B> bizClazz) {
		if (bizClazz == null) return false;
		if (bizs.size() == 0) return false;
		Set<Map.Entry<Integer, Object>> entries = bizs.entrySet();
		for (Map.Entry<Integer, Object> entry : entries) {
			Object classB = entry.getValue();
			if (classB != null) {
				if (bizClazz.equals(classB.getClass())) return true;
			}
		}
		return false;
	}

	@Override public <B extends WISERBiz> B biz(Class<B> bizClazz) {
		if (bizClazz == null) return null;
		if (bizs.size() == 0) return null;
		Set<Map.Entry<Integer, Object>> entries = bizs.entrySet();
		for (Map.Entry<Integer, Object> entry : entries) {
			Object classB = entry.getValue();
			if (classB != null) {
				if (bizClazz.equals(classB.getClass())) return (B) classB;
			}
		}
		return null;
	}

	@Override public Map<Integer, Object> bizList() {
		return bizs;
	}
}
