package com.example.dong.tiss2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.example.dong.tiss2.MainActivity.latitudem;
import static com.example.dong.tiss2.MainActivity.longitudem;


public class PostSendAct extends AppCompatActivity {
    ImageView imageView;
    private SharedPreferences appData;
    Bitmap img;
    String url = "http://192.168.64.142:8080/app/post";
    Session se=new Session();
    String title,content;
    JSONObject session;
    File tempFile;
    JSONObject go = new JSONObject();
    HashMap<String, Object> goo = new HashMap();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postsendx);
        imageView = (ImageView)findViewById(R.id.image);
       // Button bt= findViewById(R.id.button);
        Button su = findViewById(R.id.sum);

        /*bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });*/
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        try {
            session=se.load(appData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        su.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = ((EditText)(findViewById(R.id.title))).getText().toString();
                content = ((EditText)(findViewById(R.id.content))).getText().toString();
                putgo();
                Sending networkTask = new Sending(url, go,"POST","application/json");
//                Sending networkTask = new Sending(url+"/file/"+title, goo,"POST","application/x-www-form-urlencoded");
                try {
                    networkTask.execute().get();
//                    networ.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }
    public void putgo(){
        try {
            go.accumulate("userId", session.getString("id"));
            go.accumulate("title", title);
            go.accumulate("content", content);
            go.accumulate("positionX", latitudem);
            go.accumulate("positionY", longitudem);
            goo.put("file",tempFile);
            Log.d("Title", String.valueOf(go));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    Context context=getApplicationContext();
                    ssaveBitmapToJpeg(context,img,title);
                    // 이미지 표시
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean ssaveBitmapToJpeg(Context context,Bitmap bitmap, String name){

        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로

        String fileName = name + ".jpg";  // 파일이름은 마음대로!

         tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // 파일을 생성해주고

            FileOutputStream out = new FileOutputStream(tempFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌

            out.close(); // 마무리로 닫아줍니다.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
