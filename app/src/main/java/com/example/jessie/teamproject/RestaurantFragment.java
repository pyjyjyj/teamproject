package com.example.jessie.teamproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment {
    MenuAdapter adapter;
    DBHelper mDBHelper;
    ArrayList<MenuItem> data;
    View rootView;
    ListView listView;

    public interface OnMenuSelectedListener {
        public void onMenuSelected(MenuItem menuItem);
    }

    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (View) inflater.inflate(R.layout.fragment_restaurant, container, false);

        mDBHelper = new DBHelper(getActivity());
        getRestaurantData();
        data = new ArrayList<MenuItem>();

        listView = (ListView) rootView.findViewById(R.id.menuList);
        listViewSet();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity activity = getActivity();
                ((OnMenuSelectedListener)activity).onMenuSelected((MenuItem) adapter.getItem(position));
            }
        });
        return rootView;
    }

    public void listViewSet() {
        Cursor c = mDBHelper.getAllMenusByMethod();
        TextView rName = (TextView) rootView.findViewById(R.id.rdetail_name);
        adapter = new MenuAdapter(getActivity(), R.layout.menu_item, data);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //http://hashcode.co.kr/questions/674
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.restaurant_detail_actionbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    final static int REQUEST_MENU_INSERT = 0;
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intent = new Intent(getActivity(), MenuInsertActivity.class);
        intent.putExtra("resName", getActivity().getIntent().getStringExtra("resName"));
        startActivityForResult(intent, REQUEST_MENU_INSERT);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MENU_INSERT && resultCode == RESULT_OK) {
            listViewSet();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getRestaurantData(){
        Cursor cursor = mDBHelper.getAllRestaurantsByMethod();

        while(cursor.moveToNext()) {
            if (cursor.getString(1).equals(getActivity().getIntent().getStringExtra("resName"))) {
                TextView name = (TextView) rootView.findViewById(R.id.rdetail_name);
                name.setText(cursor.getString(1));
                TextView address = (TextView) rootView.findViewById(R.id.rdetail_address);
                address.setText(cursor.getString(2));
                TextView phone = (TextView) rootView.findViewById(R.id.rdetail_phone);
                phone.setText(cursor.getString(3));
                TextView time = (TextView) rootView.findViewById(R.id.rdetail_time);
                time.setText(cursor.getString(4));
                ImageView image = (ImageView) rootView.findViewById(R.id.rdetail_img);
                String mPhotoFileName = cursor.getString(5);
                if (mPhotoFileName != null) {
                    File mPhotoFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), mPhotoFileName);
                    Uri uri = Uri.fromFile(mPhotoFile);
                    image.setImageURI(uri);
                }
            }
        }
    }

    public void callButtonClick(View view) {
        TextView phone_n = (TextView) rootView.findViewById(R.id.rdetail_phone);
        String num = phone_n.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+num));
        startActivity(intent);
    }
}
