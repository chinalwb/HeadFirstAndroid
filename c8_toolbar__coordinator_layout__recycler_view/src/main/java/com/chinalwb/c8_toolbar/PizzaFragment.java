package com.chinalwb.c8_toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PizzaFragment extends Fragment {


    public PizzaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView pizzaRecycler = (RecyclerView) inflater.inflate(
                R.layout.fragment_pizza, container, false);

        String[] pizzaNames = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }
        int[] pizzaImages = new int[Pizza.pizzas.length];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        pizzaRecycler.setAdapter(adapter);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getContext(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZA_ID, pos);
                startActivity(intent);
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        pizzaRecycler.setLayoutManager(layoutManager);

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        pizzaRecycler.setLayoutManager(layoutManager);

//        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        pizzaRecycler.setLayoutManager(layoutManager);
        return pizzaRecycler;
    }
}
