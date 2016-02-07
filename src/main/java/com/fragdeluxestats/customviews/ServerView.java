package com.fragdeluxestats.customviews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class ServerView extends CardView {
    public static final String T = "TERRORIST";
    private TextView serverName;
    private TextView serverIP;
    //    private TextView serverUrl;
    private TextView mapName;
    private ImageView mapImage;
    private TextView onlinePlayers;
    private LinearLayout listOfPlayersLayout;
    private LinearLayout serverPlayersLayoutCT;


    private ScrollView scrollView;
    private ImageView arrowImageButton;
    private ScrollView scrollViewCT;


    private ImageView arrowImageButtonCT;

    public ServerView(Context context) {
        this(context, null, 0);
    }

    public ServerView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public ServerView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        serverName = (TextView) findViewById(R.id.serverName);
        serverIP = (TextView) findViewById(R.id.serverIP);
//        serverUrl = (TextView) findViewById(R.id.serverURL);
        mapName = (TextView) findViewById(R.id.serverMap);
        mapImage = (ImageView) findViewById(R.id.serverMapImage);
        serverName = (TextView) findViewById(R.id.serverName);
        onlinePlayers = (TextView) findViewById(R.id.serverPlayerStatus);
        listOfPlayersLayout = (LinearLayout) findViewById(R.id.serverPlayersLayout);
        serverPlayersLayoutCT = (LinearLayout) findViewById(R.id.serverPlayersLayoutCT);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        arrowImageButton = (ImageView) findViewById(R.id.scrollButton);
        scrollViewCT = (ScrollView) findViewById(R.id.scrollViewCT);
        arrowImageButtonCT = (ImageView) findViewById(R.id.scrollButtonCT);
    }

    public void setServerObject(GameServer gameServer) {
        Log.v("SERVER_TYPE_INFO", "Server Name (SetServerObject): " + gameServer.getServerName());
        serverName.setText(gameServer.getServerName());
        serverIP.setText(gameServer.getServerIP().trim());
//        serverUrl.setText(gameServer.getServerUrl());
        mapName.setText(gameServer.getMapName());
        int availablePlayers = gameServer.getOnlinePlayers();
        int maxPlayers = gameServer.getMaximumPlayers();
        String onlinePlayersStr = availablePlayers + "/" + maxPlayers;
        onlinePlayers.setText(onlinePlayersStr);
        List<Player> listOfPlayers = gameServer.getListOfPlayers();
        Resources res = getResources();
        int weaponID = gameServer.getMapImage();
        int width = 250;
        int height = 250;
        Utility utility = new Utility(getContext());
        Bitmap bitmap = utility.bitmapHandler(res, weaponID, width, height);
        mapImage.setImageBitmap(bitmap);
        if (listOfPlayers!=null) {
            int playerTerrorit = 1;
            int playerCounterTerrorit = 1;
            int playerList = listOfPlayers.size();
            int layoutChildrenSize = listOfPlayersLayout.getChildCount();
            if (layoutChildrenSize<=0) {
                for (Player item : listOfPlayers) {
                    String team = item.getTeam();
                    PlayerOnlineView playerView = (PlayerOnlineView) View.inflate(this.getContext(), R.layout.online_playeritem, null);
                    Log.v("TEAM_INFO", "Player: " + item.getName() + "  Team: " + team);
                    if (team.equals(T)) {
                        playerView.setPlayerObject(item, playerTerrorit);
                        listOfPlayersLayout.addView(playerView);
                        playerTerrorit++;
                    } else {
                        playerView.setPlayerObject(item, playerCounterTerrorit);
                        serverPlayersLayoutCT.addView(playerView);
                        playerCounterTerrorit++;
                    }
                }
            }
        }
    }

    public ImageView getArrowImageButtonCT() {
        return arrowImageButtonCT;
    }

    public ScrollView getScrollViewCT() {
        return scrollViewCT;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public ImageView getArrowImageButton() {
        return arrowImageButton;
    }

}
