package com.uyab.sibalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                    Intent splashIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    splashIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(splashIntent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}
