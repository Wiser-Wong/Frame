package com.wiser.library.manager.biz;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import com.wiser.library.base.IWISERBiz;
import com.wiser.library.model.WISERBizModel;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERBizManage implements IWISERBizManage {

	private final Map<Integer, WISERBizModel> bizs;

	@Inject public WISERBizManage() {
		bizs = new ConcurrentHashMap<>();
	}

	@Override public void attach(WISERBizModel model) {
		synchronized (bizs) {
			if (model == null) return;

			bizs.put(model.getKey(), model);
		}
	}

	@Override public void detach(WISERBizModel model) {
		synchronized (bizs) {
			if (model == null) return;
			bizs.remove(model.getKey());
			model.clearAll();
		}
	}

	@Override public void detachAll() {
		bizs.clear();
	}

	@Override public <B extends IWISERBiz> boolean isExist(Class<B> bizClazz) {
		if (bizClazz == null) return false;
		if (bizs.size() == 0) return false;
		Set<Map.Entry<Integer, WISERBizModel>> entries = bizs.entrySet();
		for (Map.Entry<Integer, WISERBizModel> entry : entries) {
			WISERBizModel model = entry.getValue();
			if (model != null) {
				if (bizClazz.equals(model.getService())) return true;
			}
		}
		return false;
	}

	@Override public <B extends IWISERBiz> B biz(Class<B> bizClazz) {
		if (bizClazz == null) return null;
		if (bizs.size() == 0) return null;
		Set<Map.Entry<Integer, WISERBizModel>> entries = bizs.entrySet();
		for (Map.Entry<Integer, WISERBizModel> entry : entries) {
			WISERBizModel model = entry.getValue();
			if (model != null) {
				if (bizClazz.equals(model.getService())) return (B) model.getBizObj();
			}
		}
		return null;
	}

	@Override public Map<Integer, WISERBizModel> bizList() {
		return bizs;
	}
}
