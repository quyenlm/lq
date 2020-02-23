package com.amazonaws.services.dynamodbv2.model;

import java.util.HashMap;
import java.util.Map;

public enum Select {
    ALL_ATTRIBUTES("ALL_ATTRIBUTES"),
    ALL_PROJECTED_ATTRIBUTES("ALL_PROJECTED_ATTRIBUTES"),
    SPECIFIC_ATTRIBUTES("SPECIFIC_ATTRIBUTES"),
    COUNT("COUNT");
    
    private static final Map<String, Select> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("ALL_ATTRIBUTES", ALL_ATTRIBUTES);
        enumMap.put("ALL_PROJECTED_ATTRIBUTES", ALL_PROJECTED_ATTRIBUTES);
        enumMap.put("SPECIFIC_ATTRIBUTES", SPECIFIC_ATTRIBUTES);
        enumMap.put("COUNT", COUNT);
    }

    private Select(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static Select fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
