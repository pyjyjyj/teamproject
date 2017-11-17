package com.example.jessie.teamproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
/* currentDateFormat(), dispatchTakePictureIntent(), onActivityResult(), checkDangerousPermissions()
* 출처:https://kwanulee.github.io/Android/multimedia/multimedia.html*/

public class RestaurantInsertActivity extends AppCompatActivity {
    ImageButton imageButton;
    private DBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_insert);
        checkDangerousPermissions();

        mDbHelper = new DBHelper(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button restaurant_insert_btn = (Button) findViewById(R.id.restaurant_insert_btn);
        restaurant_insert_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                insertRestaurant();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File mPhotoFile =null;
    private String mPhotoFileName = null;

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //1. 카메라 앱으로 찍은 이미지를 저장할 파일 객체 생성
            mPhotoFileName = "IMG"+currentDateFormat()+".jpg";
            mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
            if (mPhotoFile != null) {
                //2. 생성된 파일 객체에 대한 Uri 객체를 얻기
                Uri imageUri = FileProvider.getUriForFile(this, "com.example.jessie.teamproject", mPhotoFile);
                //3. Uri 객체를 Extras를 통해 카메라 앱으로 전달
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else
                Toast.makeText(getApplicationContext(), "file null", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (mPhotoFileName != null) {
                mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                Uri uri = Uri.fromFile(mPhotoFile);
                imageButton.setImageURI(uri);
            } else
                Toast.makeText(getApplicationContext(), "mPhotoFile is null", Toast.LENGTH_SHORT).show();
        }
    }

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
        String[] dataArray = {name, address, phone, time, imgFileName};
        return dataArray;
    }

    final int REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA=1;
    private void checkDangerousPermissions() {
        String[] permissions = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED)
                break;
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE_FOR_MULTIMEDIA);
        }
    }

    String[] Restaurant_Data;
    private void insertRestaurant() {
        Restaurant_Data = createRestaurantDataArray();
        long nOfRows = mDbHelper.insertRestaurantByMethod(Restaurant_Data[0], Restaurant_Data[1], Restaurant_Data[2], Restaurant_Data[3], Restaurant_Data[4]);
        Intent intent = new Intent(this, RestarurantDetailActivity.class);
        intent.putExtra("resName", Restaurant_Data[0]);
        startActivity(intent);
        if (nOfRows > 0)
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
    }
}
