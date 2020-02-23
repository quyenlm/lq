package com.amazonaws.services.dynamodbv2.model;

import com.tencent.imsdk.IMConfig;
import java.util.HashMap;
import java.util.Map;

public enum ReturnItemCollectionMetrics {
    SIZE("SIZE"),
    NONE(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT);
    
    private static final Map<String, ReturnItemCollectionMetrics> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("SIZE", SIZE);
        enumMap.put(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT, NONE);
    }

    private ReturnItemCollectionMetrics(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ReturnItemCollectionMetrics fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
