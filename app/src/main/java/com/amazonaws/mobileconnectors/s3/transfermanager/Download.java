package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.mobileconnectors.s3.transfermanager.exception.PauseException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;

@Deprecated
public interface Download extends Transfer {
    void abort() throws IOException;

    String getBucketName();

    String getKey();

    ObjectMetadata getObjectMetadata();

    PersistableDownload pause() throws PauseException;
}
