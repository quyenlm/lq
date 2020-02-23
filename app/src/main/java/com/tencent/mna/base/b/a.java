package com.tencent.mna.base.b;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.m;
import java.io.File;

/* compiled from: LoadManager */
public class a {
    private static final String a = (Environment.getExternalStorageDirectory().toString() + File.separator + "mna" + File.separator + "mna_load");

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008e A[SYNTHETIC, Splitter:B:44:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0093 A[Catch:{ IOException -> 0x0098 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00a1 A[SYNTHETIC, Splitter:B:54:0x00a1] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00a6 A[Catch:{ IOException -> 0x00ac }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00aa  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean a(java.io.File r8, java.io.File r9) {
        /*
            r4 = 0
            r6 = 1
            r7 = 0
            if (r8 == 0) goto L_0x0007
            if (r9 != 0) goto L_0x0008
        L_0x0007:
            return r7
        L_0x0008:
            boolean r0 = r8.isFile()
            if (r0 == 0) goto L_0x0007
            boolean r0 = r8.canRead()
            if (r0 == 0) goto L_0x0007
            boolean r0 = r9.exists()
            if (r0 == 0) goto L_0x005b
            r9.delete()
        L_0x001d:
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Exception -> 0x006e, all -> 0x009b }
            r0.<init>(r8)     // Catch:{ Exception -> 0x006e, all -> 0x009b }
            java.nio.channels.FileChannel r1 = r0.getChannel()     // Catch:{ Exception -> 0x006e, all -> 0x009b }
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00b8, all -> 0x00ae }
            r2 = 0
            r0.<init>(r9, r2)     // Catch:{ Exception -> 0x00b8, all -> 0x00ae }
            java.nio.channels.FileChannel r0 = r0.getChannel()     // Catch:{ Exception -> 0x00b8, all -> 0x00ae }
            r2 = 0
            long r4 = r1.size()     // Catch:{ Exception -> 0x00bc, all -> 0x00b2 }
            r0.transferFrom(r1, r2, r4)     // Catch:{ Exception -> 0x00bc, all -> 0x00b2 }
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ IOException -> 0x006b }
        L_0x003e:
            if (r0 == 0) goto L_0x0043
            r0.close()     // Catch:{ IOException -> 0x006b }
        L_0x0043:
            r0 = r6
        L_0x0044:
            if (r0 == 0) goto L_0x00aa
            boolean r0 = r9.exists()
            if (r0 == 0) goto L_0x00aa
            boolean r0 = r9.isFile()
            if (r0 == 0) goto L_0x00aa
            boolean r0 = r9.canRead()
            if (r0 == 0) goto L_0x00aa
            r0 = r6
        L_0x0059:
            r7 = r0
            goto L_0x0007
        L_0x005b:
            java.io.File r0 = r9.getParentFile()
            if (r0 == 0) goto L_0x001d
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x001d
            r0.mkdirs()
            goto L_0x001d
        L_0x006b:
            r0 = move-exception
            r0 = r6
            goto L_0x0044
        L_0x006e:
            r0 = move-exception
            r2 = r0
            r3 = r4
            r1 = r4
        L_0x0072:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b5 }
            r0.<init>()     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = "[copyFileByFullPath] exception:"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ all -> 0x00b5 }
            java.lang.String r2 = r2.getMessage()     // Catch:{ all -> 0x00b5 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x00b5 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b5 }
            com.tencent.mna.base.utils.h.d(r0)     // Catch:{ all -> 0x00b5 }
            if (r1 == 0) goto L_0x0091
            r1.close()     // Catch:{ IOException -> 0x0098 }
        L_0x0091:
            if (r3 == 0) goto L_0x0096
            r3.close()     // Catch:{ IOException -> 0x0098 }
        L_0x0096:
            r0 = r7
            goto L_0x0044
        L_0x0098:
            r0 = move-exception
            r0 = r7
            goto L_0x0044
        L_0x009b:
            r0 = move-exception
            r2 = r0
            r3 = r4
            r1 = r4
        L_0x009f:
            if (r1 == 0) goto L_0x00a4
            r1.close()     // Catch:{ IOException -> 0x00ac }
        L_0x00a4:
            if (r3 == 0) goto L_0x00a9
            r3.close()     // Catch:{ IOException -> 0x00ac }
        L_0x00a9:
            throw r2
        L_0x00aa:
            r0 = r7
            goto L_0x0059
        L_0x00ac:
            r0 = move-exception
            goto L_0x00a9
        L_0x00ae:
            r0 = move-exception
            r2 = r0
            r3 = r4
            goto L_0x009f
        L_0x00b2:
            r2 = move-exception
            r3 = r0
            goto L_0x009f
        L_0x00b5:
            r0 = move-exception
            r2 = r0
            goto L_0x009f
        L_0x00b8:
            r0 = move-exception
            r2 = r0
            r3 = r4
            goto L_0x0072
        L_0x00bc:
            r2 = move-exception
            r3 = r0
            goto L_0x0072
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.b.a.a(java.io.File, java.io.File):boolean");
    }

    private static boolean a(Context context, String str) {
        int i;
        try {
            i = Settings.System.getInt(context.getContentResolver(), str, 0);
        } catch (Throwable th) {
            i = 0;
        }
        if (i == 1) {
            return true;
        }
        return false;
    }

    private static boolean c(Context context) {
        return a(context, "system_moc_mna_loadlib");
    }

    private static boolean d(Context context) {
        return a(context, "system_moc_mna_loadsdk");
    }

    public static boolean a(Context context) {
        try {
            if (d(context)) {
                String str = a + File.separator + m.d(context).replace('.', '_') + File.separator;
                h.b("load sdk " + str);
                File file = new File(str, "patch_mna_sdk.dex");
                File file2 = new File(context.getFilesDir().getParent() + File.separator + "mna_load", "patch_mna_sdk.dex");
                if (a(file, file2)) {
                    b.a(context, file2);
                    h.a("can load sdk1");
                    return true;
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    @SuppressLint({"UnsafeDynamicallyLoadedCode"})
    public static boolean b(Context context) {
        try {
            if (c(context)) {
                String str = a + File.separator + m.d(context).replace('.', '_') + File.separator + m.d().getAbiName();
                h.b("load lib " + str);
                File file = new File(str, "libgsdk.so");
                File file2 = new File(context.getFilesDir().getParent() + File.separator + "mna_load", "libgsdk.so");
                if (a(file, file2)) {
                    System.load(file2.getAbsolutePath());
                    h.a("can load library1");
                } else {
                    h.a("fail load library1");
                    System.loadLibrary("gsdk");
                }
            } else {
                System.loadLibrary("gsdk");
                h.a("load library finish");
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
