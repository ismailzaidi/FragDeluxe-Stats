package com.fragdeluxestats.model;

/**
 * Created by Yusuf on 20/12/2015.
 */
public class ResourceReference {
    /**
     * Login User Key
     *
     *You will pass through -55 as the skinindex and seed

     *
     */
    public static final String LOGIN_KEY="com.fragdeluxe.LOGIN_KEY";

    public static final String COLLECTIONS_FILE = "collections.txt";
    public static final String FILEDETAIL_FILE = "details.txt";
    public static final String PLAYER_LIST_FILE = "player_list.txt";
    public static final String PLAYER_GLOBAL_DATA = "global_data.txt";

    public enum informationType {STATISTICS, MAPS, WEAPONS}
    /**
     * About Keys
     */
    public static final String ABOUT_POSITION ="com.fragdeluxe.aboutposition";
    /**
     * Compare Fragment Stuff
     */
    public static final String COMPARE_TYPE ="com.fragdeluxe.comparetype";
    public static final String COMPARE_ARRAY ="com.fragdeluxe.compareArray";
    public static final String COMPARE_USER_ID ="com.fragdeluxe.compareArray";
    public static final String COMPARE_PROGRESS_LIST = "com.fragdeluxe.compare_progress_list";
    public static final String[] types = {"statistics","weapon","maps"};
    /**
     * Hash For Multiple JSON
     */
    public static final String SPLIT_KEY = "87930354325ee07916c3b3e45c83bc9b";


    /**
     *
     * Friends Key
     */
    public static final String FRIENDS_KEY = "com.fragdeluxestats.friends";
    public static final String IS_FRIEND_BOOL_KEY = "com.fragdeluxestats.booleanisfriend";
}
