package com.fragdeluxestats.model;

import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Yusuf on 19/12/2015.
 */
public class ProfileLog {
    private static final long startTime = SystemClock.uptimeMillis();

    public static void tick()
    {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[1];
        Log.i("ProfileLog", (SystemClock.uptimeMillis()-startTime)+ "ms at "+ stackTraceElement.getClassName()+" - " +stackTraceElement.getMethodName()+":"+stackTraceElement.getLineNumber());
    }
}
