package com.fragdeluxestats.adapters.viewpageadapers;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.fragments.CompareItemFragment;
import com.fragdeluxestats.fragments.MapFragment;
import com.fragdeluxestats.fragments.StatisticsFragment;
import com.fragdeluxestats.fragments.WeaponFragment;
import com.fragdeluxestats.model.ResourceReference;

import java.util.ArrayList;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class FriendsViewPager extends FragmentPagerAdapter {
    private Fragment fragment;
    private Context context;
    private ArrayList<Statistics> listOfStatistics;
    public FriendsViewPager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    public View getTabView(int position, int drawable) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.icon);
        tabIcon.setBackground(ContextCompat.getDrawable(context,drawable));
//        Picasso.with(context).load(drawable).into(tabIcon);
        return view;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = StatisticsFragment.newInstance();
                break;
            case 1:
                fragment = WeaponFragment.newInstance();
                break;
            case 2:
                fragment = MapFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return ResourceReference.types[position];
//    }
}
