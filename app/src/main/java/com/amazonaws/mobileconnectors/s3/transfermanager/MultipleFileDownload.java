package com.amazonaws.mobileconnectors.s3.transfermanager;

import java.io.IOException;

@Deprecated
public interface MultipleFileDownload extends Transfer {
    void abort() throws IOException;

    String getBucketName();

    String getKeyPrefix();
}
