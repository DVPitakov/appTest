package com.example.dmitry.apptest;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

public class MainActivity extends AppCompatActivity implements ServiceHelper.ServiceHelperListener {

    EditText loginEdit;
    EditText passwordEdit;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Storage.getInstance(this);
        ServiceHelper.getInstance().setListener(this);

        loginEdit = (EditText)findViewById(R.id.editLogin);
        passwordEdit = (EditText)findViewById(R.id.editPassword);
        signInButton = (Button)findViewById(R.id.signInButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserRepositoriesActivity.class);
                startActivity(intent);
                Storage.getInstance(MainActivity.this.getBaseContext())
                        .saveUserData(new UserData(
                                loginEdit.getText().toString()
                                , passwordEdit.getText().toString()));
            }
        });
    }

    @Override
    public void onServiceHelperResult(Bundle data) {
        Log.d("1996", "start");
        if (data.getInt(ServerResponse.STATUS) == 200) {
            Log.d("1996", "good");
        }

    }
}
