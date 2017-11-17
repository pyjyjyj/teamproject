package com.example.jessie.teamproject;

import android.provider.BaseColumns;

/*교수님 강의자료에서 가져와 수정한 코드입니다.*/
public final class RestaurantContract {
    public static final String DB_NAME="restaurant.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private RestaurantContract() {}

    /* Inner class that defines the table contents */
    public static class Restaurants implements BaseColumns {
        public static final String TABLE_NAME="Restaurants";
        public static final String KEY_NAME = "Name";
        public static final String KEY_PHONE = "Phone";
        public static final String KEY_ADDRESS = "Address";
        public static final String KEY_IMAGE = "Image";
        public static final String KEY_TIME = "Time";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_ADDRESS + TEXT_TYPE + COMMA_SEP +
                KEY_PHONE + TEXT_TYPE +  COMMA_SEP+
                KEY_TIME + TEXT_TYPE + COMMA_SEP +
                KEY_IMAGE + TEXT_TYPE +" )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class Menus implements BaseColumns {
        public static final String TABLE_NAME="Menus";
        public static final String KEY_RESTAURANT = "Restaurant";
        public static final String KEY_NAME = "Name";
        public static final String KEY_PRICE = "Price";
        public static final String KEY_INFO = "Info";
        public static final String KEY_STAR = "Star";
        public static final String KEY_IMAGE = "Image";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                KEY_RESTAURANT + TEXT_TYPE + COMMA_SEP +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_PRICE + TEXT_TYPE + COMMA_SEP +
                KEY_INFO + TEXT_TYPE +  COMMA_SEP+
                KEY_STAR + TEXT_TYPE + COMMA_SEP +
                KEY_IMAGE + TEXT_TYPE +" )";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}