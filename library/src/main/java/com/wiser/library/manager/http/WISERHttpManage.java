package com.wiser.library.manager;

import com.wiser.library.helper.WISERHelper;

import javax.inject.Inject;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHttpManage {

	@Inject public WISERHttpManage() {}

	public <H> H http(Class<H> hClass) {
		return WISERHelper.retrofit().create(hClass);
	}

}
