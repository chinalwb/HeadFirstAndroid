package com.chinalwb.c9_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutDetailFragment extends Fragment {

    private int workId;

    private TextView mName;
    private TextView mDesc;

    public WorkoutDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        return view;
    }

    private void init() {
        View root = getView();
        mName = root.findViewById(R.id.textTitle);
        mDesc = root.findViewById(R.id.textDescription);
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        String name = Workout.workouts[this.workId].getName();
        mName.setText(name);
        String desc = Workout.workouts[this.workId].getDescription();
        mDesc.setText(desc);
    }
}
