package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class Statistics implements Parcelable{

    private int stateImage;
    private String statName;
    private String statValue;

    public Statistics(int stateImage, String statName, String statValue) {
        this.stateImage = stateImage;
        this.statName = statName;
        this.statValue = statValue;
    }
    public Statistics(String statName, String statValue) {
        this.statName = statName;
        this.statValue = statValue;
    }
    public int getStateImage() {
        return stateImage;
    }
    public void setStateImage(int stateImage) {
        this.stateImage = stateImage;
    }
    public String getStatName() {
        return statName;
    }
    public void setStatName(String statName) {
        this.statName = statName;
    }
    public String getStatValue() {
        return statValue;
    }
    public void setStatValue(String statValue) {
        this.statValue = statValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stateImage);
        dest.writeString(this.statName);
        dest.writeString(this.statValue);
    }

    private Statistics(Parcel in) {
        this.stateImage = in.readInt();
        this.statName = in.readString();
        this.statValue = in.readString();
    }

    public static final Creator<Statistics> CREATOR = new Creator<Statistics>() {
        public Statistics createFromParcel(Parcel source) {
            return new Statistics(source);
        }

        public Statistics[] newArray(int size) {
            return new Statistics[size];
        }
    };
}
