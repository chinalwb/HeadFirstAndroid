package com.chinalwb.c18_started_service;

import android.app.IntentService;
import android.content.Intent;
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
        synchronized (this) {
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String msg = intent.getStringExtra(EXTRA_MESSAGE);
        Log.e("XX", msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("XX", "LogService destroyed");
    }
}
