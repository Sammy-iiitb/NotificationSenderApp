package com.example.samyakupadhyay.notificationsender;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    Button submit;
    EditText title, description;
    RadioButton rbtn1, rbtn2, finalcheck;
    int drain;
    BroadcastReceiver receiver;
    Bitmap icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this is my broadcast receiver with the intent filter
        receiver = new BroadcastReceiver() {
                             @Override
                             public void onReceive(Context context, Intent intent) {
                                 StringBuilder sb = new StringBuilder();
                                 sb.append("Action: " + intent.getAction() + "\n");
                                 sb.append(intent.getExtras().getString(Intent.EXTRA_TEXT));
                                 String log = sb.toString();
                                 Log.d("Received", log);
                                 Intent i = new Intent(context, Main2Activity.class);
                                 i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
                                 i.putExtra("data", log);
                                 context.startActivity(i);
                             } };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SEND");
        registerReceiver(receiver, filter);


        title = findViewById(R.id.editText1);
        description = findViewById(R.id.editText2);
        rbtn1 = findViewById(R.id.simpleRadioButton);
        rbtn2 = findViewById(R.id.simpleRadioButton1);

        submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Name -  " + title.getText().toString() + "\n", Toast.LENGTH_SHORT).show();
                //RadioButton finalcheck;
                if (rbtn1.isChecked()){
                    finalcheck = rbtn1;
                    icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher_round);
                    //drain = R.mipmap.ic_launcher_round;
                }
                else{
                    finalcheck = rbtn2;
                    //drain = R.mipmap.ic_launcher;
                    icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
                }
                if (title.getText().toString().length()==0){
                    title.setError("Title is required");
                    //title.setError(null);
                }
                if (description.getText().toString().length()<=3){
                    description.setError("Description should have at least 10 letters");
                    //description.setError(null);
                }
                if (rbtn1.isChecked()==false && rbtn2.isChecked()== false){
                    rbtn1.setError("Atleast one should be selected");
                    //rbtn1.setError(null);
                }
                else {
                    title.setError(null);
                    description.setError(null);
                    rbtn1.setError(null);
                    createNotify();
                }

            }
        });
    }

    public void createNotify(){
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification noti = new Notification.Builder(this)
                 .setContentTitle(title.getText())
                 .setContentText(description.getText())
                 .setSmallIcon(R.drawable.ic_launcher_foreground)
                 .setLargeIcon(icon)
                 .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

}
