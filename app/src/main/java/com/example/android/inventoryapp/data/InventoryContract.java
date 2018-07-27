package com.example.android.inventoryapp.data;

import android.provider.BaseColumns;

public final class InventoryContract {
    //Constructor created but not completed because it should never be used
    private InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns{

        public final static String TABLE_NAME = "inventory";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "product name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_SUPPLIER_NAME = "supplier name";
        public final static String COLUMN_PRODUCT_SUPPLIER_NUMBER = "supplier number";
    }
}
