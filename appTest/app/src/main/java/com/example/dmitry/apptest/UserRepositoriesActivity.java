package com.example.dmitry.apptest;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmitry.apptest.GitHubObjects.Commit;
import com.example.dmitry.apptest.GitHubObjects.CommitsList;
import com.example.dmitry.apptest.GitHubObjects.GitHubImage;
import com.example.dmitry.apptest.GitHubObjects.GitHubObject;
import com.example.dmitry.apptest.GitHubObjects.Repo;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserRepositoriesActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    LinearLayout linearLayout;
    ReposList reposList;
    HashMap<ImageView, String> imageViewStringHashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repositories);
        linearLayout = (LinearLayout)findViewById(R.id.userRepositoriesTable);

        String uri = getIntent().getStringExtra("repoUrl");
        ServiceHelper.getInstance().removeListener();
        ServiceHelper.getInstance().setListener(this);

        if (reposList == null) {
            ServiceHelper.getInstance().sendRequest(this.getBaseContext(),
                    MyIntentService.GET_REPOS_LIST, uri);
        }


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
                imageViewStringHashMap = tableCreate((ReposList)gitHubObject);
                HashSet<String> hashSetForOnlyOneRequestRepSameUri = new HashSet<>();
                for(Repo repo: ((ReposList)gitHubObject).repos) {
                    hashSetForOnlyOneRequestRepSameUri.add(repo.owner.avatarUrl);
                }
                for(String uri: hashSetForOnlyOneRequestRepSameUri) {
                    ServiceHelper.getInstance().sendRequest(this, MyIntentService.GET_IMAGE, uri);
                }
            }
            else if(gitHubObject.getClass() == GitHubImage.class) {
                GitHubImage gitHubImage = (GitHubImage)gitHubObject;
                tableUpdate(imageViewStringHashMap, gitHubImage.uri, gitHubImage.bitmap);
            }

        }


    }



    private void tableUpdate(HashMap<ImageView, String> hashMap, String uri, Bitmap newBitmap) {
        for(Map.Entry<ImageView, String> entry: hashMap.entrySet()) {
            if(entry.getValue().equals(uri)) {
                entry.getKey().setImageBitmap(newBitmap);
            }
        }
    }

    private HashMap<ImageView, String> tableCreate(ReposList reposList) {
        HashMap<ImageView,String> imageViews = new HashMap<>();

        for(Repo repo: reposList.repos) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);
            LinearLayout.LayoutParams textLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT
                    , LinearLayout.LayoutParams.WRAP_CONTENT);
            textLayoutParams.weight = 1;

            TextView gitText = new TextView(this);
            gitText.setText(repo.name);
            gitText.setLayoutParams(textLayoutParams);

            TextView ownerNameText = new TextView(this);
            ownerNameText.setText(repo.owner.login);
            ownerNameText.setLayoutParams(textLayoutParams);

            ImageView ownerImg = new ImageView(this);
            ownerImg.setImageResource(R.mipmap.ic_launcher_round);
            ownerImg.setLayoutParams(textLayoutParams);
            ownerImg.setScaleType(ImageView.ScaleType.FIT_XY);

            TextView watchesText = new TextView(this);
            watchesText.setText(String.valueOf(repo.watchers));
            watchesText.setLayoutParams(textLayoutParams);

            TextView forksText = new TextView(this);
            forksText.setText(String.valueOf(repo.forks));
            forksText.setLayoutParams(textLayoutParams);

            if (Build.VERSION.SDK_INT >= 17) {
                gitText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                ownerNameText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                watchesText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                forksText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }

            layout.addView(gitText);
            layout.addView(ownerNameText);
            layout.addView(ownerImg);
            layout.addView(watchesText);
            layout.addView(forksText);
            layout.setDividerDrawable(getResources()
                    .getDrawable(R.drawable.btn_cab_done_focused_example));
            layout.setDividerPadding((int)(getResources().getDimension(R.dimen.horizontal_padding)));
            layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            final String uri = repo.commitsUrl;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserRepositoriesActivity.this.openCommits(uri);
                    Intent intent = new Intent(UserRepositoriesActivity.this, CommitsActivity.class);
                    intent.putExtra("repoUrl", uri);
                    startActivity(intent);
                }
            });
            linearLayout.addView(layout);

            imageViews.put(ownerImg, repo.owner.avatarUrl);
        }

        return imageViews;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_user_repositories, menu);
        return true;
    }

    void openCommits(String uri) {

    }
}
