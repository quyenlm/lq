package com.huawei.iaware.sdk;

import android.os.Parcel;
import android.rms.iaware.IAwareSdkCore;

public class IAwareSdk {
    private static final int FIRST_ASYNC_CALL_TRANSACTION = 10001;
    private static final int FIRST_SYNC_CALL_TRANSACTION = 1;
    private static final int LAST_ASYNC_CALL_TRANSACTION = 16777215;
    private static final int LAST_SYNC_CALL_TRANSACTION = 10000;
    private static final int TRANSACTION_ASYNC_REPORT_DATA = 10001;
    private static final int TRANSACTION_REPORT_DATA = 1;

    public static void asyncReportData(int i, String str, long j) {
        reportData(i, str, true);
    }

    public static void reportData(int i, String str, long j) {
        reportData(i, str, false);
    }

    private static void reportData(int i, String str, boolean z) {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        obtain.writeInt(i);
        obtain.writeLong(System.currentTimeMillis());
        obtain.writeString(str);
        IAwareSdkCore.handleEvent(z ? 10001 : 1, obtain, obtain2, i);
        obtain2.recycle();
        obtain.recycle();
    }
}
