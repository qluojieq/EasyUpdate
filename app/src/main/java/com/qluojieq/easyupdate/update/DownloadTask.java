package com.qluojieq.easyupdate.update;

import android.app.Notification;

public class DownloadTask
{
	private String url;
	private int notifyID;
	private Notification notification;

	public Notification getNotification()
    {
	    return notification;
    }

	public void setNotification(Notification notification)
    {
	    this.notification = notification;
    }

	public int getNotifyID()
    {
	    return notifyID;
    }

	public void setNotifyID(int notifyID)
    {
	    this.notifyID = notifyID;
    }

	public String getUrl()
    {
	    return url;
    }

	public void setUrl(String url)
    {
	    this.url = url;
    }

}
