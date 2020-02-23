package com.amazonaws.services.dynamodbv2.model;

import com.tencent.imsdk.IMConfig;
import java.util.HashMap;
import java.util.Map;

public enum ReturnConsumedCapacity {
    INDEXES("INDEXES"),
    TOTAL("TOTAL"),
    NONE(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT);
    
    private static final Map<String, ReturnConsumedCapacity> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("INDEXES", INDEXES);
        enumMap.put("TOTAL", TOTAL);
        enumMap.put(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT, NONE);
    }

    private ReturnConsumedCapacity(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ReturnConsumedCapacity fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
