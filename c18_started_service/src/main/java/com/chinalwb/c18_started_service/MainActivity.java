package com.chinalwb.c18_started_service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Button button = findViewById(R.id.start_service);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("XX", "Clicked");
                Intent intent = new Intent(MainActivity.this, LogService.class);
                intent.putExtra(LogService.EXTRA_MESSAGE, "Hello Service");
                startService(intent);
            }
        });

        Button music = findViewById(R.id.music_service);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MusicService.class);
                intent.putExtra(MusicService.EXTRA_MID, R.raw.meet);
                startService(intent);
            }
        });
    }
}
