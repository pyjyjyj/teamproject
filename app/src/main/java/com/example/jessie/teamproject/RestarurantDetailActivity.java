package com.example.jessie.teamproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RestarurantDetailActivity extends AppCompatActivity implements RestaurantFragment.OnMenuSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarurant_detail);
    }

    @Override
    public void onMenuSelected(MenuItem menuItem) {
        String menuIcon = menuItem.menuIcon;
        String menuName = menuItem.menuName;
        String menuPrice = menuItem.menuPrice;
        String menuInfo = menuItem.menuInfo;
        String menuStar = menuItem.menuStar;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MenuFragment menuFragment = new MenuFragment();
            menuFragment.setSelection(menuIcon, menuName, menuPrice, menuInfo, menuStar);
            getSupportFragmentManager().beginTransaction().replace(R.id.menu_detail_container, menuFragment).commit();
        } else {
            Intent intent = new Intent(getApplicationContext(), MenuDetail.class);
            intent.putExtra("menuIcon", menuIcon);
            intent.putExtra("menuName", menuName);
            intent.putExtra("menuPrice", menuPrice);
            intent.putExtra("menuInfo", menuInfo);
            intent.putExtra("menuStar", menuStar);
            intent.putExtra("resName", getIntent().getStringExtra("resName"));
            startActivity(intent);
        }
    }

    public void callButtonClick(View view) {
        TextView phone_n = (TextView)findViewById(R.id.rdetail_phone);
        String num = phone_n.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }
}
