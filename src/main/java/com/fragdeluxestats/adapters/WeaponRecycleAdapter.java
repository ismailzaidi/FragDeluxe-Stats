package com.fragdeluxestats.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.customviews.WeaponView;

import java.util.List;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class WeaponRecycleAdapter extends RecyclerView.Adapter<WeaponRecycleAdapter.WeaponHolder> {
    private List<Weapon> listOfWeapons;

    public WeaponRecycleAdapter(List<Weapon> listOfWeapons) {
        this.listOfWeapons = listOfWeapons;
    }

    @Override
    public WeaponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WeaponView weaponView = (WeaponView) LayoutInflater.from(parent.getContext()).inflate(R.layout.weapon_view, null);
        WeaponHolder holder = new WeaponHolder(weaponView);
        return holder;
    }

    @Override
    public void onBindViewHolder(WeaponHolder holder, int position) {
        WeaponView view = (WeaponView) holder.itemView;
        view.setWeaponObject(listOfWeapons.get(position));
    }

    public static class WeaponHolder extends RecyclerView.ViewHolder {
        public WeaponHolder(View itemRow) {
            super(itemRow);
        }
    }
    public void clear(){
        listOfWeapons.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Weapon> list){
        listOfWeapons.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listOfWeapons.size();
    }
}
