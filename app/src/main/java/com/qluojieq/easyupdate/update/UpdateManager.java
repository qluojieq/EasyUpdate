package com.qluojieq.easyupdate.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.Toast;
/**
 * Created by shiliushuo-1 on 16/5/30.
 * 更新帮助类
 */
public class UpdateManager {

    private Dialog downloadDialog;
    private String currentVersion;//当前版本
    private boolean isForceUpdate;//是否强制更新
    private boolean isUpdate;
    Context context;




    //强制更新
    public void forceUpdate() {
        Toast.makeText(context, "强制更新", Toast.LENGTH_SHORT).show();
        Dialog dialog = new AlertDialog.Builder(context).setIcon(
                android.R.drawable.btn_star).setTitle("强制版本升级").setMessage(
                "马上下载安装更新吗,否则无法使用?").setPositiveButton("立即安装",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(keylistener);
        dialog.show();
    }
    //禁掉
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;
    //普通更新
    public void normalUpdate() {
        Dialog dialog = new AlertDialog.Builder(context).setIcon(
                android.R.drawable.btn_star).setTitle("版本升级").setMessage(
                "马上下载安装更新吗?").setPositiveButton("立即安装",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                }).setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        }).create();
        dialog.show();
    }

    public void startDownloadService(int notifyId, String url) {
        Intent i = new Intent(context, DownloadServices.class);
        i.putExtra("url", url);
        i.putExtra("notifyId", notifyId);
        context.startService(i);
    }

    //版本号码比较

    /**
     * @param current 当前版本号
     * @param force   更新版本号
     * @return true 更新,false 不更新
     */
    public boolean compareVersion(String current, String force) {
        int currentItem[] = praseVersion(current);
        int forceItem[] = praseVersion(force);
        for (int i = 0; i < Math.min(forceItem.length, currentItem.length); i++) {
            if (forceItem[i] > currentItem[i]) {
                return true;
            } else {

            }
        }

        return false;
    }

    private int[] praseVersion(String version) {
        int[] versions = new int[4];
        String strVersions[] = version.split("\\.");
        for (int i = 0; i < 4; i++) {
            if (i >= strVersions.length) {
                versions[i] = 0;
            } else {
                versions[i] = Integer.parseInt(strVersions[i]);
            }
        }
        return versions;
    }



}
