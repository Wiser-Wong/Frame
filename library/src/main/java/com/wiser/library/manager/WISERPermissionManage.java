package com.wiser.library.manager;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * @author Wiser
 * 
 *         权限管理
 */
public class WISERPermissionManage implements IWISERPermissionManage {

	private Map<Integer, IWISERPermissionCallBack> hashMap = new ConcurrentHashMap<>();

	/**
	 * 请求权限
	 * 
	 * @param activity
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	@Override public void requestPermission(Activity activity, int request, String permission, IWISERPermissionCallBack ikmPermissionCallBack) {
		permission(activity, ikmPermissionCallBack, request, permission);
	}

	/**
	 * 请求权限
	 * 
	 * @param fragment
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	@Override public void requestPermission(Fragment fragment, int request, String permission, IWISERPermissionCallBack ikmPermissionCallBack) {
		permission(fragment, ikmPermissionCallBack, request, permission);
	}

	/**
	 * 获取权限成功
	 */
	@Override public void onPermission(int requestCode) {
		IWISERPermissionCallBack iwiserPermissionCallBack = hashMap.get(requestCode);
		if (iwiserPermissionCallBack != null) {
			hashMap.remove(requestCode);
			iwiserPermissionCallBack.hadPermissionResult();
		}
	}

	/**
	 * 获取权限
	 *
	 * @param activity
	 * @param iwiserPermissionCallBack
	 * @param request
	 * @param permission
	 */
	private void permission(final Activity activity, final IWISERPermissionCallBack iwiserPermissionCallBack, final int request, final String permission) {
		if (activity == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iwiserPermissionCallBack != null) {
				iwiserPermissionCallBack.hadPermissionResult();
			}
		} else {
			if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
					hashMap.put(request, iwiserPermissionCallBack);
					ActivityCompat.requestPermissions(activity, new String[] { permission }, request);
				} else {
					hashMap.put(request, iwiserPermissionCallBack);
					activity.requestPermissions(new String[] { permission }, request);
				}
			} else {
				if (iwiserPermissionCallBack != null) {
					iwiserPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}

	/**
	 * 获取权限
	 *
	 * @param fragment
	 * @param iwiserPermissionCallBack
	 * @param request
	 * @param permission
	 */
	private void permission(final Fragment fragment, final IWISERPermissionCallBack iwiserPermissionCallBack, final int request, final String permission) {
		if (fragment == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iwiserPermissionCallBack != null) {
				iwiserPermissionCallBack.hadPermissionResult();
			}
		} else {
			if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(fragment.getActivity()), permission) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), permission)) {
					hashMap.put(request, iwiserPermissionCallBack);
					ActivityCompat.requestPermissions(fragment.getActivity(), new String[] { permission }, request);
				} else {
					hashMap.put(request, iwiserPermissionCallBack);
					fragment.requestPermissions(new String[] { permission }, request);
				}
			} else {
				if (iwiserPermissionCallBack != null) {
					iwiserPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}

}
