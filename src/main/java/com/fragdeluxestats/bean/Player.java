package com.fragdeluxestats.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Player implements Comparable<Player>, Parcelable, Serializable {
    private int id;
    private String name;
    private String uniqueid;
    private String avatar;
    private double activity;


    private long connected;
    private String team;
    private int skillChange;
    private int online;
    private int skill;
    private int kills;
    private int deaths;
    private int hs;
    private int suicides;
    private int shots;
    private int hits;
    private long time;
    private int rank;
    private int assists;
    private int assisted;
    private int killstreak;
    private int deathStreak;
    private int rounds;
    private double survived;
    private int wins;
    private int losses;
    private double averageping;
    private String countryCode;
    private String countryName;
    private String weaponCode;
    private String weaponName;
    private int weaponKills;


    private int skillDifference;

    private String steam64ID;

    private boolean isPrivate;


    private String steamFullURL;
    private boolean isSelected;

    private ArrayList<GameMap> listOfMaps;


    private ArrayList<Weapon> listOfWeapons;

    /**
     * Server List Constructor
     *
     * @param name
     * @param uniqueid
     * @param avatar
     * @param connected
     * @param kills
     * @param deaths
     * @param team
     * @param skillChange
     * @param skill
     * @param countryCode
     * @param countryName
     */
    public Player(String name, String uniqueid, String avatar, long connected, int kills, int deaths, String team, int skillChange, int skill, String countryCode, String countryName) {
        this.name = name;
        this.uniqueid = uniqueid;
        this.avatar = avatar;
        this.connected = connected;
        this.kills = kills;
        this.deaths = deaths;
        this.team = team;
        this.skillChange = skillChange;
        this.skill = skill;
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    /**
     * Used for Ranks
     */
    public Player(String name, String uniqueid, String avatar, int online, int rank, int skill, String countryName, String countryCode) {
        this.name = name;
        this.uniqueid = uniqueid;
        this.avatar = avatar;
        this.online = online;
        this.rank = rank;
        this.skill = skill;
        this.countryName = countryName;
        this.countryCode = countryCode;
    }

    /**
     * Compare Feature
     *
     * @param name
     * @param uniqueid
     * @param avatar
     * @param online
     * @param skill
     * @param kills
     * @param deaths
     * @param hs
     * @param suicides
     * @param shots
     * @param hits
     * @param time
     * @param rank
     * @param assists
     * @param assisted
     * @param killstreak
     * @param deathStreak
     * @param rounds
     * @param survived
     * @param wins
     * @param losses
     * @param countryCode
     * @param countryName
     * @param weaponCode
     * @param weaponName
     * @param weaponKills
     */
    public Player(String name, String uniqueid, String avatar, int online, int skill, int kills, int deaths, int hs, int suicides, int shots, int hits, long time, int rank, int assists, int assisted, int killstreak, int deathStreak, int rounds, double survived, int wins, int losses, String countryCode, String countryName, String weaponCode, String weaponName, int weaponKills, ArrayList<GameMap> listOfMaps, ArrayList<Weapon> listOfWeapons) {
        this.name = name;
        this.uniqueid = uniqueid;
        this.avatar = avatar;
        this.online = online;
        this.skill = skill;
        this.kills = kills;
        this.deaths = deaths;
        this.hs = hs;
        this.suicides = suicides;
        this.shots = shots;
        this.hits = hits;
        this.time = time;
        this.rank = rank;
        this.assists = assists;
        this.assisted = assisted;
        this.killstreak = killstreak;
        this.deathStreak = deathStreak;
        this.rounds = rounds;
        this.survived = survived;
        this.wins = wins;
        this.losses = losses;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.weaponCode = weaponCode;
        this.weaponName = weaponName;
        this.weaponKills = weaponKills;
        this.listOfMaps = listOfMaps;
        this.listOfWeapons = listOfWeapons;
    }


    /**
     * Used for general summeries
     */

    public Player(String name, String uniqueid, String avatar, int online, int skill, int kills, int deaths, int hs, int suicides, int shots, int hits, long time, int rank, int assists, int assisted, int killstreak, int deathStreak, int rounds, double survived, int wins, int losses, String countryCode, String countryName, String weaponCode, String weaponName, int weaponKills) {
        this.name = name;
        this.uniqueid = uniqueid;
        this.avatar = avatar;
        this.online = online;
        this.skill = skill;
        this.kills = kills;
        this.deaths = deaths;
        this.hs = hs;
        this.suicides = suicides;
        this.shots = shots;
        this.hits = hits;
        this.time = time;
        this.rank = rank;
        this.assists = assists;
        this.assisted = assisted;
        this.killstreak = killstreak;
        this.deathStreak = deathStreak;
        this.rounds = rounds;
        this.survived = survived;
        this.wins = wins;
        this.losses = losses;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.weaponCode = weaponCode;
        this.weaponName = weaponName;
        this.weaponKills = weaponKills;
    }

    /**
     * User Login
     *
     * @return
     */
    public Player(String uniqueid, String steam64ID) {
        this.uniqueid = uniqueid;
        this.steam64ID = steam64ID;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getActivity() {
        return activity;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getKills() {
        return ((Integer) kills == null) ? 0 : kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return ((Integer) deaths == null) ? 0 : deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getHs() {
        return hs;
    }

    public void setHs(int hs) {
        this.hs = hs;
    }

    public int getSuicides() {
        return suicides;
    }

    public void setSuicides(int suicides) {
        this.suicides = suicides;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public long getTime() {
        return TimeUnit.SECONDS.toHours(time);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getAssisted() {
        return assisted;
    }

    public void setAssisted(int assisted) {
        this.assisted = assisted;
    }

    public int getKillstreak() {
        return killstreak;
    }

    public void setKillstreak(int killstreak) {
        this.killstreak = killstreak;
    }

    public int getDeathStreak() {
        return deathStreak;
    }

    public void setDeathStreak(int deathStreak) {
        this.deathStreak = deathStreak;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public double getSurvived() {
        return survived;
    }

    public void setSurvived(double survived) {
        this.survived = survived;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getAverageping() {
        return averageping;
    }

    public void setAverageping(double averageping) {
        this.averageping = averageping;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public int getWeaponKills() {
        return weaponKills;
    }

    public void setWeaponKills(int weaponKills) {
        this.weaponKills = weaponKills;
    }

    public long getConnected() {
        return connected;
    }

    public void setConnected(long connected) {
        this.connected = connected;
    }

    public int getSkillChange() {
        return skillChange;
    }

    public void setSkillChange(int skillChange) {
        this.skillChange = skillChange;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ArrayList<GameMap> getListOfMaps() {
        return listOfMaps;
    }

    public void setListOfMaps(ArrayList<GameMap> listOfMaps) {
        this.listOfMaps = listOfMaps;
    }

    public ArrayList<Weapon> getListOfWeapons() {
        return listOfWeapons;
    }

    public void setListOfWeapons(ArrayList<Weapon> listOfWeapons) {
        this.listOfWeapons = listOfWeapons;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int compareTo(Player player) {
        Integer rank = this.rank;
        return rank.compareTo(player.getRank());
    }

    public int getSkillDifference() {
        return skillDifference;
    }

    public void setSkillDifference(int skillDifference) {
        this.skillDifference = skillDifference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.uniqueid);
        dest.writeString(this.avatar);
        dest.writeDouble(this.activity);
        dest.writeLong(this.connected);
        dest.writeString(this.team);
        dest.writeInt(this.skillChange);
        dest.writeInt(this.online);
        dest.writeInt(this.skill);
        dest.writeInt(this.kills);
        dest.writeInt(this.deaths);
        dest.writeInt(this.hs);
        dest.writeInt(this.suicides);
        dest.writeInt(this.shots);
        dest.writeInt(this.hits);
        dest.writeLong(this.time);
        dest.writeInt(this.rank);
        dest.writeInt(this.assists);
        dest.writeInt(this.assisted);
        dest.writeInt(this.killstreak);
        dest.writeInt(this.deathStreak);
        dest.writeInt(this.rounds);
        dest.writeDouble(this.survived);
        dest.writeInt(this.wins);
        dest.writeInt(this.losses);
        dest.writeDouble(this.averageping);
        dest.writeString(this.countryCode);
        dest.writeString(this.countryName);
        dest.writeString(this.weaponCode);
        dest.writeString(this.weaponName);
        dest.writeInt(this.weaponKills);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.listOfMaps);
        dest.writeSerializable(this.listOfWeapons);
    }

    public String getSteamFullURL() {
        return steamFullURL;
    }

    public void setSteamFullURL(String steamFullURL) {
        this.steamFullURL = steamFullURL;
    }

    private Player(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.uniqueid = in.readString();
        this.avatar = in.readString();
        this.activity = in.readDouble();
        this.connected = in.readLong();
        this.team = in.readString();
        this.skillChange = in.readInt();
        this.online = in.readInt();
        this.skill = in.readInt();
        this.kills = in.readInt();
        this.deaths = in.readInt();
        this.hs = in.readInt();
        this.suicides = in.readInt();
        this.shots = in.readInt();
        this.hits = in.readInt();
        this.time = in.readLong();
        this.rank = in.readInt();
        this.assists = in.readInt();
        this.assisted = in.readInt();
        this.killstreak = in.readInt();
        this.deathStreak = in.readInt();
        this.rounds = in.readInt();
        this.survived = in.readDouble();
        this.wins = in.readInt();
        this.losses = in.readInt();
        this.averageping = in.readDouble();
        this.countryCode = in.readString();
        this.countryName = in.readString();
        this.weaponCode = in.readString();
        this.weaponName = in.readString();
        this.weaponKills = in.readInt();
        this.isSelected = in.readByte() != 0;
        this.listOfMaps = (ArrayList<GameMap>) in.readSerializable();
        this.listOfWeapons = (ArrayList<Weapon>) in.readSerializable();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getSteam64ID() {
        return steam64ID;
    }

    public void setSteam64ID(String steam64ID) {
        this.steam64ID = steam64ID;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uniqueid='" + uniqueid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", activity=" + activity +
                ", connected=" + connected +
                ", team='" + team + '\'' +
                ", skillChange=" + skillChange +
                ", online=" + online +
                ", skill=" + skill +
                ", kills=" + kills +
                ", deaths=" + deaths +
                ", hs=" + hs +
                ", suicides=" + suicides +
                ", shots=" + shots +
                ", hits=" + hits +
                ", time=" + time +
                ", rank=" + rank +
                ", assists=" + assists +
                ", assisted=" + assisted +
                ", killstreak=" + killstreak +
                ", deathStreak=" + deathStreak +
                ", rounds=" + rounds +
                ", survived=" + survived +
                ", wins=" + wins +
                ", losses=" + losses +
                ", averageping=" + averageping +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", weaponCode='" + weaponCode + '\'' +
                ", weaponName='" + weaponName + '\'' +
                ", weaponKills=" + weaponKills +
                ", skillDifference=" + skillDifference +
                ", steam64ID='" + steam64ID + '\'' +
                ", isPrivate=" + isPrivate +
                ", steamFullURL='" + steamFullURL + '\'' +
                ", isSelected=" + isSelected +
                ", listOfMaps=" + listOfMaps +
                ", listOfWeapons=" + listOfWeapons +
                '}';
    }
}
