package com.tencent.smtt.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.b;
import com.tencent.smtt.utils.n;
import com.tencent.smtt.utils.w;
import com.tencent.smtt.utils.x;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;

class TbsLogReport {
    private static TbsLogReport a;
    private Handler b = null;
    private Context c;
    private boolean d = false;

    public enum EventType {
        TYPE_DOWNLOAD(0),
        TYPE_INSTALL(1),
        TYPE_LOAD(2),
        TYPE_DOWNLOAD_DECOUPLE(3),
        TYPE_INSTALL_DECOUPLE(4),
        TYPE_COOKIE_DB_SWITCH(5);
        
        int a;

        private EventType(int i) {
            this.a = i;
        }
    }

    public static class TbsLogInfo implements Cloneable {
        int a;
        /* access modifiers changed from: private */
        public long b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public int e;
        /* access modifiers changed from: private */
        public int f;
        /* access modifiers changed from: private */
        public int g;
        /* access modifiers changed from: private */
        public int h;
        /* access modifiers changed from: private */
        public String i;
        /* access modifiers changed from: private */
        public int j;
        /* access modifiers changed from: private */
        public int k;
        /* access modifiers changed from: private */
        public long l;
        /* access modifiers changed from: private */
        public long m;
        /* access modifiers changed from: private */
        public int n;
        /* access modifiers changed from: private */
        public String o;
        /* access modifiers changed from: private */
        public String p;
        /* access modifiers changed from: private */
        public long q;

        private TbsLogInfo() {
            resetArgs();
        }

        /* synthetic */ TbsLogInfo(aw awVar) {
            this();
        }

        /* access modifiers changed from: protected */
        public Object clone() {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e2) {
                return this;
            }
        }

        public int getDownFinalFlag() {
            return this.k;
        }

        public void resetArgs() {
            this.b = 0;
            this.c = null;
            this.d = null;
            this.e = 0;
            this.f = 0;
            this.g = 0;
            this.h = 2;
            this.i = "unknown";
            this.j = 0;
            this.k = 2;
            this.l = 0;
            this.m = 0;
            this.n = 1;
            this.a = 0;
            this.o = null;
            this.p = null;
            this.q = 0;
        }

        public void setApn(String str) {
            this.i = str;
        }

        public void setCheckErrorDetail(String str) {
            setErrorCode(108);
            this.o = str;
        }

        public void setDownConsumeTime(long j2) {
            this.m += j2;
        }

        public void setDownFinalFlag(int i2) {
            this.k = i2;
        }

        public void setDownloadCancel(int i2) {
            this.g = i2;
        }

        public void setDownloadSize(long j2) {
            this.q += j2;
        }

        public void setDownloadUrl(String str) {
            if (this.c == null) {
                this.c = str;
            } else {
                this.c += ";" + str;
            }
        }

        public void setErrorCode(int i2) {
            if (!(i2 == 100 || i2 == 110 || i2 == 120 || i2 == 111 || i2 >= 400)) {
                TbsLog.i(TbsDownloader.LOGTAG, "error occured, errorCode:" + i2, true);
            }
            if (i2 == 111) {
                TbsLog.i(TbsDownloader.LOGTAG, "you are not in wifi, downloading stoped", true);
            }
            this.a = i2;
        }

        public void setEventTime(long j2) {
            this.b = j2;
        }

        public void setFailDetail(String str) {
            if (str != null) {
                if (str.length() > 1024) {
                    str = str.substring(0, 1024);
                }
                this.p = str;
            }
        }

        public void setFailDetail(Throwable th) {
            if (th == null) {
                this.p = "";
                return;
            }
            String stackTraceString = Log.getStackTraceString(th);
            if (stackTraceString.length() > 1024) {
                stackTraceString = stackTraceString.substring(0, 1024);
            }
            this.p = stackTraceString;
        }

        public void setHttpCode(int i2) {
            this.e = i2;
        }

        public void setNetworkChange(int i2) {
            this.n = i2;
        }

        public void setNetworkType(int i2) {
            this.j = i2;
        }

        public void setPatchUpdateFlag(int i2) {
            this.f = i2;
        }

        public void setPkgSize(long j2) {
            this.l = j2;
        }

        public void setResolveIp(String str) {
            this.d = str;
        }

