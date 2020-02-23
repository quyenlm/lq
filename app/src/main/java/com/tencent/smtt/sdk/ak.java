package com.tencent.smtt.sdk;

import com.tencent.mna.NetworkBindingListener;
import com.tencent.smtt.sdk.TbsDownloadConfig;
import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.n;

final class ak implements n.a {
    final /* synthetic */ TbsDownloadConfig a;
    final /* synthetic */ boolean b;

    ak(TbsDownloadConfig tbsDownloadConfig, boolean z) {
        this.a = tbsDownloadConfig;
        this.b = z;
    }

    public void a(int i) {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.sendRequest] httpResponseCode=" + i);
        if (TbsShareManager.isThirdPartyApp(TbsDownloader.c) && i == 200) {
            this.a.a.put(TbsDownloadConfig.TbsConfigKey.KEY_LAST_REQUEST_SUCCESS, Long.valueOf(System.currentTimeMillis()));
            this.a.a.put(TbsDownloadConfig.TbsConfigKey.KEY_REQUEST_FAIL, 0L);
            this.a.a.put(TbsDownloadConfig.TbsConfigKey.KEY_COUNT_REQUEST_FAIL_IN_24HOURS, 0L);
            this.a.commit();
        }
        if (i < 300) {
            return;
        }
        if (this.b) {
            this.a.setDownloadInterruptCode(NetworkBindingListener.NB_PREPARE_BIND_FD_FAIL);
        } else {
            this.a.setDownloadInterruptCode(-207);
        }
    }
}
