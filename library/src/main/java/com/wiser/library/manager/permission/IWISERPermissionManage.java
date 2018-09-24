package com.wiser.library.manager.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * @author Wiser
 *
 *         权限管理
 */
public interface IWISERPermissionManage {

	/**
	 * 请求权限
	 * 
	 * @param activity
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	void requestPermission(Activity activity, int request, String permission, IWISERPermissionCallBack ikmPermissionCallBack);

	/**
	 * 请求权限
	 * 
	 * @param fragment
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	void requestPermission(Fragment fragment, int request, String permission, IWISERPermissionCallBack ikmPermissionCallBack);

	/**
	 * 获取权限成功
	 */
	void onPermission(int requestCode);

}
