package com.example.jessie.teamproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<MenuItem> data = new ArrayList<MenuItem>();
        data.add(new MenuItem(R.drawable.momil, "냉모밀", "3000"));
        data.add(new MenuItem(R.drawable.coolnoodle, "냉면", "2500"));
        data.add(new MenuItem(R.drawable.porkdubu, "돈육순두부찌개", "3800"));
        data.add(new MenuItem(R.drawable.gimbab, "야채김밥", "1200"));

        adapter = new MenuAdapter(this, R.layout.menu_item, data);

        ListView listView = (ListView)findViewById(R.id.menuList);
        final View menuHeader = getLayoutInflater().inflate(R.layout.menu_header, null, false) ;
        listView.addHeaderView(menuHeader);
        listView.setAdapter(adapter);

        listView.setDividerHeight(8);
    }

    public void callButtonClick(View view) {
        String num = getString(R.string.phone_number);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }
}
