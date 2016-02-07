package com.fragdeluxestats.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.CompareRecyclerAdapter;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.CompareStatisticsView;
import com.fragdeluxestats.model.DataModel;
import com.fragdeluxestats.model.ResourceReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class CompareItemFragment extends Fragment{
    private RecyclerView recyclerView;
    private CompareRecyclerAdapter adapter;
    private CompareStatisticsCallBack callback;
    public interface CompareStatisticsCallBack{
        ArrayList<Player> FetchCompareList();
    }
    public static CompareItemFragment newInstance(String type){
        CompareItemFragment fragment = new CompareItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ResourceReference.COMPARE_TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     *
     *
     * NORSKEN
     * Stablis
     * JavaFreak
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View compareView = inflater.inflate(R.layout.compare_fragment, container,false);
        String type = getArguments().getString(ResourceReference.COMPARE_TYPE);
        List<Player> listOfCompare = callback.FetchCompareList();
        int numofFriends = listOfCompare.size();
        recyclerView = (RecyclerView) compareView.findViewById(R.id.compareRecyclerView);
        recyclerView.setHasFixedSize(false);
        Collections.reverse(listOfCompare);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numofFriends));
        adapter = new CompareRecyclerAdapter(listOfCompare,type);
        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return compareView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (CompareItemFragment.CompareStatisticsCallBack) context;

    }
}
