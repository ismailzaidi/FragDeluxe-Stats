package com.fragdeluxestats.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.fragdeluxestats.R;
import com.fragdeluxestats.bean.GameServer;
import com.fragdeluxestats.bean.Weapon;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class Utility {

    private Context context;
    private SharedPreferenceModel spm;

    public Utility(Context context) {
        this.context = context;
        spm = new SharedPreferenceModel(context);
    }

    public static String steam64TOSteamID(String steam64ID) {
        long steam64 = Long.parseLong(steam64ID);
        long universe = (steam64 >> 56) & 0xFF;
        if (universe == 1) {
            universe = 0;
        }
        long accountIDLowBit = (steam64 & 1);
        long accountIdHighBit = (steam64 >> 1) & 0x7FFFFFF;
        String steamID = "STEAM_" + universe + ":" + accountIDLowBit + ":" + accountIdHighBit;
        return steamID;
    }

    public static long convertSteamIdToCommunityId(String steamId) {
        if (steamId.matches("^STEAM_[0-1]:[0-1]:[0-9]+$")) {
            String[] tmpId = steamId.substring(8).split(":");
            return Long.valueOf(tmpId[0]) + Long.valueOf(tmpId[1]) * 2 + 76561197960265728L;
        } else if (steamId.matches("^\\[U:[0-1]:[0-9]+\\]+$")) {
            String[] tmpId = steamId.substring(3, steamId.length() - 1).split(":");
            return Long.valueOf(tmpId[0]) + Long.valueOf(tmpId[1]) + 76561197960265727L;
        }
        return 0l;
    }



    public int getFlagID(String countryCode) {
        return context.getResources().getIdentifier(countryCode.toLowerCase(Locale.getDefault()), "drawable",
                context.getPackageName());
    }

    public int getDrawableID(Context context, String imageCode) {
        return context.getResources().getIdentifier(imageCode.toLowerCase(Locale.getDefault()), "drawable",
                context.getPackageName());
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void ShowInternetError(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        builder.title("FragDeluxe");
        builder.content("No internet connection");
        builder.widgetColor(ContextCompat.getColor(context, R.color.profilecolor));
        builder.backgroundColor(ContextCompat.getColor(context, R.color.secondryColor));
        builder.titleColor(ContextCompat.getColor(context, android.R.color.white));
        builder.contentColor(ContextCompat.getColor(context, android.R.color.white));
        final MaterialDialog dialog = (MaterialDialog) builder.build();
        builder.show();
    }

    /**
     * To add new servers to the application.
     * Add port numbers in the PORT_NUMBERS Array
     *
     * @return
     */
    public static String[] getListOfIP() {
        String server1 = "http://play.fragdeluxe.com";
        String ip_address = "";
        try {
            URL url = new URL(server1);
            InetAddress address = InetAddress.getByName(url.getHost());
            ip_address = address.getHostAddress();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int[] PORT_NUMBERS = {27015, 27016, 27017, 27018, 27019};
        String[] IP_ADDRESSES = new String[PORT_NUMBERS.length];
        for (int i = 0; i < IP_ADDRESSES.length; i++) {
            String IP = ip_address + ":" + PORT_NUMBERS[i];
            IP_ADDRESSES[i] = "http://fragdeluxe.gameme.com/api/serverinfo/" + IP + "/players";
        }
        return IP_ADDRESSES;
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static boolean isExistInFragDeluxe(String steam64ID) {
        try {
            String steamID = Utility.steam64TOSteamID(steam64ID);
            String httpUrl = "http://fragdeluxe.gameme.com/api/playerinfo/csgo/" + steamID + "/";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setCoalescing(true);
            Log.v("xml_parser", "Coalescing Aware: " + factory.isCoalescing());
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL uri = new URL(httpUrl);
            Document doc = builder.parse(new InputSource(uri.openStream()));
            doc.normalizeDocument();
            NodeList xml = doc.getElementsByTagName("error");
            return xml.getLength() == 0 ? true : false;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return false;
    }

    public Bitmap bitmapHandler(Resources res, int resId, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        return bitmap;
    }

    public static Weapon SetEmptyWeapon(String weaponName, int weapon_resource) {
        return new Weapon(weapon_resource, weaponName, 0.0, 0, 0, 0, 0, 0, 0);
    }
//    public static GameServer SetEmptyServer(String weaponName, int weapon_resource) {
//        return new GameServer(weapon_resource, weaponName, 0.0, 0, 0, 0, 0, 0, 0);
//    }


    public String getFormatSorter(int num) {
        DecimalFormat format = new DecimalFormat("####,###,###.##");
        return format.format(num);
    }

    public String getFormatSorter(double num) {
        DecimalFormat format = new DecimalFormat("####,###,###.##");
        return format.format(num);
    }
}
