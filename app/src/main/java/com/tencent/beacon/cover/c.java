package com.tencent.beacon.cover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.appsflyer.share.Constants;
import com.tencent.component.debug.TraceFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CompUpdate */
public final class c extends BroadcastReceiver implements Runnable {
    private static boolean e = false;
    private static int f = 0;
    private Context a;
    private List<a> b = null;
    private String c = null;
    private boolean d = false;

    public c(Context context) {
        this.a = context;
        if (context.getFilesDir() != null) {
            this.c = context.getFilesDir().getAbsolutePath() + File.separator + "beacon/comp";
        }
    }

    public final void onReceive(Context context, Intent intent) {
        NetworkInfo.State state;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity");
            if (connectivityManager == null) {
                g.a(TraceFormat.STR_WARN, "CompUpdate onReceive ConnectivityManager is null!", new Object[0]);
                return;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
            if (networkInfo != null) {
                state = networkInfo.getState();
            } else {
                state = null;
            }
            if (state != null && state == NetworkInfo.State.CONNECTED && this.b != null && f != 1 && !e) {
                new Thread(this).start();
            }
        } catch (Exception e2) {
            g.a(TraceFormat.STR_ERROR, "onReceive has a exception", new Object[0]);
        }
    }

    public final void a() {
        if (this.a != null && !this.d) {
            this.d = true;
            this.a.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public final void run() {
        b();
    }

    private synchronized void b() {
        if (this.b != null && this.b.size() > 0) {
            f = 1;
            if (g.g(this.a)) {
                if (g.c() >= 10485760) {
                    Iterator<a> it = this.b.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            e = true;
                            f = 2;
                            c();
                            break;
                        }
                        a next = it.next();
                        if (a(next)) {
                            String str = this.c + Constants.URL_PATH_DELIMITER + next.d + ".tmp";
                            boolean a2 = g.a(this.c + Constants.URL_PATH_DELIMITER + next.d + ".ziptmp", str);
                            if (!a2) {
                                g.a(TraceFormat.STR_ERROR, "unzip file failure: " + str, new Object[0]);
                            }
                            File file = new File(str);
                            if (file.exists()) {
                                next.f = (int) file.length();
                                if (next.g.equals(g.a(file))) {
                                    a2 = true;
                                }
                            }
                            if ("libBeacon.so".equals(next.d) || "5.jar".equals(next.d)) {
                                g.a(this.a);
                                continue;
                            }
                            if (!a2) {
                                f = 2;
                                break;
                            }
                        } else {
                            f = 2;
                            break;
                        }
                    }
                } else {
                    g.a(TraceFormat.STR_WARN, "Not enough storage, cancel!", new Object[0]);
                    e.a(this.a).b("Not enough storage");
                    e.a(this.a).a(true);
                    f = 2;
                }
            } else {
                g.a(TraceFormat.STR_WARN, "it's not on wifi stat, cancel!", new Object[0]);
                f = 2;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b8 A[SYNTHETIC, Splitter:B:40:0x00b8] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00bd A[Catch:{ Exception -> 0x016b }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c2 A[Catch:{ Exception -> 0x016b }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0131 A[SYNTHETIC, Splitter:B:69:0x0131] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0136 A[Catch:{ Exception -> 0x0160 }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x013b A[Catch:{ Exception -> 0x0160 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(com.tencent.beacon.cover.a r15) {
        /*
            r14 = this;
            r12 = 30000(0x7530, double:1.4822E-319)
            r3 = 0
            r1 = 0
            java.lang.String r0 = r15.e
            if (r0 == 0) goto L_0x015e
            java.lang.String r0 = ""
            java.lang.String r2 = r15.e
            java.lang.String r2 = r2.trim()
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x015e
            java.net.URL r7 = new java.net.URL     // Catch:{ Exception -> 0x00cb }
            java.lang.String r0 = r15.e     // Catch:{ Exception -> 0x00cb }
            r7.<init>(r0)     // Catch:{ Exception -> 0x00cb }
            r0 = r1
        L_0x001e:
            int r6 = r0 + 1
            r2 = 3
            if (r0 >= r2) goto L_0x015e
            java.net.URLConnection r0 = r7.openConnection()     // Catch:{ Exception -> 0x00e6 }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Exception -> 0x00e6 }
            java.lang.String r2 = "Accept-Encoding"
            java.lang.String r4 = "identity"
            r0.setRequestProperty(r2, r4)     // Catch:{ Exception -> 0x00e6 }
            java.lang.String r2 = "GET"
            r0.setRequestMethod(r2)     // Catch:{ Exception -> 0x00e6 }
            r2 = 0
            r0.setInstanceFollowRedirects(r2)     // Catch:{ Exception -> 0x00e6 }
            r2 = 20000(0x4e20, float:2.8026E-41)
            r0.setConnectTimeout(r2)     // Catch:{ Exception -> 0x00e6 }
            r2 = 30000(0x7530, float:4.2039E-41)
            r0.setReadTimeout(r2)     // Catch:{ Exception -> 0x00e6 }
            if (r0 == 0) goto L_0x00c5
            int r2 = r0.getResponseCode()     // Catch:{ Exception -> 0x013f }
            r4 = 200(0xc8, float:2.8E-43)
            if (r2 != r4) goto L_0x0141
            int r2 = r15.f     // Catch:{ Exception -> 0x013f }
            if (r2 <= 0) goto L_0x00c5
            int r2 = r15.f     // Catch:{ Exception -> 0x013f }
            int r4 = r0.getContentLength()     // Catch:{ Exception -> 0x013f }
            if (r2 != r4) goto L_0x00c5
            java.io.InputStream r4 = r0.getInputStream()     // Catch:{ Exception -> 0x016e, all -> 0x012b }
            if (r4 == 0) goto L_0x0123
            java.lang.String r0 = r0.getContentEncoding()     // Catch:{ Exception -> 0x0174, all -> 0x0162 }
            if (r0 == 0) goto L_0x00ed
            java.lang.String r2 = "gzip"
            boolean r0 = r0.contains(r2)     // Catch:{ Exception -> 0x0174, all -> 0x0162 }
            if (r0 == 0) goto L_0x00ed
            java.util.zip.GZIPInputStream r2 = new java.util.zip.GZIPInputStream     // Catch:{ Exception -> 0x0174, all -> 0x0162 }
            r2.<init>(r4)     // Catch:{ Exception -> 0x0174, all -> 0x0162 }
        L_0x0072:
            r0 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.io.File r8 = new java.io.File     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.String r9 = r14.c     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            r10.<init>()     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.String r11 = r15.d     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.String r11 = ".ziptmp"
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            r8.<init>(r9, r10)     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
            r5.<init>(r8)     // Catch:{ Exception -> 0x0179, all -> 0x0166 }
        L_0x0097:
            r8 = 0
            r9 = 4096(0x1000, float:5.74E-42)
            int r8 = r2.read(r0, r8, r9)     // Catch:{ Exception -> 0x00a8 }
            if (r8 <= 0) goto L_0x00ef
            r9 = 0
            r5.write(r0, r9, r8)     // Catch:{ Exception -> 0x00a8 }
            r5.flush()     // Catch:{ Exception -> 0x00a8 }
            goto L_0x0097
        L_0x00a8:
            r0 = move-exception
        L_0x00a9:
            r0.printStackTrace()     // Catch:{ all -> 0x0169 }
            java.lang.String r0 = "E"
            java.lang.String r8 = "read InputStream error!"
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x0169 }
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r8, (java.lang.Object[]) r9)     // Catch:{ all -> 0x0169 }
            if (r2 == 0) goto L_0x00bb
            r2.close()     // Catch:{ Exception -> 0x016b }
        L_0x00bb:
            if (r4 == 0) goto L_0x00c0
            r4.close()     // Catch:{ Exception -> 0x016b }
        L_0x00c0:
            if (r5 == 0) goto L_0x00c5
            r5.close()     // Catch:{ Exception -> 0x016b }
        L_0x00c5:
            com.tencent.beacon.cover.g.a((long) r12)
            r0 = r6
            goto L_0x001e
        L_0x00cb:
            r0 = move-exception
            java.lang.String r0 = "W"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "download url is error! "
            r2.<init>(r3)
            java.lang.String r3 = r15.e
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r2, (java.lang.Object[]) r3)
            r0 = r1
        L_0x00e5:
            return r0
        L_0x00e6:
            r0 = move-exception
            com.tencent.beacon.cover.g.a((long) r12)
            r0 = r6
            goto L_0x001e
        L_0x00ed:
            r2 = r4
            goto L_0x0072
        L_0x00ef:
            java.lang.String r0 = "D"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r9 = "down load file:"
            r8.<init>(r9)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r9 = r14.c     // Catch:{ Exception -> 0x00a8 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r9 = r15.d     // Catch:{ Exception -> 0x00a8 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r9 = ".ziptmp"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x00a8 }
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x00a8 }
            com.tencent.beacon.cover.g.a((java.lang.String) r0, (java.lang.String) r8, (java.lang.Object[]) r9)     // Catch:{ Exception -> 0x00a8 }
            if (r2 == 0) goto L_0x0119
            r2.close()     // Catch:{ Exception -> 0x017d }
        L_0x0119:
            if (r4 == 0) goto L_0x011e
            r4.close()     // Catch:{ Exception -> 0x017d }
        L_0x011e:
            r5.close()     // Catch:{ Exception -> 0x017d }
        L_0x0121:
            r0 = 1
            goto L_0x00e5
        L_0x0123:
            if (r4 == 0) goto L_0x00c5
            r4.close()     // Catch:{ Exception -> 0x0129 }
            goto L_0x00c5
        L_0x0129:
            r0 = move-exception
            goto L_0x00c5
        L_0x012b:
            r0 = move-exception
            r2 = r3
            r4 = r3
            r5 = r3
        L_0x012f:
            if (r2 == 0) goto L_0x0134
            r2.close()     // Catch:{ Exception -> 0x0160 }
        L_0x0134:
            if (r4 == 0) goto L_0x0139
            r4.close()     // Catch:{ Exception -> 0x0160 }
        L_0x0139:
            if (r5 == 0) goto L_0x013e
            r5.close()     // Catch:{ Exception -> 0x0160 }
        L_0x013e:
            throw r0     // Catch:{ Exception -> 0x013f }
        L_0x013f:
            r0 = move-exception
            goto L_0x00c5
        L_0x0141:
            java.lang.String r2 = "E"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x013f }
            java.lang.String r5 = "http response code: "
            r4.<init>(r5)     // Catch:{ Exception -> 0x013f }
            int r0 = r0.getResponseCode()     // Catch:{ Exception -> 0x013f }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ Exception -> 0x013f }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x013f }
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x013f }
            com.tencent.beacon.cover.g.a((java.lang.String) r2, (java.lang.String) r0, (java.lang.Object[]) r4)     // Catch:{ Exception -> 0x013f }
            goto L_0x00c5
        L_0x015e:
            r0 = r1
            goto L_0x00e5
        L_0x0160:
            r2 = move-exception
            goto L_0x013e
        L_0x0162:
            r0 = move-exception
            r2 = r3
            r5 = r3
            goto L_0x012f
        L_0x0166:
            r0 = move-exception
            r5 = r3
            goto L_0x012f
        L_0x0169:
            r0 = move-exception
            goto L_0x012f
        L_0x016b:
            r0 = move-exception
            goto L_0x00c5
        L_0x016e:
            r0 = move-exception
            r2 = r3
            r4 = r3
            r5 = r3
            goto L_0x00a9
        L_0x0174:
            r0 = move-exception
            r2 = r3
            r5 = r3
            goto L_0x00a9
        L_0x0179:
            r0 = move-exception
            r5 = r3
            goto L_0x00a9
        L_0x017d:
            r0 = move-exception
            goto L_0x0121
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.beacon.cover.c.a(com.tencent.beacon.cover.a):boolean");
    }

    private void c() {
        String a2;
        List<a> list;
        boolean z;
        for (a next : this.b) {
            File file = new File(this.c, next.d);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            new File(this.c, next.d + ".tmp").renameTo(file);
        }
        h a3 = h.a(this.a);
        ArrayList arrayList = new ArrayList();
        if (a3 != null) {
            arrayList.addAll(a3.a());
        }
        if (arrayList.size() == 0) {
            list = this.b;
            a2 = g.a(this.b);
        } else {
            for (a next2 : this.b) {
                if (next2 != null) {
                    int size = arrayList.size();
                    int i = 0;
                    while (true) {
                        if (i < size) {
                            a aVar = (a) arrayList.get(i);
                            if (aVar != null && aVar.a == next2.a) {
                                arrayList.set(i, next2);
                                z = true;
                                break;
                            }
                            i++;
                        } else {
                            z = false;
                            break;
                        }
                    }
                    if (!z) {
                        arrayList.add(next2);
                    }
                }
            }
            a2 = g.a((List<a>) arrayList);
            list = arrayList;
        }
        if (!"".equals(a2)) {
            g.a(this.a, "COMP_INFO", a2);
            g.a(TraceFormat.STR_DEBUG, "new config:" + a2, new Object[0]);
        }
        g.a(TraceFormat.STR_INFO, "update component success.", new Object[0]);
        b.a(this.a, list).a();
    }

    public final void a(List<a> list) {
        this.b = list;
        if (f != 1 && !e) {
            new Thread(this).start();
        }
    }
}
