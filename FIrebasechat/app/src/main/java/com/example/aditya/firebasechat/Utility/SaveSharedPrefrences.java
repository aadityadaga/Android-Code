package com.example.aditya.firebasechat.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPrefrences {
  static final String PREF_USER_NAME = "username";
  static final String PREF_USER_EMAIL = "email";
  static final String PREF_USER_KEY = "key";
  static SharedPreferences.Editor editor = null;

  static SharedPreferences getSharedPreferences(Context ctx) {
    return PreferenceManager.getDefaultSharedPreferences(ctx);
  }

  public static void setUserName(Context ctx, String userName) {
    editor = getSharedPreferences(ctx).edit();
    editor.putString(PREF_USER_NAME, userName);
    editor.commit();
  }

  public static void setUserEmail(Context ctx, String Email) {
    editor = getSharedPreferences(ctx).edit();
    editor.putString(PREF_USER_EMAIL, Email);
    editor.commit();
  }
  public static void setUserKey(Context ctx, String Email) {
    editor = getSharedPreferences(ctx).edit();
    editor.putString(PREF_USER_KEY, Email);
    editor.commit();
  }

  public static String getUserEmail(Context ctx) {
    return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
  }

  public static String getUserName(Context ctx) {
    return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
  }


  public static String getUserKey(Context ctx) {
    return getSharedPreferences(ctx).getString(PREF_USER_KEY, "");
  }

  public static void clear(Context ctx) {
    editor = getSharedPreferences(ctx).edit();
    editor.clear();
    editor.commit();
  }



}
