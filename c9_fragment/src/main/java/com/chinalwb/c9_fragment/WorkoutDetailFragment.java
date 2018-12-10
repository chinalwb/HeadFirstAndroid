package com.chinalwb.c9_fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    private static final String FRAGMENT_STATE_WORK_ID = "fragment_state_work_id";

    private int workId;

    private TextView mName;
    private TextView mDesc;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            StopwatchFragment fragment = new StopwatchFragment();
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.stopwatch_fragment_container, fragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            this.workId = savedInstanceState.getInt(FRAGMENT_STATE_WORK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        boolean isFirstTimeCreate = savedInstanceState == null;
        init(view, isFirstTimeCreate);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        String name = Workout.workouts[this.workId].getName();
        mName.setText(name);
        String desc = Workout.workouts[this.workId].getDescription();
        mDesc.setText(desc);
    }

    private void init(View root, boolean isFirstTimeCreate) {
        mName = root.findViewById(R.id.textTitle);
        mDesc = root.findViewById(R.id.textDescription);
        if (isFirstTimeCreate) {

        }
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_STATE_WORK_ID, this.workId);
    }
}
