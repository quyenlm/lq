package com.garena.android.beepost.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

public class BeePostAPI {
    private static final int CONNECT_TIMEOUT = 60000;
    private static final String DeviceType = "2";
    private static final int READ_TIMEOUT = 60000;
    private static final int REQUEST_OK = 200;
    public static final int SERVICE_AUTO = 1;
    public static final int SERVICE_FCM = 4;
    public static final int SERVICE_GCM = 3;
    public static final int SERVICE_GPNS = 2;

    private BeePostAPI() {
    }

    public static void registerBeePost(Context context, String appId, String appKey, String account, String token, String gcmDefaultSender, int serviceType, String deviceId) {
        Intent registerBeePost = new Intent(context.getApplicationContext(), BeePostIntentService.class);
        registerBeePost.putExtra("app_id", appId);
        registerBeePost.putExtra("app_key", appKey);
        registerBeePost.putExtra("account", account);
        registerBeePost.putExtra("service_gcm_send_id", gcmDefaultSender);
        registerBeePost.putExtra(NotificationCompat.CATEGORY_SERVICE, serviceType);
        registerBeePost.putExtra("device_id", deviceId);
        if (!TextUtils.isEmpty(token)) {
            registerBeePost.putExtra("token", token);
        }
        context.startService(registerBeePost);
    }

    public static boolean submitToken(Context context, String appId, String appKey, String account, String token, int serviceType, String deviceId) throws IOException, JSONException {
        String salt = Security.generateNonce();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String notifyToken = Security.toHexString(token.getBytes("UTF-8"));
        String notifyType = String.valueOf(serviceType);
        StringBuilder data = new StringBuilder();
        addParams(data, "app_id", appId, false);
        addParams(data, "nonce", salt, false);
        addParams(data, "timestamp", timestamp, false);
        addParams(data, "device_type", "2", false);
        addParams(data, "device_id", deviceId, false);
        addParams(data, "notify_type", notifyType, false);
        addParams(data, "notify_token", notifyToken, false);
        addParams(data, "account", account, true);
        JSONObject jsonResponse = doPost(appKey, BeePostRuntimeConfig.getPushRegisterUrl(), data.toString());
        if (jsonResponse == null || !jsonResponse.has(GGLiveConstants.PARAM.RESULT) || jsonResponse.getInt(GGLiveConstants.PARAM.RESULT) != 0) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.i(BeePostRuntimeConfig.LOG_TAG, "push token registration failed");
            }
            return false;
        }
        if (BeePostRuntimeConfig.LogEnabled) {
            Log.i(BeePostRuntimeConfig.LOG_TAG, "push token registration completed");
        }
        return true;
    }

    public static boolean setTags(Context context, String appId, String appKey, String deviceId, String tags) throws IOException, JSONException {
        String salt = Security.generateNonce();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        StringBuilder data = new StringBuilder();
        addParams(data, "app_id", appId, false);
        addParams(data, "nonce", salt, false);
        addParams(data, "timestamp", timestamp, false);
        addParams(data, "device_id", deviceId, false);
        addParams(data, "tags", tags, true);
        JSONObject jsonResponse = doPost(appKey, BeePostRuntimeConfig.getSetTagsUrl(), data.toString());
        if (jsonResponse == null || !jsonResponse.has(GGLiveConstants.PARAM.RESULT) || jsonResponse.getInt(GGLiveConstants.PARAM.RESULT) != 0) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.i(BeePostRuntimeConfig.LOG_TAG, "set tags failed");
            }
            return false;
        } else if (!BeePostRuntimeConfig.LogEnabled) {
            return true;
        } else {
            Log.i(BeePostRuntimeConfig.LOG_TAG, "set tags success");
            return true;
        }
    }

    public static boolean deleteTags(Context context, String appId, String appKey, String deviceId, String tags) throws IOException, JSONException {
        String salt = Security.generateNonce();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        StringBuilder data = new StringBuilder();
        addParams(data, "app_id", appId, false);
        addParams(data, "nonce", salt, false);
        addParams(data, "timestamp", timestamp, false);
        addParams(data, "device_id", deviceId, false);
        addParams(data, "tags", tags, true);
        JSONObject jsonResponse = doPost(appKey, BeePostRuntimeConfig.getDeleteTagsUrl(), data.toString());
        if (jsonResponse == null || !jsonResponse.has(GGLiveConstants.PARAM.RESULT) || jsonResponse.getInt(GGLiveConstants.PARAM.RESULT) != 0) {
            if (BeePostRuntimeConfig.LogEnabled) {
                Log.i(BeePostRuntimeConfig.LOG_TAG, "delete tags failed");
            }
            return false;
        } else if (!BeePostRuntimeConfig.LogEnabled) {
            return true;
        } else {
            Log.i(BeePostRuntimeConfig.LOG_TAG, "delete tags success");
            return true;
        }
    }

    private static JSONObject doPost(String appKey, String urlStr, String params) throws IOException, JSONException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(60000);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        conn.setRequestProperty("Authorization", "Signature " + Security.getHTTPRequestSignature(appKey, urlStr, params));
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(params);
        writer.flush();
        writer.close();
        os.close();
        if (conn.getResponseCode() == 200) {
            return readJSONResponse(conn.getInputStream());
        }
        return null;
    }

    private static void addParams(StringBuilder builder, String key, String value, boolean isLast) throws UnsupportedEncodingException {
        if (builder != null && !TextUtils.isEmpty(key)) {
            builder.append(URLEncoder.encode(key, "UTF-8"));
            builder.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            builder.append(TextUtils.isEmpty(value) ? "" : URLEncoder.encode(value, "UTF-8"));
            if (!isLast) {
                builder.append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
        }
    }

    private static JSONObject readJSONResponse(InputStream is) throws IOException, JSONException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder content = new StringBuilder();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                break;
            }
            content.append(line);
        }
        r.close();
        if (BeePostRuntimeConfig.LogEnabled) {
            Log.d(BeePostRuntimeConfig.LOG_TAG, "remote response: " + content);
        }
        return new JSONObject(content.toString());
    }
}
