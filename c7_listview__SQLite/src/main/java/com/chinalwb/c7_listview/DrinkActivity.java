package com.chinalwb.c7_listview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.print.PrinterId;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKID = "drinkId";
    public static final String EXTRA_DRINK = "drink";

    private ImageView mPhoto;
    private TextView mName;
    private TextView mDescription;
    private CheckBox mFavorite;

    private int drinkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        Drink drink;
        boolean hasDrinkId = getIntent().getExtras().containsKey(EXTRA_DRINKID);
        if (hasDrinkId) {
            drinkId = getIntent().getExtras().getInt(EXTRA_DRINKID);
            drink = loadDrinkById(drinkId);
        } else {
            drink = getIntent().getExtras().getParcelable(EXTRA_DRINK);
        }

        init(drink);
    }

    private void init(Drink drink) {
        mPhoto = findViewById(R.id.photo);
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.description);
        mFavorite = findViewById(R.id.favorite);

        mPhoto.setImageResource(drink.getImageResourceId());
        mName.setText(drink.getName());
        mDescription.setText(drink.getDescription());
        mFavorite.setChecked(drink.getFavorite() == 1);

        mFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateFavorite(isChecked);
            }
        });
    }

    private void updateFavorite(boolean isChecked) {
        new UpdateFavoriteTask(this, isChecked).execute(this.drinkId);
//        SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(this);
//        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE, isChecked ? 1 : 0);
//        int r = db.update(StarbuzzDatabaseHelper.TABLE_DRINK,
//                contentValues,
//                "_id = ?",
//                new String[] { Integer.toString(drinkId) }
//                );
//        if (r > 0) {
//            Toast.makeText(this, "Updated.", Toast.LENGTH_SHORT).show();
//        }
//
//        db.close();
    }

    private Drink loadDrinkById(int id) {
        SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(this);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(
                StarbuzzDatabaseHelper.TABLE_DRINK,
                new String[] {
                        StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME,
                        StarbuzzDatabaseHelper.TABLE_DRINK_COL_DESCRIPTION,
                        StarbuzzDatabaseHelper.TABLE_DRINK_COL_IMAGE_RESOURCE_ID,
                        StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE,
                },
                "_id = ?",
                new String[] {Integer.toString(id) },
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(0);
            String desc = cursor.getString(1);
            int imageId = cursor.getInt(2);
            int favorite = cursor.getInt(3);

            cursor.close();
            Drink drink = new Drink(name, desc, imageId, favorite);
            return drink;
        }
        db.close();
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     *
     */
    private static class UpdateFavoriteTask extends AsyncTask<Integer, Void, Boolean> {

        ContentValues contentValues;
        SQLiteDatabase db;
        WeakReference<Context> contextWeakReference;
        boolean isChecked;
        UpdateFavoriteTask(Context context, boolean isChecked) {
            contextWeakReference = new WeakReference<>(context);
            this.isChecked = isChecked;
        }

        private Context getContext() {
            return this.contextWeakReference.get();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context = this.getContext();
            if (context == null) {
                return;
            }

            SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(context);
            db = sqLiteOpenHelper.getWritableDatabase();
            contentValues = new ContentValues();
            contentValues.put(StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE, isChecked ? 1 : 0);
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            if (params.length == 0) {
                return false;
            }
            int id = params[0];
            int r = db.update(
                    StarbuzzDatabaseHelper.TABLE_DRINK,
                    this.contentValues,
                    "_id = ?",
                    new String[] { Integer.toString(id) }
            );
            return r > 0;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            try {
                Context context = this.getContext();
                if (context == null) {
                    return;
                }

                if (success) {
                    Toast.makeText(context, "Updated!!", Toast.LENGTH_SHORT).show();
                }
            } finally {
                db.close();
            }
        }
    }
}
