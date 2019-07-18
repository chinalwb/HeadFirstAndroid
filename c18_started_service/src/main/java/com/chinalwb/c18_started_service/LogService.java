package com.chinalwb.c18_started_service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class LogService extends IntentService {

    public static final String EXTRA_MESSAGE = "message";

    public LogService() {
        super("LogService");
        setIntentRedelivery(true);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("XX", "LogService started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra(EXTRA_MESSAGE);

        // foreground service
        startForeground(1, getNotification());
        int c = 0;
        synchronized (this) {
            try {
                while (true) {
                    if (c > 10) {
                        Log.w("XX", "c = 10, stop!!");
                        stopForeground(true);
                        stopSelf();
                        break;
                    }
                    Thread.sleep(1 * 1000);
                    Log.e("XX", msg + " : count : " + ++c);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Notification getNotification() {
        NotificationCompat.Builder b=new NotificationCompat.Builder(this);

        b.setOngoing(true)
                .setContentTitle("title")
                .setContentText("text")
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setTicker("ticker");

        return(b.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("XX", "LogService destroyed");
    }
}
