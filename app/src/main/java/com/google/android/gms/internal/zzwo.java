package com.google.android.gms.internal;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import com.google.android.gms.ads.internal.zzbs;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;

final class zzwo implements DialogInterface.OnClickListener {
    private /* synthetic */ String zzNO;
    private /* synthetic */ String zzNP;
    private /* synthetic */ zzwn zzNQ;

    zzwo(zzwn zzwn, String str, String str2) {
        this.zzNQ = zzwn;
        this.zzNO = str;
        this.zzNP = str2;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        DownloadManager downloadManager = (DownloadManager) this.zzNQ.mContext.getSystemService(DownloadDBHelper.TABLE_DOWNLOAD);
        try {
            String str = this.zzNO;
            String str2 = this.zzNP;
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, str2);
            zzbs.zzbB().zza(request);
            downloadManager.enqueue(request);
        } catch (IllegalStateException e) {
            this.zzNQ.zzan("Could not store picture.");
        }
    }
}
