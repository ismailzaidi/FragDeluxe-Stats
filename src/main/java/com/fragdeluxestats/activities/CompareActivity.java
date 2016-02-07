package com.fragdeluxestats.activities;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.viewpageadapers.CompareViewPager;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.fragments.CompareItemFragment;
import com.fragdeluxestats.fragments.CompareItemMaps;
import com.fragdeluxestats.fragments.CompareItemStatistics;
import com.fragdeluxestats.fragments.progressbars.ProgressDialogFragment;
import com.fragdeluxestats.model.HTTPHandlers.CompareAsyncTask;
import com.fragdeluxestats.model.ProfileLog;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.Utility;

import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity implements CompareItemStatistics.CompareStatisticsCallBack, CompareItemFragment.CompareStatisticsCallBack,CompareItemMaps.CompareMapCallBack {
    private TabLayout tabLayout;
    private CompareViewPager adapter;
    private ViewPager viewPager;
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Player> compareAsyncList;
    private LinearLayout backButton;
    private int[] imageResID = {R.drawable.tabselector_stats, R.drawable.tabselector_glock, R.drawable.tabselector_map};
    private int[] tintColours = {android.R.color.white, R.color.profilecolor};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_activity);
        listOfPlayers = (ArrayList<Player>) getIntent().getSerializableExtra(ResourceReference.COMPARE_ARRAY);
        final ProgressDialogFragment fragment = new ProgressDialogFragment(this);
//        String steamID = Utility.steam64TOSteamID(userSteam64);
//        String steamID = Utility.steam64TOSteamID("76561198022395070");
        String steamID = "STEAM_0:1:99141982";
        CompareAsyncTask.GetCompareData userData = new CompareAsyncTask.GetCompareData() {
            @Override
            public void fetchUserData(ArrayList<Player> data) {
                compareAsyncList = data;
                initView();
                ProfileLog.tick();
                fragment.dismiss();
            }
        };
        CompareAsyncTask task = new CompareAsyncTask(getApplicationContext(), listOfPlayers, fragment, userData);
        task.execute();
    }

    private void initView() {
        ProfileLog.tick();
        setContentView(R.layout.compare_activity);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(getColorList());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        adapter = new CompareViewPager(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        backButton = (LinearLayout) findViewById(R.id.refreshButton);
        generateTabs();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void generateTabs() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(imageResID[i]);
        }
    }

    public ColorStateList getColorList() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected}, // enabled
                new int[]{-android.R.attr.state_selected}, // disabled
        };

        int[] colors = new int[]{
                android.R.color.white,
                R.color.profilecolor
        };
        return new ColorStateList(states, colors);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ArrayList<Player> FetchCompareList() {
        return compareAsyncList;
    }

    @Override
    public ArrayList<Player> FetchStatisticsList() {
        return compareAsyncList;
    }

    @Override
    public ArrayList<Player> FetchMapList() {
        return compareAsyncList;
    }
}
