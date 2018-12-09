package com.chinalwb.c9_fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isTablet() && savedInstanceState == null) { // savedInstance == null means the
            // activity is being restored
             updateDetailFragment(0, false);
        }
    }

    private void updateDetailFragment(int workId, boolean addToBackStack) {
        WorkoutDetailFragment detailFragment = new WorkoutDetailFragment();
        detailFragment.setWorkId(workId);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private boolean isTablet() {
        View view = this.findViewById(R.id.fragment_container);
        return view != null;
    }

    @Override
    public void itemClicked(int id) {
        if (isTablet()) {
            updateDetailFragment(id, true);
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORK_ID, id);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
