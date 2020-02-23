package com.tencent.smtt.sdk;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.amazonaws.services.s3.internal.Constants;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.sdk.TbsLogReport;
import com.tencent.smtt.utils.Apn;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.a;
import com.tencent.smtt.utils.aa;
import com.tencent.smtt.utils.b;
import com.tencent.smtt.utils.k;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.protocol.HTTP;

class ag {
    private static int d = 5;
    private static int e = 1;
    private static final String[] f = {"tbs_downloading_com.tencent.mtt", "tbs_downloading_com.tencent.mm", "tbs_downloading_com.tencent.mobileqq", "tbs_downloading_com.tencent.tbs", "tbs_downloading_com.qzone"};
    private Set<String> A;
    private int B = d;
    private boolean C;
    String a;
    String[] b = null;
    int c = 0;
    private Context g;
    private String h;
    private String i;
    private String j;
    private File k;
    private long l;
    private int m = 30000;
    private int n = 20000;
    private boolean o;
    private int p;
    private int q;
    private boolean r;
    private boolean s;
    private HttpURLConnection t;
    private String u;
    private TbsLogReport.TbsLogInfo v;
    private String w;
    private int x;
    private boolean y;
    private Handler z;

    public ag(Context context) {
        this.g = context.getApplicationContext();
        this.v = TbsLogReport.a(this.g).a();
        this.A = new HashSet();
        this.u = "tbs_downloading_" + this.g.getPackageName();
        am.a();
        this.k = am.s(this.g);
        if (this.k == null) {
            throw new NullPointerException("TbsCorePrivateDir is null!");
        }
        f();
        this.w = null;
        this.x = -1;
    }

    private long a(long j2, long j3) {
        long currentTimeMillis = System.currentTimeMillis();
        this.v.setDownConsumeTime(currentTimeMillis - j2);
        this.v.setDownloadSize(j3);
        return currentTimeMillis;
    }

