package com.fragdeluxestats.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class MapView extends CardView {
    private ImageView mapImage;
    private TextStyleView mapName;
    private TextStyleView mapTime;
    private TextStyleView mapKills;
    private TextStyleView mapDeaths;
    private TextStyleView mapHeadShot;
    private Utility utility;
    public MapView(Context context) {
        this(context, null, 0);
    }

    public MapView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MapView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        setPreventCornerOverlap(false);
        utility = new Utility(context);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mapImage = (ImageView) findViewById(R.id.mapImage);
        mapName = (TextStyleView) findViewById(R.id.mapName);
        mapTime = (TextStyleView) findViewById(R.id.mapTime);
        mapKills = (TextStyleView) findViewById(R.id.mapKills);
        mapDeaths = (TextStyleView) findViewById(R.id.mapDeaths);
        mapHeadShot = (TextStyleView) findViewById(R.id.mapHeadshot);
    }
    public void setMapObject(GameMap map) {
        mapName.setText(map.getMapName());
        mapTime.setText(utility.getFormatSorter(map.getMapTime())+"h");
        mapKills.setText(utility.getFormatSorter(map.getMapKills()));
        mapDeaths.setText(utility.getFormatSorter(map.getMapDeaths()));
        mapHeadShot.setText(utility.getFormatSorter(map.getMapHeadshots()));
        Resources res = getResources();
        int weaponID = map.getMapImage();
        int width = 250;
        int height = 250;
        Utility utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        mapImage.setImageBitmap(bitmap);
    }
    public void setMapObjectCompare(GameMap map) {
        mapName.setText(map.getMapName());
        mapTime.setText(utility.getFormatSorter(map.getMapTime())+"h");
        mapKills.setText(utility.getFormatSorter(map.getMapKills()));
        mapDeaths.setText(utility.getFormatSorter(map.getMapDeaths()));
        mapHeadShot.setText(utility.getFormatSorter(map.getMapHeadshots()));
        Resources res = getResources();
        int weaponID = map.getMapImage();
        int width = 50;
        int height = 50;
        Utility utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        mapImage.setImageBitmap(bitmap);
    }
}
