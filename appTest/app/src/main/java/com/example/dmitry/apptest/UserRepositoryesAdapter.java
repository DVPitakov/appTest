package com.example.dmitry.apptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dmitry on 30.09.17.
 */

public class UserRepositoryesAdapter extends BaseAdapter {
    private Context context;
    ArrayList<String> logins = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imageUrls = new ArrayList<>();
    HashMap<String, Bitmap> images = new HashMap<>();
    HashMap<String, View> views = new HashMap<>();

    UserRepositoryesAdapter(Context context
            , ArrayList<String> logins
            , ArrayList<String> names
            , ArrayList<String> imageUrls
            , HashMap<String, Bitmap> images
            , HashMap<String, View> views) {
        this.logins = logins;
        this.names = names;
        this.images = images;
        this.imageUrls = imageUrls;
        this.context = context;
        this.views = views;

    }

    @Override
    public int getCount() {
        return logins.size() * 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i % 3 == 2) {
            ImageView imageView;
            if (view == null) {
                imageView = new ImageView(context);
            } else {
                imageView = (ImageView) view;
            }
            if (images.get(imageUrls.get(i / 3)) == null) {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }
            else {
                imageView.setImageBitmap(images.get(imageUrls.get(i / 3)));
            }
            views.put(imageUrls.get(i / 3), imageView);

            imageView.setLayoutParams(new GridView.LayoutParams(120, 120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            return imageView;
        } else {
            TextView textView;
            if (view == null) {
                textView = new TextView(context);
                textView.setLayoutParams(new GridView.LayoutParams(120, 120));
                textView.setPadding(8, 8, 8, 8);
            }
            else {
                textView = (TextView)view;
            }
            if (i % 3 == 0) {
                textView.setText(names.get(i / 3));
            }
            else {
                textView.setText(logins.get(i / 3));
            }
            return textView;

        }
    }
}
