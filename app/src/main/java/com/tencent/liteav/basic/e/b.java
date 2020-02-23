package com.tencent.liteav.basic.e;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import com.amazonaws.services.s3.Headers;
import com.google.android.gms.common.Scopes;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TXCConfigCenter */
public class b {
    protected static final String a = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/txrtmp/");
    protected static b f;
    private static Context g = null;
    SharedPreferences b;
    SharedPreferences.Editor c;
    String d = "";
    long e = 0;
    private boolean h = false;
    private boolean i = false;
    private a j = new a();

    /* compiled from: TXCConfigCenter */
    private static class a {
        String a;
        /* access modifiers changed from: private */
        public int b;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d;
        /* access modifiers changed from: private */
        public int e;
        /* access modifiers changed from: private */
        public int[] f;
        /* access modifiers changed from: private */
        public int g;
        /* access modifiers changed from: private */
        public String h;
        /* access modifiers changed from: private */
        public int i;
        /* access modifiers changed from: private */
        public int j;
        /* access modifiers changed from: private */
        public int k;
        /* access modifiers changed from: private */
        public int l;
        /* access modifiers changed from: private */
        public int m;
        /* access modifiers changed from: private */
        public int n;

        private a() {
            this.b = 2;
            this.a = "";
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = null;
            this.g = 0;
            this.h = "";
            this.i = 60;
            this.j = 70;
            this.k = 80;
            this.l = 50;
            this.m = 10;
            this.n = 0;
        }
    }

    public static b a() {
        if (f == null) {
            synchronized (b.class) {
                if (f == null) {
                    f = new b();
                }
            }
        }
        return f;
    }

    public int b() {
        f();
        e();
        return this.j.b;
    }

    public int c() {
        f();
        e();
        if (this.j.f == null) {
            return 0;
        }
        int i2 = this.j.f[0];
        int[] l = this.j.f;
        int length = l.length;
        int i3 = 0;
        while (i3 < length) {
            int i4 = l[i3];
            if (i2 <= i4) {
                i4 = i2;
            }
            i3++;
            i2 = i4;
        }
        return i2;
    }

    public int d() {
        f();
        e();
        if (this.j.f == null) {
            return 0;
        }
        int i2 = this.j.f[0];
        int[] l = this.j.f;
        int length = l.length;
        int i3 = 0;
        while (i3 < length) {
            int i4 = l[i3];
            if (i2 >= i4) {
                i4 = i2;
            }
            i3++;
            i2 = i4;
        }
        return i2;
    }

    private synchronized void f() {
        synchronized (this) {
            if (h()) {
                b(true);
                if (g != null) {
                    this.b = g.getSharedPreferences("cloud_config", 0);
                    if (this.b == null || !this.b.contains("expired_time")) {
                        b(this.j);
                    } else {
                        this.c = this.b.edit();
                        try {
                            this.d = this.b.getString("last_modify", "");
                            this.e = this.b.getLong("expired_time", System.currentTimeMillis());
                            int unused = this.j.b = this.b.getInt("hw_config", 2);
                            int unused2 = this.j.c = this.b.getInt("ExposureCompensation", 0);
                            int unused3 = this.j.n = this.b.getInt("UGCSWMuxerConfig", 0);
                            int unused4 = this.j.i = this.b.getInt("CPU", 60);
                            int unused5 = this.j.j = this.b.getInt("FPS", 70);
                            int unused6 = this.j.k = this.b.getInt("CPU_MAX", 80);
                            int unused7 = this.j.l = this.b.getInt("FPS_MIN", 50);
                            int unused8 = this.j.m = this.b.getInt("CheckCount", 10);
                            this.j.a = this.b.getString("trae_config", "");
                            String string = this.b.getString("system_aec_config", "");
                            TXCLog.i("TXCConfigCenter", "system aec config:" + string);
                            if (!string.isEmpty()) {
                                try {
                                    String[] split = string.split(",");
                                    if (split != null && split.length >= 5) {
                                        int unused9 = this.j.d = Integer.valueOf(split[0]).intValue();
                                        int unused10 = this.j.e = Integer.valueOf(split[1]).intValue();
                                        String[] split2 = split[2].split("\\|");
                                        if (split2 != null) {
                                            int[] unused11 = this.j.f = new int[split2.length];
                                            for (int i2 = 0; i2 < split2.length; i2++) {
                                                this.j.f[i2] = Integer.valueOf(split2[i2].trim()).intValue();
                                            }
                                        }
                                        int unused12 = this.j.g = Integer.valueOf(split[3]).intValue();
                                        String unused13 = this.j.h = split[4];
                                    }
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                    TXCLog.e("TXCConfigCenter", "load Exception: " + e2.getMessage());
                                }
                            }
                        } catch (ClassCastException e3) {
                            this.c.clear();
                            this.c.commit();
                            TXCLog.d("TXCConfigCenter", "local config is invalid " + e3);
                        }
                    }
                } else {
                    b(this.j);
                }
                TXCLog.i("TXCConfigCenter", "load config(system aec):" + this.j.d + "," + this.j.e + "," + this.j.g + "," + this.j.h + ", model = " + Build.MODEL + ", manufacturer = " + Build.MANUFACTURER + "， board = " + Build.BOARD);
            }
        }
        return;
    }

