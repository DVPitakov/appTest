package com.example.dmitry.apptest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;

/**
 * Created by dmitry on 28.09.17.
 */

public class AppResultReceiver extends ResultReceiver {
    public AppResultReceiver(Handler handler) {
        super(handler);

    }

    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle data);

    }
    private Receiver receiver;


    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;

    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        receiver.onReceiveResult(resultCode, resultData);

    }

}
