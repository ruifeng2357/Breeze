package com.airapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref
{
    public static void setIsFirstTime(Context context, boolean isfirsttime)
    {
        SharedPreferences pref = context.getSharedPreferences(Constant.g_strSharedPref_FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constant.g_strSharedPref_IsFirstTime, isfirsttime);
        editor.commit();
    }

    public static boolean getIsFirstTime(Context context)
    {
        SharedPreferences pref = context.getSharedPreferences(Constant.g_strSharedPref_IsFirstTime, Context.MODE_PRIVATE);
        return pref.getBoolean(Constant.g_strSharedPref_IsFirstTime, true);
    }

    public static void setWifiInfo(Context context, String szSSID, String szPassword)
    {
        SharedPreferences pref = context.getSharedPreferences(Constant.g_strSharedPref_FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(szSSID, szPassword);
        editor.commit();
    }

    public static String getWifiInfo(Context context, String szSSID) {
        SharedPreferences pref = context.getSharedPreferences(Constant.g_strSharedPref_FileName, Context.MODE_PRIVATE);
        return pref.getString(szSSID, "");
    }
}
