package com.fragdeluxestats.model.HTTPHandlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.ResourceReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Yusuf on 27/12/2015.
 */
public class MapAsyncTask {
    private HTTPHandler handler;
    private Context context;
    public MapAsyncTask(Context context) {
        this.context = context;
        handler = new HTTPHandler();
    }
    public HashMap<String,String> getMapList(){
        HashMap<String, String> userMapList = new HashMap<String, String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            Log.v("xml_parser", "Coalescing Aware: " + factory.isCoalescing());
            DocumentBuilder builder = factory.newDocumentBuilder();
            String URL = "http://ismailzd.co.uk/maplist/?collectionid=432947084";
            URL uri = new URL(URL);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            NodeList xml = doc.getElementsByTagName("maps");
            Node node = xml.item(0);
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Element element = (Element) children.item(i);
                String mapImage = element.getElementsByTagName("image").item(0).getTextContent();
                String mapName = element.getElementsByTagName("display_name").item(0).getTextContent();
                String mapCode = element.getElementsByTagName("internal_name").item(0).getTextContent();
                userMapList.put(mapCode + "," + mapName, mapImage);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return userMapList;
    }
}
