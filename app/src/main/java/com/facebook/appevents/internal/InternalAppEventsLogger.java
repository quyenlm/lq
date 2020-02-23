package com.facebook.appevents.internal;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;

class InternalAppEventsLogger extends AppEventsLogger {
    InternalAppEventsLogger(String activityName, String applicationId, AccessToken accessToken) {
        super(activityName, applicationId, accessToken);
    }
}
