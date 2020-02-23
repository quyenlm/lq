package com.facebook.appevents;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import java.io.Serializable;

class AccessTokenAppIdPair implements Serializable {
    private static final long serialVersionUID = 1;
    private final String accessTokenString;
    private final String applicationId;

    public AccessTokenAppIdPair(AccessToken accessToken) {
        this(accessToken.getToken(), FacebookSdk.getApplicationId());
    }

    public AccessTokenAppIdPair(String accessTokenString2, String applicationId2) {
        this.accessTokenString = Utility.isNullOrEmpty(accessTokenString2) ? null : accessTokenString2;
        this.applicationId = applicationId2;
    }

    public String getAccessTokenString() {
        return this.accessTokenString;
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.accessTokenString == null ? 0 : this.accessTokenString.hashCode();
        if (this.applicationId != null) {
            i = this.applicationId.hashCode();
        }
        return hashCode ^ i;
    }

    public boolean equals(Object o) {
        if (!(o instanceof AccessTokenAppIdPair)) {
            return false;
        }
        AccessTokenAppIdPair p = (AccessTokenAppIdPair) o;
        if (!Utility.areObjectsEqual(p.accessTokenString, this.accessTokenString) || !Utility.areObjectsEqual(p.applicationId, this.applicationId)) {
            return false;
        }
        return true;
    }

    static class SerializationProxyV1 implements Serializable {
        private static final long serialVersionUID = -2488473066578201069L;
        private final String accessTokenString;
        private final String appId;

        private SerializationProxyV1(String accessTokenString2, String appId2) {
            this.accessTokenString = accessTokenString2;
            this.appId = appId2;
        }

        private Object readResolve() {
            return new AccessTokenAppIdPair(this.accessTokenString, this.appId);
        }
    }

    private Object writeReplace() {
        return new SerializationProxyV1(this.accessTokenString, this.applicationId);
    }
}
