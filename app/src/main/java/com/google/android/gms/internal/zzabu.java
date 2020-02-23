package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzy;
import java.util.HashMap;
import java.util.concurrent.Future;

@zzzn
public final class zzabu {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    /* access modifiers changed from: private */
    public String zzQx;
    /* access modifiers changed from: private */
    public String zzUL;
    /* access modifiers changed from: private */
    public zzajg<zzaca> zzUM = new zzajg<>();
    zzy zzUN;
    public final zzrd zzUO = new zzabv(this);
    public final zzrd zzUP = new zzabw(this);
    public final zzrd zzUQ = new zzabx(this);

    public zzabu(String str, String str2) {
        this.zzUL = str2;
        this.zzQx = str;
    }

    public final void fail() {
        this.zzUM.zzg(new zzaca(0, new HashMap()));
    }

    public final Future<zzaca> zzgG() {
        return this.zzUM;
    }
}
