package com.garena.android.push;

import android.app.NotificationManager;
import android.content.Context;
import com.garena.network.HTTPRequestParam;

public class PushClient {
    public static void cancelNotification(Context context, int id) {
        ((NotificationManager) context.getSystemService("notification")).cancel(id);
    }

    public static void cancelAll(Context context) {
        ((NotificationManager) context.getSystemService("notification")).cancelAll();
    }

    public static final class PushRequest {
        @HTTPRequestParam(fieldName = "account")
        public String account;
        @HTTPRequestParam(fieldName = "app_id")
        public Integer appId;
        @HTTPRequestParam(fieldName = "app_key")
        public String appKey;
        @HTTPRequestParam(fieldName = "device_type")
        public Integer deviceType;
        @HTTPRequestParam(fieldName = "notify_token")
        public String registrationId = "";
        @HTTPRequestParam(fieldName = "tags")
        public String tags;

        public PushRequest(String appKey2, Integer appId2, Integer deviceType2) {
            new PushRequest("", appKey2, appId2, deviceType2);
        }

        public PushRequest(String registrationId2, String appKey2, Integer appId2, Integer deviceType2) {
            this.registrationId = registrationId2;
            this.appKey = appKey2;
            this.appId = appId2;
            this.deviceType = deviceType2;
        }

        public PushRequest(String registrationId2, String appKey2, Integer appId2, Integer deviceType2, String tags2, String account2) {
            this.registrationId = registrationId2;
            this.appKey = appKey2;
            this.appId = appId2;
            this.deviceType = deviceType2;
            this.tags = tags2;
            this.account = account2;
        }
    }

    public static final class PushRequestBuilder {
        public String account;
        public Integer appId;
        public String appKey;
        public Integer deviceType;
        public String registrationId = "";
        public String tags;

        public PushRequestBuilder setTags(String tags2) {
            this.tags = tags2;
            return this;
        }

        public PushRequestBuilder setAccount(String account2) {
            this.account = account2;
            return this;
        }

        public PushRequestBuilder setAppKey(String appKey2) {
            this.appKey = appKey2;
            return this;
        }

        public PushRequestBuilder setAppId(Integer appId2) {
            this.appId = appId2;
            return this;
        }

        public PushRequestBuilder setDeviceType(Integer deviceType2) {
            this.deviceType = deviceType2;
            return this;
        }

        public PushRequestBuilder setRegistrationId(String id) {
            this.registrationId = id;
            return this;
        }

        public PushRequest build() {
            return new PushRequest(this.registrationId, this.appKey, this.appId, this.deviceType, this.tags, this.account);
        }
    }
}
