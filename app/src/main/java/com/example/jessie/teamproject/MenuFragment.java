package com.example.jessie.teamproject;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    MenuAdapter adapter;
    ListView listView;
    ArrayList<MenuItem> data;
    DBHelper mDBHelper;

    static int index=-1;

    public MenuFragment() {
        // Required empty public constructor
    }

    public void setSelection(int i){
        index = i;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Intent intent = getActivity().getIntent();
        String detailImg = intent.getStringExtra("menuIcon");
        String detailName = intent.getStringExtra("menuName");
        String detailPrice = intent.getStringExtra("menuPrice");
        String detailInfo = intent.getStringExtra("menuInfo");
        String detailStar = intent.getStringExtra("menuStar");

        ImageView img = (ImageView) view.findViewById(R.id.detailImg);
        if (detailImg != null) {
            File mPhotoFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), detailImg);
            Uri uri = Uri.fromFile(mPhotoFile);
            img.setImageURI(uri);
        }

        TextView name = (TextView) view.findViewById(R.id.detailName);
        name.setText(detailName);

        TextView price = (TextView) view.findViewById(R.id.detailPrice);
        price.setText(detailPrice);

        TextView info = (TextView) view.findViewById(R.id.detailInfo);
        info.setText(detailInfo);

        TextView star = (TextView) view.findViewById(R.id.detailStar);
        star.setText(detailStar);

        return view;
    }

}
