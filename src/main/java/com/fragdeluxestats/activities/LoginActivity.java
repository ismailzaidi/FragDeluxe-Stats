package com.fragdeluxestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.fragdeluxestats.R;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.fragments.MainMenuFragment;
import com.fragdeluxestats.model.Utility;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextStyleView normalLogin;
    private TextStyleView steamLogin;
    private TextStyleView guestLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        normalLogin = (TextStyleView) findViewById(R.id.loginButton);
        steamLogin = (TextStyleView) findViewById(R.id.steamLoginButton);
        guestLogin = (TextStyleView) findViewById(R.id.guestLoginButton);
        normalLogin.setOnClickListener(this);
        steamLogin.setOnClickListener(this);
        guestLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class type = null;
        int id = view.getId();
        if (id == R.id.loginButton) {
            type = StandardLoginActivity.class;
        }
        if (id == R.id.steamLoginButton) {
            type = SteamLoginActivity.class;
        }
        if(id == R.id.guestLoginButton){
            type = GuestActivity.class;
        }
        if(Utility.isNetworkAvailable(this)){
            Intent intent = new Intent(getApplicationContext(), type);
            startActivity(intent);
            finish();
        }else{
            Utility.ShowInternetError(this);
        }

    }
}
