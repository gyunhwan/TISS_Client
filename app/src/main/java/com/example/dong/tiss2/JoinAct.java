package com.example.dong.tiss2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class JoinAct extends Activity  {

    JSONObject requestData = new JSONObject();
    String hi;
    String url = "http://192.168.64.142:8080/app/join";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinx);

        Button canbt = (Button) findViewById(R.id.cancle);
        Button sumbt = (Button) findViewById(R.id.summit);
        final String token=FirebaseInstanceId.getInstance().getToken();
        Log.d("tokne",token);

        canbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        sumbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String jid,jpw,pn,n;
                jid = ((EditText)(findViewById(R.id.id))).getText().toString();
                jpw = ((EditText)(findViewById(R.id.pw))).getText().toString();
                pn = ((EditText)(findViewById(R.id.phoneNum))).getText().toString();
                n = ((EditText)(findViewById(R.id.name))).getText().toString();
                JSONObject go = new JSONObject();
                try {
                    go.accumulate("id",jid);
                    go.accumulate("pw",jpw);
                    go.accumulate("phoneNum",pn);
                    go.accumulate("name",n);
                    go.accumulate("token",token);
                    //
                    Sending networkTask = new Sending(url, go,"POST","application/json");
                    hi = networkTask.execute().get();
                    Log.d("HI", hi);
                    requestData = new JSONObject(hi);
                    Log.d("HI", requestData.toString());

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