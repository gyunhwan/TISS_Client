package com.example.dong.tiss2;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.dong.tiss2.MainActivity.latitudem;
import static com.example.dong.tiss2.MainActivity.longitudem;

public class SettingMapAct extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    Double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingmap);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Button joo= (Button) findViewById(R.id.jo);
        Button jpp= (Button) findViewById(R.id.jp);

        LatLng korea = new LatLng(36.68812, 127.78516);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea,8));
        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                latitude = point.latitude; // 위도
                longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });

        joo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitudem=latitude;
                longitudem=longitude;
                finish();
            }
        });
        jpp.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                getposi();
                LatLng korea = new LatLng(latitude, longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea,8));
                mMap.clear();
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });
    }
    public void getposi(){
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( SettingMapAct.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
    }
}
