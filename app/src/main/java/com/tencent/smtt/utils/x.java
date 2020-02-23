package com.tencent.smtt.utils;

import android.annotation.TargetApi;
import android.content.Context;
import com.tencent.mtt.engine.http.HttpUtils;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class x {
    private static x c = null;
    private Context a = null;
    private File b = null;
    private String d = "http://log.tbs.qq.com/ajax?c=pu&v=2&k=";
    private String e = "http://log.tbs.qq.com/ajax?c=pu&tk=";
    private String f = HttpUtils.WUP_PROXY_DOMAIN;
    private String g = "http://log.tbs.qq.com/ajax?c=dl&k=";
    private String h = "http://cfg.imtt.qq.com/tbs?v=2&mk=";
    private String i = "http://log.tbs.qq.com/ajax?c=ul&v=2&k=";
    private String j = "http://mqqad.html5.qq.com/adjs";
    private String k = "http://log.tbs.qq.com/ajax?c=ucfu&k=";

    @TargetApi(11)
    private x(Context context) {
        TbsLog.w("TbsCommonConfig", "TbsCommonConfig constructing...");
        this.a = context.getApplicationContext();
        g();
    }

    public static synchronized x a() {
        x xVar;
        synchronized (x.class) {
            xVar = c;
        }
        return xVar;
    }

    public static synchronized x a(Context context) {
        x xVar;
        synchronized (x.class) {
            if (c == null) {
                c = new x(context);
            }
            xVar = c;
        }
        return xVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f4 A[SYNTHETIC, Splitter:B:51:0x00f4] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0103 A[SYNTHETIC, Splitter:B:59:0x0103] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void g() {
        /*
            r5 = this;
            monitor-enter(r5)
            r1 = 0
            java.io.File r0 = r5.h()     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            if (r0 != 0) goto L_0x0016
            java.lang.String r0 = "TbsCommonConfig"
            java.lang.String r2 = "Config file is null, default values will be applied"
            com.tencent.smtt.utils.TbsLog.e(r0, r2)     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            if (r1 == 0) goto L_0x0014
            r1.close()     // Catch:{ IOException -> 0x010c }
        L_0x0014:
            monitor-exit(r5)
            return
        L_0x0016:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00c8, all -> 0x00ff }
            java.util.Properties r0 = new java.util.Properties     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            r0.<init>()     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            r0.load(r2)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r1 = "pv_post_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x003a
            r5.d = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x003a:
            java.lang.String r1 = "wup_proxy_domain"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x004c
            r5.f = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x004c:
            java.lang.String r1 = "tbs_download_stat_post_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x005e
            r5.g = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x005e:
            java.lang.String r1 = "tbs_downloader_post_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x0070
            r5.h = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x0070:
            java.lang.String r1 = "tbs_log_post_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x0082
            r5.i = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x0082:
            java.lang.String r1 = "tips_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x0094
            r5.j = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x0094:
            java.lang.String r1 = "tbs_cmd_post_url"
            java.lang.String r3 = ""
            java.lang.String r1 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r3 = ""
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r3 != 0) goto L_0x00a6
            r5.k = r1     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x00a6:
            java.lang.String r1 = "pv_post_url_tk"
            java.lang.String r3 = ""
            java.lang.String r0 = r0.getProperty(r1, r3)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            java.lang.String r1 = ""
            boolean r1 = r1.equals(r0)     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
            if (r1 != 0) goto L_0x00b8
            r5.e = r0     // Catch:{ Throwable -> 0x0117, all -> 0x0112 }
        L_0x00b8:
            if (r2 == 0) goto L_0x0014
            r2.close()     // Catch:{ IOException -> 0x00bf }
            goto L_0x0014
        L_0x00bf:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
            goto L_0x0014
        L_0x00c5:
            r0 = move-exception
            monitor-exit(r5)
            throw r0
        L_0x00c8:
            r0 = move-exception
        L_0x00c9:
            java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ all -> 0x0114 }
            r2.<init>()     // Catch:{ all -> 0x0114 }
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ all -> 0x0114 }
            r3.<init>(r2)     // Catch:{ all -> 0x0114 }
            r0.printStackTrace(r3)     // Catch:{ all -> 0x0114 }
            java.lang.String r0 = "TbsCommonConfig"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0114 }
            r3.<init>()     // Catch:{ all -> 0x0114 }
            java.lang.String r4 = "exceptions occurred1:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0114 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0114 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ all -> 0x0114 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0114 }
            com.tencent.smtt.utils.TbsLog.e(r0, r2)     // Catch:{ all -> 0x0114 }
            if (r1 == 0) goto L_0x0014
            r1.close()     // Catch:{ IOException -> 0x00f9 }
            goto L_0x0014
        L_0x00f9:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
            goto L_0x0014
        L_0x00ff:
            r0 = move-exception
            r2 = r1
        L_0x0101:
            if (r2 == 0) goto L_0x0106
            r2.close()     // Catch:{ IOException -> 0x0107 }
        L_0x0106:
            throw r0     // Catch:{ all -> 0x00c5 }
        L_0x0107:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x00c5 }
            goto L_0x0106
        L_0x010c:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x00c5 }
            goto L_0x0014
        L_0x0112:
            r0 = move-exception
            goto L_0x0101
        L_0x0114:
            r0 = move-exception
            r2 = r1
            goto L_0x0101
        L_0x0117:
            r0 = move-exception
            r1 = r2
            goto L_0x00c9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.x.g():void");
    }

    private File h() {
        File file;
        try {
            if (this.b == null) {
                this.b = new File(k.a(this.a, 5));
                if (this.b == null || !this.b.isDirectory()) {
                    return null;
                }
            }
            file = new File(this.b, "tbsnet.conf");
            if (!file.exists()) {
                TbsLog.e("TbsCommonConfig", "Get file(" + file.getCanonicalPath() + ") failed!");
                return null;
            }
            try {
                TbsLog.w("TbsCommonConfig", "pathc:" + file.getCanonicalPath());
                return file;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            file = null;
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            TbsLog.e("TbsCommonConfig", "exceptions occurred2:" + stringWriter.toString());
            return file;
        }
    }

    public String b() {
        return this.d;
    }

    public String c() {
        return this.g;
    }

    public String d() {
        return this.h;
    }

    public String e() {
        return this.i;
    }

    public String f() {
        return this.e;
    }
}
