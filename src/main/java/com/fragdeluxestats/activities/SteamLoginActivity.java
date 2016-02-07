package com.fragdeluxestats.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.fragdeluxestats.MainActivity;
import com.fragdeluxestats.R;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class SteamLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private String REALM_PARAM = "FragDeluxe";
    private int attempts = 0;
    public interface CheckUserCallBack {
        void GetPlayerStatus(boolean isExist);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_steam_activity);
        webView = (WebView) findViewById(R.id.webViewForSteamUsers);
        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
                setTitle(url);
                Uri Url = Uri.parse(url);

                if (Url.getAuthority().equals(REALM_PARAM.toLowerCase())) {
                    // That means that authentication is finished and the url contains user's id.
                    webView.stopLoading();
                    attempts++;
                    // Extracts user id.
                    Uri userAccountUrl = Uri.parse(Url.getQueryParameter("openid.identity"));
                    String userId = userAccountUrl.getLastPathSegment();
                    final String steamID = Utility.steam64TOSteamID(userId);
                    Log.v("STEAM_LOGIN", " Steam ID: " + steamID + " Steam64: " + userId);
                    new CheckFragDeluxe(new CheckUserCallBack() {
                        @Override
                        public void GetPlayerStatus(boolean isExist) {
                            Log.v("STEAM_LOGIN", " Steam ID: " + steamID + " Exists: " + isExist);
                            if(isExist){
                                launchIntent(steamID);
                            }else{
                                Toast.makeText(getApplicationContext(),"Not Found On FragDeluxe", Toast.LENGTH_LONG).show();
                                finish();
                            }

                        }
                    }).execute(userId);
                }

            }
        });
        String url = "https://steamcommunity.com/openid/login?" +
                "openid.claimed_id=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.identity=http://specs.openid.net/auth/2.0/identifier_select&" +
                "openid.mode=checkid_setup&" +
                "openid.ns=http://specs.openid.net/auth/2.0&" +
                "openid.realm=https://" + REALM_PARAM + "&" +
                "openid.return_to=https://" + REALM_PARAM + "/signin/";
        webView.loadUrl(url);
    }

    public void launchIntent(String steamID) {
        Intent intent = new Intent(this, MainActivity.class);
//        sharedModel.saveSharedUserID(steamID);
//        intent.putExtra(LoginActivity.INTENT_KEY, steamID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {

    }
    private class CheckFragDeluxe extends AsyncTask<String, Void, Boolean> {
        CheckUserCallBack mCallBack;
        public CheckFragDeluxe(CheckUserCallBack callBack) {
            this.mCallBack = callBack;
        }
        @Override
        protected Boolean doInBackground(String... strings) {
            String steam64Id = strings[0];
            return Utility.isExistInFragDeluxe(steam64Id);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mCallBack.GetPlayerStatus(aBoolean);
        }
    }
}
