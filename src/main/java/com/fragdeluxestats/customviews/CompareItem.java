package com.fragdeluxestats.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.activities.CompareActivity;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.ProfileLog;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class CompareItem extends CardView {
    private TextView compareName;
    private ImageView compareCountryImage;
    private ImageView compareWeaponImage;
    private TextStyleView compareCountryName;
    private CircleImageView compareProfileImage;
    private LinearLayout layoutManager;
    private int max_size;

    public CompareItem(Context context) {
        this(context, null, 0);
    }

    public CompareItem(Context context, AttributeSet attr) {
        this(context, null, 0);
    }

    public CompareItem(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        setPreventCornerOverlap(false);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        compareName = (TextView) findViewById(R.id.compareName);
        compareCountryImage = (ImageView) findViewById(R.id.compareCountryImage);
        compareWeaponImage = (ImageView) findViewById(R.id.compareFavoriteWeaponImage);
        compareProfileImage = (CircleImageView) findViewById(R.id.compareAvatarImage);
        compareCountryName = (TextStyleView) findViewById(R.id.compareCountryName);
        layoutManager = (LinearLayout) findViewById(R.id.layoutManager);
    }

    public void setPlayerObject(Player player) {
        compareName.setText(player.getName());
        int online = player.getOnline();
        if (online == 1) {
            compareProfileImage.setBorderColor(ContextCompat.getColor(getContext(), R.color.greenbutton));
        } else {
            compareProfileImage.setBorderColor(ContextCompat.getColor(getContext(), R.color.redbuttoncolor));
        }
//        compareFavoriteWeaponName.setText(player.getWeaponName());
        Utility utility = new Utility(getContext());
        int countryCode = utility.getFlagID(player.getCountryCode());
        compareCountryName.setText(player.getCountryName());
        if (countryCode != 0) {
            Picasso.with(getContext()).load(countryCode).into(compareCountryImage);
            Picasso.with(getContext()).load(player.getAvatar()).into(compareProfileImage);
        }
    }

    public void setStatistcsLayout(Player player) {
        CompareStatisticsView view = (CompareStatisticsView) LayoutInflater.from(getContext()).inflate(R.layout.compare_statistics_item, null, false);
        view.setStatisticsItem(player);
        layoutManager.addView(view);
    }

    public void setMapLayout(Player player) {
        List<GameMap> mapList = player.getListOfMaps();
        for (GameMap map : mapList) {
            MapView view = (MapView) LayoutInflater.from(getContext()).inflate(R.layout.map_compare_view, null, false);
            view.setMapObjectCompare(map);
            layoutManager.addView(view);
        }
        adjustBackGroundColor();
    }

    public void setWeaponLayout(Player player) {
        ArrayList<Weapon> weaponList = player.getListOfWeapons();
        for (int i = 0; i < weaponList.size(); i++) {
            WeaponView weaponView = (WeaponView) LayoutInflater.from(getContext()).inflate(R.layout.weapon_compare_view, null, false);
            ProfileLog.tick();
            weaponView.setWeaponObjectCompare(weaponList.get(i));
            if (i == 0) {
                Log.v("WEAPON_SIZE", "Weapon Padding");
                Log.v("WEAPON_SIZE", "Weapon Padding: " + weaponView.getPaddingTop());

            }
            layoutManager.addView(weaponView);
        }
        adjustBackGroundColor();
    }

    public void setWeaponDefaultLayout(Player player) {
        ArrayList<Weapon> weaponList = player.getListOfWeapons();
        for (int i = 0; i < weaponList.size(); i++) {
            DefaultItemCompare weaponView = (DefaultItemCompare) LayoutInflater.from(getContext()).inflate(R.layout.map_weapon_compare_default, null, false);
            ProfileLog.tick();
            weaponView.setWeaponObject(weaponList.get(i));
            layoutManager.addView(weaponView);
        }
        adjustBackGroundColor();
    }

    public void setMapDefaultLayout(Player player) {
        ArrayList<GameMap> mapList = player.getListOfMaps();
        for (int i = 0; i < mapList.size(); i++) {
            DefaultItemCompare mapView = (DefaultItemCompare) LayoutInflater.from(getContext()).inflate(R.layout.map_weapon_compare_default, null, false);
            ProfileLog.tick();
            mapView.setMapObject(mapList.get(i));
            layoutManager.addView(mapView);
        }
        adjustBackGroundColor();
    }

    public void adjustBackGroundColor() {
        LinearLayout mainLayout = layoutManager;
        int childCount = mainLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            boolean evenNumber = (i % 2 == 0);
            View child = mainLayout.getChildAt(i);
            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                if (evenNumber) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.sideMenuButton));
                } else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.buttoncolor));
                }
            }
        }


    }

}
