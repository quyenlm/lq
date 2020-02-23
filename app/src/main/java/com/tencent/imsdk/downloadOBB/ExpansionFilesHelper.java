package com.tencent.imsdk.downloadOBB;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Messenger;
import com.tencent.imsdk.expansion.downloader.DownloadProgressInfo;
import com.tencent.imsdk.expansion.downloader.IDownloaderClient;
import com.tencent.imsdk.expansion.downloader.IMLogger;
import com.tencent.imsdk.expansion.downloader.IMSDKLicenseVerification;
import com.tencent.imsdk.expansion.downloader.IUserConfirm;
import com.tencent.imsdk.expansion.downloader.api.DownloaderUIBase;
import com.tencent.imsdk.expansion.downloader.impl.CustomLicenseVerificationFactory;
import com.unity3d.player.UnityPlayer;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExpansionFilesHelper implements IDownloaderClient {
    private static final String TARGET_GAME_OBJECT_NAME = "Tencent.iMSDK.IMExpansionFilesDownload";
    private static final String UNITY_SETTINGS_FILENAME = "bin/Data/settings.xml";
    private static final String UNITY_SETTINGS_OBB_LABEL = "useObb";
    /* access modifiers changed from: private */
    public static DownloaderUIBase downloaderUIBase = null;
    private static int mCurrentState = 1;

    public void onServiceConnected(Messenger m) {
        UnityPlayer.UnitySendMessage(TARGET_GAME_OBJECT_NAME, "onServiceConnected", "");
    }

    public void onDownloadStateChanged(int newState) {
        Intent intent;
        mCurrentState = newState;
        if (newState == 5 && (intent = UnityPlayer.currentActivity.getPackageManager().getLaunchIntentForPackage(UnityPlayer.currentActivity.getPackageName())) != null) {
            intent.addFlags(67108864);
            UnityPlayer.currentActivity.startActivity(intent);
        }
        UnityPlayer.UnitySendMessage(TARGET_GAME_OBJECT_NAME, "onDownloadStateChanged", newState + "");
    }

    public void onDownloadProgress(DownloadProgressInfo progress) {
        UnityPlayer.UnitySendMessage(TARGET_GAME_OBJECT_NAME, "onDownloadProgress", progress.mOverallTotal + "#" + progress.mOverallProgress);
    }

    /* access modifiers changed from: package-private */
    public boolean isExpansionFilesExist(boolean isMainOBB) {
        try {
            String root = Environment.getExternalStorageDirectory().getPath();
            String packageName = UnityPlayer.currentActivity.getPackageName();
            String obbFilePath = root + "/Android/obb/" + packageName + (isMainOBB ? "/main." : "/patch.") + UnityPlayer.currentActivity.getPackageManager().getPackageInfo(packageName, 0).versionCode + "." + packageName + ".obb";
            IMLogger.d("isExpansionFilesExist obbFilePath = " + obbFilePath);
            if (new File(obbFilePath).exists()) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onPreExecute(String publicKey, byte[] salt) {
        IMLogger.d("onPreExecute ..." + publicKey + " /n " + salt.toString());
        if (publicKey != null && salt != null && salt.length != 0) {
            CustomLicenseVerificationFactory.BASE64_PUBLIC_KEY = publicKey;
            CustomLicenseVerificationFactory.SALT = salt;
        }
    }

    public void onInitial(final String tag) {
        IMLogger.d("onInitial ..." + tag);
        if (downloaderUIBase == null) {
            downloaderUIBase = new DownloaderUIBase();
        }
        UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
            public void run() {
                CustomLicenseVerificationFactory.setLicenseVerificationByTag(UnityPlayer.currentActivity, tag);
                ExpansionFilesHelper.downloaderUIBase.setCustomDownloaderClient(ExpansionFilesHelper.this);
                if (ExpansionFilesHelper.downloaderUIBase.onCreate(UnityPlayer.currentActivity, (PendingIntent) null) != 0) {
                    ExpansionFilesHelper.downloaderUIBase.onStart(UnityPlayer.currentActivity);
                }
            }
        });
    }

    public void onStop() {
        IMLogger.d("onStop ...");
        if (downloaderUIBase != null) {
            downloaderUIBase.onStop(UnityPlayer.currentActivity);
        }
    }

    public void requestAbortDownload() {
        if (downloaderUIBase != null) {
            downloaderUIBase.getmRemoteService().requestAbortDownload();
        }
    }

    public void requestPauseDownload() {
        if (downloaderUIBase != null) {
            downloaderUIBase.getmRemoteService().requestPauseDownload();
        }
    }

    public void requestContinueDownload() {
        if (downloaderUIBase != null) {
            downloaderUIBase.getmRemoteService().requestContinueDownload();
        }
    }

    public void setDownloadFlag(int flags) {
        if (downloaderUIBase != null) {
            downloaderUIBase.getmRemoteService().setDownloadFlags(flags);
        }
    }

    public boolean isUnityApkUseObb() {
        try {
            InputStream ins = UnityPlayer.currentActivity.getAssets().open(UNITY_SETTINGS_FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            while (true) {
                String tempStringContainer = br.readLine();
                if (tempStringContainer == null) {
                    break;
                } else if (tempStringContainer.contains(UNITY_SETTINGS_OBB_LABEL)) {
                    if (tempStringContainer.contains("True")) {
                        br.close();
                        ins.close();
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCustomURL(String url) {
        if (url == null || url.isEmpty()) {
            IMLogger.e("URL can not be null");
        } else {
            IMSDKLicenseVerification.setCustomFetchURL(url);
        }
    }

    public int getCurrentState() {
        return mCurrentState;
    }

    public void setUserConfirmListener(IUserConfirm confirmListener) {
        IMLogger.d("setUserConfirmListener ...");
        CustomLicenseVerificationFactory.setUserConfirmListener(confirmListener);
    }
}
