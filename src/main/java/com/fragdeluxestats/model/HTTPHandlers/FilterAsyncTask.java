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
 * Created by Yusuf on 31/12/2015.
 */
public class FilterAsyncTask extends AsyncTask<String, Void, ArrayList<Player>> {
    private StandardLoginCallBack callBack;
    public FilterAsyncTask(StandardLoginCallBack callBack) {
        this.callBack = callBack;
    }
    public interface StandardLoginCallBack {
        void fetchFragDeluxePlayerList(ArrayList<Player> playerList);
    }

    @Override
    protected ArrayList<Player> doInBackground(String... voids) {
        String URL = "http://ismailzd.co.uk/playerlist/?all=false&name=" + voids[0];
        return fetchXMLHTTP(URL);
    }
    public ArrayList<Player> fetchXMLHTTP(String httpUrl) {
        ArrayList<Player> listOfPlayer = new ArrayList<Player>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL uri = new URL(httpUrl);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            NodeList xml = doc.getElementsByTagName("players");
            NodeList foundCount = doc.getElementsByTagName("results_found");
            Log.v("NEW_LOGIN", "Count: " + foundCount);
            int itemsFound = Integer.parseInt(foundCount.item(0).getTextContent());
            if (itemsFound != 0) {
                StringWriter sw = new StringWriter();
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.transform(new DOMSource(xml.item(0)), new StreamResult(sw));
                String newXML = sw.toString();
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
                    Log.v("NEW_LOGIN", "Player: " + name);
                    String cn = element.getElementsByTagName("cn").item(0).getTextContent();
                    System.out.println("Name: " + name + " Rank: " + rank);
                    listOfPlayer.add(new Player(name, uniqueid, avatar, online, rank, skill, cn, cc));
                }
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return listOfPlayer;
    }
    @Override
    protected void onPostExecute(ArrayList<Player> players) {
        super.onPostExecute(players);
        callBack.fetchFragDeluxePlayerList(players);
    }
}
