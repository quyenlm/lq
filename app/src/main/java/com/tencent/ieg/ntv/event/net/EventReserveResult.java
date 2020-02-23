package com.tencent.ieg.ntv.event.net;

import com.tencent.ieg.ntv.event.Event;

public class EventReserveResult extends Event {
    public int mMatchId;
    public short mOptType;
    public short mResult;

    public EventReserveResult(short result, short optType, int matchId) {
        this.mResult = result;
        this.mOptType = optType;
        this.mMatchId = matchId;
    }
}
