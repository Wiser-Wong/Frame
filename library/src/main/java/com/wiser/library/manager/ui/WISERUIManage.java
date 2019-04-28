package com.wiser.library.manager.ui;

import javax.inject.Inject;

import com.wiser.library.manager.ui.notification.IWISERNotificationManage;
import com.wiser.library.manager.ui.notification.WISERNotificationManage;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERUIManage {

	private WISERNotificationManage notificationManage;

	@Inject public WISERUIManage() {}

	public IWISERNotificationManage notification(boolean isDefaultChannel) {
		if (notificationManage == null) synchronized (WISERNotificationManage.class) {
			if (notificationManage == null) notificationManage = new WISERNotificationManage(isDefaultChannel);
		}
		return notificationManage;
	}
}
