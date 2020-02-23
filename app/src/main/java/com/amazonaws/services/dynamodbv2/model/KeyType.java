package com.amazonaws.services.dynamodbv2.model;

import java.util.HashMap;
import java.util.Map;

public enum KeyType {
    HASH("HASH"),
    RANGE("RANGE");
    
    private static final Map<String, KeyType> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("HASH", HASH);
        enumMap.put("RANGE", RANGE);
    }

    private KeyType(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static KeyType fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
