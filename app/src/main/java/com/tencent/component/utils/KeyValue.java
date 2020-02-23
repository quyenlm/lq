package com.tencent.component.utils;

public class KeyValue {
    private String key;
    private Object value;

    public KeyValue(String key2, Object value2) {
        this.key = key2;
        this.value = value2;
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }
}
