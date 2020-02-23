package com.tencent.imsdk.expansion.downloader;

import android.content.Context;
import android.content.pm.PackageManager;
import com.tencent.imsdk.expansion.downloader.impl.DownloadInfo;
import com.tencent.imsdk.expansion.downloader.impl.DownloadNotification;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.imsdk.expansion.downloader.impl.DownloadsDB;
import java.io.File;

public abstract class LicenseVerificationBase {
    /* access modifiers changed from: protected */
    public byte[] SALT;
    protected DownloaderService dsInstance;
    /* access modifiers changed from: private */
    public IUserConfirm iUserConfirm = null;
    /* access modifiers changed from: protected */
    public String publicKey;

    public interface ILicenseVerificationResult {
        void onLVFail(String str);

        void onLVSuccess(int i, DownloadsDB downloadsDB);
    }

    public abstract void updateDownloadInfo2DB(Context context, ILicenseVerificationResult iLicenseVerificationResult);

    public LicenseVerificationBase(DownloaderService instance) {
        this.dsInstance = instance;
    }

    public DownloaderService getDownloadService() {
        return this.dsInstance;
    }

    public void setPublicKey(String publicKey2) {
        this.publicKey = publicKey2;
    }

    public void setSALT(byte[] salt) {
        this.SALT = salt;
    }

    public void setUserConfirmListener(IUserConfirm iUserConfirm2) {
        this.iUserConfirm = iUserConfirm2;
    }

    public boolean handleFileUpdated(DownloadsDB db, int index, String filename, long fileSize) {
        String oldFile;
        boolean z = true;
        DownloadInfo di = db.getDownloadInfoByFileName(filename);
        if (!(di == null || (oldFile = di.mFileName) == null)) {
            if (filename.equals(oldFile)) {
                return false;
            }
            File f = new File(Helpers.generateSaveFileName(this.dsInstance, oldFile));
            if (f.exists()) {
                f.delete();
            }
        }
        if (Helpers.doesFileExist(this.dsInstance, filename, fileSize, true)) {
            z = false;
        }
        return z;
    }

    public void updateLVL(DownloaderService downloaderService) {
        DownloaderService.setServiceRunning(true);
        final DownloadNotification mNotification = this.dsInstance.getmNotification();
        mNotification.onDownloadStateChanged(2);
        final Context mContext = this.dsInstance.getApplicationContext();
        updateDownloadInfo2DB(mContext, new ILicenseVerificationResult() {
            public void onLVSuccess(int status, DownloadsDB db) {
                long contentLength = 0;
                for (DownloadInfo di : db.getDownloads()) {
                    IMLogger.d("TotalBytes = " + di.mTotalBytes + " currentBytes = " + di.mCurrentBytes);
                    contentLength += di.mTotalBytes - di.mCurrentBytes;
                }
                if (LicenseVerificationBase.this.iUserConfirm == null || LicenseVerificationBase.this.iUserConfirm.isAccepted(String.valueOf(contentLength))) {
                    try {
                        db.updateMetadata(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode, status);
                        switch (LicenseVerificationBase.this.dsInstance.startDownloadServiceIfRequired()) {
                            case 0:
                                mNotification.onDownloadStateChanged(5);
                                break;
                            case 1:
                                IMLogger.e("In LVL checking loop!");
                                mNotification.onDownloadStateChanged(15);
                                throw new RuntimeException("Error with LVL checking and database integrity");
                        }
                        DownloaderService.setServiceRunning(false);
                    } catch (PackageManager.NameNotFoundException e1) {
                        e1.printStackTrace();
                        throw new RuntimeException("Error with getting information from package name");
                    } catch (Throwable th) {
                        DownloaderService.setServiceRunning(false);
                        throw th;
                    }
                } else {
                    IMLogger.d("User Cancel, may be file (" + contentLength + " byte) is too large");
                    mNotification.onDownloadStateChanged(18);
                    DownloaderService.setServiceRunning(false);
                }
            }

            public void onLVFail(String message) {
                mNotification.onDownloadStateChanged(19);
                DownloaderService.setServiceRunning(false);
                if (message != null) {
                    IMLogger.e(message);
                }
            }
        });
    }
}