    private void a(a aVar) {
        SharedPreferences sharedPreferences;
        if (!(this.c != null || g == null || (sharedPreferences = g.getSharedPreferences("cloud_config", 0)) == null)) {
            this.c = sharedPreferences.edit();
        }
        if (this.c != null) {
            this.c.putLong("expired_time", this.e);
            this.c.putInt("hw_config", aVar.b);
            this.c.putInt("ExposureCompensation", aVar.c);
            this.c.putInt("UGCSWMuxerConfig", aVar.n);
            this.c.putInt("CPU", aVar.i);
            this.c.putInt("FPS", aVar.j);
            this.c.putInt("CPU_MAX", aVar.k);
            this.c.putInt("FPS_MIN", aVar.l);
            this.c.putInt("CheckCount", aVar.m);
            this.c.putString("trae_config", aVar.a);
            String str = "0";
            if (aVar.f != null) {
                str = "" + aVar.f[0];
                for (int i2 = 1; i2 < aVar.f.length; i2++) {
                    str = (str + "|") + aVar.f[i2];
                }
            }
            if (aVar.h == null) {
                String unused = aVar.h = "";
            }
            this.c.putString("system_aec_config", aVar.d + "," + aVar.e + "," + str + "," + aVar.g + "," + aVar.h);
            this.c.commit();
        }
    }

    public void e() {
        if (g()) {
            a(true);
            new Thread() {
                public void run() {
                    b.this.i();
                }
            }.start();
        }
    }

