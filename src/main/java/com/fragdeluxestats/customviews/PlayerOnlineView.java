package com.fragdeluxestats.customviews;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.Player;

import org.w3c.dom.Text;


/**
 * Created by Yusuf on 18/12/2015.
 */
public class PlayerOnlineView extends LinearLayout {
    private TextView playerCounter;
    private TextView playerName;

    public PlayerOnlineView(Context context) {
        this(context, null, 0);
    }

    public PlayerOnlineView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PlayerOnlineView(Context context, AttributeSet attr, int id) {
        super(context, attr, id);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playerCounter = (TextView) findViewById(R.id.playerCounter);
        playerName = (TextView) findViewById(R.id.playerName);
    }

    public void setPlayerObject(Player player, int counter) {
        playerName.setText(player.getName());
        playerCounter.setText(String.valueOf(counter));
    }

    public TextView generateCounterView(String item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this.getContext());
        textView.setLayoutParams(params);
        textView.setText(item);
        return textView;
    }

    public TextView generateNameView(String item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this.getContext());
        textView.setLayoutParams(params);
        textView.setText(item);
        return textView;
    }


}
