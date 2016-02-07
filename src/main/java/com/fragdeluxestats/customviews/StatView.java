package com.fragdeluxestats.customviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.model.Utility;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class StatView extends CardView {
    private ImageView statImage;
    private TextView statName;
    private TextView statValue;
    private Utility utility;

    public StatView(Context context) {
        this(context, null, 0);
    }

    public StatView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public StatView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        statImage = (ImageView) findViewById(R.id.statImage);
        statName = (TextView) findViewById(R.id.statName);
        statValue = (TextView) findViewById(R.id.statValue);
    }

    public void setStatObject(Statistics stat) {
        utility = new Utility(getContext());
        statImage.setImageResource(stat.getStateImage());
        statName.setText(stat.getStatName());
        if (stat.getStatValue().contains(",")) {
            statValue.setText(stat.getStatValue());
        } else {
            if (stat.getStatName().equals("Time")) {
                statName.setText(stat.getStatName());
                double statValueNumerical = Double.parseDouble(stat.getStatValue());
                statValue.setText(utility.getFormatSorter(statValueNumerical) + "h");
            } else {
                statName.setText(stat.getStatName());
                double statValueNumerical = Double.parseDouble(stat.getStatValue());
                statValue.setText(utility.getFormatSorter(statValueNumerical));
            }
        }
    }
}
