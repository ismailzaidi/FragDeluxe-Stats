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
import com.fragdeluxestats.fragments.AboutItemFragment;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class AboutViewPager extends FragmentPagerAdapter {
    private Fragment fragment;
    private Context context;
    private int[] imageResID = {R.drawable.stats_dark, R.drawable.glock_dark, R.drawable.map_dark};
    public AboutViewPager(FragmentManager fm, Context context) {
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
                fragment = AboutItemFragment.newInstance(0);
                break;
            case 1:
                fragment = AboutItemFragment.newInstance(1);
                break;
            case 2:
                fragment = AboutItemFragment.newInstance(2);
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
