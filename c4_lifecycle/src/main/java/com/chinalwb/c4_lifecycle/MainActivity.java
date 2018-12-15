package com.chinalwb.c4_lifecycle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Test activity for life cycles
 */
public class MainActivity extends AppCompatActivity {

    private TextView timeView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("XX", "onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            Log.e("XX", "was running == " + wasRunning);
        }

        init();
        runTimer();
        startActivity();
    }

    private void init() {
        timeView = this.findViewById(R.id.time_text);
        startButton = this.findViewById(R.id.start_button);
        stopButton = this.findViewById(R.id.stop_button);
        resetButton = this.findViewById(R.id.reset_button);
    }

    private void runTimer() {
        updateTimeView();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (running) {
                    updateTimeView();
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void updateTimeView() {
        int hour = seconds / 60 / 60;
        int minute = (seconds / 60) % 60;
        int second = seconds % 60;
        String time = String.format("%d:%02d:%02d", hour, minute, second);
        timeView.setText(time);
    }


    public void start(View view) {
        running = true;
    }

    public void stop(View view) {
        running = false;
    }

    public void reset(View view) {
        running = false;
        seconds = 0;
        updateTimeView();
    }

    public void start2nd(View view) {
        startActivity();
    }

    private void startActivity() {
        Intent intent = new Intent(this, C4_SecondActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("XX", "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("XX", "onStart");
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("XX", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("XX", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("XX", "onStop");
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running", running);
//        outState.putBoolean("wasRunning", wasRunning);
        outState.putInt("seconds", seconds);
        Log.e("XX", "onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("XX", "onDestroy");
    }
}
