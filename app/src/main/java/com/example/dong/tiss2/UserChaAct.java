package com.example.dong.tiss2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class UserChaAct extends AppCompatActivity {
    private SharedPreferences appData;
    String url = "http://192.168.64.142:8080/app/user";
    Session se=new Session();
    JSONObject hi;
    String a;
    String jid,jpw,jpn,jn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userchax);
        Button ch=findViewById(R.id.cha);
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        try {
            hi=se.load(appData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jid=hi.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 jpw=((EditText)(findViewById(R.id.pw))).getText().toString();
                 jpn=((EditText)(findViewById(R.id.pn))).getText().toString();
                 jn=((EditText)(findViewById(R.id.n))).getText().toString();
                JSONObject go = new JSONObject();
                try {
                    go.accumulate("id",jid);
                    go.accumulate("pw",jpw);
                    go.accumulate("phoneNum",jpn);
                    go.accumulate("name",jn);
                    se.save(appData,jid,jpw,jpn,jn,true);
                    //
                    Sending networkTask = new Sending(url, go,"PUT","application/json");
                    a = networkTask.execute().get();
                    Log.d("HI", a);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
}
