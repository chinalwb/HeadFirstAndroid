package com.chinalwb.c19_bound_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Random;

public class RemoteOdometerService extends Service {
    private Random random = new Random();
    private float total;
    public RemoteOdometerService() {
    }

    private IBinder binder = new Iodometer.Stub() {
        @Override
        public float getDistance() throws RemoteException {
            total += random.nextFloat();
            return total;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("XX", "Remove service On Create!");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("XX", "Remote service on destroy!!");
    }
}
