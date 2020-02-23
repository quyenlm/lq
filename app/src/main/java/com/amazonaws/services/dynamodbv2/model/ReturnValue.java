package com.amazonaws.services.dynamodbv2.model;

import com.tencent.imsdk.IMConfig;
import java.util.HashMap;
import java.util.Map;

public enum ReturnValue {
    NONE(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT),
    ALL_OLD("ALL_OLD"),
    UPDATED_OLD("UPDATED_OLD"),
    ALL_NEW("ALL_NEW"),
    UPDATED_NEW("UPDATED_NEW");
    
    private static final Map<String, ReturnValue> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put(IMConfig.SERVER_CERTIFICATE_FILE_DEFAULT, NONE);
        enumMap.put("ALL_OLD", ALL_OLD);
        enumMap.put("UPDATED_OLD", UPDATED_OLD);
        enumMap.put("ALL_NEW", ALL_NEW);
        enumMap.put("UPDATED_NEW", UPDATED_NEW);
    }

    private ReturnValue(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static ReturnValue fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
