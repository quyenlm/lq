package com.tencent.mna.base.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public final class NetworkChangeReceiver extends BroadcastReceiver {
    private int a = -1;
    private b b = null;

    private static class a {
        /* access modifiers changed from: private */
        public static final NetworkChangeReceiver a = new NetworkChangeReceiver();
    }

    public interface b {
        void a(int i);
    }

    public void onReceive(Context context, Intent intent) {
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            int a2 = k.a(context);
            if (this.a == -1) {
                this.a = a2;
            } else if (this.a != a2) {
                this.a = a2;
                a(a2);
            }
        }
    }

    public static void a(Context context) {
        if (context == null) {
            h.d("register NetworkChangeReceiver failed, context is null");
            return;
        }
        context.registerReceiver(a.a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        h.a("register NetworkChangeReceiver succeed");
    }

    public static void a(b bVar) {
        if (bVar != null) {
            a.a.b = bVar;
        }
    }

    public static void a() {
        a.a.b = null;
    }

    private void a(int i) {
        if (a.a.b != null) {
            a.a.b.a(i);
        }
    }
}
