package com.tencent.ieg.ntv.event;

import com.tencent.ieg.ntv.event.net.EventReserveResult;

public class EventReserveResultUI extends EventReserveResult {
    public int mStatus;
    public String mTagText;

    public EventReserveResultUI(short result, short optType, int matchId, String tagText, int status) {
        super(result, optType, matchId);
        this.mTagText = tagText;
        this.mStatus = status;
    }
}
