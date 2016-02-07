package com.fragdeluxestats.model.HTTPHandlers;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Yusuf on 26/12/2015.
 */
public class HTTPHandler {
    private static final String ERROR_REQUEST = "Error";

    public String readHTTPRequest(String url) {
        StringBuilder content = new StringBuilder();
        try {
            HttpURLConnection client = (HttpURLConnection) new URL(url).openConnection();
            client.connect();
            Log.v("achievements", "Status Code: " + client.getResponseCode() + "\nURL: " + url);
            if (client.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = client.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                // Build string from input stream
                String readLine = reader.readLine();
                while (readLine != null) {
                    content.append(readLine);
                    readLine = reader.readLine();
                }

            } else if (client.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST) {
                return ERROR_REQUEST;
            } else {
                content = null;
            }
        } catch (IOException e) {
            Log.e("readData", "IOException:\n+e.getMessage()");
        }

        // return data
        if (content == null) {
            return (null);
        } else {
            return (content.toString());
        }
    }

    public String sendPost(String url, String... params) {
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "0");
            con.setRequestProperty("Accept-Language", "application/json");
            String urlparameter = "";
            for (int i = 0; i < params.length; i++) {
                urlparameter += params[i];
            }
            String urlParameters = urlparameter;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}