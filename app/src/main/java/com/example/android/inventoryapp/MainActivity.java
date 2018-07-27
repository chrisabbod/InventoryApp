package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryDbHelper;

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

        // Setup FAB in MainActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertInventory();
                Toast.makeText(MainActivity.this, "Inventory Saved", Toast.LENGTH_SHORT).show();
            }
        });

        mProductName = (EditText)findViewById(R.id.edit_product_name);
        mProductPrice = (EditText)findViewById(R.id.edit_product_price);
        mProductQuantity = (EditText)findViewById(R.id.edit_product_quantity);
        mSupplierName = (EditText)findViewById(R.id.edit_supplier_name);
        mSupplierNumber = (EditText)findViewById(R.id.edit_supplier_number);

        mDbHelper = new InventoryDbHelper(this);
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
            case R.id.action_insert_dummy_data:
                //Do nothing

                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
