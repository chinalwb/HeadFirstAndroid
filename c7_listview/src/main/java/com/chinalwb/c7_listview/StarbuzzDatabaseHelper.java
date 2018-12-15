package com.chinalwb.c7_listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.print.PrinterId;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_DRINK = "DRINK";
    public static final String TABLE_DRINK_COL_IMAGE_RESOURCE_ID = "IMAGE_RESOURCE_ID";
    public static final String TABLE_DRINK_COL_DESCRIPTION = "DESCRIPTION";
    public static final String TABLE_DRINK_COL_NAME = "NAME";

    private static final String DB_NAME = "starbuzz";
    private static final int DB_VERSION = 1;

    public StarbuzzDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            // initial creation
            // Create table
            String createDrinkTableSQL = getCreateDrinkTableSQL();
            db.execSQL(createDrinkTableSQL);

            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
    }

    private static void insertDrink(SQLiteDatabase db,
                                    String name,
                                    String description,
                                    int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put(TABLE_DRINK_COL_NAME, name);
        drinkValues.put(TABLE_DRINK_COL_DESCRIPTION, description);
        drinkValues.put(TABLE_DRINK_COL_IMAGE_RESOURCE_ID, resourceId);
        db.insert(TABLE_DRINK, null, drinkValues);
    }

    private static String getCreateDrinkTableSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ");
        sqlBuilder.append(TABLE_DRINK);
        sqlBuilder.append(" (");
        sqlBuilder.append("_id INTEGER PRIMARY KEY AUTOINCREMENT,");
        sqlBuilder.append(TABLE_DRINK_COL_NAME + " TEXT,");
        sqlBuilder.append(TABLE_DRINK_COL_DESCRIPTION + " TEXT,");
        sqlBuilder.append(TABLE_DRINK_COL_IMAGE_RESOURCE_ID + "IMAGE_RESOURCE_ID INTEGER");
        sqlBuilder.append(");");
        return sqlBuilder.toString();
    }
}
