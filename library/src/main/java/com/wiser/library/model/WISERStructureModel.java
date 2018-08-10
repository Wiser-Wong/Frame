package com.wiser.library.model;


/**
 * @author Wiser
 * @version 版本
 */
public class WISERStructureModel {

    private Object bizClass;

    private int key;

    public WISERStructureModel(Object bizClass) {
        key = bizClass.hashCode();
        this.bizClass = bizClass;
    }

    public Object getBizClass() {
        return bizClass;
    }

    public int getKey() {
        return key;
    }

    public void clearAll() {
        bizClass = null;
    }
}
