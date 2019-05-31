package com.wiser.library.manager.ui.notification;

import com.wiser.library.helper.WISERHelper;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 通知
 * 
 * @author Wiser
 * @version 版本
 */
public class WISERNotificationManage implements IWISERNotificationManage {

	public WISERNotificationManage(boolean isDefaultChannel) {
		initNotification(isDefaultChannel);
	}

	@Override public void initNotification(boolean isDefaultChannel) {
		if (isDefaultChannel) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				setNotificationChannel();
			}
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.O) @Override public void setNotificationChannel() {
		NotificationChannel channel = notificationChannel();
		// 是否绕过请勿打扰模式
		channel.canBypassDnd();
		// 闪光灯
		channel.enableLights(true);
		// 锁屏显示通知
		channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET);
		// 闪关灯的灯光颜色
		channel.setLightColor(Color.RED);
		// 桌面launcher的消息角标
		channel.canShowBadge();
		// 是否允许震动
		channel.enableVibration(true);
		// 获取系统通知响铃声音的配置
		channel.getAudioAttributes();
		// 获取通知取到组
		channel.getGroup();
		// 设置可绕过 请勿打扰模式
		channel.setBypassDnd(true);
		// 设置震动模式
		channel.setVibrationPattern(new long[] { 100, 100, 200 });
		// 是否会有灯光
		channel.shouldShowLights();
		notificationManage().createNotificationChannel(channel);
	}

	@RequiresApi(api = Build.VERSION_CODES.O) @Override public NotificationChannel notificationChannel() {
		return new NotificationChannel("wiser_channel_id", "wiser_channel_name", NotificationManager.IMPORTANCE_DEFAULT);
	}

	@Override public NotificationManager notificationManage() {
		return (NotificationManager) WISERHelper.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override public NotificationCompat.Builder getNotificationBuilder() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(WISERHelper.getInstance(), "wiser_channel_id");
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
			builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
		}
		return builder;
	}

	@Override public void showNotification(int notifyId, String ticker, String title, String content, Bitmap bitIconBitmap, int smallIcon, Class broadcastReceiveClazz) {
		NotificationCompat.Builder builder = getNotificationBuilder().setContentTitle(title)// 设置标题
				// 设置内容
				.setContentText(content)
				// 设置大图标
				.setLargeIcon(bitIconBitmap)
				// 设置小图标
				.setSmallIcon(smallIcon)
				// 设置通知时间
				.setWhen(System.currentTimeMillis())
				// 首次进入时显示效果
				.setTicker(ticker)
				// 设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
				.setDefaults(Notification.DEFAULT_ALL)
				// 通知点击取消
				.setAutoCancel(true);
		if (broadcastReceiveClazz != null) {
			// 设置跳转广播
			builder.setContentIntent(PendingIntent.getBroadcast(WISERHelper.getInstance(), 0, new Intent(WISERHelper.getInstance(), broadcastReceiveClazz), PendingIntent.FLAG_UPDATE_CURRENT));
		}
		notificationManage().notify(notifyId, builder.build());
	}

	@Override public void showProgressNotification(int notifyId, String ticker, String title, String content, Bitmap bitIconBitmap, int smallIcon, int max, int progress, Class broadcastReceiveClazz) {
		NotificationCompat.Builder builder = getNotificationBuilder().setContentTitle(title)// 设置标题
				// 设置内容
				.setContentText(content)
				// 设置大图标
				.setLargeIcon(bitIconBitmap)
				// 设置小图标
				.setSmallIcon(smallIcon)
				// 设置通知时间
				.setWhen(System.currentTimeMillis())
				// 首次进入时显示效果
				.setTicker(ticker)
				// 设置进度
				.setProgress(max, progress, false)
				// 设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
				.setDefaults(Notification.DEFAULT_ALL)
				// 通知点击取消
				.setAutoCancel(true);
		if (broadcastReceiveClazz != null) {
			// 设置跳转广播
			builder.setContentIntent(PendingIntent.getBroadcast(WISERHelper.getInstance(), 0, new Intent(WISERHelper.getInstance(), broadcastReceiveClazz), PendingIntent.FLAG_UPDATE_CURRENT));
		}
		notificationManage().notify(notifyId, builder.build());
	}

	@Override public void showRemoteViewNotification(int notifyId, int smallIcon, String ticker, RemoteViews remoteViews, Class broadcastReceiveClazz) {
		NotificationCompat.Builder builder = getNotificationBuilder()
				// 小icon
				.setSmallIcon(smallIcon)
				// 设置自定义布局
				.setContent(remoteViews)
				// 首次进入时显示效果
				.setTicker(ticker)
				// 设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为声音
				.setDefaults(Notification.DEFAULT_ALL)
				// 通知点击取消
				.setAutoCancel(true);
		if (broadcastReceiveClazz != null) {
			// 设置跳转广播
			builder.setContentIntent(PendingIntent.getBroadcast(WISERHelper.getInstance(), 0, new Intent(WISERHelper.getInstance(), broadcastReceiveClazz), PendingIntent.FLAG_UPDATE_CURRENT));
		}
		notificationManage().notify(notifyId, builder.build());
	}

}
