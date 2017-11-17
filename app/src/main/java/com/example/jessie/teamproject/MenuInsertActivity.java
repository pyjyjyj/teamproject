package com.example.jessie.teamproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuInsertActivity extends AppCompatActivity {
    ImageButton imageButton;
    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_insert);
        mDBHelper = new DBHelper(this);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button menu_insert_btn = (Button) findViewById(R.id.menu_insert_btn);
        menu_insert_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                insertMenu();
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

    private String[] createMenuDataArray() {
        EditText mName = (EditText) findViewById(R.id.mName);
        EditText mPrice = (EditText) findViewById(R.id.mPrice);
        EditText mInfo = (EditText) findViewById(R.id.mInfo);
        EditText mStar = (EditText) findViewById(R.id.mStar);
        String restaurant = getIntent().getStringExtra("resName");
        String name = mName.getText().toString();
        String price = mPrice.getText().toString()+"원";
        String info = mInfo.getText().toString();
        String star = mStar.getText().toString();
        String imgFileName = mPhotoFileName;
        String[] dataArray = {restaurant, name, price, info, star, imgFileName};
        return dataArray;
    }

    private void insertMenu() {
        String[] Menu_Data = createMenuDataArray();
        long nOfRows = mDBHelper.insertMenuByMethod(Menu_Data[0], Menu_Data[1], Menu_Data[2], Menu_Data[3], Menu_Data[4], Menu_Data[5]);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        if (nOfRows > 0)
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
    }
}
