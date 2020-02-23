package com.tencent.smtt.sdk;

import com.tencent.tbs.video.interfaces.IUserStateChangedListener;

class be implements IUserStateChangedListener {
    final /* synthetic */ bd a;

    be(bd bdVar) {
        this.a = bdVar;
    }

    public void onUserStateChanged() {
        this.a.a.c();
    }
}
