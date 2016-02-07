package com.fragdeluxestats.fragments.progressbars;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fragdeluxestats.R;
import com.fragdeluxestats.model.HTTPHandlers.CompareAsyncTask;
import com.fragdeluxestats.model.HTTPHandlers.DataAsyncTask;
import com.fragdeluxestats.model.HTTPHandlers.LeaderBoardTask;
import com.fragdeluxestats.model.SharedPreferenceModel;

import java.text.NumberFormat;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class LeaderBoardProgressBar implements LeaderBoardTask.LeaderBoardProgressBar {
    private MaterialDialog dialog;
    private Context context;

    public LeaderBoardProgressBar(Context context) {
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        dialog = getMaterialDialog().build();
        dialog.setCancelable(false);
        dialog.show();
    }

    @NonNull
    private MaterialDialog.Builder getMaterialDialog() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("Loading Players...");
        builder.content("Please Wait. . .");
        builder.autoDismiss(false);
        SharedPreferenceModel sharedPreferenceModel = new SharedPreferenceModel(context);
        int maxValue = sharedPreferenceModel.loadSharedPrefencesMaxValue();
        builder.progress(false, maxValue, true);
        builder.progressNumberFormat("%d/%d");
        builder.progressPercentFormat(NumberFormat.getPercentInstance());
        builder.widgetColor(ContextCompat.getColor(context, R.color.profilecolor));
        builder.backgroundColor(ContextCompat.getColor(context, R.color.secondryColor));
        builder.titleColor(ContextCompat.getColor(context, android.R.color.white));
        builder.contentColor(ContextCompat.getColor(context, android.R.color.white));
        return builder;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void updateProgress(int value, String playerName) {
        dialog.setProgress(value);
        dialog.setContent(playerName);
    }
}
