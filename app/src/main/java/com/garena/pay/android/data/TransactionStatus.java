package com.garena.pay.android.data;

import com.google.android.gms.games.GamesStatusCodes;

public enum TransactionStatus {
    CREATED(Integer.valueOf(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER)),
    OPENING(Integer.valueOf(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_MULTIPLAYER_TYPE)),
    OPENED(Integer.valueOf(GamesStatusCodes.STATUS_MULTIPLAYER_DISABLED)),
    PROCESSED(Integer.valueOf(GamesStatusCodes.STATUS_MULTIPLAYER_ERROR_INVALID_OPERATION)),
    CLOSED(6005),
    CLOSED_WITH_ERROR(6006);
    
    private int value;

    private TransactionStatus(Integer value2) {
        this.value = value2.intValue();
    }

    public Integer getValue() {
        return Integer.valueOf(this.value);
    }
}
