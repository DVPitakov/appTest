package com.example.dmitry.apptest;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class MyIntentService extends IntentService {
    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final ResultReceiver receiver
                    = intent.getParcelableExtra(AppResultReceiver.class.getCanonicalName());

            final Bundle data = new Bundle();
            ServerResponseOnSignIn serverResponseOnSignIn = Processor.getInstance().trySignIn();
            data.putString(ServerResponseOnSignIn.STATUS, serverResponseOnSignIn.status);
            receiver.send(0, data);

        }
    }
}
