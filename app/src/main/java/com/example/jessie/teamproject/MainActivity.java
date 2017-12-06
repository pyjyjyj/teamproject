package com.example.jessie.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ImageView Start;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start = (ImageView) findViewById(R.id.ani);

       /* mDbHelper = new DBHelper(this);

        Button restaurant_insert = (Button) findViewById(R.id.restaurant_insert);
        restaurant_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantInsertActivity.class);
                startActivity(intent);
            }
        });

        Cursor cursor = mDbHelper.getAllRestaurantsByMethod(); // 전체 레스토랑 정보를 db 에서 가져옴
        String[] restaurants = new String[cursor.getCount()]; // 레스토랑 이름을 저장하기 위한 String 배열
        int i = 0;

        while (cursor.moveToNext()) { // 모든 레스토랑 이름을 배열에 저장하는 반복문
            restaurants[i++] = cursor.getString(1).toString();
        }

        *//* 리스트뷰 강의자료에서 참조 *//*
        final ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurants);

        ListView restaurant_list = (ListView)findViewById(R.id.restaurant_list);
        restaurant_list.setAdapter(adapt);

        restaurant_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), RestarurantDetailActivity.class);
                intent.putExtra("resName", adapt.getItem(i).toString());
                startActivity(intent);
            }
        });*/
        startTwinAnimation();
    }

    private void startTwinAnimation(){
        Animation start_anim = AnimationUtils.loadAnimation(this,R.anim.start);
        Start.startAnimation(start_anim);
    }
}