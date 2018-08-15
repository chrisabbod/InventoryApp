package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;
import com.example.android.inventoryapp.data.InventoryProvider;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor){
        // Find individual views that we want to modify in the list item layout
        TextView productNameTextView = (TextView) view.findViewById(R.id.name);
        TextView productPriceTextView = (TextView) view.findViewById(R.id.price);
        final TextView productQuantityTextView = (TextView)view.findViewById(R.id.quantity);

        // Find the columns of product attributes that we're interested in
        final int productId = cursor.getColumnIndex(InventoryEntry._ID);
        final int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        final int productPriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the product attributes from the Cursor for the current product
        final String productName = cursor.getString(productNameColumnIndex);
        final String productPrice = cursor.getString(productPriceColumnIndex);
        final String productQuantity = cursor.getString(productQuantityColumnIndex);

        // Update the TextViews with the attributes for the current product
        productNameTextView.setText(productName);
        productPriceTextView.setText("$" + productPrice);
        productQuantityTextView.setText("Quantity: " + productQuantity);

        final int id = cursor.getPosition();

        Button salesButton = (Button)view.findViewById(R.id.sale_button);
        salesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri currentInventoryUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, id);

                Toast.makeText(context, "BUTTON CLICKED " + currentInventoryUri, Toast.LENGTH_SHORT).show();

                ContentValues values = new ContentValues();
                values.put(InventoryEntry.COLUMN_PRODUCT_NAME, productName);
                values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, productPrice);
                values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, 2);
                context.getContentResolver().update(currentInventoryUri, values, null, null);
                //int productQuantityChange = context.getContentResolver().update(currentInventoryUri, values, null, null);

                productQuantityTextView.setText("Quantity: " + productQuantity);
                //Toast.makeText(context, "Quantity for " + productName + " is: " + productQuantity, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
