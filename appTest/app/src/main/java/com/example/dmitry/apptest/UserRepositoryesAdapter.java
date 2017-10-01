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

import com.example.dmitry.apptest.GitHubObjects.ReposList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dmitry on 30.09.17.
 */

public class UserRepositoryesAdapter extends BaseAdapter {
    private final int yy = 5;
    private Context context;
    ReposList reposList;
    ArrayList<String> imageUrls = new ArrayList<>();
    HashMap<String, Bitmap> images = new HashMap<>();
    HashMap<String, View> views = new HashMap<>();

    UserRepositoryesAdapter(Context context
            , ReposList reposList
            , ArrayList<String> imageUrls
            , HashMap<String, Bitmap> images
            , HashMap<String, View> views) {
        this.reposList = reposList;
        this.images = images;
        this.imageUrls = imageUrls;
        this.context = context;
        this.views = views;

    }

    @Override
    public int getCount() {
        return reposList.repos.size() * yy + 5;
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
        if(i < 5) {
            final String[] strs = {"Имя гитхаба", "Автор", "Картинка", "Просмотры", "Фоловеры"};
            TextView textView;
            if (view == null || view.getClass() != TextView.class) {
                textView = new TextView(context);
            }
            else {
                textView = (TextView)view;
            }
            textView.setLayoutParams(new GridView.LayoutParams(
                    (int)context.getResources().getDimension(R.dimen.UserRepoW),
                    (int)context.getResources().getDimension(R.dimen.UserRepoH)));
            textView.setPadding(8, 8, 8, 8);
            textView.setText(strs[i]);
            return textView;
        }
        i -= 5;
        if(i % yy == 2) {
            ImageView imageView;
            if (view == null || view.getClass() !=ImageView.class) {
                imageView = new ImageView(context);
            } else {
                imageView = (ImageView) view;
            }
            if (images.get(imageUrls.get(i / yy)) == null) {
                imageView.setImageResource(R.mipmap.ic_launcher_round);
            }
            else {
                imageView.setImageBitmap(images.get(imageUrls.get(i / 5)));
                views.put(imageUrls.get(i / yy), imageView);
            }

            imageView.setLayoutParams(new GridView.LayoutParams(
                    (int)context.getResources().getDimension(R.dimen.UserRepoH),
                    (int)context.getResources().getDimension(R.dimen.UserRepoH)));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            return imageView;

        } else {
            TextView textView;
            if (view == null || view.getClass() != TextView.class) {
                textView = new TextView(context);
                textView.setLayoutParams(new GridView.LayoutParams(
                        (int)context.getResources().getDimension(R.dimen.UserRepoW),
                        (int)context.getResources().getDimension(R.dimen.UserRepoH)));
                textView.setPadding(8, 8, 8, 8);
            }
            else {
                textView = (TextView)view;
            }


            textView.setTextSize(10);
            if (i % yy == 0) {
                textView.setText(reposList.repos.get(i / yy).name);
            }
            else if (i % yy == 1) {
                textView.setText(reposList.repos.get(i / yy).owner.login);
            }
            else if (i % yy == 3) {
                textView.setText(String.valueOf(reposList.repos.get(i / yy).watchers));
            }
            else if (i % yy == 4) {
                textView.setText(String.valueOf(reposList.repos.get(i / yy).forks));
            }
            return textView;

        }
    }
}