    private synchronized boolean g() {
        boolean z;
        if (this.h || this.e > System.currentTimeMillis()) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private synchronized boolean h() {
        boolean z;
        if (this.i) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private synchronized void a(boolean z) {
        this.h = z;
    }

    private synchronized void b(boolean z) {
        this.i = z;
    }

    private synchronized void a(int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        this.e = System.currentTimeMillis() + ((long) (i2 * 24 * 60 * 60 * 1000));
    }

    /* access modifiers changed from: private */
    public void i() {
        File file;
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://dldir1.qq.com/hudongzhibo/liteavsvrcfg/serverconfig_en.zip").openConnection();
            if (!this.d.isEmpty()) {
                httpURLConnection.addRequestProperty(Headers.GET_OBJECT_IF_MODIFIED_SINCE, this.d);
            }
            String headerField = httpURLConnection.getHeaderField("Last-Modified");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                if (headerField != null && !headerField.isEmpty()) {
                    this.d = headerField;
                    if (this.c != null) {
                        this.c.putString("last_modify", this.d);
                    }
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byte[] a2 = a.a(byteArrayOutputStream.toByteArray(), a.a(Base64.decode("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAOnmX5h7KCQsoIQ+2ot1dIayWsmA3LU7p0kl1t5T2cbosedcsSGT+YM5bFiVBeAYbAM10WSvzZ2+oexMW7B2RcYZ1qulSR4eNXk74biDy2DmQqXK3qt1ZP4DnpiR+UXVKt6rqdtpDqRk4VGUw33/w3mMOyzkSjueewYB32n/l2JPAgMBAAECgYEA5rzfcyGTQNRRaQREPa0ZzcLmcr/Pem2lojBU3jBjtqhYz/8Nsi0yyHP+YQhpql8NNsGBlk0jjsi/HcdZ8CNMwbRfPYoe9mICe/iKMJ5P3+DtcH7AtE0ckHg01rY8pbqV9EAICijU1BwgbZh9M715HLSCeKwSWBWmpq1aQ/8l7PkCQQD5GFqrmGtMJOfTxaqS5hCHg+VsYpPsb566DEZQIJBWMP7eE58H1rphWMMSQ36c1V/iZuauYO0gYC1UlMfYHsRVAkEA8GIwlFXPG+LnkPENHo2pKORCnY7wo63hjyeQRipHhY7yUJjaPA50wDI7XCGOrJryBCVTOVszEUz4ocHQ0mOQEwJBAOnCPySVTuwQHjaQYzikCpMB5gVGpUbWoQA7kKiVRp58MFG73BwBGLtODxJOoL0RSIAwzP6MGzusxh1/2eMpTFkCQQCk5tboi0z+llPArHwRf6CRurSwHUSbJEddywg/+fUCfCNigtkC5e/VgSATfbnAUrK/gVNsP1HzBlhxruGv0jkdAkEAjNSVhjcoLg1JodbhBmD16vsAUzJpDR6EZIeiXj4pN+hKiDq9+aHEtMxtjFXiqbdKkrUjrzfZKrzQm0wy950BUw==".getBytes("UTF-8"), 2)));
                        synchronized (b.class) {
                            file = new File(a, "serverconfig_dec.zip");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(a2);
                            fileOutputStream.close();
                        }
                        a(a(file));
                        return;
                    }
                }
            } else if (responseCode == 304) {
                a(false);
                TXCLog.d("TXCConfigCenter", new StringBuilder().append("fetchconfig Not-Modified-Since ").append(this.d).toString() == null ? "" : this.d);
            }
        } catch (Exception e2) {
            TXCLog.e("TXCConfigCenter", "fetchconfig catch exception " + e2);
            a(false);
        }
    }

    private static String a(File file) throws IOException {
        synchronized (b.class) {
            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (!zipEntry.isDirectory()) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    byte[] bArr = new byte[1048576];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read > 0) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            inputStream.close();
                            String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                            return byteArrayOutputStream2;
                        }
                    }
                }
            }
            return "";
        }
    }

    private void a(String str) {
        a(false);
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i2 = 1;
            if (jSONObject.has("UpdateFrequency")) {
                i2 = jSONObject.getInt("UpdateFrequency");
            }
            a(i2);
            a aVar = new a();
            g(jSONObject, aVar);
            a(aVar);
        } catch (JSONException e2) {
            TXCLog.w("TXCConfigCenter", "parseRespon catch ecxeption" + e2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009a  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0113 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(org.json.JSONObject r13, com.tencent.liteav.basic.e.b.a r14) throws org.json.JSONException {
        /*
            r12 = this;
            r5 = 1
            r3 = 0
            r2 = 0
            int unused = r14.d = r2
            int unused = r14.e = r2
            int[] unused = r14.f = r3
            int unused = r14.g = r2
            java.lang.String r0 = ""
            java.lang.String unused = r14.h = r0
            java.lang.String r0 = "SystemAECConfig"
            boolean r0 = r13.has(r0)
            if (r0 == 0) goto L_0x013b
            java.lang.String r0 = "SystemAECConfig"
            org.json.JSONObject r0 = r13.getJSONObject(r0)
            java.lang.String r1 = "InfoList"
            boolean r1 = r0.has(r1)
            if (r1 == 0) goto L_0x013b
            java.lang.String r1 = "InfoList"
            org.json.JSONArray r8 = r0.getJSONArray(r1)
            r1 = r2
            r0 = r3
        L_0x0032:
            int r4 = r8.length()
            if (r1 >= r4) goto L_0x013a
            org.json.JSONObject r9 = r8.getJSONObject(r1)
            if (r9 == 0) goto L_0x0113
            java.lang.String r4 = "Manufacture"
            java.lang.String r4 = r9.getString(r4)
            java.lang.String r6 = android.os.Build.MANUFACTURER
            boolean r4 = r4.equalsIgnoreCase(r6)
            if (r4 == 0) goto L_0x0113
            java.lang.String r4 = "WhiteList"
            org.json.JSONArray r6 = r9.optJSONArray(r4)
            if (r6 == 0) goto L_0x01b7
            r4 = r2
        L_0x0055:
            int r7 = r6.length()
            if (r4 >= r7) goto L_0x01b7
            org.json.JSONObject r7 = r6.optJSONObject(r4)
            if (r7 == 0) goto L_0x00e3
            java.lang.String r10 = "Model"
            java.lang.String r7 = r7.optString(r10)
            if (r7 == 0) goto L_0x00e3
            java.lang.String r10 = android.os.Build.MODEL
            boolean r7 = r10.equals(r7)
            if (r7 == 0) goto L_0x00e3
            r4 = r5
        L_0x0072:
            java.lang.String r6 = "BlackList"
            org.json.JSONArray r7 = r9.optJSONArray(r6)
            if (r7 == 0) goto L_0x01b4
            r6 = r2
        L_0x007b:
            int r10 = r7.length()
            if (r6 >= r10) goto L_0x01b4
            org.json.JSONObject r10 = r7.optJSONObject(r6)
            if (r10 == 0) goto L_0x00e7
            java.lang.String r11 = "Model"
            java.lang.String r10 = r10.optString(r11)
            if (r10 == 0) goto L_0x00e7
            java.lang.String r11 = android.os.Build.MODEL
            boolean r10 = r11.equals(r10)
            if (r10 == 0) goto L_0x00e7
            r7 = r5
        L_0x0098:
            if (r4 == 0) goto L_0x0100
            java.lang.String r4 = "SystemAEC"
            r6 = 0
            int r4 = r9.optInt(r4, r6)     // Catch:{ Exception -> 0x01b0 }
            int unused = r14.d = r4     // Catch:{ Exception -> 0x01b0 }
            java.lang.String r4 = "AGC"
            r6 = 0
            int r4 = r9.optInt(r4, r6)     // Catch:{ Exception -> 0x01b0 }
            int unused = r14.e = r4     // Catch:{ Exception -> 0x01b0 }
            java.lang.String r4 = "SampleRate"
            java.lang.String r6 = ""
            java.lang.String r6 = r9.optString(r4, r6)     // Catch:{ Exception -> 0x01b0 }
            boolean r0 = r6.isEmpty()     // Catch:{ Exception -> 0x0117 }
            if (r0 != 0) goto L_0x00ea
            java.lang.String r0 = "\\|"
            java.lang.String[] r4 = r6.split(r0)     // Catch:{ Exception -> 0x0117 }
            int r0 = r4.length     // Catch:{ Exception -> 0x0117 }
            int[] r0 = new int[r0]     // Catch:{ Exception -> 0x0117 }
            int[] unused = r14.f = r0     // Catch:{ Exception -> 0x0117 }
            r0 = r2
        L_0x00c9:
            int r10 = r4.length     // Catch:{ Exception -> 0x0117 }
            if (r0 >= r10) goto L_0x00ea
            int[] r10 = r14.f     // Catch:{ Exception -> 0x0117 }
            r11 = r4[r0]     // Catch:{ Exception -> 0x0117 }
            java.lang.String r11 = r11.trim()     // Catch:{ Exception -> 0x0117 }
            java.lang.Integer r11 = java.lang.Integer.valueOf(r11)     // Catch:{ Exception -> 0x0117 }
            int r11 = r11.intValue()     // Catch:{ Exception -> 0x0117 }
            r10[r0] = r11     // Catch:{ Exception -> 0x0117 }
            int r0 = r0 + 1
            goto L_0x00c9
        L_0x00e3:
            int r4 = r4 + 1
            goto L_0x0055
        L_0x00e7:
            int r6 = r6 + 1
            goto L_0x007b
        L_0x00ea:
            java.lang.String r0 = "HWAACCodec"
            r4 = 0
            int r0 = r9.optInt(r0, r4)     // Catch:{ Exception -> 0x0117 }
            int unused = r14.g = r0     // Catch:{ Exception -> 0x0117 }
            java.lang.String r0 = "SceneType"
            java.lang.String r4 = ""
            java.lang.String r0 = r9.optString(r0, r4)     // Catch:{ Exception -> 0x0117 }
            java.lang.String unused = r14.h = r0     // Catch:{ Exception -> 0x0117 }
            r0 = r6
        L_0x0100:
            if (r7 == 0) goto L_0x0113
            int unused = r14.d = r2
            int unused = r14.e = r2
            int[] unused = r14.f = r3
            int unused = r14.g = r2
            java.lang.String r4 = ""
            java.lang.String unused = r14.h = r4
        L_0x0113:
            int r1 = r1 + 1
            goto L_0x0032
        L_0x0117:
            r0 = move-exception
            r4 = r0
        L_0x0119:
            r4.printStackTrace()
            java.lang.String r0 = "TXCConfigCenter"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "parseSysAECConfig Exception: "
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.String r4 = r4.getMessage()
            java.lang.StringBuilder r4 = r9.append(r4)
            java.lang.String r4 = r4.toString()
            com.tencent.liteav.basic.log.TXCLog.e(r0, r4)
            r0 = r6
            goto L_0x0100
        L_0x013a:
            r3 = r0
        L_0x013b:
            java.lang.String r0 = "TXCConfigCenter"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "system aec config1:"
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r14.d
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r14.e
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)
            int r2 = r14.g
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ","
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r14.h
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", model = "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = android.os.Build.MODEL
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = ", manufacturer = "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = android.os.Build.MANUFACTURER
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "， board = "
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = android.os.Build.BOARD
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.tencent.liteav.basic.log.TXCLog.i(r0, r1)
            return
        L_0x01b0:
            r4 = move-exception
            r6 = r0
            goto L_0x0119
        L_0x01b4:
            r7 = r2
            goto L_0x0098
        L_0x01b7:
            r4 = r2
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.e.b.a(org.json.JSONObject, com.tencent.liteav.basic.e.b$a):void");
    }

    private void b(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("TraeConfig")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("TraeConfig");
            if (jSONObject2.has("InfoList")) {
                JSONArray jSONArray = jSONObject2.getJSONArray("InfoList");
                int i2 = 0;
                while (i2 < jSONArray.length()) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i2);
                    if (jSONObject3 == null || !jSONObject3.getString("MachineType").equals(Build.MODEL)) {
                        i2++;
                    } else {
                        aVar.a = jSONObject3.getString("ConfigValue");
                        TXCLog.d("TXCConfigCenter", "parseTRAEConfig get TRAE config: " + aVar.a);
                        return;
                    }
                }
            }
        }
    }

    private void c(JSONObject jSONObject, a aVar) throws JSONException {
        int unused = aVar.i = 60;
        int unused2 = aVar.j = 70;
        int unused3 = aVar.k = 80;
        int unused4 = aVar.l = 50;
        int unused5 = aVar.m = 10;
        if (jSONObject.has("HWWhiteList")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("HWWhiteList");
            if (jSONObject2.has("SWToHWThreshold")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("SWToHWThreshold");
                if (jSONObject3.has("CPU")) {
                    int unused6 = aVar.i = jSONObject3.getInt("CPU");
                    TXCLog.d("TXCConfigCenter", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + aVar.i);
                }
                if (jSONObject3.has("FPS")) {
                    int unused7 = aVar.j = jSONObject3.getInt("FPS");
                    TXCLog.d("TXCConfigCenter", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + aVar.j);
                }
                if (jSONObject3.has("CPU_MAX")) {
                    int unused8 = aVar.k = jSONObject3.getInt("CPU_MAX");
                    TXCLog.d("TXCConfigCenter", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + aVar.k);
                }
                if (jSONObject3.has("FPS_MIN")) {
                    int unused9 = aVar.l = jSONObject3.getInt("FPS_MIN");
                    TXCLog.d("TXCConfigCenter", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + aVar.l);
                }
                if (jSONObject3.has("CheckCount")) {
                    int unused10 = aVar.m = jSONObject3.getInt("CheckCount");
                    TXCLog.d("TXCConfigCenter", "parseAutoSWHWConfig get SWToHWThreshold.CheckCount:" + aVar.m);
                }
            }
        }
    }

    private void d(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("ExposureWhiteConfig")) {
            JSONArray jSONArray = jSONObject.getJSONObject("ExposureWhiteConfig").getJSONArray("InfoList");
            int i2 = 0;
            while (true) {
                if (i2 >= jSONArray.length()) {
                    break;
                }
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                if (jSONObject2.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) && jSONObject2.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                    int unused = aVar.c = jSONObject2.getInt("ExposureCompensation");
                    TXCLog.d("TXCConfigCenter", "parseExposureConfig get exposure config: " + aVar.c);
                    break;
                }
                i2++;
            }
            if (g != null) {
                g.sendBroadcast(new Intent("com.tencent.liteav.basic.serverconfig.get"));
            }
        }
    }

    private void e(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("HWBlackConfig")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("HWBlackConfig");
            int i2 = 16;
            if (jSONObject2.has("HWMiniSupportAPI")) {
                i2 = jSONObject2.getInt("HWMiniSupportAPI");
            }
            if (Build.VERSION.SDK_INT < i2) {
                int unused = aVar.b = 0;
                return;
            }
            JSONArray jSONArray = jSONObject2.getJSONArray("InfoList");
            int i3 = 0;
            while (i3 < jSONArray.length()) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i3);
                if (!jSONObject3.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) || !jSONObject3.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                    i3++;
                } else if (jSONObject3.has(Scopes.PROFILE)) {
                    int unused2 = aVar.b = 1;
                    TXCLog.d("TXCConfigCenter", "parseHWBlackConfig get HWBlack config: " + aVar.b);
                    return;
                } else {
                    int unused3 = aVar.b = 0;
                    TXCLog.d("TXCConfigCenter", "parseHWBlackConfig get HWBlack config: " + aVar.b);
                    return;
                }
            }
        }
    }

    private void f(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("UGCSWMuxerConfig")) {
            JSONArray jSONArray = jSONObject.getJSONObject("UGCSWMuxerConfig").getJSONArray("InfoList");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                if (jSONArray.getJSONObject(i2).getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER)) {
                    int unused = aVar.n = 1;
                    return;
                }
            }
        }
    }

    private void b(a aVar) {
        try {
            g(new JSONObject("{\n    \"version\": 3,\n\t\"UpdateFrequency\": 1,\n    \"UGCSWMuxerConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\"\n            }\n        ]\n    },\n    \"HWBlackConfig\": {\n        \"HWMiniSupportAPI\": 17,\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"P6-U06\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"SCH-I939(S3)\"\n            },\n            {\n                \"Manufacture\": \"VIVO\",\n                \"Model\": \"vivo X5Pro D\"\n            },\n            {\n                \"Manufacture\": \"金立\",\n                \"Model\": \"GN9006\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"A7000\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI NXT-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI MHA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L29\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-TL00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n             {\n                 \"Manufacture\": \"HUAWEI\",\n                 \"Model\": \"HUAWEI GRA-UL00\"\n             }\n        ]\n    },\n    \"ExposureWhiteConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"Meizu\",\n                \"Model\": \"MX4 Pro\",\n                \"ExposureCompensation\": 1\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3\",\n                \"ExposureCompensation\": 30\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3C\",\n                \"ExposureCompensation\": 30\n            }\n        ]\n    },\n    \"SystemAECConfig\": {\n        \"__comment__\":\"SceneType(开启系统aec的场景，1为连麦，2为通话)\",\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9\"},{\"Model\":\"vivo Y67A\"}],\n                \"BlackList\": [],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9Plus\"},{\"Model\":\"vivo X7Plus\"},{\"Model\":\"vivo X7\"}],\n                \"BlackList\": [{\"Model\":\"vivo Y51A\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"WhiteList\": [{\"Model\":\"VTR-TL00\"},{\"Model\":\"HUAWEI GRA-UL00\"},{\"Model\":\"HUAWEI NXT-AL10\"},{\"Model\":\"PLK-AL10\"},{\"Model\":\"PLK-UL00\"},{\"Model\":\"EVA-AL10\"},{\"Model\":\"HUAWEI MT7-TL10\"}],\n                \"BlackList\": [{\"Model\":\"MHA-AL00\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 2\"},{\"Model\":\"Redmi Note 4\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 3\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"MI 4\"},{\"Model\":\"MI 3C\"},{\"Model\":\"Mi-4c\"}],\n                \"BlackList\": [{\"Model\":\"MI 6\"},{\"Model\":\"Redmi 4A\"},{\"Model\":\"MI 5X\"},{\"Model\":\"MI 5\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Google\",\n                \"WhiteList\": [{\"Model\":\"Pixel XL\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"samsung\",\n                \"WhiteList\": [{\"Model\":\"SM-G9350\"},{\"Model\":\"SM-G9500\"},{\"Model\":\"SM-G950U\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                 \"Manufacture\": \"LeMobile\",\n                 \"WhiteList\": [{\"Model\":\"X620\"}],\n                 \"SystemAEC\": 1,\n                 \"AGC\": 0,\n                 \"SampleRate\": \"48000|16000\",\n                 \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n             {\n                  \"Manufacture\": \"asus\",\n                  \"WhiteList\": [{\"Model\":\"ASUS_Z00ADB\"}],\n                  \"SystemAEC\": 1,\n                  \"AGC\": 0,\n                  \"SampleRate\": \"48000|16000\",\n                  \"HWAACCodec\": 1,\n                 \"SceneType\": \"1|2\"\n             }\n        ]\n    },\n    \"HWWhiteList\": {\n        \"SWToHWThreshold\": {\n            \"CPU_MAX\": 80,\n            \"FPS_MIN\": 50,\n            \"CPU\": 20,\n            \"FPS\": 70,\n            \"CheckCount\": 10,\n            \"__comment__\": \"软编根据性能切硬编的阈值，avgTotalCPU >= CPU_MAX || avgFPS <= FPS_MIN || (avgAppCPU >= CPU && avgFPS <= FPS) 性能指标满足上述条件则切硬编。CheckCount表示需要做X次有效性能数据采集。\"\n\t\t}\n    },\n    \"TraeConfig\": {\n        \"InfoList\": [\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-AL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-CL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-DL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-TL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-CL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-TL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-TL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI NXT-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"PLK-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"JTY\",\n            \"MachineType\": \"KT096H\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LENOVO\",\n            \"MachineType\": \"Lenovo K900\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"MachineType\": \"Nexus 5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"MachineType\": \"Nexus 5X\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"M5\",\n            \"MachineType\": \"X5 R1\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"MachineType\": \"MX4 Pro\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"A31\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO A33\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO A59s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R7\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9km\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9m\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\naec {\\r\\nUseHQAEC y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9tm\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-G9350\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\nvolume 2.0\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-N9108V\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-N9200\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"TCL\",\n            \"MachineType\": \"TCL P501M\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 4LTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 4W\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\nvolume 0.25\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5s Plus\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI NOTE LTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"Redmi Note 3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"ZTE\",\n            \"MachineType\": \"ZTE N928Dt\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\"\n          }\n        ]\n    }\n}"), aVar);
        } catch (JSONException e2) {
            TXCLog.w("TXCConfigCenter", "parseRespon catch ecxeption" + e2);
        }
    }

    private void g(JSONObject jSONObject, a aVar) {
        if (jSONObject != null && aVar != null) {
            try {
                e(jSONObject, aVar);
                d(jSONObject, aVar);
                c(jSONObject, aVar);
                a(jSONObject, aVar);
                b(jSONObject, aVar);
                f(jSONObject, aVar);
            } catch (JSONException e2) {
                TXCLog.w("TXCConfigCenter", "parse catch ecxeption" + e2);
            }
        }
    }
}
