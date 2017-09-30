package com.example.dmitry.apptest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.dmitry.apptest.GitHubObjects.ServerResponse;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }

    public static  final int TRY_SIGN_IN = 1;
    public static  final int GET_COMMITS_LIST = 2;
    public static  final int GET_REPOS_LIST = 3;
    public static final int GET_IMAGE = 4;
    public static  final String MY_ACTION = "MY_ACTION";
    public static  final String MY_DATA = "MY_DATA";

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String uri = intent.getStringExtra(MY_DATA);
            final ResultReceiver receiver
                    = intent.getParcelableExtra(AppResultReceiver.class.getCanonicalName());
            final Bundle data = new Bundle();
            int event = intent.getIntExtra(MY_ACTION, 1);
            ServerResponse serverResponse;
            switch(event) {
                case TRY_SIGN_IN:{
                    serverResponse = Processor.getInstance().trySignIn();
                    break;
                }
                case GET_COMMITS_LIST:{
                    serverResponse = Processor.getInstance().getCommitsList();
                    break;
                }
                case GET_REPOS_LIST:{
                    serverResponse = Processor.getInstance().getReposList();
                    break;
                }
                case GET_IMAGE:{
                    serverResponse = Processor.getInstance().getImage(uri);
                    break;
                }
                default: {
                    serverResponse = new ServerResponse(0, null);
                }
            }
            data.putInt(ServerResponse.STATUS, serverResponse.status);
            if (serverResponse.gitHubObject != null) {
                data.putParcelable(ServerResponse.PARCEABLE, serverResponse.gitHubObject);
            }
            receiver.send(event, data);

        }
    }
}
