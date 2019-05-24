package com.hcmus.movieapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Requester extends Request<String> {
    private String mService = null;
    private String mEntry = null;
    private String mTrailingUrl = null;
    protected Object jsonRequest = null;//JSONObject or JSONArray
    private Response.Listener<String> mListener = null;
    private String currentSessionKey = null;

    public Requester(int method, String url, Object jsonRequest, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.jsonRequest = jsonRequest;
        this.mListener = listener;
        setupRequest();
    }

    public Requester(String service, String entry, int method, Object jsonRequest, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, null, errorListener);
        this.mService = service;
        this.mEntry = entry;
        this.jsonRequest = jsonRequest;
        this.mListener = listener;
        setupRequest();
    }

    protected void setupRequest() {
        setupRequest(30000);
    }

    public void setTrailingUrl(String mTrailingUrl) {
        this.mTrailingUrl = mTrailingUrl;
    }

    public void deliverError(VolleyError error) {
        if (isCanceled()) {
            return;
        }
        if (getErrorListener() != null) {
            getErrorListener().onErrorResponse(error);
        }
    }

    @Override
    protected void deliverResponse(String response) {
        if (isCanceled()) {
            return;
        }
        mListener.onResponse(response);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Requester(mService, mEntry, getMethod(), jsonRequest, getResponseListener(), getErrorListener());
    }

    public Response.Listener<String> getResponseListener() {
        return mListener;
    }

    protected void setupRequest(int timeoutMs) {
        RetryPolicy policy = new DefaultRetryPolicy(timeoutMs, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        setRetryPolicy(policy);
        setShouldCache(false);
    }

    @Override
    public byte[] getBody() {
        try {
            if (jsonRequest != null && !TextUtils.isEmpty(jsonRequest.toString())) {
                Log.d("Requester", "body:" + jsonRequest.toString());
                if (isPostOrPutMethod()) {
                    return trimSignedUrl(jsonRequest.toString()).getBytes();
                }
                return jsonRequest.toString().getBytes();
            }
            return super.getBody();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        return null;
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        HashMap headers = new HashMap();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    private String cachedKey = null;

    public String getCacheKey() {
        if (cachedKey == null) {
            cachedKey = getUrl();
        }
        return cachedKey;
    }

    private boolean isGetMethod() {
        if (getMethod() == Method.POST ||
                getMethod() == Method.PUT ||
                getMethod() == Method.DELETE) return false;
        return true;
    }

    private boolean isPostOrPutMethod() {
        if (getMethod() == Method.POST || getMethod() == Method.PUT) {
            return true;
        }
        return false;
    }

    public String trimSignedUrl(String trimSignedUrl) {
        String patternString = "(\\?Policy=[^\"]+\")";
        return trimSignedUrl.replaceAll(patternString, "\\\\\"");
    }

    @Override
    public String getUrl() {
        String key = "cloud.api.url." + mService + "." + mEntry;
        String url = AppManager.getInstance().getCloudUrl(key);
        //TODO: Implement access token of user here
        currentSessionKey = "";
        if (url != null) {
            try {
                if (mTrailingUrl != null) {
                    url = url + "/" + mTrailingUrl;
                }
                //TODO: Implement access token of user here
                String authenticationKey = "";
                if (isGetMethod()) {
                    if (!TextUtils.isEmpty(authenticationKey)) {
                        if (jsonRequest == JSONObject.NULL || jsonRequest == null) {
                            jsonRequest = new JSONObject();
                        }
                        ((JSONObject) jsonRequest).put("access_token", authenticationKey);
                    }
                    if (jsonRequest instanceof JSONObject) {
                        Map<String, Object> map = Utilities.jsonToMap(((JSONObject) jsonRequest));
                        String parameters = encodeParameters(map, "UTF-8");
                        url += "?" + parameters;
                    }
                } else if (!TextUtils.isEmpty(authenticationKey)) {
                    url += "?access_token=" + authenticationKey;
                }
                return url;
            } catch (JSONException jsonException) {

            } catch (Exception exception) {
            }
        }
        return super.getUrl();
    }

    private String encodeParameters(Map<String, Object> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));
                encodedParams.append('&');
            }
            if (encodedParams.length() > 0) {
                encodedParams.deleteCharAt(encodedParams.length() - 1);
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }
}
