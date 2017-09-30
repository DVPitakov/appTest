package com.example.dmitry.apptest;

import android.app.IntentService;
import android.app.Service;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    UserRepositoryesAdapter adapter;
    ArrayList<String> repos = new ArrayList<>();
    ArrayList<String> logins = new ArrayList<>();
    ArrayList<String> imageUrls = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);
        ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                MyIntentService.GET_COMMITS_LIST, null);

        setContentView(R.layout.activity_user_repositories);

        gvMain = (GridView) findViewById(R.id.repositoryGrid);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        Log.d("1996", "222222sdfsdfsd2");
        if(data.getInt(ServerResponse.STATUS) == 200) {
            HashMap<String, Bitmap> images = new HashMap<>();
            HashMap<String, View> views = new HashMap<>();
            GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
            if (gitHubObject.getClass() == ReposList.class) {
                Log.d("1996", "dddddddddd");
                for(Repo repo: ((ReposList)gitHubObject).repos) {
                    Log.d("1996", "2222222");
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
                Log.d("1996", gitHubImage.bitmap.toString());
                images.put(gitHubImage.uri, gitHubImage.bitmap);
                adapter = new UserRepositoryesAdapter(this, repos, logins, imageUrls, images, views);
                gvMain.setAdapter(adapter);
                gvMain.setNumColumns(3);

            }

        }
    }
}
