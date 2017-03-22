package com.baha.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.baha.BahaApplication;
import com.baha.R;
import com.baha.demo.view.MainActivity;

/**
 * Created by Yann on 15/02/2017.
 */

public class NotificationUtil {

	public static void senNotification(Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent intent = new Intent(context, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		int id = Resources.getSystem().getIdentifier("status_bar_latest_event_content", "id", "android");
		Notification notification;
		if (id != 0) {
			notification = new NotificationCompat.Builder(context).setContentTitle("这是一个自定义的notifications")
					.setContentText("能保留系统Notification中title和subtitle的style").setContentIntent(pendingIntent)
					.setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setShowWhen(false).build();
			RemoteViews contentView = notification.contentView.clone();
			notification.contentView.removeAllViews(id);

			RemoteViews customParentView = new RemoteViews(context.getPackageName(),
					R.layout.layout_custom_notification);
			customParentView.addView(R.id.custom_notification_item_parent, contentView);

			RemoteViews customChildView = new RemoteViews(context.getPackageName(),
					R.layout.layout_custom_notification_child);
			customChildView.setTextViewText(R.id.child, "Child");
			customParentView.addView(R.id.custom_notification_item_parent, customChildView);

			notification.contentView.addView(id, customParentView);
		} else {
			notification = new NotificationCompat.Builder(context).setContentTitle("这是一个自定义的notifications")
					.setContentText("能保留系统Notification中title和subtitle的style").setContentIntent(pendingIntent)
					.setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher)
					.setLargeIcon(BitmapFactory.decodeResource(BahaApplication.RESOURCES, R.mipmap.ic_launcher))
					.build();
		}
		notificationManager.notify(1, notification);
	}
}
