package com.fragdeluxestats.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.model.ResourceReference;
import com.fragdeluxestats.model.SharedPreferenceModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class MainMenuFragment extends Fragment implements View.OnClickListener {
    private LinearLayout serverListButton, statisticsListButton, weaponListButton, mapListButton, rankListButton,aboutButton;
    private TextView actionBarTitle;
    private String fragment_name = "Profile";
    private static String STATISTIC_KEY = "com.fragdeluxe.statistics";
    private GlobalData data;
    private Activity activity;
    private SharedPreferenceModel sharedPreferenceModel;
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mainMenuView = inflater.inflate(R.layout.main_menu_fragment, container, false);
        serverListButton = (LinearLayout) mainMenuView.findViewById(R.id.serverListButton);
        statisticsListButton = (LinearLayout) mainMenuView.findViewById(R.id.statisticsListButton);
        weaponListButton = (LinearLayout) mainMenuView.findViewById(R.id.weaponListButton);
        mapListButton = (LinearLayout) mainMenuView.findViewById(R.id.mapListButton);
        rankListButton = (LinearLayout) mainMenuView.findViewById(R.id.topListButton);
        aboutButton = (LinearLayout) mainMenuView.findViewById(R.id.aboutButton);
        serverListButton.setOnClickListener(this);
        statisticsListButton.setOnClickListener(this);
        weaponListButton.setOnClickListener(this);
        mapListButton.setOnClickListener(this);
        rankListButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        setActionBarTitle();
        sharedPreferenceModel = new SharedPreferenceModel(getContext());
        return mainMenuView;
    }

    public void setActionBarTitle() {
        actionBarTitle = (TextView) activity.findViewById(R.id.actionBarTitleTextView);
        ImageView actionBarIcon = (ImageView) getActivity().findViewById(R.id.actionBarIcon);
        Picasso.with(getActivity().getApplicationContext()).load(R.drawable.profile_icon_white).into(actionBarIcon);
        actionBarTitle.setText(fragment_name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragment_title", R.id.actionBarTitleTextView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Fragment fragment = null;
        if (id == R.id.serverListButton) {
            fragment = getFragmentList(0);
//            FragmentManager fm = getChildFragmentManager();
//            ProgressDialogFragment dialogFragment = ProgressDialogFragment.newInstance();
//            dialogFragment.show(fm, "com.progressdialog");
        }
        if (id == R.id.statisticsListButton) {
            fragment = getFragmentList(1);
        }
        if (id == R.id.weaponListButton) {
            fragment = getFragmentList(2);
        }
        if (id == R.id.mapListButton) {
            fragment = getFragmentList(3);
        }
        if (id == R.id.topListButton) {
            fragment = getFragmentList(4);
        }
        if(id==R.id.aboutButton){
            FragmentManager fm = getChildFragmentManager();
            AboutFragment dialogFragment = AboutFragment.newInstance();
            dialogFragment.show(fm, "com.progressdialog");
        }
        if(fragment!=null){
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_content, fragment).addToBackStack(null).commit();
        }
    }

    public Fragment getFragmentList(int position){
        List<Fragment> listOfFragments = new ArrayList<Fragment>();
        listOfFragments.add(ServerFragment.newInstance());
        listOfFragments.add(StatisticsFragment.newInstance());
        listOfFragments.add(WeaponFragment.newInstance());
        listOfFragments.add(MapFragment.newInstance());
        listOfFragments.add(RankFragment.newInstance());
        listOfFragments.add(AboutFragment.newInstance());
        return  listOfFragments.get(position);
    }
}
