package com.fragdeluxestats.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.ServerRecycleAdapter;
import com.fragdeluxestats.adapters.StatisticsRecyclerAdapter;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.model.DataModel;
import com.fragdeluxestats.model.ModelInterfaces;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private RecyclerView recyclerView;
    private StatisticsRecyclerAdapter adapter;
    private StatisticsFragment.StatsInterFace callBack;

    public interface StatsInterFace{
        ArrayList<Statistics>  UserRandomStatCallBack();
    }
    public static StatisticsFragment newInstance(){
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_content,container,false);
        List<Statistics> listOfServers = callBack.UserRandomStatCallBack();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false));
        adapter = new StatisticsRecyclerAdapter(listOfServers);
        Log.v("SERVER_INSPECTION" ,"Inside OnCreateView");
        recyclerView.setAdapter(adapter);
//        setActionBarTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (StatisticsFragment.StatsInterFace) context;
    }
    public void setActionBarTitle(){
        String fragment_name = "Statistics";
        TextView  actionBarTitle= (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        if(actionBarTitle!=null){
            ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
            Picasso.with(getActivity().getApplicationContext()).load(R.drawable.stats_icon_white).into(actionBarIcon);
            actionBarTitle.setText(fragment_name);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
