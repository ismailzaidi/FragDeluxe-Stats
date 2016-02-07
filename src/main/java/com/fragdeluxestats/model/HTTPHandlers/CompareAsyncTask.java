package com.fragdeluxestats.model.HTTPHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.DataHandler;
import com.fragdeluxestats.model.Utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Yusuf on 24/12/2015.
 */
public class CompareAsyncTask extends AsyncTask<String, Integer, ArrayList<Player>> {
    private Context context;

    public interface GetCompareData {
        void fetchUserData(ArrayList<Player> data);
    }

    public interface CompareProgressBar {
        void updateProgressBar(int progress);
    }

    private CompareProgressBar progressListener;
    private GetCompareData dataListener;
    private ArrayList<Player> steamIDS;

    public CompareAsyncTask(Context context, ArrayList<Player> steamIDS, CompareProgressBar progressListener, GetCompareData dataListener) {
        this.context = context;
        this.steamIDS = steamIDS;
        this.progressListener = progressListener;
        this.dataListener = dataListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Player> doInBackground(String... strings) {
        ArrayList<Player> listOfPlayers = new ArrayList<Player>();
        double counter = 100 / steamIDS.size();
        for (int i = 0; i < steamIDS.size(); i++) {
            Player player = steamIDS.get(i);
            String uniqueID = player.getUniqueid();
            DataHandler dataHandler = new DataHandler(context, uniqueID, true);
            listOfPlayers.add(dataHandler.getPlayerSummary());
            publishProgress((int) counter);
            counter += counter;
        }
        return listOfPlayers;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressListener.updateProgressBar(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Player> globalData) {
        super.onPostExecute(globalData);
        dataListener.fetchUserData(globalData);

    }

    private void specifyRange(int from, int to) {
        for (int i = from; i < to; i++) {
            publishProgress(i);
        }

    }

}
