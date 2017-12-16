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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.MenuItem;
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

//        insertRestaurant("밥퍼스", "대한민국 서울특별시 성북구 삼선동2가 330-12", "02-762-4437", "10:00~22:00", null);
//        insertRestaurant("할매순대국한성대역점", "대한민국 서울특별시 성북구 삼선동1가 13-4", "02-742-2655", "00:00~23:59", null);
//        insertRestaurant("박효신꼬지비어", "대한민국 서울특별시 성북구 삼선동2가 44-1", "02-742-4226", "18:00~04:00", null);
//        insertRestaurant("안즈나 선아 당신 생각", "대한민국 서울특별시 성북구 동소문동1가 111-2", "02-6465-3945", "10:00~23:00",null);
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
                    LatLng curLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,15));
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
                return true;
            case R.id.distance2:
                item.setChecked(true);
                return true;
            case R.id.distance3:
                item.setChecked(true);
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
         try{
            Geocoder geocoder = new Geocoder(this, Locale.KOREAN);
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            LatLng location = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            mGoogleMap.addMarker(
                    new MarkerOptions().
                            position(location).
                            title(name.toString()).
                            alpha(0.8f).
                            icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant_locator_for_map))
            );
        } catch (IOException e){
            Log.e(getClass().toString(), "Failed in using Geocoder.", e);
            return;
        }
    }

    private void startMarker(){
        Cursor cursor = mDbHelper.getAllRestaurantsByMethod();
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            addMarker(name, address);
        }
    }
}
