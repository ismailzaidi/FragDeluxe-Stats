package com.fragdeluxestats.customviews;

/**
 * Created by Yusuf on 18/12/2015.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.fragdeluxestats.R;

public class ProfileImageView extends ImageView {

    private final int scaleFactor;
    private final boolean tintSet;
    private final int tintColour;
    private final float blurRadius;

    public ProfileImageView(Context context) {
        this(context, null, 0);
    }

    public ProfileImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {
            TypedArray styleArray = context.obtainStyledAttributes(attrs, R.styleable.ProfileImageView);
            tintColour = styleArray.getColor(R.styleable.ProfileImageView_tintColor, 0);
            blurRadius = styleArray.getFloat(R.styleable.ProfileImageView_blurRadius, 0.0f);
            scaleFactor = styleArray.getInteger(R.styleable.ProfileImageView_scaleFactor, 1);
            tintSet = tintColour != 0;
            styleArray.recycle();
        } else {
            tintSet = false;
            blurRadius = 0.0f;
            tintColour = 0;
            scaleFactor = 1;
        }
    }

    public void blurImage(Drawable drawable) {
        Bitmap bm = Bitmap.createBitmap(drawable.getIntrinsicWidth() / scaleFactor, drawable.getIntrinsicHeight() / scaleFactor, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / scaleFactor, drawable.getIntrinsicHeight() / scaleFactor);
        drawable.draw(canvas);
        RenderScript rs = RenderScript.create(getContext());
        final Allocation input = Allocation.createFromBitmap(rs, bm);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(blurRadius);
        script.setInput(input);
        script.forEach(output);
        Bitmap finalBmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        output.copyTo(finalBmp);
        setImageBitmap(finalBmp);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.v("Drawable", "Drawable: " + getDrawable());
        if (isInEditMode()) {
            Matrix matrix = new Matrix();
            float sx = (getMeasuredWidth() / (float) (getDrawable().getIntrinsicWidth()));
            matrix.setScale(sx, sx);
            matrix.postTranslate(0, (getMeasuredHeight() - (getDrawable().getIntrinsicHeight()) * sx) / 2);
            setImageMatrix(matrix);
        } else {
            Matrix matrix = new Matrix();
            float sx = (getMeasuredWidth() / (float) (getDrawable().getIntrinsicWidth() / scaleFactor));
            matrix.setScale(sx, sx);
            matrix.postTranslate(0, (getMeasuredHeight() - (getDrawable().getIntrinsicHeight() / scaleFactor) * sx) / 2);
            setImageMatrix(matrix);
            if (blurRadius >= 0.1f) {
                blurImage(getDrawable());
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (tintSet) {
            canvas.drawColor(tintColour);
        }
    }
}