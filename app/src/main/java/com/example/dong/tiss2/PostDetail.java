package com.example.dong.tiss2;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.dong.tiss2.MainActivity.latitudem;
import static com.example.dong.tiss2.MainActivity.longitudem;

public class PostDetail  extends AppCompatActivity {
    private SharedPreferences appData;
    Session se=new Session();
    JSONObject info;
    String idi;
    JSONArray big;
    JSONObject hi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailx);
        Intent intent = getIntent();
        TextView tx1 = (TextView) findViewById(R.id.title);
        TextView tx2 = (TextView) findViewById(R.id.content);
        TextView tx3 = (TextView) findViewById(R.id.ui);
        Button suu = (Button) findViewById(R.id.su);
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        try {
            hi=se.load(appData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String ti = intent.getExtras().getString("title");
        idi = intent.getExtras().getString("id");
        String co = intent.getExtras().getString("content");
        String uii=intent.getExtras().getString("userId");

        tx1.setText(ti);
        tx2.setText(co);
        tx3.setText("작성자 : "+uii);
        send();

        final LinearLayout lm = (LinearLayout) findViewById(R.id.ll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        for (int j = 0; j < big.length(); j++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            final TextView txt = new TextView(this);
            txt.setId(j + 1);
            try {
                txt.setText(big.getJSONObject(j).getString("userId")+" : "+big.getJSONObject(j).getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            txt.setLayoutParams(params);
            ll.addView(txt);
            lm.addView(ll);
        }
        suu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String f = ((EditText)(findViewById(R.id.f))).getText().toString();
                JSONObject go = new JSONObject();
                try {
                    go.accumulate("postId",idi);
                    go.accumulate("userId",hi.getString("id"));
                    go.accumulate("content",f);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Sending networkTask = new Sending("http://192.168.64.142:8080/app/comment", go,"POST","application/json");
                try {
                    networkTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
    public void send(){
        String hi = null;
        StringBuilder url = new StringBuilder("http://192.168.64.142:8080/app/comment/"+idi);
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
