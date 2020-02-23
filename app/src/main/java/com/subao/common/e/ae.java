package com.subao.common.e;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.e.z;
import com.subao.common.g.c;

/* compiled from: PortalRedirectConfigDownloader */
public class ae extends ac {
    private final c a;

    protected ae(z.a aVar, c cVar) {
        super(new z.b(aVar));
        this.a = cVar;
    }

    public static void a(z.a aVar, c cVar) {
        ac.a((ac) new ae(aVar, cVar));
    }

    /* access modifiers changed from: protected */
    public void b(aa aaVar) {
        if (aaVar.c != null && aaVar.c.length > 2) {
            this.a.a(0, "key_redirect_game_ip", aaVar.c);
        }
    }

    /* access modifiers changed from: protected */
    public void a(@NonNull String str, @Nullable String str2) {
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String b() {
        return "redirect";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public String a() {
        return "configs/redirect_game_ip";
    }
}
