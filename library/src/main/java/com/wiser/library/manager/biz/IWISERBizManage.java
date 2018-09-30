package com.wiser.library.manager.biz;

import java.util.Map;

import com.wiser.library.base.IWISERBiz;
import com.wiser.library.model.WISERBizModel;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERBizManage {

	/**
	 * @param model
	 *            参数
	 */
	void attach(WISERBizModel model);

	/**
	 * @param model
	 *            参数
	 */
	void detach(WISERBizModel model);

	/**
	 * 销毁所有Biz
	 */
	void detachAll();

	/**
	 * @param bizClazz
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	<B extends IWISERBiz> boolean isExist(Class<B> bizClazz);

	/**
	 * @param bizClazz
	 *            参数
	 * @param <B>
	 *            参数
	 * @return 返回值
	 */
	<B extends IWISERBiz> B biz(Class<B> bizClazz);

	/**
	 * 获取biz列表
	 * 
	 * @return
	 */
	Map<Integer, WISERBizModel> bizList();

}
