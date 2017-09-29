package com.example.dmitry.apptest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Created by dmitry on 28.09.17.
 */

public class ServiceHelper implements AppResultReceiver.Receiver{
    private ServiceHelper() {}

    public interface ServiceHelperListener {
        void onServiceHelperResult(Bundle data);
    }

    private static AppResultReceiver appResultReceiver;
    private ServiceHelperListener listener;
    private static ServiceHelper instance;

    void sendRequest(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), MyIntentService.class);
        appResultReceiver.setReceiver(this);
        intent.putExtra(AppResultReceiver.class.getCanonicalName(), appResultReceiver);
        context.startService(intent);

    }

    synchronized public static ServiceHelper getInstance() {
        if (instance == null) {
            instance = new ServiceHelper();
            appResultReceiver = new AppResultReceiver(new Handler());
        }
        return instance;

    }

    public void setListener(ServiceHelperListener listener) {
        this.listener = listener;

    }

    public void removeListener() {
        listener = null;

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        Log.d("1996", "TARGET SUCCESS");
        if (listener != null) {
            listener.onServiceHelperResult(data);
        }
    }

}
