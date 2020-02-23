package com.tencent.msdk.stat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class b extends BroadcastReceiver {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.a.e != null) {
            this.a.e.post(new c(this));
        }
    }
}
