package com.tencent.msdk.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteFullException;
import android.os.Handler;
import android.os.HandlerThread;
import com.appsflyer.share.Constants;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import com.tencent.msdk.stat.a.d;
import com.tencent.msdk.stat.common.StatConstants;
import com.tencent.msdk.stat.common.StatLogger;
import com.tencent.msdk.stat.common.a;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.o;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class aj {
    /* access modifiers changed from: private */
    public static StatLogger h = j.b();
    private static Context i = null;
    private static int j = 307200;
    private static aj k = null;
    volatile int a = 0;
    a b = null;
    private ar c = null;
    private ar d = null;
    private Handler e = null;
    private String f = "";
    private String g = "";
    private int l = 0;
    private ConcurrentHashMap<d, String> m = null;
    private boolean n = false;
    private HashMap<String, String> o = new HashMap<>();

    private aj(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            this.e = new Handler(handlerThread.getLooper());
            i = context.getApplicationContext();
            this.m = new ConcurrentHashMap<>();
            this.f = j.q(context);
            this.g = "pri_" + j.q(context);
            this.c = new ar(i, this.f);
            this.d = new ar(i, this.g);
            f();
            b(true);
            b(false);
            g();
            b(i);
            d();
            k();
        } catch (Throwable th) {
            h.e(th);
        }
    }

    public static aj a(Context context) {
        if (k == null) {
            synchronized (aj.class) {
                if (k == null) {
                    k = new aj(context);
                }
            }
        }
        return k;
    }

    private String a(String str) {
        return StatConstants.MTA_DB2SP_TAG + str;
    }

    private String a(List<as> list) {
        StringBuilder sb = new StringBuilder(list.size() * 3);
        sb.append("event_id in (");
        int i2 = 0;
        int size = list.size();
        Iterator<as> it = list.iterator();
        while (true) {
            int i3 = i2;
            if (it.hasNext()) {
                sb.append(it.next().a);
                if (i3 != size - 1) {
                    sb.append(",");
                }
                i2 = i3 + 1;
            } else {
                sb.append(h.b);
                return sb.toString();
            }
        }
    }

    private synchronized void a(int i2, boolean z) {
        try {
            if (this.a > 0 && i2 > 0 && !StatServiceImpl.a()) {
                if (StatConfig.isDebugEnable()) {
                    h.i("Load " + this.a + " unsent events");
                }
                ArrayList arrayList = new ArrayList(i2);
                b(arrayList, i2, z);
                if (arrayList.size() > 0) {
                    if (StatConfig.isDebugEnable()) {
                        h.i("Peek " + arrayList.size() + " unsent events.");
                    }
                    a((List<as>) arrayList, 2, z);
                    k.b(i).b(arrayList, new ap(this, arrayList, z));
                }
            }
        } catch (Throwable th) {
            h.e(th);
        }
        return;
    }

    private void a(Context context, String str, int i2, long j2) {
        HashMap hashMap = new HashMap(4);
        hashMap.put(a(GGLiveConstants.PARAM.UID), str);
        hashMap.put(a("user_type"), Integer.valueOf(i2));
        hashMap.put(a("app_ver"), j.m(context));
        hashMap.put(a("ts"), Long.valueOf(j2));
        o.a(context, (Map<String, Object>) hashMap);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00f6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.tencent.msdk.stat.a.d r9, com.tencent.msdk.stat.j r10, boolean r11) {
        /*
            r8 = this;
            r4 = 0
            r1 = 0
            android.database.sqlite.SQLiteDatabase r1 = r8.d(r11)     // Catch:{ Throwable -> 0x00d1 }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00d1 }
            if (r11 != 0) goto L_0x002f
            int r0 = r8.a     // Catch:{ Throwable -> 0x00d1 }
            int r2 = com.tencent.msdk.stat.StatConfig.getMaxStoreEventCount()     // Catch:{ Throwable -> 0x00d1 }
            if (r0 <= r2) goto L_0x002f
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "Too many events stored in db."
            r0.warn(r2)     // Catch:{ Throwable -> 0x00d1 }
            int r0 = r8.a     // Catch:{ Throwable -> 0x00d1 }
            com.tencent.msdk.stat.ar r2 = r8.c     // Catch:{ Throwable -> 0x00d1 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r3 = "events"
            java.lang.String r6 = "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)"
            r7 = 0
            int r2 = r2.delete(r3, r6, r7)     // Catch:{ Throwable -> 0x00d1 }
            int r0 = r0 - r2
            r8.a = r0     // Catch:{ Throwable -> 0x00d1 }
        L_0x002f:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00d1 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = r9.g()     // Catch:{ Throwable -> 0x00d1 }
            boolean r3 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00d1 }
            if (r3 == 0) goto L_0x0056
            com.tencent.msdk.stat.common.StatLogger r3 = h     // Catch:{ Throwable -> 0x00d1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d1 }
            r6.<init>()     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r7 = "insert 1 event, content:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x00d1 }
            r3.i(r6)     // Catch:{ Throwable -> 0x00d1 }
        L_0x0056:
            java.lang.String r2 = com.tencent.msdk.stat.common.p.b((java.lang.String) r2)     // Catch:{ Throwable -> 0x00d1 }
            if (r2 == 0) goto L_0x0115
            int r3 = r2.length()     // Catch:{ Throwable -> 0x00d1 }
            int r6 = j     // Catch:{ Throwable -> 0x00d1 }
            if (r3 >= r6) goto L_0x0115
            java.lang.String r3 = "content"
            r0.put(r3, r2)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "send_count"
            java.lang.String r3 = "0"
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "status"
            r3 = 1
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ Throwable -> 0x00d1 }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "timestamp"
            long r6 = r9.c()     // Catch:{ Throwable -> 0x00d1 }
            java.lang.Long r3 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x00d1 }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00d1 }
            java.lang.String r2 = "events"
            r3 = 0
            long r2 = r1.insert(r2, r3, r0)     // Catch:{ Throwable -> 0x00d1 }
        L_0x008e:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00d1 }
            if (r1 == 0) goto L_0x0113
            r1.endTransaction()     // Catch:{ Throwable -> 0x00c9 }
            r0 = r2
        L_0x0097:
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x00f6
            int r0 = r8.a
            int r0 = r0 + 1
            r8.a = r0
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()
            if (r0 == 0) goto L_0x00c3
            com.tencent.msdk.stat.common.StatLogger r0 = h
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "directStoreEvent insert event to db, event:"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r9.g()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.d(r1)
        L_0x00c3:
            if (r10 == 0) goto L_0x00c8
            r10.a()
        L_0x00c8:
            return
        L_0x00c9:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            r0 = r2
            goto L_0x0097
        L_0x00d1:
            r0 = move-exception
            r2 = -1
            com.tencent.msdk.stat.common.StatLogger r6 = h     // Catch:{ all -> 0x00e8 }
            r6.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00e8 }
            if (r1 == 0) goto L_0x0113
            r1.endTransaction()     // Catch:{ Throwable -> 0x00e0 }
            r0 = r2
            goto L_0x0097
        L_0x00e0:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            r0 = r2
            goto L_0x0097
        L_0x00e8:
            r0 = move-exception
            if (r1 == 0) goto L_0x00ee
            r1.endTransaction()     // Catch:{ Throwable -> 0x00ef }
        L_0x00ee:
            throw r0
        L_0x00ef:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h
            r2.e((java.lang.Throwable) r1)
            goto L_0x00ee
        L_0x00f6:
            com.tencent.msdk.stat.common.StatLogger r0 = h
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to store event:"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r9.g()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.error((java.lang.Object) r1)
            goto L_0x00c8
        L_0x0113:
            r0 = r2
            goto L_0x0097
        L_0x0115:
            r2 = r4
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.a(com.tencent.msdk.stat.a.d, com.tencent.msdk.stat.j, boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00d8 A[SYNTHETIC, Splitter:B:40:0x00d8] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e9 A[SYNTHETIC, Splitter:B:48:0x00e9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.util.List<com.tencent.msdk.stat.as> r7, int r8, boolean r9) {
        /*
            r6 = this;
            r2 = 0
            monitor-enter(r6)
            int r0 = r7.size()     // Catch:{ all -> 0x008b }
            if (r0 != 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r6)
            return
        L_0x000a:
            int r3 = r6.c((boolean) r9)     // Catch:{ all -> 0x008b }
            android.database.sqlite.SQLiteDatabase r1 = r6.d(r9)     // Catch:{ Throwable -> 0x00f6, all -> 0x00e5 }
            r0 = 2
            if (r8 != r0) goto L_0x008e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d0 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r3 = "update events set status="
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r3 = ", send_count=send_count+1  where "
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r3 = r6.a((java.util.List<com.tencent.msdk.stat.as>) r7)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00d0 }
        L_0x0036:
            boolean r3 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00d0 }
            if (r3 == 0) goto L_0x0054
            com.tencent.msdk.stat.common.StatLogger r3 = h     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d0 }
            r4.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r5 = "update sql:"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x00d0 }
            r3.i(r4)     // Catch:{ Throwable -> 0x00d0 }
        L_0x0054:
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00d0 }
            r1.execSQL(r0)     // Catch:{ Throwable -> 0x00d0 }
            if (r2 == 0) goto L_0x007a
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d0 }
            r3.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = "update for delete sql:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00d0 }
            r0.i(r3)     // Catch:{ Throwable -> 0x00d0 }
            r1.execSQL(r2)     // Catch:{ Throwable -> 0x00d0 }
            r6.g()     // Catch:{ Throwable -> 0x00d0 }
        L_0x007a:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00d0 }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x0083 }
            goto L_0x0008
        L_0x0083:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x008b }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x008b }
            goto L_0x0008
        L_0x008b:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        L_0x008e:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d0 }
            r0.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = "update events set status="
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = " where "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = r6.a((java.util.List<com.tencent.msdk.stat.as>) r7)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00d0 }
            int r4 = r6.l     // Catch:{ Throwable -> 0x00d0 }
            int r4 = r4 % 3
            if (r4 != 0) goto L_0x00c8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d0 }
            r2.<init>()     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r4 = "delete from events where send_count>"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00d0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00d0 }
        L_0x00c8:
            int r3 = r6.l     // Catch:{ Throwable -> 0x00d0 }
            int r3 = r3 + 1
            r6.l = r3     // Catch:{ Throwable -> 0x00d0 }
            goto L_0x0036
        L_0x00d0:
            r0 = move-exception
        L_0x00d1:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00f4 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00f4 }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00dd }
            goto L_0x0008
        L_0x00dd:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x008b }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x008b }
            goto L_0x0008
        L_0x00e5:
            r0 = move-exception
            r1 = r2
        L_0x00e7:
            if (r1 == 0) goto L_0x00ec
            r1.endTransaction()     // Catch:{ Throwable -> 0x00ed }
        L_0x00ec:
            throw r0     // Catch:{ all -> 0x008b }
        L_0x00ed:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x008b }
            r2.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x008b }
            goto L_0x00ec
        L_0x00f4:
            r0 = move-exception
            goto L_0x00e7
        L_0x00f6:
            r0 = move-exception
            r1 = r2
            goto L_0x00d1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.a(java.util.List, int, boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ce, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        h.e(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f5, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f6, code lost:
        h.e(r1);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:25:0x00c9, B:44:0x00f1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.util.List<com.tencent.msdk.stat.as> r9, boolean r10) {
        /*
            r8 = this;
            r1 = 0
            monitor-enter(r8)
            int r0 = r9.size()     // Catch:{ all -> 0x00d6 }
            if (r0 != 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r8)
            return
        L_0x000a:
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x00d6 }
            if (r0 == 0) goto L_0x0036
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            r2.<init>()     // Catch:{ all -> 0x00d6 }
            java.lang.String r3 = "Delete "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00d6 }
            int r3 = r9.size()     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00d6 }
            java.lang.String r3 = " events, important:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00d6 }
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch:{ all -> 0x00d6 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00d6 }
            r0.i(r2)     // Catch:{ all -> 0x00d6 }
        L_0x0036:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d6 }
            int r0 = r9.size()     // Catch:{ all -> 0x00d6 }
            int r0 = r0 * 3
            r3.<init>(r0)     // Catch:{ all -> 0x00d6 }
            java.lang.String r0 = "event_id in ("
            r3.append(r0)     // Catch:{ all -> 0x00d6 }
            r0 = 0
            int r4 = r9.size()     // Catch:{ all -> 0x00d6 }
            java.util.Iterator r5 = r9.iterator()     // Catch:{ all -> 0x00d6 }
            r2 = r0
        L_0x0050:
            boolean r0 = r5.hasNext()     // Catch:{ all -> 0x00d6 }
            if (r0 == 0) goto L_0x006e
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00d6 }
            com.tencent.msdk.stat.as r0 = (com.tencent.msdk.stat.as) r0     // Catch:{ all -> 0x00d6 }
            long r6 = r0.a     // Catch:{ all -> 0x00d6 }
            r3.append(r6)     // Catch:{ all -> 0x00d6 }
            int r0 = r4 + -1
            if (r2 == r0) goto L_0x006a
            java.lang.String r0 = ","
            r3.append(r0)     // Catch:{ all -> 0x00d6 }
        L_0x006a:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x0050
        L_0x006e:
            java.lang.String r0 = ")"
            r3.append(r0)     // Catch:{ all -> 0x00d6 }
            android.database.sqlite.SQLiteDatabase r1 = r8.d(r10)     // Catch:{ Throwable -> 0x00d9 }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r0 = "events"
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x00d9 }
            r5 = 0
            int r0 = r1.delete(r0, r2, r5)     // Catch:{ Throwable -> 0x00d9 }
            boolean r2 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00d9 }
            if (r2 == 0) goto L_0x00bb
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ Throwable -> 0x00d9 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d9 }
            r5.<init>()     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r6 = "delete "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r5 = " event "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00d9 }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r4 = ", success delete:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ Throwable -> 0x00d9 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00d9 }
            r2.i(r3)     // Catch:{ Throwable -> 0x00d9 }
        L_0x00bb:
            int r2 = r8.a     // Catch:{ Throwable -> 0x00d9 }
            int r0 = r2 - r0
            r8.a = r0     // Catch:{ Throwable -> 0x00d9 }
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00d9 }
            r8.g()     // Catch:{ Throwable -> 0x00d9 }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0008
        L_0x00ce:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00d6 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00d6 }
            goto L_0x0008
        L_0x00d6:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x00d9:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00ee }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00ee }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00e6 }
            goto L_0x0008
        L_0x00e6:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00d6 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00d6 }
            goto L_0x0008
        L_0x00ee:
            r0 = move-exception
            if (r1 == 0) goto L_0x00f4
            r1.endTransaction()     // Catch:{ Throwable -> 0x00f5 }
        L_0x00f4:
            throw r0     // Catch:{ all -> 0x00d6 }
        L_0x00f5:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00d6 }
            r2.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x00d6 }
            goto L_0x00f4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.a(java.util.List, boolean):void");
    }

    private boolean a(boolean z) {
        try {
            SQLiteDatabase d2 = d(z);
            d2.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", APGlobalInfo.TestEnv);
            contentValues.put("send_count", "100");
            contentValues.put("status", Integer.toString(1));
            contentValues.put("timestamp", Long.valueOf(System.currentTimeMillis()));
            d2.insert("events", (String) null, contentValues);
            d2.setTransactionSuccessful();
            d2.endTransaction();
            int delete = d2.delete("events", "content = ?", new String[]{APGlobalInfo.TestEnv});
            Cursor query = d2.query("events", (String[]) null, "content=?", new String[]{APGlobalInfo.TestEnv}, (String) null, (String) null, (String) null, "1");
            int count = query.getCount();
            query.close();
            if (StatConfig.isDebugEnable()) {
                h.i("delNum=" + delete + ",queryNum=" + count);
            }
            if (delete == 0 || count > 0) {
                throw new SQLException("test delete error.");
            }
            if (StatConfig.isDebugEnable()) {
                String[] split = d2.getPath().split(Constants.URL_PATH_DELIMITER);
                h.i("test db passed, db name:" + split[split.length - 1]);
            }
            return true;
        } catch (SQLiteFullException e2) {
            h.warn("db is full, change to INSTANT");
            StatConfig.setReportEventsByOrder(false);
            StatConfig.setStatSendStrategy(StatReportStrategy.INSTANT);
            return true;
        } catch (Throwable th) {
            h.e(th);
            return false;
        }
    }

    public static aj b() {
        return k;
    }

    /* access modifiers changed from: private */
    public void b(int i2, boolean z) {
        int h2 = i2 == -1 ? !z ? h() : i() : i2;
        if (h2 > 0) {
            int sendPeriodMinutes = StatConfig.getSendPeriodMinutes() * 60 * StatConfig.getNumEventsCommitPerSec();
            if (h2 > sendPeriodMinutes && sendPeriodMinutes > 0) {
                h2 = sendPeriodMinutes;
            }
            int a2 = StatConfig.a();
            int i3 = h2 / a2;
            int i4 = h2 % a2;
            if (StatConfig.isDebugEnable()) {
                h.i("sentStoreEventsByDb sendNumbers=" + h2 + ",important=" + z + ",maxSendNumPerFor1Period=" + sendPeriodMinutes + ",maxCount=" + i3 + ",restNumbers=" + i4);
            }
            for (int i5 = 0; i5 < i3; i5++) {
                a(a2, z);
            }
            if (i4 > 0) {
                a(i4, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(d dVar, j jVar, boolean z, boolean z2) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            if (StatConfig.m <= 0 || z || z2) {
                a(dVar, jVar, z);
            } else if (StatConfig.m > 0) {
                if (StatConfig.isDebugEnable()) {
                    h.i("cacheEventsInMemory.size():" + this.m.size() + ",numEventsCachedInMemory:" + StatConfig.m + ",numStoredEvents:" + this.a);
                    h.i("cache event:" + dVar.g());
                }
                this.m.put(dVar, "");
                if (this.m.size() >= StatConfig.m) {
                    j();
                }
                if (jVar != null) {
                    if (this.m.size() > 0) {
                        j();
                    }
                    jVar.a();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0117 A[SYNTHETIC, Splitter:B:52:0x0117] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:20:0x00a2=Splitter:B:20:0x00a2, B:56:0x0123=Splitter:B:56:0x0123, B:54:0x011a=Splitter:B:54:0x011a} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(com.tencent.msdk.stat.h r14) {
        /*
            r13 = this;
            r9 = 1
            r10 = 0
            r8 = 0
            monitor-enter(r13)
            java.lang.String r11 = r14.a()     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r0 = com.tencent.msdk.stat.common.j.a((java.lang.String) r11)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            android.content.ContentValues r12 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            r12.<init>()     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r1 = "content"
            org.json.JSONObject r2 = r14.b     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            r12.put(r1, r2)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r1 = "md5sum"
            r12.put(r1, r0)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            r14.c = r0     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r0 = "version"
            int r1 = r14.d     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            r12.put(r0, r1)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
            java.lang.String r1 = "config"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0134, all -> 0x0113 }
        L_0x0040:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x00df }
            if (r0 == 0) goto L_0x0137
            r0 = 0
            int r0 = r1.getInt(r0)     // Catch:{ Throwable -> 0x00df }
            int r2 = r14.a     // Catch:{ Throwable -> 0x00df }
            if (r0 != r2) goto L_0x0040
            r0 = r9
        L_0x0050:
            com.tencent.msdk.stat.ar r2 = r13.c     // Catch:{ Throwable -> 0x00df }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x00df }
            r2.beginTransaction()     // Catch:{ Throwable -> 0x00df }
            if (r9 != r0) goto L_0x00ad
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x00df }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00df }
            java.lang.String r2 = "config"
            java.lang.String r3 = "type=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00df }
            r5 = 0
            int r6 = r14.a     // Catch:{ Throwable -> 0x00df }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00df }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00df }
            int r0 = r0.update(r2, r12, r3, r4)     // Catch:{ Throwable -> 0x00df }
            long r2 = (long) r0     // Catch:{ Throwable -> 0x00df }
        L_0x0076:
            r4 = -1
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x00c6
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00df }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00df }
            r2.<init>()     // Catch:{ Throwable -> 0x00df }
            java.lang.String r3 = "Failed to store cfg:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00df }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00df }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00df }
            r0.e((java.lang.Object) r2)     // Catch:{ Throwable -> 0x00df }
        L_0x0094:
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x00df }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00df }
            r0.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00df }
            if (r1 == 0) goto L_0x00a2
            r1.close()     // Catch:{ Throwable -> 0x00fe }
        L_0x00a2:
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x0105 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x0105 }
            r0.endTransaction()     // Catch:{ Throwable -> 0x0105 }
        L_0x00ab:
            monitor-exit(r13)
            return
        L_0x00ad:
            java.lang.String r0 = "type"
            int r2 = r14.a     // Catch:{ Throwable -> 0x00df }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x00df }
            r12.put(r0, r2)     // Catch:{ Throwable -> 0x00df }
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x00df }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00df }
            java.lang.String r2 = "config"
            r3 = 0
            long r2 = r0.insert(r2, r3, r12)     // Catch:{ Throwable -> 0x00df }
            goto L_0x0076
        L_0x00c6:
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00df }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00df }
            r2.<init>()     // Catch:{ Throwable -> 0x00df }
            java.lang.String r3 = "Sucessed to store cfg:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00df }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00df }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00df }
            r0.d(r2)     // Catch:{ Throwable -> 0x00df }
            goto L_0x0094
        L_0x00df:
            r0 = move-exception
        L_0x00e0:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0132 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0132 }
            if (r1 == 0) goto L_0x00ea
            r1.close()     // Catch:{ Throwable -> 0x010c }
        L_0x00ea:
            com.tencent.msdk.stat.ar r0 = r13.c     // Catch:{ Throwable -> 0x00f4 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00f4 }
            r0.endTransaction()     // Catch:{ Throwable -> 0x00f4 }
            goto L_0x00ab
        L_0x00f4:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00fb }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00fb }
            goto L_0x00ab
        L_0x00fb:
            r0 = move-exception
            monitor-exit(r13)
            throw r0
        L_0x00fe:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00fb }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00fb }
            goto L_0x00a2
        L_0x0105:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00fb }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00fb }
            goto L_0x00ab
        L_0x010c:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00fb }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00fb }
            goto L_0x00ea
        L_0x0113:
            r0 = move-exception
            r1 = r8
        L_0x0115:
            if (r1 == 0) goto L_0x011a
            r1.close()     // Catch:{ Throwable -> 0x0124 }
        L_0x011a:
            com.tencent.msdk.stat.ar r1 = r13.c     // Catch:{ Throwable -> 0x012b }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x012b }
            r1.endTransaction()     // Catch:{ Throwable -> 0x012b }
        L_0x0123:
            throw r0     // Catch:{ all -> 0x00fb }
        L_0x0124:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00fb }
            r2.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x00fb }
            goto L_0x011a
        L_0x012b:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00fb }
            r2.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x00fb }
            goto L_0x0123
        L_0x0132:
            r0 = move-exception
            goto L_0x0115
        L_0x0134:
            r0 = move-exception
            r1 = r8
            goto L_0x00e0
        L_0x0137:
            r0 = r10
            goto L_0x0050
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.b(com.tencent.msdk.stat.h):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a7 A[SYNTHETIC, Splitter:B:30:0x00a7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.List<com.tencent.msdk.stat.as> r11, int r12, boolean r13) {
        /*
            r10 = this;
            r9 = 0
            android.database.sqlite.SQLiteDatabase r0 = r10.e(r13)     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
            java.lang.String r1 = "events"
            r2 = 0
            java.lang.String r3 = "status=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
            r5 = 0
            r6 = 1
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r8 = java.lang.Integer.toString(r12)     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x00b7, all -> 0x00a3 }
        L_0x0020:
            boolean r0 = r7.moveToNext()     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            if (r0 == 0) goto L_0x008f
            r0 = 0
            long r2 = r7.getLong(r0)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r0 = 1
            java.lang.String r4 = r7.getString(r0)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            boolean r0 = com.tencent.msdk.stat.StatConfig.g     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            if (r0 != 0) goto L_0x0038
            java.lang.String r4 = com.tencent.msdk.stat.common.p.a((java.lang.String) r4)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
        L_0x0038:
            r0 = 2
            int r5 = r7.getInt(r0)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r0 = 3
            int r6 = r7.getInt(r0)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            com.tencent.msdk.stat.as r1 = new com.tencent.msdk.stat.as     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            if (r0 == 0) goto L_0x007e
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r4.<init>()     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.String r5 = "peek event, id="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.String r3 = ",send_count="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.String r3 = ",timestamp="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r3 = 4
            long r4 = r7.getLong(r3)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            r0.i(r2)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
        L_0x007e:
            r11.add(r1)     // Catch:{ Throwable -> 0x0082, all -> 0x00b2 }
            goto L_0x0020
        L_0x0082:
            r0 = move-exception
            r1 = r7
        L_0x0084:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00b4 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x00b4 }
            if (r1 == 0) goto L_0x008e
            r1.close()     // Catch:{ Throwable -> 0x009c }
        L_0x008e:
            return
        L_0x008f:
            if (r7 == 0) goto L_0x008e
            r7.close()     // Catch:{ Throwable -> 0x0095 }
            goto L_0x008e
        L_0x0095:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x008e
        L_0x009c:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x008e
        L_0x00a3:
            r0 = move-exception
            r7 = r9
        L_0x00a5:
            if (r7 == 0) goto L_0x00aa
            r7.close()     // Catch:{ Throwable -> 0x00ab }
        L_0x00aa:
            throw r0
        L_0x00ab:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h
            r2.e((java.lang.Throwable) r1)
            goto L_0x00aa
        L_0x00b2:
            r0 = move-exception
            goto L_0x00a5
        L_0x00b4:
            r0 = move-exception
            r7 = r1
            goto L_0x00a5
        L_0x00b7:
            r0 = move-exception
            r1 = r9
            goto L_0x0084
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.b(java.util.List, int, boolean):void");
    }

    private void b(boolean z) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            SQLiteDatabase d2 = d(z);
            d2.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("status", 1);
            int update = d2.update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            if (StatConfig.isDebugEnable()) {
                h.i("update " + update + " unsent events.");
            }
            d2.execSQL("delete from events where timestamp<" + ((System.currentTimeMillis() / 1000) - 604800) + "  or length(content) >" + j);
            if (d2 != null) {
                try {
                    d2.setTransactionSuccessful();
                } catch (Exception e2) {
                }
                try {
                    d2.endTransaction();
                } catch (Throwable th) {
                    h.e(th);
                }
            }
        } catch (Throwable th2) {
            h.e(th2);
        }
    }

    private int c(boolean z) {
        return !z ? StatConfig.getMaxSendRetryCount() : StatConfig.getMaxImportantDataSendRetryCount();
    }

    private boolean c(Context context) {
        return o.a(context, a(GGLiveConstants.PARAM.UID)) || o.a(context, a("user_type")) || o.a(context, a("app_ver")) || o.a(context, a("ts"));
    }

    private SQLiteDatabase d(boolean z) {
        return !z ? this.c.getWritableDatabase() : this.d.getWritableDatabase();
    }

    private SQLiteDatabase e(boolean z) {
        return !z ? this.c.getReadableDatabase() : this.d.getReadableDatabase();
    }

    private void f() {
        try {
            if (!a(false)) {
                h.warn("delete " + this.c.a + ", and create new one");
                this.c.a();
                this.c = new ar(i, this.f);
            }
            if (!a(true)) {
                h.warn("delete " + this.d.a + ", and create new one");
                this.d.a();
                this.d = new ar(i, this.g);
            }
        } catch (Throwable th) {
            h.e(th);
        }
    }

    private void g() {
        this.a = h() + i();
    }

    private int h() {
        try {
            return (int) DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), "events");
        } catch (Throwable th) {
            h.e(th);
            return 0;
        }
    }

    private int i() {
        try {
            return (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
        } catch (Throwable th) {
            h.e(th);
            return 0;
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0138, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        h.e(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0150, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x0151, code lost:
        h.e(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:45:0x0131, B:54:0x0149] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void j() {
        /*
            r9 = this;
            r1 = 0
            boolean r0 = r9.n
            if (r0 == 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            java.util.concurrent.ConcurrentHashMap<com.tencent.msdk.stat.a.d, java.lang.String> r2 = r9.m
            monitor-enter(r2)
            java.util.concurrent.ConcurrentHashMap<com.tencent.msdk.stat.a.d, java.lang.String> r0 = r9.m     // Catch:{ all -> 0x0013 }
            int r0 = r0.size()     // Catch:{ all -> 0x0013 }
            if (r0 != 0) goto L_0x0016
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            goto L_0x0005
        L_0x0013:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            throw r0
        L_0x0016:
            r0 = 1
            r9.n = r0     // Catch:{ all -> 0x0013 }
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x0055
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            r3.<init>()     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = "insert "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.util.concurrent.ConcurrentHashMap<com.tencent.msdk.stat.a.d, java.lang.String> r4 = r9.m     // Catch:{ all -> 0x0013 }
            int r4 = r4.size()     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = " events ,numEventsCachedInMemory:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            int r4 = com.tencent.msdk.stat.StatConfig.m     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = ",numStoredEvents:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            int r4 = r9.a     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0013 }
            r0.i(r3)     // Catch:{ all -> 0x0013 }
        L_0x0055:
            com.tencent.msdk.stat.ar r0 = r9.c     // Catch:{ Throwable -> 0x00dc }
            android.database.sqlite.SQLiteDatabase r1 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00dc }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00dc }
            java.util.concurrent.ConcurrentHashMap<com.tencent.msdk.stat.a.d, java.lang.String> r0 = r9.m     // Catch:{ Throwable -> 0x00dc }
            java.util.Set r0 = r0.entrySet()     // Catch:{ Throwable -> 0x00dc }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ Throwable -> 0x00dc }
        L_0x0068:
            boolean r0 = r3.hasNext()     // Catch:{ Throwable -> 0x00dc }
            if (r0 == 0) goto L_0x012c
            java.lang.Object r0 = r3.next()     // Catch:{ Throwable -> 0x00dc }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Throwable -> 0x00dc }
            java.lang.Object r0 = r0.getKey()     // Catch:{ Throwable -> 0x00dc }
            com.tencent.msdk.stat.a.d r0 = (com.tencent.msdk.stat.a.d) r0     // Catch:{ Throwable -> 0x00dc }
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00dc }
            r4.<init>()     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r5 = r0.g()     // Catch:{ Throwable -> 0x00dc }
            boolean r6 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00dc }
            if (r6 == 0) goto L_0x00a1
            com.tencent.msdk.stat.common.StatLogger r6 = h     // Catch:{ Throwable -> 0x00dc }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00dc }
            r7.<init>()     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r8 = "insert content:"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00dc }
            java.lang.StringBuilder r7 = r7.append(r5)     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00dc }
            r6.i(r7)     // Catch:{ Throwable -> 0x00dc }
        L_0x00a1:
            java.lang.String r5 = com.tencent.msdk.stat.common.p.b((java.lang.String) r5)     // Catch:{ Throwable -> 0x00dc }
            if (r5 == 0) goto L_0x00d8
            int r6 = r5.length()     // Catch:{ Throwable -> 0x00dc }
            int r7 = j     // Catch:{ Throwable -> 0x00dc }
            if (r6 >= r7) goto L_0x00d8
            java.lang.String r6 = "content"
            r4.put(r6, r5)     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r5 = "send_count"
            java.lang.String r6 = "0"
            r4.put(r5, r6)     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r5 = "status"
            r6 = 1
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00dc }
            r4.put(r5, r6)     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r5 = "timestamp"
            long r6 = r0.c()     // Catch:{ Throwable -> 0x00dc }
            java.lang.Long r0 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x00dc }
            r4.put(r5, r0)     // Catch:{ Throwable -> 0x00dc }
            java.lang.String r0 = "events"
            r5 = 0
            r1.insert(r0, r5, r4)     // Catch:{ Throwable -> 0x00dc }
        L_0x00d8:
            r3.remove()     // Catch:{ Throwable -> 0x00dc }
            goto L_0x0068
        L_0x00dc:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x0146 }
            r3.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0146 }
            if (r1 == 0) goto L_0x00ea
            r1.endTransaction()     // Catch:{ Throwable -> 0x013f }
            r9.g()     // Catch:{ Throwable -> 0x013f }
        L_0x00ea:
            r0 = 0
            r9.n = r0     // Catch:{ all -> 0x0013 }
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x0129
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            r1.<init>()     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = "after insert, cacheEventsInMemory.size():"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.util.concurrent.ConcurrentHashMap<com.tencent.msdk.stat.a.d, java.lang.String> r3 = r9.m     // Catch:{ all -> 0x0013 }
            int r3 = r3.size()     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = ",numEventsCachedInMemory:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            int r3 = com.tencent.msdk.stat.StatConfig.m     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = ",numStoredEvents:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            int r3 = r9.a     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0013 }
            r0.i(r1)     // Catch:{ all -> 0x0013 }
        L_0x0129:
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            goto L_0x0005
        L_0x012c:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00dc }
            if (r1 == 0) goto L_0x00ea
            r1.endTransaction()     // Catch:{ Throwable -> 0x0138 }
            r9.g()     // Catch:{ Throwable -> 0x0138 }
            goto L_0x00ea
        L_0x0138:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0013 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0013 }
            goto L_0x00ea
        L_0x013f:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0013 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0013 }
            goto L_0x00ea
        L_0x0146:
            r0 = move-exception
            if (r1 == 0) goto L_0x014f
            r1.endTransaction()     // Catch:{ Throwable -> 0x0150 }
            r9.g()     // Catch:{ Throwable -> 0x0150 }
        L_0x014f:
            throw r0     // Catch:{ all -> 0x0013 }
        L_0x0150:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x0013 }
            r3.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x0013 }
            goto L_0x014f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.j():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004d A[SYNTHETIC, Splitter:B:23:0x004d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r9 = this;
            r8 = 0
            com.tencent.msdk.stat.ar r0 = r9.c     // Catch:{ Throwable -> 0x005a, all -> 0x0049 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x005a, all -> 0x0049 }
            java.lang.String r1 = "keyvalues"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x005a, all -> 0x0049 }
        L_0x0013:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x0029 }
            if (r0 == 0) goto L_0x0035
            java.util.HashMap<java.lang.String, java.lang.String> r0 = r9.o     // Catch:{ Throwable -> 0x0029 }
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0029 }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x0029 }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x0029 }
            goto L_0x0013
        L_0x0029:
            r0 = move-exception
        L_0x002a:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0058 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ Throwable -> 0x0042 }
        L_0x0034:
            return
        L_0x0035:
            if (r1 == 0) goto L_0x0034
            r1.close()     // Catch:{ Throwable -> 0x003b }
            goto L_0x0034
        L_0x003b:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0034
        L_0x0042:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0034
        L_0x0049:
            r0 = move-exception
            r1 = r8
        L_0x004b:
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ Throwable -> 0x0051 }
        L_0x0050:
            throw r0
        L_0x0051:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h
            r2.e((java.lang.Throwable) r1)
            goto L_0x0050
        L_0x0058:
            r0 = move-exception
            goto L_0x004b
        L_0x005a:
            r0 = move-exception
            r1 = r8
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.k():void");
    }

    public int a() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public void a(int i2) {
        this.e.post(new aq(this, i2));
    }

    /* access modifiers changed from: package-private */
    public void a(d dVar, j jVar, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.post(new an(this, dVar, jVar, z, z2));
        }
    }

    /* access modifiers changed from: package-private */
    public void a(h hVar) {
        if (hVar != null) {
            this.e.post(new ao(this, hVar));
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<as> list, int i2, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.post(new ak(this, list, i2, z, z2));
        }
    }

    /* access modifiers changed from: package-private */
    public void a(List<as> list, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.post(new al(this, list, z, z2));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:127:0x0302 A[SYNTHETIC, Splitter:B:127:0x0302] */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x031c A[SYNTHETIC, Splitter:B:136:0x031c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.tencent.msdk.stat.common.a b(android.content.Context r15) {
        /*
            r14 = this;
            monitor-enter(r14)
            com.tencent.msdk.stat.common.a r0 = r14.b     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x0009
            com.tencent.msdk.stat.common.a r0 = r14.b     // Catch:{ all -> 0x02c6 }
        L_0x0007:
            monitor-exit(r14)
            return r0
        L_0x0009:
            boolean r0 = r14.c((android.content.Context) r15)     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x0118
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x001c
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x02c6 }
            java.lang.String r1 = "try to load user info from sp."
            r0.i(r1)     // Catch:{ all -> 0x02c6 }
        L_0x001c:
            java.lang.String r0 = ""
            java.lang.String r0 = "uid"
            java.lang.String r0 = r14.a((java.lang.String) r0)     // Catch:{ all -> 0x02c6 }
            java.lang.String r1 = ""
            java.lang.String r9 = com.tencent.msdk.stat.common.o.a((android.content.Context) r15, (java.lang.String) r0, (java.lang.String) r1)     // Catch:{ all -> 0x02c6 }
            java.lang.String r6 = com.tencent.msdk.stat.common.p.a((java.lang.String) r9)     // Catch:{ all -> 0x02c6 }
            java.lang.String r0 = "user_type"
            java.lang.String r0 = r14.a((java.lang.String) r0)     // Catch:{ all -> 0x02c6 }
            r1 = 1
            int r8 = com.tencent.msdk.stat.common.o.a((android.content.Context) r15, (java.lang.String) r0, (int) r1)     // Catch:{ all -> 0x02c6 }
            java.lang.String r0 = "app_ver"
            java.lang.String r0 = r14.a((java.lang.String) r0)     // Catch:{ all -> 0x02c6 }
            java.lang.String r1 = ""
            java.lang.String r0 = com.tencent.msdk.stat.common.o.a((android.content.Context) r15, (java.lang.String) r0, (java.lang.String) r1)     // Catch:{ all -> 0x02c6 }
            java.lang.String r1 = "ts"
            java.lang.String r1 = r14.a((java.lang.String) r1)     // Catch:{ all -> 0x02c6 }
            r2 = 0
            long r2 = com.tencent.msdk.stat.common.o.a((android.content.Context) r15, (java.lang.String) r1, (long) r2)     // Catch:{ all -> 0x02c6 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x02c6 }
            r10 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r10
            r1 = 1
            if (r8 == r1) goto L_0x0350
            r10 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r10
            java.lang.String r1 = com.tencent.msdk.stat.common.j.a((long) r2)     // Catch:{ all -> 0x02c6 }
            r2 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            java.lang.String r2 = com.tencent.msdk.stat.common.j.a((long) r2)     // Catch:{ all -> 0x02c6 }
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x02c6 }
            if (r1 != 0) goto L_0x0350
            r3 = 1
        L_0x0070:
            java.lang.String r1 = com.tencent.msdk.stat.common.j.m(r15)     // Catch:{ all -> 0x02c6 }
            boolean r0 = r0.equals(r1)     // Catch:{ all -> 0x02c6 }
            if (r0 != 0) goto L_0x007c
            r3 = r3 | 2
        L_0x007c:
            java.lang.String r0 = ","
            java.lang.String[] r10 = r6.split(r0)     // Catch:{ all -> 0x02c6 }
            r2 = 0
            if (r10 == 0) goto L_0x00ea
            int r0 = r10.length     // Catch:{ all -> 0x02c6 }
            if (r0 <= 0) goto L_0x00ea
            r0 = 0
            r1 = r10[r0]     // Catch:{ all -> 0x02c6 }
            if (r1 == 0) goto L_0x0095
            int r0 = r1.length()     // Catch:{ all -> 0x02c6 }
            r7 = 11
            if (r0 >= r7) goto L_0x034d
        L_0x0095:
            java.lang.String r0 = com.tencent.msdk.stat.common.p.a((android.content.Context) r15)     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x034a
            int r7 = r0.length()     // Catch:{ all -> 0x02c6 }
            r11 = 10
            if (r7 <= r11) goto L_0x034a
            r1 = 1
            r2 = r1
        L_0x00a5:
            r7 = r0
        L_0x00a6:
            if (r10 == 0) goto L_0x00f2
            int r0 = r10.length     // Catch:{ all -> 0x02c6 }
            r1 = 2
            if (r0 < r1) goto L_0x00f2
            r0 = 1
            r0 = r10[r0]     // Catch:{ all -> 0x02c6 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02c6 }
            r1.<init>()     // Catch:{ all -> 0x02c6 }
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ all -> 0x02c6 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r1 = r1.append(r6)     // Catch:{ all -> 0x02c6 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ all -> 0x02c6 }
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x02c6 }
            r1 = r2
        L_0x00c7:
            com.tencent.msdk.stat.common.a r2 = new com.tencent.msdk.stat.common.a     // Catch:{ all -> 0x02c6 }
            r2.<init>(r7, r0, r3)     // Catch:{ all -> 0x02c6 }
            r14.b = r2     // Catch:{ all -> 0x02c6 }
            java.lang.String r2 = com.tencent.msdk.stat.common.p.b((java.lang.String) r6)     // Catch:{ all -> 0x02c6 }
            if (r1 == 0) goto L_0x00df
            boolean r0 = r2.equals(r9)     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x00df
            r0 = r14
            r1 = r15
            r0.a((android.content.Context) r1, (java.lang.String) r2, (int) r3, (long) r4)     // Catch:{ all -> 0x02c6 }
        L_0x00df:
            if (r3 == r8) goto L_0x00e6
            r0 = r14
            r1 = r15
            r0.a((android.content.Context) r1, (java.lang.String) r2, (int) r3, (long) r4)     // Catch:{ all -> 0x02c6 }
        L_0x00e6:
            com.tencent.msdk.stat.common.a r0 = r14.b     // Catch:{ all -> 0x02c6 }
            goto L_0x0007
        L_0x00ea:
            java.lang.String r1 = com.tencent.msdk.stat.common.j.b((android.content.Context) r15)     // Catch:{ all -> 0x02c6 }
            r2 = 1
            r7 = r1
            r6 = r1
            goto L_0x00a6
        L_0x00f2:
            java.lang.String r0 = com.tencent.msdk.stat.common.j.c((android.content.Context) r15)     // Catch:{ all -> 0x02c6 }
            if (r0 == 0) goto L_0x0347
            int r1 = r0.length()     // Catch:{ all -> 0x02c6 }
            if (r1 <= 0) goto L_0x0347
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x02c6 }
            r1.<init>()     // Catch:{ all -> 0x02c6 }
            java.lang.StringBuilder r1 = r1.append(r7)     // Catch:{ all -> 0x02c6 }
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x02c6 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ all -> 0x02c6 }
            java.lang.String r2 = r1.toString()     // Catch:{ all -> 0x02c6 }
            r1 = 1
            r6 = r2
            goto L_0x00c7
        L_0x0118:
            r9 = 0
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            r0.beginTransaction()     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            boolean r0 = com.tencent.msdk.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            if (r0 == 0) goto L_0x012f
            com.tencent.msdk.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            java.lang.String r1 = "try to load user info from db."
            r0.i(r1)     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
        L_0x012f:
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            java.lang.String r1 = "user"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            android.database.Cursor r6 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x02f9, all -> 0x0318 }
            r0 = 0
            java.lang.String r1 = ""
            boolean r1 = r6.moveToNext()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r1 == 0) goto L_0x0233
            r0 = 0
            java.lang.String r11 = r6.getString(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = com.tencent.msdk.stat.common.p.a((java.lang.String) r11)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0 = 1
            int r10 = r6.getInt(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0 = 2
            java.lang.String r0 = r6.getString(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1 = 3
            long r2 = r6.getLong(r1)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r7 = 1
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r12 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r12
            r1 = 1
            if (r10 == r1) goto L_0x0344
            r12 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r12
            java.lang.String r1 = com.tencent.msdk.stat.common.j.a((long) r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r2 = 1000(0x3e8, double:4.94E-321)
            long r2 = r2 * r4
            java.lang.String r2 = com.tencent.msdk.stat.common.j.a((long) r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r1 != 0) goto L_0x0344
            r3 = 1
        L_0x0183:
            java.lang.String r1 = com.tencent.msdk.stat.common.j.m(r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            boolean r0 = r0.equals(r1)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r0 != 0) goto L_0x018f
            r3 = r3 | 2
        L_0x018f:
            java.lang.String r0 = ","
            java.lang.String[] r12 = r8.split(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r2 = 0
            if (r12 == 0) goto L_0x02c9
            int r0 = r12.length     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r0 <= 0) goto L_0x02c9
            r0 = 0
            r1 = r12[r0]     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r1 == 0) goto L_0x01a8
            int r0 = r1.length()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r9 = 11
            if (r0 >= r9) goto L_0x0341
        L_0x01a8:
            java.lang.String r0 = com.tencent.msdk.stat.common.p.a((android.content.Context) r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r0 == 0) goto L_0x033e
            int r9 = r0.length()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r13 = 10
            if (r9 <= r13) goto L_0x033e
            r1 = 1
            r2 = r1
        L_0x01b8:
            r9 = r0
        L_0x01b9:
            if (r12 == 0) goto L_0x02d2
            int r0 = r12.length     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1 = 2
            if (r0 < r1) goto L_0x02d2
            r0 = 1
            r0 = r12[r0]     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1.<init>()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r1 = r1.append(r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = ","
            java.lang.StringBuilder r1 = r1.append(r8)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1 = r2
        L_0x01da:
            com.tencent.msdk.stat.common.a r2 = new com.tencent.msdk.stat.common.a     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r2.<init>(r9, r0, r3)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r14.b = r2     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.<init>()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r2 = com.tencent.msdk.stat.common.p.b((java.lang.String) r8)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "uid"
            r0.put(r8, r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "user_type"
            java.lang.Integer r9 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.put(r8, r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "app_ver"
            java.lang.String r9 = com.tencent.msdk.stat.common.j.m(r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.put(r8, r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "ts"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.put(r8, r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r1 == 0) goto L_0x021f
            com.tencent.msdk.stat.ar r1 = r14.c     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "user"
            java.lang.String r9 = "uid=?"
            r12 = 1
            java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r13 = 0
            r12[r13] = r11     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1.update(r8, r0, r9, r12)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
        L_0x021f:
            if (r3 == r10) goto L_0x022d
            com.tencent.msdk.stat.ar r1 = r14.c     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = "user"
            r9 = 0
            r1.replace(r8, r9, r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
        L_0x022d:
            r0 = r14
            r1 = r15
            r0.a((android.content.Context) r1, (java.lang.String) r2, (int) r3, (long) r4)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0 = r7
        L_0x0233:
            if (r0 != 0) goto L_0x02a5
            java.lang.String r7 = com.tencent.msdk.stat.common.j.b((android.content.Context) r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r8 = com.tencent.msdk.stat.common.j.c((android.content.Context) r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r8 == 0) goto L_0x0338
            int r0 = r8.length()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r0 <= 0) goto L_0x0338
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.<init>()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r1 = ","
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
        L_0x025c:
            r3 = 0
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r10 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r10
            java.lang.String r1 = com.tencent.msdk.stat.common.j.m(r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.content.ContentValues r9 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r9.<init>()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r2 = com.tencent.msdk.stat.common.p.b((java.lang.String) r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r0 = "uid"
            r9.put(r0, r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r0 = "user_type"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r9.put(r0, r10)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r0 = "app_ver"
            r9.put(r0, r1)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r0 = "ts"
            java.lang.Long r1 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r9.put(r0, r1)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r1 = "user"
            r10 = 0
            r0.insert(r1, r10, r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0 = r14
            r1 = r15
            r0.a((android.content.Context) r1, (java.lang.String) r2, (int) r3, (long) r4)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            com.tencent.msdk.stat.common.a r0 = new com.tencent.msdk.stat.common.a     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.<init>(r7, r8, r3)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r14.b = r0     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
        L_0x02a5:
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r0.setTransactionSuccessful()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r6 == 0) goto L_0x02b3
            r6.close()     // Catch:{ Throwable -> 0x02be }
        L_0x02b3:
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x02be }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x02be }
            r0.endTransaction()     // Catch:{ Throwable -> 0x02be }
            goto L_0x00e6
        L_0x02be:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x02c6 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x02c6 }
            goto L_0x00e6
        L_0x02c6:
            r0 = move-exception
            monitor-exit(r14)
            throw r0
        L_0x02c9:
            java.lang.String r1 = com.tencent.msdk.stat.common.j.b((android.content.Context) r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r2 = 1
            r9 = r1
            r8 = r1
            goto L_0x01b9
        L_0x02d2:
            java.lang.String r0 = com.tencent.msdk.stat.common.j.c((android.content.Context) r15)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r0 == 0) goto L_0x033b
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            if (r1 <= 0) goto L_0x033b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1.<init>()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r1 = r1.append(r9)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            java.lang.String r2 = r1.toString()     // Catch:{ Throwable -> 0x0335, all -> 0x0330 }
            r1 = 1
            r8 = r2
            goto L_0x01da
        L_0x02f9:
            r0 = move-exception
            r1 = r9
        L_0x02fb:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0332 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0332 }
            if (r1 == 0) goto L_0x0305
            r1.close()     // Catch:{ Throwable -> 0x0310 }
        L_0x0305:
            com.tencent.msdk.stat.ar r0 = r14.c     // Catch:{ Throwable -> 0x0310 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x0310 }
            r0.endTransaction()     // Catch:{ Throwable -> 0x0310 }
            goto L_0x00e6
        L_0x0310:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x02c6 }
            r1.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x02c6 }
            goto L_0x00e6
        L_0x0318:
            r0 = move-exception
            r6 = r9
        L_0x031a:
            if (r6 == 0) goto L_0x031f
            r6.close()     // Catch:{ Throwable -> 0x0329 }
        L_0x031f:
            com.tencent.msdk.stat.ar r1 = r14.c     // Catch:{ Throwable -> 0x0329 }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Throwable -> 0x0329 }
            r1.endTransaction()     // Catch:{ Throwable -> 0x0329 }
        L_0x0328:
            throw r0     // Catch:{ all -> 0x02c6 }
        L_0x0329:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x02c6 }
            r2.e((java.lang.Throwable) r1)     // Catch:{ all -> 0x02c6 }
            goto L_0x0328
        L_0x0330:
            r0 = move-exception
            goto L_0x031a
        L_0x0332:
            r0 = move-exception
            r6 = r1
            goto L_0x031a
        L_0x0335:
            r0 = move-exception
            r1 = r6
            goto L_0x02fb
        L_0x0338:
            r0 = r7
            goto L_0x025c
        L_0x033b:
            r1 = r2
            goto L_0x01da
        L_0x033e:
            r0 = r1
            goto L_0x01b8
        L_0x0341:
            r9 = r1
            goto L_0x01b9
        L_0x0344:
            r3 = r10
            goto L_0x0183
        L_0x0347:
            r1 = r2
            goto L_0x00c7
        L_0x034a:
            r0 = r1
            goto L_0x00a5
        L_0x034d:
            r7 = r1
            goto L_0x00a6
        L_0x0350:
            r3 = r8
            goto L_0x0070
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.b(android.content.Context):com.tencent.msdk.stat.common.a");
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (StatConfig.isEnableStatService()) {
            try {
                this.e.post(new am(this));
            } catch (Throwable th) {
                h.e(th);
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0069 A[SYNTHETIC, Splitter:B:23:0x0069] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d() {
        /*
            r9 = this;
            r8 = 0
            com.tencent.msdk.stat.ar r0 = r9.c     // Catch:{ Throwable -> 0x0076, all -> 0x0065 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x0076, all -> 0x0065 }
            java.lang.String r1 = "config"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0076, all -> 0x0065 }
        L_0x0013:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x0045 }
            if (r0 == 0) goto L_0x0051
            r0 = 0
            int r0 = r1.getInt(r0)     // Catch:{ Throwable -> 0x0045 }
            r2 = 1
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0045 }
            r3 = 2
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x0045 }
            r4 = 3
            int r4 = r1.getInt(r4)     // Catch:{ Throwable -> 0x0045 }
            com.tencent.msdk.stat.h r5 = new com.tencent.msdk.stat.h     // Catch:{ Throwable -> 0x0045 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x0045 }
            r5.a = r0     // Catch:{ Throwable -> 0x0045 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0045 }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0045 }
            r5.b = r0     // Catch:{ Throwable -> 0x0045 }
            r5.c = r3     // Catch:{ Throwable -> 0x0045 }
            r5.d = r4     // Catch:{ Throwable -> 0x0045 }
            android.content.Context r0 = i     // Catch:{ Throwable -> 0x0045 }
            com.tencent.msdk.stat.StatConfig.a((android.content.Context) r0, (com.tencent.msdk.stat.h) r5)     // Catch:{ Throwable -> 0x0045 }
            goto L_0x0013
        L_0x0045:
            r0 = move-exception
        L_0x0046:
            com.tencent.msdk.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0074 }
            r2.e((java.lang.Throwable) r0)     // Catch:{ all -> 0x0074 }
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ Throwable -> 0x005e }
        L_0x0050:
            return
        L_0x0051:
            if (r1 == 0) goto L_0x0050
            r1.close()     // Catch:{ Throwable -> 0x0057 }
            goto L_0x0050
        L_0x0057:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0050
        L_0x005e:
            r0 = move-exception
            com.tencent.msdk.stat.common.StatLogger r1 = h
            r1.e((java.lang.Throwable) r0)
            goto L_0x0050
        L_0x0065:
            r0 = move-exception
            r1 = r8
        L_0x0067:
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ Throwable -> 0x006d }
        L_0x006c:
            throw r0
        L_0x006d:
            r1 = move-exception
            com.tencent.msdk.stat.common.StatLogger r2 = h
            r2.e((java.lang.Throwable) r1)
            goto L_0x006c
        L_0x0074:
            r0 = move-exception
            goto L_0x0067
        L_0x0076:
            r0 = move-exception
            r1 = r8
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.aj.d():void");
    }
}
