package com.example.jessie.teamproject;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class RestarurantDetailActivity extends AppCompatActivity {
    MenuAdapter adapter;
    DBHelper mDBHelper;
    ArrayList<MenuItem> data;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restarurant_detail);

        mDBHelper = new DBHelper(this);
        getRestaurantData();
        data = new ArrayList<MenuItem>();

        listView = (ListView)findViewById(R.id.menuList);
        listViewSet();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MenuDetail.class);
                MenuItem item = (MenuItem) adapter.getItem(position);
                String menuIcon = item.menuIcon;
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
        });
    }

    public void listViewSet() {
        Cursor c = mDBHelper.getAllMenusByMethod();
        TextView rName = (TextView) findViewById(R.id.rdetail_name);
        adapter = new MenuAdapter(this, R.layout.menu_item, data);
        boolean exist = false;
        while(c.moveToNext()) {
            if(c.getString(1).equals(rName.getText().toString())) {
                for(int i=0; i<adapter.getCount(); i++) {
                    MenuItem item = (MenuItem) adapter.getItem(i);
                    if(item.menuName.equals(c.getString(2))) {
                        exist=true;
                        break;
                    }else
                        exist=false;
                }
                if(!exist)
                    data.add(new MenuItem(c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
            }
        }
        listView.setAdapter(adapter);
        listView.setDividerHeight(8);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_MENU_INSERT && resultCode == RESULT_OK) {
            listViewSet();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_detail_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    final static int REQUEST_MENU_INSERT = 0;
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intent = new Intent(this, MenuInsertActivity.class);
        intent.putExtra("resName", getIntent().getStringExtra("resName"));
        startActivityForResult(intent, REQUEST_MENU_INSERT);
        return true;
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
                String mPhotoFileName = cursor.getString(5);
                if (mPhotoFileName != null) {
                    File mPhotoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                    Uri uri = Uri.fromFile(mPhotoFile);
                    image.setImageURI(uri);
                }
            }
        }
    }
}
