package com.amazonaws.services.s3.model;

@Deprecated
public interface ProgressListener {
    void progressChanged(ProgressEvent progressEvent);
}
