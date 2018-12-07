package com.chinalwb.c7_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";

    private ImageView mPhoto;
    private TextView mName;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        int drinkId = getIntent().getExtras().getInt(EXTRA_DRINKID);
        Drink drink = Drink.drinks[drinkId];

        init(drink);
    }

    private void init(Drink drink) {
        mPhoto = findViewById(R.id.photo);
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.description);

        mPhoto.setImageResource(drink.getImageResourceId());
        mName.setText(drink.getName());
        mDescription.setText(drink.getDescription());
    }
}
