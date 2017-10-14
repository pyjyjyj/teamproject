package com.example.jessie.teamproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        int detailImg = intent.getIntExtra("menuIcon", 0);
        String detailName = intent.getStringExtra("menuName");
        String detailPrice = intent.getStringExtra("menuPrice");
        String detailInfo = intent.getStringExtra("menuInfo");
        String detailStar = intent.getStringExtra("menuStar");

        ImageView img = (ImageView) findViewById(R.id.detailImg);
        img.setImageResource(detailImg);

        TextView name = (TextView) findViewById(R.id.detailName);
        name.setText(detailName);

        TextView price = (TextView) findViewById(R.id.detailPrice);
        price.setText(detailPrice);

        TextView info = (TextView) findViewById(R.id.detailInfo);
        info.setText(detailInfo);

        TextView star = (TextView) findViewById(R.id.detailStar);
        star.setText(detailStar);
    }
}
