package com.tencent.liteav.basic.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.safetynet.SafetyNetStatusCodes;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.rtmp2.TXLiveConstants;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.UUID;

/* compiled from: TXCSystemUtil */
public class a {
    private static float a = 0.0f;
    private static float b = 0.0f;
    private static float c = 0.0f;
    private static float d = 0.0f;
    private static float e = 0.0f;
    private static float f = 0.0f;
    private static boolean g = true;
    private static int[] h = new int[2];
    private static long i = 0;
    private static String j = "";
    private static final Object k = new Object();
    private static boolean l = false;
    private static int[] m = {96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, SafetyNetStatusCodes.SAFE_BROWSING_UNSUPPORTED_THREAT_TYPES, 11025, 8000, 7350};

    private static long f() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            String[] split = readLine.split(" ");
            if (split == null || TextUtils.isEmpty(split[13])) {
                return 0;
            }
            return Long.parseLong(split[16]) + Long.parseLong(split[13]) + Long.parseLong(split[14]) + Long.parseLong(split[15]);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void g() {
        /*
            r2 = 0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 26
            if (r0 >= r1) goto L_0x0093
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0083 }
            java.io.InputStreamReader r1 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0083 }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0083 }
            java.lang.String r5 = "/proc/stat"
            r4.<init>(r5)     // Catch:{ Exception -> 0x0083 }
            r1.<init>(r4)     // Catch:{ Exception -> 0x0083 }
            r4 = 1000(0x3e8, float:1.401E-42)
            r0.<init>(r1, r4)     // Catch:{ Exception -> 0x0083 }
            if (r0 == 0) goto L_0x0093
            java.lang.String r1 = r0.readLine()     // Catch:{ Exception -> 0x0083 }
            r0.close()     // Catch:{ Exception -> 0x0083 }
            java.lang.String r0 = " "
            java.lang.String[] r0 = r1.split(r0)     // Catch:{ Exception -> 0x0083 }
            if (r0 == 0) goto L_0x0093
            int r1 = r0.length     // Catch:{ Exception -> 0x0083 }
            r4 = 9
            if (r1 < r4) goto L_0x0093
            r1 = 2
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r4 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            r1 = 3
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 4
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 6
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 5
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 7
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 8
            r1 = r0[r1]     // Catch:{ Exception -> 0x0083 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0083 }
            long r4 = r4 + r6
            r1 = 5
            r1 = r0[r1]     // Catch:{ Exception -> 0x0091 }
            long r6 = java.lang.Long.parseLong(r1)     // Catch:{ Exception -> 0x0091 }
            r1 = 6
            r0 = r0[r1]     // Catch:{ Exception -> 0x0091 }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ Exception -> 0x0091 }
            long r0 = r0 + r6
        L_0x0078:
            boolean r2 = g
            if (r2 == 0) goto L_0x008a
            float r2 = (float) r4
            a = r2
            float r0 = (float) r0
            e = r0
        L_0x0082:
            return
        L_0x0083:
            r0 = move-exception
            r4 = r2
        L_0x0085:
            r0.printStackTrace()
            r0 = r2
            goto L_0x0078
        L_0x008a:
            float r2 = (float) r4
            b = r2
            float r0 = (float) r0
            f = r0
            goto L_0x0082
        L_0x0091:
            r0 = move-exception
            goto L_0x0085
        L_0x0093:
            r0 = r2
            r4 = r2
            goto L_0x0078
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.a.g():void");
    }

    public static int[] a() {
        float f2;
        float f3;
        if (i != 0 && TXCTimeUtil.getTimeTick() - i < 2000) {
            return h;
        }
        int[] iArr = new int[2];
        if (g) {
            c = (float) f();
            g();
            g = false;
            iArr[0] = 0;
            iArr[1] = 0;
            return iArr;
        }
        d = (float) f();
        g();
        if (b != a) {
            float f4 = ((d - c) * 100.0f) / (b - a);
            f2 = (((b - a) - (f - e)) * 100.0f) / (b - a);
            f3 = f4;
        } else {
            f2 = 0.0f;
            f3 = 0.0f;
        }
        a = b;
        c = d;
        e = f;
        iArr[0] = (int) (f3 * 10.0f);
        iArr[1] = (int) (f2 * 10.0f);
        h[0] = iArr[0];
        h[1] = iArr[1];
        i = TXCTimeUtil.getTimeTick();
        return iArr;
    }

    public static String a(Context context) {
        return TXCDRApi.getSimulateIDFA(context);
    }

    public static String b(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static int c(Context context) {
        if (context == null) {
            return 255;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 255;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 255;
        }
        switch (telephonyManager.getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 4;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 2;
            default:
                return 2;
        }
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String c() {
        return UUID.randomUUID().toString();
    }

    public static String d(Context context) {
        return TXCDRApi.getDevUUID(context, TXCDRApi.getSimulateIDFA(context));
    }

    public static void a(WeakReference<com.tencent.liteav.basic.c.a> weakReference, long j2, int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putLong("EVT_USERID", j2);
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        a(weakReference, i2, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.c.a> weakReference, int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        a(weakReference, i2, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.c.a> weakReference, int i2, Bundle bundle) {
        com.tencent.liteav.basic.c.a aVar;
        if (weakReference != null && (aVar = (com.tencent.liteav.basic.c.a) weakReference.get()) != null) {
            aVar.onNotifyEvent(i2, bundle);
        }
    }

    public static void a(WeakReference<com.tencent.liteav.basic.c.a> weakReference, long j2, int i2, Bundle bundle) {
        com.tencent.liteav.basic.c.a aVar;
        if (weakReference != null && (aVar = (com.tencent.liteav.basic.c.a) weakReference.get()) != null) {
            bundle.putLong("EVT_USERID", j2);
            aVar.onNotifyEvent(i2, bundle);
        }
    }

    public static void d() {
        synchronized (k) {
            if (!l) {
                a("stlport_shared");
                a("saturn");
                a("txffmpeg");
                a("liteavsdk");
                l = true;
            }
        }
    }

    public static void a(String str) {
        try {
            System.loadLibrary(str);
        } catch (Error e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
            b(j, str);
        } catch (Exception e3) {
            Log.d("NativeLoad", "load library : " + e3.toString());
            b(j, str);
        }
    }

    private static void b(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                System.load(str + "/lib" + str2 + ".so");
            }
        } catch (Error e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
        } catch (Exception e3) {
            Log.d("NativeLoad", "load library : " + e3.toString());
        }
    }

    public static void b(String str) {
        j = str;
    }

    public static String e() {
        return j;
    }

    public static int a(int i2) {
        int i3 = 0;
        while (i3 < m.length && m[i3] != i2) {
            i3++;
        }
        if (i3 >= m.length) {
            return -1;
        }
        return i3;
    }

    @TargetApi(16)
    public static MediaFormat a(int i2, int i3, int i4) {
        int a2 = a(i2);
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put(0, (byte) ((i4 << 3) | (a2 >> 1)));
        allocate.put(1, (byte) (((a2 & 1) << 7) | (i3 << 3)));
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i2, i3);
        createAudioFormat.setInteger("channel-count", i3);
        createAudioFormat.setInteger("sample-rate", i2);
        createAudioFormat.setByteBuffer("csd-0", allocate);
        return createAudioFormat;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v9, resolved type: java.io.FileOutputStream} */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r4v2, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r4v5 */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0074 A[SYNTHETIC, Splitter:B:42:0x0074] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0087 A[SYNTHETIC, Splitter:B:51:0x0087] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:68:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.lang.String r6, java.lang.String r7) {
        /*
            r3 = 0
            r0 = 0
            r1 = 0
            if (r6 == 0) goto L_0x0007
            if (r7 != 0) goto L_0x0017
        L_0x0007:
            if (r3 == 0) goto L_0x000c
            r1.close()     // Catch:{ IOException -> 0x0012 }
        L_0x000c:
            if (r3 == 0) goto L_0x0011
            r3.release()
        L_0x0011:
            return r0
        L_0x0012:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x000c
        L_0x0017:
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x006c, all -> 0x0082 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x006c, all -> 0x0082 }
            boolean r2 = r2.exists()     // Catch:{ Exception -> 0x006c, all -> 0x0082 }
            if (r2 != 0) goto L_0x0032
            if (r3 == 0) goto L_0x0027
            r1.close()     // Catch:{ IOException -> 0x002d }
        L_0x0027:
            if (r3 == 0) goto L_0x0011
            r3.release()
            goto L_0x0011
        L_0x002d:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0027
        L_0x0032:
            android.media.MediaMetadataRetriever r2 = new android.media.MediaMetadataRetriever     // Catch:{ Exception -> 0x006c, all -> 0x0082 }
            r2.<init>()     // Catch:{ Exception -> 0x006c, all -> 0x0082 }
            r2.setDataSource(r6)     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            android.graphics.Bitmap r1 = r2.getFrameAtTime()     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            java.io.File r5 = new java.io.File     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            r5.<init>(r7)     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            boolean r4 = r5.exists()     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            if (r4 == 0) goto L_0x004c
            r5.delete()     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
        L_0x004c:
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            r4.<init>(r5)     // Catch:{ Exception -> 0x009a, all -> 0x0095 }
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x009d }
            r5 = 100
            r1.compress(r3, r5, r4)     // Catch:{ Exception -> 0x009d }
            r4.flush()     // Catch:{ Exception -> 0x009d }
            if (r4 == 0) goto L_0x0060
            r4.close()     // Catch:{ IOException -> 0x0067 }
        L_0x0060:
            if (r2 == 0) goto L_0x0065
            r2.release()
        L_0x0065:
            r0 = 1
            goto L_0x0011
        L_0x0067:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x0060
        L_0x006c:
            r1 = move-exception
            r2 = r3
            r4 = r3
        L_0x006f:
            r1.printStackTrace()     // Catch:{ all -> 0x0098 }
            if (r4 == 0) goto L_0x0077
            r4.close()     // Catch:{ IOException -> 0x007d }
        L_0x0077:
            if (r2 == 0) goto L_0x0011
            r2.release()
            goto L_0x0011
        L_0x007d:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0077
        L_0x0082:
            r0 = move-exception
            r2 = r3
            r4 = r3
        L_0x0085:
            if (r4 == 0) goto L_0x008a
            r4.close()     // Catch:{ IOException -> 0x0090 }
        L_0x008a:
            if (r2 == 0) goto L_0x008f
            r2.release()
        L_0x008f:
            throw r0
        L_0x0090:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x008a
        L_0x0095:
            r0 = move-exception
            r4 = r3
            goto L_0x0085
        L_0x0098:
            r0 = move-exception
            goto L_0x0085
        L_0x009a:
            r1 = move-exception
            r4 = r3
            goto L_0x006f
        L_0x009d:
            r1 = move-exception
            goto L_0x006f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.a.a(java.lang.String, java.lang.String):boolean");
    }
}
