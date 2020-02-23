package com.tencent.imsdk.unity.stat;

import android.content.Context;
import android.content.res.AssetManager;
import com.tencent.imsdk.stat.api.IMStat;
import com.tencent.imsdk.stat.api.IMStatPlatInterface;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatHelper extends IMStat {

    public class StatParams {
        public static final String CRASH_REPORT_CHANNELS = "crashReport";
        public static final String EVENT_CHANNELS = "eventReport";
        public static final String EXCEPTION_REPORT_CHANNELS = "exceptionReport";
        public static final String HOST = "host";
        public static final String KEY = "key";
        public static final String PAY_CHANNELS = "purchaseReport";
        public static final String PORT = "port";
        public static final String TEST_SPEED_CHANNELS = "testSpeed";
        public static final String TRACK_EVENT_CHANNELS = "eventTrack";
        public static final String TRACK_PAGE_CHANNELS = "pageTrack";
        public static final String VALUE = "value";

        public StatParams() {
        }
    }

    public static String readAllText(String path) {
        new File(path);
        AssetManager am = UnityPlayer.currentActivity.getAssets();
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(am.open(path)));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                buffer.append(line);
            }
        } catch (IOException e) {
            IMLogger.e("read stat config file " + path + " error : " + e.getMessage());
        }
        return buffer.toString();
    }

    public static boolean initialize(String jsonArrayString) {
        Context currentContext = UnityPlayer.currentActivity;
        IMLogger.d("json array string : " + jsonArrayString);
        try {
            final JSONObject jsonObject = new JSONObject(jsonArrayString);
            IMLogger.d(jsonObject.toString());
            initialize(currentContext, new IMStatPlatInterface() {
                public String[] setEventReportChannels() {
                    if (!jsonObject.has(StatParams.EVENT_CHANNELS)) {
                        IMLogger.d("no eventReport is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.EVENT_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get stat event channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setPurchaseReportChannels() {
                    if (!jsonObject.has(StatParams.PAY_CHANNELS)) {
                        IMLogger.d("no purchaseReport is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.PAY_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get pay event channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setTrackEventReportChannels() {
                    if (!jsonObject.has(StatParams.TRACK_EVENT_CHANNELS)) {
                        IMLogger.d("no eventTrack is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.TRACK_EVENT_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get track event channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setTrackPageChannels() {
                    if (!jsonObject.has(StatParams.TRACK_PAGE_CHANNELS)) {
                        IMLogger.d("no pageTrack is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.TRACK_PAGE_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get track page channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setTestSpeedReportChannels() {
                    if (!jsonObject.has(StatParams.TRACK_PAGE_CHANNELS)) {
                        IMLogger.d("no pageTrack is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.TEST_SPEED_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get test speed channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setExceptionReportChannels() {
                    if (!jsonObject.has(StatParams.EXCEPTION_REPORT_CHANNELS)) {
                        IMLogger.d("no exceptionReport is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.EXCEPTION_REPORT_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get exception report channels : " + e.getMessage());
                        return new String[0];
                    }
                }

                public String[] setCrashReportChannels() {
                    if (!jsonObject.has(StatParams.CRASH_REPORT_CHANNELS)) {
                        IMLogger.d("no crashReport is set");
                        return new String[0];
                    }
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray(StatParams.CRASH_REPORT_CHANNELS);
                        String[] eventChannels = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            eventChannels[i] = jsonArray.getString(i);
                        }
                        return eventChannels;
                    } catch (Exception e) {
                        IMLogger.d("can not get crash report channels : " + e.getMessage());
                        return new String[0];
                    }
                }
            });
            return true;
        } catch (JSONException e) {
            IMLogger.e("stat channels json params error : " + e.getMessage());
            return false;
        } catch (Exception e2) {
            IMLogger.e("stat channels json params error : " + e2.getMessage());
            return false;
        }
    }

    public static String getFilter() {
        JSONArray jsonArray = new JSONArray(getChannelFilter());
        IMLogger.d("get filter : " + jsonArray.toString());
        return jsonArray.toString();
    }

    public static void setFilter(String jsonListString) {
        IMLogger.d("set filter : " + jsonListString);
        try {
            JSONArray jsonArray = new JSONArray(jsonListString);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
                IMLogger.d("add filter : " + jsonArray.getString(i));
            }
            setChannelFilter(list);
        } catch (JSONException e) {
            IMLogger.e("set filter channel error : " + e.getMessage());
        }
    }

    public static void reportEvent(String eventName, String jsonParamString, boolean isRealTime) {
        try {
            JSONArray jsonArray = new JSONArray(jsonParamString);
            HashMap<String, String> params = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                params.put(jsonObject.getString("key"), jsonObject.getString("value"));
            }
            reportEvent(eventName, params, isRealTime);
        } catch (JSONException e) {
            IMLogger.e("parse json params string error : " + e.getMessage());
        }
    }

    public static void speedTest(String jsonHostsString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonHostsString);
            ArrayList<String> paramsList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                paramsList.add(jsonArray.getString(i));
            }
            if (paramsList.size() > 0) {
                speedTest(paramsList);
            }
        } catch (JSONException e) {
            IMLogger.e("parse json params string error : " + e.getMessage());
        }
    }

    public static void speedTestWithPort(String jsonHostsString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonHostsString);
            HashMap<String, Integer> paramsMap = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has(StatParams.HOST) && jsonObject.has("port")) {
                    paramsMap.put(jsonObject.getString(StatParams.HOST), Integer.valueOf(jsonObject.getInt("port")));
                }
            }
            if (paramsMap.size() > 0) {
                speedTest(paramsMap);
            }
        } catch (JSONException e) {
            IMLogger.e("parse json params string error : " + e.getMessage());
        }
    }
}
