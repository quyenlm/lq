package com.tencent.ieg.ntv.event;

public class EventMatchReserveUI extends Event {
    public static short RESERVE_TYPE_CANCEL = 0;
    public static short RESERVE_TYPE_REPLAY = 2;
    public static short RESERVE_TYPE_RESERVE = 1;
    public int matchId;
    public short optType;
    public String replayurl;

    public EventMatchReserveUI(short type, int mid) {
        this.optType = type;
        this.matchId = mid;
    }
}
