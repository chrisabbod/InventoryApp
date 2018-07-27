package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryDbHelper extends SQLiteOpenHelper {

    /**Name of the database file */
    private static final String DATABASE_NAME = "inventory.db";

    /**
     * Database version. If you change the database schema, you must increment the database version
     */
    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Create a String that contains the SQL statement to create the inventory table
        String SQL_CREATE_INVENTORY_TABLE = " CREATE TABLE " + InventoryEntry.TABLE_NAME + "("
                + InventoryEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_PRODUCT_NAME + "TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_PRICE + "INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.COLUMN_PRODUCT_QUANTITY + "INTEGER NOT NULL DEFAULT 0, "
                + InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME + "TEXT, "
                + InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER + "TEXT);";

        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int il){

    }
}
