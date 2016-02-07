package com.fragdeluxestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fragdeluxestats.MainActivity;
import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.AutoCompleteAdapter;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.HTTPHandlers.FilterAsyncTask;
import com.fragdeluxestats.model.HTTPHandlers.LoginAsyncTask;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.SharedPreferenceModel;
import com.fragdeluxestats.model.Utility;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StandardLoginActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    public static final String ROTATION_TEST = "ROTATION_TEST";
    public static final String PLAYER_LIST = "player_list";
    public static final String USER_INPUT = "user_input";
    private AutoCompleteTextView userIdEditText;
    private ArrayList<Player> listOfPlayers;
    private AutoCompleteAdapter adapter;
    private String userInput;
    private TextStyleView findButton;
    private TextStyleView tempSteamLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_normal_activity);
        userIdEditText = (AutoCompleteTextView) findViewById(R.id.userIdEditText);
        findButton = (TextStyleView) findViewById(R.id.loginButton);
        tempSteamLogin = (TextStyleView) findViewById(R.id.tempSteamLogin);
        String tempString = tempSteamLogin.getText().toString();
        tempSteamLogin.setText(Html.fromHtml(tempString));
        tempSteamLogin.setOnClickListener(this);
        findButton.setOnClickListener(this);
        userIdEditText.setOnItemClickListener(this);
    }
    private void init() {
        if (listOfPlayers.size() != 0) {
            adapter = new AutoCompleteAdapter(this, listOfPlayers);
            userIdEditText.setAdapter(adapter);
            userIdEditText.showDropDown();
        }
        ;
    }

    private void executeTask(String userFilterID) {
        final MaterialDialog dialog = getMaterialDialog();
        new FilterAsyncTask(new FilterAsyncTask.StandardLoginCallBack() {
            @Override
            public void fetchFragDeluxePlayerList(ArrayList<Player> playerList) {
                listOfPlayers = playerList;
                Log.v("NEW_LOGIN", "Players Available: " + listOfPlayers.size());
                Collections.sort(listOfPlayers, new SortPlayer());
                dialog.dismiss();
                init();
            }
        }).execute(userFilterID);
    }

    @NonNull
    private MaterialDialog getMaterialDialog() {
        final MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
        builder.title("Searching...");
        builder.content("Please Wait. . .");
        builder.autoDismiss(false);
        builder.progress(true, 0);
        builder.widgetColor(ContextCompat.getColor(this, R.color.profilecolor));
        builder.backgroundColor(ContextCompat.getColor(this, R.color.secondryColor));
        builder.titleColor(ContextCompat.getColor(this, android.R.color.white));
        builder.contentColor(ContextCompat.getColor(this, android.R.color.white));
        final MaterialDialog dialog = builder.build();
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public void AuthenticateUser() {
        String userID = userIdEditText.getText().toString();
        if (userID != null && userID.length() != 0) {
            new LoginAsyncTask(userID, new LoginAsyncTask.UserLoginCallBack() {
                @Override
                public void AuthenticateUser(Player player) {
                    if (player != null) {
                        System.out.println("Player Unique: " + player.getUniqueid());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra(ResourceReference.LOGIN_KEY, player.getSteam64ID());
                        startActivity(intent);

                    } else {

                    }
                }
            }).execute();

        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Player player = adapter.getItem(i);
        Log.v("STANDARD_LOGIN", "Click Position : " + i + " Item: " + player.getName());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        SharedPreferenceModel sharedPreferenceModel = new SharedPreferenceModel(getApplicationContext());
        sharedPreferenceModel.saveSharedPreferenceSteamID(player.getUniqueid());
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == findButton.getId()) {
            String autoTextString = userIdEditText.getText().toString();
            boolean isNetwork = Utility.isNetworkAvailable(this);
            if (isNetwork) {
                if (autoTextString.length() >= 1 || autoTextString != null) {
                    executeTask(autoTextString);
                }
            } else {
                Utility.ShowInternetError(this);
            }
        }
        if(view.getId()==tempSteamLogin.getId()){
            Intent intent = new Intent(getApplicationContext(), SteamLoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class SortPlayer implements Comparator<Player> {
        @Override
        public int compare(Player player, Player t1) {
            String player1 = player.getName();
            return player1.compareTo(t1.getName());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(USER_INPUT, userIdEditText.getText().toString());
        outState.putSerializable(PLAYER_LIST, listOfPlayers);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listOfPlayers = (ArrayList<Player>) savedInstanceState.getSerializable(PLAYER_LIST);
        Log.v(ROTATION_TEST, "Player Size: " + listOfPlayers.size());
        userInput = savedInstanceState.getString(USER_INPUT);
        Log.v(ROTATION_TEST, "UserInput: " + userInput);
    }
}
