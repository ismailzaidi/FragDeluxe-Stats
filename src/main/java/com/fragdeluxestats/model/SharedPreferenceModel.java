package com.fragdeluxestats.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Yusuf on 05/01/2016.
 */
public class SharedPreferenceModel {
    private static String STEAM_KEY = "SteamID.Preference";
    private static String MAX_VALUE_KEY = "com.fragdeluxestats.maxvalue";
    private static String RANK_KEY = "Rank.Preference";
    private static String TIME_KEY = "Time.Preference";
    private static String FORCE_REFRESH = "FOrce_Refresh.Preference";
    private Context context;
    public SharedPreferenceModel(Context context) {
        this.context = context;
    }
    /**
     * No Login
     */
    public void saveSharedPreferenceSteamID(String steamID) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(STEAM_KEY, steamID);
        editor.apply();
    }

    public String loadSharedPreferenceSteamID() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(STEAM_KEY, "0");
    }
    /**
     * No Login
     */
    public void saveSharedPreferenceRank(int steamID) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(RANK_KEY, steamID);
        editor.apply();
    }

    public int loadSharedPreferenceRank() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(RANK_KEY, 0);
    }
    /**
     * Remember Fragment upon Refresh
     */
    public void saveSharedPrefencesMaxValue(int maxValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(MAX_VALUE_KEY, maxValue);
        editor.apply();
    }
    public int loadSharedPrefencesMaxValue() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(MAX_VALUE_KEY, 0);
    }

}
