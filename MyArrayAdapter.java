package com.example.a0000142025.postget150507app;

/**
 * Created by 0000142025 on 2015/05/13.
 */

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ArrayAdapter;



public class MyArrayAdapter extends ArrayAdapter<Bitmap>{

    private int _resourceId;

    public MyArrayAdapter(Context context, int resource, List<Bitmap> objects) {
        super(context, resource, objects);
        _resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(_resourceId, null);
        }

        ImageView view = (ImageView) convertView;
        view.setImageBitmap(getItem(position));

        return view;
    }

}
