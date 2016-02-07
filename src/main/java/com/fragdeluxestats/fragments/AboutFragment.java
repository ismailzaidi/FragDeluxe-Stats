package com.fragdeluxestats.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.viewpageadapers.AboutViewPager;

import java.util.ArrayList;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class AboutFragment extends DialogFragment{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AboutViewPager adapter;
    private int[] imageResID = {R.drawable.about_info_selector, R.drawable.about_info_selector, R.drawable.about_info_selector};

    public static AboutFragment newInstance(){
        AboutFragment fragment = new AboutFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View aboutView = inflater.inflate(R.layout.about_dialog, container,false);
        tabLayout = (TabLayout) aboutView.findViewById(R.id.tabLayout);
        tabLayout.setTabTextColors(getColorList());
        viewPager = (ViewPager) aboutView.findViewById(R.id.viewPager);
        adapter = new AboutViewPager(getChildFragmentManager(),getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        generateTabs();
        return aboutView;
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
