package com.chinalwb.c9_fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private TextView timeView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private int seconds = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    private Handler handler = new Handler();

    public StopwatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("XX", "Fragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("XX", "Fragment onCreate");
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            Log.e("XX", "was running == " + wasRunning);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("XX", "Fragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        init(view);
        runTimer();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("XX", "Fragment onActivityCreated");
    }

    private void init(View view) {
        timeView = view.findViewById(R.id.time_text);
        startButton = view.findViewById(R.id.start_button);
        stopButton = view.findViewById(R.id.stop_button);
        resetButton = view.findViewById(R.id.reset_button);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
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

    @Override
    public void onStart() {
        super.onStart();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
        outState.putInt("seconds", seconds);
        Log.e("XX", "onSaveInstanceState");
    }

    @Override
    public void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.start_button:
                clickStart();
                break;
            case R.id.stop_button:
                clickStop();
                break;
            case R.id.reset_button:
                clickReset();
                break;
        }
    }

    private void clickStart() {
        running = true;
    }

    private void clickStop() {
        running = false;
    }

    private void clickReset() {
        running = false;
        seconds = 0;
        updateTimeView();
    }
}
