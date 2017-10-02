package com.example.dmitry.apptest;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.dmitry.apptest.GitHubObjects.GitHubImage;
import com.example.dmitry.apptest.GitHubObjects.GitHubObject;
import com.example.dmitry.apptest.GitHubObjects.Repo;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UserRepositoriesActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    GridView gvMain;
    ReposList reposList;
    UserRepositoryesAdapter adapter;
    ArrayList<String> imageUrls = new ArrayList<>();
    HashMap<String, Bitmap> images = new HashMap<>();
    HashMap<String, View> views = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uri = getIntent().getStringExtra("repoUrl");
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);

        //reposList = savedInstanceState.
        if (reposList == null) {
            ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                    MyIntentService.GET_REPOS_LIST, uri);
        }
        else {
            updateGrid();
        }

        setContentView(R.layout.activity_user_repositories);

        gvMain = (GridView) findViewById(R.id.repositoryGrid);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(UserRepositoriesActivity.this, CommitsActivity.class);
                if (i >= 5) {
                    i -= 5;
                    intent.putExtra("repoUrl", reposList.repos.get(i / 5).commitsUrl);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        if((data.getInt(ServerResponse.STATUS) < 0)) {
            Alertic.show(UserRepositoriesActivity.this, "Ошибка соединения",
                    "С интернетом возникла проблема");
            UserRepositoriesActivity.this.finish();
            return;
        }
        if(data.getInt(ServerResponse.STATUS) == 200) {
            GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
            if (gitHubObject.getClass() == ReposList.class) {
                reposList = (ReposList)gitHubObject;
                for(Repo repo: reposList.repos) {
                    imageUrls.add(repo.owner.avatarUrl);
                }
                HashSet<String> hashSet = new HashSet<>(imageUrls);
                for(String url: hashSet) {
                    ServiceHelper.getInstance().sendRequest(UserRepositoriesActivity.this
                            ,MyIntentService.GET_IMAGE, url);
                }
                adapter = new UserRepositoryesAdapter(this, reposList, imageUrls, images, views);
            }
            else if(gitHubObject.getClass() == GitHubImage.class) {
                GitHubImage gitHubImage = (GitHubImage)gitHubObject;
                images.put(gitHubImage.uri, gitHubImage.bitmap);

            }
            updateGrid();

        }


    }

    void updateGrid() {
        gvMain.setAdapter(adapter);
        gvMain.setHorizontalSpacing(0);
        gvMain.setMinimumHeight((int)getResources().getDimension(R.dimen.UserRepoH));
        gvMain.setVerticalSpacing((int)getResources().getDimension(R.dimen.UserRepoH));
        gvMain.setNumColumns(5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_user_repositories, menu);
        return true;
    }
}
