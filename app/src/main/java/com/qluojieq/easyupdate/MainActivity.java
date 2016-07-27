package com.qluojieq.easyupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.qluojieq.easyupdate.BaseUpdate.UpdateService;
import com.qluojieq.easyupdate.update.DownloadServices;

//主要实现在service中使用asynctask下载文件,并notification中使用remoteviews来显示进度条,下载完毕后更新
//version 1.0
// 存在的问题:
// ??现在存在的问题是实时更新notification的remoteviews的时候有卡顿现象??

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.test_btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.test_btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.test_btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.test_btn4);
        btn5 = (Button) findViewById(R.id.test_btn5);
        btn6 = (Button) findViewById(R.id.test_btn6);
        btn7 = (Button) findViewById(R.id.test_btn7);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_btn1:
                stepNotification();
                break;
            case R.id.test_btn2:
                startDownLoad();
                break;
            case R.id.test_btn3:
                Intent i = new Intent(this, UpdateService.class);
                this.startService(i);
                break;
            case R.id.test_btn4:
                break;
            case R.id.test_btn5:
                break;
            case R.id.test_btn6:
                break;
            case R.id.test_btn7:
                break;
        }

    }

    public void stepNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试标题")//设置通知栏标题
                .setContentText("测试内容") //设置通知栏显示内容
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//                .setNumber(10) //设置通知集合的数量
                .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        notificationManager.notify(1, mBuilder.build());
    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    public void startDownLoad(){
        Intent i = new Intent(this, DownloadServices.class);
        i.putExtra("url", "http://doc.imiaole.com/apk/shiliushuo.apk");
        i.putExtra("notifyId", 100);
        this.startService(i);
    }
}
