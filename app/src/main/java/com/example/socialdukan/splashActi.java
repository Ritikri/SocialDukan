package com.example.socialdukan;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
;
import com.example.socialdukan.Student.Login_Register_Student.Login_Student;


import java.util.Timer;
import java.util.TimerTask;

public class splashActi extends AppCompatActivity {

Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );


       // FirebaseMessaging.getInstance().subscribeToTopic(getString( R.string.default_notification_channel_id));


        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(splashActi.this, Login_Student.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }
}
