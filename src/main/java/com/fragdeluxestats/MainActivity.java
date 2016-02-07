package com.fragdeluxestats;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fragdeluxestats.activities.LoginActivity;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.fragments.AboutFragment;
import com.fragdeluxestats.fragments.AboutItemFragment;
import com.fragdeluxestats.fragments.CompareDialogFragment;
import com.fragdeluxestats.fragments.FriendsFragment;
import com.fragdeluxestats.fragments.MainFragment;
import com.fragdeluxestats.fragments.MapFragment;
import com.fragdeluxestats.fragments.progressbars.ProgressDialogFragment;
import com.fragdeluxestats.fragments.RankFragment;
import com.fragdeluxestats.fragments.ServerFragment;
import com.fragdeluxestats.fragments.StatisticsFragment;
import com.fragdeluxestats.fragments.WeaponFragment;
import com.fragdeluxestats.model.HTTPHandlers.DataAsyncTask;
import com.fragdeluxestats.model.SharedPreferenceModel;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements StatisticsFragment.StatsInterFace, RankFragment.RankCallBack, ServerFragment.ServerCallBack, WeaponFragment.WeaponCallBack, MapFragment.MapCallBack, CompareDialogFragment.CompareListFetcher, AboutItemFragment.FetchAboutStatsCallBack {
    private Toolbar toolbar;
    private TextStyleView playerName;
    private TextStyleView playerRank;
    private TextStyleView playerPoints;
    private GlobalData globalData;
    private CircleImageView profileImage;
    private ImageView countryImage;
    private TextView countryName;
    private LinearLayout refreshButton;
    private Utility utility;
    private SharedPreferenceModel sharedPreferenceModel;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceModel = new SharedPreferenceModel(getApplicationContext());
        utility = new Utility(this);
        loadView();
    }

    public void loadView() {
        String steamID = sharedPreferenceModel.loadSharedPreferenceSteamID();
        final ProgressDialogFragment fragment = new ProgressDialogFragment(this);
        fragment.setMainMenuBool();
        DataAsyncTask.GetUserData userData = new DataAsyncTask.GetUserData() {
            @Override
            public void fetchUserData(GlobalData data) {
                globalData = data;
                generateMainView();
                fragment.dismiss();
            }
        };
        DataAsyncTask task = new DataAsyncTask(getApplicationContext(), steamID, fragment, userData, false);
        task.execute();
    }


    public void generateMainView() {
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        refreshButton = (LinearLayout) findViewById(R.id.refreshButton);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setupDrawer();
        initValues();
        displayFragment(FriendsFragment.newInstance());
    }

    private void setupDrawer() {
        playerName = (TextStyleView) findViewById(R.id.playerName);
        profileImage = (CircleImageView) findViewById(R.id.profile_image);
        countryImage = (ImageView) findViewById(R.id.countryImage);
        countryName = (TextView) findViewById(R.id.countryName);
        playerRank = (TextStyleView) findViewById(R.id.playerRank);
        playerPoints = (TextStyleView) findViewById(R.id.playerPoints);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationListener());
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

    }

    private void initValues() {
        Player player = globalData.getPlayer();
        if (player != null) {
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

            playerRank.setText("Rank: " + utility.getFormatSorter(player.getRank()));
            SharedPreferenceModel sharedPreferenceModel = new SharedPreferenceModel(this);
            sharedPreferenceModel.saveSharedPreferenceRank(player.getRank());
            playerPoints.setText("Points required to rank up: " + player.getSkillDifference());
        } else {
            Toast.makeText(getApplicationContext(), "Your Profile stats are not available, please contact FragDeluxe Staff", Toast.LENGTH_LONG).show();
            long seconds = 2000;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }, seconds);
        }
    }

    public void displayFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if (!(fragment instanceof FriendsFragment)) {
            fm.beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(null).commit();
        } else {
            fm.beginTransaction().replace(R.id.frame_content, fragment).commit();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setContentView(R.layout.activity_main_land);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            setContentView(R.layout.activity_main);
//        }
    }

    @Override
    public ArrayList<Statistics> UserRandomStatCallBack() {
        return globalData.getRandomList();
    }

    @Override
    public ArrayList<Player> UserRankedCallBack() {
        return globalData.getCompareList();
    }

    @Override
    public ArrayList<GameServer> fetchGameServers() {
        return globalData.getServerList();
    }

    @Override
    public ArrayList<Weapon> fetchWeaponList() {
        Player player = globalData.getPlayer();
        if (player != null) {
            return globalData.getPlayer().getListOfWeapons();
        } else {
            return new ArrayList<Weapon>();
        }
    }

    @Override
    public ArrayList<GameMap> fetchMapList() {
        Player player = globalData.getPlayer();
        if (player != null) {
            return globalData.getPlayer().getListOfMaps();
        } else {
            return new ArrayList<GameMap>();
        }
    }

    @Override
    public ArrayList<Player> CompareList() {
        Player player = globalData.getPlayer();
        if (player != null) {
            return globalData.getCompareList();
        } else {
            return new ArrayList<Player>();
        }
    }

    @Override
    public ArrayList<Integer> getAboutStatsList() {
        return globalData.getAboutList();
    }

    private class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.actionHome:
                    displayFragment(MainFragment.newInstance());
                    return true;
                case R.id.actionServer:
                    displayFragment(ServerFragment.newInstance());
                    return true;
                case R.id.actionSupport:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.fragdeluxe.com/community/index.php?app=nexus&module=clients&section=donations"));
                    startActivity(intent);
                    return true;
                case R.id.actionAbout:
                    AboutFragment aboutFragment = AboutFragment.newInstance();
                    aboutFragment.show(getSupportFragmentManager(), "com.fragdeluxeskin");
                    return true;
                case R.id.actionLeaderboard:
                    displayFragment(RankFragment.newInstance());
                    return true;
                case R.id.actionLogout:
                    sharedPreferenceModel.saveSharedPreferenceSteamID("0");
                    Intent intentLogin = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentLogin);
                    return true;
                case R.id.actionCompare:
                    FragmentManager fm = getSupportFragmentManager();
                    ArrayList<Player> players = new ArrayList<Player>();
                    players.add(globalData.getPlayer());
                    CompareDialogFragment dialogFragment = CompareDialogFragment.newInstance(players);
                    dialogFragment.show(fm, "com.comparefragment");
                    return true;
                case R.id.actionSkin:
                    Intent intentSkin = new Intent(Intent.ACTION_VIEW);
                    intentSkin.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.fragdeluxestatsskins"));
                    startActivity(intentSkin);
                    return true;
                case R.id.actionSync:
                    if (Utility.isNetworkAvailable(getApplicationContext())) {
                        finish();
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    } else {
                        Utility.ShowInternetError(getApplicationContext());
                    }
                    return true;

            }

            return false;
        }

    }
}
