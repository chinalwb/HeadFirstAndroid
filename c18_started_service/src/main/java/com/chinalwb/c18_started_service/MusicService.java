package com.chinalwb.c18_started_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {

    public static final String EXTRA_MID = "MID";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            playMusic(R.raw.game);
            return START_NOT_STICKY;
        }
        int mid = intent.getExtras().getInt(EXTRA_MID);
        playMusic(mid);
        return START_STICKY; // super.onStartCommand(intent, flags, startId);
    }

    private void playMusic(int mid) {
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), mid);
        mediaPlayer.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
