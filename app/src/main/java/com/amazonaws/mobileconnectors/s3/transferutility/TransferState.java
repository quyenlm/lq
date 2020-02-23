package com.amazonaws.mobileconnectors.s3.transferutility;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public enum TransferState {
    WAITING,
    IN_PROGRESS,
    PAUSED,
    RESUMED_WAITING,
    COMPLETED,
    CANCELED,
    FAILED,
    WAITING_FOR_NETWORK,
    PART_COMPLETED,
    PENDING_CANCEL,
    PENDING_PAUSE,
    PENDING_NETWORK_DISCONNECT,
    UNKNOWN;
    
    private static final Map<String, TransferState> map = null;

    static {
        int i;
        map = new HashMap();
        for (TransferState state : values()) {
            map.put(state.toString(), state);
        }
    }

    public static TransferState getState(String stateAsString) {
        if (map.containsKey(stateAsString)) {
            return map.get(stateAsString);
        }
        Log.e("TransferState", "Unknown state " + stateAsString + " transfer will be have state set to UNKNOWN.");
        return UNKNOWN;
    }
}
