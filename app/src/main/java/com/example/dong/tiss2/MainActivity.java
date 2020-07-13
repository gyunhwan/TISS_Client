package com.example.dong.tiss2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    static double longitudem,latitudem;
    private SharedPreferences appData;
    Session se=new Session();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hi=false;
        setContentView(R.layout.activity_main);
        Button logout = (Button) findViewById(R.id.logoutbt);
        Button post = (Button) findViewById(R.id.postbt);
        Button en = (Button) findViewById(R.id.envi);
        Button regis = (Button) findViewById(R.id.regisbt);
        getposi();
        Log.d("longitude", String.valueOf(longitudem));
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        try {
            hi=(se.load(appData)).getBoolean("ch");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(hi==false)
        {
            Intent intent = new Intent(MainActivity.this,
                    LoginAct.class);
            startActivity(intent);
        }
        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        PostAct.class);
                startActivity(intent);
            }
        });
        regis.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        PostSendAct.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                se.save(appData,"","","","",false);
                Intent intent = new Intent(MainActivity.this,
                        LoginAct.class);
                startActivity(intent);
            }
        });
        en.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        SettingAct.class);
                startActivity(intent);
            }
        });
    }
    public void getposi(){
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitudem = location.getLongitude();
            latitudem = location.getLatitude();
        }
    }
}