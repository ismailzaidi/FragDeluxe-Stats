package com.fragdeluxestats.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.adapters.CompareRecyclerAdapter;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.customviews.TextStyleView;
import com.fragdeluxestats.model.DataModel;
import com.fragdeluxestats.model.ResourceReference;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class AboutItemFragment extends Fragment implements View.OnClickListener{
    private int[] about_res = {R.layout.about_info, R.layout.about_community, R.layout.about_credit};
    private AboutItemFragment.FetchAboutStatsCallBack callBack;


    public interface FetchAboutStatsCallBack {
        ArrayList<Integer> getAboutStatsList();
    }
    public static AboutItemFragment newInstance(int position) {
        AboutItemFragment fragment = new AboutItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ResourceReference.COMPARE_TYPE, position);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int about_position = getArguments().getInt(ResourceReference.COMPARE_TYPE);
        View compareView = inflater.inflate(about_res[about_position], container, false);
        switch (about_position) {
            case 0:
                initInfo(compareView);
                break;
            case 1:
                initCommunity(compareView);
                break;
            case 2:
                initCredit(compareView);
                break;
        }
        return compareView;
    }
    private void initInfo(View aboutInfoView) {
        TextStyleView about1 = (TextStyleView) aboutInfoView.findViewById(R.id.normalText1);
        String htmlText1 = about1.getText().toString();
        about1.setText(Html.fromHtml(htmlText1));
        TextStyleView about2 = (TextStyleView) aboutInfoView.findViewById(R.id.normalText2);
        String htmlText2 = about2.getText().toString();
        about2.setText(Html.fromHtml(htmlText2));
    }

    private void initCommunity(View aboutCommunityView) {
        TextStyleView currentSteamMembers = (TextStyleView) aboutCommunityView.findViewById(R.id.currentSteamMembers);
        TextStyleView currentOnlineMembers = (TextStyleView) aboutCommunityView.findViewById(R.id.currentOnlineMembers);
        TextStyleView currentOnlineAllMembers = (TextStyleView) aboutCommunityView.findViewById(R.id.currentOnlineAllMembers);
        TextStyleView possibleSlotsMembers = (TextStyleView) aboutCommunityView.findViewById(R.id.possibleSlotsMembers);
        ArrayList<Integer> listOfStats = callBack.getAboutStatsList();
        currentSteamMembers.setText(String.valueOf(listOfStats.get(0)));
        currentOnlineMembers.setText(String.valueOf(listOfStats.get(1)));
        currentOnlineAllMembers.setText(String.valueOf(listOfStats.get(2)));
        possibleSlotsMembers.setText(String.valueOf(listOfStats.get(3)));
    }
    private void initCredit(View aboutCreditView) {
        TextStyleView credit1 = (TextStyleView) aboutCreditView.findViewById(R.id.textCredit1);
        String textCredit1 = credit1.getText().toString();
        credit1.setText(Html.fromHtml(textCredit1));
        credit1.setMovementMethod(LinkMovementMethod.getInstance());
        TextStyleView credit2 = (TextStyleView) aboutCreditView.findViewById(R.id.textCredit2);
        String textCredit2 = credit2.getText().toString();
        credit2.setText(Html.fromHtml(textCredit2));
        credit2.setMovementMethod(LinkMovementMethod.getInstance());
        TextStyleView siteCredit = (TextStyleView) aboutCreditView.findViewById(R.id.siteButton);
        ImageView facebookImage = (ImageView) aboutCreditView.findViewById(R.id.faceBookButton);
        ImageView twitterImage = (ImageView) aboutCreditView.findViewById(R.id.twitterButton);
        ImageView youtubeImage = (ImageView) aboutCreditView.findViewById(R.id.youtubeButton);
        siteCredit.setOnClickListener(this);
        facebookImage.setOnClickListener(this);
        twitterImage.setOnClickListener(this);
        youtubeImage.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.siteButton){
            String siteURL = "https://www.fragdeluxe.com/community/";
            startSocialIntent(siteURL);
        }
        if(v.getId()==R.id.faceBookButton){
            String facebookURL = "https://www.facebook.com/fragdeluxe";
            startSocialIntent(facebookURL);
        }
        if(v.getId()==R.id.twitterButton){
            String twitterURL = "https://twitter.com/fragdeluxe";
            startSocialIntent(twitterURL);

        }
        if(v.getId()==R.id.youtubeButton){
            String youtubeURL = "https://www.youtube.com/channel/UCccXpFcB1ba6QBIBdye6DNQ";
            startSocialIntent(youtubeURL);

        }
    }

    private void startSocialIntent(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void initCredit() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (AboutItemFragment.FetchAboutStatsCallBack) context;
        Log.v("ABOUT_VIEW", "Attached Called:  Arr Size: " + callBack.getAboutStatsList().size());
    }
}
