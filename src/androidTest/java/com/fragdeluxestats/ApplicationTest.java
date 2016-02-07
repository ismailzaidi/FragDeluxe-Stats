package com.fragdeluxestats;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.model.DataHandler;

import java.util.ArrayList;

/**
 *
 *
 *
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 *
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }

    private MainActivity activity;
    private Context context;
    private String actualResult = null;
    private static final String PLAYER_NOT_STRING = "Player Not Found";

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        context = activity.getApplicationContext();
    }

    public void testPlayerFile() {
        DataHandler handler = new DataHandler(activity.getApplicationContext());
        ArrayList<Player> playerList = handler.getPlayerListOnline();
        for (Player player : playerList) {
            handler = new DataHandler(context, player.getUniqueid(), false);
            //Map List
            Player temp = handler.getPlayerSummary();
            ArrayList<Statistics> statsList = handler.getStatsList(player);
            assertNotNull(statsList);
        }


    }

}