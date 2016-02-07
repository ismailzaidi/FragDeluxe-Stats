package com.fragdeluxestats.model.HTTPHandlers;

import android.os.AsyncTask;
import android.os.Handler;

import com.fragdeluxestats.bean.Player;
import com.fragdeluxestats.model.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yusuf on 26/12/2015.
 */
public class LoginAsyncTask extends AsyncTask<String, Void, Player> {
    private String API_KEY = "EFE0814E9EA00E09C2ADA579B18C3C5E";
    private String userID;
    private HTTPHandler handler;
    private UserLoginCallBack callBack;
    public interface UserLoginCallBack{
        void AuthenticateUser(Player player);
    }
    public LoginAsyncTask(String userID, UserLoginCallBack callBack) {
        this.userID = userID;
        handler = new HTTPHandler();
        this.callBack=callBack;
    }
    @Override
    protected Player doInBackground(String... strings) {
        String steam64ID = fetchSteam64ID();
        if(steam64ID!=null){
            Player player = generateSummaryProfile(steam64ID);
            return player;
        }
        //Captures Private and other circumstances.
        return null;
    }
    @Override
    protected void onPostExecute(Player player) {
        super.onPostExecute(player);
        callBack.AuthenticateUser(player);
    }
    public Player generateSummaryProfile(String steam64ID) {
        try {
            String userFriendsProfile = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=" + API_KEY + "&steamids="
                    + steam64ID;
            String friendProfileJson = handler.readHTTPRequest(userFriendsProfile);
            JSONObject json_friend = new JSONObject(friendProfileJson);
            JSONArray arr = json_friend.getJSONObject("response").getJSONArray("players");
            JSONObject obj = arr.getJSONObject(0);
            int communityState = obj.getInt("communityvisibilitystate");
            int profileState = obj.getInt("profilestate");
            String profileURL = obj.getString("profileurl");
            String profileAvatarURL = obj.getString("avatarfull");
            int personstate = obj.getInt("personastate");
            String userName = obj.getString("personaname");
            int lastLogin = obj.getInt("lastlogoff");
            String timeCreated = (obj.isNull("timecreated")) ? " " : obj.getString("timecreated");
            String uniqueID = Utility.steam64TOSteamID(steam64ID);
            if(Utility.isExistInFragDeluxe(steam64ID)){
                return new Player(uniqueID,steam64ID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String fetchSteam64ID(){
        String URL = "http://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/?key=" +API_KEY + "&vanityurl="
                + userID;
        String steamID = "";
        try {
            HTTPHandler handler = new HTTPHandler();
            String jsonUser = handler.readHTTPRequest(URL);
            JSONObject obj = new JSONObject(jsonUser);
            JSONObject responseObj = obj.getJSONObject("response");
            int status = responseObj.getInt("success");
            if (status == 1) {// Success
                steamID = responseObj.getString("steamid");
                return steamID;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
