package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import com.facebook.GraphRequest;
import com.facebook.appevents.internal.AppEventsLoggerUtility;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.Utility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class SessionEventsState {
    private final int MAX_ACCUMULATED_LOG_EVENTS = 1000;
    private List<AppEvent> accumulatedEvents = new ArrayList();
    private String anonymousAppDeviceGUID;
    private AttributionIdentifiers attributionIdentifiers;
    private List<AppEvent> inFlightEvents = new ArrayList();
    private int numSkippedEventsDueToFullBuffer;

    public SessionEventsState(AttributionIdentifiers identifiers, String anonymousGUID) {
        this.attributionIdentifiers = identifiers;
        this.anonymousAppDeviceGUID = anonymousGUID;
    }

    public synchronized void addEvent(AppEvent event) {
        if (this.accumulatedEvents.size() + this.inFlightEvents.size() >= 1000) {
            this.numSkippedEventsDueToFullBuffer++;
        } else {
            this.accumulatedEvents.add(event);
        }
    }

    public synchronized int getAccumulatedEventCount() {
        return this.accumulatedEvents.size();
    }

    public synchronized void clearInFlightAndStats(boolean moveToAccumulated) {
        if (moveToAccumulated) {
            this.accumulatedEvents.addAll(this.inFlightEvents);
        }
        this.inFlightEvents.clear();
        this.numSkippedEventsDueToFullBuffer = 0;
    }

    public int populateRequest(GraphRequest request, Context applicationContext, boolean includeImplicitEvents, boolean limitEventUsage) {
        synchronized (this) {
            int numSkipped = this.numSkippedEventsDueToFullBuffer;
            this.inFlightEvents.addAll(this.accumulatedEvents);
            this.accumulatedEvents.clear();
            JSONArray jsonArray = new JSONArray();
            for (AppEvent event : this.inFlightEvents) {
                if (!event.isChecksumValid()) {
                    Utility.logd("Event with invalid checksum: %s", event.toString());
                } else if (includeImplicitEvents || !event.getIsImplicit()) {
                    jsonArray.put(event.getJSONObject());
                }
            }
            if (jsonArray.length() == 0) {
                return 0;
            }
            populateRequest(request, applicationContext, numSkipped, jsonArray, limitEventUsage);
            return jsonArray.length();
        }
    }

    public synchronized List<AppEvent> getEventsToPersist() {
        List<AppEvent> result;
        result = this.accumulatedEvents;
        this.accumulatedEvents = new ArrayList();
        return result;
    }

    public synchronized void accumulatePersistedEvents(List<AppEvent> events) {
        this.accumulatedEvents.addAll(events);
    }

    private void populateRequest(GraphRequest request, Context applicationContext, int numSkipped, JSONArray events, boolean limitEventUsage) {
        JSONObject publishParams;
        try {
            publishParams = AppEventsLoggerUtility.getJSONObjectForGraphAPICall(AppEventsLoggerUtility.GraphAPIActivityType.CUSTOM_APP_EVENTS, this.attributionIdentifiers, this.anonymousAppDeviceGUID, limitEventUsage, applicationContext);
            if (this.numSkippedEventsDueToFullBuffer > 0) {
                publishParams.put("num_skipped_events", numSkipped);
            }
        } catch (JSONException e) {
            publishParams = new JSONObject();
        }
        request.setGraphObject(publishParams);
        Bundle requestParameters = request.getParameters();
        if (requestParameters == null) {
            requestParameters = new Bundle();
        }
        String jsonString = events.toString();
        if (jsonString != null) {
            requestParameters.putByteArray("custom_events_file", getStringAsByteArray(jsonString));
            request.setTag(jsonString);
        }
        request.setParameters(requestParameters);
    }

    private byte[] getStringAsByteArray(String jsonString) {
        try {
            return jsonString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Utility.logd("Encoding exception: ", (Exception) e);
            return null;
        }
    }
}
