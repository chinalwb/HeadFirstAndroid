package com.chinalwb.c9_fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkoutListFragment extends ListFragment {

    static interface Listener {
        void itemClicked(int id);
    }

    private Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    public WorkoutListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] names = new String[Workout.workouts.length];
        for (int i = 0; i < Workout.workouts.length; i++) {
            names[i] = Workout.workouts[i].getName();
        }
        ArrayAdapter adapter = new ArrayAdapter(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                names
        );
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        this.listener.itemClicked((int) id);
    }
}
