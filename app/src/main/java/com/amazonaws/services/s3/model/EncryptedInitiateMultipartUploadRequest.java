package com.amazonaws.services.s3.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EncryptedInitiateMultipartUploadRequest extends InitiateMultipartUploadRequest implements MaterialsDescriptionProvider {
    private Map<String, String> materialsDescription;

    public EncryptedInitiateMultipartUploadRequest(String bucketName, String key) {
        super(bucketName, key);
    }

    public EncryptedInitiateMultipartUploadRequest(String bucketName, String key, ObjectMetadata objectMetadata) {
        super(bucketName, key, objectMetadata);
    }

    public Map<String, String> getMaterialsDescription() {
        return this.materialsDescription;
    }

    public void setMaterialsDescription(Map<String, String> materialsDescription2) {
        Map<String, String> unmodifiableMap;
        if (materialsDescription2 == null) {
            unmodifiableMap = null;
        } else {
            unmodifiableMap = Collections.unmodifiableMap(new HashMap(materialsDescription2));
        }
        this.materialsDescription = unmodifiableMap;
    }

    public EncryptedInitiateMultipartUploadRequest withMaterialsDescription(Map<String, String> materialsDescription2) {
        setMaterialsDescription(materialsDescription2);
        return this;
    }
}
