package com.chinalwb.c7_listview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class DrinkCategoryActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        init();
    }

    private void init() {
        mListView = findViewById(R.id.list_drinks);

//        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_list_item_1,
//                Drink.drinks);

        SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(this);
        db = sqLiteOpenHelper.getReadableDatabase();
//        try {
//
//        } catch (SQLiteException e) {
//            e.printStackTrace();;
//        }

        cursor = db.query(
                StarbuzzDatabaseHelper.TABLE_DRINK,
                new String[] { "_id", StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME, StarbuzzDatabaseHelper.TABLE_DRINK_COL_DESCRIPTION, StarbuzzDatabaseHelper.TABLE_DRINK_COL_IMAGE_RESOURCE_ID },
                null,
                null,
                null,
                null,
                null
        );

        final SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_expandable_list_item_1,
                cursor,
                new String[] { StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME },
                new int[] { android.R.id.text1 },
                0
        );

        mListView.setAdapter(cursorAdapter);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Start a new activity
                // pass the id?
//                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
//                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
//                startActivity(intent);

                Cursor cursor = (Cursor) cursorAdapter.getItem(position);
                int drinkId = cursor.getInt(0);
                Intent intent = new Intent(DrinkCategoryActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, drinkId);
                startActivity(intent);
            }
        };

        mListView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
