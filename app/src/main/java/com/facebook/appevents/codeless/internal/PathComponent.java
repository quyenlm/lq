package com.facebook.appevents.codeless.internal;

import org.json.JSONException;
import org.json.JSONObject;

public final class PathComponent {
    private static final String PATH_CLASS_NAME_KEY = "class_name";
    private static final String PATH_DESCRIPTION_KEY = "description";
    private static final String PATH_HINT_KEY = "hint";
    private static final String PATH_ID_KEY = "id";
    private static final String PATH_INDEX_KEY = "index";
    private static final String PATH_MATCH_BITMASK_KEY = "match_bitmask";
    private static final String PATH_TAG_KEY = "tag";
    private static final String PATH_TEXT_KEY = "text";
    public final String className;
    public final String description;
    public final String hint;
    public final int id;
    public final int index;
    public final int matchBitmask;
    public final String tag;
    public final String text;

    public enum MatchBitmaskType {
        ID(1),
        TEXT(2),
        TAG(4),
        DESCRIPTION(8),
        HINT(16);
        
        private final int value;

        private MatchBitmaskType(int value2) {
            this.value = value2;
        }

        public int getValue() {
            return this.value;
        }
    }

    PathComponent(JSONObject component) throws JSONException {
        this.className = component.getString(PATH_CLASS_NAME_KEY);
        this.index = component.optInt("index", -1);
        this.id = component.optInt("id");
        this.text = component.optString("text");
        this.tag = component.optString("tag");
        this.description = component.optString("description");
        this.hint = component.optString(PATH_HINT_KEY);
        this.matchBitmask = component.optInt(PATH_MATCH_BITMASK_KEY);
    }
}
