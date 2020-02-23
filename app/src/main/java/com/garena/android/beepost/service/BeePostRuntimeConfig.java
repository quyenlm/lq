package com.garena.android.beepost.service;

public class BeePostRuntimeConfig {
    public static String LOG_TAG = "beepost-gpns";
    public static boolean LogEnabled = false;
    public static boolean SandboxMode = false;

    private static String getBaseUrl() {
        return SandboxMode ? "https://testbeepost.beetalkmobile.com" : "https://beepost.beetalkmobile.com";
    }

    protected static String getPushRegisterUrl() {
        return getBaseUrl() + "/api/device/register";
    }

    protected static String getSetTagsUrl() {
        return getBaseUrl() + "/api/device/tag/set";
    }

    protected static String getDeleteTagsUrl() {
        return getBaseUrl() + "/api/device/tag/delete";
    }
}
