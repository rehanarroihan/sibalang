package com.uyab.sibalang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;
import com.uyab.sibalang.Util.GlobalConfig;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean isLoggedIn = Prefs.getBoolean(GlobalConfig.IS_LOGGED_IN, false);
        final Intent splashIntent;
        if(isLoggedIn) {
            splashIntent = new Intent(SplashActivity.this, MainActivity.class);
        } else {
            splashIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
        }

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(500);
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
