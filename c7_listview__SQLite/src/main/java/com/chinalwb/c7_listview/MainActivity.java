package com.chinalwb.c7_listview;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ListView mFavoriteList;


    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mListView = findViewById(R.id.list_options);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this,
                            DrinkCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };

        mListView.setOnItemClickListener(itemClickListener);


        // Your favorite drinks
        mFavoriteList = findViewById(R.id.favorite_drinks_list);
        SQLiteOpenHelper sqLiteOpenHelper = new StarbuzzDatabaseHelper(this);
        db = sqLiteOpenHelper.getReadableDatabase();
        cursor = queryForFavoriteDrinks();
        final CursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] { StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME },
                new int[] { android.R.id.text1 },
                0
        );
        mFavoriteList.setAdapter(cursorAdapter);
        mFavoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor tmpCursor = (Cursor) cursorAdapter.getItem(position);
                int drinkId = tmpCursor.getInt(0);
                Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, drinkId);
                startActivity(intent);
            }
        });
    }

    private Cursor queryForFavoriteDrinks() {
         return db.query(
                StarbuzzDatabaseHelper.TABLE_DRINK,
                new String[] { "_id", StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME },
                StarbuzzDatabaseHelper.TABLE_DRINK_COL_FAVORITE + "= ?",
                new String[] { "1" },
                null,
                null,
                StarbuzzDatabaseHelper.TABLE_DRINK_COL_NAME + " ASC"
        );
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        cursor = queryForFavoriteDrinks();
        CursorAdapter adapter = (CursorAdapter) mFavoriteList.getAdapter();
        adapter.changeCursor(cursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
