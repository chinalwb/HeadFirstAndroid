package com.chinalwb.c8_toolbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class PizzaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PIZZA_ID = "pizzaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        PizzaDetailFragment fragment = (PizzaDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.pizza_detail_fragment);

        int pizzaId = getIntent().getIntExtra(EXTRA_PIZZA_ID, 0);
        fragment.setPizzaId(pizzaId);
    }
}
