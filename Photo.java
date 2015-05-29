package com.example.a0000142025.postget150507app;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Created by 0000142025 on 2015/05/15.
 */
@JsonIgnoreProperties(ignoreUnknown=true)   //使わないパラメータは無視
public class Photo {

    public String id;
    public String secret;
    public String server;
    public int farm;

//    String owner;
//    String title;
//    int ispublic;
//    int isfriend;
//    int isfamily;



    public String getURL(){
        String photourl = "http://farm" + String.valueOf(farm) + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg";
        return photourl;
    }


}
