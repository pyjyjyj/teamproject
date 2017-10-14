package com.example.jessie.teamproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        String num = getString(R.string.phone_number);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }
}
