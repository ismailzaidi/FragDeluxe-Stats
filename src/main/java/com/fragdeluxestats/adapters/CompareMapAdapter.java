package com.fragdeluxestats.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.CompareItem;
import com.fragdeluxestats.model.ProfileLog;
import com.fragdeluxestats.model.ResourceReference;

import java.util.List;

public class CompareMapAdapter extends RecyclerView.Adapter<CompareMapAdapter.CompareHolder> {
    private List<Player> compareList;
    private String type;

    public CompareMapAdapter(List<Player> compareList, String type) {
        this.compareList = compareList;
        this.type = type;
    }

    @Override
    public CompareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CompareItem view = (CompareItem) LayoutInflater.from(parent.getContext()).inflate(R.layout.compare_item, parent, false);
        return new CompareHolder(view);
    }

    @Override
    public void onBindViewHolder(CompareHolder holder, int position) {
        CompareItem compareView = (CompareItem) holder.itemView;
        Player player = compareList.get(position);
        Log.v("COMPARE_RECYCLER", "Player: " + player.getName());
        compareView.setPlayerObject(player);
        compareView.setMapLayout(player);
    }

    @Override
    public int getItemCount() {
        return compareList.size();
    }

    public static class CompareHolder extends RecyclerView.ViewHolder {
        public CompareHolder(View itemRow) {
            super(itemRow);
        }
    }

}
