package com.fragdeluxestats.adapters;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.activities.FriendsActivity;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.RankView;
import com.fragdeluxestats.model.ModelInterfaces;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class RankViewAdapter extends RecyclerView.Adapter<RankViewAdapter.RankHolder> implements Filterable {
    private ArrayList<Player> listOfPlayers;
    private ArrayList<Player> filter_list;
    private ModelInterfaces.ItemClickListener listener;
    private FragmentActivity activity;
    private PlayerFilter filter;

    /**
     * For Viewing Top 100 Rank
     *
     * @param listOfPlayers
     */
    public RankViewAdapter(ArrayList<Player> listOfPlayers, FragmentActivity activity) {
        this.listOfPlayers = listOfPlayers;
        this.activity = activity;
        filter_list = listOfPlayers;
    }

    /**
     * For Compare Function
     *
     * @param listOfPlayers
     * @param listener
     */
    public RankViewAdapter(ArrayList<Player> listOfPlayers, ModelInterfaces.ItemClickListener listener) {
        this.listOfPlayers = listOfPlayers;
        this.listener = listener;
        filter_list = listOfPlayers;
    }
    @Override
    public RankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RankView rankView = null;
        if (listener != null) {
            rankView = (RankView) LayoutInflater.from(parent.getContext()).inflate(R.layout.compare_friend_rank, parent, false);
        } else {
            rankView = (RankView) LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_player_item, parent, false);
        }
        RankHolder holder = new RankHolder(rankView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RankHolder holder, int position) {
        RankView view = (RankView) holder.itemView;
        Player player = listOfPlayers.get(position);
        if(player.isSelected()){
            view.setSelected(true);
        }else{
            view.setSelected(false);
        }
//        setSelectedItems(position, view);
        view.setPlayerRankObject(player);
    }
    public class RankHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout statisticsButton;
        private RankView rankView;

        public RankHolder(View itemRow) {
            super(itemRow);
            itemRow.setOnClickListener(this);
            rankView = (RankView) itemRow;
            statisticsButton = rankView.getPlayerStatisticButton();
            if (statisticsButton != null) {
                statisticsButton.setOnClickListener(this);
            }
            if (statisticsButton != null && listener != null) {
                statisticsButton.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            Log.v("rank", "Player: " + getObjectItem(getLayoutPosition()).getName());
            if (listener != null) {
                int position = getLayoutPosition();
                Player player = getObjectItem(position);
                listener.onClickListener(view, position, rankView);
            }
            if (view.getId() == R.id.profileStatsButton) {
                if (Utility.isNetworkAvailable(activity.getApplicationContext())) {
                    Intent friendsIntent = new Intent(activity.getApplicationContext(), FriendsActivity.class);
                    friendsIntent.putExtra(ResourceReference.FRIENDS_KEY, getObjectItem(getLayoutPosition()).getUniqueid());
                    activity.startActivity(friendsIntent);
                } else {
                    Utility.ShowInternetError(activity.getApplicationContext());
                }
            }
        }

    }

    public Player getObjectItem(int position) {
        return listOfPlayers.get(position);
    }

    public void clear() {
        listOfPlayers.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Player> list) {
        listOfPlayers.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listOfPlayers.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new PlayerFilter();
        }
        return filter;
    }


    private class PlayerFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = filter_list;
                results.count = filter_list.size();
            } else {
                ArrayList<Player> nlist = new ArrayList<Player>();
                for (Player player : filter_list) {
                    String countryName = player.getName().toLowerCase();
                    if (countryName.contains(filterString)) {
                        Player element = player;
                        Log.v("CountryFilter Checker", "True: " + countryName);
                        nlist.add(element);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listOfPlayers = (ArrayList<Player>) results.values;
            notifyDataSetChanged();
        }
    }

}
