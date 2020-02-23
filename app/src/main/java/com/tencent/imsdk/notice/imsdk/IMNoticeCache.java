package com.tencent.imsdk.notice.imsdk;

import android.content.Context;
import android.webkit.URLUtil;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.notice.api.IMNoticeListener;
import com.tencent.imsdk.notice.entity.IMNoticeInfo;
import com.tencent.imsdk.notice.entity.IMNoticePic;
import com.tencent.imsdk.notice.entity.IMNoticeResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import com.tencent.imsdk.tool.net.AsyncHttpResponseHandler;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;

public class IMNoticeCache {
    private static final String LOCAL_PIC_PREFIX = "Notice_";
    /* access modifiers changed from: private */
    public static final String SECOND_DIRECTORY = (File.separator + "images" + File.separator);
    /* access modifiers changed from: private */
    public boolean isAllPicturesCached = true;
    private AsyncHttpClient mAsyncHttpClient = null;
    private ClearUnusedPics mClearUnusedPics = null;
    public Context mContext;
    private int mSumOfPictures = 0;

    /* access modifiers changed from: private */
    public File getExternalDir() {
        if (this.mContext != null) {
            return this.mContext.getExternalCacheDir();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void closeQuietly(Closeable... files) {
        for (Closeable file : files) {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public IMNoticeCache(Context context) {
        this.mContext = context;
    }

    public void saveAndClearExpiredNoticePicture(IMNoticeResult noticeResult, IMNoticeListener noticeListener) {
        if (noticeResult == null) {
            IMNoticeResult noticeResult2 = new IMNoticeResult();
            noticeResult2.imsdkRetCode = 11;
            noticeResult2.imsdkRetMsg = IMErrorMsg.getMessageByCode(11);
            if (noticeListener != null) {
                noticeListener.onLoadNoticeCallback(noticeResult2);
                return;
            }
            return;
        }
        List<IMNoticeInfo> noticeInfoList = noticeResult.noticesList;
        int noticeInfoSize = noticeInfoList.size();
        String[] noticeIds = new String[noticeInfoSize];
        for (int i = 0; i < noticeInfoSize; i++) {
            if (noticeInfoList.get(i) != null) {
                noticeIds[i] = String.valueOf(noticeInfoList.get(i).noticeId);
            }
        }
        clearNoticePicOutOfDate(noticeIds);
        saveNoticePicture(noticeResult, noticeListener);
    }

    /* access modifiers changed from: private */
    public void isAllPictureLoaded(IMNoticeResult noticeResult, IMNoticeListener noticeListener, List<IMNoticeInfo> noticeInfoWithLocalPicPathList) {
        int i = this.mSumOfPictures - 1;
        this.mSumOfPictures = i;
        if (i <= 0) {
            IMLogger.d("final step , isAllPicturesCached = " + this.isAllPicturesCached);
            if (this.isAllPicturesCached) {
                noticeResult.noticesList = noticeInfoWithLocalPicPathList;
            } else {
                noticeResult.imsdkRetCode = 11;
                noticeResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(11);
                ImsdkNotice.mLastRequestStartTime = 0;
            }
            if (noticeListener != null) {
                noticeListener.onLoadNoticeCallback(noticeResult);
            }
        }
    }

    public void saveNoticePicture(IMNoticeResult noticeResult, IMNoticeListener noticeListener) {
        final List<IMNoticeInfo> infoWithLocalPicPathList = new ArrayList<>(noticeResult.noticesList);
        for (IMNoticeInfo noticeInfo : noticeResult.noticesList) {
            if (noticeInfo.noticePics != null) {
                this.mSumOfPictures += noticeInfo.noticePics.size();
            }
        }
        IMLogger.d("sum of picture is  " + this.mSumOfPictures);
        if (this.mSumOfPictures != 0) {
            for (int indexOfInfo = 0; indexOfInfo < noticeResult.noticesList.size(); indexOfInfo++) {
                IMNoticeInfo noticeInfo2 = noticeResult.noticesList.get(indexOfInfo);
                if (!(noticeInfo2 == null || noticeInfo2.noticePics == null)) {
                    for (int indexOfPics = 0; indexOfPics < noticeInfo2.noticePics.size(); indexOfPics++) {
                        final IMNoticePic noticePic = noticeInfo2.noticePics.get(indexOfPics);
                        final int indexOfInfoInner = indexOfInfo;
                        final int indexOfPicsInner = indexOfPics;
                        if (noticePic != null) {
                            if (!URLUtil.isHttpUrl(noticePic.picUrl) && !URLUtil.isHttpsUrl(noticePic.picUrl)) {
                                isAllPictureLoaded(noticeResult, noticeListener, infoWithLocalPicPathList);
                            } else if (isNoticePicUnmodified(noticePic)) {
                                IMLogger.d("cache found for " + noticePic.picUrl);
                                infoWithLocalPicPathList.get(indexOfInfo).noticePics.get(indexOfPics).picUrl = getNoticePicFilePath(noticePic);
                                isAllPictureLoaded(noticeResult, noticeListener, infoWithLocalPicPathList);
                            } else {
                                if (this.mAsyncHttpClient == null) {
                                    IMLogger.d("init network request async http client");
                                    this.mAsyncHttpClient = new AsyncHttpClient();
                                }
                                final IMNoticeResult iMNoticeResult = noticeResult;
                                final IMNoticeListener iMNoticeListener = noticeListener;
                                this.mAsyncHttpClient.get(noticePic.picUrl, new AsyncHttpResponseHandler() {
                                    /* JADX WARNING: Unknown top exception splitter block from list: {B:16:0x00a2=Splitter:B:16:0x00a2, B:20:0x00c5=Splitter:B:20:0x00c5} */
                                    /* Code decompiled incorrectly, please refer to instructions dump. */
                                    public void onSuccess(int r11, org.apache.http.Header[] r12, byte[] r13) {
                                        /*
                                            r10 = this;
                                            r7 = 1
                                            r8 = 0
                                            r2 = 0
                                            r5 = 200(0xc8, float:2.8E-43)
                                            if (r11 != r5) goto L_0x0089
                                            java.io.File r1 = new java.io.File     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            com.tencent.imsdk.notice.entity.IMNoticePic r6 = r2     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.String r5 = r5.getNoticePicFilePath(r6)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            r1.<init>(r5)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.io.File r5 = r1.getParentFile()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            boolean r5 = r5.exists()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            if (r5 != 0) goto L_0x004c
                                            java.io.File r5 = r1.getParentFile()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            boolean r4 = r5.mkdirs()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            r6.<init>()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            if (r4 == 0) goto L_0x009e
                                            java.lang.String r5 = "success"
                                        L_0x002f:
                                            java.lang.StringBuilder r5 = r6.append(r5)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.String r6 = " to create direct of "
                                            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.io.File r6 = r1.getParentFile()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.String r6 = r6.toString()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            java.lang.String r5 = r5.toString()     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r5)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                        L_0x004c:
                                            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            r3.<init>(r1)     // Catch:{ FileNotFoundException -> 0x00a1, IOException -> 0x00c4 }
                                            r3.write(r13)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            r5.<init>()     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.String r6 = "save picture successfully in "
                                            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.String r6 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.String r5 = r5.toString()     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r5)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.util.List r5 = r3     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            int r6 = r4     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.Object r5 = r5.get(r6)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            com.tencent.imsdk.notice.entity.IMNoticeInfo r5 = (com.tencent.imsdk.notice.entity.IMNoticeInfo) r5     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.util.List<com.tencent.imsdk.notice.entity.IMNoticePic> r5 = r5.noticePics     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            int r6 = r5     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.Object r5 = r5.get(r6)     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            com.tencent.imsdk.notice.entity.IMNoticePic r5 = (com.tencent.imsdk.notice.entity.IMNoticePic) r5     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            java.lang.String r6 = r1.toString()     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            r5.picUrl = r6     // Catch:{ FileNotFoundException -> 0x0103, IOException -> 0x0100, all -> 0x00fd }
                                            r2 = r3
                                        L_0x0089:
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            java.io.Closeable[] r6 = new java.io.Closeable[r7]
                                            r6[r8] = r2
                                            r5.closeQuietly(r6)
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            com.tencent.imsdk.notice.entity.IMNoticeResult r6 = r6
                                            com.tencent.imsdk.notice.api.IMNoticeListener r7 = r7
                                            java.util.List r8 = r3
                                            r5.isAllPictureLoaded(r6, r7, r8)
                                        L_0x009d:
                                            return
                                        L_0x009e:
                                            java.lang.String r5 = "fail"
                                            goto L_0x002f
                                        L_0x00a1:
                                            r0 = move-exception
                                        L_0x00a2:
                                            java.lang.String r5 = r0.getMessage()     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.tool.etc.IMLogger.e(r5)     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this     // Catch:{ all -> 0x00e7 }
                                            r6 = 0
                                            boolean unused = r5.isAllPicturesCached = r6     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            java.io.Closeable[] r6 = new java.io.Closeable[r7]
                                            r6[r8] = r2
                                            r5.closeQuietly(r6)
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            com.tencent.imsdk.notice.entity.IMNoticeResult r6 = r6
                                            com.tencent.imsdk.notice.api.IMNoticeListener r7 = r7
                                            java.util.List r8 = r3
                                            r5.isAllPictureLoaded(r6, r7, r8)
                                            goto L_0x009d
                                        L_0x00c4:
                                            r0 = move-exception
                                        L_0x00c5:
                                            java.lang.String r5 = r0.getMessage()     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.tool.etc.IMLogger.e(r5)     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this     // Catch:{ all -> 0x00e7 }
                                            r6 = 0
                                            boolean unused = r5.isAllPicturesCached = r6     // Catch:{ all -> 0x00e7 }
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            java.io.Closeable[] r6 = new java.io.Closeable[r7]
                                            r6[r8] = r2
                                            r5.closeQuietly(r6)
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r5 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            com.tencent.imsdk.notice.entity.IMNoticeResult r6 = r6
                                            com.tencent.imsdk.notice.api.IMNoticeListener r7 = r7
                                            java.util.List r8 = r3
                                            r5.isAllPictureLoaded(r6, r7, r8)
                                            goto L_0x009d
                                        L_0x00e7:
                                            r5 = move-exception
                                        L_0x00e8:
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r6 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            java.io.Closeable[] r7 = new java.io.Closeable[r7]
                                            r7[r8] = r2
                                            r6.closeQuietly(r7)
                                            com.tencent.imsdk.notice.imsdk.IMNoticeCache r6 = com.tencent.imsdk.notice.imsdk.IMNoticeCache.this
                                            com.tencent.imsdk.notice.entity.IMNoticeResult r7 = r6
                                            com.tencent.imsdk.notice.api.IMNoticeListener r8 = r7
                                            java.util.List r9 = r3
                                            r6.isAllPictureLoaded(r7, r8, r9)
                                            throw r5
                                        L_0x00fd:
                                            r5 = move-exception
                                            r2 = r3
                                            goto L_0x00e8
                                        L_0x0100:
                                            r0 = move-exception
                                            r2 = r3
                                            goto L_0x00c5
                                        L_0x0103:
                                            r0 = move-exception
                                            r2 = r3
                                            goto L_0x00a2
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.notice.imsdk.IMNoticeCache.AnonymousClass1.onSuccess(int, org.apache.http.Header[], byte[]):void");
                                    }

                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        IMLogger.e("save picture fail : " + error.getMessage() + " from " + noticePic.picUrl);
                                        boolean unused = IMNoticeCache.this.isAllPicturesCached = false;
                                        IMNoticeCache.this.isAllPictureLoaded(iMNoticeResult, iMNoticeListener, infoWithLocalPicPathList);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        } else if (noticeListener != null) {
            noticeListener.onLoadNoticeCallback(noticeResult);
        }
    }

    class ClearUnusedPics implements Runnable {
        private String[] mNoticeIds;

        ClearUnusedPics() {
        }

        public void setNoticeIds(String[] notices) {
            this.mNoticeIds = notices;
        }

        public void run() {
            File file = new File(IMNoticeCache.this.getExternalDir(), IMNoticeCache.SECOND_DIRECTORY);
            IMLogger.d(file.toString());
            File[] unSortFiles = file.listFiles();
            if (unSortFiles != null) {
                IMLogger.d("number of pics those already cached are " + unSortFiles.length);
                if (this.mNoticeIds == null || this.mNoticeIds.length <= 0) {
                    IMLogger.d("delete all cached ...");
                    for (File file2Del : unSortFiles) {
                        if (file2Del.exists()) {
                            IMLogger.d("delete file : " + file2Del.toString());
                            file2Del.delete();
                        }
                    }
                    return;
                }
                for (File file2Compare : unSortFiles) {
                    boolean isNeed2Delete = true;
                    String[] strArr = this.mNoticeIds;
                    int length = strArr.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        if (file2Compare.toString().contains(IMNoticeCache.LOCAL_PIC_PREFIX + strArr[i] + "_")) {
                            isNeed2Delete = false;
                            break;
                        }
                        i++;
                    }
                    if (isNeed2Delete && file2Compare.exists()) {
                        IMLogger.d("delete file : " + file2Compare.toString());
                        file2Compare.delete();
                    }
                }
            }
        }
    }

    public void clearNoticePicOutOfDate(String[] noticeIds) {
        IMLogger.d("start clear pics out of date , and  " + noticeIds.length + " will be save");
        if (this.mClearUnusedPics == null) {
            this.mClearUnusedPics = new ClearUnusedPics();
        }
        this.mClearUnusedPics.setNoticeIds(noticeIds);
        new Thread(this.mClearUnusedPics).start();
    }

    public boolean isNoticePicExist(IMNoticePic noticePic) {
        if (T.ckNonEmpty(String.valueOf(noticePic.noticeId), noticePic.picUrl, noticePic.picHash)) {
            return false;
        }
        return new File(getNoticePicFilePath(noticePic)).exists();
    }

    public void deleteNoticePic(IMNoticePic noticePic) {
        if (isNoticePicExist(noticePic)) {
            new File(getNoticePicFilePath(noticePic)).delete();
        }
    }

    public String getNoticePicFilePath(IMNoticePic noticePic) {
        if (this.mContext != null) {
            if (!T.ckNonEmpty(String.valueOf(noticePic.noticeId), noticePic.picUrl, noticePic.picHash)) {
                return new File(getExternalDir(), SECOND_DIRECTORY + LOCAL_PIC_PREFIX + noticePic.noticeId + "_" + noticePic.picHash).toString();
            }
        }
        IMLogger.d("noticeId = " + noticePic.noticeId + " ,url = " + noticePic.picUrl + " ,hash = " + noticePic.picHash);
        return "";
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:26:0x00c2=Splitter:B:26:0x00c2, B:22:0x00b4=Splitter:B:22:0x00b4} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isNoticePicUnmodified(com.tencent.imsdk.notice.entity.IMNoticePic r15) {
        /*
            r14 = this;
            r0 = 3
            java.lang.String[] r0 = new java.lang.String[r0]
            r1 = 0
            int r2 = r15.noticeId
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r0[r1] = r2
            r1 = 1
            java.lang.String r2 = r15.picUrl
            r0[r1] = r2
            r1 = 2
            java.lang.String r2 = r15.picHash
            r0[r1] = r2
            boolean r0 = com.tencent.imsdk.tool.etc.T.ckNonEmpty(r0)
            if (r0 == 0) goto L_0x001e
            r0 = 0
        L_0x001d:
            return r0
        L_0x001e:
            java.io.File r9 = new java.io.File
            java.lang.String r0 = r14.getNoticePicFilePath(r15)
            r9.<init>(r0)
            boolean r0 = r9.exists()
            if (r0 != 0) goto L_0x002f
            r0 = 0
            goto L_0x001d
        L_0x002f:
            r10 = 0
            java.io.FileInputStream r11 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00a1, NoSuchAlgorithmException -> 0x00b3, IOException -> 0x00c1 }
            r11.<init>(r9)     // Catch:{ FileNotFoundException -> 0x00a1, NoSuchAlgorithmException -> 0x00b3, IOException -> 0x00c1 }
            java.nio.channels.FileChannel r0 = r11.getChannel()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.nio.channels.FileChannel$MapMode r1 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            r2 = 0
            long r4 = r9.length()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.nio.MappedByteBuffer r6 = r0.map(r1, r2, r4)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r0 = "MD5"
            java.security.MessageDigest r12 = java.security.MessageDigest.getInstance(r0)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            r12.update(r6)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            byte[] r7 = r12.digest()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r0 = com.tencent.imsdk.tool.etc.HexUtil.bytes2HexStr(r7)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.util.Locale r1 = java.util.Locale.CHINA     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r13 = r0.toLowerCase(r1)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            r0.<init>()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r1 = "picture md5 :"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.StringBuilder r0 = r0.append(r13)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r1 = ", picture hash :"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r1 = r15.picHash     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r0 = r0.toString()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            com.tencent.imsdk.tool.etc.IMLogger.d((java.lang.String) r0)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            java.lang.String r0 = r15.picHash     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            boolean r0 = r13.equalsIgnoreCase(r0)     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            if (r0 != 0) goto L_0x0094
            r9.delete()     // Catch:{ FileNotFoundException -> 0x00e3, NoSuchAlgorithmException -> 0x00e0, IOException -> 0x00dd, all -> 0x00da }
            r0 = 0
            r1 = 1
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r2 = 0
            r1[r2] = r11
            r14.closeQuietly(r1)
            goto L_0x001d
        L_0x0094:
            r0 = 1
            java.io.Closeable[] r0 = new java.io.Closeable[r0]
            r1 = 0
            r0[r1] = r11
            r14.closeQuietly(r0)
            r10 = r11
        L_0x009e:
            r0 = 1
            goto L_0x001d
        L_0x00a1:
            r8 = move-exception
        L_0x00a2:
            java.lang.String r0 = "picture not found"
            com.tencent.imsdk.tool.etc.IMLogger.e(r0)     // Catch:{ all -> 0x00cf }
            r0 = 0
            r1 = 1
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r2 = 0
            r1[r2] = r10
            r14.closeQuietly(r1)
            goto L_0x001d
        L_0x00b3:
            r8 = move-exception
        L_0x00b4:
            r8.printStackTrace()     // Catch:{ all -> 0x00cf }
            r0 = 1
            java.io.Closeable[] r0 = new java.io.Closeable[r0]
            r1 = 0
            r0[r1] = r10
            r14.closeQuietly(r0)
            goto L_0x009e
        L_0x00c1:
            r8 = move-exception
        L_0x00c2:
            r8.printStackTrace()     // Catch:{ all -> 0x00cf }
            r0 = 1
            java.io.Closeable[] r0 = new java.io.Closeable[r0]
            r1 = 0
            r0[r1] = r10
            r14.closeQuietly(r0)
            goto L_0x009e
        L_0x00cf:
            r0 = move-exception
        L_0x00d0:
            r1 = 1
            java.io.Closeable[] r1 = new java.io.Closeable[r1]
            r2 = 0
            r1[r2] = r10
            r14.closeQuietly(r1)
            throw r0
        L_0x00da:
            r0 = move-exception
            r10 = r11
            goto L_0x00d0
        L_0x00dd:
            r8 = move-exception
            r10 = r11
            goto L_0x00c2
        L_0x00e0:
            r8 = move-exception
            r10 = r11
            goto L_0x00b4
        L_0x00e3:
            r8 = move-exception
            r10 = r11
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.imsdk.notice.imsdk.IMNoticeCache.isNoticePicUnmodified(com.tencent.imsdk.notice.entity.IMNoticePic):boolean");
    }
}
