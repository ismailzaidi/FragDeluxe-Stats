package com.fragdeluxestats.model.HTTPHandlers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Yusuf on 27/12/2015.
 */
public class AboutDataAsync {
    public ArrayList<Integer> getAboutStats() {
        ArrayList<Integer> statsList = new ArrayList<Integer>();
        String url = "http://steamcommunity.com/groups/fragdeluxecommunity/memberslistxml/?xml=1";
        try{
            DocumentBuilderFactory factory;
            factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL uri = new URL(url);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            int memberCount = Integer.parseInt(doc.getElementsByTagName("memberCount").item(0).getTextContent());
            int membersOnline = Integer.parseInt(doc.getElementsByTagName("membersOnline").item(0).getTextContent());
            statsList.add(memberCount);
            statsList.add(membersOnline);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("ABOUT_VIEW", "Error Exception:"  +   e.getStackTrace());
        }
        return statsList;
    }
}
