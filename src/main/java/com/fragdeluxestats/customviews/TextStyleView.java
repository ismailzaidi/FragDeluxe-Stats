package com.fragdeluxestats.customviews;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.fragdeluxestats.R;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by Yusuf on 19/12/2015.
 */
public class TextStyleView extends TextView {
    private String textType ="normal";
    private Context context;

    public TextStyleView(Context context) {
        this(context, null, 0);
    }

    public TextStyleView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public TextStyleView(Context context, AttributeSet attr, int res) {
        super(context, attr, res);
        if(!isInEditMode()){
            if (attr != null) {
                TypedArray array = context.obtainStyledAttributes(attr, R.styleable.TextStyleView);
                textType = array.getString(R.styleable.TextStyleView_textType);
                array.recycle();
            }
            this.context = context;
            init();
        }
    }


    public void init() {
        Typeface typeface;
        if (textType.equals("bold")) {
            typeface = Typeface.create("sans-serif",Typeface.BOLD);
        } else {
            typeface = Typeface.create("sans-serif",Typeface.NORMAL);
        }
        this.setTypeface(typeface);

    }
}
