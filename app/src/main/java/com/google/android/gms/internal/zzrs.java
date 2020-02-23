package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

@zzzn
public final class zzrs implements zzrd {
    private final Object mLock = new Object();
    private final Map<String, zzrt> zzJI = new HashMap();

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaka r6, java.util.Map<java.lang.String, java.lang.String> r7) {
        /*
            r5 = this;
            java.lang.String r0 = "id"
            java.lang.Object r0 = r7.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r1 = "fail"
            java.lang.Object r1 = r7.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "fail_reason"
            r7.get(r2)
            java.lang.String r2 = "result"
            java.lang.Object r2 = r7.get(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r4 = r5.mLock
            monitor-enter(r4)
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzrt> r3 = r5.zzJI     // Catch:{ all -> 0x0045 }
            java.lang.Object r3 = r3.remove(r0)     // Catch:{ all -> 0x0045 }
            com.google.android.gms.internal.zzrt r3 = (com.google.android.gms.internal.zzrt) r3     // Catch:{ all -> 0x0045 }
            if (r3 != 0) goto L_0x0048
            java.lang.String r1 = "Received result for unexpected method invocation: "
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x0045 }
            int r2 = r0.length()     // Catch:{ all -> 0x0045 }
            if (r2 == 0) goto L_0x003f
            java.lang.String r0 = r1.concat(r0)     // Catch:{ all -> 0x0045 }
        L_0x003a:
            com.google.android.gms.internal.zzafr.zzaT(r0)     // Catch:{ all -> 0x0045 }
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
        L_0x003e:
            return
        L_0x003f:
            java.lang.String r0 = new java.lang.String     // Catch:{ all -> 0x0045 }
            r0.<init>(r1)     // Catch:{ all -> 0x0045 }
            goto L_0x003a
        L_0x0045:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            throw r0
        L_0x0048:
            boolean r0 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0045 }
            if (r0 != 0) goto L_0x0050
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            goto L_0x003e
        L_0x0050:
            if (r2 != 0) goto L_0x0054
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            goto L_0x003e
        L_0x0054:
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x005b }
            r0.<init>(r2)     // Catch:{ JSONException -> 0x005b }
        L_0x0059:
            monitor-exit(r4)     // Catch:{ all -> 0x0045 }
            goto L_0x003e
        L_0x005b:
            r0 = move-exception
            r0.getMessage()     // Catch:{ all -> 0x0045 }
            goto L_0x0059
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrs.zza(com.google.android.gms.internal.zzaka, java.util.Map):void");
    }
}
