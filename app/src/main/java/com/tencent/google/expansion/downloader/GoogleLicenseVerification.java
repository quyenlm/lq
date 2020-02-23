package com.tencent.google.expansion.downloader;

import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.APKExpansionPolicy;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.Policy;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.LicenseVerificationBase;
import com.tencent.imsdk.expansion.downloader.impl.DownloadInfo;
import com.tencent.imsdk.expansion.downloader.impl.DownloadNotification;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.imsdk.expansion.downloader.impl.DownloadsDB;

public class GoogleLicenseVerification extends LicenseVerificationBase {
    /* access modifiers changed from: private */
    public DownloaderService dsInstance;

    public GoogleLicenseVerification(DownloaderService downloaderService) {
        super(downloaderService);
        this.dsInstance = downloaderService;
    }

    public void updateDownloadInfo2DB(Context mContext, LicenseVerificationBase.ILicenseVerificationResult iLVResult) {
        new Handler(mContext.getMainLooper()).post(new LVLRunnable(mContext, iLVResult));
    }

    private class LVLRunnable implements Runnable {
        final LicenseVerificationBase.ILicenseVerificationResult iLVResult;
        final Context mContext;

        LVLRunnable(Context context, LicenseVerificationBase.ILicenseVerificationResult iLVResult2) {
            this.mContext = context;
            this.iLVResult = iLVResult2;
        }

        public void run() {
            final DownloadNotification mNotification = GoogleLicenseVerification.this.dsInstance.getmNotification();
            final APKExpansionPolicy aep = new APKExpansionPolicy(this.mContext, new AESObfuscator(GoogleLicenseVerification.this.SALT, this.mContext.getPackageName(), Settings.Secure.getString(this.mContext.getContentResolver(), "android_id")));
            IMLogger.d("GoogleLicenseVerification enter ...." + this.mContext.getPackageName());
            aep.resetPolicy();
            new LicenseChecker(this.mContext, aep, GoogleLicenseVerification.this.publicKey).checkAccess(new LicenseCheckerCallback() {
                public void allow(int reason) {
                    int count = aep.getExpansionURLCount();
                    DownloadsDB db = DownloadsDB.getDB(LVLRunnable.this.mContext);
                    int status = 0;
                    if (count != 0) {
                        for (int i = 0; i < count; i++) {
                            String currentFileName = aep.getExpansionFileName(i);
                            if (currentFileName != null) {
                                DownloadInfo di = new DownloadInfo(i, currentFileName, LVLRunnable.this.mContext.getPackageName());
                                long fileSize = aep.getExpansionFileSize(i);
                                if (GoogleLicenseVerification.this.handleFileUpdated(db, i, currentFileName, fileSize)) {
                                    status |= -1;
                                    di.resetDownload();
                                    di.mUri = aep.getExpansionURL(i);
                                    di.mTotalBytes = fileSize;
                                    di.mStatus = status;
                                    db.updateDownload(di);
                                } else {
                                    DownloadInfo dbdi = db.getDownloadInfoByFileName(di.mFileName);
                                    if (dbdi == null) {
                                        IMLogger.d("file " + di.mFileName + " found. Not downloading.");
                                        di.mStatus = 200;
                                        di.mTotalBytes = fileSize;
                                        di.mCurrentBytes = fileSize;
                                        di.mUri = aep.getExpansionURL(i);
                                        db.updateDownload(di);
                                    } else if (dbdi.mStatus != 200) {
                                        dbdi.mUri = aep.getExpansionURL(i);
                                        db.updateDownload(dbdi);
                                        status |= -1;
                                    }
                                }
                            }
                        }
                    }
                    LVLRunnable.this.iLVResult.onLVSuccess(status, db);
                }

                public void dontAllow(int reason) {
                    switch (reason) {
                        case Policy.RETRY /*291*/:
                            mNotification.onDownloadStateChanged(16);
                            break;
                        case 561:
                            mNotification.onDownloadStateChanged(15);
                            break;
                    }
                    LVLRunnable.this.iLVResult.onLVFail("dontAllow reason code = " + reason);
                }

                public void applicationError(int errorCode) {
                    mNotification.onDownloadStateChanged(16);
                    LVLRunnable.this.iLVResult.onLVFail("applicationError code = " + errorCode);
                }
            });
        }
    }
}
