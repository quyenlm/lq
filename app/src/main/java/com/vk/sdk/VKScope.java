package com.vk.sdk;

import java.util.ArrayList;

public class VKScope {
    public static final String ADS = "ads";
    public static final String AUDIO = "audio";
    public static final String DIRECT = "direct";
    public static final String DOCS = "docs";
    public static final String EMAIL = "email";
    public static final String FRIENDS = "friends";
    public static final String GROUPS = "groups";
    public static final String MESSAGES = "messages";
    public static final String NOHTTPS = "nohttps";
    public static final String NOTES = "notes";
    public static final String NOTIFICATIONS = "notifications";
    public static final String NOTIFY = "notify";
    public static final String OFFLINE = "offline";
    public static final String PAGES = "pages";
    public static final String PHOTOS = "photos";
    public static final String STATS = "stats";
    public static final String STATUS = "status";
    public static final String VIDEO = "video";
    public static final String WALL = "wall";

    public static ArrayList<String> parseVkPermissionsFromInteger(int permissionsValue) {
        ArrayList<String> res = new ArrayList<>();
        if ((permissionsValue & 1) > 0) {
            res.add("notify");
        }
        if ((permissionsValue & 2) > 0) {
            res.add("friends");
        }
        if ((permissionsValue & 4) > 0) {
            res.add("photos");
        }
        if ((permissionsValue & 8) > 0) {
            res.add("audio");
        }
        if ((permissionsValue & 16) > 0) {
            res.add("video");
        }
        if ((permissionsValue & 128) > 0) {
            res.add("pages");
        }
        if ((permissionsValue & 1024) > 0) {
            res.add("status");
        }
        if ((permissionsValue & 2048) > 0) {
            res.add("notes");
        }
        if ((permissionsValue & 4096) > 0) {
            res.add("messages");
        }
        if ((permissionsValue & 8192) > 0) {
            res.add("wall");
        }
        if ((32768 & permissionsValue) > 0) {
            res.add("ads");
        }
        if ((65536 & permissionsValue) > 0) {
            res.add("offline");
        }
        if ((131072 & permissionsValue) > 0) {
            res.add("docs");
        }
        if ((262144 & permissionsValue) > 0) {
            res.add("groups");
        }
        if ((524288 & permissionsValue) > 0) {
            res.add("notifications");
        }
        if ((1048576 & permissionsValue) > 0) {
            res.add("stats");
        }
        return res;
    }
}
