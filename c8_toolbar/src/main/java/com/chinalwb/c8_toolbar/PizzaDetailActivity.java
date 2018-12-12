package com.chinalwb.c8_toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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

        init();
    }

    private void init() {
        int pizzaId = getIntent().getExtras().getInt(EXTRA_PIZZA_ID);
        String pizzaName = Pizza.pizzas[pizzaId].getName();
        int imageId = Pizza.pizzas[pizzaId].getImageResourceId();

        TextView textView = findViewById(R.id.pizza_text);
        textView.setText(pizzaName);

        ImageView imageView = findViewById(R.id.pizza_image);
        Drawable drawable = ContextCompat.getDrawable(this, imageId);
        imageView.setImageDrawable(drawable);
    }
}
