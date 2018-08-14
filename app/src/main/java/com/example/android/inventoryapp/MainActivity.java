package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertProduct(){
        //Create a ContentValues object where column names are the keys,
        //and milk product attributes are the values
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_PRODUCT_NAME, "Milk");
        values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, "1.99");
        values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, "1");
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NAME, "Milk Man");
        values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_NUMBER, "1234567");

        Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
    }
}
