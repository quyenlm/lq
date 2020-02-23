package com.facebook.appevents;

import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import com.facebook.LoggingBehavior;
import com.facebook.internal.Logger;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

class FacebookSDKJSInterface {
    private static final String PARAMETER_FBSDK_PIXEL_REFERRAL = "_fb_pixel_referral_id";
    private static final String PROTOCOL = "fbmq-0.1";
    public static final String TAG = FacebookSDKJSInterface.class.getSimpleName();
    private Context context;

    public FacebookSDKJSInterface(Context context2) {
        this.context = context2;
    }

    private static Bundle jsonToBundle(JSONObject jsonObject) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            bundle.putString(key, jsonObject.getString(key));
        }
        return bundle;
    }

    private static Bundle jsonStringToBundle(String jsonString) {
        try {
            return jsonToBundle(new JSONObject(jsonString));
        } catch (JSONException e) {
            return new Bundle();
        }
    }

    @JavascriptInterface
    public void sendEvent(String pixelId, String event_name, String jsonString) {
        if (pixelId == null) {
            Logger.log(LoggingBehavior.DEVELOPER_ERRORS, TAG, "Can't bridge an event without a referral Pixel ID. Check your webview Pixel configuration");
            return;
        }
        AppEventsLogger logger = AppEventsLogger.newLogger(this.context);
        Bundle parameters = jsonStringToBundle(jsonString);
        parameters.putString(PARAMETER_FBSDK_PIXEL_REFERRAL, pixelId);
        logger.logEvent(event_name, parameters);
    }

    @JavascriptInterface
    public String getProtocol() {
        return PROTOCOL;
    }
}
