package com.fragdeluxestats.fragments.progressbars;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.HTTPHandlers.CompareAsyncTask;
import com.fragdeluxestats.model.ResourceReference;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class ProgressDialogCompareFragment extends DialogFragment implements CompareAsyncTask.CompareProgressBar {

    private TextStyleView profileOneTextView, profileTwoTextView, profileThreeTextView;
    private CircleImageView profileOneImageView, profileTwoImageView, profileThreeImageView;
    private LinearLayout layoutOne, layoutTwo, layoutThree;
    private CircularProgressBar progressBar;
    private ArrayList<Player> playerArrayList;

    public static ProgressDialogCompareFragment newInstance(ArrayList<Player> playerArrayList) {
        ProgressDialogCompareFragment fragment = new ProgressDialogCompareFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ResourceReference.COMPARE_PROGRESS_LIST, playerArrayList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        playerArrayList = (ArrayList<Player>) getArguments().getSerializable(ResourceReference.COMPARE_PROGRESS_LIST);
        View progressView = inflater.inflate(R.layout.progress_compare, container, false);
        progressBar = (CircularProgressBar) progressView.findViewById(R.id.progressbar);
        int size = playerArrayList.size();
        initWidgets(progressView);
        displayWidgets(size);
        return progressView;
    }

    private void displayWidgets(int size) {
        Player playerOne = playerArrayList.get(0);
        Player playerTwo = playerArrayList.get(1);
        Player playerThree = null;
        if(size==3){
            playerThree = playerArrayList.get(2);
        }
        Context context = getActivity().getApplicationContext();
        switch (size) {
            case 1:
                layoutOne.setVisibility(View.VISIBLE);
                Picasso.with(context).load(playerOne.getAvatar()).into(profileOneImageView);
                profileOneTextView.setText(playerOne.getName());

                break;
            case 2:
                layoutOne.setVisibility(View.VISIBLE);
                layoutTwo.setVisibility(View.VISIBLE);
                Picasso.with(context).load(playerOne.getAvatar()).into(profileOneImageView);
                profileOneTextView.setText(playerOne.getName());
                Picasso.with(context).load(playerTwo.getAvatar()).into(profileTwoImageView);
                profileTwoTextView.setText(playerTwo.getName());
                break;
            case 3:
                layoutOne.setVisibility(View.VISIBLE);
                layoutTwo.setVisibility(View.VISIBLE);
                layoutThree.setVisibility(View.VISIBLE);
                Picasso.with(context).load(playerOne.getAvatar()).into(profileOneImageView);
                profileOneTextView.setText(playerOne.getName());
                Picasso.with(context).load(playerTwo.getAvatar()).into(profileTwoImageView);
                profileTwoTextView.setText(playerTwo.getName());
                Picasso.with(context).load(playerThree.getAvatar()).into(profileThreeImageView);
                profileThreeTextView.setText(playerThree.getName());
                break;
        }

    }

    private void initWidgets(View layout) {
        layoutOne = (LinearLayout) layout.findViewById(R.id.layoutOne);
        layoutTwo = (LinearLayout) layout.findViewById(R.id.layoutTwo);
        layoutThree = (LinearLayout) layout.findViewById(R.id.layoutThree);
        profileOneTextView = (TextStyleView) layout.findViewById(R.id.profileOneTextView);
        profileTwoTextView = (TextStyleView) layout.findViewById(R.id.profileTwoTextView);
        profileThreeTextView = (TextStyleView) layout.findViewById(R.id.profileThreeTextView);
        profileOneImageView = (CircleImageView) layout.findViewById(R.id.profileOneImageView);
        profileTwoImageView = (CircleImageView) layout.findViewById(R.id.profileTwoImageView);
        profileThreeImageView = (CircleImageView) layout.findViewById(R.id.profileThreeImageView);
    }

    @Override
    public void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
        updateViews(progress);
    }

    public void updateViews(int value) {
        int size = playerArrayList.size();
        if (size == 2) {
            if (value <= 50) {
                changeViews(profileOneTextView, profileOneImageView);
            } else {
                changeViews(profileTwoTextView, profileTwoImageView);
            }
        }
        if (size == 3) {
            if (value <= 33.33) {
                changeViews(profileOneTextView, profileOneImageView);
            } else if (value > 33.33 && value < 67) {
                changeViews(profileTwoTextView, profileTwoImageView);
            } else {
                changeViews(profileThreeTextView, profileThreeImageView);
            }
        }
        if(value>=100){
            getDialog().dismiss();
        }
    }


    public void changeViews(TextStyleView textStyleView, View imageView) {
        textStyleView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        imageView.setAlpha(1);
        YoYo.with(Techniques.BounceInDown).duration(500).playOn(textStyleView);
        YoYo.with(Techniques.BounceInDown).duration(500).playOn(imageView);

    }
}
