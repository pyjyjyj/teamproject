package com.example.jessie.teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class RestaurantInsertActivity extends AppCompatActivity {
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_insert);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        String[] Restaurant_Data = createRestaurantDataArray();
    }

    private String mPhotoFileName = null;

    private String[] createRestaurantDataArray() {
        EditText rName = (EditText) findViewById(R.id.rName);
        EditText rAddress = (EditText) findViewById(R.id.rAddress);
        EditText rPhone = (EditText) findViewById(R.id.rPhone);
        EditText rStartTime = (EditText) findViewById(R.id.rStartTime);
        EditText rEndTime = (EditText) findViewById(R.id.rEndTime);
        String name = rName.getText().toString();
        String address = rAddress.getText().toString();
        String phone = rPhone.getText().toString();
        //String.format() : https://kwanulee.github.io/Android/data-management/sqlite.html 참조
        String time = String.format("%s ~ %s", rStartTime.getText().toString(), rEndTime.getText().toString());
        String imgFileName = mPhotoFileName;
        String[] Restaurant_Data = {name, address, phone, time, imgFileName};
        return Restaurant_Data;
    }
}
