package com.chinalwb.c9_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.List;

public class TempActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("XX", "Start Temp Activity onCreate");

        setContentView(R.layout.activity_temp);

        boolean isFirstTimeCreate = savedInstanceState == null;
        init(isFirstTimeCreate);

        Log.e("XX", "End Temp Activity onCreate");
    }

    private void init(boolean isFirstTimeCreate) {
        this.frameLayout = this.findViewById(R.id.fragment_container);
        if (isFirstTimeCreate) {
            StopwatchFragment fragment = new StopwatchFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        } else {
            int childCount = this.frameLayout.getChildCount();
            Log.e("XX", "child count = " + childCount);

            FragmentManager fragmentManager = getSupportFragmentManager();
            List<Fragment> fragmentList =  fragmentManager.getFragments();
            Log.e("XX", "fragments list size " + fragmentList.size());
        }
    }
}
