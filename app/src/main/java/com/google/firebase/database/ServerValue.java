package com.google.firebase.database;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerValue {
    public static final Map<String, String> TIMESTAMP;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(".sv", "timestamp");
        TIMESTAMP = Collections.unmodifiableMap(hashMap);
    }
}
