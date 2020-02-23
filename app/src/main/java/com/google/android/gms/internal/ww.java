package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;

public final class ww {
    private final List<qr> zzcao;
    private final List<String> zzcap;

    private ww(List<qr> list, List<String> list2) {
        if (list.size() != list2.size() - 1) {
            throw new IllegalArgumentException("Number of posts need to be n-1 for n hashes in CompoundHash");
        }
        this.zzcao = list;
        this.zzcap = list2;
    }

    /* access modifiers changed from: private */
    public static void zza(xm xmVar, wy wyVar) {
        if (xmVar.zzIQ()) {
            wyVar.zzb((xh<?>) (xh) xmVar);
        } else if (xmVar.isEmpty()) {
            throw new IllegalArgumentException("Can't calculate hash on empty node!");
        } else if (!(xmVar instanceof wr)) {
            String valueOf = String.valueOf(xmVar);
            throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 33).append("Expected children node, but got: ").append(valueOf).toString());
        } else {
            ((wr) xmVar).zza(new wx(wyVar), true);
        }
    }

    public static ww zzh(xm xmVar) {
        wz wzVar = new wz(xmVar);
        if (xmVar.isEmpty()) {
            return new ww(Collections.emptyList(), Collections.singletonList(""));
        }
        wy wyVar = new wy(wzVar);
        zza(xmVar, wyVar);
        wyVar.zzIZ();
        return new ww(wyVar.zzcid, wyVar.zzcie);
    }

    public final List<qr> zzFR() {
        return Collections.unmodifiableList(this.zzcao);
    }

    public final List<String> zzFS() {
        return Collections.unmodifiableList(this.zzcap);
    }
}
