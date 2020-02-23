package com.amazonaws.services.s3.internal.crypto;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class MultipartUploadContext {
    private final String bucketName;
    private boolean hasFinalPartBeenSeen;
    private final String key;
    private Map<String, String> materialsDescription;

    protected MultipartUploadContext(String bucketName2, String key2) {
        this.bucketName = bucketName2;
        this.key = key2;
    }

    public final String getBucketName() {
        return this.bucketName;
    }

    public final String getKey() {
        return this.key;
    }

    public final boolean hasFinalPartBeenSeen() {
        return this.hasFinalPartBeenSeen;
    }

    public final void setHasFinalPartBeenSeen(boolean hasFinalPartBeenSeen2) {
        this.hasFinalPartBeenSeen = hasFinalPartBeenSeen2;
    }

    public final Map<String, String> getMaterialsDescription() {
        return this.materialsDescription;
    }

    public final void setMaterialsDescription(Map<String, String> materialsDescription2) {
        Map<String, String> unmodifiableMap;
        if (materialsDescription2 == null) {
            unmodifiableMap = null;
        } else {
            unmodifiableMap = Collections.unmodifiableMap(new HashMap(materialsDescription2));
        }
        this.materialsDescription = unmodifiableMap;
    }
}
