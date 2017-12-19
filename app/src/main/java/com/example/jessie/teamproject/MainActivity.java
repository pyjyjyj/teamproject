package com.example.jessie.teamproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView Start;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Start = (ImageView) findViewById(R.id.ani);

        startTwinAnimation();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this, RestaurantMapActivity.class);
                startActivity(intent);
                finish();
            }
        },2200);
        //Handler 참조 블로그 : http://grayd.tistory.com/11
    }

    private void startTwinAnimation(){
        Animation start_anim = AnimationUtils.loadAnimation(this,R.anim.start);
        Start.startAnimation(start_anim);
    }
}