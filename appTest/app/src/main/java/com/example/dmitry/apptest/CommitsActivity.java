package com.example.dmitry.apptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.dmitry.apptest.GitHubObjects.CommitsList;
import com.example.dmitry.apptest.GitHubObjects.GitHubImage;
import com.example.dmitry.apptest.GitHubObjects.GitHubObject;
import com.example.dmitry.apptest.GitHubObjects.Repo;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class CommitsActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener{

    GridView gvMain;
    CommitsAdapter adapter;

    @Override
    public void onServiceHelperResult(Bundle data) {
        if(data.getInt(ServerResponse.STATUS) == 200) {
            GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
            Log.d("1996", "TTTTTTT");
            if (gitHubObject.getClass() == CommitsList.class) {
                Log.d("1996", "DDDDDDD");
                adapter = new CommitsAdapter(this, (CommitsList)gitHubObject);
                gvMain.setAdapter(adapter);
                gvMain.setNumColumns(4);
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        String uri = getIntent().getStringExtra("repoUrl");
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);
        ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                MyIntentService.GET_COMMITS_LIST, uri);
        gvMain = (GridView) findViewById(R.id.commitsView);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


}




