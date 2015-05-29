package com.example.a0000142025.postget150507app;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by 0000142025 on 2015/05/15.
 */


public class MyApplication extends Application{
    private final String TAG = "APPLICATION";
    private Bitmap obj;

    @Override
    public void onCreate() {
        //Application作成時
        Log.v(TAG,"--- onCreate() in ---");
    }

    @Override
    public void onTerminate() {
        //Application終了時
        Log.v(TAG,"--- onTerminate() in ---");
    }

    public void setObj(Bitmap bmp){
        obj = bmp;
    }

    public Bitmap getObj(){
        return obj;
    }

    public void clearObj(){
        obj = null;
    }
}