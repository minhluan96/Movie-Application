package com.example.movieapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.movieapp.models.User;

import static com.example.movieapp.utils.Constant.LOGGED_IN_PREF;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(LOGGED_IN_PREF, false);
    }

    public static void setUserInfo(Context context, User userInfo) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("full_name", userInfo.getFullName());
        editor.putInt("gender", userInfo.getGender());
        editor.putString("email", userInfo.getEmail());
        editor.putString("phone", userInfo.getPhone());
        editor.putString("address", userInfo.getAddress());
        editor.putString("birthday", userInfo.getBirthday());
        editor.apply();
    }

    public static User getUserInfo(Context context) {
        User userInfo = new User();
        userInfo.setFullName(getPreferences(context).getString("full_name", null));
        userInfo.setGender(getPreferences(context).getInt("gender", 1));
        userInfo.setEmail(getPreferences(context).getString("email", null));
        userInfo.setPhone(getPreferences(context).getString("phone", null));
        userInfo.setAddress(getPreferences(context).getString("address", null));
        userInfo.setBirthday(getPreferences(context).getString("birthday", null));

        return userInfo;
    }

    public static void removeUserInfo(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.remove("full_name");
        editor.remove("gender");
        editor.remove("email");
        editor.remove("phone");
        editor.remove("address");
        editor.remove("birthday");
        editor.commit();
    }
}
