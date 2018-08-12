package com.wiser.library.manager;


import com.wiser.library.base.WISERBiz;
import com.wiser.library.model.WISERStructureModel;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERBizManage {

    /**
     * @param model 参数
     */
    void attach(WISERStructureModel model);


    /**
     * @param model 参数
     */
    void detach(WISERStructureModel model);

    /**
     * @param bizClazz 参数
     * @param <B>      参数
     * @return 返回值
     */
    <B extends WISERBiz> boolean isExist(Class<B> bizClazz);

    /**
     * @param bizClazz 参数
     * @param <B>      参数
     * @return 返回值
     */
    <B extends WISERBiz> B biz(Class<B> bizClazz);

}
