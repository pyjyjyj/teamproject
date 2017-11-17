package com.example.jessie.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class RestarurantDetailActivity extends AppCompatActivity {
    static MenuAdapter adapter;
    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarurant_detail);

        mDBHelper = new DBHelper(this);

        getRestaurantData();

        ArrayList<MenuItem> data = new ArrayList<MenuItem>();
        data.add(new MenuItem(R.drawable.momil, "냉모밀", "3000", "차가운 국물에 모밀 면이 들어가 있어요", "평점 : 3.4"));
        data.add(new MenuItem(R.drawable.coolnoodle, "냉면", "2500", "여름 별미 메뉴", "평점 : 2.5"));
        data.add(new MenuItem(R.drawable.porkdubu, "돈육순두부찌개", "3800", "돼지 고기와 순두부의 조화", "평점 : 3.8"));
        data.add(new MenuItem(R.drawable.gimbab, "야채김밥", "1200", "야채가 들어간 김밥", "평점 : 4.5"));

        adapter = new MenuAdapter(this, R.layout.menu_item, data);

        ListView listView = (ListView)findViewById(R.id.menuList);
        final View menuHeader = getLayoutInflater().inflate(R.layout.menu_header, null, false) ;
        listView.addHeaderView(menuHeader);
        listView.setAdapter(adapter);

        listView.setDividerHeight(8);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MenuDetail.class);
                if (position==0) {
                    return;  // 메뉴 헤더 클릭 시 아무 일도 안 일어나고 리턴
                } else {
                    MenuItem item = (MenuItem) adapter.getItem(position-1);
                    int menuIcon = item.menuIcon;
                    String menuName = item.menuName;
                    String menuPrice = item.menuPrice;
                    String menuInfo = item.menuInfo;
                    String menuStar = item.menuStar;
                    intent.putExtra("menuIcon", menuIcon);
                    intent.putExtra("menuName", menuName);
                    intent.putExtra("menuPrice", menuPrice);
                    intent.putExtra("menuInfo", menuInfo);
                    intent.putExtra("menuStar", menuStar);
                    startActivity(intent);
                }

            }
        });
    }

    public void callButtonClick(View view) {
        TextView phone_n = (TextView)findViewById(R.id.rdetail_phone);
        String num = phone_n.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }

    private void getRestaurantData(){
        Cursor cursor = mDBHelper.getAllRestaurantsByMethod();

        while(cursor.moveToNext()) {
            if (cursor.getString(1).equals(getIntent().getStringExtra("resName"))) {
                TextView name = (TextView) findViewById(R.id.rdetail_name);
                name.setText(cursor.getString(1));
                TextView address = (TextView) findViewById(R.id.rdetail_address);
                address.setText(cursor.getString(2));
                TextView phone = (TextView) findViewById(R.id.rdetail_phone);
                phone.setText(cursor.getString(3));
                TextView time = (TextView) findViewById(R.id.rdetail_time);
                time.setText(cursor.getString(4));
                ImageView image = (ImageView) findViewById(R.id.rdetail_img);
                File mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), cursor.getString(5));
                Uri uri = Uri.fromFile(mPhotoFile);
                image.setImageURI(uri);
            }
        }
    }
}
