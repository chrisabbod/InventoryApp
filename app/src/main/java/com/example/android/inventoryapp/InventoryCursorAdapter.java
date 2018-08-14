package com.example.android.inventoryapp;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        // Find individual views that we want to modify in the list item layout
        TextView productNameTextView = (TextView) view.findViewById(R.id.name);
        TextView productPriceTextView = (TextView) view.findViewById(R.id.price);
        TextView productQuantityTextView = (TextView)view.findViewById(R.id.quantity);

        // Find the columns of pet attributes that we're interested in
        int productNameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int productPriceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int productQuantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the pet attributes from the Cursor for the current product
        String productName = cursor.getString(productNameColumnIndex);
        String productPrice = cursor.getString(productPriceColumnIndex);
        String productQuantity = cursor.getString(productQuantityColumnIndex);

        // Update the TextViews with the attributes for the current product
        productNameTextView.setText(productName);
        productPriceTextView.setText("$" + productPrice);
        productQuantityTextView.setText("Quantity:" + productQuantity);
    }
}
