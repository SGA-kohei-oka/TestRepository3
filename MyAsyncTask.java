package com.example.a0000142025.postget150507app;

/**
 * Created by 0000142025 on 2015/05/07.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;


public class MyAsyncTask extends AsyncTask<String,String,String> {

    private MainActivity _main;
    private ImageActivity _image;
    private String _hoge = "hoge";

    public MyAsyncTask(Object object) {
        if(object.getClass().getSimpleName().equals("MainActivity")) {
            this._main = (MainActivity) object;
        }
        else if(object.getClass().getSimpleName().equals("ImageActivity")) {
            this._image = (ImageActivity) object;
            _hoge = "Image done.";
        }
    }


//    public MyAsyncTask(ImageActivity ia) {
//        this._image = ia;
//    }

    @Override       //別スレッドで行う処理
    protected String doInBackground(String...value) {

        String  arg1 = value[0];//executeで渡される"hoge"
        //String  arg2 = value[1];//executeで渡される"foo"
        //String  arg3 = value[2];//executeで渡される"bar"

        String result = "empty";

        if(_hoge.equals("Image done.")){
            result = _image.methods();
        }
        else{
            result = _main.methods(arg1);
        }

        return result;
    }

    /*
    @Override
    protected void onProgressUpdate(String... values) {
        //
    }
    */


    @Override       //最後にメインスレッドで行う処理
    protected void onPostExecute(String result) {

        //UIの描画
        if(_hoge.equals("Image done.")){
            _image.result_job(result);
        }
        else{
            _main.result_job(result);
        }


    }


}
