package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf on 24/12/2015.
 */
public class GlobalData implements Parcelable {

    private Player player;
    private ArrayList<Statistics> randomList;
    private ArrayList<GameServer> serverList;
    private ArrayList<Player> compareList;


    private ArrayList<Integer> aboutList;
    public GlobalData(Player player, ArrayList<Statistics> randomList, ArrayList<GameServer> serverList,ArrayList<Player> compareList, ArrayList<Integer> aboutList) {
        this.player = player;
        this.randomList = randomList;
        this.serverList = serverList;
        this.compareList = compareList;
        this.aboutList=aboutList;
    }
    public GlobalData(Player player, ArrayList<Statistics> randomList) {
        this.player = player;
        this.randomList = randomList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public ArrayList<Statistics> getRandomList() {
        return randomList;
    }

    public void setRandomList(ArrayList<Statistics> randomList) {
        this.randomList = randomList;
    }
    public ArrayList<GameServer> getServerList() {
        return serverList;
    }
    public void setServerList(ArrayList<GameServer> serverList) {
        this.serverList = serverList;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.player, 0);
        dest.writeSerializable(this.randomList);
        dest.writeSerializable(this.serverList);
    }

    private GlobalData(Parcel in) {
        this.player = in.readParcelable(Player.class.getClassLoader());
        this.randomList = (ArrayList<Statistics>) in.readSerializable();
        this.serverList = (ArrayList<GameServer>) in.readSerializable();
    }
    public ArrayList<Player> getCompareList() {
        return compareList;
    }

    public void setCompareList(ArrayList<Player> compareList) {
        this.compareList = compareList;
    }

    public static final Creator<GlobalData> CREATOR = new Creator<GlobalData>() {
        public GlobalData createFromParcel(Parcel source) {
            return new GlobalData(source);
        }

        public GlobalData[] newArray(int size) {
            return new GlobalData[size];
        }
    };
    public ArrayList<Integer> getAboutList() {
        return aboutList;
    }
    public void setAboutList(ArrayList<Integer> aboutList) {
        this.aboutList = aboutList;
    }
}
