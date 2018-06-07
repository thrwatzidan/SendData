package com.example.android.navigation;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyIntentService extends IntentService {
    public static final String MESSAGE_KEY = "message_key";
    public MyIntentService(){super(" MyIntentService");}
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("MyIntentService", "onhandleIntent");
Intent returnIntent=new Intent("custom-event");
        LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(returnIntent);
    }
}
