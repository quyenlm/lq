package com.tencent.component.event;

import com.tencent.component.annotation.PluginApi;
import com.tencent.component.event.Event;

@PluginApi(since = 6)
public class Observable {
    private EventSource eventSource;

    @PluginApi(since = 6)
    public Observable(String sourceName) {
        this.eventSource = new EventSource(sourceName, (Object) this);
    }

    @PluginApi(since = 6)
    public Observable() {
        this.eventSource = new EventSource(getClass(), (Object) this);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public void notify(Event event) {
        event.source = this.eventSource;
        EventCenter.getInstance().notify(event);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public void notifyNormal(int what, Object... params) {
        broadCastEvent(what, Event.EventRank.NORMAL, params);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public void notifyCore(int what, Object... params) {
        broadCastEvent(what, Event.EventRank.CORE, params);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public void notifySystem(int what, Object... params) {
        broadCastEvent(what, Event.EventRank.SYSTEM, params);
    }

    private void broadCastEvent(int what, Event.EventRank eventRank, Object... parameters) {
        EventCenter.getInstance().notify(this.eventSource, what, eventRank, parameters);
    }

    /* access modifiers changed from: protected */
    @PluginApi(since = 6)
    public EventSource getEventSource() {
        return this.eventSource;
    }
}
