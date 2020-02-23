package com.amazonaws.services.s3.model;

public enum StorageClass {
    Standard("STANDARD"),
    ReducedRedundancy("REDUCED_REDUNDANCY"),
    Glacier("GLACIER");
    
    private final String storageClassId;

    public static StorageClass fromValue(String s3StorageClassString) throws IllegalArgumentException {
        for (StorageClass storageClass : values()) {
            if (storageClass.toString().equals(s3StorageClassString)) {
                return storageClass;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + s3StorageClassString + " value!");
    }

    private StorageClass(String id) {
        this.storageClassId = id;
    }

    public String toString() {
        return this.storageClassId;
    }
}
