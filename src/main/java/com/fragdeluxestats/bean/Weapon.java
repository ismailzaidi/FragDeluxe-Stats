package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Yusuf on 14/12/2015.
 */
public class Weapon implements Parcelable,Serializable{

    private String weaponCode;
    private String weaponName;
    private double weaponModifier;
    private int weaponKills;
    private int weaponDeaths;
    private int weaponHeadshot;
    private int weaponShots;
    private int weaponHits;
    private int weaponDamage;
    private int weaponImage;

    public Weapon(int weaponImage, String weaponName, double weaponModifier, int weaponKills, int weaponDeaths, int weaponHeadshot, int weaponShots, int weaponHits, int weaponDamage) {
        this.weaponImage = weaponImage;
        this.weaponName = weaponName;
        this.weaponModifier = weaponModifier;
        this.weaponKills = weaponKills;
        this.weaponDeaths = weaponDeaths;
        this.weaponHeadshot = weaponHeadshot;
        this.weaponShots = weaponShots;
        this.weaponHits = weaponHits;
        this.weaponDamage = weaponDamage;
    }

    public String getWeaponCode() {
        return weaponCode;
    }

    public void setWeaponCode(String weaponCode) {
        this.weaponCode = weaponCode;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public double getWeaponModifier() {
        return weaponModifier;
    }

    public void setWeaponModifier(double weaponModifier) {
        this.weaponModifier = weaponModifier;
    }

    public int getWeaponKills() {
        return weaponKills;
    }

    public void setWeaponKills(int weaponKills) {
        this.weaponKills = weaponKills;
    }

    public int getWeaponDeaths() {
        return weaponDeaths;
    }

    public void setWeaponDeaths(int weaponDeaths) {
        this.weaponDeaths = weaponDeaths;
    }

    public int getWeaponHeadshot() {
        return weaponHeadshot;
    }

    public void setWeaponHeadshot(int weaponHeadshot) {
        this.weaponHeadshot = weaponHeadshot;
    }

    public int getWeaponShots() {
        return weaponShots;
    }

    public void setWeaponShots(int weaponShots) {
        this.weaponShots = weaponShots;
    }

    public int getWeaponHits() {
        return weaponHits;
    }

    public void setWeaponHits(int weaponHits) {
        this.weaponHits = weaponHits;
    }

    public int getWeaponDamage() {
        return weaponDamage;
    }

    public void setWeaponDamage(int weaponDamage) {
        this.weaponDamage = weaponDamage;
    }
    public int getWeaponImage() {
        return weaponImage;
    }

    public void setWeaponImage(int weaponImage) {
        this.weaponImage = weaponImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weaponCode);
        dest.writeString(this.weaponName);
        dest.writeDouble(this.weaponModifier);
        dest.writeInt(this.weaponKills);
        dest.writeInt(this.weaponDeaths);
        dest.writeInt(this.weaponHeadshot);
        dest.writeInt(this.weaponShots);
        dest.writeInt(this.weaponHits);
        dest.writeInt(this.weaponDamage);
        dest.writeInt(this.weaponImage);
    }

    private Weapon(Parcel in) {
        this.weaponCode = in.readString();
        this.weaponName = in.readString();
        this.weaponModifier = in.readDouble();
        this.weaponKills = in.readInt();
        this.weaponDeaths = in.readInt();
        this.weaponHeadshot = in.readInt();
        this.weaponShots = in.readInt();
        this.weaponHits = in.readInt();
        this.weaponDamage = in.readInt();
        this.weaponImage = in.readInt();
    }
    public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
        public Weapon createFromParcel(Parcel source) {
            return new Weapon(source);
        }

        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };
    @Override
    public String toString() {
        return "Weapon{" +
                "weaponCode='" + weaponCode + '\'' +
                ", weaponName='" + weaponName + '\'' +
                ", weaponModifier=" + weaponModifier +
                ", weaponKills=" + weaponKills +
                ", weaponDeaths=" + weaponDeaths +
                ", weaponHeadshot=" + weaponHeadshot +
                ", weaponShots=" + weaponShots +
                ", weaponHits=" + weaponHits +
                ", weaponDamage=" + weaponDamage +
                ", weaponImage=" + weaponImage +
                '}';
    }
}
