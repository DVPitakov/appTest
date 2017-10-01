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
    protected void onStart() {
        super.onStart();
        ServiceHelper.getInstance().setListener(this);
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        if((data.getInt(ServerResponse.STATUS) < 0)) {
            Alertic.show(CommitsActivity.this, "Ошибка соединения",
                    "С интернетом возникла проблема, не могу загрузить коммиты");
            CommitsActivity.this.finish();
            return;
        }
        if(data.getInt(ServerResponse.STATUS) == 200) {
            GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
            if (gitHubObject.getClass() == CommitsList.class) {
                adapter = new CommitsAdapter(this, (CommitsList)gitHubObject);
                gvMain.setMinimumHeight((int)getResources().getDimension(R.dimen.UserRepoH));
                gvMain.setVerticalSpacing((int)getResources().getDimension(R.dimen.UserRepoH));
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




