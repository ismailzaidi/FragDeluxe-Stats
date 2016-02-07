package com.fragdeluxestats.model;

import android.content.Context;
import android.util.Log;

import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.RandomStats;
import com.fragdeluxestats.bean.Weapon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Yusuf on 15/12/2015.
 */
public class XMLHandler {

    public static final String JSON_HANDLER = "JSONHandler_TAG";
    private Context context;

    public XMLHandler(Context context) {
        this.context = context;
    }

    public Player getPlayerInfo(String jsonString) {
        Player player;

        String name = "Unknown";
        String uniqueid = "Unknown";
        String avatar = "Unknown";
        int online = 0;
        int skill = 0;
        int kills = 0;
        int deaths = 0;
        int hs = 0;
        int rank = 0;
        int suicides = 0;
        int shots = 0;
        int hits = 0;
        long time = 0;
        int assists = 0;
        int assisted = 0;
        int killstreak = 0;
        int deathStreak = 0;
        int rounds = 0;
        double survived = 0.0;
        int wins = 0;
        int losses = 0;
        String cc = "Unknown";
        String cn = "Unknown";
        /**
         * Weapon Favorite
         */
        String weaponCode = "DEFAULT";
        String weaponName = "";
        int weaponKills = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("player");
            Element element = (Element) playerObject.item(0);
            name = element.getElementsByTagName("name").item(0).getTextContent();
            uniqueid = element.getElementsByTagName("uniqueid").item(0).getTextContent();
            avatar = element.getElementsByTagName("avatar").item(0).getTextContent();
            online = Integer.parseInt(element.getElementsByTagName("online").item(0).getTextContent());
            skill = Integer.parseInt(element.getElementsByTagName("skill").item(0).getTextContent());
            Log.v("SKILL_ERROR", "Skill:  " + skill);
            kills = Integer.parseInt(element.getElementsByTagName("kills").item(0).getTextContent());
            deaths = Integer.parseInt(element.getElementsByTagName("deaths").item(0).getTextContent());
            hs = Integer.parseInt(element.getElementsByTagName("hs").item(0).getTextContent());
            rank = Integer.parseInt(element.getElementsByTagName("rank").item(0).getTextContent());
            suicides = Integer.parseInt(element.getElementsByTagName("suicides").item(0).getTextContent());
            shots = Integer.parseInt(element.getElementsByTagName("shots").item(0).getTextContent());
            hits = Integer.parseInt(element.getElementsByTagName("hits").item(0).getTextContent());
            time = Long.parseLong(element.getElementsByTagName("time").item(0).getTextContent());
            assists = Integer.parseInt(element.getElementsByTagName("assists").item(0).getTextContent());
            assisted = Integer.parseInt(element.getElementsByTagName("assisted").item(0).getTextContent());
            killstreak = Integer.parseInt(element.getElementsByTagName("killstreak").item(0).getTextContent());
            deathStreak = Integer.parseInt(element.getElementsByTagName("deathstreak").item(0).getTextContent());
            rounds = Integer.parseInt(element.getElementsByTagName("rounds").item(0).getTextContent());
            survived = Double.parseDouble(element.getElementsByTagName("survived").item(0).getTextContent());
            wins = Integer.parseInt(element.getElementsByTagName("wins").item(0).getTextContent());
            losses = Integer.parseInt(element.getElementsByTagName("losses").item(0).getTextContent());
            cc = element.getElementsByTagName("cc").item(0).getTextContent();
            cn = element.getElementsByTagName("cn").item(0).getTextContent();
            if (cc.equals("uk")) {
                cc = "gb";
            }
            if (cc == null) {
                cc = "Unknown";
            }
            if (cn == null) {
                cn = "Unknown";
            }
            NodeList favObject = doc.getElementsByTagName("favweapon");
            boolean isNullElement = (favObject.getLength() != 0) ? true : false;
            Log.v("SKILL_ERROR", "Element Exist: " + isNullElement);
            if (isNullElement) {
                Element weaponElement = (Element) favObject.item(0);
                weaponCode = weaponElement.getElementsByTagName("code").item(0).getTextContent();
                weaponName = weaponElement.getElementsByTagName("name").item(0).getTextContent();
                weaponKills = Integer.parseInt(weaponElement.getElementsByTagName("kills").item(0).getTextContent());
                Log.v("SKILL_ERROR", "Weapon Content: " + weaponCode + " Weapon Name: " + weaponName);
            }
//            //    public Player( String name, String uniqueid, String avatar, int online, int skill, int kills, int deaths, int hs, int suicides, int shots, int hits, long time, int rank, int assists, int assisted, int killstreak, int deathStreak, int rounds, double survived, int wins, int losses, String countryCode, String countryName, String weaponCode, String weaponName, int weaponKills) {
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        player = new Player(name, uniqueid, avatar, online, skill, kills, deaths, hs, suicides, shots, hits, time, rank, assists, assisted, killstreak, deathStreak, rounds, survived, wins, losses, cc, cn, weaponCode, weaponName, weaponKills);
        return player;
    }

