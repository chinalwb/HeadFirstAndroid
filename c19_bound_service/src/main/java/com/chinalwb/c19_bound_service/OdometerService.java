package com.chinalwb.c19_bound_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class OdometerService extends Service {

    private final OdometerBinder odometerBinder = new OdometerBinder();
    private final Random random = new Random();
    private float currentDistance;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("XX", "Bound service created!");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return odometerBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("XX", "Bound service destroyed!");
    }

    public float getDistance() {
        currentDistance += random.nextFloat();
        return currentDistance;
    }

    public class OdometerBinder extends Binder {
        public OdometerService getOdometer() {
            return new OdometerService();
        }
    }
}
