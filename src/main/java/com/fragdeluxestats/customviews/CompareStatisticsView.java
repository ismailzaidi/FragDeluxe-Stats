package com.fragdeluxestats.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class CompareStatisticsView extends LinearLayout {
    private TextStyleView compareRank;
    private TextStyleView comparePoints;
    private TextStyleView compareKDA;
    private TextStyleView compareKills;
    private TextStyleView compareDeaths;
    private TextStyleView compareTimePlayed;
    private TextStyleView compareHeadShot;
    private TextStyleView compareWin;
    private TextStyleView compareAccuracy;
    private TextStyleView compareSuicides;
    private TextStyleView compareAssists;
    private TextStyleView compareAssisted;
    private TextStyleView compareKillStreak;
    private TextStyleView compareDeathStreak;
    private TextStyleView compareRoundsWon;
    private TextStyleView compareRoundsSurvived;
    private TextStyleView compareRoundsLost;
    private TextStyleView compareFavoriteWeaponKills;
    private ImageView compareWeaponImage;
    private Utility utility;
    public CompareStatisticsView(Context context) {
        this(context, null, 0);
    }
    public CompareStatisticsView(Context context, AttributeSet attr) {
        this(context, null, 0);
    }
    public CompareStatisticsView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        utility = new Utility(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        compareRank = (TextStyleView) findViewById(R.id.compareRank);
        comparePoints = (TextStyleView) findViewById(R.id.comparePoints);
        compareKDA = (TextStyleView) findViewById(R.id.compareKDA);
        compareKills = (TextStyleView) findViewById(R.id.compareKills);
        compareDeaths = (TextStyleView) findViewById(R.id.compareDeaths);
        compareTimePlayed = (TextStyleView) findViewById(R.id.compareTimePlayed);
        compareHeadShot = (TextStyleView) findViewById(R.id.compareHeadShot);
        compareWin = (TextStyleView) findViewById(R.id.compareWins);
        compareAccuracy = (TextStyleView) findViewById(R.id.compareAccuracy);
        compareSuicides = (TextStyleView) findViewById(R.id.compareSuicides);
        compareAssists = (TextStyleView) findViewById(R.id.compareAssists);
        compareAssisted = (TextStyleView) findViewById(R.id.compareAssisted);
        compareKillStreak = (TextStyleView) findViewById(R.id.compareKillStreak);
        compareDeathStreak = (TextStyleView) findViewById(R.id.compareDeathStreak);
        compareRoundsWon = (TextStyleView) findViewById(R.id.compareRoundsWon);
        compareRoundsSurvived = (TextStyleView) findViewById(R.id.compareRoundsSurvived);
        compareRoundsLost = (TextStyleView) findViewById(R.id.compareRoundsLost);
//        compareFavoriteWeaponName = (TextStyleView) findViewById(R.id.compareFavoriteWeaponName);
        compareFavoriteWeaponKills = (TextStyleView) findViewById(R.id.compareFavoriteWeaponKills);
        compareWeaponImage = (ImageView) findViewById(R.id.compareFavoriteWeaponImage);
    }
    public void setStatisticsItem(Player player) {
        double kda = (double) player.getKills() / player.getDeaths();
        double accuracy = 100 * player.getHits() / player.getShots();
        compareRank.setText(utility.getFormatSorter(player.getRank()));
        comparePoints.setText(utility.getFormatSorter(player.getSkill()));
        compareKDA.setText(String.format("%.2f",kda));
        compareKills.setText(utility.getFormatSorter(player.getKills()));
        compareDeaths.setText(utility.getFormatSorter(player.getDeaths()));
        compareTimePlayed.setText(utility.getFormatSorter(player.getTime())+"h");
        compareHeadShot.setText(utility.getFormatSorter(player.getHs()));
        compareWin.setText(utility.getFormatSorter(player.getWins()));
        compareAccuracy.setText(utility.getFormatSorter(accuracy));
        compareSuicides.setText(utility.getFormatSorter(player.getSuicides()));
        compareAssists.setText(utility.getFormatSorter(player.getAssists()));
        compareAssisted.setText(utility.getFormatSorter(player.getAssisted()));
        compareKillStreak.setText(utility.getFormatSorter(player.getKillstreak()));
        compareDeathStreak.setText(utility.getFormatSorter(player.getDeathStreak()));
        compareRoundsWon.setText(utility.getFormatSorter(player.getRounds()));
        compareRoundsSurvived.setText(utility.getFormatSorter(player.getSurvived()));
        compareRoundsLost.setText(utility.getFormatSorter(player.getLosses()));
        compareFavoriteWeaponKills.setText(utility.getFormatSorter(player.getWeaponKills()));
        Utility utility = new Utility(getContext());
        Log.v("COMPARE_WEAPON","Weapon: "+ player.getWeaponCode());
        if(!player.getWeaponCode().equals("DEFAULT") || player.getWeaponCode() != null){
            int weaponImage = utility.getDrawableID(getContext(),player.getWeaponCode());
            Picasso.with(getContext()).load(weaponImage).into(compareWeaponImage);
        }

    }

}
