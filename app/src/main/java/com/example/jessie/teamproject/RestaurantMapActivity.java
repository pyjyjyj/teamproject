package com.example.jessie.teamproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RestaurantMapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 100;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mLastLocation;
    private GoogleMap mGoogleMap = null;
    private int distanceSelect = 1000;
    private float zoomLevel = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDbHelper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_map);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkLocationPermissions()) {
            requestLocationPermissions(REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION);
        } else {
            getLastLocation();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button searchbtn = (Button) findViewById(R.id.button);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAddress();
            }
        });

//        insertRestaurant("밥퍼스", "대한민국 서울특별시 성북구 삼선동2가 330-12", "02-762-4437", "10:00~22:00", null);
//        insertRestaurant("할매순대국한성대역점", "대한민국 서울특별시 성북구 삼선동1가 13-4", "02-742-2655", "00:00~23:59", null);
//        insertRestaurant("박효신꼬지비어", "대한민국 서울특별시 성북구 삼선동2가 44-1", "02-742-4226", "18:00~04:00", null);
//        insertRestaurant("안즈나 선아 당신 생각", "대한민국 서울특별시 성북구 동소문동1가 111-2", "02-6465-3945", "10:00~23:00",null);
//        mDbHelper.insertRestaurantByMethod("소문난국수집", "서울특별시 성북구 성북동 58-26", "02-3672-6685", "10:00~22:00", null);
//        mDbHelper.insertRestaurantByMethod("왕코등갈비", "서울특별시 종로구 종로1.2.3.4가동 돈화문로 49", "02-766-4492", "17:00~23:30", null);
//        mDbHelper.insertRestaurantByMethod("삼숙이라면", "서울특별시 종로구 인사동 220-2", "02-720-9711", "08:30~21:30", null);
//        mDbHelper.insertRestaurantByMethod("오소리순대", "서울특별시 동대문구 제기동 1148-10", "02-918-9797", "10:30~22:00", null);
    }

    private boolean checkLocationPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    private void requestLocationPermissions(int requestCode) {
        ActivityCompat.requestPermissions(
                RestaurantMapActivity.this, // MainActivity 액티비티의 객체 인스턴스를 나타냄
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},// 요청할 권한 목록을 설정한 String 배열
                requestCode // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission required", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        Task task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    mLastLocation = location;
                    mLastLocation.setLatitude(37.581759);mLastLocation.setLongitude(127.010369);//현재 위치를 한성대로 설정
                    LatLng curLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation, zoomLevel));
                    startMarker();
                } else
                    Toast.makeText(getApplicationContext(), getString(R.string.no_location_detected), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurant_map_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.location:
                getLastLocation();
                return true;
            case R.id.distance1:
                item.setChecked(true);
                distanceSelect = 1000;
                zoomLevel = 14;
                getLastLocation();
                return true;
            case R.id.distance2:
                item.setChecked(true);
                distanceSelect = 2000;
                zoomLevel = 13.3f;
                getLastLocation();
                return true;
            case R.id.distance3:
                item.setChecked(true);
                distanceSelect = 3000;
                zoomLevel = 12.6f;
                getLastLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    private DBHelper mDbHelper;
    private void insertRestaurant(String name, String address, String phone, String time, String imgFileName) {
        long nOfRows = mDbHelper.insertRestaurantByMethod(name, address, phone, time, imgFileName);
        Intent intent = new Intent(this, RestarurantDetailActivity.class);
        intent.putExtra("resName", name);
        if (nOfRows > 0)
            Toast.makeText(this, nOfRows + " Record Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "No Record Inserted", Toast.LENGTH_SHORT).show();
    }

    private void addMarker(String name, String address){
        Location location = getLocationFromAddress(address);
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mGoogleMap.addMarker(
                new MarkerOptions().position(latLng).title(name).
                        icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map))
        );
    }

    private void startMarker() {
        mGoogleMap.clear();
        Cursor cursor = mDbHelper.getAllRestaurantsByMethod();
        int distance;
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            distance = getDistance(mLastLocation, address);
            if (distance < distanceSelect)
                addMarker(name, address);
            Log.i("거리", distanceSelect + "이내, " + name + ", 거리=" + distance);
        }
    }

    private int getDistance(Location startLocation, String endAddress) {
        // http://developer88.tistory.com/70
        Location endLocation = getLocationFromAddress(endAddress);
        int distance;
        distance = (int) startLocation.distanceTo(endLocation);
        return distance;
    }

    private Location getLocationFromAddress(String address) {
        Location location = new Location("location");
        try{
            Geocoder geocoder = new Geocoder(this, Locale.KOREAN);
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            location.setLatitude(addresses.get(0).getLatitude());
            location.setLongitude(addresses.get(0).getLongitude());
        } catch (IOException e){
            Log.e(getClass().toString(), "Failed in using Geocoder.", e);
        }
        return location;
    }

    private void getAddress() {
        Location searchLocation = new Location("searchLocation");
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            Cursor cursor = mDbHelper.getAllRestaurantsByMethod();
            EditText place = (EditText) findViewById(R.id.editText);
            TextView result = (TextView) findViewById(R.id.resultText);

            while (cursor.moveToNext()) {
                if(place.getText().toString().equals(cursor.getString(1))){
                    List<Address> addresses = geocoder.getFromLocationName(cursor.getString(2), 1);
                    result.setText(place.getText());
                    LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
                    searchLocation.setLatitude(addresses.get(0).getLatitude()); searchLocation.setLongitude(addresses.get(0).getLongitude());

                    mGoogleMap.clear();
                    Cursor c = mDbHelper.getAllRestaurantsByMethod();
                    int distance;
                    while (c.moveToNext()) {
                        String name = c.getString(1);
                        String address = c.getString(2);
                        if(name.equals(place.getText().toString()))
                            continue;
                        distance = getDistance(searchLocation, address);
                        if (distance < distanceSelect)
                            addMarker(name, address);
                    }

                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(location).
                                    title(place.getText().toString())
                    );
                    return;
                }
            }
            List<Address> addresses = geocoder.getFromLocationName(place.getText().toString(), 1);
            if (addresses.size() > 0) {
                Address bestResult = (Address) addresses.get(0);

                result.setText(String.format("[%s, %s]",
                        bestResult.getLatitude(),
                        bestResult.getLongitude()));
            }

            LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
            searchLocation.setLatitude(addresses.get(0).getLatitude()); searchLocation.setLongitude(addresses.get(0).getLongitude());

            mGoogleMap.clear();
            Cursor c = mDbHelper.getAllRestaurantsByMethod();
            int distance;
            while (c.moveToNext()) {
                String name = c.getString(1);
                String address = c.getString(2);
                if(name.equals(place.getText().toString()))
                    continue;
                distance = getDistance(searchLocation, address);
                if (distance < distanceSelect)
                    addMarker(name, address);
            }

            mGoogleMap.addMarker(
                    new MarkerOptions().
                            position(location).
                            title(place.getText().toString())
            );
        } catch (IOException e) {
            Log.e(getClass().toString(), "Failed in using Geocoder", e);
            return;
        }
    }
}
