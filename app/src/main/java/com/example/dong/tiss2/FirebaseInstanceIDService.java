package com.example.dong.tiss2;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIDService";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, token);
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {//appserver에서 토큰 사용할때 여기를 활용해라

    }
}