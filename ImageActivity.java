package com.example.a0000142025.postget150507app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;


public class ImageActivity extends Activity {


    Bitmap _displayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        final ImageActivity ia = this;

        //別スレッドで非同期処理
        MyAsyncTask asynctask = new MyAsyncTask(ia);
        asynctask.execute("image","hoge","foo");

    }

    //別スレッドでの処理
    public String methods(){
        String result = "empty";

        //URLを受け取るとき
//        Intent intent = getIntent();
//        String photourl = intent.getStringExtra("urlstring");
//        _displayImage = getBitmap(photourl);
//        result = photourl;

        //画像を受け取るとき
//        MySerializable serializable = (MySerializable)getIntent().getSerializableExtra("serializablestring");
//        _displayImage = serializable._serializableBitmap;

        //③Applicationクラスを経由して画像を受け取るとき
        MyApplication app = (MyApplication)getApplication();
        _displayImage = app.getObj();

        return result;
    }


    ////該当画像のGet処理
    public Bitmap getBitmap(String photourl){
        Bitmap bitmap = null;
        try {
            HttpGet getpic = new HttpGet(photourl);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(getpic);
            InputStream in = response.getEntity().getContent();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            Log.e("TAG", "getResource：" + e.toString());
        }
        return bitmap;
    }


    //画像取得後の処理
    public void result_job(String result){
        //画像表示
        ImageView iv = (ImageView)findViewById(R.id.imageView10);
        iv.setImageBitmap(_displayImage);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
