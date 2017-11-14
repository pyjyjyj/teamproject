package com.example.jessie.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DBHelper(this);

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

        /* 리스트뷰 강의자료에서 참조 */
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurants);

        ListView restaurant_list = (ListView)findViewById(R.id.restaurant_list);
        restaurant_list.setAdapter(adapt);
    }
}