package com.fragdeluxestats.model;

import android.content.Context;
import android.util.Log;

import com.fragdeluxestats.bean.GameMap;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.bean.RandomStats;
import com.fragdeluxestats.bean.Statistics;
import com.fragdeluxestats.bean.Weapon;
import com.fragdeluxestats.model.HTTPHandlers.MapAsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DataHandler {
    private String MAIN_QUERY;
    private String GUEST_QUERY;
    private String COMPARE_QUERY;
    private String RANK_QUERY;
    private String RANK_DIFFERENCE_QUERY;
    private String[] SERVER_QUERIES;
    private Document summaryDoc, guestDoc,rankDoc, compareDoc, rankDifferenceDoc;
    private Document[] listOfDocs;
    private Context context;
    private String steamID;
    private XMLHandler handler;
    private ArrayList<GameMap> filterList;
    private boolean isFriend;
    public DataHandler(Context context, String steamID, boolean isFriend) {
        this.context = context;
        this.steamID = steamID;
        this.isFriend = isFriend;
        generateQueries();
        generateDocuments();
        handler = new XMLHandler(context);
    }

    /**
     * For GuestActivity
     *
     * @param context
     */
    public DataHandler(Context context) {
        this.context = context;
        handler = new XMLHandler(context);
        generateRankQueries();
    }

    private void generateQueries() {
        MAIN_QUERY = "http://fragdeluxe.gameme.com/api/playerinfo/csgo/" + steamID + "/extended";
        RANK_QUERY = "http://fragdeluxe.gameme.com/api/playerlist/csgo/?limit=200&page=1";
        COMPARE_QUERY = "http://fragdeluxe.gameme.com/api/playerlist/csgo/?limit=1000&page=1";
        SERVER_QUERIES = Utility.getListOfIP();

    }

    private void generateRankQueries() {
        GUEST_QUERY = "http://fragdeluxe.gameme.com/api/playerlist/csgo/?limit=10&page=1";
        guestDoc = fetchXMLHTTP(GUEST_QUERY);
    }

    private void generateDocuments() {
        summaryDoc = fetchXMLHTTP(MAIN_QUERY);
        rankDoc = fetchXMLHTTP(RANK_QUERY);
        compareDoc = fetchXMLHTTP(COMPARE_QUERY);
        listOfDocs = new Document[SERVER_QUERIES.length];
        for (int i = 0; i < listOfDocs.length; i++) {
            listOfDocs[i] = fetchXMLHTTP(SERVER_QUERIES[i]);
        }
    }

    /**
     * Used for Guest Activity
     *
     * @return
     */
    public ArrayList<Player> getGuestList() {
        try {
            String newXML = generateNewXML(guestDoc, "playerlist");
            ArrayList<Player> rankedList = handler.getRankedList(newXML);
            return rankedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Player> getRankedList() {
        try {
            String newXML = generateNewXML(rankDoc, "gameME");
            ArrayList<Player> rankedList = handler.getRankedList(newXML);
            return rankedList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Player> getPlayerListOnline() {
        ArrayList<Player> listOfPlayer = new ArrayList<Player>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            Log.v("xml_parser", "Coalescing Aware: " + factory.isCoalescing());
            DocumentBuilder builder = factory.newDocumentBuilder();
            String URL = "http://ismailzd.co.uk/playerlist/?all=true";
            URL uri = new URL(URL);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            NodeList xml = doc.getElementsByTagName("players");
            Node node = xml.item(0);
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
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
                Log.v("RANK_TESTER", "Name: " + name + " Rank: " + rank);
                listOfPlayer.add(new Player(name, uniqueid, avatar, online, rank, skill, cn, cc));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return listOfPlayer;
    }


    public Player getPlayerSummary() {
        try {
            String newXML = generateNewXML(summaryDoc, "player");

            Player player = handler.getPlayerInfo(newXML);
            int rankDifference = generateSkillDifference(player.getRank());
            ArrayList<Weapon> listOfWeapons = getWeaponList();
            ArrayList<GameMap> mapList = getMapStats(isFriend);
            Log.v("COMPARE_RECYCLER", "Player: " + player.getName() + " Has Weapon: " + listOfWeapons.size() + " Map: " + mapList.size());
            player.setSkillDifference(rankDifference);
            player.setListOfWeapons(listOfWeapons);
            player.setListOfMaps(mapList);
            return player;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private int generateSkillDifference(int rank) {
        if (rank != 0) {
            String url = "http://fragdeluxe.gameme.com/api/playerlist/csgo/?limit=" + rank + "&page=1";
            rankDifferenceDoc = fetchXMLHTTP(url);
            String xmlStr = generateNewXML(rankDifferenceDoc, "playerlist");
            int result = handler.getRankDifference(xmlStr);
            return result;
        }
        return 0;
    }

    public ArrayList<GameServer> getServerList() {
        ArrayList<GameServer> listOfServers = new ArrayList<GameServer>();
        int[] PORT_NUMBERS = {27015, 27016, 27017, 27018, 27019};
        String URL = "play.fragdeluxe.com:";
        for (int i = 0; i < listOfDocs.length; i++) {
            try {
                String newXML = generateNewXML(listOfDocs[i], "serverinfo");
                GameServer server = handler.getGameServer(newXML);
                server.setServerIP(URL + PORT_NUMBERS[i]);
                Log.v("SERVER_INFO", "Server: " + i + " " + server);
                listOfServers.add(server);
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return listOfServers;
    }

    private ArrayList<Weapon> getWeaponList() {
        System.out.println("Before XML Parsing");
        try {
            String newXML = generateNewXML(summaryDoc, "weapons");
            ArrayList<Weapon> listOfWeapons = handler.getWeaponList(newXML);
            return listOfWeapons;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public ArrayList<Statistics> getStatsList(Player player) {
        Log.v("SKILL_ERROR", "Player: " + player);
        ArrayList<Statistics> listOfStats = new ArrayList<Statistics>();
        List<RandomStats> listOfRandomStats = getOtherStats();
        if(player!=null) {
            listOfStats.add(new Statistics("Points", String.valueOf(player.getSkill())));
            listOfStats.add(new Statistics("Kills", String.valueOf(player.getKills())));
            listOfStats.add(new Statistics("Deaths", String.valueOf(player.getDeaths())));
            /**
             * KDAxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
             */
            double KD = (double) player.getKills() / player.getDeaths();
            listOfStats.add(new Statistics("KD", String.valueOf(KD)));
            listOfStats.add(new Statistics("Headshots", String.valueOf(player.getHs())));
            listOfStats.add(new Statistics("Suicides", String.valueOf(player.getSuicides())));
            listOfStats.add(new Statistics("Shots", String.valueOf(player.getShots())));
            listOfStats.add(new Statistics("Hits", String.valueOf(player.getHits())));
            listOfStats.add(new Statistics("Time", String.valueOf(player.getTime())));
            listOfStats.add(new Statistics("Assists", String.valueOf(player.getAssists())));
            listOfStats.add(new Statistics("Assisted", String.valueOf(player.getAssisted())));
            listOfStats.add(new Statistics("Killstreak", String.valueOf(player.getKillstreak())));
            listOfStats.add(new Statistics("Deathstreak", String.valueOf(player.getDeathStreak())));
            listOfStats.add(new Statistics("Rounds", String.valueOf(player.getRounds())));
            listOfStats.add(new Statistics("Survived rounds", String.valueOf(player.getSurvived())));
            listOfStats.add(new Statistics("Won rounds", String.valueOf(player.getWins())));
            listOfStats.add(new Statistics("Lost rounds", String.valueOf(player.getLosses())));
            /**
             * Random Statistic
             */
            if (listOfRandomStats != null) {
                for (RandomStats stats : listOfRandomStats) {
                    String statName = stats.getStateName();
                    String statValue = String.valueOf(stats.getStatAchieved());
                    listOfStats.add(new Statistics(statName, statValue));
                }
            }
        }
        return listOfStats;
    }

    private List<RandomStats> getOtherStats() {
        System.out.println("Before XML Parsing");
        try {
            String newXML = generateNewXML(summaryDoc, "actions");
            ArrayList<RandomStats> listOfRandomStats = handler.getRandomStats(newXML);
            return listOfRandomStats;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    private ArrayList<GameMap> getMapStats(boolean isFriend) {
        try {
            String newXML = generateNewXML(summaryDoc, "maps");
            final ArrayList<GameMap> listOfMaps = handler.getMapList(newXML);
            filterList = new ArrayList<GameMap>();
            System.out.println("Last Map: " + listOfMaps.get(listOfMaps.size() - 1).getMapName());
            MapAsyncTask mapTask = new MapAsyncTask(context);
            HashMap<String, String> workShopMap = mapTask.getMapList();
            for (GameMap map : listOfMaps) {
                String mapName = map.getMapName().toLowerCase();
                for (Map.Entry<String, String> workshopItem : workShopMap.entrySet()) {
                    String[] mapItems = workshopItem.getKey().split(",");
                    String mapCode = mapItems[0].toLowerCase();
                    String workshopMapName = mapItems[1];
                    if (mapCode.equals(mapName)) {
                        Log.v("MAP_INFO", "Map Match True");
                        map.setMapName(workshopMapName);
                        Utility utility = new Utility(context);
                        int map_image = utility.getDrawableID(context, mapCode);
                        map.setMapImage(map_image);
                        filterList.add(map);
                    }
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return filterList;
    }

    public Document fetchXMLHTTP(String httpUrl) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL uri = new URL(httpUrl);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            return doc;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public String generateNewXML(Document doc, String tag) {
        try {
            NodeList xml = doc.getElementsByTagName(tag);
            StringWriter sw = new StringWriter();
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.transform(new DOMSource(xml.item(0)), new StreamResult(sw));
            String newXML = sw.toString();
            System.out.println("New XML: " + newXML);
            return newXML;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return "";
    }
}
/**
 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 * DocumentBuilder builder = factory.newDocumentBuilder();
 * Document doc = builder.parse(newXML);
 * Element element = (Element) doc.getElementsByTagName("pagination").item(0);
 * element.getParentNode().removeChild(element);
 * doc.normalize();
 * <p/>
 * <p/>
 * StringWriter sw = new StringWriter();
 * Transformer serializer = TransformerFactory.newInstance().newTransformer();
 * serializer.transform(new DOMSource(doc), new StreamResult(sw));
 * String updatedXML = sw.toString();
 * System.out.println("Updated XML: " + updatedXML);
 */

