package com.fragdeluxestats.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.fragments.FriendsFragment;
import com.fragdeluxestats.fragments.MapFragment;
import com.fragdeluxestats.fragments.progressbars.ProgressDialogFragment;
import com.fragdeluxestats.fragments.StatisticsFragment;
import com.fragdeluxestats.fragments.WeaponFragment;
import com.fragdeluxestats.model.HTTPHandlers.DataAsyncTask;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsActivity extends AppCompatActivity implements StatisticsFragment.StatsInterFace, WeaponFragment.WeaponCallBack, MapFragment.MapCallBack {
    private Toolbar toolbar;
    private TextStyleView playerName;
    private TextStyleView playerRank;
    private TextStyleView playerPoints;
    private GlobalData globalData;
    private CircleImageView profileImage;
    private ImageView countryImage;
    private TextView countryName;
    private LinearLayout backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String steamID = getIntent().getExtras().getString(ResourceReference.FRIENDS_KEY);
//        userSteamProfile = Utility.generateUserURL(userSteam64);
        setContentView(R.layout.friends_main);
        final ProgressDialogFragment fragment = new ProgressDialogFragment(this);
        DataAsyncTask.GetUserData userData = new DataAsyncTask.GetUserData() {
            @Override
            public void fetchUserData(GlobalData data) {
                globalData = data;
                generateMainView();
                fragment.dismiss();
            }
        };
        DataAsyncTask task = new DataAsyncTask(getApplicationContext(), steamID, fragment, userData, true);
        task.execute();
    }


    public void generateMainView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        playerName = (TextStyleView) findViewById(R.id.playerName);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        countryImage = (ImageView) findViewById(R.id.countryImage);
        countryName = (TextView) findViewById(R.id.countryName);
        playerRank = (TextStyleView) findViewById(R.id.playerRank);
        playerPoints = (TextStyleView) findViewById(R.id.playerPoints);
        backButton = (LinearLayout) findViewById(R.id.refreshButton);
        initValues();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        displayFragment();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initValues() {
        Player player = globalData.getPlayer();
        playerName.setText(player.getName());
        int playerOnline = player.getOnline();
        if (playerOnline == 1) {
            profileImage.setBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.greenbutton));
        } else {
            profileImage.setBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.terroritColor));
        }
        int countryFlag = new Utility(getApplicationContext()).getFlagID(player.getCountryCode());
        if (countryFlag != 0) {
            countryImage.setImageResource(countryFlag);
        }
        Picasso.with(getApplicationContext()).load(player.getAvatar()).into(profileImage);
        countryName.setText(player.getCountryName());
        playerRank.setText("Rank: " + player.getRank());
        playerPoints.setText("Points required to rank up: " + player.getSkillDifference());
    }

    public void displayFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FriendsFragment mainMenuFragment = FriendsFragment.newInstance();
        fm.beginTransaction().replace(R.id.frame_content, mainMenuFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<Statistics> UserRandomStatCallBack() {
        return globalData.getRandomList();
    }

    @Override
    public ArrayList<Weapon> fetchWeaponList() {
        return globalData.getPlayer().getListOfWeapons();
    }

    @Override
    public ArrayList<GameMap> fetchMapList() {
        return globalData.getPlayer().getListOfMaps();
    }
}
