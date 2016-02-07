package com.fragdeluxestats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.ServerRecycleAdapter;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.model.DataModel;
import com.fragdeluxestats.model.ModelInterfaces;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServerFragment extends Fragment {
    private RecyclerView recyclerView;
    private ServerRecycleAdapter adapter;
    private ServerFragment.ServerCallBack callBack;

    public interface ServerCallBack{
        ArrayList<GameServer> fetchGameServers();
    }
    public static ServerFragment newInstance() {
        ServerFragment fragment = new ServerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_content_server, container, false);
        List<GameServer> listOfServers = callBack.fetchGameServers();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL, false));
        adapter = new ServerRecycleAdapter(listOfServers);
        Log.v("SERVER_INSPECTION", "Inside OnCreateView");
        recyclerView.setAdapter(adapter);
        setActionBarTitle();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       callBack = (ServerFragment.ServerCallBack) context;
    }

    public void setActionBarTitle() {
        String fragment_name = "Servers";
        TextView actionBarTitle = (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
        Picasso.with(getActivity().getApplicationContext()).load(R.drawable.server_icon_white).into(actionBarIcon);
        actionBarTitle.setText(fragment_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
