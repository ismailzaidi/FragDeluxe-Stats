package com.fragdeluxestats.model;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.RandomStats;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class DataModel {
    /**
     * About Server Members
     *
     * https://steamcommunity.com/groups/fragdeluxecommunity/memberslistxml?xml=1
     */

    /**
     * Dummy Data
     */

    public List<GameServer> getGameServer() {
        String serverName = "FragDeluxe Competitive Server #1";
        String serverIP = "127.0.0.1";
        String serverUrl = "play.fragdeluxe.com";
        String mapName = "De_dust2";
        int mapImage = R.drawable.de_dust2;
        long serverTime = 232131;
        int onlinePlayers = 20;
        int maximumPlayers = 26;
        List<Player> listOfPlayers = new ArrayList<Player>();
        /**
         * Player Section
         */
        String name = "JavaFreak #twitch.tv/ismailzd";
        String uniqueid = "Steam_0:1:1231321";
        String avatar = "";
        long connected = 2343242;
        int kills = 23;
        int deaths = 2;
        String team = "CT";
        int skillChange = 123;
        int skill = 202;
        String countryCode = "gb";
        String countryName = "United Kingdom";
        Player player1 = new Player(name, uniqueid, avatar, connected, kills, deaths, team, skillChange, skill, countryCode, countryName);
        listOfPlayers.add(player1);
        listOfPlayers.add(player1);
        listOfPlayers.add(player1);
        listOfPlayers.add(player1);
        listOfPlayers.add(player1);
        List<GameServer> listOfServers = new ArrayList<GameServer>();
        GameServer gameServer = new GameServer(serverName, serverIP, serverUrl, mapName, mapImage, serverTime, onlinePlayers, maximumPlayers, listOfPlayers);
        listOfServers.add(gameServer);
        listOfServers.add(gameServer);
        listOfServers.add(gameServer);
        listOfServers.add(gameServer);
        return listOfServers;
    }

    public List<Weapon> getWeaponList() {

        List<Weapon> listOfWeapons = new ArrayList<Weapon>();
        int weaponImage = R.drawable.awp_white;
        String weaponName = "AWP";
        double weaponModifier = 2.9;
        int weaponKills = 2000;
        int weaponDeaths = 20;
        int weaponHeadshot = 30;
        int weaponShots = 345;
        int weaponHits = 200;
        int weaponDamage = 2444;
        Weapon weapon1 = new Weapon(weaponImage, weaponName, weaponModifier, weaponKills, weaponDeaths, weaponHeadshot, weaponShots, weaponHits, weaponDamage);
        listOfWeapons.add(weapon1);
        weapon1.setWeaponImage(R.drawable.p2000_white);
        weapon1.setWeaponName("P2000");
        weapon1 = new Weapon(weaponImage, weaponName, weaponModifier, weaponKills, weaponDeaths, weaponHeadshot, weaponShots, weaponHits, weaponDamage);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);
        listOfWeapons.add(weapon1);

        return listOfWeapons;
    }

    public List<GameMap> getMapList() {
        List<GameMap> listOfMaps = new ArrayList<GameMap>();
        int mapImage = R.drawable.de_overpass;
        String mapName = "De_Overpass";
        long mapTime = 29l;
        int mapKills = 23;
        int mapDeaths = 2;
        int mapHeadShot = 20;
        GameMap gameMap = new GameMap(mapImage, mapName, mapTime, mapKills, mapDeaths, mapHeadShot);
        listOfMaps.add(gameMap);
        gameMap.setMapImage(R.drawable.de_dust2);
        gameMap.setMapName("De_dust2");
        gameMap = new GameMap(mapImage, mapName, mapTime, mapKills, mapDeaths, mapHeadShot);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        listOfMaps.add(gameMap);
        return listOfMaps;
    }

//    public List<Player> getRankedPlayers() {
////        String name = "JavaFreak #OverWatchPolice";
////        String uniqueid = "steam_0:1:23423423";
////        String avatar = "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/avatars/11/11c82457c292cc278ab84c6e912d9668491f7d3e_full.jpg";
////        int online = 1;
////        int rank = 1;
////        List<Player> listOfPlayers = new ArrayList<Player>();
////        Player player = new Player(name, uniqueid, avatar, online, 2);
////        listOfPlayers.add(player);
////        name = "Stabilis";
////        uniqueid = "steam_0:1:23423423";
////        avatar = "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/avatars/4a/4aa0f3121a70c0960e97f07e33482f418db91db7_full.jpg";
////        online = 1;
////        rank = 1;
////        player = new Player(name, uniqueid, avatar, online, rank);
////        name = "Norsken";
////        uniqueid = "steam_0:1:23423423";
////        avatar = "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/avatars/4a/4aa0f3121a70c0960e97f07e33482f418db91db7_full.jpg";
////        online = 1;
////        rank = 3;
////        Player player1 = new Player(name, uniqueid, avatar, online, rank);
////        listOfPlayers.add(player);
////        listOfPlayers.add(player1);
////        Collections.sort(listOfPlayers);
//        return listOfPlayers;
//    }

    public List<Statistics> getStatsList() {
        List<Statistics> listOfStats = new ArrayList<Statistics>();
        int mapImage = R.drawable.time_icon;

        /**
         * Player Stats
         *
         *
         */
        Player player;

        /**Random Statistic
         */

        List<RandomStats> listOfRandomStats = null;
        for(RandomStats stats: listOfRandomStats){
            String statName = stats.getStateName();
            String statValue = String.valueOf(stats.getStatAchieved());
            listOfStats.add(new Statistics(statName,statValue));

        }
        return listOfStats;
    }

//    public List<Player> getComparePlayerList() {
//        List<Player> list = new ArrayList<Player>();
//        String name = "JavaFreak";
//        String uniqueid = "STEAM_0_1:13232423";
//        String avatar = "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/avatars/4a/4aa0f3121a70c0960e97f07e33482f418db91db7_full.jpg";
//        int online = 1;
//        int skill = 23565;
//        int kills = 232;
//        int deaths = 23254;
//        int hs = 323;
//        int suicides = 332;
//        int shots = 45433;
//        int hits = 233242;
//        long time = 432423l;
//        int rank = 194;
//        int assists = 232;
//        int assisted = 546;
//        int killstreak = 4;
//        int deathStreak = 8;
//        int rounds = 2343;
//        double survived = 3424;
//        int wins = 3423;
//        int losses = 231;
//        String countryCode = "gb";
//        String countryName = "United Kingdon";
//        String weaponCode = "ak47_icon";
//        String weaponName = "AK47";
//        int weaponKills = 2313;
//        List<GameMap> listOfMaps = getMapList();
//        List<Weapon> listOfWeapons = getWeaponList();
//        Player player = new Player(name, uniqueid, avatar, online, skill, kills, deaths, hs, suicides, shots, hits, time, rank, assists, assisted, killstreak, deathStreak, rounds, survived, wins, losses, countryCode, countryName, weaponCode, weaponName, weaponKills, listOfMaps, listOfWeapons);
//        list.add(player);
//        list.add(player);
//        return list;
//    }


}

/**
 * <parent> css: display:in-line-block;
 * <img> :display:block;
 * <text>
 * </parent>
 */
