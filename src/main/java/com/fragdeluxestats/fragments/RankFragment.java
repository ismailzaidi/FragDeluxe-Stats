package com.fragdeluxestats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.MapRecycleAdapter;
import com.fragdeluxestats.adapters.RankViewAdapter;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.fragments.progressbars.LeaderBoardProgressBar;
import com.fragdeluxestats.model.DataModel;
import com.fragdeluxestats.model.HTTPHandlers.LeaderBoardTask;
import com.fragdeluxestats.model.ModelInterfaces;
import com.fragdeluxestats.model.SharedPreferenceModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment {
    private RecyclerView recyclerView;
    private RankViewAdapter adapter;
    private RankFragment.RankCallBack callBack;
    private EditText userIdEditText;
    private ArrayList<Player> listOfPlayers;

    public interface RankCallBack {
        ArrayList<Player> UserRankedCallBack();
    }

    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.data_content_rank, container, false);
        final LeaderBoardProgressBar progressBar = new LeaderBoardProgressBar(getActivity());
        LeaderBoardTask.LeaderBoardCallBack leaderBoardCallBack = new LeaderBoardTask.LeaderBoardCallBack() {
            @Override
            public void fetchPlayers(ArrayList<Player> data) {
                listOfPlayers = data;
                generateView(view);
                progressBar.dismiss();
            }
        };
        LeaderBoardTask task = new LeaderBoardTask(getActivity(),leaderBoardCallBack, progressBar);
        task.execute();
        return view;
    }

    private void generateView(View view) {
        if (listOfPlayers != null) {
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            final RelativeLayout rankButton = (RelativeLayout) view.findViewById(R.id.rankButton);
            userIdEditText = (EditText) view.findViewById(R.id.userIdEditText);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            final int rank = new SharedPreferenceModel(getContext()).loadSharedPreferenceRank();
            rankButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rank > listOfPlayers.size()) {
                        Toast.makeText(getContext(), "Rank not found, wait 24 Hours", Toast.LENGTH_SHORT).show();
                    } else {
                        recyclerView.scrollToPosition(rank);
                    }
                }
            });
            adapter = new RankViewAdapter(listOfPlayers, getActivity());
            Log.v("SERVER_INSPECTION", "Inside OnCreateView");
            recyclerView.setAdapter(adapter);
            userIdEditText.addTextChangedListener(textListener);
        }
        setActionBarTitle();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setActionBarTitle() {
        String fragment_name = "Leaderboard";
        TextView actionBarTitle = (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
        Picasso.with(getActivity().getApplicationContext()).load(R.drawable.trophy_icon_white).into(actionBarIcon);
        actionBarTitle.setText(fragment_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            adapter.getFilter().filter(input);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
