package com.fragdeluxestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.fragdeluxestats.MainActivity;
import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.RankViewAdapter;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.fragments.progressbars.ProgressDialogFragment;
import com.fragdeluxestats.model.HTTPHandlers.GuestAsyncTask;
import com.fragdeluxestats.model.ModelInterfaces;
import com.fragdeluxestats.model.SharedPreferenceModel;
import com.fragdeluxestats.model.Utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GuestActivity extends AppCompatActivity {
    private RecyclerView guestRecyclerView;
    private RankViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_activity);
        final ProgressDialogFragment dialog = new ProgressDialogFragment(GuestActivity.this,true);
        guestRecyclerView = (RecyclerView) findViewById(R.id.guestRecyclerView);
        new GuestAsyncTask(getApplicationContext(), new GuestAsyncTask.GuestFetchPlayers() {
            @Override
            public void fetchPlayers(ArrayList<Player> data) {
                init(data);
                dialog.dismiss();
            }
        }, dialog).execute();
    }

    private void init(ArrayList<Player> data) {
        adapter = new RankViewAdapter(data, new RecyclerListener());
        guestRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        guestRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private class RecyclerListener implements ModelInterfaces.ItemClickListener {
        @Override
        public void onClickListener(View view, int position, LinearLayout background) {
            Player player = adapter.getObjectItem(position);
            Log.v("STANDARD_LOGIN", "Click Position : " + position + " Item: " + player.getName());
            boolean isNetwork = Utility.isNetworkAvailable(getApplicationContext());
            if(isNetwork){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                SharedPreferenceModel sharedPreferenceModel = new SharedPreferenceModel(getApplicationContext());
                sharedPreferenceModel.saveSharedPreferenceSteamID(player.getUniqueid());
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }else{
                Utility.ShowInternetError(getApplicationContext());
            }
        }
    }
    private class SortPlayer implements Comparator<Player> {
        @Override
        public int compare(Player player, Player t1) {
            String player1 = player.getName();
            return player1.compareTo(t1.getName());
        }
    }

}
