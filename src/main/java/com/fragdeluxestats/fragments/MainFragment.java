package com.fragdeluxestats.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.viewpageadapers.FriendsViewPager;
import com.squareup.picasso.Picasso;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class MainFragment extends Fragment{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FriendsViewPager adapter;
    private int[] imageResID = {R.drawable.tabselector_stats, R.drawable.tabselector_glock, R.drawable.tabselector_map};
    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View aboutView = inflater.inflate(R.layout.friend_content, container,false);
        tabLayout = (TabLayout) aboutView.findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(getColorList());
        viewPager = (ViewPager) aboutView.findViewById(R.id.viewPager);
        adapter = new FriendsViewPager(getChildFragmentManager(),getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        generateTabs();
        setActionBarTitle();
        return aboutView;
    }
    public void setActionBarTitle(){
        String fragment_name = "Home";
        TextView actionBarTitle= (TextView) getActivity().findViewById(R.id.actionBarTitleTextView);
        if(actionBarTitle!=null){
            ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
            Picasso.with(getActivity().getApplicationContext()).load(R.drawable.home_icon).into(actionBarIcon);
            actionBarTitle.setText(fragment_name);
        }
    }
    private void generateTabs() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setIcon(imageResID[i]);
        }
    }
    public ColorStateList getColorList() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected}, // enabled
                new int[]{-android.R.attr.state_selected}, // disabled
        };

        int[] colors = new int[]{
                R.color.profilecolor,
                R.color.greenbutton
        };
        return new ColorStateList(states, colors);
    }
}
