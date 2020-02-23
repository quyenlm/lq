package com.tencent.smtt.sdk;

class w implements Runnable {
    final /* synthetic */ v a;

    w(v vVar) {
        this.a = vVar;
    }

    public void run() {
        if (!TbsShareManager.forceLoadX5FromTBSDemo(this.a.b.getContext()) && TbsDownloader.needDownload(this.a.b.getContext(), false)) {
            TbsDownloader.startDownload(this.a.b.getContext());
        }
    }
}
