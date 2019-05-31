package com.wiser.library.manager.ui.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * @author Wiser
 * @version 版本
 */
public interface IWISERNotificationManage {

	/**
	 * 初始化通知
	 */
	void initNotification(boolean isDefaultChannel);

	/**
	 * 设置通知Channel 只有大于等于26版本
	 */
	void setNotificationChannel();

	/**
	 * 获取NotificationChannel
	 * 
	 * @return
	 */
	NotificationChannel notificationChannel();

	/**
	 * 获取NotificationManager
	 * 
	 * @return
	 */
	NotificationManager notificationManage();

	/**
	 * 获取Builder
	 * 
	 * @return
	 */
	NotificationCompat.Builder getNotificationBuilder();

	/**
	 * 显示通知
	 * 
	 * @param notifyId
	 *            通知id
	 * @param ticker
	 *            //来信息第一次显示的消息
	 * @param title
	 *            消息标题
	 * @param content
	 *            消息内容
	 * @param bitIconBitmap
	 *            大图标
	 * @param smallIcon
	 *            小图标
	 * @param broadcastReceiveClazz
	 *            广播
	 */
	void showNotification(int notifyId, String ticker, String title, String content, Bitmap bitIconBitmap, int smallIcon, Class broadcastReceiveClazz);

	/**
	 * 显示通知
	 *
	 * @param notifyId
	 *            通知id
	 * @param ticker
	 *            //来信息第一次显示的消息
	 * @param title
	 *            消息标题
	 * @param content
	 *            消息内容
	 * @param bitIconBitmap
	 *            大图标
	 * @param smallIcon
	 *            小图标
	 * @param broadcastReceiveClazz
	 *            广播
	 */
	void showProgressNotification(int notifyId, String ticker, String title, String content, Bitmap bitIconBitmap, int smallIcon, int max, int progress, Class broadcastReceiveClazz);

	/**
	 * 显示自定义布局通知
	 * 
	 * @param notifyId
	 *            通知id
	 * @param smallIcon
	 *            小icon
	 * @param ticker
	 *            第一次显示信息
	 * @param remoteViews
	 *            自定义布局
	 * @param broadcastReceiveClazz
	 *            广播
	 */
	void showRemoteViewNotification(int notifyId, int smallIcon, String ticker, RemoteViews remoteViews, Class broadcastReceiveClazz);

}
