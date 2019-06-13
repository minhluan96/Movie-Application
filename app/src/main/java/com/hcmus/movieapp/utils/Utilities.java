package com.hcmus.movieapp.utils;

import android.graphics.Bitmap;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Utilities {

    public static Map jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL && json != null) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertDate(String s) {
        //2018-04-18T23:57:09
        String newStr = s;
        String[] parts = newStr.split("T");
        String[] data = parts[0].split("-");
        String formatted = "";
        for (int i = data.length - 1; i >= 0; i--)
        {
            if (i != 0) {
                formatted += data[i] + "/";
            } else {
                formatted += data[i];
            }

        }

        return formatted;
    }

    public static String convertToSimpleDateFormat(String s) {
        //2018-04-18T23:57:09
        String newStr = s;
        String[] parts = newStr.split("T");
        String[] data = parts[0].split("-");
        String formatted = "";
        for (int i = 0; i <= data.length - 1; i++)
        {
            if (i != data.length - 1) {
                formatted += data[i] + "-";
            } else {
                formatted += data[i];
            }

        }
        // 2018-04-18
        return formatted;
    }

    public static String convertDateTime(String s) {
        //2018-04-18T23:57:09
        String newStr = s;
        String[] parts = newStr.split("T");
        String[] date = parts[0].split("-");
        String[] time = parts[1].split(":");
        String formatted = "";
        for (int i = 0; i < time.length - 1; i++)
        {
            if (i != time.length - 2) {
                formatted += time[i] + ":";
            } else {
                formatted += time[i];
            }
        }
        formatted += " ";
        for (int i = date.length - 1; i >= 0; i--)
        {
            if (i != 0) {
                formatted += date[i] + "/";
            } else {
                formatted += date[i];
            }

        }

        return formatted;
    }

    public static String formatTime(String time) {
        String newStr = time;
        String[] data = newStr.split(":");
        String formatted = data[0] + ":" + data[1];
        return formatted;
    }

    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String formatCurrency(double price) {
        NumberFormat formatter = new DecimalFormat("#,### đ");
        String formattedNumber = formatter.format(price);
        return formattedNumber;
    }

    public static String[] extractDateFromString(String dateStr) {
        // dateStr: dd/MM/YYYY
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String newDateStr = null;
        try {
            newDateStr = simpleDateFormat.format(simpleDateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str[] = newDateStr.split("/");
        return str;
    }

    public static int convertStringToInt(String str) {
        char c = str.charAt(0); // because the string only has a character
        return c - 'A' + 1;
    }

    public static Bitmap getBitMapFromView(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }
}
