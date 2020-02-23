package com.garena.android.gpns.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public final class LocalStorage {
    public static final String ACK_MSG_LIST = "ACK_MSG_LIST";
    private static final String CONNECTION_ADDRESS = "CONNECTION_ADDRESS";
    private static final String CONNECTION_ID = "CONNECTION_ID";
    private static final String DEVICE_ID = "DEVICE_ID";
    private static final String LAST_REGION_REFRESH_TIME = "LAST_REGION_REFRESH_TIME";
    private static final String REGIONAL_SERVER_ADDRESS = "REGIONAL_SERVER_ADDRESS";

    public static void saveConnectionId(long connectionId) {
        SharedPreferenceStore.getInstance().putLong(CONNECTION_ID, connectionId);
    }

    public static long getConnectionId() {
        return SharedPreferenceStore.getInstance().getLong(CONNECTION_ID, -1);
    }

    public static void saveConnectionAddress(String connServerAddress) {
        SharedPreferenceStore.getInstance().putString(CONNECTION_ADDRESS, connServerAddress);
    }

    public static String getConnectionAddress() {
        return SharedPreferenceStore.getInstance().getString(CONNECTION_ADDRESS, "");
    }

    private LocalStorage() {
    }

    public static long getDeviceId(Context context) {
        return context.getSharedPreferences(SharedPreferenceStore.KEY, 0).getLong("DEVICE_ID", -1);
    }

    public static String getDeviceIdString(Context context) {
        long value = context.getSharedPreferences(SharedPreferenceStore.KEY, 0).getLong("DEVICE_ID", -1);
        return value <= 0 ? "" : String.valueOf(value);
    }

    public static void setDeviceId(Context context, long deviceId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SharedPreferenceStore.KEY, 0).edit();
        editor.putLong("DEVICE_ID", deviceId);
        editor.commit();
    }

    public static List<Integer> getAcknowledgedMsgList() {
        List<Integer> list = new ArrayList<>();
        for (String token : SharedPreferenceStore.getInstance().getString(ACK_MSG_LIST, "").split(";")) {
            if (!TextUtils.isEmpty(token)) {
                list.add(Integer.valueOf(Integer.parseInt(token)));
            }
        }
        return list;
    }

    public static void putAcknowledgedMsgList(List<Integer> list) {
        StringBuilder builder = new StringBuilder("");
        for (Integer intValue : list) {
            builder.append(intValue.intValue()).append(";");
        }
        SharedPreferenceStore.getInstance().putString(ACK_MSG_LIST, builder.toString());
    }

    public static long getRegionRequestTime() {
        return SharedPreferenceStore.getInstance().getLong(LAST_REGION_REFRESH_TIME, 0);
    }

    public static void updateRegionRequestTime(long timestamp) {
        SharedPreferenceStore.getInstance().putLong(LAST_REGION_REFRESH_TIME, timestamp);
    }

    public static void setAuthServerAddress(String region) {
        SharedPreferenceStore.getInstance().putString(REGIONAL_SERVER_ADDRESS, region);
    }

    public static String getAuthServerAddress() {
        return SharedPreferenceStore.getInstance().getString(REGIONAL_SERVER_ADDRESS, "gpushsg1.beetalkmobile.com:10086");
    }
}
