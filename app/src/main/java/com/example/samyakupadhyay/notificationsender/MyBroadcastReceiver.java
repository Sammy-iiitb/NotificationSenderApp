package com.example.samyakupadhyay.notificationsender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SamyakUpadhyay on 23-01-2018.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    TextView textView;
    private static final String TAG = "MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        //textView = (TextView) Main2Activity.this.findViewById(R.id.textView);
        StringBuilder sb = new StringBuilder();
        sb.append("Action: "+intent.getAction()+"\n");
        sb.append(intent.getExtras().getString(Intent.EXTRA_TEXT));
        String log = sb.toString();
        Log.d("Received", log);
        //Toast.makeText(context, log, Toast.LENGTH_LONG).show();
        //Intent myIntent = new Intent(MyBroadcastReceiver.class, Main2Activity.class);
        Intent i = new Intent(context, Main2Activity.class);
        i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        i.putExtra("data", log);
        context.startActivity(i);
    }
}
