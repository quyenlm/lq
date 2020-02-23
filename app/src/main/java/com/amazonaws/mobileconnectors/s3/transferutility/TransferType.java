package com.amazonaws.mobileconnectors.s3.transferutility;

public enum TransferType {
    UPLOAD,
    DOWNLOAD,
    ANY;

    public static TransferType getType(String type) {
        if (type.equalsIgnoreCase(UPLOAD.toString())) {
            return UPLOAD;
        }
        if (type.equalsIgnoreCase(DOWNLOAD.toString())) {
            return DOWNLOAD;
        }
        if (type.equalsIgnoreCase(ANY.toString())) {
            return ANY;
        }
        throw new IllegalArgumentException("Type " + type + " is not a recognized type");
    }
}
