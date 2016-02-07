package com.fragdeluxestats.model;

import android.view.View;
import android.widget.LinearLayout;

import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.GlobalData;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.Statistics;

import java.util.ArrayList;

/**
 * Created by Yusuf on 23/12/2015.
 */
public class ModelInterfaces {
    public interface ItemClickListener {
        public void onClickListener(View view, int position, LinearLayout background);
    }
}
