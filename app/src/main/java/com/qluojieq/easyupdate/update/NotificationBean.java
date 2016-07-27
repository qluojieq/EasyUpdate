package com.qluojieq.easyupdate.update;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.qluojieq.easyupdate.MainActivity;
import com.qluojieq.easyupdate.R;


@SuppressLint("ParcelCreator")
public class NotificationBean extends Notification
{
	private Context mContext;

	public NotificationBean(Context context, int icon, CharSequence tickerText, long when)
	{
		super(icon, tickerText, when);
		this.mContext = context;
		this.flags = Notification.FLAG_AUTO_CANCEL; // |= 
//		this.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews mRemoteView = new RemoteViews(mContext.getPackageName(), R.layout.remote_view);
		this.contentView = mRemoteView;

		Intent intent = new Intent(mContext, MainActivity.class); // 点击安装APK 未实现
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		this.contentIntent = pIntent;
	}

}
