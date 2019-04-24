package com.example.movieapp.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.models.Account;
import com.example.movieapp.models.Movie;
import com.example.movieapp.models.User;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class CommunicationManager {

    private static final String TAG = CommunicationManager.class.getName();
    private static final String TAG_CLOUD = "CLOUD";

    private Context mContext;
    private RequestQueue mRequestQueue;

    public CommunicationManager() {
    }

    public CommunicationManager(Context mContext) {
        this.mContext = mContext;
        mRequestQueue = Volley.newRequestQueue(mContext);
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

    public void getLatestMovies(String tag, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Latest", null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getUserInfo(String tag, final DataParser.DataResponseListener<LinkedList<User>> listener) {
        Type type = new TypeToken<LinkedList<User>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "User", "Info", null, new DataParser<LinkedList<User>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void doLogin(String tag, JSONObject body, DataParser.DataResponseListener<LinkedList<Account>> listener) {
        Type type = new TypeToken<LinkedList<Account>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "Login", body, new DataParser(listener,null, type));
    }

    public void doRegister(String tag, JSONObject body, DataParser.DataResponseListener<LinkedList<Account>> listener) {
        Type type = new TypeToken<LinkedList<Account>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "Register", body, new DataParser(listener,null, type));
    }

    public void doChangePassword(String tag, JSONObject body, DataParser.DataResponseListener<JSONObject> listener) {
        makeJSONObjectRequest(tag, Request.Method.POST, "Account", "ChangePassword", body, new DataParser(listener,null, JSONObject.class));
    }

    public void doUpdateUserInfo(String tag, JSONObject body, DataParser.DataResponseListener<JSONObject> listener) {
        makeJSONObjectRequest(tag, Request.Method.POST, "User", "Update", body, new DataParser(listener, null, JSONObject.class));
    }

    public void getNowShowingMovies(String tag, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "NowShowing", null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getUpcomingMovies(String tag, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {}.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Upcoming", null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    public void getMovieDetail(String tag, String movieID, final DataParser.DataResponseListener<Movie> listener) {
        Type type = new TypeToken<Movie>() {
        }.getType();
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "Detail", movieID,null, new DataParser<Movie>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
    }

    // TODO: rename this function later
    public void getMovieByPage(String tag, int page, final DataParser.DataResponseListener<LinkedList<Movie>> listener) {
        Type type = new TypeToken<LinkedList<Movie>>() {
        }.getType();
        int perPage = 50;
        makeJSONObjectRequest(tag, Request.Method.GET, "Movie", "List", page + "/" + perPage,null, new DataParser<LinkedList<Movie>>(listener, DataParser.DataRequestType.REQUEST_DATA, null, type));
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
