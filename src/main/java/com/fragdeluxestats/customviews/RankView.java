package com.fragdeluxestats.customviews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.Utility;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.picasso.transformations.BlurTransformation;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class RankView extends LinearLayout implements View.OnClickListener{
    private TextStyleView playerCounter;
    private TextStyleView playerName;
    private TextStyleView profilePoints;
    private CircleImageView playerAvatar;
    private TextView compareCountryName;
    private ImageView compareCountryImage;
    private Utility utility;
    private LinearLayout playerStatisticButton;
    private LinearLayout background;
    private Player player;
    public RankView(Context context) {
        this(context, null, 0);
    }
    public RankView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public RankView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
        utility = new Utility(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playerCounter = (TextStyleView) findViewById(R.id.counterObject);
        playerName = (TextStyleView) findViewById(R.id.profileName);
        background = (LinearLayout) findViewById(R.id.background);
        profilePoints = (TextStyleView) findViewById(R.id.profilePoints);
        playerStatisticButton = (LinearLayout) findViewById(R.id.profileStatsButton);

        playerAvatar = (CircleImageView) findViewById(R.id.profileImage);
        compareCountryName = (TextView) findViewById(R.id.compareCountryName);
        compareCountryImage = (ImageView) findViewById(R.id.compareCountryImage);
    }


    /**
     * Player Search
     * @param player
     */
    public void setPlayerRankObject(Player player) {
        this.player = player;
        playerCounter.setText("#" + player.getRank());
        playerName.setText(player.getName());
        profilePoints.setText(utility.getFormatSorter(player.getSkill())+" Points");
        playerStatisticButton = (LinearLayout) findViewById(R.id.profileStatsButton);
        Log.v("RANK_PLAYER_URL","Player URL: "+ player.getAvatar());
        Picasso.with(getContext()).load(player.getAvatar()).into(playerAvatar);
        initValues(player);
    }
    private void initValues(Player player){
        int online = player.getOnline();
        if(online==1){
            playerAvatar.setBorderColor(ContextCompat.getColor(getContext(),R.color.greenbutton));
        }else{
            playerAvatar.setBorderColor(ContextCompat.getColor(getContext(),R.color.shadedcolordark));
        }
        Utility utility = new Utility(getContext());
        int image_resouce = utility.getFlagID(player.getCountryCode());
        Log.v("PLAYER_INFO", "Player Name: " + player.getName() +  " Code: " + player.getCountryCode()  +  "  Country Name: " + player.getCountryName());
        if(image_resouce!=0){
            Picasso.with(getContext()).load(image_resouce).into(compareCountryImage);
            compareCountryName.setText(player.getCountryName());
        }


    }
    @Override
    public void onClick(View view) {
        if(view.getId() == playerStatisticButton.getId()){
            if(player!=null){
                //TODO CALLBACK FUNCTION
//                FragmentManager fragmentManager = activity.getSupportFragmentManager();
//                FriendsFragment friendDialog = FriendsFragment.newInstance();
//                friendDialog.show(fragmentManager,"com.friendDialog");

            }

        }
    }
    public LinearLayout getPlayerStatisticButton() {
        return playerStatisticButton;
    }
    public LinearLayout getBackgroundView() {
        return background;
    }
}
