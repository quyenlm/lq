package com.facebook.appevents;

import android.content.Context;
import com.facebook.FacebookSdk;
import com.facebook.internal.AttributionIdentifiers;
import java.util.HashMap;
import java.util.Set;

class AppEventCollection {
    private final HashMap<AccessTokenAppIdPair, SessionEventsState> stateMap = new HashMap<>();

    public synchronized void addPersistedEvents(PersistedEvents persistedEvents) {
        if (persistedEvents != null) {
            for (AccessTokenAppIdPair accessTokenAppIdPair : persistedEvents.keySet()) {
                SessionEventsState sessionEventsState = getSessionEventsState(accessTokenAppIdPair);
                for (AppEvent appEvent : persistedEvents.get(accessTokenAppIdPair)) {
                    sessionEventsState.addEvent(appEvent);
                }
            }
        }
    }

    public synchronized void addEvent(AccessTokenAppIdPair accessTokenAppIdPair, AppEvent appEvent) {
        getSessionEventsState(accessTokenAppIdPair).addEvent(appEvent);
    }

    public synchronized Set<AccessTokenAppIdPair> keySet() {
        return this.stateMap.keySet();
    }

    public synchronized SessionEventsState get(AccessTokenAppIdPair accessTokenAppIdPair) {
        return this.stateMap.get(accessTokenAppIdPair);
    }

    public synchronized int getEventCount() {
        int count;
        count = 0;
        for (SessionEventsState sessionEventsState : this.stateMap.values()) {
            count += sessionEventsState.getAccumulatedEventCount();
        }
        return count;
    }

    private synchronized SessionEventsState getSessionEventsState(AccessTokenAppIdPair accessTokenAppId) {
        SessionEventsState eventsState;
        eventsState = this.stateMap.get(accessTokenAppId);
        if (eventsState == null) {
            Context context = FacebookSdk.getApplicationContext();
            eventsState = new SessionEventsState(AttributionIdentifiers.getAttributionIdentifiers(context), AppEventsLogger.getAnonymousAppDeviceGUID(context));
        }
        this.stateMap.put(accessTokenAppId, eventsState);
        return eventsState;
    }
}
