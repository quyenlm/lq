package com.facebook.messenger;

import java.util.List;

public class MessengerThreadParams {
    public final String metadata;
    public final Origin origin;
    public final List<String> participants;
    public final String threadToken;

    public enum Origin {
        REPLY_FLOW,
        COMPOSE_FLOW,
        UNKNOWN
    }

    public MessengerThreadParams(Origin origin2, String threadToken2, String metadata2, List<String> participants2) {
        this.threadToken = threadToken2;
        this.metadata = metadata2;
        this.participants = participants2;
        this.origin = origin2;
    }
}
