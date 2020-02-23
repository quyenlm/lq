package com.google.android.gms.internal;

import java.util.Iterator;

public final class yy {
    private static long zzc(xh<?> xhVar) {
        long j = 8;
        if (!(xhVar instanceof xc) && !(xhVar instanceof xk)) {
            if (xhVar instanceof wo) {
                j = 4;
            } else if (xhVar instanceof xu) {
                j = (long) (((String) xhVar.getValue()).length() + 2);
            } else {
                String valueOf = String.valueOf(xhVar.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 24).append("Unknown leaf node type: ").append(valueOf).toString());
            }
        }
        if (xhVar.zzIR().isEmpty()) {
            return j;
        }
        return zzc((xh) xhVar.zzIR()) + 24 + j;
    }

    public static long zzn(xm xmVar) {
        long j;
        if (xmVar.isEmpty()) {
            return 4;
        }
        if (xmVar.zzIQ()) {
            return zzc((xh) xmVar);
        }
        long j2 = 1;
        Iterator it = xmVar.iterator();
        while (true) {
            j = j2;
            if (!it.hasNext()) {
                break;
            }
            xl xlVar = (xl) it.next();
            j2 = zzn(xlVar.zzFn()) + j + ((long) xlVar.zzJk().asString().length()) + 4;
        }
        return !xmVar.zzIR().isEmpty() ? j + 12 + zzc((xh) xmVar.zzIR()) : j;
    }

    public static int zzo(xm xmVar) {
        int i = 0;
        if (xmVar.isEmpty()) {
            return 0;
        }
        if (xmVar.zzIQ()) {
            return 1;
        }
        Iterator it = xmVar.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return i2;
            }
            i = zzo(((xl) it.next()).zzFn()) + i2;
        }
    }
}
