package com.example.jessie.teamproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MenuDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        Intent intent = getIntent();
        String detailImg = intent.getStringExtra("menuIcon");
        String detailName = intent.getStringExtra("menuName");
        String detailPrice = intent.getStringExtra("menuPrice");
        String detailInfo = intent.getStringExtra("menuInfo");
        String detailStar = intent.getStringExtra("menuStar");

        MenuFragment MenuFragment = new MenuFragment();
        MenuFragment.setSelection(detailImg, detailName, detailPrice, detailInfo, detailStar);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_detail, MenuFragment).commit();
    }

    @Nullable
    @Override
    public Intent getSupportParentActivityIntent() { //http://blog.daum.net/creazier/15310385
        Intent intent = new Intent(this, RestarurantDetailActivity.class);
        intent.putExtra("resName", getIntent().getStringExtra("resName"));
        return intent;
    }
}
