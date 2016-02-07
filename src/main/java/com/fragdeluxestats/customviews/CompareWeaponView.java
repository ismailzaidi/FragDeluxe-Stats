package com.fragdeluxestats.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class CompareWeaponView extends LinearLayout {
    public CompareWeaponView(Context context) {
        this(context, null, 0);
    }
    public CompareWeaponView(Context context, AttributeSet attr) {
        this(context, null, 0);
    }
    public CompareWeaponView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setWeaponStats(ArrayList<Weapon> weaponList) {
//        adjustBackGroundColor();
    }



}
