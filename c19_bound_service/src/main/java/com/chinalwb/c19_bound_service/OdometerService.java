package com.chinalwb.c19_bound_service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.Random;

public class OdometerService extends Service {

    private final static String PERMISSION_STRING = Manifest.permission.ACCESS_FINE_LOCATION;

    private final OdometerBinder odometerBinder = new OdometerBinder();
    private final Random random = new Random();
    private float currentDistance;

    private Location lastLocation;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("XX", "Bound service created!");

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                float distance = location.distanceTo(lastLocation);
                currentDistance += distance;
                Log.e("XX", OdometerService.this + " >> current distance in location listener == " + currentDistance);
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
            Log.e("XX", "request update location");
            String provider = locationManager.getBestProvider(new Criteria(), true);
            locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);
        }
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
        if (locationManager != null && locationListener != null) {
            if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING) == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(locationListener);
            }
            locationManager = null;
            locationListener = null;
        }
    }

    public float getDistance() {
        // currentDistance += random.nextFloat();
        Log.e("XX", OdometerService.this + ">> get distance == " + this.currentDistance);
        return currentDistance;
    }

    public class OdometerBinder extends Binder {
        public OdometerService getOdometer() {
            return OdometerService.this;
        }
    }
}
