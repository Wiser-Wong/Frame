package com.wiser.library.manager.ui;

import javax.inject.Inject;

import com.wiser.library.helper.WISERHelper;
import com.wiser.library.loading.LoadingDialogFragment;
import com.wiser.library.manager.ui.notification.IWISERNotificationManage;
import com.wiser.library.manager.ui.notification.WISERNotificationManage;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERUIManage {

	private WISERNotificationManage notificationManage;

	@Inject public WISERUIManage() {}

	/**
	 * 通知
	 * 
	 * @param isDefaultChannel
	 * @return
	 */
	public IWISERNotificationManage notification(boolean... isDefaultChannel) {
		if (notificationManage == null) synchronized (WISERNotificationManage.class) {
			if (notificationManage == null) notificationManage = new WISERNotificationManage((isDefaultChannel == null || isDefaultChannel.length <= 0) || isDefaultChannel[0]);
		}
		return notificationManage;
	}

	/**
	 * 显示loading
	 *
	 * @param isClose
	 *            是否可以关闭弹窗
	 */
	public void showLoading(boolean... isClose) {
		if (isClose.length > 0) {
			LoadingDialogFragment.showLoadingDialog(isClose[0]);
		} else {
			LoadingDialogFragment.showLoadingDialog(false);
		}
	}

	/**
	 * 隐藏loading
	 */
	public void hideLoading() {
		LoadingDialogFragment loadingDialogFragment = WISERHelper.display().findFragment(LoadingDialogFragment.class.getName());
		if (loadingDialogFragment != null) loadingDialogFragment.dismiss();
	}

}
