package com.example.android.receiver;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.android.MainActivity;
import com.example.android.R;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateWidgetService extends Service {

    private Timer timer;
    private TimerTask task;

    public UpdateWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                ComponentName componentName = new ComponentName(UpdateWidgetService.this, MyWidget.class);
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.appwidget_view);

                //设置Widget中Textview的显示内容
                remoteViews.setTextViewText(R.id.tv_runprocessnumber, "设置内容:123");
                remoteViews.setTextViewText(R.id.tv_avalimem, "拉活或者自启动");

                //点击widget显示信息部分，跳到程序管理页面
                Intent startActivityIntent = new Intent(UpdateWidgetService.this, MainActivity.class);
                startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent processInfoIntent = PendingIntent.getActivity(UpdateWidgetService.this, 0, startActivityIntent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
                remoteViews.setOnClickPendingIntent(R.id.ll_processinfo, processInfoIntent);

                //由AppWidgetManager处理Wiget。
                AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
                awm.updateAppWidget(componentName, remoteViews);
            }
        };
        timer.schedule(task, 0, 3000);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        task.cancel();
        timer = null;
        task = null;
    }
}