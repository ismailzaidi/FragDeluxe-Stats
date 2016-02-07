package com.fragdeluxestats.model.HTTPHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.model.DataHandler;

import java.util.ArrayList;

/**
 * Created by Yusuf on 24/12/2015.
 */
public class DataAsyncTask extends AsyncTask<String, Integer, GlobalData> {
    private Context context;

    public interface GetUserData {
        void fetchUserData(GlobalData data);
    }

    public interface UserProgressBar {
        void updateProgressBar(int progress);
    }

    private UserProgressBar progressListener;
    private GetUserData dataListener;
    private String steamID;
    private boolean isFriend;

    public DataAsyncTask(Context context, String steamID, UserProgressBar progressListener, GetUserData dataListener, boolean isFriend) {
        this.context = context;
        this.steamID = steamID;
        this.progressListener = progressListener;
        this.dataListener = dataListener;
        this.isFriend = isFriend;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GlobalData doInBackground(String... strings) {
        GlobalData globalData;
        if (isFriend) {
            globalData = getFriendData();
        } else {
            globalData = getGlobalData();
        }
        return globalData;
    }

    public GlobalData getFriendData() {
        DataHandler dataHandler = new DataHandler(context, steamID, true);
        specifyRange(0, 19);
        publishProgress(20);
        specifyRange(21, 39);
        sleep();
        Player player = dataHandler.getPlayerSummary();

        publishProgress(40);
        specifyRange(41, 59);
        publishProgress(60);
        specifyRange(61, 79);
        publishProgress(80);
        sleep();
        specifyRange(80, 99);
        publishProgress(100);
        ArrayList<Statistics> randomStat = dataHandler.getStatsList(player);
        GlobalData globalData = new GlobalData(player, randomStat);
        return globalData;
    }

    public GlobalData getGlobalData() {
        DataHandler dataHandler = new DataHandler(context, steamID, false);
        specifyRange(0, 19);
        publishProgress(20);
        specifyRange(21, 39);
        Player player = dataHandler.getPlayerSummary();
        Log.v("PLAYER_INFO", "Player: " + player);
        publishProgress(40);
        ArrayList<GameServer> listOfServers = dataHandler.getServerList();
        Log.v("SERVER_RANK", "Size of servers" + listOfServers.size());
        sleep();
        specifyRange(41, 59);
        publishProgress(60);
        specifyRange(61, 79);
        sleep();
        ArrayList<Statistics> randomStat = dataHandler.getStatsList(player);
        ArrayList<Player> compareList = dataHandler.getRankedList();
        ArrayList<Integer> aboutStats = new AboutDataAsync().getAboutStats();
        publishProgress(80);
        specifyRange(80, 99);
        Log.v("ABOUT_VIEW", "DataAsyncTask  Arr Size: " + aboutStats.size());
        updatePossibleSlots(listOfServers, aboutStats);
        publishProgress(100);
        GlobalData globalData = new GlobalData(player,randomStat, listOfServers, compareList, aboutStats);
        return globalData;
    }

    private void updatePossibleSlots(ArrayList<GameServer> serverList, ArrayList<Integer> listToUpdate) {
        int totalSlots = 0;
        int onlineUsers = 0;
        for (int i = 0; i < serverList.size(); i++) {
            onlineUsers += serverList.get(i).getOnlinePlayers();
            totalSlots += serverList.get(i).getMaximumPlayers();
        }
        listToUpdate.add(onlineUsers);
        listToUpdate.add(totalSlots);
    }

    public void sleep() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sleepCustom() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressListener.updateProgressBar(values[0]);
    }

    @Override
    protected void onPostExecute(GlobalData globalData) {
        super.onPostExecute(globalData);
        dataListener.fetchUserData(globalData);

    }

    private void specifyRange(int from, int to) {
        for (int i = from; i < to; i++) {
            publishProgress(i);
        }

    }

}
