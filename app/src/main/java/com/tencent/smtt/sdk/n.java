package com.tencent.smtt.sdk;

import com.tencent.smtt.utils.TbsLog;

final class n implements TbsListener {
    n() {
    }

    public void onDownloadFinish(int i) {
        if (TbsDownloader.needDownloadDecoupleCore()) {
            TbsLog.i("QbSdk", "onDownloadFinish needDownloadDecoupleCore is true", true);
            TbsDownloader.a = true;
            return;
        }
        TbsLog.i("QbSdk", "onDownloadFinish needDownloadDecoupleCore is false", true);
        TbsDownloader.a = false;
        if (i == 100) {
        }
        if (QbSdk.A != null) {
            QbSdk.A.onDownloadFinish(i);
        }
        if (QbSdk.B != null) {
            QbSdk.B.onDownloadFinish(i);
        }
    }

    public void onDownloadProgress(int i) {
        if (QbSdk.B != null) {
            QbSdk.B.onDownloadProgress(i);
        }
        if (QbSdk.A != null) {
            QbSdk.A.onDownloadProgress(i);
        }
    }

    public void onInstallFinish(int i) {
        if (i == 200 || i == 220) {
        }
        QbSdk.setTBSInstallingStatus(false);
        TbsDownloader.a = false;
        if (TbsDownloader.startDecoupleCoreIfNeeded()) {
            TbsDownloader.a = true;
        } else {
            TbsDownloader.a = false;
        }
        if (QbSdk.A != null) {
            QbSdk.A.onInstallFinish(i);
        }
        if (QbSdk.B != null) {
            QbSdk.B.onInstallFinish(i);
        }
    }
}
