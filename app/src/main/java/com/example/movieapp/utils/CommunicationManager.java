package com.example.movieapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONObject;

public class CommunicationManager {

    private static final String TAG = CommunicationManager.class.getName();
    private static final String TAG_CLOUD = "CLOUD";

    private Context mContext;
    private RequestQueue mRequestQueue;

    public CommunicationManager() {
    }

    public CommunicationManager(Context mContext) {
        this.mContext = mContext;
    }

    public void cancelAllRequests() {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void cancelAllRequestsAccept(final String tag) {
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return (!tag.equals(request.getTag()));
            }
        });
    }

    public void cancelRequest(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            mRequestQueue.cancelAll(tag);
        } else {
            mRequestQueue.cancelAll(TAG);
        }
    }

    private <T> void addToRequestQueue(String tag, Request<T> request) {
        if (!TextUtils.isEmpty(tag)) {
            request.setTag(tag);
        } else {
            request.setTag(TAG);
        }
        mRequestQueue.add(request);
    }

    public void makeJSONObjectRequest(String tag, int method, final String service, final String entry, final JSONObject params, final DataParser parser) {
        makeJSONObjectRequest(tag, method, service, entry, "", params, parser);
    }

    public void makeJSONObjectRequest(String tag, int method, String service, String entry, String urlParams, Object params, DataParser parser) {
        Requester requester = new Requester(service, entry, method, params, response -> {
            Log.d(TAG_CLOUD, "response: " + response);
            parser.parse(response);
        }, error -> parser.notifyError(error));
        if (!TextUtils.isEmpty(urlParams)) {
            requester.setTrailingUrl(urlParams);
        }
        addToRequestQueue(tag, requester);
    }
}
