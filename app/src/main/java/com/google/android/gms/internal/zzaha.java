package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;

final class zzaha implements zznm {
    private /* synthetic */ List zzZw;
    private /* synthetic */ zznl zzZx;
    private /* synthetic */ Context zztF;

    zzaha(zzagz zzagz, List list, zznl zznl, Context context) {
        this.zzZw = list;
        this.zzZx = zznl;
        this.zztF = context;
    }

    public final void zzea() {
        for (String str : this.zzZw) {
            String valueOf = String.valueOf(str);
            zzafr.zzaS(valueOf.length() != 0 ? "Pinging url: ".concat(valueOf) : new String("Pinging url: "));
            this.zzZx.mayLaunchUrl(Uri.parse(str), (Bundle) null, (List<Bundle>) null);
        }
        this.zzZx.zzc((Activity) this.zztF);
    }

    public final void zzeb() {
    }
}
