package com.example.jessie.teamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.MenuItem;

public class RestaurantMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_map_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location:
                return true;
            case R.id.distance1:
                return true;
            case R.id.distance2:
                return true;
            case R.id.distance3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
