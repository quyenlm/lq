package com.amazonaws.services.dynamodbv2.model;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;

public enum AttributeAction {
    ADD("ADD"),
    PUT(HttpPut.METHOD_NAME),
    DELETE(HttpDelete.METHOD_NAME);
    
    private static final Map<String, AttributeAction> enumMap = null;
    private String value;

    static {
        enumMap = new HashMap();
        enumMap.put("ADD", ADD);
        enumMap.put(HttpPut.METHOD_NAME, PUT);
        enumMap.put(HttpDelete.METHOD_NAME, DELETE);
    }

    private AttributeAction(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    public static AttributeAction fromValue(String value2) {
        if (value2 == null || value2.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        } else if (enumMap.containsKey(value2)) {
            return enumMap.get(value2);
        } else {
            throw new IllegalArgumentException("Cannot create enum from " + value2 + " value!");
        }
    }
}
