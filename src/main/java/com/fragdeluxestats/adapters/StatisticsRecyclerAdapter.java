package com.fragdeluxestats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.customviews.StatView;

import java.util.List;

public class StatisticsRecyclerAdapter extends RecyclerView.Adapter<StatisticsRecyclerAdapter.StatsHolder> {
    private List<Statistics> listOfStats;

    public StatisticsRecyclerAdapter(List<Statistics> listOfStats) {
        this.listOfStats = listOfStats;
    }
    @Override
    public StatsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StatView weaponView = (StatView) LayoutInflater.from(parent.getContext()).inflate(R.layout.state_item, null);
        StatsHolder holder = new StatsHolder(weaponView);
        return holder;
    }

    @Override
    public void onBindViewHolder(StatsHolder holder, int position) {
        StatView view = (StatView) holder.itemView;
        view.setStatObject(listOfStats.get(position));
    }

    public static class StatsHolder extends RecyclerView.ViewHolder {
        public StatsHolder(View itemRow) {
            super(itemRow);
        }
    }
    public void clear(){
        listOfStats.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Statistics> list){
        listOfStats.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listOfStats.size();
    }
}
