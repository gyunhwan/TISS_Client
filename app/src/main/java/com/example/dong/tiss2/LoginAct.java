package com.example.dong.tiss2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;



public class LoginAct extends AppCompatActivity {
    private SharedPreferences appData;
    JSONObject requestData;
    String url = "http://192.168.64.142:8080/app/login";
    String jid, jpw,hi=null;
    Session session= new Session();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginx);
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        Button login = (Button) findViewById(R.id.loginbt);
        Button join = (Button) findViewById(R.id.joinbt);
        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(LoginAct.this,
                        JoinAct.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                jid = ((EditText)(findViewById(R.id.id))).getText().toString();
                jpw = ((EditText)(findViewById(R.id.pw))).getText().toString();
                try {
                    JSONObject go = new JSONObject();
                    go.accumulate("id",jid);
                    go.accumulate("pw",jpw);
                    Log.d("GO", go.toString());

                    Sending networkTask = new Sending(url, go,"POST","application/json");
                    hi = networkTask.execute().get();
                    Log.d("HI", hi);
                    if(hi.equals("1")){
                        Toast.makeText(getApplicationContext(), "로그인 불가능" , Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("HI", "HI");
                        requestData = new JSONObject(hi);
                        session.save(appData, requestData.getString("id"), requestData.getString("pw"), requestData.getString("phoneNum"), requestData.getString("name"), true);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}