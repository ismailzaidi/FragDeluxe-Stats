package com.fragdeluxestats.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.Utility;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class DefaultItemCompare extends CardView {

    private TextStyleView labelName;
    private ImageView labelImage;
    private Utility utility;
    public DefaultItemCompare(Context context) {
        this(context, null, 0);
    }
    public DefaultItemCompare(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }
    public DefaultItemCompare(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        utility = new Utility(context);
        setPreventCornerOverlap(false);
        setCardBackgroundColor(ContextCompat.getColor(context,R.color.secondryColor));
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        labelName = (TextStyleView) findViewById(R.id.labelName);
        labelImage = (ImageView) findViewById(R.id.labelImage);
    }
    public void setWeaponObject(Weapon weapon) {
        labelName.setText(weapon.getWeaponName());
        Log.v("WEAPON_INFO", "setWeaponObject Weapon Name: " + weapon.getWeaponName());
        Resources res = getResources();
        int weaponID = weapon.getWeaponImage();
        int width = 100;
        int height = 100;
        utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        labelImage.setImageBitmap(bitmap);
    }
    public void setMapObject(GameMap map) {
        labelName.setText(map.getMapName());
        Log.v("WEAPON_INFO", "setMapObject Map Name: " + map.getMapName());
        Resources res = getResources();
        int weaponID = map.getMapImage();
        int width = 100;
        int height = 100;
        utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        labelImage.setImageBitmap(bitmap);
    }

}
