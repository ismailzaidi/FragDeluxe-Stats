package com.fragdeluxestats.model.HTTPHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.DataHandler;

import java.util.ArrayList;

/**
 * Created by Yusuf on 24/12/2015.
 */
public class GuestAsyncTask extends AsyncTask<String, Integer, ArrayList<Player>> {
    private Context context;
    private DataAsyncTask.UserProgressBar progressCallBack;
    public interface GuestFetchPlayers {
        void fetchPlayers(ArrayList<Player> data);
    }
    private GuestFetchPlayers dataListener;
    public GuestAsyncTask(Context context, GuestFetchPlayers dataListener, DataAsyncTask.UserProgressBar progressCallBack) {
        this.context = context;
        this.dataListener = dataListener;
        this.progressCallBack=progressCallBack;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Player> doInBackground(String... strings) {
        DataHandler dataHandler = new DataHandler(context);
        ArrayList<Player> guestList = dataHandler.getGuestList();
        Log.v("GUEST_LIST", "Guest List: " + guestList.size());
        publishProgress(100);
        return guestList;
    }

    @Override
    protected void onPostExecute(ArrayList<Player> guestList) {
        super.onPostExecute(guestList);
        dataListener.fetchPlayers(guestList);
    }

}
