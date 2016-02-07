package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class GameMap implements Parcelable, Serializable {
    private int mapImage;
    private String mapName;
    private long mapTime;
    private int mapKills;
    private int mapDeaths;
    private int mapHeadshots;


    private String mapImageUrl;

    public GameMap(int mapImage, String mapName, long mapTime, int mapKills, int mapDeaths, int mapHeadshots) {
        this.mapImage = mapImage;
        this.mapName = mapName;
        this.mapTime = mapTime;
        this.mapKills = mapKills;
        this.mapDeaths = mapDeaths;
        this.mapHeadshots = mapHeadshots;
    }

    public GameMap(String mapImageUrl, String mapName, long mapTime, int mapKills, int mapDeaths, int mapHeadshots) {
        this.mapImageUrl = mapImageUrl;
        this.mapName = mapName;
        this.mapTime = mapTime;
        this.mapKills = mapKills;
        this.mapDeaths = mapDeaths;
        this.mapHeadshots = mapHeadshots;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public long getMapTime() {
        return TimeUnit.SECONDS.toHours(mapTime);
    }

    public void setMapTime(long mapTime) {
        this.mapTime = mapTime;
    }

    public int getMapKills() {
        return mapKills;
    }

    public void setMapKills(int mapKills) {
        this.mapKills = mapKills;
    }

    public int getMapDeaths() {
        return mapDeaths;
    }

    public void setMapDeaths(int mapDeaths) {
        this.mapDeaths = mapDeaths;
    }

    public int getMapHeadshots() {
        return mapHeadshots;
    }

    public void setMapHeadshots(int mapHeadshots) {
        this.mapHeadshots = mapHeadshots;
    }

    public int getMapImage() {
        return mapImage;
    }

    public void setMapImage(int mapImage) {
        this.mapImage = mapImage;
    }

    public String getMapImageUrl() {
        return mapImageUrl;
    }

    public void setMapImageUrl(String mapImageUrl) {
        this.mapImageUrl = mapImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mapImage);
        dest.writeString(this.mapName);
        dest.writeLong(this.mapTime);
        dest.writeInt(this.mapKills);
        dest.writeInt(this.mapDeaths);
        dest.writeInt(this.mapHeadshots);
    }

    private GameMap(Parcel in) {
        this.mapImage = in.readInt();
        this.mapName = in.readString();
        this.mapTime = in.readLong();
        this.mapKills = in.readInt();
        this.mapDeaths = in.readInt();
        this.mapHeadshots = in.readInt();
    }

    public static final Creator<GameMap> CREATOR = new Creator<GameMap>() {
        public GameMap createFromParcel(Parcel source) {
            return new GameMap(source);
        }

        public GameMap[] newArray(int size) {
            return new GameMap[size];
        }
    };
}
