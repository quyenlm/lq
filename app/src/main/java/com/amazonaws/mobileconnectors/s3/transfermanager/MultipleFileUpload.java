package com.amazonaws.mobileconnectors.s3.transfermanager;

import java.util.Collection;

@Deprecated
public interface MultipleFileUpload extends Transfer {
    String getBucketName();

    String getKeyPrefix();

    Collection<? extends Upload> getSubTransfers();
}
