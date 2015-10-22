package com.alandy.servicebestpratice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.util.Log;

import com.alandy.servicebestpratice.servicetest.MyAIDLService;

/**
 * Created by AlandyFeng on 2015/10/22.
 */
public class MyService extends Service {

    private static final String TAG = "MyService";
    //private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        Log.d(TAG, "process id is " + android.os.Process.myPid());

        /**
         * Service其实是运行在主线程里的，如果直接在Service中处理一些耗时的逻辑，就会导致程序ANR。
         */
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        /**
         * 创建一个前台Service
         */
        Notification notification = new Notification(R.mipmap.ic_launcher, "有通知来", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容", pendingIntent);
        startForeground(1, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                //开始执行后台任务
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder{
        public void startDownload(){
            Log.d(TAG, "startDownload() executed");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //执行具体的下载任务
                }
            }).start();
        }
    }

    MyAIDLService.Stub mBinder = new MyAIDLService.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if (str != null){
                return str.toUpperCase();
            }
            return null;
        }
    };
}
