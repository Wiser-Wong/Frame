package com.wiser.library.manager;

import com.wiser.library.helper.WISERHelper;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERHttpManage {

	public <H> H http(Class<H> hClass) {
		return WISERHelper.retrofit().create(hClass);
	}

}
