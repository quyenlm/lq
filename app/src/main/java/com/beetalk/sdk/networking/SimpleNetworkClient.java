package com.beetalk.sdk.networking;

import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.StringUtils;
import com.tencent.imsdk.tool.net.RequestParams;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleNetworkClient {
    private static final int REQUEST_OK = 200;
    private static SimpleNetworkClient __instance;

    private SimpleNetworkClient() {
    }

    public static SimpleNetworkClient getInstance() {
        if (__instance == null) {
            __instance = new SimpleNetworkClient();
        }
        return __instance;
    }

    public JSONObject makePostRequest(String urlString, JSONObject postJsonData) {
        return makePostRequest(urlString, (List<HttpParam>) null, postJsonData);
    }

    public JSONObject makePostRequest(String urlString, List<HttpParam> headers, JSONObject postJsonData) {
        String encodedData = postJsonData == null ? "" : postJsonData.toString();
        try {
            BBLogger.i("Making Json Request for url %s & params %s", urlString, encodedData);
            List<HttpParam> finalHeaders = new ArrayList<>();
            finalHeaders.add(new HttpParam("Content-Type", RequestParams.APPLICATION_JSON));
            if (headers != null) {
                finalHeaders.addAll(headers);
            }
            return makePostRequest(urlString, finalHeaders, encodedData);
        } catch (Exception e) {
            BBLogger.e(e);
            return null;
        }
    }

    public JSONObject makePostRequest(String urlString, Map<String, String> postData) {
        return makePostRequest(urlString, (List<HttpParam>) null, postData);
    }

    public JSONObject makePostRequest(String urlString, List<HttpParam> headers, Map<String, String> postData) {
        List<HttpParam> params = new ArrayList<>();
        if (postData != null && !postData.isEmpty()) {
            for (String key : postData.keySet()) {
                params.add(new HttpParam(key, sanitize(postData.get(key))));
            }
        }
        try {
            String encodedData = StringUtils.httpParamsToString(params);
            BBLogger.i("Making Request for url %s & params %s", urlString, encodedData);
            List<HttpParam> finalHeaders = new ArrayList<>();
            finalHeaders.add(new HttpParam("Content-Type", "application/x-www-form-urlencoded"));
            if (headers != null) {
                finalHeaders.addAll(headers);
            }
            return makePostRequest(urlString, finalHeaders, encodedData);
        } catch (Exception e) {
            BBLogger.e(e);
            return null;
        }
    }

    private JSONObject makePostRequest(String urlString, List<HttpParam> headers, String encodedData) {
        try {
            HttpURLConnection conn = HttpFactory.newConnection(urlString);
            if (conn == null) {
                return null;
            }
            conn.setRequestMethod("POST");
            if (headers != null) {
                for (HttpParam p : headers) {
                    conn.setRequestProperty(p.key, p.value);
                }
            }
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodedData);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            int requestCode = conn.getResponseCode();
            BBLogger.i("request code %d", Integer.valueOf(requestCode));
            JSONObject feedback = null;
            if (requestCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder total = new StringBuilder();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    total.append(line);
                }
                r.close();
                BBLogger.d(total.toString(), new Object[0]);
                try {
                    feedback = new JSONObject(total.toString());
                } catch (JSONException e) {
                    BBLogger.e(e);
                }
            }
            conn.disconnect();
            return feedback;
        } catch (IOException e2) {
            BBLogger.e(e2);
            return null;
        }
    }

    private String sanitize(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    public JSONObject makeGetRequest(String urlString, Map<String, String> getData) {
        try {
            List<HttpParam> params = new ArrayList<>();
            if (!getData.isEmpty()) {
                for (String key : getData.keySet()) {
                    params.add(new HttpParam(key, sanitize(getData.get(key))));
                }
            }
            String urlString2 = urlString + "?" + StringUtils.httpParamsToString(params);
            BBLogger.i("request url: %s", urlString2);
            HttpURLConnection conn = HttpFactory.newConnection(urlString2);
            if (conn == null) {
                return null;
            }
            conn.connect();
            InputStream is = conn.getInputStream();
            int requestCode = conn.getResponseCode();
            BBLogger.i("request code %d", Integer.valueOf(requestCode));
            JSONObject feedback = null;
            if (requestCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder total = new StringBuilder();
                while (true) {
                    String line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    total.append(line);
                }
                r.close();
                try {
                    feedback = new JSONObject(total.toString());
                } catch (JSONException e) {
                    BBLogger.e(e);
                }
            }
            conn.disconnect();
            return feedback;
        } catch (IOException e2) {
            BBLogger.e(e2);
            return null;
        }
    }
}
