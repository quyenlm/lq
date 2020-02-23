package com.amazonaws.mobileconnectors.s3.transferutility;

import com.amazonaws.services.s3.AmazonS3;
import java.util.HashMap;
import java.util.Map;

class S3ClientReference {
    private static Map<String, AmazonS3> map = new HashMap();

    S3ClientReference() {
    }

    public static void put(String key, AmazonS3 s3) {
        map.put(key, s3);
    }

    public static AmazonS3 get(String key) {
        return map.remove(key);
    }

    public static void clear() {
        map.clear();
    }
}
