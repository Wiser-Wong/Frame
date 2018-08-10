package com.wiser.library.manager;


import com.wiser.library.base.WISERBiz;
import com.wiser.library.model.WISERStructureModel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wiser
 * @version 版本
 */
@SuppressWarnings("unchecked")
public class WISERStructureManage implements IWISERStructureManage {

    private final Map<Integer, Object> bizs;

    public WISERStructureManage() {
        bizs = new ConcurrentHashMap<>();
    }

    @Override
    public void attach(WISERStructureModel model) {
        synchronized (bizs) {
            if (model == null) return;
            if (model.getBizClass() == null) return;

            bizs.put(model.getKey(), model.getBizClass());
        }
    }

    @Override
    public void detach(WISERStructureModel model) {
        synchronized (bizs) {
            if (model == null) return;
            if (model.getBizClass() == null) return;
            bizs.remove(model.getKey());
            model.clearAll();
        }
    }

    @Override
    public <B extends WISERBiz> boolean isExist(Class<B> bizClazz) {
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
}
