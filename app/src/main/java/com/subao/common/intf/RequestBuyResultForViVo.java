package com.subao.common.intf;

import android.support.annotation.NonNull;
import com.subao.common.e;

public class RequestBuyResultForViVo extends RequestBuyResult {
    @NonNull
    private final String accessKey;
    @NonNull
    private final String transNo;

    public RequestBuyResultForViVo(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull String str4) {
        super(str, str2);
        this.accessKey = str3;
        this.transNo = str4;
    }

    @NonNull
    public String getAccessKey() {
        return this.accessKey;
    }

    @NonNull
    public String getTransNo() {
        return this.transNo;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RequestBuyResultForViVo)) {
            return false;
        }
        RequestBuyResultForViVo requestBuyResultForViVo = (RequestBuyResultForViVo) obj;
        if (!isEquals(requestBuyResultForViVo) || !e.a(this.accessKey, requestBuyResultForViVo.accessKey) || !e.a(this.transNo, requestBuyResultForViVo.transNo)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (super.hashCode() ^ this.accessKey.hashCode()) ^ this.transNo.hashCode();
    }

    public String toString() {
        return String.format("[ResultForViVo: %s, accessKey=%s, transNo=%s]", new Object[]{super.toString(), this.accessKey, this.transNo});
    }
}
