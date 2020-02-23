package com.amazonaws.mobileconnectors.s3.transfermanager;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.s3.transfermanager.exception.PauseException;
import com.amazonaws.mobileconnectors.s3.transfermanager.model.UploadResult;

@Deprecated
public interface Upload extends Transfer {
    void abort();

    PersistableUpload pause() throws PauseException;

    PauseResult<PersistableUpload> tryPause(boolean z);

    UploadResult waitForUploadResult() throws AmazonClientException, AmazonServiceException, InterruptedException;
}
