package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.k;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

final class aj extends Handler {
    aj(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        boolean z = true;
        switch (message.what) {
            case 100:
                boolean z2 = message.arg1 == 1;
                boolean a = TbsDownloader.b(true, false);
                if (message.obj != null && (message.obj instanceof TbsDownloader.TbsDownloaderCallback)) {
                    TbsLog.i(TbsDownloader.LOGTAG, "needDownload-onNeedDownloadFinish needStartDownload=" + a);
                    if (!a || z2) {
                        ((TbsDownloader.TbsDownloaderCallback) message.obj).onNeedDownloadFinish(a, TbsDownloadConfig.getInstance(TbsDownloader.c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0));
                    }
                }
                if (TbsShareManager.isThirdPartyApp(TbsDownloader.c) && a) {
                    TbsDownloader.startDownload(TbsDownloader.c);
                    return;
                }
                return;
            case 101:
            case 108:
                FileLock fileLock = null;
                FileOutputStream b = k.b(TbsDownloader.c, false, "tbs_download_lock_file" + TbsDownloadConfig.getInstance(TbsDownloader.c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0) + ".txt");
                if (b != null) {
                    fileLock = k.a(TbsDownloader.c, b);
                    if (fileLock == null) {
                        TbsLog.i(TbsDownloader.LOGTAG, "file lock locked,wx or qq is downloading");
                        TbsDownloadConfig.getInstance(TbsDownloader.c).setDownloadInterruptCode(-203);
                        TbsLog.i(TbsDownloader.LOGTAG, "MSG_START_DOWNLOAD_DECOUPLECORE return #1");
                        return;
                    }
                } else if (k.a(TbsDownloader.c)) {
                    TbsDownloadConfig.getInstance(TbsDownloader.c).setDownloadInterruptCode(-204);
                    TbsLog.i(TbsDownloader.LOGTAG, "MSG_START_DOWNLOAD_DECOUPLECORE return #2");
                    return;
                }
                boolean z3 = message.arg1 == 1;
                TbsDownloadConfig instance = TbsDownloadConfig.getInstance(TbsDownloader.c);
                if (!TbsDownloader.c(false, z3, 108 == message.what)) {
                    QbSdk.l.onDownloadFinish(110);
                } else if (z3 && am.a().a(TbsDownloader.c, TbsDownloadConfig.getInstance(TbsDownloader.c).mPreferences.getInt(TbsDownloadConfig.TbsConfigKey.KEY_TBS_DOWNLOAD_V, 0))) {
                    QbSdk.l.onDownloadFinish(TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR);
                    instance.setDownloadInterruptCode(-213);
                } else if (instance.mPreferences.getBoolean(TbsDownloadConfig.TbsConfigKey.KEY_NEEDDOWNLOAD, false)) {
                    TbsDownloadConfig.getInstance(TbsDownloader.c).setDownloadInterruptCode(-215);
                    ag b2 = TbsDownloader.g;
                    if (108 != message.what) {
                        z = false;
                    }
                    b2.b(z3, z);
                } else {
                    QbSdk.l.onDownloadFinish(110);
                }
                TbsLog.i(TbsDownloader.LOGTAG, "------freeFileLock called :");
                k.a(fileLock, b);
                return;
            case 102:
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_REPORT_DOWNLOAD_STAT");
                int a2 = TbsShareManager.isThirdPartyApp(TbsDownloader.c) ? TbsShareManager.a(TbsDownloader.c, false) : am.a().m(TbsDownloader.c);
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] localTbsVersion=" + a2);
                TbsDownloader.g.a(a2);
                TbsLogReport.a(TbsDownloader.c).b();
                return;
            case 103:
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_CONTINUEINSTALL_TBSCORE");
                if (message.arg1 == 0) {
                    am.a().a((Context) message.obj, true);
                    return;
                } else {
                    am.a().a((Context) message.obj, false);
                    return;
                }
            case 104:
                TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.handleMessage] MSG_UPLOAD_TBSLOG");
                TbsLogReport.a(TbsDownloader.c).c();
                return;
            default:
                return;
        }
    }
}
