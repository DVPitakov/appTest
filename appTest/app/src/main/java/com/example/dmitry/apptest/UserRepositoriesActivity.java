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

public class UserRepositoriesActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    ArrayList<String> data = new ArrayList<>();
    GridView gvMain;
    ReposList reposList;
    UserRepositoryesAdapter adapter;
    ArrayList<String> repos = new ArrayList<>();
    ArrayList<String> logins = new ArrayList<>();
    ArrayList<String> imageUrls = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uri = getIntent().getStringExtra("repoUrl");
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);
        ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                MyIntentService.GET_REPOS_LIST, uri);

        setContentView(R.layout.activity_user_repositories);

        gvMain = (GridView) findViewById(R.id.repositoryGrid);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i % 3 == 2) {
                    Intent intent = new Intent(UserRepositoriesActivity.this, CommitsActivity.class);
                    intent.putExtra("repoUrl", reposList.repos.get(i / 3).commitsUrl);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        if(data.getInt(ServerResponse.STATUS) == 200) {
            HashMap<String, Bitmap> images = new HashMap<>();
            HashMap<String, View> views = new HashMap<>();
            GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
            if (gitHubObject.getClass() == ReposList.class) {
                reposList = (ReposList)gitHubObject;
                for(Repo repo: reposList.repos) {
                    repos.add(repo.name);
                    logins.add(repo.owner.login);
                    imageUrls.add(repo.owner.avatarUrl);
                    ServiceHelper.getInstance().sendRequest(UserRepositoriesActivity.this
                            ,MyIntentService.GET_IMAGE, repo.owner.avatarUrl);
                }

                adapter = new UserRepositoryesAdapter(this, repos, logins, imageUrls, images, views);
                gvMain.setAdapter(adapter);
                gvMain.setNumColumns(3);
            }
            else if(gitHubObject.getClass() == GitHubImage.class) {
                GitHubImage gitHubImage = (GitHubImage)gitHubObject;
                images.put(gitHubImage.uri, gitHubImage.bitmap);
                adapter = new UserRepositoryesAdapter(this, repos, logins, imageUrls, images, views);
                gvMain.setAdapter(adapter);
                gvMain.setNumColumns(3);

            }

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_user_repositories, menu);
        return true;
    }
}
