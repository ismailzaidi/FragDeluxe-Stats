package com.fragdeluxestats.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.RankView;
import com.fragdeluxestats.customviews.ServerView;
import com.fragdeluxestats.model.ProfileLog;
import com.fragdeluxestats.model.Utility;

import java.util.List;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class ServerRecycleAdapter extends RecyclerView.Adapter<ServerRecycleAdapter.GameServerHolder> {
    private List<GameServer> listOfServers;
    public ServerRecycleAdapter(List<GameServer> listOfServers) {
        Log.v("SERVER_INSPECTION", "Size: " + listOfServers.size());
        this.listOfServers = listOfServers;
    }

    @Override
    public GameServerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("SERVER_INSPECTION", "Inside OnCreateViewHolder");
        ServerView view = (ServerView) LayoutInflater.from(parent.getContext()).inflate(R.layout.server_item, parent,false);
        GameServerHolder holder = new GameServerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GameServerHolder holder, int position) {
        Log.v("SERVER_INSPECTION", "Inside onBindViewHolder");
        ProfileLog.tick();
        ServerView serverView = (ServerView) holder.itemView;
        Log.v("SERVER_TYPE_INFO" ,  "Server Name (onBindViewHolder): " + listOfServers.get(position).getServerName());
        serverView.setServerObject(listOfServers.get(position));
    }

    public static class GameServerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ScrollView scrollView;
        private ImageView arrowImageButton;
        private ScrollView scrollViewCT;
        private ImageView arrowImageButtonCT;
        private int counter =0;
        public GameServerHolder(View itemView) {
            super(itemView);
            ServerView view = (ServerView)  itemView;
            scrollView = view.getScrollView();
            scrollViewCT = view.getScrollViewCT();

            arrowImageButton = view.getArrowImageButton();
            arrowImageButtonCT = view.getArrowImageButtonCT();
            arrowImageButton.setOnClickListener(this);
            arrowImageButtonCT.setOnClickListener(this);
            view.setTag(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == arrowImageButton.getId()){
                if(counter%2==0){
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    arrowImageButton.setImageResource(R.drawable.arrow_up);
                }else{
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    arrowImageButton.setImageResource(R.drawable.arrow_down);
                }
                counter++;
            }
            if(id == arrowImageButtonCT.getId()){
                if(counter%2==0){
                    scrollViewCT.fullScroll(ScrollView.FOCUS_DOWN);
                    arrowImageButtonCT.setImageResource(R.drawable.arrow_up);
                }else{
                    scrollViewCT.fullScroll(ScrollView.FOCUS_UP);
                    arrowImageButtonCT.setImageResource(R.drawable.arrow_down);
                }
                counter++;
            }
        }
    }
    public void clear(){
        listOfServers.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<GameServer> list){
        listOfServers.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listOfServers.size();
    }
}
