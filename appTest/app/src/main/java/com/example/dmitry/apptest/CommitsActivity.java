package com.example.dmitry.apptest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dmitry.apptest.GitHubObjects.Commit;
import com.example.dmitry.apptest.GitHubObjects.CommitsList;
import com.example.dmitry.apptest.GitHubObjects.GitHubObject;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

public class CommitsActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener{

    LinearLayout linearLayout;

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
                for(Commit commit :((CommitsList) gitHubObject).commits) {
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

                    TextView shaText = new TextView(this);
                    shaText.setText(commit.sha);
                    shaText.setLayoutParams(textLayoutParams);

                    TextView messageText = new TextView(this);
                    messageText.setText(commit.message);
                    messageText.setLayoutParams(textLayoutParams);
                    if (Build.VERSION.SDK_INT >= 17) {
                        messageText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }

                    TextView authorText = new TextView(this);
                    authorText.setText(commit.author);
                    authorText.setLayoutParams(textLayoutParams);

                    TextView dateText = new TextView(this);
                    dateText.setText(commit.date);
                    dateText.setLayoutParams(textLayoutParams);

                    layout.addView(shaText);
                    layout.addView(messageText);
                    layout.addView(authorText);
                    layout.addView(dateText);
                    layout.setDividerDrawable(getResources()
                            .getDrawable(R.drawable.btn_cab_done_focused_example));
                    layout.setDividerPadding((int)(getResources().getDimension(R.dimen.horizontal_padding)));
                    layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                    linearLayout.addView(layout);

                    if (Build.VERSION.SDK_INT >= 17) {
                        authorText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        dateText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        messageText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        shaText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    }

                }
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
        linearLayout = (LinearLayout)findViewById(R.id.commitsLinearLayout);

    }


}




