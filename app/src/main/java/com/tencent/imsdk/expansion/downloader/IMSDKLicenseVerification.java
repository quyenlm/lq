package com.tencent.imsdk.expansion.downloader;

import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;

public class IMSDKLicenseVerification extends LicenseVerificationBase {
    private static final int RETRY_CONNECT_SERVICE_NUMBER = 3;
    private static final int TIME_OUT = 8000;
    private static String customizationURL = null;
    private final String EXPANSION_FILE_LINK_HEAD = "http://file.infiniteracer.qq.com/obb/";

    public static void setCustomFetchURL(String url) {
        customizationURL = url;
    }

    public IMSDKLicenseVerification(DownloaderService dws) {
        super(dws);
    }

    /* JADX WARNING: type inference failed for: r16v30, types: [java.net.URLConnection] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateDownloadInfo2DB(android.content.Context r19, com.tencent.imsdk.expansion.downloader.LicenseVerificationBase.ILicenseVerificationResult r20) {
        /*
            r18 = this;
            r14 = 0
            java.lang.StringBuffer r8 = new java.lang.StringBuffer
            r8.<init>()
            java.lang.String r11 = r19.getPackageName()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            android.content.pm.PackageManager r16 = r19.getPackageManager()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r17 = 0
            r0 = r16
            r1 = r17
            android.content.pm.PackageInfo r16 = r0.getPackageInfo(r11, r1)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            int r15 = r0.versionCode     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16.<init>()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = "main."
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r15)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = "."
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r11)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = ".obb"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r10 = r16.toString()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16.<init>()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = "fileName = "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r10)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r16 = r16.toString()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            com.tencent.imsdk.expansion.downloader.IMLogger.i(r16)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r9 = 0
            java.lang.String r16 = customizationURL     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            if (r16 == 0) goto L_0x0068
            java.lang.String r16 = customizationURL     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            int r16 = r16.length()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            if (r16 > 0) goto L_0x00fc
        L_0x0068:
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16.<init>()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = "http://file.infiniteracer.qq.com/obb/"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r10)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r9 = r16.toString()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
        L_0x007d:
            r4 = 0
            java.net.URL r13 = new java.net.URL     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r13.<init>(r9)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
        L_0x0083:
            int r4 = r4 + 1
            r16 = 3
            r0 = r16
            if (r4 > r0) goto L_0x0113
            java.net.URLConnection r16 = r13.openConnection()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r14 = r0
            r16 = 8000(0x1f40, float:1.121E-41)
            r0 = r16
            r14.setConnectTimeout(r0)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16 = 1
            r0 = r16
            r14.setAllowUserInteraction(r0)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16 = 8000(0x1f40, float:1.121E-41)
            r0 = r16
            r14.setReadTimeout(r0)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            int r16 = r14.getResponseCode()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r17 = 200(0xc8, float:2.8E-43)
            r0 = r16
            r1 = r17
            if (r0 == r1) goto L_0x0113
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16.<init>()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = "Retry "
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r4)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = ", Error message :"
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = r14.getResponseMessage()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r16 = r16.toString()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16 = 3
            r0 = r16
            if (r4 != r0) goto L_0x0083
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x00f6
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x00f6:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
        L_0x00fb:
            return
        L_0x00fc:
            java.lang.StringBuilder r16 = new java.lang.StringBuilder     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r16.<init>()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r17 = customizationURL     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.StringBuilder r16 = r16.append(r17)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            java.lang.StringBuilder r16 = r0.append(r10)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            java.lang.String r9 = r16.toString()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            goto L_0x007d
        L_0x0113:
            int r16 = r14.getContentLength()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r16
            long r2 = (long) r0     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            com.tencent.imsdk.expansion.downloader.impl.DownloadsDB r5 = com.tencent.imsdk.expansion.downloader.impl.DownloadsDB.getDB(r19)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            com.tencent.imsdk.expansion.downloader.impl.DownloadInfo r6 = new com.tencent.imsdk.expansion.downloader.impl.DownloadInfo     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r6.<init>(r15, r10, r11)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r12 = -1
            r6.resetDownload()     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r6.mUri = r9     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r6.mTotalBytes = r2     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r6.mStatus = r12     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r5.updateDownload(r6)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            r0 = r20
            r0.onLVSuccess(r12, r5)     // Catch:{ MalformedURLException -> 0x014c, IOException -> 0x016d, NameNotFoundException -> 0x018f, SecurityException -> 0x01b1 }
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x0146
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x0146:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
            goto L_0x00fb
        L_0x014c:
            r7 = move-exception
            java.lang.String r16 = r7.getMessage()     // Catch:{ all -> 0x01d3 }
            r0 = r16
            r8.append(r0)     // Catch:{ all -> 0x01d3 }
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x0167
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x0167:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
            goto L_0x00fb
        L_0x016d:
            r7 = move-exception
            java.lang.String r16 = r7.getMessage()     // Catch:{ all -> 0x01d3 }
            r0 = r16
            r8.append(r0)     // Catch:{ all -> 0x01d3 }
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x0188
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x0188:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
            goto L_0x00fb
        L_0x018f:
            r7 = move-exception
            java.lang.String r16 = r7.getMessage()     // Catch:{ all -> 0x01d3 }
            r0 = r16
            r8.append(r0)     // Catch:{ all -> 0x01d3 }
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x01aa
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x01aa:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
            goto L_0x00fb
        L_0x01b1:
            r7 = move-exception
            java.lang.String r16 = r7.getMessage()     // Catch:{ all -> 0x01d3 }
            r0 = r16
            r8.append(r0)     // Catch:{ all -> 0x01d3 }
            int r16 = r8.length()
            if (r16 <= 0) goto L_0x01cc
            java.lang.String r16 = r8.toString()
            r0 = r20
            r1 = r16
            r0.onLVFail(r1)
        L_0x01cc:
            if (r14 == 0) goto L_0x00fb
            r14.disconnect()
            goto L_0x00fb
        L_0x01d3:
            r16 = move-exception
            int r17 = r8.length()
            if (r17 <= 0) goto L_0x01e5
            java.lang.String r17 = r8.toString()
            r0 = r20
            r1 = r17
            r0.onLVFail(r1)
        L_0x01e5:
            if (r14 == 0) goto L_0x01ea
            r14.disconnect()
        L_0x01ea:
            throw r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.expansion.downloader.IMSDKLicenseVerification.updateDownloadInfo2DB(android.content.Context, com.tencent.imsdk.expansion.downloader.LicenseVerificationBase$ILicenseVerificationResult):void");
    }
}
