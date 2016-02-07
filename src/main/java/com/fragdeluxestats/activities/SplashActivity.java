package com.fragdeluxestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.fragdeluxestats.MainActivity;
import com.fragdeluxestats.R;
import com.fragdeluxestats.model.SharedPreferenceModel;

public class SplashActivity extends AppCompatActivity {
    private static final int SECONDS = 2000;
    private SharedPreferenceModel sharedPreferenceModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        sharedPreferenceModel = new SharedPreferenceModel(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String steamID = sharedPreferenceModel.loadSharedPreferenceSteamID();
                Class classType;
//                steamID = "0"; //TODO Testing Purposes
                if (steamID.equals("0")) {
                    classType = LoginActivity.class;
                } else {
                    classType = MainActivity.class;
                }
                Intent loginIntent = new Intent(getApplicationContext(), classType);
                startActivity(loginIntent);
                finish();
            }
        }, SECONDS);
    }
}
