package com.example.dmitry.apptest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener, TextWatcher {

    EditText loginEdit;
    EditText passwordEdit;
    Button signInButton;
    UserInfo userInfo;
    UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceHelper.getInstance().setListener(this);

        userData = Storage.getInstance(this).getUserData();
        loginEdit = (EditText)findViewById(R.id.editLogin);
        passwordEdit = (EditText)findViewById(R.id.editPassword);
        loginEdit.addTextChangedListener(this);
        passwordEdit.addTextChangedListener(this);
        signInButton = (Button)findViewById(R.id.signInButton);
        signInButton.setEnabled(userData != null && userData.isValid());


        if(userData != null) {
            loginEdit.setText(userData.login);
            passwordEdit.setText(userData.password);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInButton.setEnabled(false);
                userData.login = loginEdit.getText().toString();
                userData.password = passwordEdit.getText().toString();
                if(userData.isValid()) {
                    Storage.getInstance(MainActivity.this.getBaseContext())
                            .saveUserData(userData);
                    ServiceHelper.getInstance().sendRequest(
                            MainActivity.this
                            , MyIntentService.TRY_SIGN_IN
                            , null);
                }else {
                    Alertic.show(MainActivity.this, "Неправильно", "Данные введены неправильно!");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ServiceHelper.getInstance().setListener(this);
    }


    @Override
    public void onServiceHelperResult(Bundle data) {
        GitHubObject gitHubObject = data.getParcelable(ServerResponse.PARCEABLE);
        signInButton.setEnabled(true);
        if((data.getInt(ServerResponse.STATUS) < 0)) {
            Alertic.show(MainActivity.this, "Ошибка соединения",
                    "С интернетом возникла проблема, не могу проверить правильные ли логин и пароль");
            return;
        }
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
            return;
        }
        if (data.getInt(ServerResponse.STATUS) == 401){
            Alertic.show(MainActivity.this, "Ошибка 401", "Данные введены неправильно!");
            return;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        setButtonEnabled();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        setButtonEnabled();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        setButtonEnabled();
    }

    private void setButtonEnabled() {
        if(userData.isValid()) return;
        userData.login = loginEdit.getText().toString();
        userData.password = passwordEdit.getText().toString();
        signInButton.setEnabled(userData != null && (userData.isValid()));
    }

}
