package com.example.skipq.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.skipq.model.ScannedProduct;
import com.example.skipq.model.ShoppingItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "cart.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table CartItems(productCode TEXT primary key, productName TEXT, productPrice TEXT, quantity TEXT, productionDate TEXT, expiryDate TEXT, manufacturer TEXT, productionCountry TEXT, promotion TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists CartItems");
    }

    public Boolean insertData(Long productCode, String productName, double productPrice, int quantity, String productionDate, String expiryDate, String manufacturer, String productionCountry, Boolean promotion){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("productCode", productCode);
        contentValues.put("productName", productName);
        contentValues.put("productPrice", productPrice);
        contentValues.put("quantity", quantity);
        contentValues.put("productionDate", productionDate);
        contentValues.put("expiryDate", expiryDate);
        contentValues.put("manufacturer", manufacturer);
        contentValues.put("productionCountry", productionCountry);
        contentValues.put("promotion", promotion);

        long result = db.insert("CartItems",null,contentValues);

        return result != -1;
    }

    public Boolean deleteCartItem(Long productCode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from CartItems where name = ?", new String[]{String.valueOf(productCode)});

        if(cursor.getCount()>0){
            long result = db.delete("CartItems", "productCode=?", new String[]{String.valueOf(productCode)});
            return result != -1;
        }
        else {
            return false;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from CartItems", null);
    }

    public Boolean updateCart(Long productCode, int quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity",quantity);

        Cursor cursor = db.rawQuery("Select * from CartItems where productCode = ?",new String[]{String.valueOf(productCode)});
        if (cursor.getCount()>0){
            long result = db.update("CartItems",contentValues,"productCode=?", new String[]{String.valueOf(productCode)});

            if(result==-1){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    public Boolean checkProductAlreadyInCart(Long productCode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from CartItems where productCode = ?", new String[]{String.valueOf(productCode)});
        if (cursor.getCount()>0){
            return true;
        }
        else {
            return false;
        }
    }


    public int getQuantityById(Long productCode){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from CartItems where productCode = ?",new String[]{String.valueOf(productCode)});

        return  Integer.parseInt(cursor.getString(3));
    }

    public ArrayList<ShoppingItem> getAllProducts() throws ParseException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from CartItems",null);

        ArrayList<ShoppingItem> scannedProductArrayList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                scannedProductArrayList.add(new ShoppingItem(Long.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        Boolean.valueOf(cursor.getString(8))
                        ));
            }while (cursor.moveToNext());
        }
    cursor.close();
    return scannedProductArrayList;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM CartItems";
        db.execSQL(clearDBQuery);
    }

}

