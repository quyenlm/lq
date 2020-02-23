package com.google.android.gms.internal;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzzn
public final class zzsh extends zzsb {
    private static final Set<String> zzJX = Collections.synchronizedSet(new HashSet());
    private static final DecimalFormat zzJY = new DecimalFormat("#,###");
    private File zzJZ;
    private boolean zzKa;

    public zzsh(zzaka zzaka) {
        super(zzaka);
        File cacheDir = this.mContext.getCacheDir();
        if (cacheDir == null) {
            zzafr.zzaT("Context.getCacheDir() returned null");
            return;
        }
        this.zzJZ = new File(cacheDir, "admobVideoStreams");
        if (!this.zzJZ.isDirectory() && !this.zzJZ.mkdirs()) {
            String valueOf = String.valueOf(this.zzJZ.getAbsolutePath());
            zzafr.zzaT(valueOf.length() != 0 ? "Could not create preload cache directory at ".concat(valueOf) : new String("Could not create preload cache directory at "));
            this.zzJZ = null;
        } else if (!this.zzJZ.setReadable(true, false) || !this.zzJZ.setExecutable(true, false)) {
            String valueOf2 = String.valueOf(this.zzJZ.getAbsolutePath());
            zzafr.zzaT(valueOf2.length() != 0 ? "Could not set cache file permissions at ".concat(valueOf2) : new String("Could not set cache file permissions at "));
            this.zzJZ = null;
        }
    }

    private final File zzb(File file) {
        return new File(this.zzJZ, String.valueOf(file.getName()).concat(".done"));
    }

