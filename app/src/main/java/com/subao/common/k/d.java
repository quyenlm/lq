package com.subao.common.k;

import MTT.EFvrECode;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.support.annotation.NonNull;
import com.subao.common.k.b;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"NewApi"})
/* compiled from: NetworkWatcherImpl_Support */
class d implements c {
    @NonNull
    private final ConnectivityManager a;
    private final List<ConnectivityManager.NetworkCallback> b = new ArrayList(4);

    d(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            throw new b.d(EFvrECode._ERR_FVR_BOOKMARK_EXCEPTION);
        }
        this.a = connectivityManager;
    }

    private static int a(b.e eVar) {
        switch (eVar) {
            case WIFI:
                return 1;
            case BLUETOOTH:
                return 2;
            case ETHERNET:
                return 3;
            case VPN:
                return 4;
            default:
                return 0;
        }
    }

    private void a(b.e eVar, ConnectivityManager.NetworkCallback networkCallback) {
        try {
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addTransportType(a(eVar));
            NetworkRequest build = builder.build();
            if (build != null) {
                this.a.requestNetwork(build, networkCallback);
                return;
            }
            com.subao.common.d.c("SubaoParallel", "NetworkRequest.Builder.build() return null");
            com.subao.common.d.c("SubaoParallel", "requestNetwork() failed !!!");
            throw new b.d(2002);
        } catch (RuntimeException e) {
            com.subao.common.d.c("SubaoParallel", e.getMessage());
        }
    }

    public Object a(b.e eVar, b.a aVar) {
        a aVar2 = new a(aVar);
        a(eVar, (ConnectivityManager.NetworkCallback) aVar2);
        synchronized (this) {
            this.b.add(aVar2);
        }
        return aVar2;
    }

    public void a(Object obj) {
        boolean remove;
        if (obj != null) {
            synchronized (this) {
                remove = this.b.remove(obj);
            }
            if (remove) {
                this.a.unregisterNetworkCallback((ConnectivityManager.NetworkCallback) obj);
            }
        }
    }

    public void a() {
        ConnectivityManager.NetworkCallback[] networkCallbackArr;
        synchronized (this) {
            int size = this.b.size();
            if (size > 0) {
                this.b.clear();
                networkCallbackArr = (ConnectivityManager.NetworkCallback[]) this.b.toArray(new ConnectivityManager.NetworkCallback[size]);
            } else {
                networkCallbackArr = null;
            }
        }
        if (networkCallbackArr != null) {
            for (ConnectivityManager.NetworkCallback unregisterNetworkCallback : networkCallbackArr) {
                this.a.unregisterNetworkCallback(unregisterNetworkCallback);
            }
        }
    }

    /* compiled from: NetworkWatcherImpl_Support */
    static class a extends ConnectivityManager.NetworkCallback {
        private final b.a a;

        public a(b.a aVar) {
            if (aVar == null) {
                throw new NullPointerException("Null callback");
            }
            this.a = aVar;
        }

        public void onAvailable(Network network) {
            this.a.b(new e(network));
        }

        public void onLost(Network network) {
            this.a.c(new e(network));
        }
    }
}
