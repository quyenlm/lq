package com.google.android.gms.internal;

import android.os.SystemClock;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class zzag implements zzb {
    private final Map<String, zzai> zzav;
    private long zzaw;
    private final File zzax;
    private final int zzay;

    public zzag(File file) {
        this(file, 5242880);
    }

    private zzag(File file, int i) {
        this.zzav = new LinkedHashMap(16, 0.75f, true);
        this.zzaw = 0;
        this.zzax = file;
        this.zzay = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        zzai zzai = this.zzav.get(str);
        if (zzai != null) {
            this.zzaw -= zzai.size;
            this.zzav.remove(str);
        }
        if (!delete) {
            zzab.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzai zzai) {
        if (!this.zzav.containsKey(str)) {
            this.zzaw += zzai.size;
        } else {
            this.zzaw = (zzai.size - this.zzav.get(str).size) + this.zzaw;
        }
        this.zzav.put(str, zzai);
    }

    private static byte[] zza(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == i) {
            return bArr;
        }
        throw new IOException(new StringBuilder(50).append("Expected ").append(i).append(" bytes, read ").append(i2).append(" bytes").toString());
    }

    static int zzb(InputStream inputStream) throws IOException {
        return zza(inputStream) | 0 | (zza(inputStream) << 8) | (zza(inputStream) << 16) | (zza(inputStream) << 24);
    }

    static long zzc(InputStream inputStream) throws IOException {
        return 0 | (((long) zza(inputStream)) & 255) | ((((long) zza(inputStream)) & 255) << 8) | ((((long) zza(inputStream)) & 255) << 16) | ((((long) zza(inputStream)) & 255) << 24) | ((((long) zza(inputStream)) & 255) << 32) | ((((long) zza(inputStream)) & 255) << 40) | ((((long) zza(inputStream)) & 255) << 48) | ((((long) zza(inputStream)) & 255) << 56);
    }

    static String zzd(InputStream inputStream) throws IOException {
        return new String(zza(inputStream, (int) zzc(inputStream)), "UTF-8");
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzax, zzd(str));
    }

    static Map<String, String> zze(InputStream inputStream) throws IOException {
        int zzb = zzb(inputStream);
        Map<String, String> emptyMap = zzb == 0 ? Collections.emptyMap() : new HashMap<>(zzb);
        for (int i = 0; i < zzb; i++) {
            emptyMap.put(zzd(inputStream).intern(), zzd(inputStream).intern());
        }
        return emptyMap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0058 A[SYNTHETIC, Splitter:B:27:0x0058] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x005d A[SYNTHETIC, Splitter:B:30:0x005d] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0068 A[SYNTHETIC, Splitter:B:36:0x0068] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0050 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void initialize() {
        /*
            r8 = this;
            r0 = 0
            monitor-enter(r8)
            java.io.File r1 = r8.zzax     // Catch:{ all -> 0x006c }
            boolean r1 = r1.exists()     // Catch:{ all -> 0x006c }
            if (r1 != 0) goto L_0x0025
            java.io.File r0 = r8.zzax     // Catch:{ all -> 0x006c }
            boolean r0 = r0.mkdirs()     // Catch:{ all -> 0x006c }
            if (r0 != 0) goto L_0x0023
            java.lang.String r0 = "Unable to create cache dir %s"
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x006c }
            r2 = 0
            java.io.File r3 = r8.zzax     // Catch:{ all -> 0x006c }
            java.lang.String r3 = r3.getAbsolutePath()     // Catch:{ all -> 0x006c }
            r1[r2] = r3     // Catch:{ all -> 0x006c }
            com.google.android.gms.internal.zzab.zzc(r0, r1)     // Catch:{ all -> 0x006c }
        L_0x0023:
            monitor-exit(r8)
            return
        L_0x0025:
            java.io.File r1 = r8.zzax     // Catch:{ all -> 0x006c }
            java.io.File[] r3 = r1.listFiles()     // Catch:{ all -> 0x006c }
            if (r3 == 0) goto L_0x0023
            int r4 = r3.length     // Catch:{ all -> 0x006c }
            r2 = r0
        L_0x002f:
            if (r2 >= r4) goto L_0x0023
            r5 = r3[r2]     // Catch:{ all -> 0x006c }
            r1 = 0
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x0054, all -> 0x0063 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0054, all -> 0x0063 }
            r6.<init>(r5)     // Catch:{ IOException -> 0x0054, all -> 0x0063 }
            r0.<init>(r6)     // Catch:{ IOException -> 0x0054, all -> 0x0063 }
            com.google.android.gms.internal.zzai r1 = com.google.android.gms.internal.zzai.zzf(r0)     // Catch:{ IOException -> 0x0077 }
            long r6 = r5.length()     // Catch:{ IOException -> 0x0077 }
            r1.size = r6     // Catch:{ IOException -> 0x0077 }
            java.lang.String r6 = r1.key     // Catch:{ IOException -> 0x0077 }
            r8.zza((java.lang.String) r6, (com.google.android.gms.internal.zzai) r1)     // Catch:{ IOException -> 0x0077 }
            r0.close()     // Catch:{ IOException -> 0x006f }
        L_0x0050:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x002f
        L_0x0054:
            r0 = move-exception
            r0 = r1
        L_0x0056:
            if (r5 == 0) goto L_0x005b
            r5.delete()     // Catch:{ all -> 0x0073 }
        L_0x005b:
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ IOException -> 0x0061 }
            goto L_0x0050
        L_0x0061:
            r0 = move-exception
            goto L_0x0050
        L_0x0063:
            r0 = move-exception
            r2 = r0
            r3 = r1
        L_0x0066:
            if (r3 == 0) goto L_0x006b
            r3.close()     // Catch:{ IOException -> 0x0071 }
        L_0x006b:
            throw r2     // Catch:{ all -> 0x006c }
        L_0x006c:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x006f:
            r0 = move-exception
            goto L_0x0050
        L_0x0071:
            r0 = move-exception
            goto L_0x006b
        L_0x0073:
            r1 = move-exception
            r2 = r1
            r3 = r0
            goto L_0x0066
        L_0x0077:
            r1 = move-exception
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.initialize():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0079 A[SYNTHETIC, Splitter:B:25:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009e A[SYNTHETIC, Splitter:B:36:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00ac A[SYNTHETIC, Splitter:B:44:0x00ac] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized com.google.android.gms.internal.zzc zza(java.lang.String r11) {
        /*
            r10 = this;
            r1 = 0
            monitor-enter(r10)
            java.util.Map<java.lang.String, com.google.android.gms.internal.zzai> r0 = r10.zzav     // Catch:{ all -> 0x00b0 }
            java.lang.Object r0 = r0.get(r11)     // Catch:{ all -> 0x00b0 }
            com.google.android.gms.internal.zzai r0 = (com.google.android.gms.internal.zzai) r0     // Catch:{ all -> 0x00b0 }
            if (r0 != 0) goto L_0x000f
            r0 = r1
        L_0x000d:
            monitor-exit(r10)
            return r0
        L_0x000f:
            java.io.File r4 = r10.zze((java.lang.String) r11)     // Catch:{ all -> 0x00b0 }
            com.google.android.gms.internal.zzaj r3 = new com.google.android.gms.internal.zzaj     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            r5.<init>(r4)     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            r2.<init>(r5)     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            r5 = 0
            r3.<init>(r2)     // Catch:{ IOException -> 0x005c, NegativeArraySizeException -> 0x0081, all -> 0x00a8 }
            com.google.android.gms.internal.zzai.zzf(r3)     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r4.length()     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            int r2 = r3.zzaz     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r8 = (long) r2     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r6 - r8
            int r2 = (int) r6     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            byte[] r5 = zza((java.io.InputStream) r3, (int) r2)     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            com.google.android.gms.internal.zzc r2 = new com.google.android.gms.internal.zzc     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.<init>()     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.data = r5     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            java.lang.String r5 = r0.zza     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zza = r5     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r0.zzb     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zzb = r6     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r0.zzc     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zzc = r6     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r0.zzd     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zzd = r6     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            long r6 = r0.zze     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zze = r6     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            java.util.Map<java.lang.String, java.lang.String> r0 = r0.zzf     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r2.zzf = r0     // Catch:{ IOException -> 0x00be, NegativeArraySizeException -> 0x00bc }
            r3.close()     // Catch:{ IOException -> 0x0059 }
            r0 = r2
            goto L_0x000d
        L_0x0059:
            r0 = move-exception
            r0 = r1
            goto L_0x000d
        L_0x005c:
            r0 = move-exception
            r2 = r1
        L_0x005e:
            java.lang.String r3 = "%s: %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00b9 }
            r6 = 0
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x00b9 }
            r5[r6] = r4     // Catch:{ all -> 0x00b9 }
            r4 = 1
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b9 }
            r5[r4] = r0     // Catch:{ all -> 0x00b9 }
            com.google.android.gms.internal.zzab.zzb(r3, r5)     // Catch:{ all -> 0x00b9 }
            r10.remove(r11)     // Catch:{ all -> 0x00b9 }
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ IOException -> 0x007e }
        L_0x007c:
            r0 = r1
            goto L_0x000d
        L_0x007e:
            r0 = move-exception
            r0 = r1
            goto L_0x000d
        L_0x0081:
            r0 = move-exception
            r3 = r1
        L_0x0083:
            java.lang.String r2 = "%s: %s"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x00b7 }
            r6 = 0
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ all -> 0x00b7 }
            r5[r6] = r4     // Catch:{ all -> 0x00b7 }
            r4 = 1
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x00b7 }
            r5[r4] = r0     // Catch:{ all -> 0x00b7 }
            com.google.android.gms.internal.zzab.zzb(r2, r5)     // Catch:{ all -> 0x00b7 }
            r10.remove(r11)     // Catch:{ all -> 0x00b7 }
            if (r3 == 0) goto L_0x00a1
            r3.close()     // Catch:{ IOException -> 0x00a4 }
        L_0x00a1:
            r0 = r1
            goto L_0x000d
        L_0x00a4:
            r0 = move-exception
            r0 = r1
            goto L_0x000d
        L_0x00a8:
            r0 = move-exception
            r3 = r1
        L_0x00aa:
            if (r3 == 0) goto L_0x00af
            r3.close()     // Catch:{ IOException -> 0x00b3 }
        L_0x00af:
            throw r0     // Catch:{ all -> 0x00b0 }
        L_0x00b0:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x00b3:
            r0 = move-exception
            r0 = r1
            goto L_0x000d
        L_0x00b7:
            r0 = move-exception
            goto L_0x00aa
        L_0x00b9:
            r0 = move-exception
            r3 = r2
            goto L_0x00aa
        L_0x00bc:
            r0 = move-exception
            goto L_0x0083
        L_0x00be:
            r0 = move-exception
            r2 = r3
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.zza(java.lang.String):com.google.android.gms.internal.zzc");
    }

    public final synchronized void zza(String str, zzc zzc) {
        int i;
        int i2 = 0;
        synchronized (this) {
            int length = zzc.data.length;
            if (this.zzaw + ((long) length) >= ((long) this.zzay)) {
                if (zzab.DEBUG) {
                    zzab.zza("Pruning old cache entries.", new Object[0]);
                }
                long j = this.zzaw;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator<Map.Entry<String, zzai>> it = this.zzav.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        i = i2;
                        break;
                    }
                    zzai zzai = (zzai) it.next().getValue();
                    if (zze(zzai.key).delete()) {
                        this.zzaw -= zzai.size;
                    } else {
                        zzab.zzb("Could not delete cache entry for key=%s, filename=%s", zzai.key, zzd(zzai.key));
                    }
                    it.remove();
                    i = i2 + 1;
                    if (((float) (this.zzaw + ((long) length))) < ((float) this.zzay) * 0.9f) {
                        break;
                    }
                    i2 = i;
                }
                if (zzab.DEBUG) {
                    zzab.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i), Long.valueOf(this.zzaw - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            }
            File zze = zze(str);
            try {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zze));
                zzai zzai2 = new zzai(str, zzc);
                if (!zzai2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.close();
                    zzab.zzb("Failed to write header for %s", zze.getAbsolutePath());
                    throw new IOException();
                }
                bufferedOutputStream.write(zzc.data);
                bufferedOutputStream.close();
                zza(str, zzai2);
            } catch (IOException e) {
                if (!zze.delete()) {
                    zzab.zzb("Could not clean up file %s", zze.getAbsolutePath());
                }
            }
        }
    }
}
