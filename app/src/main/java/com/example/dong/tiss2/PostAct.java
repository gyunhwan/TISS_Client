package com.example.dong.tiss2;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.dong.tiss2.MainActivity.latitudem;
import static com.example.dong.tiss2.MainActivity.longitudem;

public class PostAct extends FragmentActivity implements OnMapReadyCallback {
    int i;
    String o;
    StringBuilder url = new StringBuilder("http://192.168.64.142:8080/app/post?keyword=");
    private GoogleMap mMap=null;
    JSONArray big;
    private Button ssu;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postx);
        ssu=(Button)findViewById(R.id.su);
        context=this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        send();
        final LinearLayout lm = (LinearLayout) findViewById(R.id.ll);
        // linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        for (int j = 0; j <big.length(); j++) {
            // LinearLayout 생성
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            // 버튼 생성
            final Button btn = new Button(this);
            // setId 버튼에 대한 키값
            btn.setId(j + 1);
            try {
                btn.setText(big.getJSONObject(j).getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            btn.setLayoutParams(params);
            final int position = j;
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PostAct.this,
                            PostDetail.class);
                    String op;
                    try {
                        intent.putExtra("id",big.getJSONObject(position).getString("id"));
                        intent.putExtra("userId",big.getJSONObject(position).getString("userId"));
                        intent.putExtra("title",big.getJSONObject(position).getString("title"));
                        intent.putExtra("content",big.getJSONObject(position).getString("content"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);
                }
            });
            //버튼 add
            ll.addView(btn);
            //LinearLayout 정의된거 add
            lm.addView(ll);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        search();
        ssu.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                mMap.clear();
                o = ((EditText)(findViewById(R.id.kw))).getText().toString();
                search();
            }
        });
        LatLng korea = new LatLng(latitudem, longitudem);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(korea,14));
    }
    public void search(){
        JSONObject fsf = null;
        send();
            for (i = 0; i < big.length(); i++) {
                double x1 = 0, y1 = 0;
                String t = null;
                MarkerOptions mOptions = new MarkerOptions();
                try {
                    fsf = big.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    t = fsf.getString("title");
                    x1 = fsf.getDouble("positionX");
                    y1 = fsf.getDouble("positionY");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mOptions.title(t);
                mOptions.snippet(t);
                mOptions.position(new LatLng(x1, y1));
                mMap.addMarker(mOptions);
            }
    }
    public void send(){
        String hi = null;
        url = new StringBuilder("http://192.168.64.142:8080/app/post?keyword=");
        if(o==null)
            url.append("&").append("positionX=").append(latitudem).append('&').append("positionY=").append(longitudem);
        else
            url.append(o).append("&").append("positionX=").append(latitudem).append('&').append("positionY=").append(longitudem);
        Log.d("HI11", url.toString());
        Sending networkTask = new Sending(url.toString(), (JSONObject) null,"GET","Application/x-www-form-urlencode");
        try {
            hi = networkTask.execute().get();
            Log.d("HI", hi);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            big=new JSONArray(hi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}