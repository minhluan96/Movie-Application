package com.hcmus.movieapp.utils;

import android.text.TextUtils;

import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;

public class DataParser<T> {
    protected DataResponseListener<T> mListner = null;
    protected int mRequestType = DataRequestType.REQUEST_NODATA;
    protected Class<T> mClass = null;
    protected Type mType = null;
    protected Class<?> mTypeAdapterClass = null;
    protected JsonDeserializer mDeserializer;
    protected boolean isRemoveResult = true;

    public DataParser(DataResponseListener<T> listener, Class<T> clazz, Type type) {
        this(listener, DataRequestType.REQUEST_DATA, clazz, type);
    }

    public DataParser(DataResponseListener<T> listener, int requestType, Class<T> clazz, Type type) {
        this(listener, requestType, clazz, type, null);
    }

    public DataParser(DataResponseListener<T> listener, int requestType, Class<T> clazz, Type type, boolean isRemoveResult) {
        this(listener, requestType, clazz, type, null);
        this.isRemoveResult = isRemoveResult;
    }

    public DataParser(DataResponseListener<T> listener, int requestType, Class<T> clazz, Type type, JsonDeserializer customDeserializer) {
        mListner = listener;
        mRequestType = requestType;
        mClass = clazz;
        mType = type;
        mDeserializer = customDeserializer;
    }

    public void parse(String jsonString) {
        if (mRequestType == DataRequestType.REQUEST_NODATA) {
            if (mListner != null) mListner.onDataResponse(null);
            return;
        }
        if (TextUtils.isEmpty(jsonString)) {
            if (mListner != null) mListner.onDataError("No data found");
            return;
        }
        try {
            Gson gson;
            if (mDeserializer != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                if (mClass != null) {
                    gsonBuilder.registerTypeAdapter(mClass, mDeserializer);
                } else if (mType != null) {
                    gsonBuilder.registerTypeAdapter(mType, mDeserializer);
                }
                gson = gsonBuilder.create();
            } else {
                gson = new Gson();
            }
            //need to remove result field if have
            if (isRemoveResult) {
                jsonString = removeResultField(jsonString);
            }
            T result = null;
            if (mClass != null) {
                result = gson.fromJson(jsonString, mClass);
            } else if (mType != null) {
                result = gson.fromJson(jsonString, mType);
            } else {
                //FIXME: cannot initialize TypeToken if T is defined in run time.
                Type type = new TypeToken<T>() {
                }.getType();
                result = gson.fromJson(jsonString, mType);
            }
            if (mListner != null) mListner.onDataResponse(result);
        } catch (Exception e) {
            e.printStackTrace();
            if (mListner != null) mListner.onDataError("Data incorrect");
        }
    }

    public void notifyError(VolleyError volleyError) {
        if (mListner == null) return;
        String errorMessage = volleyError.getMessage();
        if (volleyError instanceof NoConnectionError) {
            if (volleyError.getCause() != null) {
                if (volleyError.getCause() instanceof NoRouteToHostException) {
                    errorMessage = "The server is under maintenance. Please try again later.";
                } else if (volleyError.getCause() instanceof UnknownHostException) {
                    errorMessage = "Cannot connect to the internet. The service may not be available.";
                }
            }
        } else if (volleyError instanceof ServerError) {
            errorMessage = "We are experiencing an error contacting the server. Please try again later.";
        } else if (volleyError.networkResponse != null) {
            if (volleyError.networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                errorMessage = "The request has not been applied because it lacks valid authentication credentials for the target resource";
            } else if (volleyError.networkResponse.statusCode == HttpStatus.SC_BAD_GATEWAY ||
                    volleyError.networkResponse.statusCode == HttpStatus.SC_NOT_FOUND) {
                errorMessage = "The server is under maintenance. Please try again later.";
            } else if (volleyError.networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                errorMessage = "You are not authorized to access this event";
            } else if (volleyError.networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                errorMessage = "The server cannot or will not process the request due to an apparent client error (e.g., malformed request syntax, size too large, invalid request message framing, or deceptive request routing)";
            }
        }
        mListner.onRequestError(errorMessage, volleyError);
    }

    public void notifyCancel() {
        if (mListner != null) mListner.onCancel();
    }

    public interface DataRequestType {
        int REQUEST_DATA = 1;
        int REQUEST_NODATA = 2;
    }

    public interface DataResponseListener<T> {
        void onDataResponse(T result);

        void onDataError(String errorMessage);

        void onRequestError(String errorMessage, VolleyError volleyError);

        void onCancel();
    }

    protected String removeResultField(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            //it's array
            return jsonString;
        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject != null && jsonObject.has("Result")) {
                    return jsonObject.get("Result").toString();
                }
                if (jsonObject != null && jsonObject.has("result")) {
                    return jsonObject.get("result").toString();
                }
            } catch (JSONException e1) {
                return jsonString;
            }
        }
        return jsonString;
    }

    protected boolean isJsonArray(String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            //it's array
            return true;
        } catch (JSONException e) {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                if (jsonObject != null && jsonObject.has("result")) {
                    return (jsonObject.get("result") instanceof JsonObject);
                }
            } catch (JSONException e1) {
                return false;
            }
        }
        return false;
    }
}
