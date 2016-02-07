package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yusuf on 14/12/2015.
 */
public class RandomStats implements Parcelable {
    private String stateCode;
    private String stateName;
    private int statAchieved;
    private int statBonus;
    public RandomStats(String stateCode, String stateName, int statAchieved, int statBonus) {
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.statAchieved = statAchieved;
        this.statBonus = statBonus;
    }
    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getStatAchieved() {
        return statAchieved;
    }

    public void setStatAchieved(int statAchieved) {
        this.statAchieved = statAchieved;
    }

    public int getStatBonus() {
        return statBonus;
    }

    public void setStatBonus(int statBonus) {
        this.statBonus = statBonus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stateCode);
        dest.writeString(this.stateName);
        dest.writeInt(this.statAchieved);
        dest.writeInt(this.statBonus);
    }

    private RandomStats(Parcel in) {
        this.stateCode = in.readString();
        this.stateName = in.readString();
        this.statAchieved = in.readInt();
        this.statBonus = in.readInt();
    }

    public static final Creator<RandomStats> CREATOR = new Creator<RandomStats>() {
        public RandomStats createFromParcel(Parcel source) {
            return new RandomStats(source);
        }

        public RandomStats[] newArray(int size) {
            return new RandomStats[size];
        }
    };
}
