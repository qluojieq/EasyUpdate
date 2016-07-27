package com.qluojieq.easyupdate.BaseUpdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViews.RemoteView;

import com.qluojieq.easyupdate.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by shiliushuo-1 on 16/7/27.
 */

public class UpdateService extends Service {

    NotificationManager notificationManager;
    RemoteViews remoteView;
    NotificationCompat.Builder builder;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        initNotification();
        DownLoadAysncTack downLoadAysncTack = new DownLoadAysncTack();
        downLoadAysncTack.execute("http://doc.imiaole.com/apk/shiliushuo.apk");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;


    }

    public void initNotification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder
//                .setContentTitle("测试标题")//设置通知栏标题
//                .setContentText("测试内容") //设置通知栏显示内容
//                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//                .setNumber(10) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON

        remoteView = new RemoteViews(this.getPackageName(), R.layout.remote_view);
//        remoteView.
        builder.setContent(remoteView);
        notificationManager.notify(10, builder.build());
    }

    private class DownLoadAysncTack extends AsyncTask<String, Integer, Boolean> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            initNotification();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            UpdateService.this.stopSelf();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            remoteView.setProgressBar(R.id.pb, 100, values[0], false);
            remoteView.setTextViewText(R.id.tv, "已下载" + values[0] + "%");
            builder.setContent(remoteView);
            notificationManager.notify(10,builder.build());

            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            //下载文件
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5 * 1000);
                int totalLength = connection.getContentLength();
                String fileName = strings[0].substring(strings[0].lastIndexOf("/") + 1);
                File apkFile = new File(Environment.getExternalStorageDirectory(), fileName);
                if (totalLength < 0) {
                    return false;
                }
                int progressNum = 0;
                InputStream inputStream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(apkFile);
                BufferedInputStream inputStreamBuffer = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStreamBuffer.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                    progressNum += len;
                    //刷新太频繁,会死的!
                   if(progressNum%(totalLength/100)<1024) {
                        publishProgress((progressNum*100/totalLength));
                       Log.i("doinbackground",""+ ( progressNum*100/totalLength));
                   }

                }
                outputStream.flush();
                inputStream.close();
                inputStreamBuffer.close();

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }
}
