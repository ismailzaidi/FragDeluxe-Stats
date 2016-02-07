package com.fragdeluxestats.bean;

import java.util.List;

/**
 * Created by Yusuf on 14/12/2015.
 */
public class GameServer {
    String serverName;
    String serverIP;
    String serverUrl;
    String mapName ;
    int mapImage ;

    long serverTime  ;
    int onlinePlayers ;
    int  maximumPlayers ;
    List<Player> listOfPlayers;
    public GameServer(String serverName, String serverIP, String serverUrl, String mapName, int mapImage, long serverTime, int onlinePlayers, int maximumPlayers, List<Player> listOfPlayers) {
        this.serverName = serverName;
        this.serverIP = serverIP;
        this.serverUrl = serverUrl;
        this.mapName = mapName;
        this.mapImage = mapImage;
        this.serverTime = serverTime;
        this.onlinePlayers = onlinePlayers;
        this.maximumPlayers = maximumPlayers;
        this.listOfPlayers = listOfPlayers;
    }
    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public String getServerName() {
        return serverName.split("\\:\\:")[1];
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public int getMapImage() {
        return mapImage;
    }

    public void setMapImage(int mapImage) {
        this.mapImage = mapImage;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(int onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    @Override
    public String toString() {
        return "GameServer{" +
                "serverName='" + serverName + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", serverUrl='" + serverUrl + '\'' +
                ", mapName='" + mapName + '\'' +
                ", mapImage=" + mapImage +
                ", serverTime=" + serverTime +
                ", onlinePlayers=" + onlinePlayers +
                ", maximumPlayers=" + maximumPlayers +
                ", listOfPlayers=" + listOfPlayers +
                '}';
    }
}