package com.chinalwb.c20_others;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    float downX;
    float downY;
    float dx;
    float dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.w("XX", "rawx == " + event.getRawX() + ", getx == " + event.getX());
                        dx += event.getX() - downX;
                        dy += event.getY() - downY;
                        fab.setTranslationX(dx);
                        fab.setTranslationY(dy);
                        break;
                    case MotionEvent.ACTION_UP:
                        fab.performClick();
                        break;
                }
                return true;
            }
        });
    }

}
