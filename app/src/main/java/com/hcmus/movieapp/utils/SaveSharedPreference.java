package com.hcmus.movieapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.models.User;

public class SaveSharedPreference {
    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLoggedIn(Context context, boolean loggedIn) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean(Constant.LOGGED_IN_PREF, loggedIn);
        editor.apply();
    }

    public static boolean getLoggedStatus(Context context) {
        return getPreferences(context).getBoolean(Constant.LOGGED_IN_PREF, false);
    }

    public static void setAccountInfo(Context context, Account accountInfo) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putInt("id", accountInfo.getId());
        editor.putString("full_name", accountInfo.getUser().getFullName());
        Integer gender = accountInfo.getUser().getGender();
        editor.putString("gender", gender == null ? null : gender.toString());
        editor.putString("email", accountInfo.getUser().getEmail());
        editor.putString("phone", accountInfo.getUser().getPhone());
        editor.putString("address", accountInfo.getUser().getAddress());
        editor.putString("birthday", accountInfo.getUser().getBirthday());
        editor.apply();
    }

    public static Account getAccountInfo(Context context) {
        Account accountInfo = new Account();
        accountInfo.setId(getPreferences(context).getInt("id", 0));

        User userInfo = new User();
        userInfo.setFullName(getPreferences(context).getString("full_name", null));
        String gender = getPreferences(context).getString("gender", null);
        userInfo.setGender(gender == null ? null : Integer.parseInt(gender));
        userInfo.setEmail(getPreferences(context).getString("email", null));
        userInfo.setPhone(getPreferences(context).getString("phone", null));
        userInfo.setAddress(getPreferences(context).getString("address", null));
        userInfo.setBirthday(getPreferences(context).getString("birthday", null));

        accountInfo.setUser(userInfo);

        return accountInfo;
    }

    public static void removeUserInfo(Context context) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.remove("id");
        editor.remove("full_name");
        editor.remove("gender");
        editor.remove("email");
        editor.remove("phone");
        editor.remove("address");
        editor.remove("birthday");
        editor.commit();
    }
}
