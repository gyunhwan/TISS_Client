package com.example.dong.tiss2;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Session {
    protected void save(SharedPreferences appData,String jid, String jpw, String jpn, String jn,Boolean on) {
        SharedPreferences.Editor editor = appData.edit();

        editor.putBoolean("SAVE_LOGIN_DATA", on);
        editor.putString("ID", jid);
        editor.putString("PWD", jpw);
        editor.putString("PN", jpn);
        editor.putString("N", jn);
        editor.apply();
    }
    JSONObject load(SharedPreferences appData) throws JSONException {
        JSONObject session = new JSONObject();
         boolean saveLoginData;
         String id;
         String pwd;
         String pn;
         String n;
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
        pn = appData.getString("PN", "");
        n = appData.getString("N", "");

        session.accumulate("ch", saveLoginData);
        session.accumulate("id", id);
        session.accumulate("pwd", pwd);
        session.accumulate("pn", pn);
        session.accumulate("n", n);
        return session;
    }
}

