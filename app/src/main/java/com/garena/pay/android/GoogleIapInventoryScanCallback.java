package com.garena.pay.android;

import android.support.annotation.NonNull;
import com.garena.pay.android.data.GoogleIapItemInfo;
import java.util.List;

public interface GoogleIapInventoryScanCallback {
    void onResult(@NonNull List<Result> list);

    public static class Result {
        public final String error;
        public final GoogleIapItemInfo item;
        public final boolean success;

        public static Result success(GoogleIapItemInfo item2) {
            return new Result(true, item2, (String) null);
        }

        public static Result error(GoogleIapItemInfo item2, String error2) {
            return new Result(false, item2, error2);
        }

        private Result(boolean success2, GoogleIapItemInfo item2, String error2) {
            this.success = success2;
            this.item = item2;
            this.error = error2;
        }
    }
}