    public final void abort() {
        this.zzKa = true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:101:?, code lost:
        r7 = r3.getContentLength();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x0255, code lost:
        if (r7 >= 0) goto L_0x028b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0257, code lost:
        r2 = java.lang.String.valueOf(r27);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0261, code lost:
        if (r2.length() == 0) goto L_0x0280;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0263, code lost:
        r2 = "Stream cache aborted, missing content-length header at ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x0267, code lost:
        com.google.android.gms.internal.zzafr.zzaT(r2);
        zza(r27, r13.getAbsolutePath(), "contentLengthMissing", (java.lang.String) null);
        zzJX.remove(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0280, code lost:
        r2 = new java.lang.String("Stream cache aborted, missing content-length header at ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0286, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0287, code lost:
        r3 = null;
        r4 = "error";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x028b, code lost:
        r4 = zzJY.format((long) r7);
        r15 = ((java.lang.Integer) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzCn)).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x02a6, code lost:
        if (r7 <= r15) goto L_0x030d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x02a8, code lost:
        com.google.android.gms.internal.zzafr.zzaT(new java.lang.StringBuilder((java.lang.String.valueOf(r4).length() + 33) + java.lang.String.valueOf(r27).length()).append("Content length ").append(r4).append(" exceeds limit at ").append(r27).toString());
        r2 = java.lang.String.valueOf(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x02e7, code lost:
        if (r2.length() == 0) goto L_0x0302;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x02e9, code lost:
        r2 = "File too big for full file cache. Size: ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x02ed, code lost:
        zza(r27, r13.getAbsolutePath(), "sizeExceeded", r2);
        zzJX.remove(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0302, code lost:
        r2 = new java.lang.String("File too big for full file cache. Size: ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0308, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0309, code lost:
        r3 = null;
        r4 = "error";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x030d, code lost:
        com.google.android.gms.internal.zzafr.zzaC(new java.lang.StringBuilder((java.lang.String.valueOf(r4).length() + 20) + java.lang.String.valueOf(r27).length()).append("Caching ").append(r4).append(" bytes from ").append(r27).toString());
        r16 = java.nio.channels.Channels.newChannel(r3.getInputStream());
        r12 = new java.io.FileOutputStream(r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:?, code lost:
        r17 = r12.getChannel();
        r18 = java.nio.ByteBuffer.allocate(1048576);
        r19 = com.google.android.gms.ads.internal.zzbs.zzbF();
        r6 = 0;
        r20 = r19.currentTimeMillis();
        r0 = new com.google.android.gms.internal.zzair(((java.lang.Long) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzCr)).longValue());
        r24 = ((java.lang.Long) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzCq)).longValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x0389, code lost:
        r2 = r16.read(r18);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0391, code lost:
        if (r2 < 0) goto L_0x044e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0393, code lost:
        r6 = r6 + r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0394, code lost:
        if (r6 <= r15) goto L_0x03c3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:?, code lost:
        r3 = java.lang.String.valueOf(java.lang.Integer.toString(r6));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x03a6, code lost:
        if (r3.length() == 0) goto L_0x03b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x03a8, code lost:
        r3 = "File too big for full file cache. Size: ".concat(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x03b3, code lost:
        throw new java.io.IOException("stream cache file size limit exceeded");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x03b4, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x03b5, code lost:
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:?, code lost:
        r3 = new java.lang.String("File too big for full file cache. Size: ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x03be, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x03bf, code lost:
        r3 = null;
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:?, code lost:
        r18.flip();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x03ca, code lost:
        if (r17.write(r18) > 0) goto L_0x03c6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x03cc, code lost:
        r18.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x03db, code lost:
        if ((r19.currentTimeMillis() - r20) <= (1000 * r24)) goto L_0x0416;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:?, code lost:
        r2 = java.lang.String.valueOf(java.lang.Long.toString(r24));
        r3 = new java.lang.StringBuilder(java.lang.String.valueOf(r2).length() + 29).append("Timeout exceeded. Limit: ").append(r2).append(" sec").toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x0411, code lost:
        throw new java.io.IOException("stream cache time limit exceeded");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x0412, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0413, code lost:
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x041a, code lost:
        if (r26.zzKa == false) goto L_0x042b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0425, code lost:
        throw new java.io.IOException("abort requested");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x0426, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x0427, code lost:
        r3 = null;
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x042f, code lost:
        if (r0.tryAcquire() == false) goto L_0x0389;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x0431, code lost:
        com.google.android.gms.internal.zzaiy.zzaaH.post(new com.google.android.gms.internal.zzsc(r26, r27, r13.getAbsolutePath(), r6, r7, false));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0448, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0449, code lost:
        r3 = null;
        r4 = "error";
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x044e, code lost:
        r12.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0456, code lost:
        if (com.google.android.gms.internal.zzafr.zzz(3) == false) goto L_0x0494;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0458, code lost:
        r2 = zzJY.format((long) r6);
        com.google.android.gms.internal.zzafr.zzaC(new java.lang.StringBuilder((java.lang.String.valueOf(r2).length() + 22) + java.lang.String.valueOf(r27).length()).append("Preloaded ").append(r2).append(" bytes from ").append(r27).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0494, code lost:
        r13.setReadable(true, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x049d, code lost:
        if (r14.isFile() == false) goto L_0x04b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x049f, code lost:
        r14.setLastModified(java.lang.System.currentTimeMillis());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:?, code lost:
        r14.createNewFile();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x04bf, code lost:
        com.google.android.gms.internal.zzafr.zzc(new java.lang.StringBuilder(java.lang.String.valueOf(r27).length() + 25).append("Preload failed for URL \"").append(r27).append("\"").toString(), r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x04e9, code lost:
        r2 = new java.lang.String("Could not delete partial cache file at ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x04f6, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x04f7, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x04fd, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x04fe, code lost:
        r3 = null;
        r4 = "error";
        r5 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0160, code lost:
        r5 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:?, code lost:
        com.google.android.gms.ads.internal.zzbs.zzbM();
        r3 = com.google.android.gms.internal.zzajo.zzb(r27, ((java.lang.Integer) com.google.android.gms.ads.internal.zzbs.zzbL().zzd(com.google.android.gms.internal.zzmo.zzCs)).intValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x017f, code lost:
        if ((r3 instanceof java.net.HttpURLConnection) == false) goto L_0x0251;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0181, code lost:
        r2 = r3.getResponseCode();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x018b, code lost:
        if (r2 < 400) goto L_0x0251;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x018d, code lost:
        r4 = "badUrl";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:?, code lost:
        r3 = java.lang.String.valueOf(java.lang.Integer.toString(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x019d, code lost:
        if (r3.length() == 0) goto L_0x0247;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x019f, code lost:
        r3 = "HTTP request failed. Code: ".concat(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01d1, code lost:
        throw new java.io.IOException(new java.lang.StringBuilder(java.lang.String.valueOf(r27).length() + 32).append("HTTP status code ").append(r2).append(" at ").append(r27).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01d2, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01d5, code lost:
        if ((r2 instanceof java.lang.RuntimeException) != false) goto L_0x01d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01d7, code lost:
        com.google.android.gms.ads.internal.zzbs.zzbD().zza(r2, "VideoStreamFullFileCache.preload");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01e7, code lost:
        if (r26.zzKa != false) goto L_0x01e9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01e9, code lost:
        com.google.android.gms.internal.zzafr.zzaS(new java.lang.StringBuilder(java.lang.String.valueOf(r27).length() + 26).append("Preload aborted for URL \"").append(r27).append("\"").toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x021d, code lost:
        r2 = java.lang.String.valueOf(r13.getAbsolutePath());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x022b, code lost:
        if (r2.length() != 0) goto L_0x022d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x022d, code lost:
        r2 = "Could not delete partial cache file at ".concat(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0231, code lost:
        com.google.android.gms.internal.zzafr.zzaT(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0234, code lost:
        zza(r27, r13.getAbsolutePath(), r4, r3);
        zzJX.remove(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:?, code lost:
        r3 = new java.lang.String("HTTP request failed. Code: ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x024e, code lost:
        r2 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x024f, code lost:
        r3 = null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x04bf  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x04e9  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x04fd A[ExcHandler: RuntimeException (e java.lang.RuntimeException), Splitter:B:120:0x034f] */
    /* JADX WARNING: Removed duplicated region for block: B:81:0x01d7  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x022d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzU(java.lang.String r27) {
        /*
            r26 = this;
            r0 = r26
            java.io.File r2 = r0.zzJZ
            if (r2 != 0) goto L_0x0013
            r2 = 0
            java.lang.String r3 = "noCacheDir"
            r4 = 0
            r0 = r26
            r1 = r27
            r0.zza(r1, r2, r3, r4)
            r2 = 0
        L_0x0012:
            return r2
        L_0x0013:
            r0 = r26
            java.io.File r2 = r0.zzJZ
            if (r2 != 0) goto L_0x0048
            r2 = 0
            r3 = r2
        L_0x001b:
            com.google.android.gms.internal.zzme<java.lang.Integer> r2 = com.google.android.gms.internal.zzmo.zzCm
            com.google.android.gms.internal.zzmm r4 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r2 = r4.zzd(r2)
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r3 <= r2) goto L_0x00b1
            r0 = r26
            java.io.File r2 = r0.zzJZ
            if (r2 != 0) goto L_0x006a
            r2 = 0
        L_0x0034:
            if (r2 != 0) goto L_0x0013
            java.lang.String r2 = "Unable to expire stream cache"
            com.google.android.gms.internal.zzafr.zzaT(r2)
            r2 = 0
            java.lang.String r3 = "expireFailed"
            r4 = 0
            r0 = r26
            r1 = r27
            r0.zza(r1, r2, r3, r4)
            r2 = 0
            goto L_0x0012
        L_0x0048:
            r2 = 0
            r0 = r26
            java.io.File r3 = r0.zzJZ
            java.io.File[] r4 = r3.listFiles()
            int r5 = r4.length
            r3 = 0
        L_0x0053:
            if (r3 >= r5) goto L_0x0068
            r6 = r4[r3]
            java.lang.String r6 = r6.getName()
            java.lang.String r7 = ".done"
            boolean r6 = r6.endsWith(r7)
            if (r6 != 0) goto L_0x0065
            int r2 = r2 + 1
        L_0x0065:
            int r3 = r3 + 1
            goto L_0x0053
        L_0x0068:
            r3 = r2
            goto L_0x001b
        L_0x006a:
            r7 = 0
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r0 = r26
            java.io.File r2 = r0.zzJZ
            java.io.File[] r9 = r2.listFiles()
            int r10 = r9.length
            r2 = 0
            r8 = r2
        L_0x007b:
            if (r8 >= r10) goto L_0x0098
            r6 = r9[r8]
            java.lang.String r2 = r6.getName()
            java.lang.String r3 = ".done"
            boolean r2 = r2.endsWith(r3)
            if (r2 != 0) goto L_0x0503
            long r2 = r6.lastModified()
            int r11 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r11 >= 0) goto L_0x0503
        L_0x0093:
            int r8 = r8 + 1
            r4 = r2
            r7 = r6
            goto L_0x007b
        L_0x0098:
            r2 = 0
            if (r7 == 0) goto L_0x0034
            boolean r2 = r7.delete()
            r0 = r26
            java.io.File r3 = r0.zzb(r7)
            boolean r4 = r3.isFile()
            if (r4 == 0) goto L_0x0034
            boolean r3 = r3.delete()
            r2 = r2 & r3
            goto L_0x0034
        L_0x00b1:
            com.google.android.gms.internal.zzji.zzds()
            java.lang.String r2 = com.google.android.gms.internal.zzaiy.zzaR(r27)
            java.io.File r13 = new java.io.File
            r0 = r26
            java.io.File r3 = r0.zzJZ
            r13.<init>(r3, r2)
            r0 = r26
            java.io.File r14 = r0.zzb(r13)
            boolean r2 = r13.isFile()
            if (r2 == 0) goto L_0x00ff
            boolean r2 = r14.isFile()
            if (r2 == 0) goto L_0x00ff
            long r2 = r13.length()
            int r3 = (int) r2
            java.lang.String r4 = "Stream cache hit at "
            java.lang.String r2 = java.lang.String.valueOf(r27)
            int r5 = r2.length()
            if (r5 == 0) goto L_0x00f9
            java.lang.String r2 = r4.concat(r2)
        L_0x00e8:
            com.google.android.gms.internal.zzafr.zzaC(r2)
            java.lang.String r2 = r13.getAbsolutePath()
            r0 = r26
            r1 = r27
            r0.zza((java.lang.String) r1, (java.lang.String) r2, (int) r3)
            r2 = 1
            goto L_0x0012
        L_0x00f9:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r4)
            goto L_0x00e8
        L_0x00ff:
            r0 = r26
            java.io.File r2 = r0.zzJZ
            java.lang.String r2 = r2.getAbsolutePath()
            java.lang.String r3 = java.lang.String.valueOf(r2)
            java.lang.String r2 = java.lang.String.valueOf(r27)
            int r4 = r2.length()
            if (r4 == 0) goto L_0x014d
            java.lang.String r2 = r3.concat(r2)
            r9 = r2
        L_0x011a:
            java.util.Set<java.lang.String> r3 = zzJX
            monitor-enter(r3)
            java.util.Set<java.lang.String> r2 = zzJX     // Catch:{ all -> 0x014a }
            boolean r2 = r2.contains(r9)     // Catch:{ all -> 0x014a }
            if (r2 == 0) goto L_0x015a
            java.lang.String r4 = "Stream cache already in progress at "
            java.lang.String r2 = java.lang.String.valueOf(r27)     // Catch:{ all -> 0x014a }
            int r5 = r2.length()     // Catch:{ all -> 0x014a }
            if (r5 == 0) goto L_0x0154
            java.lang.String r2 = r4.concat(r2)     // Catch:{ all -> 0x014a }
        L_0x0135:
            com.google.android.gms.internal.zzafr.zzaT(r2)     // Catch:{ all -> 0x014a }
            java.lang.String r2 = r13.getAbsolutePath()     // Catch:{ all -> 0x014a }
            java.lang.String r4 = "inProgress"
            r5 = 0
            r0 = r26
            r1 = r27
            r0.zza(r1, r2, r4, r5)     // Catch:{ all -> 0x014a }
            r2 = 0
            monitor-exit(r3)     // Catch:{ all -> 0x014a }
            goto L_0x0012
        L_0x014a:
            r2 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x014a }
            throw r2
        L_0x014d:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r3)
            r9 = r2
            goto L_0x011a
        L_0x0154:
            java.lang.String r2 = new java.lang.String     // Catch:{ all -> 0x014a }
            r2.<init>(r4)     // Catch:{ all -> 0x014a }
            goto L_0x0135
        L_0x015a:
            java.util.Set<java.lang.String> r2 = zzJX     // Catch:{ all -> 0x014a }
            r2.add(r9)     // Catch:{ all -> 0x014a }
            monitor-exit(r3)     // Catch:{ all -> 0x014a }
            r5 = 0
            java.lang.String r11 = "error"
            r10 = 0
            com.google.android.gms.ads.internal.zzbs.zzbM()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzme<java.lang.Integer> r2 = com.google.android.gms.internal.zzmo.zzCs     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.Object r2 = r3.zzd(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2.intValue()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r0 = r27
            java.net.HttpURLConnection r3 = com.google.android.gms.internal.zzajo.zzb(r0, r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            boolean r2 = r3 instanceof java.net.HttpURLConnection     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            if (r2 == 0) goto L_0x0251
            r0 = r3
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2 = r0
            int r2 = r2.getResponseCode()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r4 = 400(0x190, float:5.6E-43)
            if (r2 < r4) goto L_0x0251
            java.lang.String r4 = "badUrl"
            java.lang.String r6 = "HTTP request failed. Code: "
            java.lang.String r3 = java.lang.Integer.toString(r2)     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
            int r7 = r3.length()     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
            if (r7 == 0) goto L_0x0247
            java.lang.String r3 = r6.concat(r3)     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
        L_0x01a3:
            java.io.IOException r6 = new java.io.IOException     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            java.lang.String r7 = java.lang.String.valueOf(r27)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            int r7 = r7.length()     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            int r7 = r7 + 32
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            r8.<init>(r7)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            java.lang.String r7 = "HTTP status code "
            java.lang.StringBuilder r7 = r8.append(r7)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            java.lang.StringBuilder r2 = r7.append(r2)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            java.lang.String r7 = " at "
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            r0 = r27
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            r6.<init>(r2)     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
            throw r6     // Catch:{ IOException -> 0x01d2, RuntimeException -> 0x04fa }
        L_0x01d2:
            r2 = move-exception
        L_0x01d3:
            boolean r6 = r2 instanceof java.lang.RuntimeException
            if (r6 == 0) goto L_0x01e0
            com.google.android.gms.internal.zzafk r6 = com.google.android.gms.ads.internal.zzbs.zzbD()
            java.lang.String r7 = "VideoStreamFullFileCache.preload"
            r6.zza((java.lang.Throwable) r2, (java.lang.String) r7)
        L_0x01e0:
            r5.close()     // Catch:{ IOException -> 0x04f0, NullPointerException -> 0x04f3 }
        L_0x01e3:
            r0 = r26
            boolean r5 = r0.zzKa
            if (r5 == 0) goto L_0x04bf
            java.lang.String r2 = java.lang.String.valueOf(r27)
            int r2 = r2.length()
            int r2 = r2 + 26
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r2)
            java.lang.String r2 = "Preload aborted for URL \""
            java.lang.StringBuilder r2 = r5.append(r2)
            r0 = r27
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r5 = "\""
            java.lang.StringBuilder r2 = r2.append(r5)
            java.lang.String r2 = r2.toString()
            com.google.android.gms.internal.zzafr.zzaS(r2)
        L_0x0211:
            boolean r2 = r13.exists()
            if (r2 == 0) goto L_0x0234
            boolean r2 = r13.delete()
            if (r2 != 0) goto L_0x0234
            java.lang.String r5 = "Could not delete partial cache file at "
            java.lang.String r2 = r13.getAbsolutePath()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            int r6 = r2.length()
            if (r6 == 0) goto L_0x04e9
            java.lang.String r2 = r5.concat(r2)
        L_0x0231:
            com.google.android.gms.internal.zzafr.zzaT(r2)
        L_0x0234:
            java.lang.String r2 = r13.getAbsolutePath()
            r0 = r26
            r1 = r27
            r0.zza(r1, r2, r4, r3)
            java.util.Set<java.lang.String> r2 = zzJX
            r2.remove(r9)
            r2 = 0
            goto L_0x0012
        L_0x0247:
            java.lang.String r3 = new java.lang.String     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x024e, RuntimeException -> 0x04f6 }
            goto L_0x01a3
        L_0x024e:
            r2 = move-exception
            r3 = r10
            goto L_0x01d3
        L_0x0251:
            int r7 = r3.getContentLength()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            if (r7 >= 0) goto L_0x028b
            java.lang.String r3 = "Stream cache aborted, missing content-length header at "
            java.lang.String r2 = java.lang.String.valueOf(r27)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r4 = r2.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            if (r4 == 0) goto L_0x0280
            java.lang.String r2 = r3.concat(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
        L_0x0267:
            com.google.android.gms.internal.zzafr.zzaT(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r2 = r13.getAbsolutePath()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r3 = "contentLengthMissing"
            r4 = 0
            r0 = r26
            r1 = r27
            r0.zza(r1, r2, r3, r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.util.Set<java.lang.String> r2 = zzJX     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2.remove(r9)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2 = 0
            goto L_0x0012
        L_0x0280:
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            goto L_0x0267
        L_0x0286:
            r2 = move-exception
            r3 = r10
            r4 = r11
            goto L_0x01d3
        L_0x028b:
            java.text.DecimalFormat r2 = zzJY     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            long r0 = (long) r7     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r16 = r0
            r0 = r16
            java.lang.String r4 = r2.format(r0)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzme<java.lang.Integer> r2 = com.google.android.gms.internal.zzmo.zzCn     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzmm r6 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.Object r2 = r6.zzd(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r15 = r2.intValue()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            if (r7 <= r15) goto L_0x030d
            java.lang.String r2 = java.lang.String.valueOf(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2 + 33
            java.lang.String r3 = java.lang.String.valueOf(r27)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r3 = r3.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2 + r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r2 = "Content length "
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r3 = " exceeds limit at "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r0 = r27
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzafr.zzaT(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r3 = "File too big for full file cache. Size: "
            java.lang.String r2 = java.lang.String.valueOf(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r4 = r2.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            if (r4 == 0) goto L_0x0302
            java.lang.String r2 = r3.concat(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
        L_0x02ed:
            java.lang.String r3 = r13.getAbsolutePath()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r4 = "sizeExceeded"
            r0 = r26
            r1 = r27
            r0.zza(r1, r3, r4, r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.util.Set<java.lang.String> r2 = zzJX     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2.remove(r9)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2 = 0
            goto L_0x0012
        L_0x0302:
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            goto L_0x02ed
        L_0x0308:
            r2 = move-exception
            r3 = r10
            r4 = r11
            goto L_0x01d3
        L_0x030d:
            java.lang.String r2 = java.lang.String.valueOf(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2 + 20
            java.lang.String r6 = java.lang.String.valueOf(r27)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r6 = r6.length()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            int r2 = r2 + r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r6.<init>(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r2 = "Caching "
            java.lang.StringBuilder r2 = r6.append(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r4 = " bytes from "
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r0 = r27
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            com.google.android.gms.internal.zzafr.zzaC(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.io.InputStream r2 = r3.getInputStream()     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.nio.channels.ReadableByteChannel r16 = java.nio.channels.Channels.newChannel(r2)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.io.FileOutputStream r12 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            r12.<init>(r13)     // Catch:{ IOException -> 0x0286, RuntimeException -> 0x0308 }
            java.nio.channels.FileChannel r17 = r12.getChannel()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r2 = 1048576(0x100000, float:1.469368E-39)
            java.nio.ByteBuffer r18 = java.nio.ByteBuffer.allocate(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.common.util.zze r19 = com.google.android.gms.ads.internal.zzbs.zzbF()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r6 = 0
            long r20 = r19.currentTimeMillis()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzme<java.lang.Long> r2 = com.google.android.gms.internal.zzmo.zzCr     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.Object r2 = r3.zzd(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            long r2 = r2.longValue()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzair r22 = new com.google.android.gms.internal.zzair     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r0 = r22
            r0.<init>(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzme<java.lang.Long> r2 = com.google.android.gms.internal.zzmo.zzCq     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzmm r3 = com.google.android.gms.ads.internal.zzbs.zzbL()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.Object r2 = r3.zzd(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            long r24 = r2.longValue()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
        L_0x0389:
            r0 = r16
            r1 = r18
            int r2 = r0.read(r1)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 < 0) goto L_0x044e
            int r6 = r6 + r2
            if (r6 <= r15) goto L_0x03c3
            java.lang.String r4 = "sizeExceeded"
            java.lang.String r2 = "File too big for full file cache. Size: "
            java.lang.String r3 = java.lang.Integer.toString(r6)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            int r5 = r3.length()     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            if (r5 == 0) goto L_0x03b8
            java.lang.String r3 = r2.concat(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
        L_0x03ac:
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
            java.lang.String r5 = "stream cache file size limit exceeded"
            r2.<init>(r5)     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
            throw r2     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
        L_0x03b4:
            r2 = move-exception
            r5 = r12
            goto L_0x01d3
        L_0x03b8:
            java.lang.String r3 = new java.lang.String     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            goto L_0x03ac
        L_0x03be:
            r2 = move-exception
            r3 = r10
            r5 = r12
            goto L_0x01d3
        L_0x03c3:
            r18.flip()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
        L_0x03c6:
            int r2 = r17.write(r18)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 > 0) goto L_0x03c6
            r18.clear()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            long r2 = r19.currentTimeMillis()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            long r2 = r2 - r20
            r4 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 * r24
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 <= 0) goto L_0x0416
            java.lang.String r4 = "downloadTimeout"
            java.lang.String r2 = java.lang.Long.toString(r24)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = java.lang.String.valueOf(r2)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            int r3 = r3.length()     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            int r3 = r3 + 29
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = "Timeout exceeded. Limit: "
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = " sec"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = r2.toString()     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
            java.lang.String r5 = "stream cache time limit exceeded"
            r2.<init>(r5)     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
            throw r2     // Catch:{ IOException -> 0x03b4, RuntimeException -> 0x0412 }
        L_0x0412:
            r2 = move-exception
            r5 = r12
            goto L_0x01d3
        L_0x0416:
            r0 = r26
            boolean r2 = r0.zzKa     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 == 0) goto L_0x042b
            java.lang.String r4 = "externalAbort"
            java.io.IOException r2 = new java.io.IOException     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            java.lang.String r3 = "abort requested"
            r2.<init>(r3)     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
            throw r2     // Catch:{ IOException -> 0x03be, RuntimeException -> 0x0426 }
        L_0x0426:
            r2 = move-exception
            r3 = r10
            r5 = r12
            goto L_0x01d3
        L_0x042b:
            boolean r2 = r22.tryAcquire()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 == 0) goto L_0x0389
            java.lang.String r5 = r13.getAbsolutePath()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            android.os.Handler r23 = com.google.android.gms.internal.zzaiy.zzaaH     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzsc r2 = new com.google.android.gms.internal.zzsc     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r8 = 0
            r3 = r26
            r4 = r27
            r2.<init>(r3, r4, r5, r6, r7, r8)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r0 = r23
            r0.post(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            goto L_0x0389
        L_0x0448:
            r2 = move-exception
            r3 = r10
            r4 = r11
            r5 = r12
            goto L_0x01d3
        L_0x044e:
            r12.close()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r2 = 3
            boolean r2 = com.google.android.gms.internal.zzafr.zzz(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 == 0) goto L_0x0494
            java.text.DecimalFormat r2 = zzJY     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            long r4 = (long) r6     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.String r2 = r2.format(r4)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.String r3 = java.lang.String.valueOf(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            int r3 = r3.length()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            int r3 = r3 + 22
            java.lang.String r4 = java.lang.String.valueOf(r27)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            int r4 = r4.length()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            int r3 = r3 + r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r4.<init>(r3)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.String r3 = "Preloaded "
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.StringBuilder r2 = r3.append(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.String r3 = " bytes from "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r0 = r27
            java.lang.StringBuilder r2 = r2.append(r0)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            com.google.android.gms.internal.zzafr.zzaC(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
        L_0x0494:
            r2 = 1
            r3 = 0
            r13.setReadable(r2, r3)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            boolean r2 = r14.isFile()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            if (r2 == 0) goto L_0x04b9
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r14.setLastModified(r2)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
        L_0x04a6:
            java.lang.String r2 = r13.getAbsolutePath()     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r0 = r26
            r1 = r27
            r0.zza((java.lang.String) r1, (java.lang.String) r2, (int) r6)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            java.util.Set<java.lang.String> r2 = zzJX     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r2.remove(r9)     // Catch:{ IOException -> 0x0448, RuntimeException -> 0x04fd }
            r2 = 1
            goto L_0x0012
        L_0x04b9:
            r14.createNewFile()     // Catch:{ IOException -> 0x04bd, RuntimeException -> 0x04fd }
            goto L_0x04a6
        L_0x04bd:
            r2 = move-exception
            goto L_0x04a6
        L_0x04bf:
            java.lang.String r5 = java.lang.String.valueOf(r27)
            int r5 = r5.length()
            int r5 = r5 + 25
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Preload failed for URL \""
            java.lang.StringBuilder r5 = r6.append(r5)
            r0 = r27
            java.lang.StringBuilder r5 = r5.append(r0)
            java.lang.String r6 = "\""
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.google.android.gms.internal.zzafr.zzc(r5, r2)
            goto L_0x0211
        L_0x04e9:
            java.lang.String r2 = new java.lang.String
            r2.<init>(r5)
            goto L_0x0231
        L_0x04f0:
            r5 = move-exception
            goto L_0x01e3
        L_0x04f3:
            r5 = move-exception
            goto L_0x01e3
        L_0x04f6:
            r2 = move-exception
            r3 = r10
            goto L_0x01d3
        L_0x04fa:
            r2 = move-exception
            goto L_0x01d3
        L_0x04fd:
            r2 = move-exception
            r3 = r10
            r4 = r11
            r5 = r12
            goto L_0x01d3
        L_0x0503:
            r2 = r4
            r6 = r7
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzsh.zzU(java.lang.String):boolean");
    }
}
