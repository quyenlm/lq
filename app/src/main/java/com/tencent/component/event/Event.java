package com.tencent.component.event;

import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 6)
public final class Event {
    private static final int MAX_POOL_SIZE = 50;
    private static Event sPool;
    private static int sPoolSize = 0;
    private static final Object sPoolSync = new Object();
    @PluginApi(since = 6)
    public EventRank eventRank;
    private int mRefrenceTimes = 0;
    Event next;
    @PluginApi(since = 6)
    public Object params;
    @PluginApi(since = 6)
    public EventSource source;
    @PluginApi(since = 6)
    public int what;

    @PluginApi(since = 6)
    public enum EventRank {
        NORMAL,
        SYSTEM,
        CORE
    }

    private Event() {
    }

    public String toString() {
        return "Event [what=" + this.what + ", source=" + this.source + ", params=" + this.params + ", eventRank=" + this.eventRank + "]";
    }

    static Event obtain() {
        return new Event();
    }

    static Event obtain(int what2, EventSource source2, Object params2, EventRank eventRank2) {
        Event e = obtain();
        e.what = what2;
        e.source = source2;
        e.params = params2;
        e.eventRank = eventRank2;
        e.mRefrenceTimes++;
        return e;
    }

    static Event obtain(int what2, EventSource source2, EventRank eventRank2) {
        return obtain(what2, source2, (Object) null, eventRank2);
    }

    public static Event obtain(int what2, EventSource source2) {
        return obtain(what2, source2, (Object) null, EventRank.NORMAL);
    }

    public void recycle() {
        int i = this.mRefrenceTimes - 1;
        this.mRefrenceTimes = i;
        if (i <= 0) {
            clearForRecycle();
            synchronized (sPoolSync) {
                if (sPoolSize < 50) {
                    this.next = sPool;
                    sPool = this;
                    sPoolSize++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearForRecycle() {
        this.what = 0;
        this.source = null;
        this.params = null;
        this.eventRank = null;
        this.mRefrenceTimes = 0;
    }

    public void retain() {
        this.mRefrenceTimes++;
    }
}
