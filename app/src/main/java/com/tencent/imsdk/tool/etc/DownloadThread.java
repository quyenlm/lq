package com.tencent.imsdk.tool.etc;

import android.os.Handler;
import android.os.Message;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DownloadThread extends Thread {
    public static final int THREAD_BEGIN = 1;
    public static final int THREAD_FINISHED_FAIL = 3;
    public static final int THREAD_FINISHED_SUCC = 2;
    /* access modifiers changed from: private */
    public static Queue<DownloadItem> downloadList = new LinkedList();
    private static Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            DownloadItem tempDownloadItem;
            Message message = new Message();
            new DownloadItem();
            switch (msg.what) {
                case 1:
                    DownloadThread.sLock.lock();
                    if (!DownloadThread.needDownloadLists.isEmpty()) {
                        do {
                            tempDownloadItem = (DownloadItem) DownloadThread.needDownloadLists.poll();
                            if (!DownloadThread.downloadList.contains(tempDownloadItem)) {
                                DownloadThread tempDownloadThread = new DownloadThread(tempDownloadItem);
                                if (!tempDownloadThread.isStarted()) {
                                    tempDownloadThread.start();
                                }
                                DownloadThread.downloadList.add(tempDownloadItem);
                            }
                        } while (!DownloadThread.downloadList.contains(tempDownloadItem));
                    } else if (!DownloadThread.downloadList.isEmpty()) {
                        IMLogger.d("no new task");
                    } else if (DownloadThread.isFinished()) {
                        IMLogger.d("all task finished have been notified");
                    } else {
                        DownloadThread.setFinished(true);
                        IMLogger.d("all task finished");
                    }
                    DownloadThread.sLock.unlock();
                    return;
                case 2:
                    DownloadThread.sLock.lock();
                    DownloadItem tempDownloadItem2 = (DownloadItem) msg.obj;
                    if (DownloadThread.downloadList.contains(tempDownloadItem2)) {
                        DownloadThread.downloadList.remove(tempDownloadItem2);
                    }
                    message.what = 1;
                    sendMessage(message);
                    DownloadThread.sLock.unlock();
                    return;
                case 3:
                    DownloadThread.sLock.lock();
                    DownloadItem tempDownloadItem3 = (DownloadItem) msg.obj;
                    if (DownloadThread.downloadList.contains(tempDownloadItem3)) {
                        DownloadThread.downloadList.remove(tempDownloadItem3);
                    }
                    message.what = 1;
                    sendMessage(message);
                    DownloadThread.sLock.unlock();
                    return;
                default:
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public static Queue<DownloadItem> needDownloadLists = new LinkedList();
    private static boolean sIsFinished = false;
    /* access modifiers changed from: private */
    public static Lock sLock = new ReentrantLock();
    private DownloadItem mDownloadItem = null;
    private boolean mIsStarted = false;

    public DownloadThread(DownloadItem tempDownloadItem) {
        this.mDownloadItem = tempDownloadItem;
    }

    public static void addToDownloadQueue(URL url, String filePath, String hashValue) {
        if (url == null || T.ckIsEmpty(filePath) || T.ckIsEmpty(hashValue)) {
            IMLogger.w("url or filePath or hashValue is null");
            return;
        }
        sLock.lock();
        try {
            DownloadItem tempDownloadThread = new DownloadItem(url, filePath, hashValue);
            if (!needDownloadLists.contains(tempDownloadThread)) {
                needDownloadLists.add(tempDownloadThread);
            }
            setFinished(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sLock.unlock();
        }
        Message message = new Message();
        message.what = 1;
        myHandler.sendMessage(message);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0108 A[SYNTHETIC, Splitter:B:16:0x0108] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x010d A[Catch:{ IOException -> 0x0230 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x01d6 A[SYNTHETIC, Splitter:B:39:0x01d6] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01db A[Catch:{ IOException -> 0x0236 }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r24 = this;
            r21 = 1
            r0 = r21
            r1 = r24
            r1.mIsStarted = r0
            r4 = 0
            r6 = 0
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0240 }
            r21 = r0
            r0 = r21
            java.net.URL r0 = r0.mFileUrl     // Catch:{ Exception -> 0x0240 }
            r21 = r0
            java.net.URLConnection r11 = r21.openConnection()     // Catch:{ Exception -> 0x0240 }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ Exception -> 0x0240 }
            r21 = 5000(0x1388, float:7.006E-42)
            r0 = r21
            r11.setConnectTimeout(r0)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r21 = "GET"
            r0 = r21
            r11.setRequestMethod(r0)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r21 = "Accept"
            java.lang.String r22 = "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*"
            r0 = r21
            r1 = r22
            r11.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r21 = "Charset"
            java.lang.String r22 = "UTF-8"
            r0 = r21
            r1 = r22
            r11.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r21 = "User-Agent"
            java.lang.String r22 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"
            r0 = r21
            r1 = r22
            r11.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0240 }
            java.lang.String r21 = "Connection"
            java.lang.String r22 = "Keep-Alive"
            r0 = r21
            r1 = r22
            r11.setRequestProperty(r0, r1)     // Catch:{ Exception -> 0x0240 }
            r11.connect()     // Catch:{ Exception -> 0x0240 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0240 }
            r21 = r0
            int r22 = r11.getContentLength()     // Catch:{ Exception -> 0x0240 }
            r0 = r22
            long r0 = (long) r0     // Catch:{ Exception -> 0x0240 }
            r22 = r0
            r0 = r22
            r2 = r21
            r2.mFileLength = r0     // Catch:{ Exception -> 0x0240 }
            r21 = 4096(0x1000, float:5.74E-42)
            r0 = r21
            byte[] r9 = new byte[r0]     // Catch:{ Exception -> 0x0240 }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0240 }
            java.io.InputStream r21 = r11.getInputStream()     // Catch:{ Exception -> 0x0240 }
            r0 = r21
            r5.<init>(r0)     // Catch:{ Exception -> 0x0240 }
            java.io.File r20 = new java.io.File     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r21.<init>()     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r22 = r0
            r0 = r22
            java.lang.String r0 = r0.mLocalFilePath     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            java.lang.String r22 = "_temp"
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            java.lang.String r21 = r21.toString()     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r20.<init>(r21)     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            java.io.FileOutputStream r21 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r0 = r21
            r1 = r20
            r0.<init>(r1)     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r0 = r21
            r7.<init>(r0)     // Catch:{ Exception -> 0x0243, all -> 0x023d }
            r12 = 0
            r10 = 0
            java.lang.String r21 = "MD5"
            java.security.MessageDigest r17 = java.security.MessageDigest.getInstance(r21)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r19 = ""
        L_0x00be:
            int r10 = r5.read(r9)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = -1
            r0 = r21
            if (r10 == r0) goto L_0x0111
            r21 = 0
            r0 = r21
            r7.write(r9, r0, r10)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = 0
            r0 = r17
            r1 = r21
            r0.update(r9, r1, r10)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r7.flush()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            long r0 = (long) r10     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            long r12 = r12 + r22
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            r0 = r22
            long r0 = r0.mFileLength     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            long r22 = r12 / r22
            r0 = r22
            float r0 = (float) r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            r0 = r22
            r1 = r21
            r1.mPercent = r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            goto L_0x00be
        L_0x0100:
            r14 = move-exception
            r6 = r7
            r4 = r5
        L_0x0103:
            r14.printStackTrace()     // Catch:{ all -> 0x023b }
            if (r4 == 0) goto L_0x010b
            r4.close()     // Catch:{ IOException -> 0x0230 }
        L_0x010b:
            if (r6 == 0) goto L_0x0110
            r6.close()     // Catch:{ IOException -> 0x0230 }
        L_0x0110:
            return
        L_0x0111:
            byte[] r8 = r17.digest()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r21 = com.tencent.imsdk.tool.etc.HexUtil.bytes2HexStr(r8)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.util.Locale r22 = java.util.Locale.CHINA     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r19 = r21.toLowerCase(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            android.os.Message r18 = new android.os.Message     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r18.<init>()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r21
            r1 = r18
            r1.obj = r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r21
            java.lang.String r0 = r0.mHashValue     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r19
            r1 = r21
            boolean r21 = r0.equalsIgnoreCase(r1)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            if (r21 == 0) goto L_0x01df
            java.io.File r16 = new java.io.File     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r21
            java.lang.String r0 = r0.mLocalFilePath     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r16
            r1 = r21
            r0.<init>(r1)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r20
            r1 = r16
            boolean r15 = r0.renameTo(r1)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            if (r15 == 0) goto L_0x01a6
            r21 = 2
            r0 = r21
            r1 = r18
            r1.what = r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21.<init>()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r22 = "rename pic succ："
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            r0 = r22
            java.lang.String r0 = r0.mLocalFilePath     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r21 = r21.toString()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r21)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
        L_0x018f:
            android.os.Handler r21 = myHandler     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r21
            r1 = r18
            r0.sendMessage(r1)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            if (r5 == 0) goto L_0x019d
            r5.close()     // Catch:{ IOException -> 0x0228 }
        L_0x019d:
            if (r7 == 0) goto L_0x01a2
            r7.close()     // Catch:{ IOException -> 0x0228 }
        L_0x01a2:
            r6 = r7
            r4 = r5
            goto L_0x0110
        L_0x01a6:
            r21 = 3
            r0 = r21
            r1 = r18
            r1.what = r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21.<init>()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r22 = "rename pic failed："
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            r0 = r22
            java.lang.String r0 = r0.mLocalFilePath     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r21 = r21.toString()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r21)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            goto L_0x018f
        L_0x01d1:
            r21 = move-exception
            r6 = r7
            r4 = r5
        L_0x01d4:
            if (r4 == 0) goto L_0x01d9
            r4.close()     // Catch:{ IOException -> 0x0236 }
        L_0x01d9:
            if (r6 == 0) goto L_0x01de
            r6.close()     // Catch:{ IOException -> 0x0236 }
        L_0x01de:
            throw r21
        L_0x01df:
            r21 = 3
            r0 = r21
            r1 = r18
            r1.what = r0     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.StringBuilder r21 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21.<init>()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r22 = "picMd5:"
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r21
            r1 = r19
            java.lang.StringBuilder r21 = r0.append(r1)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r22 = ";hashValue:"
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            r0 = r22
            java.lang.String r0 = r0.mHashValue     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r22 = r0
            java.lang.StringBuilder r21 = r21.append(r22)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            java.lang.String r21 = r21.toString()     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            com.tencent.imsdk.tool.etc.IMLogger.w(r21)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r0 = r24
            com.tencent.imsdk.tool.etc.DownloadItem r0 = r0.mDownloadItem     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            r0 = r21
            java.lang.String r0 = r0.mLocalFilePath     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            r21 = r0
            delFileByPath(r21)     // Catch:{ Exception -> 0x0100, all -> 0x01d1 }
            goto L_0x018f
        L_0x0228:
            r14 = move-exception
            r14.printStackTrace()
            r6 = r7
            r4 = r5
            goto L_0x0110
        L_0x0230:
            r14 = move-exception
            r14.printStackTrace()
            goto L_0x0110
        L_0x0236:
            r14 = move-exception
            r14.printStackTrace()
            goto L_0x01de
        L_0x023b:
            r21 = move-exception
            goto L_0x01d4
        L_0x023d:
            r21 = move-exception
            r4 = r5
            goto L_0x01d4
        L_0x0240:
            r14 = move-exception
            goto L_0x0103
        L_0x0243:
            r14 = move-exception
            r4 = r5
            goto L_0x0103
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.tool.etc.DownloadThread.run():void");
    }

    /* access modifiers changed from: private */
    public static boolean isFinished() {
        return sIsFinished;
    }

    /* access modifiers changed from: private */
    public static void setFinished(boolean isFinished) {
        sIsFinished = isFinished;
    }

    /* access modifiers changed from: private */
    public boolean isStarted() {
        return this.mIsStarted;
    }

    public static void delFileByPath(String filePath) {
        File tempPicFile;
        if (!T.ckIsEmpty(filePath) && (tempPicFile = new File(filePath)) != null && !tempPicFile.delete()) {
            IMLogger.w("tempPicFile delete failed");
        }
    }
}
