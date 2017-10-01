package com.example.dmitry.apptest;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.apptest.GitHubObjects.GitHubObject;
import com.example.dmitry.apptest.GitHubObjects.Repo;
import com.example.dmitry.apptest.GitHubObjects.ReposList;
import com.example.dmitry.apptest.GitHubObjects.ServerResponse;
import com.example.dmitry.apptest.GitHubObjects.UserInfo;

public class MainActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    EditText loginEdit;
    EditText passwordEdit;
    Button signInButton;
    UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserData userData = Storage.getInstance(this).getUserData();
        ServiceHelper.getInstance().setListener(this);

        loginEdit = (EditText)findViewById(R.id.editLogin);
        passwordEdit = (EditText)findViewById(R.id.editPassword);
        signInButton = (Button)findViewById(R.id.signInButton);

        if(userData != null) {
            loginEdit.setText(userData.login);
            passwordEdit.setText(userData.password);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceHelper.getInstance().sendRequest(MainActivity.this
                        , MyIntentService.TRY_SIGN_IN
                        , null);
            }
        });
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
        if (data.getInt(ServerResponse.STATUS) == 200) {
            if (gitHubObject.getClass() == UserInfo.class) {
                userInfo = (UserInfo)gitHubObject;
                Intent intent = new Intent(MainActivity.this, UserRepositoriesActivity.class);
                intent.putExtra("repoUrl", userInfo.reposUrl);
                startActivity(intent);
                Storage.getInstance(MainActivity.this.getBaseContext())
                        .saveUserData(new UserData(
                                loginEdit.getText().toString()
                                , passwordEdit.getText().toString()));
            }
        }

    }

}
