package com.fragdeluxestats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.MapView;

import java.util.List;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class MapRecycleAdapter extends RecyclerView.Adapter<MapRecycleAdapter.MapViewHolder> {
    private List<GameMap> listOfMaps;

    public MapRecycleAdapter(List<GameMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MapView mapView = (MapView) LayoutInflater.from(parent.getContext()).inflate(R.layout.map_view, null);
        MapViewHolder holder = new MapViewHolder(mapView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MapViewHolder holder, int position) {
        GameMap gameMap = listOfMaps.get(position);
        MapView mapView = (MapView) holder.itemView;
        mapView.setMapObject(gameMap);
    }

    public static class MapViewHolder extends RecyclerView.ViewHolder {

        public MapViewHolder(View itemRow) {
            super(itemRow);
        }

    }
    public void clear(){
        listOfMaps.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<GameMap> list){
        listOfMaps.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listOfMaps.size();
    }
}
