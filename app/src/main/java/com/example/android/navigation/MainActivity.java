package com.example.android.navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EMAIL_KEY = "emai_key";
    public static final String NAME_KEY = "name_key";
    public static final int EMAIL_REQUEST=1000;
    private static final String COORDINATES = "34.3852964,-119.4875023";
    private TextView mLog;
    public static final String TAG = "mainActivity";
    private BroadcastReceiver br;
    BroadcastReceiver mLocalReciver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
String message =intent.getStringExtra(MyIntentService.MESSAGE_KEY);
logMessage("Received :"+message);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLog = (TextView) findViewById(R.id.log);
        mLog.setMovementMethod(new ScrollingMovementMethod());
        mLog.setText("");
        //Broad cast function
        br = new MyBroadcastReciver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        registerReceiver(br, filter);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mLocalReciver,new IntentFilter("custom-event"));
        }

    @Override
    protected void onDestroy() {
unregisterReceiver(br );
LocalBroadcastManager.getInstance(getApplicationContext())
        .unregisterReceiver(mLocalReciver);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        if (intent != null) {
            String message = intent.getStringExtra("payload");
            if (message != null) {
                logMessage(message);
            }
        }
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,EmailListActivity.class);
                intent.putExtra(NAME_KEY,"Mike smith");
                startActivityForResult(intent,EMAIL_REQUEST);
            }
        });
    }

    public void displayStarters(View view) {
        Intent intent = new Intent(this, StartersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==EMAIL_REQUEST && resultCode == RESULT_OK){
            String nameText = data.getStringExtra(NAME_KEY);
            String emailText = data.getStringExtra(EMAIL_KEY);
            Toast.makeText(this, "you entered"+nameText +"and" +emailText,
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void displaymap(View view) {
        Uri uri = Uri.parse("geo:" + COORDINATES);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    //to send text from app to another from the sam phone
    public void sendText(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("Payload", "HELLO FROM NADIA'S Restaurant!!");
        intent.setType("text/plain");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {
            Toast.makeText(this, "dont find  any app to handle this", Toast.LENGTH_SHORT).show();
        }

    }

    public void runcodee(View view) {
        Intent intente = new Intent(this, MyIntentService.class);
        startActivity(intente);
    }

    class MyBroadcastReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            logMessage("Action :" + intent.getAction());

        }
    }
    private void logMessage(String message) {
//      Output message to logcat console
        Log.i(TAG, message);

//      Output message to TextView
        mLog.append(message + "\n");

//      Adjust scroll position to make last line visible
        Layout layout = mLog.getLayout();
        if (layout != null) {
            int scrollAmount = layout.getLineTop(mLog.getLineCount()) - mLog.getHeight();
            mLog.scrollTo(0, scrollAmount > 0 ? scrollAmount : 0);
        }
    }



}

