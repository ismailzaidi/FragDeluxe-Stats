package com.fragdeluxestats.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class WeaponView extends CardView {

    private TextStyleView weaponName;
    private TextStyleView weaponKills;
    private TextStyleView weaponDeaths;
    private ImageView weaponImage;
    private TextStyleView weaponHeadShot;
    private TextStyleView weaponShots;
    private TextStyleView weaponHits;
    private TextStyleView weaponDamage;
    private TextStyleView weaponAccuracy;
    private Utility utility;
    public WeaponView(Context context) {
        this(context, null, 0);
    }
    public WeaponView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }
    public WeaponView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        utility = new Utility(context);
        setPreventCornerOverlap(false);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        weaponName = (TextStyleView) findViewById(R.id.weaponName);
        weaponKills = (TextStyleView) findViewById(R.id.weaponKills);
        weaponDeaths = (TextStyleView) findViewById(R.id.weaponDeaths);
        weaponImage = (ImageView) findViewById(R.id.weaponImage);
        weaponHeadShot = (TextStyleView) findViewById(R.id.weaponHeadshots);
        weaponShots = (TextStyleView) findViewById(R.id.weaponShots);
        weaponHits = (TextStyleView) findViewById(R.id.weaponHits);
        weaponDamage = (TextStyleView) findViewById(R.id.weaponDamage);
        weaponAccuracy = (TextStyleView) findViewById(R.id.weaponAccuracy);
    }

    public void setWeaponObject(Weapon weapon) {
        Utility utility = new Utility(getContext());
        weaponName.setText(weapon.getWeaponName());
        weaponKills.setText(utility.getFormatSorter(weapon.getWeaponKills()));
        weaponDeaths.setText(utility.getFormatSorter(weapon.getWeaponDeaths()));
        weaponHeadShot.setText(utility.getFormatSorter(weapon.getWeaponHeadshot()));
        weaponShots.setText(utility.getFormatSorter(weapon.getWeaponShots()));
        weaponHits.setText(utility.getFormatSorter(weapon.getWeaponHits()));
        weaponDamage.setText(utility.getFormatSorter(weapon.getWeaponDamage()));
        Log.v("WEAPON_INFO", "setWeaponObject Weapon Name: " + weapon.getWeaponName());
        int hits = weapon.getWeaponHits();
        int shots = weapon.getWeaponShots();
        ;
        double accuracy = 0;
        if (hits != 0 && shots != 0) {
            accuracy = 100 * weapon.getWeaponHits() / weapon.getWeaponShots();
        }
        weaponAccuracy.setText(utility.getFormatSorter(accuracy));
//        Picasso.with(getContext()).load(weapon.getWeaponImage()).into(weaponImage);
        Resources res = getResources();
        int weaponID = weapon.getWeaponImage();
        int width = 100;
        int height = 100;
        utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        weaponImage.setImageBitmap(bitmap);
    }
    public void setWeaponObjectCompare(Weapon weapon) {
        if (weapon != null) {
            weaponName.setText(weapon.getWeaponName());
            weaponKills.setText(utility.getFormatSorter(weapon.getWeaponKills()));
            weaponHeadShot.setText(utility.getFormatSorter(weapon.getWeaponHeadshot()));
            Log.v("WEAPON_INFO", "setWeaponObject Weapon Name: " + weapon.getWeaponName());
            int hits = weapon.getWeaponHits();
            int shots = weapon.getWeaponShots();
            double accuracy = 0;
            if (hits != 0 && shots != 0) {
                accuracy = 100 * weapon.getWeaponHits() / weapon.getWeaponShots();
            }
            weaponAccuracy.setText(utility.getFormatSorter(accuracy)+"%");
            Resources res = getResources();
            int weaponID = weapon.getWeaponImage();
            int width = 50;
            int height = 50;
            utility = new Utility(getContext());
            Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
            weaponImage.setImageBitmap(bitmap);
        }
    }
    public TextStyleView getWeaponName() {
        return weaponName;
    }

}
