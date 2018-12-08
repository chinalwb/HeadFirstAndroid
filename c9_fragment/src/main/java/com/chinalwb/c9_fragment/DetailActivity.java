package com.chinalwb.c9_fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_WORK_ID = "workId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        WorkoutDetailFragment fragment = (WorkoutDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        int workId = getIntent().getExtras().getInt(EXTRA_WORK_ID);
        fragment.setWorkId(workId);
    }
}