        public void setUnpkgFlag(int i2) {
            this.h = i2;
        }
    }

    private static class a {
        private final String a;
        private final String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x003d A[SYNTHETIC, Splitter:B:19:0x003d] */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x004a A[SYNTHETIC, Splitter:B:26:0x004a] */
        /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static void a(java.io.File r6) {
            /*
                r2 = 0
                java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0036, all -> 0x0046 }
                java.lang.String r0 = "rw"
                r1.<init>(r6, r0)     // Catch:{ Exception -> 0x0036, all -> 0x0046 }
                if (r1 == 0) goto L_0x002b
                java.lang.String r0 = "00001000"
                r2 = 2
                int r0 = java.lang.Integer.parseInt(r0, r2)     // Catch:{ Exception -> 0x0055 }
                r2 = 7
                r1.seek(r2)     // Catch:{ Exception -> 0x0055 }
                int r2 = r1.read()     // Catch:{ Exception -> 0x0055 }
                r3 = r2 & r0
                if (r3 <= 0) goto L_0x002b
                r4 = 7
                r1.seek(r4)     // Catch:{ Exception -> 0x0055 }
                r0 = r0 ^ -1
                r0 = r0 & 255(0xff, float:3.57E-43)
                r0 = r0 & r2
                r1.write(r0)     // Catch:{ Exception -> 0x0055 }
            L_0x002b:
                if (r1 == 0) goto L_0x0030
                r1.close()     // Catch:{ IOException -> 0x0031 }
            L_0x0030:
                return
            L_0x0031:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0030
            L_0x0036:
                r0 = move-exception
                r1 = r2
            L_0x0038:
                r0.printStackTrace()     // Catch:{ all -> 0x0053 }
                if (r1 == 0) goto L_0x0030
                r1.close()     // Catch:{ IOException -> 0x0041 }
                goto L_0x0030
            L_0x0041:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0030
            L_0x0046:
                r0 = move-exception
                r1 = r2
            L_0x0048:
                if (r1 == 0) goto L_0x004d
                r1.close()     // Catch:{ IOException -> 0x004e }
            L_0x004d:
                throw r0
            L_0x004e:
                r1 = move-exception
                r1.printStackTrace()
                goto L_0x004d
            L_0x0053:
                r0 = move-exception
                goto L_0x0048
            L_0x0055:
                r0 = move-exception
                goto L_0x0038
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLogReport.a.a(java.io.File):void");
        }

        /* JADX WARNING: Removed duplicated region for block: B:115:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:116:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x004d A[SYNTHETIC, Splitter:B:21:0x004d] */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x0052 A[SYNTHETIC, Splitter:B:24:0x0052] */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x0061 A[SYNTHETIC, Splitter:B:29:0x0061] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x0066 A[SYNTHETIC, Splitter:B:32:0x0066] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x0088 A[SYNTHETIC, Splitter:B:50:0x0088] */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x008d A[SYNTHETIC, Splitter:B:53:0x008d] */
        /* JADX WARNING: Removed duplicated region for block: B:62:0x009e A[SYNTHETIC, Splitter:B:62:0x009e] */
        /* JADX WARNING: Removed duplicated region for block: B:65:0x00a3 A[SYNTHETIC, Splitter:B:65:0x00a3] */
        /* JADX WARNING: Removed duplicated region for block: B:76:0x00b6 A[SYNTHETIC, Splitter:B:76:0x00b6] */
        /* JADX WARNING: Removed duplicated region for block: B:79:0x00bb A[SYNTHETIC, Splitter:B:79:0x00bb] */
        /* JADX WARNING: Unknown top exception splitter block from list: {B:26:0x0055=Splitter:B:26:0x0055, B:81:0x00be=Splitter:B:81:0x00be} */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a() {
            /*
                r8 = this;
                r2 = 0
                java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00ed, all -> 0x00e2 }
                java.lang.String r0 = r8.b     // Catch:{ Exception -> 0x00ed, all -> 0x00e2 }
                r5.<init>(r0)     // Catch:{ Exception -> 0x00ed, all -> 0x00e2 }
                java.util.zip.ZipOutputStream r4 = new java.util.zip.ZipOutputStream     // Catch:{ Exception -> 0x00f1, all -> 0x00e6 }
                java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x00f1, all -> 0x00e6 }
                r0.<init>(r5)     // Catch:{ Exception -> 0x00f1, all -> 0x00e6 }
                r4.<init>(r0)     // Catch:{ Exception -> 0x00f1, all -> 0x00e6 }
                r0 = 2048(0x800, float:2.87E-42)
                byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                java.lang.String r6 = r8.a     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00fa, all -> 0x00b1 }
                r3.<init>(r6)     // Catch:{ Exception -> 0x00fa, all -> 0x00b1 }
                java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x00ff, all -> 0x00f5 }
                r7 = 2048(0x800, float:2.87E-42)
                r1.<init>(r3, r7)     // Catch:{ Exception -> 0x00ff, all -> 0x00f5 }
                java.util.zip.ZipEntry r2 = new java.util.zip.ZipEntry     // Catch:{ Exception -> 0x0047 }
                java.lang.String r7 = "/"
                int r7 = r6.lastIndexOf(r7)     // Catch:{ Exception -> 0x0047 }
                int r7 = r7 + 1
                java.lang.String r6 = r6.substring(r7)     // Catch:{ Exception -> 0x0047 }
                r2.<init>(r6)     // Catch:{ Exception -> 0x0047 }
                r4.putNextEntry(r2)     // Catch:{ Exception -> 0x0047 }
            L_0x0038:
                r2 = 0
                r6 = 2048(0x800, float:2.87E-42)
                int r2 = r1.read(r0, r2, r6)     // Catch:{ Exception -> 0x0047 }
                r6 = -1
                if (r2 == r6) goto L_0x006a
                r6 = 0
                r4.write(r0, r6, r2)     // Catch:{ Exception -> 0x0047 }
                goto L_0x0038
            L_0x0047:
                r0 = move-exception
            L_0x0048:
                r0.printStackTrace()     // Catch:{ all -> 0x00f8 }
                if (r1 == 0) goto L_0x0050
                r1.close()     // Catch:{ IOException -> 0x00a7 }
            L_0x0050:
                if (r3 == 0) goto L_0x0055
                r3.close()     // Catch:{ IOException -> 0x00ac }
            L_0x0055:
                java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                java.lang.String r1 = r8.b     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                r0.<init>(r1)     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                a(r0)     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                if (r4 == 0) goto L_0x0064
                r4.close()     // Catch:{ IOException -> 0x00c9 }
            L_0x0064:
                if (r5 == 0) goto L_0x0069
                r5.close()     // Catch:{ IOException -> 0x00ce }
            L_0x0069:
                return
            L_0x006a:
                r4.flush()     // Catch:{ Exception -> 0x0047 }
                r4.closeEntry()     // Catch:{ Exception -> 0x0047 }
                if (r1 == 0) goto L_0x0075
                r1.close()     // Catch:{ IOException -> 0x0096 }
            L_0x0075:
                if (r3 == 0) goto L_0x0055
                r3.close()     // Catch:{ IOException -> 0x007b }
                goto L_0x0055
            L_0x007b:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x0055
            L_0x0080:
                r0 = move-exception
                r1 = r4
                r3 = r5
            L_0x0083:
                r0.printStackTrace()     // Catch:{ all -> 0x00e9 }
                if (r1 == 0) goto L_0x008b
                r1.close()     // Catch:{ IOException -> 0x00d3 }
            L_0x008b:
                if (r3 == 0) goto L_0x0069
                r3.close()     // Catch:{ IOException -> 0x0091 }
                goto L_0x0069
            L_0x0091:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0069
            L_0x0096:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x0075
            L_0x009b:
                r0 = move-exception
            L_0x009c:
                if (r4 == 0) goto L_0x00a1
                r4.close()     // Catch:{ IOException -> 0x00d8 }
            L_0x00a1:
                if (r5 == 0) goto L_0x00a6
                r5.close()     // Catch:{ IOException -> 0x00dd }
            L_0x00a6:
                throw r0
            L_0x00a7:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x0050
            L_0x00ac:
                r0 = move-exception
                r0.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x0055
            L_0x00b1:
                r0 = move-exception
                r1 = r2
                r3 = r2
            L_0x00b4:
                if (r1 == 0) goto L_0x00b9
                r1.close()     // Catch:{ IOException -> 0x00bf }
            L_0x00b9:
                if (r3 == 0) goto L_0x00be
                r3.close()     // Catch:{ IOException -> 0x00c4 }
            L_0x00be:
                throw r0     // Catch:{ Exception -> 0x0080, all -> 0x009b }
            L_0x00bf:
                r1 = move-exception
                r1.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x00b9
            L_0x00c4:
                r1 = move-exception
                r1.printStackTrace()     // Catch:{ Exception -> 0x0080, all -> 0x009b }
                goto L_0x00be
            L_0x00c9:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0064
            L_0x00ce:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x0069
            L_0x00d3:
                r0 = move-exception
                r0.printStackTrace()
                goto L_0x008b
            L_0x00d8:
                r1 = move-exception
                r1.printStackTrace()
                goto L_0x00a1
            L_0x00dd:
                r1 = move-exception
                r1.printStackTrace()
                goto L_0x00a6
            L_0x00e2:
                r0 = move-exception
                r4 = r2
                r5 = r2
                goto L_0x009c
            L_0x00e6:
                r0 = move-exception
                r4 = r2
                goto L_0x009c
            L_0x00e9:
                r0 = move-exception
                r4 = r1
                r5 = r3
                goto L_0x009c
            L_0x00ed:
                r0 = move-exception
                r1 = r2
                r3 = r2
                goto L_0x0083
            L_0x00f1:
                r0 = move-exception
                r1 = r2
                r3 = r5
                goto L_0x0083
            L_0x00f5:
                r0 = move-exception
                r1 = r2
                goto L_0x00b4
            L_0x00f8:
                r0 = move-exception
                goto L_0x00b4
            L_0x00fa:
                r0 = move-exception
                r1 = r2
                r3 = r2
                goto L_0x0048
            L_0x00ff:
                r0 = move-exception
                r1 = r2
                goto L_0x0048
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLogReport.a.a():void");
        }
    }

    private TbsLogReport(Context context) {
        this.c = context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("TbsLogReportThread");
        handlerThread.start();
        this.b = new aw(this, handlerThread.getLooper());
    }

    public static TbsLogReport a(Context context) {
        if (a == null) {
            synchronized (TbsLogReport.class) {
                if (a == null) {
                    a = new TbsLogReport(context);
                }
            }
        }
        return a;
    }

    private String a(int i) {
        return i + "|";
    }

    private String a(long j) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(j));
        } catch (Exception e) {
            return null;
        }
    }

    private String a(String str) {
        StringBuilder sb = new StringBuilder();
        if (str == null) {
            str = "";
        }
        return sb.append(str).append("|").toString();
    }

    /* access modifiers changed from: private */
    public void a(int i, TbsLogInfo tbsLogInfo) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(a(i));
        sb.append(a(b.c(this.c)));
        sb.append(a(w.a(this.c)));
        sb.append(a(am.a().m(this.c)));
        String str2 = Build.MODEL;
        try {
            str = new String(str2.getBytes("UTF-8"), "ISO8859-1");
        } catch (Exception e) {
            str = str2;
        }
        sb.append(a(str));
        String packageName = this.c.getPackageName();
        sb.append(a(packageName));
        if (TbsConfig.APP_WX.equals(packageName)) {
            sb.append(a(b.a(this.c, "com.tencent.mm.BuildInfo.CLIENT_VERSION")));
        } else {
            sb.append(a(b.b(this.c)));
        }
        sb.append(a(a(tbsLogInfo.b)));
        sb.append(a(tbsLogInfo.c));
        sb.append(a(tbsLogInfo.d));
        sb.append(a(tbsLogInfo.e));
        sb.append(a(tbsLogInfo.f));
        sb.append(a(tbsLogInfo.g));
        sb.append(a(tbsLogInfo.h));
        sb.append(a(tbsLogInfo.i));
        sb.append(a(tbsLogInfo.j));
        sb.append(a(tbsLogInfo.k));
        sb.append(b(tbsLogInfo.q));
        sb.append(b(tbsLogInfo.l));
        sb.append(b(tbsLogInfo.m));
        sb.append(a(tbsLogInfo.n));
        sb.append(a(tbsLogInfo.a));
        sb.append(a(tbsLogInfo.o));
        sb.append(a(tbsLogInfo.p));
        sb.append(a(TbsDownloadConfig.getInstance(this.c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0)));
        sb.append(a(b.f(this.c)));
        sb.append(a("3.6.0.1211_43600"));
        sb.append(false);
        SharedPreferences i2 = i();
        JSONArray f = f();
        JSONArray jSONArray = new JSONArray();
        if (jSONArray.length() >= 5) {
            for (int i3 = 4; i3 >= 1; i3--) {
                try {
                    jSONArray.put(f.get(jSONArray.length() - i3));
                } catch (Exception e2) {
                    TbsLog.e("upload", "JSONArray transform error!");
                }
            }
        } else {
            jSONArray = f;
        }
        jSONArray.put(sb.toString());
        SharedPreferences.Editor edit = i2.edit();
        edit.putString("tbs_download_upload", jSONArray.toString());
        edit.commit();
        if (this.d || i != EventType.TYPE_LOAD.a) {
            g();
        }
    }

    private void a(int i, TbsLogInfo tbsLogInfo, EventType eventType) {
        tbsLogInfo.setErrorCode(i);
        tbsLogInfo.setEventTime(System.currentTimeMillis());
        QbSdk.l.onInstallFinish(i);
        a(eventType, tbsLogInfo);
    }

    private String b(long j) {
        return j + "|";
    }

    private JSONArray f() {
        String string = i().getString("tbs_download_upload", (String) null);
        if (string == null) {
            return new JSONArray();
        }
        try {
            JSONArray jSONArray = new JSONArray(string);
            if (jSONArray.length() <= 5) {
                return jSONArray;
            }
            JSONArray jSONArray2 = new JSONArray();
            int length = jSONArray.length() - 1;
            if (length <= jSONArray.length() - 5) {
                return jSONArray;
            }
            jSONArray2.put(jSONArray.get(length));
            return jSONArray2;
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        if (QbSdk.m == null || !QbSdk.m.containsKey(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD) || !QbSdk.m.get(QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD).equals("false")) {
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat]");
            JSONArray f = f();
            if (f == null || f.length() == 0) {
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] no data");
                return;
            }
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] jsonArray:" + f);
            try {
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloadStat.reportDownloadStat] response:" + n.a(x.a(this.c).c(), f.toString().getBytes("utf-8"), new ay(this), true) + " testcase: " + -1);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            TbsLog.i("upload", "[TbsLogReport.sendLogReportRequest] -- SET_SENDREQUEST_AND_UPLOAD is false");
        }
    }

    /* access modifiers changed from: private */
    public void h() {
        SharedPreferences.Editor edit = i().edit();
        edit.remove("tbs_download_upload");
        edit.commit();
    }

    private SharedPreferences i() {
        return this.c.getSharedPreferences("tbs_download_stat", 4);
    }

    public TbsLogInfo a() {
        return new TbsLogInfo((aw) null);
    }

    public void a(int i, String str) {
        a(i, str, EventType.TYPE_INSTALL);
    }

    public void a(int i, String str, EventType eventType) {
        if (!(i == 200 || i == 220 || i == 221)) {
            TbsLog.i(TbsDownloader.LOGTAG, "error occured in installation, errorCode:" + i, true);
        }
        TbsLogInfo a2 = a();
        a2.setFailDetail(str);
        a(i, a2, eventType);
    }

    public void a(int i, Throwable th) {
        TbsLogInfo a2 = a();
        a2.setFailDetail(th);
        a(i, a2, EventType.TYPE_INSTALL);
    }

    public void a(EventType eventType, TbsLogInfo tbsLogInfo) {
        try {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.what = 600;
            obtainMessage.arg1 = eventType.a;
            obtainMessage.obj = (TbsLogInfo) tbsLogInfo.clone();
            this.b.sendMessage(obtainMessage);
        } catch (Throwable th) {
            TbsLog.w("upload", "[TbsLogReport.eventReport] error, message=" + th.getMessage());
        }
    }

    public void a(boolean z) {
        this.d = z;
    }

    public void b() {
        this.b.sendEmptyMessage(601);
    }

    public void b(int i, String str) {
        TbsLogInfo a2 = a();
        a2.setErrorCode(i);
        a2.setEventTime(System.currentTimeMillis());
        a2.setFailDetail(str);
        a(EventType.TYPE_LOAD, a2);
    }

    public void b(int i, Throwable th) {
        String str = "NULL";
        if (th != null) {
            str = "msg: " + th.getMessage() + "; err: " + th + "; cause: " + Log.getStackTraceString(th.getCause());
            if (str.length() > 1024) {
                str = str.substring(0, 1024);
            }
        }
        b(i, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00fb A[SYNTHETIC, Splitter:B:30:0x00fb] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0100 A[SYNTHETIC, Splitter:B:33:0x0100] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x014f A[SYNTHETIC, Splitter:B:51:0x014f] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0154 A[SYNTHETIC, Splitter:B:54:0x0154] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0159  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0183  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c() {
        /*
            r11 = this;
            r10 = 0
            r2 = 0
            java.util.Map<java.lang.String, java.lang.Object> r0 = com.tencent.smtt.sdk.QbSdk.m
            if (r0 == 0) goto L_0x0028
            java.util.Map<java.lang.String, java.lang.Object> r0 = com.tencent.smtt.sdk.QbSdk.m
            java.lang.String r1 = com.tencent.smtt.sdk.QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto L_0x0028
            java.util.Map<java.lang.String, java.lang.Object> r0 = com.tencent.smtt.sdk.QbSdk.m
            java.lang.String r1 = com.tencent.smtt.sdk.QbSdk.KEY_SET_SENDREQUEST_AND_UPLOAD
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r1 = "false"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0028
            java.lang.String r0 = "upload"
            java.lang.String r1 = "[TbsLogReport.reportTbsLog] -- SET_SENDREQUEST_AND_UPLOAD is false"
            com.tencent.smtt.utils.TbsLog.i(r0, r1)
        L_0x0027:
            return
        L_0x0028:
            android.content.Context r0 = r11.c
            int r0 = com.tencent.smtt.utils.Apn.getApnType(r0)
            r1 = 3
            if (r0 != r1) goto L_0x0027
            java.lang.String r3 = com.tencent.smtt.utils.TbsLog.getTbsLogFilePath()
            if (r3 == 0) goto L_0x0027
            com.tencent.smtt.utils.p r0 = com.tencent.smtt.utils.p.a()
            java.lang.String r5 = r0.b()
            android.content.Context r0 = r11.c
            java.lang.String r0 = com.tencent.smtt.utils.b.c(r0)
            android.content.Context r1 = r11.c
            java.lang.String r4 = com.tencent.smtt.utils.b.f(r1)
            byte[] r1 = r0.getBytes()
            byte[] r0 = r4.getBytes()
            com.tencent.smtt.utils.p r4 = com.tencent.smtt.utils.p.a()     // Catch:{ Exception -> 0x0180 }
            byte[] r1 = r4.a(r1)     // Catch:{ Exception -> 0x0180 }
            com.tencent.smtt.utils.p r4 = com.tencent.smtt.utils.p.a()     // Catch:{ Exception -> 0x0180 }
            byte[] r0 = r4.a(r0)     // Catch:{ Exception -> 0x0180 }
        L_0x0063:
            java.lang.String r1 = com.tencent.smtt.utils.p.b(r1)
            java.lang.String r0 = com.tencent.smtt.utils.p.b(r0)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            android.content.Context r6 = r11.c
            com.tencent.smtt.utils.x r6 = com.tencent.smtt.utils.x.a(r6)
            java.lang.String r6 = r6.e()
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r4 = "&aid="
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r6 = r0.toString()
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            java.lang.String r0 = "Content-Type"
            java.lang.String r1 = "application/octet-stream"
            r7.put(r0, r1)
            java.lang.String r0 = "Charset"
            java.lang.String r1 = "UTF-8"
            r7.put(r0, r1)
            java.lang.String r0 = "QUA2"
            android.content.Context r1 = r11.c
            java.lang.String r1 = com.tencent.smtt.utils.w.a((android.content.Context) r1)
            r7.put(r0, r1)
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r1 = com.tencent.smtt.utils.k.a     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            r0.<init>(r1)     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            com.tencent.smtt.sdk.TbsLogReport$a r0 = new com.tencent.smtt.sdk.TbsLogReport$a     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            r1.<init>()     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r4 = com.tencent.smtt.utils.k.a     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r4 = "/tbslog_temp.zip"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            r0.<init>(r3, r1)     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            r0.a()     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r0 = com.tencent.smtt.utils.k.a     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.lang.String r1 = "tbslog_temp.zip"
            r3.<init>(r0, r1)     // Catch:{ Exception -> 0x0172, all -> 0x0149 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0177, all -> 0x0169 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0177, all -> 0x0169 }
            r0 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x017c, all -> 0x016d }
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x017c, all -> 0x016d }
            r1.<init>()     // Catch:{ Exception -> 0x017c, all -> 0x016d }
        L_0x00e9:
            int r8 = r4.read(r0)     // Catch:{ Exception -> 0x00f5 }
            r9 = -1
            if (r8 == r9) goto L_0x012a
            r9 = 0
            r1.write(r0, r9, r8)     // Catch:{ Exception -> 0x00f5 }
            goto L_0x00e9
        L_0x00f5:
            r0 = move-exception
        L_0x00f6:
            r0.printStackTrace()     // Catch:{ all -> 0x0170 }
            if (r4 == 0) goto L_0x00fe
            r4.close()     // Catch:{ Exception -> 0x0161 }
        L_0x00fe:
            if (r1 == 0) goto L_0x0103
            r1.close()     // Catch:{ Exception -> 0x0163 }
        L_0x0103:
            if (r3 == 0) goto L_0x0183
            r3.delete()
            r0 = r2
        L_0x0109:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r6)
            java.lang.String r2 = "&ek="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r5)
            java.lang.String r1 = r1.toString()
            com.tencent.smtt.sdk.ax r2 = new com.tencent.smtt.sdk.ax
            r2.<init>(r11)
            com.tencent.smtt.utils.n.a(r1, r7, r0, r2, r10)
            goto L_0x0027
        L_0x012a:
            r1.flush()     // Catch:{ Exception -> 0x00f5 }
            com.tencent.smtt.utils.p r0 = com.tencent.smtt.utils.p.a()     // Catch:{ Exception -> 0x00f5 }
            byte[] r8 = r1.toByteArray()     // Catch:{ Exception -> 0x00f5 }
            byte[] r0 = r0.a(r8)     // Catch:{ Exception -> 0x00f5 }
            if (r4 == 0) goto L_0x013e
            r4.close()     // Catch:{ Exception -> 0x015d }
        L_0x013e:
            if (r1 == 0) goto L_0x0143
            r1.close()     // Catch:{ Exception -> 0x015f }
        L_0x0143:
            if (r3 == 0) goto L_0x0109
            r3.delete()
            goto L_0x0109
        L_0x0149:
            r0 = move-exception
            r1 = r2
            r3 = r2
            r4 = r2
        L_0x014d:
            if (r4 == 0) goto L_0x0152
            r4.close()     // Catch:{ Exception -> 0x0165 }
        L_0x0152:
            if (r1 == 0) goto L_0x0157
            r1.close()     // Catch:{ Exception -> 0x0167 }
        L_0x0157:
            if (r3 == 0) goto L_0x015c
            r3.delete()
        L_0x015c:
            throw r0
        L_0x015d:
            r2 = move-exception
            goto L_0x013e
        L_0x015f:
            r1 = move-exception
            goto L_0x0143
        L_0x0161:
            r0 = move-exception
            goto L_0x00fe
        L_0x0163:
            r0 = move-exception
            goto L_0x0103
        L_0x0165:
            r2 = move-exception
            goto L_0x0152
        L_0x0167:
            r1 = move-exception
            goto L_0x0157
        L_0x0169:
            r0 = move-exception
            r1 = r2
            r4 = r2
            goto L_0x014d
        L_0x016d:
            r0 = move-exception
            r1 = r2
            goto L_0x014d
        L_0x0170:
            r0 = move-exception
            goto L_0x014d
        L_0x0172:
            r0 = move-exception
            r1 = r2
            r3 = r2
            r4 = r2
            goto L_0x00f6
        L_0x0177:
            r0 = move-exception
            r1 = r2
            r4 = r2
            goto L_0x00f6
        L_0x017c:
            r0 = move-exception
            r1 = r2
            goto L_0x00f6
        L_0x0180:
            r4 = move-exception
            goto L_0x0063
        L_0x0183:
            r0 = r2
            goto L_0x0109
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.TbsLogReport.c():void");
    }

    public void d() {
        try {
            SharedPreferences.Editor edit = i().edit();
            edit.clear();
            edit.commit();
        } catch (Exception e) {
        }
    }

    public boolean e() {
        return this.d;
    }
}
