package com.tencent.qqgamemi.srp.aws.upload;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;

public enum UploadState {
    NULL(0),
    UPOADING(1),
    PAUSE(2),
    RESUME(3),
    SUCCESS(4),
    CANCEL(5),
    FAIL(6);
    
    private int value;

    private UploadState(int value2) {
        this.value = value2;
    }

    public int getValue() {
        return this.value;
    }

    public static UploadState fromInt(int value2) {
        for (UploadState state : values()) {
            if (state.getValue() == value2) {
                return state;
            }
        }
        return null;
    }

    public static UploadState changeToUploadState(TransferState state) {
        UploadState uploadState = NULL;
        switch (state) {
            case IN_PROGRESS:
                return UPOADING;
            case PAUSED:
                return PAUSE;
            case RESUMED_WAITING:
                return RESUME;
            case COMPLETED:
                return SUCCESS;
            case CANCELED:
                return CANCEL;
            case FAILED:
                return FAIL;
            case WAITING_FOR_NETWORK:
                return FAIL;
            default:
                return uploadState;
        }
    }
}
