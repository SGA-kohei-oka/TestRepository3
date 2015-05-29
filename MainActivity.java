package com.example.a0000142025.postget150507app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


//追加
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;

import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;

import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import android.widget.GridView;


import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class MainActivity extends Activity {

    String _photourls[] = new String[100];
    Bitmap _bitmaps[] = new Bitmap[100];
    MyArrayAdapter _adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getBtn = (Button)findViewById(R.id.Button001);
        Button postBtn = (Button)findViewById(R.id.Button002);
        Button deleteBtn = (Button)findViewById(R.id.Button003);
        Button flickrBtn = (Button)findViewById(R.id.Button004);


        //final AsyncJob asynctask = new AsyncJob(this);
        final MainActivity main = this;

        getBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                //別スレッドで非同期処理
                MyAsyncTask asynctask = new MyAsyncTask(main);
                asynctask.execute("get","hoge","foo");
            }
        });
        postBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                //別スレッドで非同期処理
                MyAsyncTask asynctask = new MyAsyncTask(main);
                asynctask.execute("post","hoge","foo");
            }
        });
        deleteBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                //別スレッドで非同期処理
                MyAsyncTask asynctask = new MyAsyncTask(main);
                asynctask.execute("delete","hoge","foo");
            }
        });
        flickrBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                //別スレッドで非同期処理
                MyAsyncTask asynctask = new MyAsyncTask(main);
                asynctask.execute("flickr","hoge","foo");
            }
        });
    }


    public String methods(String method){
        String result = null;
        if(method.equals("post")){
            //Post実行
            httpPost();
            result = "Post done.";
        }
        else if (method.equals("get")){
            //Get実行　resに格納
            result = httpGet();
        }
        else if (method.equals("delete")){
            //Delete実行
            httpDelete();
            result = "Delete done.";
        }
        else if (method.equals("flickr")){
            //Flickr
            httpFlickr();
            result = "Flickr done.";
        }
        return result;
    }


    //Postを実行
    public void httpPost () {
        HttpPost post = new HttpPost("http://192.168.61.55:8080/test/database/");

        HttpClient client = new DefaultHttpClient();

        EditText et1 = (EditText)findViewById(R.id.editText1);
        EditText et2 = (EditText)findViewById(R.id.editText2);

        //クエリを設定
        ArrayList<NameValuePair> list = new ArrayList <NameValuePair>();
        list.add( new BasicNameValuePair("code", et1.getText().toString()));
        list.add( new BasicNameValuePair("name", et2.getText().toString()));

        //Postを実行
        try {
            post.setEntity(new UrlEncodedFormEntity(list,"UTF-8"));
            HttpResponse response = client.execute(post);
        } catch (Exception e) {
            Log.i("httppost",null, ((Exception)e));
        }
    }


    //Getを実行して取得結果を返す
    public String httpGet () {
        //HttpGet get = new HttpGet("http://192.168.61.55:8080/test/database/?method=get");
        HttpGet get = new HttpGet("http://192.168.61.55:8080/test/database/");
        HttpClient client = new DefaultHttpClient();
        String str = "empty";
        try {
            HttpResponse response = client.execute(get);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
            Log.d("HTTP", str);
        } catch (Exception ex) {
            System.out.println(ex);
            Log.d("tag", "error");
        }
        return str;
    }


    //Deleteを実行
    public void httpDelete () {
        //EditText et1 = (EditText)findViewById(R.id.editText1);
        EditText et2 = (EditText)findViewById(R.id.editText2);
        String url2 = "http://192.168.61.55:8080/test/database/?name=" + et2.getText().toString();
        HttpDelete delete = new HttpDelete(url2);
        HttpClient client = new DefaultHttpClient();


        //Deleteを実行
        try {
            HttpResponse response = client.execute(delete);
        } catch (Exception e) {
            Log.i("httpdelete",null, ((Exception)e));
        }
    }

    //flickr
    public void httpFlickr () {
        //フォームの入力値から取得する情報を決定
        EditText et1 = (EditText)findViewById(R.id.editText1);
        EditText et2 = (EditText)findViewById(R.id.editText2);
        String geturl = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3b95b3a46552591aaa157ed0a41b2eeb&tags="
                + et2.getText().toString() + "&format=json&&nojsoncallback=1&per_page=" + et1.getText().toString();
        HttpGet get = new HttpGet(geturl);
        HttpClient client = new DefaultHttpClient();
        String str = "empty";
        //画像検索
        try {
            HttpResponse response = client.execute(get);
            str = EntityUtils.toString(response.getEntity());
            //str = str.substring(14, str.length()-1);              //余計な文字　"jsonFlickrApi("　と　")"　の削除が必要
            Log.d("HTTP", str);
        } catch (Exception ex) {
            System.out.println(ex);
            Log.d("tag", "error");
        }

        ArrayList<Bitmap> list = new ArrayList<Bitmap>();


        try{

            //Jackson処理
            ObjectMapper mapper = new ObjectMapper();
            FlickrPhotoInfo flickrPhotoInfo = mapper.readValue(str, FlickrPhotoInfo.class);


/*
            //JSONをパース
            JSONObject json = new JSONObject(str);
            JSONObject photos = json.getJSONObject("photos");
            JSONArray photoArray = photos.getJSONArray("photo");

            for(int i = 0; i < photoArray.length(); i++) {

                //次のphotoを選択
                JSONObject photo = photoArray.getJSONObject(i);
                //選択したphotoの各パラメータ取得
                String id = photo.getString("id");
                //String owner = photo0.getString("owner");
                int farm = photo.getInt("farm");
                String server = photo.getString("server");
                String secret = photo.getString("secret");

                //取得したパラメータで該当画像のURL生成
                String photourl = "http://farm" + String.valueOf(farm) + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
*/
            //Jackson処理
            for(int i = 0; i <  flickrPhotoInfo.getPhotos().getPhoto().size(); i++) {
                //Jackson処理
                String photourl = flickrPhotoInfo.getPhotos().getPhoto().get(i).getURL();


                _photourls[i] = photourl;
                //生成URLから該当画像を取得しlistに追加[
                Bitmap bmp = getBitmap(photourl);
                _bitmaps[i] = bmp;
                list.add(bmp);
            }

/*
        }catch(JSONException e){
        Log.e("log_tag", "Error parsing data " + e.toString());
*/
        }catch(IOException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        //GridViewに挿入するAdapterを用意
        _adapter = new MyArrayAdapter(getApplicationContext(), R.layout.list_item, list);

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



    //onPostExecuteで実行される関数
    public void result_job(String result){
        Log.e("RES",result);

        //結果を描画(UIは要メインスレッド)
        TextView tv = (TextView)findViewById(R.id.textView001);
        tv.setText(result);
        Log.d("fin", result);

        //GridViewに描画
        GridView gridView = (GridView) findViewById(R.id.gridView001);
        gridView.setAdapter(_adapter);


        //gridView.setOnItemClickListener(gridView.getOnItemClickListener());
        gridView.setOnItemClickListener(new OnItemClickListener() {
           // 項目がクリックされた時のハンドラ
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // クリックされた時の処理を記述
                Intent intent = new Intent();
                intent.setClassName("com.example.a0000142025.postget150507app", "com.example.a0000142025.postget150507app.ImageActivity");

                //①URLを渡すとき
//                intent.putExtra("urlstring", String.valueOf(_photourls[(int)id])) ;

                //②画像を渡すとき
//                MySerializable serializable = new MySerializable();
//                serializable._serializableBitmap = _bitmaps[(int)id];
//                intent.putExtra("serializablestring", serializable);

                //③Applicationクラスを経由して画像を渡すとき
                MyApplication app = (MyApplication)getApplication();
                app.setObj(_bitmaps[(int)id]);


                startActivity(intent);
            }
        });



        //取得した画像を表示
//        ImageView iv = (ImageView)findViewById(R.id.imageView001);
//        iv.setImageBitmap(bitmap);


        //文字列をhtml形式で出力する
        //WebView wv = new WebView(getApplicationContext());
        //WebView wv = (WebView)findViewById(R.id.webView1);
        //wv.loadData(result, "text/html", "UTF-8");
        //setContentView(wv);
    }








    //追加
    /*
    public List<NameValuePair> setJson (String objName, String... str) {
        JSONObject object = new JSONObject();
        try {
            for(int i = 1; i < str.length;i++){
                object.put("val_" + i, str[i]);
            }
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(objName, object.toString()));
            return params;
        } catch (Exception e) {
            Log.i("json","error");
            return null;
        }
    }
    */






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
