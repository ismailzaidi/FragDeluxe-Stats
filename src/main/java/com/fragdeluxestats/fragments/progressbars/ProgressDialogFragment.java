package com.fragdeluxestats.fragments.progressbars;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fragdeluxestats.R;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.HTTPHandlers.CompareAsyncTask;
import com.fragdeluxestats.model.HTTPHandlers.DataAsyncTask;
import com.fragdeluxestats.model.ResourceReference;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class ProgressDialogFragment implements DataAsyncTask.UserProgressBar, CompareAsyncTask.CompareProgressBar {
    private MaterialDialog dialog;
    private Context context;
    private boolean menuBool = false;
    private boolean isGuest = false;
    public ProgressDialogFragment(Context context){
        this.context=context;
        initDialog();
    }
    public ProgressDialogFragment(Context context, boolean isGuest){
        this.context=context;
        this.isGuest = isGuest;
        initDialog();
    }
    private void initDialog(){
        dialog = getMaterialDialog().build();
        dialog.setCancelable(false);
        dialog.show();
    }
    public void setMainMenuBool(){
        menuBool = true;
    }
    @NonNull
    private MaterialDialog.Builder getMaterialDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("Loading Players...");
        builder.content("Please Wait. . .");
        builder.autoDismiss(false);
        if(isGuest){
            builder.progress(true, 0);
        }else{
            builder.progress(false, 100);
        }
        builder.widgetColor(ContextCompat.getColor(context, R.color.profilecolor));
        builder.backgroundColor(ContextCompat.getColor(context, R.color.secondryColor));
        builder.titleColor(ContextCompat.getColor(context, android.R.color.white));
        builder.contentColor(ContextCompat.getColor(context, android.R.color.white));
        return builder;
    }
    @Override
    public void updateProgressBar(int progress) {
        dialog.setProgress(progress);
    }
    public void dismiss(){
        dialog.dismiss();
    }
}
