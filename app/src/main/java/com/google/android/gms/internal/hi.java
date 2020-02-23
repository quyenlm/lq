package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class hi {
    private static Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static Uri zzbUa = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static Pattern zzbUb = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static Pattern zzbUc = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    /* access modifiers changed from: private */
    public static final AtomicBoolean zzbUd = new AtomicBoolean();
    private static HashMap<String, String> zzbUe;
    private static HashMap<String, Boolean> zzbUf = new HashMap<>();
    private static HashMap<String, Integer> zzbUg = new HashMap<>();
    private static HashMap<String, Long> zzbUh = new HashMap<>();
    private static HashMap<String, Float> zzbUi = new HashMap<>();
    private static Object zzbUj;
    private static boolean zzbUk;
    private static String[] zzbUl = new String[0];

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        long j2 = 0;
        Object zzb = zzb(contentResolver);
        Long l = (Long) zza(zzbUh, str, 0L);
        if (l != null) {
            return l.longValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null) {
            try {
                long parseLong = Long.parseLong(zza);
                l = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException e) {
            }
        }
        HashMap<String, Long> hashMap = zzbUh;
        synchronized (hi.class) {
            if (zzb == zzbUj) {
                hashMap.put(str, l);
                zzbUe.remove(str);
            }
        }
        return j2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap<java.lang.String, T> r2, java.lang.String r3, T r4) {
        /*
            java.lang.Class<com.google.android.gms.internal.hi> r1 = com.google.android.gms.internal.hi.class
            monitor-enter(r1)
            boolean r0 = r2.containsKey(r3)     // Catch:{ all -> 0x0016 }
            if (r0 == 0) goto L_0x0013
            java.lang.Object r0 = r2.get(r3)     // Catch:{ all -> 0x0016 }
            if (r0 == 0) goto L_0x0011
        L_0x000f:
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
        L_0x0010:
            return r0
        L_0x0011:
            r0 = r4
            goto L_0x000f
        L_0x0013:
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
            r0 = 0
            goto L_0x0010
        L_0x0016:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.hi.zza(java.util.HashMap, java.lang.String, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        if (zzbUk == false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0038, code lost:
        if (zzbUe.isEmpty() == false) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        zzc(r9, zzbUl);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0045, code lost:
        if (zzbUe.containsKey(r10) == false) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0047, code lost:
        r0 = zzbUe.get(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004f, code lost:
        if (r0 == null) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
        r2 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        /*
            r8 = 1
            r3 = 0
            r2 = 0
            java.lang.Class<com.google.android.gms.internal.hi> r1 = com.google.android.gms.internal.hi.class
            monitor-enter(r1)
            zza(r9)     // Catch:{ all -> 0x0054 }
            java.lang.Object r6 = zzbUj     // Catch:{ all -> 0x0054 }
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUe     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.containsKey(r10)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0020
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUe     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x001e
            r2 = r0
        L_0x001e:
            monitor-exit(r1)     // Catch:{ all -> 0x0054 }
        L_0x001f:
            return r2
        L_0x0020:
            java.lang.String[] r4 = zzbUl     // Catch:{ all -> 0x0054 }
            int r5 = r4.length     // Catch:{ all -> 0x0054 }
            r0 = r3
        L_0x0024:
            if (r0 >= r5) goto L_0x005c
            r7 = r4[r0]     // Catch:{ all -> 0x0054 }
            boolean r7 = r10.startsWith(r7)     // Catch:{ all -> 0x0054 }
            if (r7 == 0) goto L_0x0059
            boolean r0 = zzbUk     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x003a
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUe     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0057
        L_0x003a:
            java.lang.String[] r0 = zzbUl     // Catch:{ all -> 0x0054 }
            zzc(r9, r0)     // Catch:{ all -> 0x0054 }
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUe     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.containsKey(r10)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0057
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzbUe     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0052
            r2 = r0
        L_0x0052:
            monitor-exit(r1)     // Catch:{ all -> 0x0054 }
            goto L_0x001f
        L_0x0054:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0054 }
            throw r0
        L_0x0057:
            monitor-exit(r1)     // Catch:{ all -> 0x0054 }
            goto L_0x001f
        L_0x0059:
            int r0 = r0 + 1
            goto L_0x0024
        L_0x005c:
            monitor-exit(r1)     // Catch:{ all -> 0x0054 }
            android.net.Uri r1 = CONTENT_URI
            java.lang.String[] r4 = new java.lang.String[r8]
            r4[r3] = r10
            r0 = r9
            r3 = r2
            r5 = r2
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)
            if (r1 == 0) goto L_0x0072
            boolean r0 = r1.moveToFirst()     // Catch:{ all -> 0x0097 }
            if (r0 != 0) goto L_0x007c
        L_0x0072:
            r0 = 0
            zza((java.lang.Object) r6, (java.lang.String) r10, (java.lang.String) r0)     // Catch:{ all -> 0x0097 }
            if (r1 == 0) goto L_0x001f
            r1.close()
            goto L_0x001f
        L_0x007c:
            r0 = 1
            java.lang.String r0 = r1.getString(r0)     // Catch:{ all -> 0x0097 }
            if (r0 == 0) goto L_0x008b
            r3 = 0
            boolean r3 = r0.equals(r3)     // Catch:{ all -> 0x0097 }
            if (r3 == 0) goto L_0x008b
            r0 = r2
        L_0x008b:
            zza((java.lang.Object) r6, (java.lang.String) r10, (java.lang.String) r0)     // Catch:{ all -> 0x0097 }
            if (r0 == 0) goto L_0x0091
            r2 = r0
        L_0x0091:
            if (r1 == 0) goto L_0x001f
            r1.close()
            goto L_0x001f
        L_0x0097:
            r0 = move-exception
            if (r1 == 0) goto L_0x009d
            r1.close()
        L_0x009d:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.hi.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzbUa, (String[]) null, (String) null, strArr, (String) null);
        TreeMap treeMap = new TreeMap();
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    treeMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbUe == null) {
            zzbUd.set(false);
            zzbUe = new HashMap<>();
            zzbUj = new Object();
            zzbUk = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new hj((Handler) null));
        } else if (zzbUd.getAndSet(false)) {
            zzbUe.clear();
            zzbUf.clear();
            zzbUg.clear();
            zzbUh.clear();
            zzbUi.clear();
            zzbUj = new Object();
            zzbUk = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (hi.class) {
            if (obj == zzbUj) {
                zzbUe.put(str, str2);
            }
        }
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (hi.class) {
            zza(contentResolver);
            obj = zzbUj;
        }
        return obj;
    }

    public static void zzb(ContentResolver contentResolver, String... strArr) {
        String[] strArr2;
        if (strArr.length != 0) {
            synchronized (hi.class) {
                zza(contentResolver);
                HashSet hashSet = new HashSet((((zzbUl.length + strArr.length) << 2) / 3) + 1);
                hashSet.addAll(Arrays.asList(zzbUl));
                ArrayList arrayList = new ArrayList();
                for (String str : strArr) {
                    if (hashSet.add(str)) {
                        arrayList.add(str);
                    }
                }
                if (arrayList.isEmpty()) {
                    strArr2 = new String[0];
                } else {
                    zzbUl = (String[]) hashSet.toArray(new String[hashSet.size()]);
                    strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
                if (!zzbUk || zzbUe.isEmpty()) {
                    zzc(contentResolver, zzbUl);
                } else if (strArr2.length != 0) {
                    zzc(contentResolver, strArr2);
                }
            }
        }
    }

    private static void zzc(ContentResolver contentResolver, String[] strArr) {
        zzbUe.putAll(zza(contentResolver, strArr));
        zzbUk = true;
    }
}
