package com.chinalwb.c19_bound_service;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    private Iodometer odometerService;
    private boolean bound = false;


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            odometerService = Iodometer.Stub.asInterface(service);
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            Log.e("XX", "On service disconnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("bound.service.remote");
        intent.setPackage("com.chinalwb.c19_bound_service");
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);

        displayDistance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void displayDistance()  {
        final TextView distanceView = findViewById(R.id.distance);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                float distance = 0.0f;
                if (bound && odometerService != null) {
                    try {
                        distance = odometerService.getDistance();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                String distanceStr = String.format(Locale.getDefault(), "%1$,.2f miles", distance);
                distanceView.setText(distanceStr);
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("XX", "Activity2 onDestroy bound == " + bound);
        if (bound) {
            unbindService(serviceConnection);
            Log.e("XX", "unbind finishes?");
            bound = false;
        }
        Log.e("XX", "Activity2 destroy!");
    }
}
