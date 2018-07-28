package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

import java.sql.SQLData;
import java.sql.SQLInput;

public class MainActivity extends AppCompatActivity {

    private EditText mProductName;
    private EditText mProductPrice;
    private EditText mProductQuantity;
    private EditText mSupplierName;
    private EditText mSupplierNumber;

    private InventoryDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProductName = (EditText)findViewById(R.id.edit_product_name);
        mProductPrice = (EditText)findViewById(R.id.edit_product_price);
        mProductQuantity = (EditText)findViewById(R.id.edit_product_quantity);
        mSupplierName = (EditText)findViewById(R.id.edit_supplier_name);
        mSupplierNumber = (EditText)findViewById(R.id.edit_supplier_number);

        mDbHelper = new InventoryDbHelper(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void insertInventory(){
        //Read from input fields
        //Use trim to eliminate leading or trailing white space
        String productNameString = mProductName.getText().toString().trim();
        String productPriceString = mProductPrice.getText().toString().trim();
        String productQuantityString = mProductQuantity.getText().toString().trim();
        String supplierNameString = mSupplierName.getText().toString().trim();
        String supplierNumberString = mSupplierNumber.getText().toString().trim();

        InventoryDbHelper mDbHelper = new InventoryDbHelper(this);

        //Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, productNameString);
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, productPriceString);
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, productQuantityString);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, supplierNumberString);

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        if(newRowId == -1){
            Toast.makeText(this, "Error with saving inventory", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Inventory saved with row id  " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of the
     * inventory database
     */
    private void displayDatabaseInfo(){
        //Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String [] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER
        };

        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView)findViewById(R.id.number_of_rows);

        try{
            //Create a header in the TextView that looks like this:
            //
            //The pets table contains <number of rows in Cursor> pets.
            //_id - name - breed - gender - weight
            //
            //In the while loop below, iterate through the rows of the cursor and display
            //the information from each column in this order.
            displayView.setText("The inventory table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(InventoryEntry._ID + " - " +
            InventoryEntry.COLUMN_PRODUCT_NAME + " - " +
            InventoryEntry.COLUMN_PRODUCT_PRICE + " - " +
            InventoryEntry.COLUMN_PRODUCT_QUANTITY + " - " +
            InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
            InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER + "\n");

            //Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            int productPriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierNumberColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER);

            while (cursor.moveToNext()){
                //Use that index to extract the String or Int value of the word
                //at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentProductPrice = cursor.getInt(productPriceColumnIndex);
                int currentProductQuantity = cursor.getInt(productQuantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                int currentSupplierNumber = cursor.getInt(supplierNumberColumnIndex);
                //Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                currentProductName + " - " +
                currentProductPrice + " - " +
                currentProductQuantity + " - " +
                currentSupplierName + " - " +
                currentSupplierNumber));
            }
        }finally{
            //always close the cursor when you're done reading from it. This releases all its
            //resources and makes it invalid
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_save_data:
                insertInventory();
                Toast.makeText(MainActivity.this, "Inventory Saved", Toast.LENGTH_SHORT).show();
                displayDatabaseInfo();

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
