package com.chinalwb.c7_listview;

import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";
    public static final String EXTRA_DRINK = "drink";


    private ImageView mPhoto;
    private TextView mName;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Drink drink = null;
        boolean hasDrinkId = getIntent().getExtras().containsKey(EXTRA_DRINKID);
        if (hasDrinkId) {
            int drinkId = getIntent().getExtras().getInt(EXTRA_DRINKID);
            drink = Drink.drinks[drinkId];
        } else {
            drink = getIntent().getExtras().getParcelable(EXTRA_DRINK);
        }

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
