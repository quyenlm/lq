package com.subao.common.intf;

import android.support.annotation.NonNull;
import com.subao.common.e;

public class RequestBuyResult {
    @NonNull
    private final String orderId;
    @NonNull
    private final String productId;

    public RequestBuyResult(@NonNull String str, @NonNull String str2) {
        this.productId = str;
        this.orderId = str2;
    }

    @NonNull
    public String getProductId() {
        return this.productId;
    }

    @NonNull
    public String getOrderId() {
        return this.orderId;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof RequestBuyResult) {
            return isEquals((RequestBuyResult) obj);
        }
        return false;
    }

    public boolean isEquals(RequestBuyResult requestBuyResult) {
        return e.a(this.productId, requestBuyResult.productId) && e.a(this.orderId, requestBuyResult.orderId);
    }

    public String toString() {
        return String.format("[p=%s, o=%s]", new Object[]{this.productId, this.orderId});
    }

    public int hashCode() {
        return this.productId.hashCode() ^ this.orderId.hashCode();
    }
}
