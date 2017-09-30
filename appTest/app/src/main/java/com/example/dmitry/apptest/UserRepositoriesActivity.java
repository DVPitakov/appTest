package com.example.dmitry.apptest;

import android.app.IntentService;
import android.app.Service;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.dmitry.apptest.GitHubObjects.Repo;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

import java.util.ArrayList;

public class UserRepositoriesActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    ArrayList<String> data = new ArrayList<>();
    GridView gvMain;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);
        ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                MyIntentService.GET_COMMITS_LIST);

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
            ReposList reposList = (ReposList)data.getParcelable(ServerResponse.PARCEABLE);
            Log.d("1996", "dddddddddd");
            for(Repo repo: reposList.repos) {
                Log.d("1996", "2222222");
                this.data.add(repo.name);
            }
            adapter = new ArrayAdapter<String>(this
                    , R.layout.list_item
                    , R.id.tvText
                    , this.data);
            gvMain.setAdapter(adapter);
        }
    }
}
