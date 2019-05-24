package com.hcmus.movieapp.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class AppManager {

    private Context mContext;
    private static final String TAG = AppManager.class.getName();
    private static AppManager mInstance;
    private CommunicationManager mCommunicationManager;
    private Properties cloudConfig;

    public AppManager(Context context) {
        mContext = context;
    }

    public CommunicationManager getCommService() {
        return mCommunicationManager;
    }

    public float convertSpToPixel(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mContext.getResources().getDisplayMetrics());
    }

    public static synchronized void create(Context context) {
        if (mInstance == null) {
            mInstance = new AppManager(context);
        }
        mInstance.initializeInstance(context);
    }

    public static AppManager getInstance() {
        return mInstance;
    }

    private void initializeInstance(Context context) {
        loadCloudConfig();
        mCommunicationManager = new CommunicationManager(context);
    }

    private void loadCloudConfig() {
        AssetManager assetManager = mContext.getAssets();
        cloudConfig = new Properties();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("cloud_config.properties");
            cloudConfig.load(inputStream);
            refineCloudUrls();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refineCloudUrls() {
        Enumeration<?> properties = cloudConfig.propertyNames();
        String key;
        while (properties.hasMoreElements()) {
            key = (String) properties.nextElement();
            if (cloudConfig.getProperty(key).contains("${")) {
                makePlainValueConfig(key);
            }
        }
    }

    private void makePlainValueConfig(String key) {
        String value = cloudConfig.getProperty(key);
        int start = value.indexOf("${");
        int end = value.indexOf("}");
        if (start == -1 || end == -1 || (start + 2 == end)) return;
        String subKey = value.substring(start + 2, end);
        String replacedValue = value.substring(start, end + 1);
        String newValue = value.replace(replacedValue, cloudConfig.getProperty(subKey));
        cloudConfig.setProperty(key, newValue);
        makePlainValueConfig(key);
    }

    public String getCloudUrl(String key) {
        return cloudConfig.getProperty(key);
    }
}
