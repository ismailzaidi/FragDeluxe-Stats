package com.fragdeluxestats.fragments;

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
import com.fragdeluxestats.adapters.StatisticsRecyclerAdapter;
import com.fragdeluxestats.adapters.WeaponRecycleAdapter;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WeaponFragment extends Fragment {
    private RecyclerView recyclerView;
    private WeaponRecycleAdapter adapter;
    private WeaponFragment.WeaponCallBack callBack;
    public interface WeaponCallBack{
        ArrayList<Weapon> fetchWeaponList();
    }
    public static WeaponFragment newInstance(){
        WeaponFragment fragment = new WeaponFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_content,container,false);
        List<Weapon> listOfServers = callBack.fetchWeaponList();
        if(listOfServers==null){
            listOfServers = new ArrayList<Weapon>();
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WeaponRecycleAdapter(listOfServers);
        Log.v("SERVER_INSPECTION" ,"Inside OnCreateView");
        recyclerView.setAdapter(adapter);
//        setActionBarTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (WeaponFragment.WeaponCallBack) context;
    }

    public void setActionBarTitle(){
        String fragment_name = "Weapons";
        TextView  actionBarTitle= (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        if(actionBarTitle!=null){
            ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
            Picasso.with(getActivity().getApplicationContext()).load(R.drawable.glock_icon_white).into(actionBarIcon);
            actionBarTitle.setText(fragment_name);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
