package com.vk.sdk.api.model;

import java.util.ArrayList;

public class VKScopes {
    public static final String ADS = "ads";
    public static final String AUDIO = "audio";
    public static final String DOCS = "docs";
    public static final String EMAIL = "email";
    public static final String FRIENDS = "friends";
    public static final String GROUPS = "groups";
    public static final String MESSAGES = "messages";
    public static final String NOHTTPS = "nohttps";
    public static final String NOTES = "notes";
    public static final String NOTIFICATIONS = "notifications";
    public static final String NOTIFY = "notify";
    public static final String OFFERS = "offers";
    public static final String OFFLINE = "offline";
    public static final String PAGES = "pages";
    public static final String PHOTOS = "photos";
    public static final String QUESTIONS = "questions";
    public static final String STATS = "stats";
    public static final String STATUS = "status";
    public static final String VIDEO = "video";
    public static final String WALL = "wall";

    private VKScopes() {
    }

    public static ArrayList<String> parse(int permissions) {
        ArrayList<String> result = new ArrayList<>();
        if ((permissions & 1) > 0) {
            result.add("notify");
        }
        if ((permissions & 2) > 0) {
            result.add("friends");
        }
        if ((permissions & 4) > 0) {
            result.add("photos");
        }
        if ((permissions & 8) > 0) {
            result.add("audio");
        }
        if ((permissions & 16) > 0) {
            result.add("video");
        }
        if ((permissions & 128) > 0) {
            result.add("pages");
        }
        if ((permissions & 1024) > 0) {
            result.add("status");
        }
        if ((permissions & 2048) > 0) {
            result.add("notes");
        }
        if ((permissions & 4096) > 0) {
            result.add("messages");
        }
        if ((permissions & 8192) > 0) {
            result.add("wall");
        }
        if ((32768 & permissions) > 0) {
            result.add("ads");
        }
        if ((65536 & permissions) > 0) {
            result.add("offline");
        }
        if ((131072 & permissions) > 0) {
            result.add("docs");
        }
        if ((262144 & permissions) > 0) {
            result.add("groups");
        }
        if ((524288 & permissions) > 0) {
            result.add("notifications");
        }
        if ((1048576 & permissions) > 0) {
            result.add("stats");
        }
        if ((4194304 & permissions) > 0) {
            result.add("email");
        }
        return result;
    }
}
