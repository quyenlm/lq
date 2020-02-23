package com.subao.common.intf;

import android.support.annotation.NonNull;
import com.subao.common.e;

public class RequestBuyResultForSamSung extends RequestBuyResult {
    @NonNull
    private final String transId;

    public RequestBuyResultForSamSung(@NonNull String str, @NonNull String str2, @NonNull String str3) {
        super(str, str2);
        this.transId = str3;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof RequestBuyResultForSamSung)) {
            return false;
        }
        RequestBuyResultForSamSung requestBuyResultForSamSung = (RequestBuyResultForSamSung) obj;
        if (!isEquals(requestBuyResultForSamSung) || !e.a(this.transId, requestBuyResultForSamSung.transId)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return super.hashCode() ^ this.transId.hashCode();
    }

    public String toString() {
        return String.format("[ResultForSumSung: %s, transId=%s]", new Object[]{super.toString(), this.transId});
    }

    @NonNull
    public String getTransId() {
        return this.transId;
    }
}
