package com.example.dong.tiss2;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class NowGps extends Activity {
    JSONObject gps=new JSONObject();
    public JSONObject getG() throws JSONException {
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( NowGps.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            gps.accumulate("positionX",longitude);
            gps.accumulate("positionY",latitude);
        }
        return gps;
    }
}
