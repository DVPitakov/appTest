package com.example.dmitry.apptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmitry.apptest.GitHubObjects.CommitsList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dmitry on 01.10.17.
 */

public class CommitsAdapter extends BaseAdapter {
    private Context context;
    CommitsList commitsList;

    CommitsAdapter(Context context
            , CommitsList commitsList) {
        this.context = context;
        this.commitsList = commitsList;

    }

    @Override
    public int getCount() {
        return commitsList.commits.size() * 2;

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
        switch (i % 4) {
            case 0: {
                textView.setText(commitsList.commits.get(i >> 2).sha);
                break;
            }
            case 1: {
                textView.setText(commitsList.commits.get(i >> 2).message);
                break;
            }
            case 2: {
                textView.setText(commitsList.commits.get(i >> 2).author);
                break;
            }
            case 3: {
                textView.setText(commitsList.commits.get(i >> 2).date);
                break;
            }
        }
        return textView;
    }
}
