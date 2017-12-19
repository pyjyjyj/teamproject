package com.example.jessie.teamproject;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jessie on 2017-10-14.
 */

class MenuItem {
    String menuIcon;
    String menuName;
    String menuPrice;
    String menuInfo;
    String menuStar;

    MenuItem(String menuName, String menuPrice, String menuInfo, String menuStar, String menuIcon) {
        this.menuIcon = menuIcon;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuInfo = menuInfo;
        this.menuStar = menuStar;
    }
}

public class MenuAdapter extends BaseAdapter {
    private Context mContext;
    private int mResource;
    private ArrayList<MenuItem> mItems = new ArrayList<>();

    public MenuAdapter(Context context, int resource, ArrayList<MenuItem> items) {
        mContext = context;
        mResource = resource;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        ImageView icon = (ImageView) convertView.findViewById(R.id.menuIcon);
        if (mItems.get(position).menuIcon != null) {
            File mPhotoFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), mItems.get(position).menuIcon);
            Uri uri = Uri.fromFile(mPhotoFile);
            icon.setImageURI(uri);
        }

        TextView menuName = (TextView) convertView.findViewById(R.id.menuName);
        menuName.setText(mItems.get(position).menuName);

        TextView menuPrice = (TextView) convertView.findViewById(R.id.menuPrice);
        menuPrice.setText(mItems.get(position).menuPrice);

        return convertView;
    }
}
