package com.example.dong.tiss2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAct extends AppCompatActivity {
    private SharedPreferences appData;
    Session se=new Session();
    JSONObject hi;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userx);
        TextView idj=findViewById(R.id.id);;
        TextView pwj=findViewById(R.id.pw);;
        TextView pnj=findViewById(R.id.pn);;
        TextView nj=findViewById(R.id.n);
        Button opo=findViewById(R.id.cha);
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        try {
            hi=se.load(appData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            idj.setText(hi.getString("id"));
            pwj.setText(hi.getString("pwd"));
            pnj.setText(hi.getString("pn"));
            nj.setText(hi.getString("n"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        opo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserAct.this,
                        UserChaAct.class);
                startActivity(intent);
            }
        });
    }
}