    public int getRankDifference(String jsonString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("playerlist");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            int lastNode = children.getLength() - 1;
            int previousNode = children.getLength() - 2;
            Element currentPlayer = (Element) children.item(lastNode);
            Element nextPlayer = (Element) children.item(previousNode);
            int rank = Integer.parseInt(currentPlayer.getElementsByTagName("rank").item(0).getTextContent());
            int pointDifference = 0;
            if (rank != 1) {
                int pointsRankCurrent = Integer.parseInt(currentPlayer.getElementsByTagName("skill").item(0).getTextContent());
                int pointsRankNext = 0;
                pointsRankNext = Integer.parseInt(nextPlayer.getElementsByTagName("skill").item(0).getTextContent());
                pointDifference = pointsRankNext - pointsRankCurrent;
            }
            return pointDifference;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Player> getRankedList(String jsonString) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        SharedPreferenceModel sharedPreferenceModel = new SharedPreferenceModel(context);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            /**
             * Max Player Value
             */
            NodeList maxNumberObject = doc.getElementsByTagName("rankinginfo");
            Element numberElements = (Element) maxNumberObject.item(0);
            int maxNumberPlayers = Integer.parseInt(numberElements.getElementsByTagName("activeplayers").item(0).getTextContent());
            sharedPreferenceModel.saveSharedPrefencesMaxValue(maxNumberPlayers);
            NodeList playerObject = doc.getElementsByTagName("playerlist");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            for (int i = 1; i < children.getLength(); i++) {
                Element element = (Element) children.item(i);
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                String uniqueid = element.getElementsByTagName("uniqueid").item(0).getTextContent();
                String avatar = element.getElementsByTagName("avatar").item(0).getTextContent();
                int online = Integer.parseInt(element.getElementsByTagName("online").item(0).getTextContent());
                int rank = Integer.parseInt(element.getElementsByTagName("rank").item(0).getTextContent());
                int skill = Integer.parseInt(element.getElementsByTagName("skill").item(0).getTextContent());
                String cc = element.getElementsByTagName("cc").item(0).getTextContent();
                if (cc.equals("uk")) {
                    cc = "gb";
                }
                String cn = element.getElementsByTagName("cn").item(0).getTextContent();
                System.out.println("Name: " + name + " Rank: " + rank);
                playerList.add(new Player(name, uniqueid, avatar, online, rank, skill, cn, cc));
            }
            Log.v("DATA_RANK", "Rank Size: " + playerList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playerList;
    }

    public ArrayList<Weapon> getWeaponList(String jsonString) {
        Utility utility = new Utility(context);
        String[] weaponArr = context.getResources().getStringArray(R.array.weapon_list);
        ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("weapons");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            int childrenSize = children.getLength();
            int weaponLength = weaponArr.length;
            for (int i = 0; i < weaponArr.length; i++) {
                String[] splitFunction = weaponArr[i].split(",");
                int image_resource = utility.getDrawableID(context, splitFunction[1]);
                Weapon weapon = Utility.SetEmptyWeapon(splitFunction[0], image_resource);
                weaponList.add(weapon);
            }
            for (int i = 0; i < childrenSize; i++) {
                Element element = (Element) children.item(i);
                String weaponCode = element.getElementsByTagName("code").item(0).getTextContent();
                if (weaponCode.equals("inferno")) {
                    weaponCode = "molotov";
                }
                int weaponImage = utility.getDrawableID(context, weaponCode);
                String weaponName = element.getElementsByTagName("name").item(0).getTextContent();
                Log.v("WEAPON_INFO", "Weapon Code: " + weaponCode);
                double modifier = Double.parseDouble(element.getElementsByTagName("modifier").item(0).getTextContent());
                int weaponKills = Integer.parseInt(element.getElementsByTagName("kills").item(0).getTextContent());
                int weaponDeaths = Integer.parseInt(element.getElementsByTagName("deaths").item(0).getTextContent());
                int weaponHeadshot = Integer.parseInt(element.getElementsByTagName("hs").item(0).getTextContent());
                int weaponShots = Integer.parseInt(element.getElementsByTagName("shots").item(0).getTextContent());
                int weaponHits = Integer.parseInt(element.getElementsByTagName("hits").item(0).getTextContent());
                int weaponDamage = Integer.parseInt(element.getElementsByTagName("damage").item(0).getTextContent());
                Weapon weapon = new Weapon(weaponImage, weaponName, modifier, weaponKills, weaponDeaths, weaponHeadshot, weaponShots, weaponHits, weaponDamage);
                if (weaponName.equals("M4A1-S")) {
                    Log.v("WEAPON_MISSING", "Weapon Info: " + weapon);
                }
                /**
                 * Thank you Gameme for implementing such great XML. :)
                 */
                for (int j = 0; j < weaponLength; j++) {
                    String localWeaponName = weaponList.get(j).getWeaponName();
                    Log.v("WEAPON_MISSING", "Weapon Local Weapon: " + localWeaponName + " Weapon(Gameme) " + weaponName);
                    if (localWeaponName.equals(weaponName)) {
                        weaponList.set(j, weapon);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weaponList;
    }

    public ArrayList<GameMap> getMapList(String jsonString) {
        ArrayList<GameMap> mapList = new ArrayList<GameMap>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("maps");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            for (int i = 1; i < children.getLength(); i++) {
                Element element = (Element) children.item(i);
                Utility utility = new Utility(context);
                int mapImage = utility.getDrawableID(context, "de_dust2");
                String mapName = element.getElementsByTagName("name").item(0).getTextContent();
                long mapTime = Long.parseLong(element.getElementsByTagName("time").item(0).getTextContent());
                int mapKills = Integer.parseInt(element.getElementsByTagName("kills").item(0).getTextContent());
                int mapDeaths = Integer.parseInt(element.getElementsByTagName("deaths").item(0).getTextContent());
                int mapHeadshots = Integer.parseInt(element.getElementsByTagName("hs").item(0).getTextContent());
                GameMap weapon = new GameMap(mapImage, mapName, mapTime, mapKills, mapDeaths, mapHeadshots);
                mapList.add(weapon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public ArrayList<RandomStats> getRandomStats(String jsonString) {
        ArrayList<RandomStats> randomStatList = new ArrayList<RandomStats>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("actions");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            for (int i = 1; i < children.getLength(); i++) {
                Element element = (Element) children.item(i);
                Utility utility = new Utility(context);
                int mapImage = utility.getDrawableID(context, "de_dust2");
                String randomCode = element.getElementsByTagName("code").item(0).getTextContent();
                String randomName = element.getElementsByTagName("name").item(0).getTextContent();
                int randomAchievement = Integer.parseInt(element.getElementsByTagName("achieved").item(0).getTextContent());
                int randomBonus = Integer.parseInt(element.getElementsByTagName("bonus").item(0).getTextContent());
                RandomStats randomStat = new RandomStats(randomCode, randomName, randomAchievement, randomBonus);
                randomStatList.add(randomStat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return randomStatList;
    }

    public GameServer getGameServer(String jsonString) {
        Utility utility = new Utility(context);
        GameServer gameServer = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Log.v(JSON_HANDLER, "getGamerServer: " + jsonString);
            InputSource inputStream = new InputSource(new StringReader(jsonString));
            Document doc = builder.parse(inputStream);
            doc.normalizeDocument();
            NodeList playerObject = doc.getElementsByTagName("serverinfo");
            Node node = playerObject.item(0);
            NodeList children = node.getChildNodes();
            Log.v(JSON_HANDLER, "After Processing  Size Of Children" + children.item(0));
            Element element = (Element) children.item(0);
            String serverName = element.getElementsByTagName("name").item(0).getTextContent().trim();
            String serverIP = element.getElementsByTagName("addr").item(0).getTextContent();
            String serverUrl = element.getElementsByTagName("addr").item(0).getTextContent();
            String mapName = element.getElementsByTagName("map").item(0).getTextContent();
            int mapImage = utility.getDrawableID(context, mapName);
            long serverTime = Long.parseLong(element.getElementsByTagName("time").item(0).getTextContent());
            int onlinePlayers = Integer.parseInt(element.getElementsByTagName("act").item(0).getTextContent());
            int maximumPlayers = Integer.parseInt(element.getElementsByTagName("max").item(0).getTextContent());
            ArrayList<Player> listOfPlayers = new ArrayList<Player>();
            if (onlinePlayers != 0) {
                NodeList innerOuterObject = element.getElementsByTagName("players");
                Node innerNode = innerOuterObject.item(0);
                NodeList playerList = innerNode.getChildNodes();
                for (int j = 0; j < playerList.getLength(); j++) {
                    Element playerElement = (Element) playerList.item(j);
                    String name = playerElement.getElementsByTagName("name").item(0).getTextContent();
                    String uniqueid = playerElement.getElementsByTagName("uniqueid").item(0).getTextContent();
                    String avatar = playerElement.getElementsByTagName("avatar").item(0).getTextContent();
                    String team = playerElement.getElementsByTagName("team").item(0).getTextContent();
                    int skillChange = Integer.parseInt(playerElement.getElementsByTagName("skillchange").item(0).getTextContent());
                    int skill = Integer.parseInt(playerElement.getElementsByTagName("skill").item(0).getTextContent());
                    int kills = Integer.parseInt(playerElement.getElementsByTagName("kills").item(0).getTextContent());
                    int deaths = Integer.parseInt(playerElement.getElementsByTagName("deaths").item(0).getTextContent());
                    long connected = Long.parseLong(playerElement.getElementsByTagName("connected").item(0).getTextContent());
                    String countryCode = playerElement.getElementsByTagName("cc").item(0).getTextContent();
                    String countryName = playerElement.getElementsByTagName("cn").item(0).getTextContent();
                    Player player = new Player(name, uniqueid, avatar, connected, kills, deaths, team, skillChange, skill, countryCode, countryName);
                    Log.v(JSON_HANDLER, "Player Name: " + player.getName());
                    listOfPlayers.add(player);
                }
                gameServer = new GameServer(serverName, serverIP, serverUrl, mapName, mapImage, serverTime, onlinePlayers, maximumPlayers, listOfPlayers);
            } else {
                gameServer = new GameServer(serverName, serverIP, serverUrl, mapName, mapImage, serverTime, onlinePlayers, maximumPlayers, listOfPlayers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameServer;
    }

}
