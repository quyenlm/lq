package com.tencent.imsdk.tool.etc;

public class KVPair {
    public String key = "";
    public String value = "";

    public KVPair() {
    }

    public KVPair(String key2, String value2) {
        this.key = key2;
        this.value = value2;
    }

    public String toString() {
        return "key : " + this.key + ", value : " + this.value;
    }
}