    @TargetApi(8)
    static File a(Context context) {
        try {
            File file = Build.VERSION.SDK_INT >= 8 ? new File(k.a(context, 4)) : null;
            if (file == null || file.exists() || file.isDirectory()) {
                return file;
            }
            file.mkdirs();
            return file;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupApkPath] Exception is " + e2.getMessage());
            return null;
        }
    }

    private static File a(Context context, int i2) {
        File file = new File(k.a(context, i2));
        if (file == null || !file.exists() || !file.isDirectory()) {
            return null;
        }
        File file2 = new File(file, TbsDownloader.getOverSea(context) ? "x5.oversea.tbs.org" : "x5.tbs.org");
        if (file2 == null || !file2.exists()) {
            return null;
        }
        return file;
    }

    private String a(Throwable th) {
        String stackTraceString = Log.getStackTraceString(th);
        return stackTraceString.length() > 1024 ? stackTraceString.substring(0, 1024) : stackTraceString;
    }

    private String a(URL url) {
        try {
            return InetAddress.getByName(url.getHost()).getHostAddress();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        } catch (Error e3) {
            e3.printStackTrace();
            return "";
        }
    }

    private void a(int i2, String str, boolean z2) {
        if (z2 || this.p > this.B) {
            this.v.setErrorCode(i2);
            this.v.setFailDetail(str);
        }
    }

    private void a(long j2) {
        this.p++;
        if (j2 <= 0) {
            try {
                j2 = m();
            } catch (Exception e2) {
                return;
            }
        }
        Thread.sleep(j2);
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e2) {
            }
        }
    }

    public static void a(File file, Context context) {
        File file2;
        if (file != null && file.exists()) {
            try {
                File a2 = a(context);
                if (a2 != null) {
                    if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0) == 1) {
                        file2 = new File(a2, "x5.tbs.decouple");
                    } else {
                        file2 = new File(a2, TbsDownloader.getOverSea(context) ? "x5.oversea.tbs.org" : "x5.tbs.org");
                    }
                    file2.delete();
                    k.b(file, file2);
                    if (TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V_TYPE, 0) != 1 && TbsDownloadConfig.getInstance(context).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, 0) == a.a(context, file)) {
                        File file3 = new File(a2, "x5.tbs.decouple");
                        if (a.a(context, file) != a.a(context, file3)) {
                            file3.delete();
                            k.b(file, file3);
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    private void a(String str) {
        URL url = new URL(str);
        if (this.t != null) {
            try {
                this.t.disconnect();
            } catch (Throwable th) {
                TbsLog.e(TbsDownloader.LOGTAG, "[initHttpRequest] mHttpRequest.disconnect() Throwable:" + th.toString());
            }
        }
        this.t = (HttpURLConnection) url.openConnection();
        this.t.setRequestProperty("User-Agent", TbsDownloader.a(this.g));
        this.t.setRequestProperty("Accept-Encoding", HTTP.IDENTITY_CODING);
        this.t.setRequestMethod("GET");
        this.t.setInstanceFollowRedirects(false);
        this.t.setConnectTimeout(this.n);
        this.t.setReadTimeout(this.m);
    }

    @TargetApi(8)
    static File b(Context context) {
        try {
            if (Build.VERSION.SDK_INT < 8) {
                return null;
            }
            File a2 = a(context, 4);
            if (a2 == null) {
                a2 = a(context, 3);
            }
            if (a2 == null) {
                a2 = a(context, 2);
            }
            return a2 == null ? a(context, 1) : a2;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.backupApkPath] Exception is " + e2.getMessage());
            return null;
        }
    }

    private void b(boolean z2) {
        aa.a(this.g);
        TbsDownloadConfig instance = TbsDownloadConfig.getInstance(this.g);
        instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_FULL_PACKAGE, false);
        instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false);
        instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOAD_INTERRUPT_CODE_REASON, -123);
        instance.commit();
        QbSdk.l.onDownloadFinish(z2 ? 100 : TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR);
        int i2 = instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_RESPONSECODE, 0);
        if (TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
        }
        if (i2 == 3 || i2 > 10000) {
            File a2 = a(this.g);
            if (a2 != null) {
                File file = new File(a2, TbsDownloader.getOverSea(this.g) ? "x5.oversea.tbs.org" : "x5.tbs.org");
                int a3 = a.a(this.g, file);
                File file2 = new File(this.k, "x5.tbs");
                String absolutePath = file2.exists() ? file2.getAbsolutePath() : null;
                int i3 = instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
                Bundle bundle = new Bundle();
                bundle.putInt("operation", i2);
                bundle.putInt("old_core_ver", a3);
                bundle.putInt("new_core_ver", i3);
                bundle.putString("old_apk_location", file.getAbsolutePath());
                bundle.putString("new_apk_location", absolutePath);
                bundle.putString("diff_file_location", absolutePath);
                am.a().b(this.g, bundle);
                return;
            }
            d();
            instance.a.put(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, true);
            instance.commit();
            return;
        }
        am.a().a(this.g, new File(this.k, "x5.tbs").getAbsolutePath(), instance.mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
        a(new File(this.k, "x5.tbs"), this.g);
    }

    private boolean b(int i2) {
        try {
            File file = new File(this.k, "x5.tbs");
            File a2 = a(this.g);
            if (a2 == null) {
                return false;
            }
            File file2 = new File(a2, TbsDownloader.getOverSea(this.g) ? "x5.oversea.tbs.org" : "x5.tbs.org");
            file.delete();
            k.b(file2, file);
            if (a.a(this.g, file, 0, i2)) {
                return true;
            }
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.copyTbsApkFromBackupToInstall] verifyTbsApk error!!");
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            TbsLog.e(TbsDownloader.LOGTAG, "[TbsApkDownloader.copyTbsApkFromBackupToInstall] Exception is " + e2.getMessage());
            return false;
        }
    }

    public static void c(Context context) {
        try {
            am.a();
            File s2 = am.s(context);
            new File(s2, "x5.tbs").delete();
            new File(s2, "x5.tbs.temp").delete();
            File a2 = a(context);
            if (a2 != null) {
                new File(a2, "x5.tbs.org").delete();
                new File(a2, "x5.oversea.tbs.org").delete();
            }
        } catch (Exception e2) {
        }
    }

    private boolean c(boolean z2) {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.deleteFile] isApk=" + z2);
        File file = z2 ? new File(this.k, "x5.tbs") : new File(this.k, "x5.tbs.temp");
        if (file == null || !file.exists()) {
            return true;
        }
        return file.delete();
    }

    private boolean c(boolean z2, boolean z3) {
        boolean z4;
        Exception exc;
        int i2;
        long j2;
        long j3 = 0;
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] isTempFile=" + z2);
        File file = new File(this.k, !z2 ? "x5.tbs" : "x5.tbs.temp");
        if (!file.exists()) {
            return false;
        }
        String string = TbsDownloadConfig.getInstance(this.g).mPreferences.getString(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPK_MD5, (String) null);
        String a2 = a.a(file);
        if (string == null || !string.equals(a2)) {
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] isTempFile=" + z2 + " md5 failed");
            if (!z2) {
                return false;
            }
            this.v.setCheckErrorDetail("fileMd5 not match");
            return false;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] md5(" + a2 + ") successful!");
        if (z2) {
            long j4 = TbsDownloadConfig.getInstance(this.g).mPreferences.getLong(TbsDownloadConfig.TbsConfigKey.KEY_TBSAPKFILESIZE, 0);
            if (file == null || !file.exists()) {
                j2 = 0;
            } else if (j4 > 0) {
                j2 = file.length();
                if (j4 == j2) {
                    j3 = j2;
                }
            }
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] isTempFile=" + z2 + " filelength failed");
            this.v.setCheckErrorDetail("fileLength:" + j2 + ",contentLength:" + j4);
            return false;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] length(" + j3 + ") successful!");
        int i3 = -1;
        if (!z3 || (i2 = TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0)) == (i3 = a.a(this.g, file))) {
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] tbsApkVersionCode(" + i3 + ") successful!");
            if (z3 && !z2) {
                String a3 = b.a(this.g, file);
                if (!"3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(a3)) {
                    TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] isTempFile=" + z2 + " signature failed");
                    if (!z2) {
                        return false;
                    }
                    this.v.setCheckErrorDetail("signature:" + (a3 == null ? Constants.NULL_VERSION_ID : Integer.valueOf(a3.length())));
                    return false;
                }
            }
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] signature successful!");
            if (z2) {
                try {
                    z4 = file.renameTo(new File(this.k, "x5.tbs"));
                    exc = null;
                } catch (Exception e2) {
                    exc = e2;
                    z4 = false;
                }
                if (!z4) {
                    a(109, a((Throwable) exc), true);
                    return false;
                }
            } else {
                z4 = false;
            }
            TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] rename(" + z4 + ") successful!");
            return true;
        }
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.verifyTbsApk] isTempFile=" + z2 + " versionCode failed");
        if (!z2) {
            return false;
        }
        this.v.setCheckErrorDetail("fileVersion:" + i3 + ",configVersion:" + i2);
        return false;
    }

    private void f() {
        this.p = 0;
        this.q = 0;
        this.l = -1;
        this.j = null;
        this.o = false;
        this.r = false;
        this.s = false;
        this.y = false;
    }

    private void g() {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.closeHttpRequest]");
        if (this.t != null) {
            if (!this.r) {
                this.v.setResolveIp(a(this.t.getURL()));
            }
            try {
                this.t.disconnect();
            } catch (Throwable th) {
                TbsLog.e(TbsDownloader.LOGTAG, "[closeHttpRequest] mHttpRequest.disconnect() Throwable:" + th.toString());
            }
            this.t = null;
        }
        int i2 = this.v.a;
        if (this.r || !this.y) {
            TbsDownloader.a = false;
            return;
        }
        this.v.setEventTime(System.currentTimeMillis());
        String apnInfo = Apn.getApnInfo(this.g);
        if (apnInfo == null) {
            apnInfo = "";
        }
        int apnType = Apn.getApnType(this.g);
        this.v.setApn(apnInfo);
        this.v.setNetworkType(apnType);
        if (apnType != this.x || !apnInfo.equals(this.w)) {
            this.v.setNetworkChange(0);
        }
        if ((this.v.a == 0 || this.v.a == 107) && this.v.getDownFinalFlag() == 0) {
            if (!Apn.isNetworkAvailable(this.g)) {
                a(101, (String) null, true);
            } else if (!l()) {
                a(101, (String) null, true);
            }
        }
        if (TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
            TbsLogReport.a(this.g).a(TbsLogReport.EventType.TYPE_DOWNLOAD_DECOUPLE, this.v);
        } else {
            TbsLogReport.a(this.g).a(TbsLogReport.EventType.TYPE_DOWNLOAD, this.v);
        }
        this.v.resetArgs();
        if (i2 != 100) {
            QbSdk.l.onDownloadFinish(i2);
        }
    }

    private boolean h() {
        File file = new File(k.a(this.g, 4), TbsDownloader.getOverSea(this.g) ? "x5.oversea.tbs.org" : "x5.tbs.org");
        int i2 = TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_USE_BACKUP_VERSION, 0);
        if (i2 == 0) {
            i2 = TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0);
        }
        return a.a(this.g, file, 0, i2);
    }

    private void i() {
        try {
            if (TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) != 1) {
                File file = new File(k.a(this.g, 4), TbsDownloader.getOverSea(this.g) ? "x5.oversea.tbs.org" : "x5.tbs.org");
                if (file != null && file.exists()) {
                    file.delete();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private boolean j() {
        File file = new File(this.k, "x5.tbs.temp");
        return file != null && file.exists();
    }

    private long k() {
        File file = new File(this.k, "x5.tbs.temp");
        if (file == null || !file.exists()) {
            return 0;
        }
        return file.length();
    }

    private boolean l() {
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        boolean z2 = false;
        try {
            inputStream = Runtime.getRuntime().exec("ping " + "www.qq.com").getInputStream();
            try {
                inputStreamReader = new InputStreamReader(inputStream);
            } catch (Throwable th) {
                th = th;
                bufferedReader = null;
                inputStreamReader = null;
                a((Closeable) inputStream);
                a((Closeable) inputStreamReader);
                a((Closeable) bufferedReader);
                throw th;
            }
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                int i2 = 0;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            if (!readLine.contains("TTL") && !readLine.contains("ttl")) {
                                i2++;
                                if (i2 >= 5) {
                                    break;
                                }
                            } else {
                                z2 = true;
                            }
                        } else {
                            break;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        th.printStackTrace();
                        a((Closeable) inputStream);
                        a((Closeable) inputStreamReader);
                        a((Closeable) bufferedReader);
                        return z2;
                    }
                }
                z2 = true;
                a((Closeable) inputStream);
                a((Closeable) inputStreamReader);
                a((Closeable) bufferedReader);
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
                a((Closeable) inputStream);
                a((Closeable) inputStreamReader);
                a((Closeable) bufferedReader);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            bufferedReader = null;
            inputStreamReader = null;
            inputStream = null;
            a((Closeable) inputStream);
            a((Closeable) inputStreamReader);
            a((Closeable) bufferedReader);
            throw th;
        }
        return z2;
    }

    private long m() {
        switch (this.p) {
            case 1:
            case 2:
                return 20000 * ((long) this.p);
            case 3:
            case 4:
                return 20000 * 5;
            default:
                return 20000 * 10;
        }
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v1, types: [java.lang.CharSequence, java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.net.HttpURLConnection] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d6 A[SYNTHETIC, Splitter:B:37:0x00d6] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00e2 A[SYNTHETIC, Splitter:B:45:0x00e2] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean n() {
        /*
            r8 = this;
            r3 = 0
            r1 = 1
            r2 = 0
            android.content.Context r0 = r8.g
            int r0 = com.tencent.smtt.utils.Apn.getApnType(r0)
            r4 = 3
            if (r0 != r4) goto L_0x00c6
            r0 = r1
        L_0x000d:
            java.lang.String r4 = "TbsDownload"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "[TbsApkDwonloader.detectWifiNetworkAvailable] isWifi="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r4, r5)
            if (r0 == 0) goto L_0x008e
            android.content.Context r0 = r8.g
            java.lang.String r4 = com.tencent.smtt.utils.Apn.getWifiSSID(r0)
            java.lang.String r0 = "TbsDownload"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "[TbsApkDwonloader.detectWifiNetworkAvailable] localBSSID="
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.String r5 = r5.toString()
            com.tencent.smtt.utils.TbsLog.i(r0, r5)
            java.net.URL r0 = new java.net.URL     // Catch:{ Throwable -> 0x00cf }
            java.lang.String r5 = "http://pms.mb.qq.com/rsp204"
            r0.<init>(r5)     // Catch:{ Throwable -> 0x00cf }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Throwable -> 0x00cf }
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ Throwable -> 0x00cf }
            r3 = 0
            r0.setInstanceFollowRedirects(r3)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r3 = 10000(0x2710, float:1.4013E-41)
            r0.setConnectTimeout(r3)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r3 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r3)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r3 = 0
            r0.setUseCaches(r3)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r0.getInputStream()     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            int r3 = r0.getResponseCode()     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            java.lang.String r5 = "TbsDownload"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r6.<init>()     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            java.lang.String r7 = "[TbsApkDwonloader.detectWifiNetworkAvailable] responseCode="
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            java.lang.StringBuilder r6 = r6.append(r3)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            com.tencent.smtt.utils.TbsLog.i(r5, r6)     // Catch:{ Throwable -> 0x00eb, all -> 0x00e8 }
            r5 = 204(0xcc, float:2.86E-43)
            if (r3 != r5) goto L_0x00c9
        L_0x0087:
            if (r0 == 0) goto L_0x00f0
            r0.disconnect()     // Catch:{ Exception -> 0x00cb }
            r3 = r4
            r2 = r1
        L_0x008e:
            if (r2 != 0) goto L_0x00b6
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x00b6
            java.util.Set<java.lang.String> r0 = r8.A
            boolean r0 = r0.contains(r3)
            if (r0 != 0) goto L_0x00b6
            java.util.Set<java.lang.String> r0 = r8.A
            r0.add(r3)
            r8.o()
            android.os.Handler r0 = r8.z
            r1 = 150(0x96, float:2.1E-43)
            android.os.Message r0 = r0.obtainMessage(r1, r3)
            android.os.Handler r1 = r8.z
            r4 = 120000(0x1d4c0, double:5.9288E-319)
            r1.sendMessageDelayed(r0, r4)
        L_0x00b6:
            if (r2 == 0) goto L_0x00c5
            java.util.Set<java.lang.String> r0 = r8.A
            boolean r0 = r0.contains(r3)
            if (r0 == 0) goto L_0x00c5
            java.util.Set<java.lang.String> r0 = r8.A
            r0.remove(r3)
        L_0x00c5:
            return r2
        L_0x00c6:
            r0 = r2
            goto L_0x000d
        L_0x00c9:
            r1 = r2
            goto L_0x0087
        L_0x00cb:
            r0 = move-exception
            r3 = r4
            r2 = r1
            goto L_0x008e
        L_0x00cf:
            r0 = move-exception
            r1 = r0
        L_0x00d1:
            r1.printStackTrace()     // Catch:{ all -> 0x00de }
            if (r3 == 0) goto L_0x00ee
            r3.disconnect()     // Catch:{ Exception -> 0x00db }
            r3 = r4
            goto L_0x008e
        L_0x00db:
            r0 = move-exception
            r3 = r4
            goto L_0x008e
        L_0x00de:
            r0 = move-exception
            r1 = r0
        L_0x00e0:
            if (r3 == 0) goto L_0x00e5
            r3.disconnect()     // Catch:{ Exception -> 0x00e6 }
        L_0x00e5:
            throw r1
        L_0x00e6:
            r0 = move-exception
            goto L_0x00e5
        L_0x00e8:
            r1 = move-exception
            r3 = r0
            goto L_0x00e0
        L_0x00eb:
            r1 = move-exception
            r3 = r0
            goto L_0x00d1
        L_0x00ee:
            r3 = r4
            goto L_0x008e
        L_0x00f0:
            r3 = r4
            r2 = r1
            goto L_0x008e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.ag.n():boolean");
    }

    private void o() {
        if (this.z == null) {
            this.z = new ah(this, al.a().getLooper());
        }
    }

    public void a(int i2) {
        try {
            File file = new File(this.k, "x5.tbs");
            int a2 = a.a(this.g, file);
            if (-1 == a2 || (i2 > 0 && i2 == a2)) {
                file.delete();
            }
        } catch (Exception e2) {
        }
    }

    public boolean a() {
        TbsLog.i("TbsApkDownloader", "verifyAndInstallDecoupleCoreFromBackup #1");
        try {
            File file = new File(k.a(this.g, 4), "x5.tbs.decouple");
            if (file == null || !file.exists()) {
                File b2 = TbsDownloader.b(TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, -1));
                if (b2 != null && b2.exists()) {
                    k.b(b2, file);
                }
            } else {
                TbsLog.i("TbsApkDownloader", "verifyAndInstallDecoupleCoreFromBackup #2");
            }
            if (a.a(this.g, file, 0, TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DECOUPLECOREVERSION, -1))) {
                TbsLog.i("TbsApkDownloader", "verifyAndInstallDecoupleCoreFromBackup #3");
                return am.a().e(this.g);
            }
            TbsLog.i("TbsApkDownloader", "verifyAndInstallDecoupleCoreFromBackup no backup file !!!");
            File file2 = new File(k.a(this.g, 4), "x5.tbs.decouple");
            if (file2 != null && file2.exists()) {
                file2.delete();
            }
            return false;
        } catch (Exception e2) {
        }
    }

    public boolean a(boolean z2) {
        if ((z2 && !n() && (!QbSdk.getDownloadWithoutWifi() || !Apn.isNetworkAvailable(this.g))) || this.b == null || this.c < 0 || this.c >= this.b.length) {
            return false;
        }
        String[] strArr = this.b;
        int i2 = this.c;
        this.c = i2 + 1;
        this.j = strArr[i2];
        this.p = 0;
        this.q = 0;
        this.l = -1;
        this.o = false;
        this.r = false;
        this.s = false;
        this.y = false;
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(boolean r10, boolean r11) {
        /*
            r9 = this;
            r8 = -214(0xffffffffffffff2a, float:NaN)
            r1 = 1
            r2 = 0
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            android.content.SharedPreferences r0 = r0.mPreferences
            java.lang.String r3 = "use_backup_version"
            int r0 = r0.getInt(r3, r2)
            com.tencent.smtt.sdk.am r3 = com.tencent.smtt.sdk.am.a()
            android.content.Context r4 = r9.g
            int r4 = r3.i(r4)
            if (r0 != 0) goto L_0x0037
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            android.content.SharedPreferences r0 = r0.mPreferences
            java.lang.String r3 = "tbs_download_version"
            int r0 = r0.getInt(r3, r2)
            java.lang.String r3 = "by default key"
            r9.a = r3
            r3 = r0
        L_0x0031:
            if (r3 == 0) goto L_0x0035
            if (r3 != r4) goto L_0x003d
        L_0x0035:
            r1 = r2
        L_0x0036:
            return r1
        L_0x0037:
            java.lang.String r3 = "by new key"
            r9.a = r3
            r3 = r0
            goto L_0x0031
        L_0x003d:
            if (r11 == 0) goto L_0x0107
            java.io.File r4 = com.tencent.smtt.sdk.TbsDownloader.a((int) r3)
            if (r4 == 0) goto L_0x00e7
            boolean r0 = r4.exists()
            if (r0 == 0) goto L_0x00e7
            java.io.File r5 = new java.io.File
            android.content.Context r0 = r9.g
            r6 = 4
            java.lang.String r6 = com.tencent.smtt.utils.k.a((android.content.Context) r0, (int) r6)
            android.content.Context r0 = r9.g
            boolean r0 = com.tencent.smtt.sdk.TbsDownloader.getOverSea(r0)
            if (r0 == 0) goto L_0x00df
            java.lang.String r0 = "x5.oversea.tbs.org"
        L_0x005e:
            r5.<init>(r6, r0)
            android.content.Context r0 = r9.g     // Catch:{ Exception -> 0x00e3 }
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)     // Catch:{ Exception -> 0x00e3 }
            android.content.SharedPreferences r0 = r0.mPreferences     // Catch:{ Exception -> 0x00e3 }
            java.lang.String r6 = "tbs_download_version_type"
            r7 = 0
            int r0 = r0.getInt(r6, r7)     // Catch:{ Exception -> 0x00e3 }
            if (r0 == r1) goto L_0x00e7
            com.tencent.smtt.utils.k.b((java.io.File) r4, (java.io.File) r5)     // Catch:{ Exception -> 0x00e3 }
            r0 = r1
        L_0x0076:
            boolean r5 = r9.h()
            if (r5 == 0) goto L_0x00f7
            boolean r3 = r9.b((int) r3)
            if (r3 == 0) goto L_0x0107
            android.content.Context r3 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r3)
            java.util.Map<java.lang.String, java.lang.Object> r3 = r3.a
            java.lang.String r4 = "tbs_download_interrupt_code_reason"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            r3.put(r4, r5)
            android.content.Context r3 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r3 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r3)
            r3.setDownloadInterruptCode(r8)
            r9.b((boolean) r2)
            if (r0 == 0) goto L_0x0036
            r0 = 100
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "use local backup apk in startDownload"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = r9.a
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r9.a(r0, r3, r1)
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            android.content.SharedPreferences r0 = r0.mPreferences
            java.lang.String r3 = "tbs_downloaddecouplecore"
            int r0 = r0.getInt(r3, r2)
            if (r0 != r1) goto L_0x00e9
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r0)
            com.tencent.smtt.sdk.TbsLogReport$EventType r2 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD_DECOUPLE
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r3 = r9.v
            r0.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r2, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r3)
        L_0x00d8:
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r0 = r9.v
            r0.resetArgs()
            goto L_0x0036
        L_0x00df:
            java.lang.String r0 = "x5.tbs.org"
            goto L_0x005e
        L_0x00e3:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00e7:
            r0 = r2
            goto L_0x0076
        L_0x00e9:
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsLogReport r0 = com.tencent.smtt.sdk.TbsLogReport.a((android.content.Context) r0)
            com.tencent.smtt.sdk.TbsLogReport$EventType r2 = com.tencent.smtt.sdk.TbsLogReport.EventType.TYPE_DOWNLOAD
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r3 = r9.v
            r0.a((com.tencent.smtt.sdk.TbsLogReport.EventType) r2, (com.tencent.smtt.sdk.TbsLogReport.TbsLogInfo) r3)
            goto L_0x00d8
        L_0x00f7:
            r9.i()
            android.content.Context r0 = r9.g
            r6 = 0
            boolean r0 = com.tencent.smtt.utils.a.a(r0, r4, r6, r3)
            if (r0 != 0) goto L_0x0107
            com.tencent.smtt.utils.k.b((java.io.File) r4)
        L_0x0107:
            boolean r0 = r9.c(r2, r11)
            if (r0 == 0) goto L_0x012c
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            java.util.Map<java.lang.String, java.lang.Object> r0 = r0.a
            java.lang.String r3 = "tbs_download_interrupt_code_reason"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r8)
            r0.put(r3, r4)
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            r0.setDownloadInterruptCode(r8)
            r9.b((boolean) r2)
            goto L_0x0036
        L_0x012c:
            boolean r0 = r9.c((boolean) r1)
            if (r0 != 0) goto L_0x014a
            boolean r0 = r9.c((boolean) r1)
            if (r0 != 0) goto L_0x014a
            java.lang.String r0 = "TbsDownload"
            java.lang.String r1 = "[TbsApkDownloader] delete file failed!"
            com.tencent.smtt.utils.TbsLog.e(r0, r1)
            android.content.Context r0 = r9.g
            com.tencent.smtt.sdk.TbsDownloadConfig r0 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r0)
            r1 = -301(0xfffffffffffffed3, float:NaN)
            r0.setDownloadInterruptCode(r1)
        L_0x014a:
            r1 = r2
            goto L_0x0036
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.ag.a(boolean, boolean):boolean");
    }

    public int b() {
        File a2 = a(this.g);
        if (a2 == null) {
            return 0;
        }
        return a.a(this.g, new File(a2, TbsDownloader.getOverSea(this.g) ? "x5.oversea.tbs.org" : "x5.tbs.org"));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: java.io.InputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v3, resolved type: java.io.Closeable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v4, resolved type: java.io.Closeable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v6, resolved type: java.util.zip.InflaterInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v264, resolved type: java.util.zip.InflaterInputStream} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v13, resolved type: java.util.zip.InflaterInputStream} */
    /* JADX WARNING: type inference failed for: r16v8 */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x0727, code lost:
        if (r31 != false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0729, code lost:
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r30.g).a.put(com.tencent.smtt.sdk.TbsDownloadConfig.TbsConfigKey.KEY_TBSDOWNLOAD_FLOW, java.lang.Long.valueOf(r4));
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r30.g).commit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:?, code lost:
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "STEP 1/2 begin downloading...failed because you exceeded max flow!", true);
        a(112, "downloadFlow=" + r4 + " downloadMaxflow=" + r24, true);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r30.g).setDownloadInterruptCode(-307);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x0a2e, code lost:
        c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x0a33, code lost:
        if (com.tencent.smtt.sdk.QbSdk.l == null) goto L_0x0a3c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x0a35, code lost:
        com.tencent.smtt.sdk.QbSdk.l.onDownloadFinish(111);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x0a3c, code lost:
        com.tencent.smtt.utils.TbsLog.i(com.tencent.smtt.sdk.TbsDownloader.LOGTAG, "Download is paused due to NOT_WIFI error!", false);
        com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r30.g).setDownloadInterruptCode(-304);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:306:0x0a51, code lost:
        r4 = r20;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0605  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x082d A[Catch:{ all -> 0x0b82 }] */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x0aa8  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x01c8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(boolean r31, boolean r32) {
        /*
            r30 = this;
            com.tencent.smtt.sdk.am r4 = com.tencent.smtt.sdk.am.a()
            r0 = r30
            android.content.Context r5 = r0.g
            boolean r4 = r4.c(r5)
            if (r4 == 0) goto L_0x0021
            if (r31 != 0) goto L_0x0021
            r4 = 0
            com.tencent.smtt.sdk.TbsDownloader.a = r4
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -322(0xfffffffffffffebe, float:NaN)
            r4.setDownloadInterruptCode(r5)
        L_0x0020:
            return
        L_0x0021:
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            android.content.SharedPreferences r4 = r4.mPreferences
            java.lang.String r5 = "tbs_responsecode"
            r6 = 0
            int r4 = r4.getInt(r5, r6)
            r5 = 1
            if (r4 == r5) goto L_0x003b
            r5 = 2
            if (r4 == r5) goto L_0x003b
            r5 = 4
            if (r4 != r5) goto L_0x0050
        L_0x003b:
            r4 = 1
            r19 = r4
        L_0x003e:
            if (r32 != 0) goto L_0x0054
            r0 = r30
            r1 = r31
            r2 = r19
            boolean r4 = r0.a((boolean) r1, (boolean) r2)
            if (r4 == 0) goto L_0x0054
            r4 = 0
            com.tencent.smtt.sdk.TbsDownloader.a = r4
            goto L_0x0020
        L_0x0050:
            r4 = 0
            r19 = r4
            goto L_0x003e
        L_0x0054:
            r0 = r31
            r1 = r30
            r1.C = r0
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            android.content.SharedPreferences r4 = r4.mPreferences
            java.lang.String r5 = "tbs_downloadurl"
            r6 = 0
            java.lang.String r4 = r4.getString(r5, r6)
            r0 = r30
            r0.h = r4
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            android.content.SharedPreferences r4 = r4.mPreferences
            java.lang.String r5 = "tbs_downloadurl_list"
            r6 = 0
            java.lang.String r4 = r4.getString(r5, r6)
            java.lang.String r5 = "TbsDownload"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "backupUrlStrings:"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.String r6 = r6.toString()
            r7 = 1
            com.tencent.smtt.utils.TbsLog.i(r5, r6, r7)
            r5 = 0
            r0 = r30
            r0.b = r5
            r5 = 0
            r0 = r30
            r0.c = r5
            if (r31 != 0) goto L_0x00c1
            if (r4 == 0) goto L_0x00c1
            java.lang.String r5 = ""
            java.lang.String r6 = r4.trim()
            boolean r5 = r5.equals(r6)
            if (r5 != 0) goto L_0x00c1
            java.lang.String r5 = r4.trim()
            java.lang.String r6 = ";"
            java.lang.String[] r5 = r5.split(r6)
            r0 = r30
            r0.b = r5
        L_0x00c1:
            java.lang.String r5 = "TbsDownload"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "[TbsApkDownloader.startDownload] mDownloadUrl="
            java.lang.StringBuilder r6 = r6.append(r7)
            r0 = r30
            java.lang.String r7 = r0.h
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = " backupUrlStrings="
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r4 = r6.append(r4)
            java.lang.String r6 = " mLocation="
            java.lang.StringBuilder r4 = r4.append(r6)
            r0 = r30
            java.lang.String r6 = r0.j
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r6 = " mCanceled="
            java.lang.StringBuilder r4 = r4.append(r6)
            r0 = r30
            boolean r6 = r0.r
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r6 = " mHttpRequest="
            java.lang.StringBuilder r4 = r4.append(r6)
            r0 = r30
            java.net.HttpURLConnection r6 = r0.t
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r4 = r4.toString()
            com.tencent.smtt.utils.TbsLog.i(r5, r4)
            r0 = r30
            java.lang.String r4 = r0.h
            if (r4 != 0) goto L_0x0133
            r0 = r30
            java.lang.String r4 = r0.j
            if (r4 != 0) goto L_0x0133
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l
            r5 = 110(0x6e, float:1.54E-43)
            r4.onDownloadFinish(r5)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -302(0xfffffffffffffed2, float:NaN)
            r4.setDownloadInterruptCode(r5)
            goto L_0x0020
        L_0x0133:
            r0 = r30
            java.net.HttpURLConnection r4 = r0.t
            if (r4 == 0) goto L_0x0155
            r0 = r30
            boolean r4 = r0.r
            if (r4 != 0) goto L_0x0155
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l
            r5 = 110(0x6e, float:1.54E-43)
            r4.onDownloadFinish(r5)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -303(0xfffffffffffffed1, float:NaN)
            r4.setDownloadInterruptCode(r5)
            goto L_0x0020
        L_0x0155:
            if (r31 != 0) goto L_0x0186
            r0 = r30
            java.util.Set<java.lang.String> r4 = r0.A
            r0 = r30
            android.content.Context r5 = r0.g
            java.lang.String r5 = com.tencent.smtt.utils.Apn.getWifiSSID(r5)
            boolean r4 = r4.contains(r5)
            if (r4 == 0) goto L_0x0186
            java.lang.String r4 = "TbsDownload"
            java.lang.String r5 = "[TbsApkDownloader.startDownload] WIFI Unavailable"
            com.tencent.smtt.utils.TbsLog.i(r4, r5)
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l
            r5 = 110(0x6e, float:1.54E-43)
            r4.onDownloadFinish(r5)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -304(0xfffffffffffffed0, float:NaN)
            r4.setDownloadInterruptCode(r5)
            goto L_0x0020
        L_0x0186:
            r30.f()
            java.lang.String r4 = "TbsDownload"
            java.lang.String r5 = "STEP 1/2 begin downloading..."
            r6 = 1
            com.tencent.smtt.utils.TbsLog.i(r4, r5, r6)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            long r24 = r4.getDownloadMaxflow()
            r6 = 0
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            android.content.SharedPreferences r4 = r4.mPreferences
            java.lang.String r5 = "tbs_downloadflow"
            r8 = 0
            long r4 = r4.getLong(r5, r8)
            if (r31 == 0) goto L_0x0246
            int r7 = e
            r0 = r30
            r0.B = r7
        L_0x01b8:
            r0 = r30
            int r7 = r0.p
            r0 = r30
            int r8 = r0.B
            if (r7 <= r8) goto L_0x024e
        L_0x01c2:
            r0 = r30
            boolean r4 = r0.r
            if (r4 != 0) goto L_0x0241
            r0 = r30
            boolean r4 = r0.s
            if (r4 == 0) goto L_0x0214
            r0 = r30
            java.lang.String[] r4 = r0.b
            if (r4 != 0) goto L_0x01df
            if (r6 != 0) goto L_0x01df
            r4 = 1
            r0 = r30
            r1 = r19
            boolean r6 = r0.c(r4, r1)
        L_0x01df:
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r5 = r0.v
            if (r6 == 0) goto L_0x0e9b
            r4 = 1
        L_0x01e6:
            r5.setUnpkgFlag(r4)
            if (r19 != 0) goto L_0x0ea1
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r5 = r0.v
            if (r6 == 0) goto L_0x0e9e
            r4 = 1
        L_0x01f2:
            r5.setPatchUpdateFlag(r4)
        L_0x01f5:
            if (r6 == 0) goto L_0x0eab
            r4 = 1
            r0 = r30
            r0.b((boolean) r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -317(0xfffffffffffffec3, float:NaN)
            r4.setDownloadInterruptCode(r5)
            r4 = 100
            java.lang.String r5 = "success"
            r7 = 1
            r0 = r30
            r0.a(r4, r5, r7)
        L_0x0214:
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            if (r6 == 0) goto L_0x0ec0
            android.content.SharedPreferences r5 = r4.mPreferences
            java.lang.String r7 = "tbs_download_success_retrytimes"
            r8 = 0
            int r5 = r5.getInt(r7, r8)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r4.a
            java.lang.String r8 = "tbs_download_success_retrytimes"
            int r5 = r5 + 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r7.put(r8, r5)
        L_0x0234:
            r4.commit()
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r5 = r0.v
            if (r6 == 0) goto L_0x0ee6
            r4 = 1
        L_0x023e:
            r5.setDownFinalFlag(r4)
        L_0x0241:
            r30.g()
            goto L_0x0020
        L_0x0246:
            int r7 = d
            r0 = r30
            r0.B = r7
            goto L_0x01b8
        L_0x024e:
            r0 = r30
            int r7 = r0.q
            r8 = 8
            if (r7 <= r8) goto L_0x026e
            r4 = 123(0x7b, float:1.72E-43)
            r5 = 0
            r7 = 1
            r0 = r30
            r0.a(r4, r5, r7)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -306(0xfffffffffffffece, float:NaN)
            r4.setDownloadInterruptCode(r5)
            goto L_0x01c2
        L_0x026e:
            long r22 = java.lang.System.currentTimeMillis()
            if (r31 != 0) goto L_0x0371
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            android.content.SharedPreferences r7 = r7.mPreferences     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "tbs_downloadstarttime"
            r10 = 0
            long r8 = r7.getLong(r8, r10)     // Catch:{ Throwable -> 0x0563 }
            long r8 = r22 - r8
            r10 = 86400000(0x5265c00, double:4.2687272E-316)
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r7 <= 0) goto L_0x0315
            java.lang.String r7 = "TbsDownload"
            java.lang.String r8 = "[TbsApkDownloader.startDownload] OVER DOWNLOAD_PERIOD"
            com.tencent.smtt.utils.TbsLog.i(r7, r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "tbs_downloadstarttime"
            java.lang.Long r9 = java.lang.Long.valueOf(r22)     // Catch:{ Throwable -> 0x0563 }
            r7.put(r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "tbs_downloadflow"
            r10 = 0
            java.lang.Long r9 = java.lang.Long.valueOf(r10)     // Catch:{ Throwable -> 0x0563 }
            r7.put(r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r7.commit()     // Catch:{ Throwable -> 0x0563 }
            r4 = 0
        L_0x02cb:
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            boolean r7 = com.tencent.smtt.utils.k.b((android.content.Context) r7)     // Catch:{ Throwable -> 0x0563 }
            if (r7 != 0) goto L_0x0371
            java.lang.String r7 = "TbsDownload"
            java.lang.String r8 = "DownloadBegin FreeSpace too small"
            r9 = 1
            com.tencent.smtt.utils.TbsLog.i(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r7 = 105(0x69, float:1.47E-43)
            r8 = 0
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -308(0xfffffffffffffecc, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0315:
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r8.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "[TbsApkDownloader.startDownload] downloadFlow="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r4)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.utils.TbsLog.i(r7, r8)     // Catch:{ Throwable -> 0x0563 }
            int r7 = (r4 > r24 ? 1 : (r4 == r24 ? 0 : -1))
            if (r7 < 0) goto L_0x02cb
            java.lang.String r7 = "TbsDownload"
            java.lang.String r8 = "STEP 1/2 begin downloading...failed because you exceeded max flow!"
            r9 = 1
            com.tencent.smtt.utils.TbsLog.i(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r7 = 112(0x70, float:1.57E-43)
            r8 = 0
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -307(0xfffffffffffffecd, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0371:
            r7 = 1
            r0 = r30
            r0.y = r7     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.lang.String r7 = r0.j     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x050a
            r0 = r30
            java.lang.String r7 = r0.j     // Catch:{ Throwable -> 0x0563 }
        L_0x0380:
            java.lang.String r8 = "TbsDownload"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r9.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "try url:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r7)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = ",mRetryTimes:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            int r10 = r0.p     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0563 }
            r10 = 1
            com.tencent.smtt.utils.TbsLog.i(r8, r9, r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.lang.String r8 = r0.i     // Catch:{ Throwable -> 0x0563 }
            boolean r8 = r7.equals(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r8 != 0) goto L_0x03b8
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r8 = r0.v     // Catch:{ Throwable -> 0x0563 }
            r8.setDownloadUrl(r7)     // Catch:{ Throwable -> 0x0563 }
        L_0x03b8:
            r0 = r30
            r0.i = r7     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.lang.String) r7)     // Catch:{ Throwable -> 0x0563 }
            r12 = 0
            r0 = r30
            boolean r7 = r0.o     // Catch:{ Throwable -> 0x0563 }
            if (r7 != 0) goto L_0x042a
            long r12 = r30.k()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r8.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "[TbsApkDownloader.startDownload] range="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.utils.TbsLog.i(r7, r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r8 = r0.l     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r7 > 0) goto L_0x0510
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r8.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "STEP 1/2 begin downloading...current"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            r9 = 1
            com.tencent.smtt.utils.TbsLog.i(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "Range"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r9.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "bytes="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "-"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0563 }
            r7.setRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x0563 }
        L_0x042a:
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r8 = r0.v     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            int r7 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1))
            if (r7 != 0) goto L_0x05db
            r7 = 0
        L_0x0435:
            r8.setDownloadCancel(r7)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            int r7 = com.tencent.smtt.utils.Apn.getApnType(r7)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = com.tencent.smtt.utils.Apn.getApnInfo(r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.lang.String r9 = r0.w     // Catch:{ Throwable -> 0x0563 }
            if (r9 != 0) goto L_0x05de
            r0 = r30
            int r9 = r0.x     // Catch:{ Throwable -> 0x0563 }
            r10 = -1
            if (r9 != r10) goto L_0x05de
            r0 = r30
            r0.w = r8     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.x = r7     // Catch:{ Throwable -> 0x0563 }
        L_0x045d:
            r0 = r30
            int r7 = r0.p     // Catch:{ Throwable -> 0x0563 }
            r8 = 1
            if (r7 < r8) goto L_0x0471
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "Referer"
            r0 = r30
            java.lang.String r9 = r0.h     // Catch:{ Throwable -> 0x0563 }
            r7.addRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x0563 }
        L_0x0471:
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            int r7 = r7.getResponseCode()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "TbsDownload"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r9.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "[TbsApkDownloader.startDownload] responseCode="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r7)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.utils.TbsLog.i(r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r8 = r0.v     // Catch:{ Throwable -> 0x0563 }
            r8.setHttpCode(r7)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x04d5
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ Throwable -> 0x0563 }
            boolean r8 = com.tencent.smtt.sdk.TbsDownloader.getOverSea(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r8 != 0) goto L_0x04d5
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ Throwable -> 0x0563 }
            int r8 = com.tencent.smtt.utils.Apn.getApnType(r8)     // Catch:{ Throwable -> 0x0563 }
            r9 = 3
            if (r8 == r9) goto L_0x04b5
            boolean r8 = com.tencent.smtt.sdk.QbSdk.getDownloadWithoutWifi()     // Catch:{ Throwable -> 0x0563 }
            if (r8 == 0) goto L_0x04bf
        L_0x04b5:
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ Throwable -> 0x0563 }
            int r8 = com.tencent.smtt.utils.Apn.getApnType(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r8 != 0) goto L_0x04d5
        L_0x04bf:
            r30.c()     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsListener r8 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ Throwable -> 0x0563 }
            if (r8 == 0) goto L_0x04cd
            com.tencent.smtt.sdk.TbsListener r8 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ Throwable -> 0x0563 }
            r9 = 111(0x6f, float:1.56E-43)
            r8.onDownloadFinish(r9)     // Catch:{ Throwable -> 0x0563 }
        L_0x04cd:
            java.lang.String r8 = "TbsDownload"
            java.lang.String r9 = "Download is canceled due to NOT_WIFI error!"
            r10 = 0
            com.tencent.smtt.utils.TbsLog.i(r8, r9, r10)     // Catch:{ Throwable -> 0x0563 }
        L_0x04d5:
            r0 = r30
            boolean r8 = r0.r     // Catch:{ Throwable -> 0x0563 }
            if (r8 == 0) goto L_0x0624
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -309(0xfffffffffffffecb, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x050a:
            r0 = r30
            java.lang.String r7 = r0.h     // Catch:{ Throwable -> 0x0563 }
            goto L_0x0380
        L_0x0510:
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r8.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "#1 STEP 1/2 begin downloading...current/total="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "/"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r10 = r0.l     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            r9 = 1
            com.tencent.smtt.utils.TbsLog.i(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "Range"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r9.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "bytes="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = "-"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r10 = r0.l     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = r9.toString()     // Catch:{ Throwable -> 0x0563 }
            r7.setRequestProperty(r8, r9)     // Catch:{ Throwable -> 0x0563 }
            goto L_0x042a
        L_0x0563:
            r7 = move-exception
        L_0x0564:
            boolean r8 = r7 instanceof javax.net.ssl.SSLHandshakeException     // Catch:{ all -> 0x0600 }
            if (r8 == 0) goto L_0x0e4e
            if (r31 != 0) goto L_0x0e4e
            r0 = r30
            java.lang.String[] r8 = r0.b     // Catch:{ all -> 0x0600 }
            if (r8 == 0) goto L_0x0e4e
            r8 = 0
            r0 = r30
            boolean r8 = r0.a((boolean) r8)     // Catch:{ all -> 0x0600 }
            if (r8 == 0) goto L_0x0e4e
            java.lang.String r8 = "TbsDownload"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x0600 }
            r9.<init>()     // Catch:{ all -> 0x0600 }
            java.lang.String r10 = "[startdownload]url:"
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0600 }
            r0 = r30
            java.lang.String r10 = r0.j     // Catch:{ all -> 0x0600 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0600 }
            java.lang.String r10 = " download exception："
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ all -> 0x0600 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0600 }
            java.lang.StringBuilder r7 = r9.append(r7)     // Catch:{ all -> 0x0600 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0600 }
            com.tencent.smtt.utils.TbsLog.e(r8, r7)     // Catch:{ all -> 0x0600 }
            r7 = 125(0x7d, float:1.75E-43)
            r8 = 0
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ all -> 0x0600 }
        L_0x05ac:
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ all -> 0x0600 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ all -> 0x0600 }
            r8 = -316(0xfffffffffffffec4, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ all -> 0x0600 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x05db:
            r7 = 1
            goto L_0x0435
        L_0x05de:
            r0 = r30
            int r9 = r0.x     // Catch:{ Throwable -> 0x0563 }
            if (r7 != r9) goto L_0x05ee
            r0 = r30
            java.lang.String r9 = r0.w     // Catch:{ Throwable -> 0x0563 }
            boolean r9 = r8.equals(r9)     // Catch:{ Throwable -> 0x0563 }
            if (r9 != 0) goto L_0x045d
        L_0x05ee:
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r9 = r0.v     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            r9.setNetworkChange(r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.w = r8     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.x = r7     // Catch:{ Throwable -> 0x0563 }
            goto L_0x045d
        L_0x0600:
            r6 = move-exception
            r20 = r4
        L_0x0603:
            if (r31 != 0) goto L_0x0623
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            java.util.Map<java.lang.String, java.lang.Object> r4 = r4.a
            java.lang.String r5 = "tbs_downloadflow"
            java.lang.Long r7 = java.lang.Long.valueOf(r20)
            r4.put(r5, r7)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
        L_0x0623:
            throw r6
        L_0x0624:
            r8 = 200(0xc8, float:2.8E-43)
            if (r7 == r8) goto L_0x062c
            r8 = 206(0xce, float:2.89E-43)
            if (r7 != r8) goto L_0x0b8d
        L_0x062c:
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            int r7 = r7.getContentLength()     // Catch:{ Throwable -> 0x0563 }
            long r8 = (long) r7     // Catch:{ Throwable -> 0x0563 }
            long r8 = r8 + r12
            r0 = r30
            r0.l = r8     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r8.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "[TbsApkDownloader.startDownload] mContentLength="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r10 = r0.l     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.utils.TbsLog.i(r7, r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r7 = r0.v     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r8 = r0.l     // Catch:{ Throwable -> 0x0563 }
            r7.setPkgSize(r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            android.content.SharedPreferences r7 = r7.mPreferences     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "tbs_apkfilesize"
            r10 = 0
            long r8 = r7.getLong(r8, r10)     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            int r7 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r7 == 0) goto L_0x0761
            r0 = r30
            long r10 = r0.l     // Catch:{ Throwable -> 0x0563 }
            int r7 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1))
            if (r7 == 0) goto L_0x0761
            java.lang.String r7 = "TbsDownload"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r10.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r11 = "DownloadBegin tbsApkFileSize="
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r10 = r10.append(r8)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r11 = "  but contentLength="
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r12 = r0.l     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0563 }
            r11 = 1
            com.tencent.smtt.utils.TbsLog.i(r7, r10, r11)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x0749
            boolean r7 = r30.n()     // Catch:{ Throwable -> 0x0563 }
            if (r7 != 0) goto L_0x06c0
            boolean r7 = com.tencent.smtt.sdk.QbSdk.getDownloadWithoutWifi()     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x0749
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            boolean r7 = com.tencent.smtt.utils.Apn.isNetworkAvailable(r7)     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x0749
        L_0x06c0:
            r0 = r30
            java.lang.String[] r7 = r0.b     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x06f1
            r7 = 0
            r0 = r30
            boolean r7 = r0.a((boolean) r7)     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x06f1
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x06f1:
            r7 = 113(0x71, float:1.58E-43)
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0563 }
            r10.<init>()     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r11 = "tbsApkFileSize="
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r10.append(r8)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r9 = "  but contentLength="
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            long r10 = r0.l     // Catch:{ Throwable -> 0x0563 }
            java.lang.StringBuilder r8 = r8.append(r10)     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x0563 }
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -310(0xfffffffffffffeca, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
        L_0x0727:
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0749:
            r7 = 101(0x65, float:1.42E-43)
            java.lang.String r8 = "WifiNetworkUnAvailable"
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -304(0xfffffffffffffed0, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            goto L_0x0727
        L_0x0761:
            r10 = 0
            r9 = 0
            r8 = 0
            java.lang.String r7 = "TbsDownload"
            java.lang.String r11 = "[TbsApkDownloader.startDownload] begin readResponse"
            com.tencent.smtt.utils.TbsLog.i(r7, r11)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ IOException -> 0x0f0c, all -> 0x0eed }
            java.io.InputStream r17 = r7.getInputStream()     // Catch:{ IOException -> 0x0f0c, all -> 0x0eed }
            if (r17 == 0) goto L_0x0a5a
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            java.lang.String r7 = r7.getContentEncoding()     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            if (r7 == 0) goto L_0x0804
            java.lang.String r9 = "gzip"
            boolean r9 = r7.contains(r9)     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            if (r9 == 0) goto L_0x0804
            java.util.zip.GZIPInputStream r16 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            r16.<init>(r17)     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
        L_0x078c:
            r7 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r7]     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            r26 = r0
            java.io.FileOutputStream r18 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            java.io.File r7 = new java.io.File     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            r0 = r30
            java.io.File r8 = r0.k     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            java.lang.String r9 = "x5.tbs.temp"
            r7.<init>(r8, r9)     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            r8 = 1
            r0 = r18
            r0.<init>(r7, r8)     // Catch:{ IOException -> 0x0f0f, all -> 0x0f01 }
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r7 = 0
            r8 = r12
            r10 = r12
            r20 = r4
        L_0x07ae:
            r0 = r30
            boolean r4 = r0.r     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x087e
            java.lang.String r4 = "TbsDownload"
            java.lang.String r5 = "STEP 1/2 begin downloading...Canceled!"
            r8 = 1
            com.tencent.smtt.utils.TbsLog.i(r4, r5, r8)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5 = -309(0xfffffffffffffecb, float:NaN)
            r4.setDownloadInterruptCode(r5)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r4 = r20
        L_0x07cb:
            if (r7 == 0) goto L_0x0a5e
            r0 = r30
            r1 = r18
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r1 = r16
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r1 = r17
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0804:
            if (r7 == 0) goto L_0x087a
            java.lang.String r9 = "deflate"
            boolean r7 = r7.contains(r9)     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            if (r7 == 0) goto L_0x087a
            java.util.zip.InflaterInputStream r16 = new java.util.zip.InflaterInputStream     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            java.util.zip.Inflater r7 = new java.util.zip.Inflater     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            r9 = 1
            r7.<init>(r9)     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            r0 = r16
            r1 = r17
            r0.<init>(r1, r7)     // Catch:{ IOException -> 0x081f, all -> 0x0ef8 }
            goto L_0x078c
        L_0x081f:
            r7 = move-exception
            r9 = r17
        L_0x0822:
            r7.printStackTrace()     // Catch:{ all -> 0x0b82 }
            boolean r11 = r7 instanceof java.net.SocketTimeoutException     // Catch:{ all -> 0x0b82 }
            if (r11 != 0) goto L_0x082d
            boolean r11 = r7 instanceof java.net.SocketException     // Catch:{ all -> 0x0b82 }
            if (r11 == 0) goto L_0x0aa8
        L_0x082d:
            r11 = 100000(0x186a0, float:1.4013E-40)
            r0 = r30
            r0.m = r11     // Catch:{ all -> 0x0b82 }
            r12 = 0
            r0 = r30
            r0.a((long) r12)     // Catch:{ all -> 0x0b82 }
            r11 = 103(0x67, float:1.44E-43)
            r0 = r30
            java.lang.String r7 = r0.a((java.lang.Throwable) r7)     // Catch:{ all -> 0x0b82 }
            r12 = 0
            r0 = r30
            r0.a(r11, r7, r12)     // Catch:{ all -> 0x0b82 }
            r0 = r30
            r0.a((java.io.Closeable) r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r9)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x087a:
            r16 = r17
            goto L_0x078c
        L_0x087e:
            r4 = 0
            r5 = 8192(0x2000, float:1.14794E-41)
            r0 = r16
            r1 = r26
            int r27 = r0.read(r1, r4, r5)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r27 > 0) goto L_0x08d3
            r0 = r30
            java.lang.String[] r4 = r0.b     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x08b6
            r4 = 1
            r0 = r30
            r1 = r19
            boolean r4 = r0.c(r4, r1)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 != 0) goto L_0x08b6
            if (r31 != 0) goto L_0x08ac
            r4 = 0
            r0 = r30
            boolean r4 = r0.a((boolean) r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x08ac
            r7 = 1
            r4 = r20
            goto L_0x07cb
        L_0x08ac:
            r4 = 1
            r0 = r30
            r0.s = r4     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r6 = 0
            r4 = r20
            goto L_0x07cb
        L_0x08b6:
            r4 = 1
            r0 = r30
            r0.s = r4     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r30
            java.lang.String[] r4 = r0.b     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x08c2
            r6 = 1
        L_0x08c2:
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5 = -311(0xfffffffffffffec9, float:NaN)
            r4.setDownloadInterruptCode(r5)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r4 = r20
            goto L_0x07cb
        L_0x08d3:
            r4 = 0
            r0 = r18
            r1 = r26
            r2 = r27
            r0.write(r1, r4, r2)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r18.flush()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r31 != 0) goto L_0x09a8
            r0 = r27
            long r4 = (long) r0
            long r4 = r4 + r20
            int r12 = (r4 > r24 ? 1 : (r4 == r24 ? 0 : -1))
            if (r12 < 0) goto L_0x0932
            java.lang.String r8 = "TbsDownload"
            java.lang.String r9 = "STEP 1/2 begin downloading...failed because you exceeded max flow!"
            r10 = 1
            com.tencent.smtt.utils.TbsLog.i(r8, r9, r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r8 = 112(0x70, float:1.57E-43)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r9.<init>()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r10 = "downloadFlow="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.StringBuilder r9 = r9.append(r4)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r10 = " downloadMaxflow="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r0 = r24
            java.lang.StringBuilder r9 = r9.append(r0)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r10 = 1
            r0 = r30
            r0.a(r8, r9, r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            com.tencent.smtt.sdk.TbsDownloadConfig r8 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r8)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r9 = -307(0xfffffffffffffecd, float:NaN)
            r8.setDownloadInterruptCode(r9)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            goto L_0x07cb
        L_0x0929:
            r7 = move-exception
            r8 = r16
            r9 = r17
            r10 = r18
            goto L_0x0822
        L_0x0932:
            r0 = r30
            android.content.Context r12 = r0.g     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            boolean r12 = com.tencent.smtt.utils.k.b((android.content.Context) r12)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            if (r12 != 0) goto L_0x09a6
            java.lang.String r8 = "TbsDownload"
            java.lang.String r9 = "DownloadEnd FreeSpace too small "
            r10 = 1
            com.tencent.smtt.utils.TbsLog.i(r8, r9, r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r8 = 105(0x69, float:1.47E-43)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r9.<init>()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r10 = "freespace="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            long r10 = com.tencent.smtt.utils.aa.a()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r10 = ",and minFreeSpace="
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r0 = r30
            android.content.Context r10 = r0.g     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            com.tencent.smtt.sdk.TbsDownloadConfig r10 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            long r10 = r10.getDownloadMinFreeSpace()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r10 = 1
            r0 = r30
            r0.a(r8, r9, r10)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r0 = r30
            android.content.Context r8 = r0.g     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            com.tencent.smtt.sdk.TbsDownloadConfig r8 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r8)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            r9 = -308(0xfffffffffffffecc, float:NaN)
            r8.setDownloadInterruptCode(r9)     // Catch:{ IOException -> 0x0929, all -> 0x0988 }
            goto L_0x07cb
        L_0x0988:
            r7 = move-exception
            r20 = r4
        L_0x098b:
            r0 = r30
            r1 = r18
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x09a1, all -> 0x0ee9 }
            r0 = r30
            r1 = r16
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x09a1, all -> 0x0ee9 }
            r0 = r30
            r1 = r17
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x09a1, all -> 0x0ee9 }
            throw r7     // Catch:{ Throwable -> 0x09a1, all -> 0x0ee9 }
        L_0x09a1:
            r7 = move-exception
            r4 = r20
            goto L_0x0564
        L_0x09a6:
            r20 = r4
        L_0x09a8:
            r0 = r27
            long r4 = (long) r0
            r0 = r30
            r1 = r22
            long r22 = r0.a((long) r1, (long) r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            long r12 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r27
            long r4 = (long) r0     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            long r8 = r8 + r4
            long r4 = r12 - r14
            r28 = 1000(0x3e8, double:4.94E-321)
            int r4 = (r4 > r28 ? 1 : (r4 == r28 ? 0 : -1))
            if (r4 <= 0) goto L_0x0f24
            java.lang.String r4 = "TbsDownload"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5.<init>()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            java.lang.String r14 = "#2 STEP 1/2 begin downloading...current/total="
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            java.lang.String r14 = "/"
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r30
            long r14 = r0.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r14 = 1
            com.tencent.smtt.utils.TbsLog.i(r4, r5, r14)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x09fe
            double r4 = (double) r8     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r30
            long r14 = r0.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            double r14 = (double) r14     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            double r4 = r4 / r14
            r14 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r4 = r4 * r14
            int r4 = (int) r4     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsListener r5 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5.onDownloadProgress(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
        L_0x09fe:
            if (r31 != 0) goto L_0x0f21
            long r4 = r8 - r10
            r14 = 1048576(0x100000, double:5.180654E-318)
            int r4 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1))
            if (r4 <= 0) goto L_0x0f21
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            boolean r4 = com.tencent.smtt.sdk.TbsDownloader.getOverSea(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 != 0) goto L_0x0a55
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            int r4 = com.tencent.smtt.utils.Apn.getApnType(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5 = 3
            if (r4 == r5) goto L_0x0a24
            boolean r4 = com.tencent.smtt.sdk.QbSdk.getDownloadWithoutWifi()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x0a2e
        L_0x0a24:
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            int r4 = com.tencent.smtt.utils.Apn.getApnType(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 != 0) goto L_0x0a55
        L_0x0a2e:
            r30.c()     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            if (r4 == 0) goto L_0x0a3c
            com.tencent.smtt.sdk.TbsListener r4 = com.tencent.smtt.sdk.QbSdk.l     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5 = 111(0x6f, float:1.56E-43)
            r4.onDownloadFinish(r5)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
        L_0x0a3c:
            java.lang.String r4 = "TbsDownload"
            java.lang.String r5 = "Download is paused due to NOT_WIFI error!"
            r8 = 0
            com.tencent.smtt.utils.TbsLog.i(r4, r5, r8)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r0 = r30
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r5 = -304(0xfffffffffffffed0, float:NaN)
            r4.setDownloadInterruptCode(r5)     // Catch:{ IOException -> 0x0f16, all -> 0x0f08 }
            r4 = r20
            goto L_0x07cb
        L_0x0a55:
            r4 = r8
        L_0x0a56:
            r14 = r12
            r10 = r4
            goto L_0x07ae
        L_0x0a5a:
            r16 = r8
            r18 = r10
        L_0x0a5e:
            r0 = r30
            r1 = r18
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r1 = r16
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r1 = r17
            r0.a((java.io.Closeable) r1)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            boolean r7 = r0.s     // Catch:{ Throwable -> 0x0563 }
            if (r7 != 0) goto L_0x0a86
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -319(0xfffffffffffffec1, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
        L_0x0a86:
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0aa8:
            if (r31 != 0) goto L_0x0b27
            r0 = r30
            android.content.Context r11 = r0.g     // Catch:{ all -> 0x0b82 }
            boolean r11 = com.tencent.smtt.utils.k.b((android.content.Context) r11)     // Catch:{ all -> 0x0b82 }
            if (r11 != 0) goto L_0x0b27
            r7 = 105(0x69, float:1.47E-43)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x0b82 }
            r11.<init>()     // Catch:{ all -> 0x0b82 }
            java.lang.String r12 = "freespace="
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0b82 }
            long r12 = com.tencent.smtt.utils.aa.a()     // Catch:{ all -> 0x0b82 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0b82 }
            java.lang.String r12 = ",and minFreeSpace="
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0b82 }
            r0 = r30
            android.content.Context r12 = r0.g     // Catch:{ all -> 0x0b82 }
            com.tencent.smtt.sdk.TbsDownloadConfig r12 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r12)     // Catch:{ all -> 0x0b82 }
            long r12 = r12.getDownloadMinFreeSpace()     // Catch:{ all -> 0x0b82 }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x0b82 }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x0b82 }
            r12 = 1
            r0 = r30
            r0.a(r7, r11, r12)     // Catch:{ all -> 0x0b82 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ all -> 0x0b82 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ all -> 0x0b82 }
            r11 = -308(0xfffffffffffffecc, float:NaN)
            r7.setDownloadInterruptCode(r11)     // Catch:{ all -> 0x0b82 }
            r0 = r30
            r0.a((java.io.Closeable) r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r9)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0b27:
            r12 = 0
            r0 = r30
            r0.a((long) r12)     // Catch:{ all -> 0x0b82 }
            boolean r11 = r30.j()     // Catch:{ all -> 0x0b82 }
            if (r11 != 0) goto L_0x0b73
            r11 = 106(0x6a, float:1.49E-43)
            r0 = r30
            java.lang.String r7 = r0.a((java.lang.Throwable) r7)     // Catch:{ all -> 0x0b82 }
            r12 = 0
            r0 = r30
            r0.a(r11, r7, r12)     // Catch:{ all -> 0x0b82 }
        L_0x0b42:
            r0 = r30
            r0.a((java.io.Closeable) r10)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((java.io.Closeable) r9)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0b73:
            r11 = 104(0x68, float:1.46E-43)
            r0 = r30
            java.lang.String r7 = r0.a((java.lang.Throwable) r7)     // Catch:{ all -> 0x0b82 }
            r12 = 0
            r0 = r30
            r0.a(r11, r7, r12)     // Catch:{ all -> 0x0b82 }
            goto L_0x0b42
        L_0x0b82:
            r7 = move-exception
            r16 = r8
            r17 = r9
            r18 = r10
            r20 = r4
            goto L_0x098b
        L_0x0b8d:
            r8 = 300(0x12c, float:4.2E-43)
            if (r7 < r8) goto L_0x0c0d
            r8 = 307(0x133, float:4.3E-43)
            if (r7 > r8) goto L_0x0c0d
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "Location"
            java.lang.String r7 = r7.getHeaderField(r8)     // Catch:{ Throwable -> 0x0563 }
            boolean r8 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Throwable -> 0x0563 }
            if (r8 != 0) goto L_0x0bd5
            r0 = r30
            r0.j = r7     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            int r7 = r0.q     // Catch:{ Throwable -> 0x0563 }
            int r7 = r7 + 1
            r0 = r30
            r0.q = r7     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0bd5:
            r7 = 124(0x7c, float:1.74E-43)
            r8 = 0
            r9 = 1
            r0 = r30
            r0.a(r7, r8, r9)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -312(0xfffffffffffffec8, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0c0d:
            r8 = 102(0x66, float:1.43E-43)
            java.lang.String r9 = java.lang.String.valueOf(r7)     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            r0 = r30
            r0.a(r8, r9, r10)     // Catch:{ Throwable -> 0x0563 }
            r8 = 416(0x1a0, float:5.83E-43)
            if (r7 != r8) goto L_0x0c8d
            r7 = 1
            r0 = r30
            r1 = r19
            boolean r7 = r0.c(r7, r1)     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x0c58
            r6 = 1
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -214(0xffffffffffffff2a, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0c58:
            r7 = 0
            r0 = r30
            r0.c((boolean) r7)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -313(0xfffffffffffffec7, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0c8d:
            r8 = 403(0x193, float:5.65E-43)
            if (r7 == r8) goto L_0x0c95
            r8 = 406(0x196, float:5.69E-43)
            if (r7 != r8) goto L_0x0cce
        L_0x0c95:
            r0 = r30
            long r8 = r0.l     // Catch:{ Throwable -> 0x0563 }
            r10 = -1
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 != 0) goto L_0x0cce
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -314(0xfffffffffffffec6, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0cce:
            r8 = 202(0xca, float:2.83E-43)
            if (r7 != r8) goto L_0x0cf4
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0cf4:
            r0 = r30
            int r8 = r0.p     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            int r9 = r0.B     // Catch:{ Throwable -> 0x0563 }
            if (r8 >= r9) goto L_0x0d6c
            r8 = 503(0x1f7, float:7.05E-43)
            if (r7 != r8) goto L_0x0d6c
            r0 = r30
            java.net.HttpURLConnection r7 = r0.t     // Catch:{ Throwable -> 0x0563 }
            java.lang.String r8 = "Retry-After"
            java.lang.String r7 = r7.getHeaderField(r8)     // Catch:{ Throwable -> 0x0563 }
            long r8 = java.lang.Long.parseLong(r7)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            r0.a((long) r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            boolean r7 = r0.r     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x0d4a
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -309(0xfffffffffffffecb, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0d4a:
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0d6c:
            r0 = r30
            int r8 = r0.p     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            int r9 = r0.B     // Catch:{ Throwable -> 0x0563 }
            if (r8 >= r9) goto L_0x0de4
            r8 = 408(0x198, float:5.72E-43)
            if (r7 == r8) goto L_0x0d86
            r8 = 504(0x1f8, float:7.06E-43)
            if (r7 == r8) goto L_0x0d86
            r8 = 502(0x1f6, float:7.03E-43)
            if (r7 == r8) goto L_0x0d86
            r8 = 408(0x198, float:5.72E-43)
            if (r7 != r8) goto L_0x0de4
        L_0x0d86:
            r8 = 0
            r0 = r30
            r0.a((long) r8)     // Catch:{ Throwable -> 0x0563 }
            r0 = r30
            boolean r7 = r0.r     // Catch:{ Throwable -> 0x0563 }
            if (r7 == 0) goto L_0x0dc2
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -309(0xfffffffffffffecb, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0dc2:
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0de4:
            long r8 = r30.k()     // Catch:{ Throwable -> 0x0563 }
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 > 0) goto L_0x0e1f
            r0 = r30
            boolean r8 = r0.o     // Catch:{ Throwable -> 0x0563 }
            if (r8 != 0) goto L_0x0e1f
            r8 = 410(0x19a, float:5.75E-43)
            if (r7 == r8) goto L_0x0e1f
            r7 = 1
            r0 = r30
            r0.o = r7     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01b8
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r9 = java.lang.Long.valueOf(r4)
            r7.put(r8, r9)
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            r7.commit()
            goto L_0x01b8
        L_0x0e1f:
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ Throwable -> 0x0563 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ Throwable -> 0x0563 }
            r8 = -315(0xfffffffffffffec5, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ Throwable -> 0x0563 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0e4e:
            r7.printStackTrace()     // Catch:{ all -> 0x0600 }
            r8 = 0
            r0 = r30
            r0.a((long) r8)     // Catch:{ all -> 0x0600 }
            r8 = 107(0x6b, float:1.5E-43)
            r0 = r30
            java.lang.String r7 = r0.a((java.lang.Throwable) r7)     // Catch:{ all -> 0x0600 }
            r9 = 0
            r0 = r30
            r0.a(r8, r7, r9)     // Catch:{ all -> 0x0600 }
            r0 = r30
            boolean r7 = r0.r     // Catch:{ all -> 0x0600 }
            if (r7 == 0) goto L_0x05ac
            r0 = r30
            android.content.Context r7 = r0.g     // Catch:{ all -> 0x0600 }
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)     // Catch:{ all -> 0x0600 }
            r8 = -309(0xfffffffffffffecb, float:NaN)
            r7.setDownloadInterruptCode(r8)     // Catch:{ all -> 0x0600 }
            if (r31 != 0) goto L_0x01c2
            r0 = r30
            android.content.Context r7 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r7 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r7)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r7.a
            java.lang.String r8 = "tbs_downloadflow"
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r7.put(r8, r4)
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r4.commit()
            goto L_0x01c2
        L_0x0e9b:
            r4 = 0
            goto L_0x01e6
        L_0x0e9e:
            r4 = 2
            goto L_0x01f2
        L_0x0ea1:
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r4 = r0.v
            r5 = 0
            r4.setPatchUpdateFlag(r5)
            goto L_0x01f5
        L_0x0eab:
            r0 = r30
            android.content.Context r4 = r0.g
            com.tencent.smtt.sdk.TbsDownloadConfig r4 = com.tencent.smtt.sdk.TbsDownloadConfig.getInstance(r4)
            r5 = -318(0xfffffffffffffec2, float:NaN)
            r4.setDownloadInterruptCode(r5)
            r4 = 0
            r0 = r30
            r0.c((boolean) r4)
            goto L_0x0214
        L_0x0ec0:
            android.content.SharedPreferences r5 = r4.mPreferences
            java.lang.String r7 = "tbs_download_failed_retrytimes"
            r8 = 0
            int r5 = r5.getInt(r7, r8)
            java.util.Map<java.lang.String, java.lang.Object> r7 = r4.a
            java.lang.String r8 = "tbs_download_failed_retrytimes"
            int r5 = r5 + 1
            java.lang.Integer r9 = java.lang.Integer.valueOf(r5)
            r7.put(r8, r9)
            int r7 = r4.getDownloadFailedMaxRetrytimes()
            if (r5 != r7) goto L_0x0234
            r0 = r30
            com.tencent.smtt.sdk.TbsLogReport$TbsLogInfo r5 = r0.v
            r7 = 2
            r5.setDownloadCancel(r7)
            goto L_0x0234
        L_0x0ee6:
            r4 = 0
            goto L_0x023e
        L_0x0ee9:
            r4 = move-exception
            r6 = r4
            goto L_0x0603
        L_0x0eed:
            r7 = move-exception
            r16 = r8
            r17 = r9
            r18 = r10
            r20 = r4
            goto L_0x098b
        L_0x0ef8:
            r7 = move-exception
            r16 = r8
            r18 = r10
            r20 = r4
            goto L_0x098b
        L_0x0f01:
            r7 = move-exception
            r18 = r10
            r20 = r4
            goto L_0x098b
        L_0x0f08:
            r4 = move-exception
            r7 = r4
            goto L_0x098b
        L_0x0f0c:
            r7 = move-exception
            goto L_0x0822
        L_0x0f0f:
            r7 = move-exception
            r8 = r16
            r9 = r17
            goto L_0x0822
        L_0x0f16:
            r7 = move-exception
            r8 = r16
            r9 = r17
            r10 = r18
            r4 = r20
            goto L_0x0822
        L_0x0f21:
            r4 = r10
            goto L_0x0a56
        L_0x0f24:
            r12 = r14
            r4 = r10
            goto L_0x0a56
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.ag.b(boolean, boolean):void");
    }

    public void c() {
        this.r = true;
        if (TbsShareManager.isThirdPartyApp(this.g)) {
            TbsLogReport.TbsLogInfo a2 = TbsLogReport.a(this.g).a();
            a2.setErrorCode(-309);
            a2.setFailDetail((Throwable) new Exception());
            if (TbsDownloadConfig.getInstance(this.g).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_DOWNLOADDECOUPLECORE, 0) == 1) {
                TbsLogReport.a(this.g).a(TbsLogReport.EventType.TYPE_DOWNLOAD_DECOUPLE, a2);
            } else {
                TbsLogReport.a(this.g).a(TbsLogReport.EventType.TYPE_DOWNLOAD, a2);
            }
        }
    }

    public void d() {
        c();
        c(false);
        c(true);
    }

    public boolean e() {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsApkDownloader.isDownloadForeground] mIsDownloadForeground=" + this.C);
        return this.C;
    }
}
