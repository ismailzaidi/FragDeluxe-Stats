package com.fragdeluxestats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.fragdeluxestats.adapters.MapRecycleAdapter;
import com.fragdeluxestats.adapters.WeaponRecycleAdapter;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.DataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {
    private RecyclerView recyclerView;
    private MapRecycleAdapter adapter;
    private MapFragment.MapCallBack callBack;

    public interface MapCallBack {
        ArrayList<GameMap> fetchMapList();
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_content, container, false);
        List<GameMap> listOfServers = callBack.fetchMapList();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MapRecycleAdapter(listOfServers);
        Log.v("SERVER_INSPECTION", "Inside OnCreateView");
        recyclerView.setAdapter(adapter);
//        setActionBarTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (MapFragment.MapCallBack) context;
    }

    public void setActionBarTitle() {
        String fragment_name = "Maps";
        TextView actionBarTitle = (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        if (actionBarTitle != null) {
            ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
            Picasso.with(getActivity().getApplicationContext()).load(R.drawable.map_icon_white).into(actionBarIcon);
            actionBarTitle.setText(fragment_name);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
