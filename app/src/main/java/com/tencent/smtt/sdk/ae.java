package com.tencent.smtt.sdk;

class ae implements Runnable {
    final /* synthetic */ ad a;

    ae(ad adVar) {
        this.a = adVar;
    }

    public void run() {
        if (!TbsShareManager.forceLoadX5FromTBSDemo(this.a.b.getContext()) && TbsDownloader.needDownload(this.a.b.getContext(), false)) {
            TbsDownloader.startDownload(this.a.b.getContext());
        }
    }
}
