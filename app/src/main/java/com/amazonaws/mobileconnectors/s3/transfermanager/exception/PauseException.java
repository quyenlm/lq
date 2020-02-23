package com.amazonaws.mobileconnectors.s3.transfermanager.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobileconnectors.s3.transfermanager.PauseStatus;

@Deprecated
public class PauseException extends AmazonClientException {
    private static final long serialVersionUID = 1;
    private final PauseStatus status;

    public PauseException(PauseStatus status2) {
        super("Failed to pause operation; status=" + status2);
        if (status2 == null || status2 == PauseStatus.SUCCESS) {
            throw new IllegalArgumentException();
        }
        this.status = status2;
    }

    public PauseStatus getPauseStatus() {
        return this.status;
    }

    public boolean isRetryable() {
        return false;
    }
}
