package com.tencent.imsdk.expansion.downloader.api;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Messenger;
import com.tencent.imsdk.expansion.downloader.DownloadProgressInfo;
import com.tencent.imsdk.expansion.downloader.Helpers;
import com.tencent.imsdk.expansion.downloader.IDownloaderService;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.IMSDKLicenseVerification;
import com.tencent.imsdk.expansion.downloader.LicenseVerificationBase;
import com.tencent.imsdk.expansion.downloader.impl.CustomLicenseVerificationFactory;
import java.lang.reflect.Field;

public class SimpleDownloaderDialog extends DownloaderUIBase implements DialogInterface.OnClickListener {
    private static final String DIALOG_CANCEL = "Cancel";
    private static final String DIALOG_CONTINUE = "Continue";
    private static final String DIALOG_PAUSE = "Pause";
    private static final String DIALOG_SETTINGS = "Settings";
    private String customLVClassName = IMSDKLicenseVerification.class.getName();
    private String customLVTag = null;
    private int dialogSwitch = -1;
    private boolean isSettingsShowing = false;
    private Context mContext;
    private ProgressDialog mPD;
    private IDownloaderService mRemoteService;
    private boolean mStatePaused = true;

    private void initializeDownloadUI(Context context) {
        this.mPD = new ProgressDialog(context);
        this.mPD.setButton(-2, DIALOG_CANCEL, this);
        this.mPD.setButton(-3, DIALOG_PAUSE, this);
        this.mPD.setCanceledOnTouchOutside(true);
        this.mPD.setProgressStyle(1);
        this.mPD.setCancelable(false);
        this.mPD.setTitle("Download Expansion Files ...");
    }

    public int onCreate(Context context, PendingIntent pendingIntent) {
        this.mContext = context;
        if (this.customLVTag == null || this.customLVTag.length() <= 0) {
            CustomLicenseVerificationFactory.setLicenseVerification(context, this.customLVClassName);
        } else {
            CustomLicenseVerificationFactory.setLicenseVerificationByTag(context, this.customLVTag);
        }
        int status = super.onCreate(context, pendingIntent);
        if (status != 0) {
            initializeDownloadUI(context);
        }
        return status;
    }

    public void onStart(Context context) {
        super.onStart(context);
        if (this.mPD != null) {
            this.mPD.show();
        }
    }

    public void onStop(Context context) {
        super.onStop(context);
        if (this.mPD != null) {
            dialogAutoDissmissSwitch(true);
            this.mPD.dismiss();
        }
    }

    public void onServiceConnected(Messenger m) {
        super.onServiceConnected(m);
        this.mRemoteService = super.getmRemoteService();
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        super.onDownloadProgress(progress);
        this.mPD.setMax((int) (progress.mOverallTotal >> 8));
        this.mPD.setProgress((int) (progress.mOverallProgress >> 8));
        this.mPD.setMessage(Helpers.getDownloadProgressString(progress.mOverallProgress, progress.mOverallTotal));
    }

    private void dialogAutoDissmissSwitch(boolean autoDissmiss) {
        int i = 1;
        if (this.dialogSwitch != -1) {
            if (autoDissmiss) {
                if (this.dialogSwitch == 1) {
                    return;
                }
            } else if (this.dialogSwitch == 0) {
                return;
            }
        }
        try {
            Field field = this.mPD.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(this.mPD, Boolean.valueOf(autoDissmiss));
            if (!autoDissmiss) {
                i = 0;
            }
            this.dialogSwitch = i;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public void onDownloadStateChanged(int newState) {
        super.onDownloadStateChanged(newState);
        this.isSettingsShowing = false;
        switch (newState) {
            case 5:
            case 6:
            case 10:
            case 18:
            case 19:
                if (this.mPD != null) {
                    dialogAutoDissmissSwitch(true);
                    this.mPD.dismiss();
                    return;
                }
                return;
            case 7:
                if (this.mStatePaused) {
                    this.mStatePaused = false;
                    this.mPD.getButton(-3).setText(DIALOG_CONTINUE);
                    return;
                }
                return;
            case 8:
            case 11:
                this.isSettingsShowing = true;
                this.mPD.getButton(-3).setText(DIALOG_SETTINGS);
                return;
            default:
                this.isSettingsShowing = false;
                return;
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case -3:
                if (this.isSettingsShowing) {
                    this.mContext.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
                    return;
                }
                dialogAutoDissmissSwitch(false);
                if (this.mStatePaused) {
                    this.mRemoteService.requestPauseDownload();
                    this.mStatePaused = false;
                } else {
                    this.mRemoteService.requestContinueDownload();
                    this.mStatePaused = true;
                }
                this.mPD.getButton(-3).setText(this.mStatePaused ? DIALOG_PAUSE : DIALOG_CONTINUE);
                return;
            case -2:
                this.mRemoteService.requestAbortDownload();
                if (this.mPD != null) {
                    dialogAutoDissmissSwitch(true);
                    this.mPD.dismiss();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setLicenseVerification(Class<?> customLV) {
        if (customLV == null || !LicenseVerificationBase.class.isAssignableFrom(customLV)) {
            IMLogger.e("custom License Verification is not inherited from LicenseVerificationBase, please check again");
            return;
        }
        this.customLVTag = null;
        this.customLVClassName = customLV.getName();
    }

    public void setLicenseVerificationByTag(String tag) {
        this.customLVTag = tag;
    }
}
