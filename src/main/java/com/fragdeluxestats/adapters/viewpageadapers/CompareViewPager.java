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
import com.fragdeluxestats.fragments.CompareItemFragment;
import com.fragdeluxestats.fragments.CompareItemMaps;
import com.fragdeluxestats.fragments.CompareItemStatistics;
import com.fragdeluxestats.model.ResourceReference;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class CompareViewPager extends FragmentPagerAdapter {
    private Fragment fragment;
    private Context context;
    private int[] imageResID = {R.drawable.stats_dark, R.drawable.glock_dark, R.drawable.map_dark};
    public CompareViewPager(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment = CompareItemStatistics.newInstance(ResourceReference.types[position]);
                break;
            case 1:
                fragment = CompareItemFragment.newInstance(ResourceReference.types[position]);
                break;
            case 2:
                fragment = CompareItemMaps.newInstance(ResourceReference.types[position]);
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
