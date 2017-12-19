package com.example.jessie.teamproject;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {
    static String detailImg = "";
    static String detailName = "";
    static String detailPrice = "";
    static String detailInfo = "";
    static String detailStar = "";

    public MenuFragment() {
        // Required empty public constructor
    }

    public void setSelection(String img, String name, String price, String info, String star){
        detailImg = img;
        detailName = name;
        detailPrice = price;
        detailInfo = info;
        detailStar = star;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);

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
