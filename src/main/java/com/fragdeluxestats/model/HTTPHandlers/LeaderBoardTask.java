package com.fragdeluxestats.model.HTTPHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.ResourceReference;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Yusuf on 24/12/2015.
 */
public class LeaderBoardTask extends AsyncTask<String, String, ArrayList<Player>> {
    private Context context;
    private LeaderBoardProgressBar progressCallBack;
    private int progressValue =0;
    public interface LeaderBoardCallBack {
        void fetchPlayers(ArrayList<Player> data);
    }
    public interface LeaderBoardProgressBar{
        void updateProgress(int value, String playerName);
    }

    private LeaderBoardCallBack dataListener;
    public LeaderBoardTask(Context context, LeaderBoardCallBack dataListener,LeaderBoardProgressBar progressCallBack) {
        this.context = context;
        this.dataListener = dataListener;
        this.progressCallBack=progressCallBack;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.v("LEADERBOARD_PROGRESS_VALUE","Progressbar: " + progressValue);
        progressCallBack.updateProgress(progressValue,values[0]);
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected ArrayList<Player> doInBackground(String... strings) {
        ArrayList<Player> listOfPlayer = new ArrayList<Player>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            Log.v("xml_parser", "Coalescing Aware: " + factory.isCoalescing());
            DocumentBuilder builder = factory.newDocumentBuilder();
            String URL = "http://ismailzd.co.uk/playerlist/?all=true";
            java.net.URL uri = new URL(URL);
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
                publishProgress(name);
                progressValue++;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return listOfPlayer;
    }

    @Override
    protected void onPostExecute(ArrayList<Player> guestList) {
        super.onPostExecute(guestList);
        dataListener.fetchPlayers(guestList);
    }

}
