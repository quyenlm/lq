package com.subao.common.a;

import android.content.Context;
import android.net.VpnService;

/* compiled from: SubaoVpnService */
public abstract class e extends VpnService {

    /* compiled from: SubaoVpnService */
    public interface a {
        e a();

        boolean a(Context context);
    }

    public abstract int a(Iterable<String> iterable);

    public abstract void a();

    public abstract boolean b();
}
