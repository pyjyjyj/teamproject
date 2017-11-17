package com.example.jessie.teamproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* 교수님의 강의자료에서 코드를 가져와서 수정하였습니다 */

public class DBHelper extends SQLiteOpenHelper {
    final static String TAG="DBHelper";

    public DBHelper(Context context) {
        super(context, RestaurantContract.DB_NAME, null, RestaurantContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,getClass().getName()+".onCreate()");
        db.execSQL(RestaurantContract.Restaurants.CREATE_TABLE);
        db.execSQL(RestaurantContract.Menus.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG,getClass().getName() +".onUpgrade()");
        db.execSQL(RestaurantContract.Restaurants.DELETE_TABLE);
        db.execSQL(RestaurantContract.Menus.DELETE_TABLE);
        onCreate(db);
    }

    public long insertRestaurantByMethod(String name, String address, String phone, String time, String imgFileName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.Restaurants.KEY_NAME, name);
        values.put(RestaurantContract.Restaurants.KEY_ADDRESS, address);
        values.put(RestaurantContract.Restaurants.KEY_PHONE, phone);
        values.put(RestaurantContract.Restaurants.KEY_TIME, time);
        values.put(RestaurantContract.Restaurants.KEY_IMAGE, imgFileName);

        return db.insert(RestaurantContract.Restaurants.TABLE_NAME,null,values);
    }

    public long insertMenuByMethod(String restaurant, String name, String price, String info, String star, String imgFileName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.Menus.KEY_RESTAURANT, restaurant);
        values.put(RestaurantContract.Menus.KEY_NAME, name);
        values.put(RestaurantContract.Menus.KEY_PRICE, price);
        values.put(RestaurantContract.Menus.KEY_INFO, info);
        values.put(RestaurantContract.Menus.KEY_STAR, star);
        values.put(RestaurantContract.Menus.KEY_IMAGE, imgFileName);

        return db.insert(RestaurantContract.Menus.TABLE_NAME,null,values);
    }

    public long deleteRestaurantByMethod(String _id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = RestaurantContract.Restaurants._ID +" = ?";
        String[] whereArgs ={_id};
        return db.delete(RestaurantContract.Restaurants.TABLE_NAME, whereClause, whereArgs);
    }

    public long deleteMenuByMethod(String _id) {
        SQLiteDatabase db = getWritableDatabase();

        String whereClause = RestaurantContract.Menus._ID +" = ?";
        String[] whereArgs ={_id};
        return db.delete(RestaurantContract.Menus.TABLE_NAME, whereClause, whereArgs);
    }

    public Cursor getAllRestaurantsByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestaurantContract.Restaurants.TABLE_NAME,null,null,null,null,null,null);
    }

    public Cursor getAllMenusByMethod() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(RestaurantContract.Menus.TABLE_NAME,null,null,null,null,null,null);
    }
}
