package com.amazonaws.services.s3.model;

public interface Grantee {
    String getIdentifier();

    String getTypeIdentifier();

    void setIdentifier(String str);
}
