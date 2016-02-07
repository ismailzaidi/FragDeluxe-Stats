package com.fragdeluxestats.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Yusuf on 31/12/2015.
 */
public class AutoCompleteAdapter extends ArrayAdapter<Player> implements Filterable,SectionIndexer {
    private ArrayList<Player> playerArrayList;
    private ArrayList<Player> filter_list;
    private Utility utility;
    private PlayerFilter filter;
    private Context context;
    private String[] sections;
    private HashMap<String,Integer> hashMap;
    public AutoCompleteAdapter(Activity context, ArrayList<Player> playerArrayList){
        super(context, R.layout.player_item,playerArrayList);
        this.playerArrayList=playerArrayList;
        this.filter_list=playerArrayList;
        this.context = context.getApplicationContext();
        utility = new Utility(context.getApplicationContext());
        generateIndexer();
    }
    private void generateIndexer(){
        hashMap = new LinkedHashMap<String,Integer>();

        for(int i=0;i<playerArrayList.size();i++){
            String userName = playerArrayList.get(i).getName();
            String firstChar = userName.substring(0,1);
            firstChar = firstChar.toUpperCase(Locale.UK);
            hashMap.put(firstChar,i);
        }
        Set<String> keyList = hashMap.keySet();
        ArrayList<String> listOfLetterers = new ArrayList<String>(keyList);
        sections = new String[listOfLetterers.size()];
        listOfLetterers.toArray(sections);


    }
    private class ViewHolder{
        CircleImageView profileImage;
        TextStyleView profileName;
        TextView countryName;
        ImageView countryImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView =  LayoutInflater.from(getContext()).inflate(R.layout.player_item,parent,false);
            holder.profileImage = (CircleImageView) convertView.findViewById(R.id.profileImage);
            holder.profileName = (TextStyleView) convertView.findViewById(R.id.profileName);
            holder.countryName = (TextView) convertView.findViewById(R.id.compareCountryName);
            holder.countryImage = (ImageView) convertView.findViewById(R.id.compareCountryImage);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Player player = playerArrayList.get(position);
        String userURL = player.getAvatar();
        String userName = player.getName();
        String userCountry = player.getCountryName();
        String userCode = player.getCountryCode();
        int countryCode = utility.getFlagID(userCode);
        holder.profileName.setText(userName);
        if(countryCode!=0){
            Picasso.with(context).load(countryCode).into(holder.countryImage);
            holder.countryName.setText(userCountry);
        }else{
            Picasso.with(context).load(android.R.color.transparent).into(holder.countryImage);
            holder.countryName.setText("");
        }
        int online = player.getOnline();
        if(online==1){
            holder.profileImage.setBorderColor(ContextCompat.getColor(getContext(),R.color.greenbutton));
        }else{
            holder.profileImage.setBorderColor(ContextCompat.getColor(getContext(),R.color.greybackground));
        }
        Picasso.with(getContext()).load(userURL).into(holder.profileImage);
        return convertView;
    }

    @Override
    public Player getItem(int position) {
        return playerArrayList.get(position);
    }

    @Override
    public int getCount() {
        Log.v("STANDARD_LOGIN", "getCount(): " +playerArrayList.size());
        return playerArrayList.size();
    }
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new PlayerFilter();
        }
        return filter;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    private class PlayerFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                results.values = filter_list;
                results.count = filter_list.size();
            } else {
                ArrayList<Player> nlist = new ArrayList<Player>();
                for (Player player : filter_list) {
                    String countryName = player.getName().toLowerCase();
                    if (countryName.contains(filterString)) {
                        Player element = player;
                        Log.v("CountryFilter Checker", "True: " + countryName  + " Country Code: " + element.getCountryCode());
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
            playerArrayList = (ArrayList<Player>) results.values;
            notifyDataSetChanged();
        }
    }


    @Override
    public Object[] getSections() {
        return sections;
    }
    @Override
    public int getPositionForSection(int i) {
        return hashMap.get(sections[i]);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

}